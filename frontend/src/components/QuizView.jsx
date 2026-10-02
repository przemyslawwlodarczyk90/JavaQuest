import { useState } from 'react'
import { answerQuizQuestion, startQuizAttempt } from '../api'
import { useProgress } from '../useProgress'
import MasteryStars from './MasteryStars'

// Komunikat o mastery po zakonczonym podejsciu. Celowo bez dat i terminow: "quiz zaliczony" to nie to samo
// co "nowa gwiazdka" - kolejne gwiazdki daja dopiero powtorki po przerwie (spacing liczy serwer).
function masteryMessage(result, before, after) {
  if (before == null || after == null) return null
  if (after > before) return 'Zdobywasz gwiazdkę!'
  if (!result.passed) return 'Nic nie tracisz — gwiazdek nie odejmujemy za błędy. Przejrzyj wyjaśnienia i spróbuj ponownie.'
  if (after === 3) return 'Wiedza potwierdzona — masz komplet gwiazdek.'
  return 'Wiedza potwierdzona. Kolejną gwiazdkę zdobędziesz powtórką po przerwie — wróć do tej lekcji za jakiś czas.'
}

function firstUnanswered(attempt) {
  const index = attempt.questions.findIndex((q) => !q.answer)
  return index === -1 ? attempt.questions.length : index
}

