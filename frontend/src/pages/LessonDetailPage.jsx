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
    setStatus('loading')
    const tab = TABS.find((t) => t.key === activeTab)
    tab
      .loader(chapterSlug, lessonSlug)
      .then((result) => {
        setData(result)
        setStatus('ok')
      })
      .catch((err) => {
        setError(err.message)
        setStatus('error')
      })
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
            onClick={() => setActiveTab(tab.key)}
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
