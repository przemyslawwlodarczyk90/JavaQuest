import { useState } from 'react'

// Pytania otwarte (tor OCP): kolejnosc losowana raz na wejscie do zakladki, bez pisania kodu.
function shuffled(items) {
  const copy = [...items]
  for (let i = copy.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1))
    ;[copy[i], copy[j]] = [copy[j], copy[i]]
  }
  return copy
}

export default function ExercisesView({ exercises: source, openQuestions = false }) {
  const [exercises] = useState(() => (openQuestions ? shuffled(source) : source))
  const [index, setIndex] = useState(0)
  const [answer, setAnswer] = useState('')
  const [showHint, setShowHint] = useState(false)
  const [showSolution, setShowSolution] = useState(false)

  if (exercises.length === 0) {
    return <p className="placeholder">Zadania tej lekcji są w przygotowaniu.</p>
  }

  const exercise = exercises[index]

  function goTo(newIndex) {
    setIndex(newIndex)
    setAnswer('')
    setShowHint(false)
    setShowSolution(false)
  }

  return (
    <div className="exercise-view">
      <div className="exercise-view__progress">
        {openQuestions ? 'Pytanie' : 'Zadanie'} {index + 1} / {exercises.length}
      </div>

      <p className="exercise-view__prompt">{exercise.prompt}</p>

      <div className="code-editor__bar"><span aria-hidden="true">&lt;/&gt;</span> {openQuestions ? 'Twoja odpowiedź' : 'Twoje rozwiązanie'}</div>
      <textarea
        aria-label={openQuestions ? 'Twoja odpowiedź' : 'Twoje rozwiązanie'}
        spellCheck={false}
        className="exercise-view__answer"
        placeholder={openQuestions ? 'Twoja odpowiedź...' : 'Twoje rozwiązanie...'}
        value={answer}
        onChange={(e) => setAnswer(e.target.value)}
        rows={10}
      />

      <div className="exercise-view__actions">
        <button type="button" onClick={() => setShowHint(true)} disabled={showHint}>
          💡 Podpowiedź
        </button>
        <button type="button" onClick={() => setShowSolution(true)} disabled={showSolution}>
          ✅ {openQuestions ? 'Odpowiedź' : 'Rozwiązanie'}
        </button>
      </div>

      {showHint && (
        <div className="exercise-view__hint">
          <strong>Podpowiedź:</strong> {exercise.hint}
        </div>
      )}

      {showSolution && (
        <div className={`exercise-view__solution${openQuestions ? ' exercise-view__solution--prose' : ''}`}>
          <strong>{openQuestions ? 'Odpowiedź:' : 'Przykładowe rozwiązanie:'}</strong>
          <pre>{exercise.solution}</pre>
        </div>
      )}

      <div className="exercise-view__nav">
        <button type="button" onClick={() => goTo(index - 1)} disabled={index === 0}>
          &larr; Poprzednie
        </button>
        <button type="button" onClick={() => goTo(index + 1)} disabled={index === exercises.length - 1}>
          Następne &rarr;
        </button>
      </div>
    </div>
  )
}