// Quiz jest losowany i oceniany na SERWERZE (patrz platform/quiz): serwer wybiera pytania i ich
// kolejnosc, zapisuje wylosowane, a poprawna odpowiedz + wyjasnienie odsyla dopiero po udzieleniu
// odpowiedzi. Odswiezenie strony w trakcie podejscia wznawia to samo podejscie ("status.activeAttempt").
export default function QuizView({ chapterSlug, lessonSlug, status }) {
  const [attempt, setAttempt] = useState(status.activeAttempt)
  const [index, setIndex] = useState(status.activeAttempt ? firstUnanswered(status.activeAttempt) : 0)
  const [passed, setPassed] = useState(status.passed)
  const [busy, setBusy] = useState(false)
  const [error, setError] = useState(null)
  const [mastery, setMastery] = useState(status.mastery ?? { stars: 0, reviewSuggested: false })
  const { refreshMastery } = useProgress()

  if (status.questionCount === 0) {
    return <p className="placeholder">Quiz tej lekcji jest w przygotowaniu.</p>
  }

  async function start() {
    setBusy(true)
    setError(null)
    try {
      const started = await startQuizAttempt(chapterSlug, lessonSlug)
      setAttempt(started)
      setIndex(firstUnanswered(started))
    } catch (err) {
      setError(err.message)
    } finally {
      setBusy(false)
    }
  }

  async function choose(question, option) {
    if (busy || question.answer) return
    setBusy(true)
    setError(null)
    try {
      const response = await answerQuizQuestion(chapterSlug, lessonSlug, attempt.id, question.position, option)
      setAttempt((prev) => ({
        ...prev,
        result: response.result ?? prev.result,
        masteryBefore: response.masteryBefore ?? prev.masteryBefore,
        masteryAfter: response.masteryAfter ?? prev.masteryAfter,
        questions: prev.questions.map((q) =>
          q.position === question.position
            ? {
                ...q,
                answer: {
                  selected: option,
                  correct: response.correct,
                  correctOption: response.correctOption,
                  explanation: response.explanation,
                },
              }
            : q,
        ),
      }))
      if (response.result?.passed) {
        setPassed(true)
      }
      if (response.result) {
        if (response.masteryAfter != null) {
          setMastery((prev) => ({ ...prev, stars: response.masteryAfter, reviewSuggested: false }))
        }
        refreshMastery()
      }
    } catch (err) {
      setError(err.message)
    } finally {
      setBusy(false)
    }
  }

  // --- Ekran startowy (brak otwartego podejscia) ---
  if (!attempt) {
    const last = status.lastResult
    return (
      <div className="quiz-view__intro">
        {status.masteryAvailable && (
          <p className="quiz-view__mastery">
            Twoje opanowanie: <MasteryStars stars={mastery.stars} reviewSuggested={mastery.reviewSuggested} />
          </p>
        )}
        {passed && <p className="quiz-view__badge quiz-view__badge--pass">Quiz zaliczony ✓</p>}
        <p>
          Quiz losuje <strong>{status.drawSize}</strong>
          {status.drawSize < status.questionCount && <> z {status.questionCount}</>} pytań w losowej kolejności
          {status.drawSize === status.questionCount && ' (za każdym razem w innej kolejności)'}. Aby zaliczyć, potrzebujesz
          co najmniej <strong>{status.requiredCorrect}</strong> z {status.drawSize} poprawnych odpowiedzi (min.{' '}
          {status.passPercent}%).
        </p>
        {last && (
          <p className="hint">
            Ostatnie podejście: {last.correctCount} / {last.total} ({last.percent}%) —{' '}
            {last.passed ? 'zaliczone' : 'niezaliczone'}.
          </p>
        )}
        {error && <p className="error">{error}</p>}
        <button type="button" onClick={start} disabled={busy}>
          {last ? (passed ? 'Rozwiąż ponownie' : 'Powtórka — nowe losowanie') : 'Zacznij quiz'}
        </button>
      </div>
    )
  }

  // --- Ekran wyniku (wszystkie pytania podejscia odpowiedziane) ---
  if (index >= attempt.questions.length) {
    const result = attempt.result
    const message = status.masteryAvailable ? masteryMessage(result, attempt.masteryBefore, attempt.masteryAfter) : null
    return (
      <div className="quiz-view__result">
        <h3>{result.passed ? 'Quiz zaliczony!' : 'Quiz niezaliczony'}</h3>
        <p>
          Wynik: {result.correctCount} / {result.total} ({result.percent}%). Wymagane: {result.requiredCorrect} /{' '}
          {result.total} (min. {status.passPercent}%).
        </p>
        {message && (
          <p className="quiz-view__mastery">
            <MasteryStars stars={attempt.masteryAfter} showHint={false} /> {message}
          </p>
        )}
        <button type="button" onClick={start} disabled={busy}>
          {result.passed ? 'Rozwiąż ponownie (nowe losowanie)' : 'Powtórka — nowe losowanie'}
        </button>
      </div>
    )
  }

  // --- Pytanie ---
  const question = attempt.questions[index]
  const answered = Boolean(question.answer)
  const correctCount = attempt.questions.filter((q) => q.answer?.correct).length

  return (
    <div className="quiz-view">
      <div className="quiz-view__progress">
        Pytanie {index + 1} / {attempt.questions.length} &middot; poprawne: {correctCount}
      </div>

      <p className="quiz-view__question">{question.question}</p>

      <div className="quiz-view__options">
        {Object.entries(question.options).map(([key, text]) => {
          let className = 'quiz-view__option'
          if (answered && key === question.answer.correctOption) className += ' quiz-view__option--correct'
          else if (answered && key === question.answer.selected) className += ' quiz-view__option--wrong'

          return (
            <button
              key={key}
              type="button"
              className={className}
              onClick={() => choose(question, key)}
              disabled={answered || busy}
            >
              <strong>{key}.</strong> {text}
            </button>
          )
        })}
      </div>

      {error && <p className="error">{error}</p>}

      {answered && (
        <div
          className={`quiz-view__feedback ${question.answer.correct ? 'quiz-view__feedback--correct' : 'quiz-view__feedback--wrong'}`}
        >
          <strong>
            {question.answer.correct ? 'Dobrze!' : `Źle - poprawna odpowiedź to ${question.answer.correctOption}.`}
          </strong>
          <p>{question.answer.explanation}</p>
          <button type="button" onClick={() => setIndex((i) => i + 1)}>
            {index === attempt.questions.length - 1 ? 'Zobacz wynik' : 'Następne pytanie →'}
          </button>
        </div>
      )}
    </div>
  )
}
