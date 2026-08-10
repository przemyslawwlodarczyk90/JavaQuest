import { useState } from 'react'

export default function QuizView({ questions }) {
  const [index, setIndex] = useState(0)
  const [selected, setSelected] = useState(null)
  const [correctCount, setCorrectCount] = useState(0)

  if (questions.length === 0) {
    return <p className="placeholder">Quiz tej lekcji jest w przygotowaniu.</p>
  }

  if (index >= questions.length) {
    return (
      <div className="quiz-view__result">
        <h3>Koniec quizu!</h3>
        <p>
          Wynik: {correctCount} / {questions.length}
        </p>
        <button type="button" onClick={() => {
          setIndex(0)
          setSelected(null)
          setCorrectCount(0)
        }}>
          Zacznij od nowa
        </button>
      </div>
    )
  }

  const question = questions[index]
  const answered = selected !== null
  const isCorrect = selected === question.correct

  function choose(optionKey) {
    if (answered) return
    setSelected(optionKey)
    if (optionKey === question.correct) {
      setCorrectCount((c) => c + 1)
    }
  }

  function next() {
    setIndex((i) => i + 1)
    setSelected(null)
  }

  return (
    <div className="quiz-view">
      <div className="quiz-view__progress">
        Pytanie {index + 1} / {questions.length} &middot; poprawne: {correctCount}
      </div>

      <p className="quiz-view__question">{question.question}</p>

      <div className="quiz-view__options">
        {Object.entries(question.options).map(([key, text]) => {
          let className = 'quiz-view__option'
          if (answered && key === question.correct) className += ' quiz-view__option--correct'
          else if (answered && key === selected) className += ' quiz-view__option--wrong'

          return (
            <button
              key={key}
              type="button"
              className={className}
              onClick={() => choose(key)}
              disabled={answered}
            >
              <strong>{key}.</strong> {text}
            </button>
          )
        })}
      </div>

      {answered && (
        <div className={`quiz-view__feedback ${isCorrect ? 'quiz-view__feedback--correct' : 'quiz-view__feedback--wrong'}`}>
          <strong>{isCorrect ? 'Dobrze!' : `Źle - poprawna odpowiedź to ${question.correct}.`}</strong>
          <p>{question.explanation}</p>
          <button type="button" onClick={next}>
            {index === questions.length - 1 ? 'Zobacz wynik' : 'Następne pytanie →'}
          </button>
        </div>
      )}
    </div>
  )
}
