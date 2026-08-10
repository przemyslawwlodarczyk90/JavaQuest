import { useState } from 'react'

export default function ExercisesView({ exercises }) {
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
        Zadanie {index + 1} / {exercises.length}
      </div>

      <p className="exercise-view__prompt">{exercise.prompt}</p>

      <textarea
        className="exercise-view__answer"
        placeholder="Twoje rozwiązanie..."
        value={answer}
        onChange={(e) => setAnswer(e.target.value)}
        rows={8}
      />

      <div className="exercise-view__actions">
        <button type="button" onClick={() => setShowHint(true)} disabled={showHint}>
          💡 Podpowiedź
        </button>
        <button type="button" onClick={() => setShowSolution(true)} disabled={showSolution}>
          ✅ Rozwiązanie
        </button>
      </div>

      {showHint && (
        <div className="exercise-view__hint">
          <strong>Podpowiedź:</strong> {exercise.hint}
        </div>
      )}

      {showSolution && (
        <div className="exercise-view__solution">
          <strong>Przykładowe rozwiązanie:</strong>
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
