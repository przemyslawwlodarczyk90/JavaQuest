import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getExercises, getQuiz, getTheory } from '../api'
import TheoryView from '../components/TheoryView'
import ExercisesView from '../components/ExercisesView'
import QuizView from '../components/QuizView'

const TABS = [
  { key: 'theory', label: 'Teoria', loader: getTheory },
  { key: 'exercises', label: 'Zadania', loader: getExercises },
  { key: 'quiz', label: 'Quiz', loader: getQuiz },
]

export default function LessonDetailPage() {
  const { chapterSlug, lessonSlug } = useParams()
  const [activeTab, setActiveTab] = useState('theory')
  const [status, setStatus] = useState('loading')
  const [data, setData] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    // Uwaga: bez flagi "cancelled" szybkie przelaczenie zakladki (np. klik
    // w Quiz zanim fetch Teorii zdazyl sie skonczyc) powodowalo, ze
    // PozNIEJ rozwiazujacy sie, JUZ NIEAKTUALNY fetch nadpisywal "data"
    // ksztaltem z INNEJ zakladki (np. bloki teorii zamiast pytan quizu) -
    // QuizView probowal wtedy zrobic Object.entries(question.options) na
    // obiekcie bez pola "options" i caly komponent sie wywalal (brak
    // Error Boundary w aplikacji = pusta biala strona, blad tylko w konsoli).
    let cancelled = false
    setStatus('loading')
    const tab = TABS.find((t) => t.key === activeTab)
    tab
      .loader(chapterSlug, lessonSlug)
      .then((result) => {
        if (cancelled) return
        setData(result)
        setStatus('ok')
      })
      .catch((err) => {
        if (cancelled) return
        setError(err.message)
        setStatus('error')
      })
    return () => {
      cancelled = true
    }
  }, [chapterSlug, lessonSlug, activeTab])

  return (
    <div className="lesson-detail">
      <Link to={`/rozdzial/${chapterSlug}`} className="back-link">
        &larr; Lista lekcji
      </Link>
      <h2>{lessonSlug}</h2>

      <nav className="lesson-detail__tabs">
        {TABS.map((tab) => (
          <button
            key={tab.key}
            type="button"
            className={activeTab === tab.key ? 'active' : ''}
            onClick={() => {
              // setStatus('loading') TU (nie tylko wewnatrz useEffect ponizej) jest
              // KLUCZOWE: React commituje ten re-render OD RAZU po kliknieciu, ZANIM
              // efekt zdazy w ogole wystartowac (efekty odpalaja sie PO namalowaniu).
              // Bez tego, przez JEDNA klatke renderu "activeTab" byl juz nowy (np.
              // 'quiz'), ale "status"/"data" byly WCIAZ stare (np. 'ok' + bloki
              // teorii z poprzedniej zakladki) - QuizView dostawal dane w zlym
              // ksztalcie i wywalal sie na Object.entries(question.options).
              // Wywolane w TYM SAMYM handlerze co setActiveTab, React batchuje obie
              // aktualizacje w JEDEN render, wiec "status" i "activeTab" zmieniaja
              // sie RAZEM, nigdy osobno.
              setStatus('loading')
              setActiveTab(tab.key)
            }}
          >
            {tab.label}
          </button>
        ))}
      </nav>

      <div className="lesson-detail__content">
        {status === 'loading' && <p className="hint">Wczytywanie...</p>}
        {status === 'error' && <p className="error">Błąd: {error}</p>}
        {status === 'ok' && activeTab === 'theory' && <TheoryView blocks={data} />}
        {status === 'ok' && activeTab === 'exercises' && <ExercisesView exercises={data} />}
        {status === 'ok' && activeTab === 'quiz' && <QuizView questions={data} />}
      </div>
    </div>
  )
}
