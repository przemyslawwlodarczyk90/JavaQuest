import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getLessons } from '../api'

export default function LessonListPage() {
  const { chapterSlug } = useParams()
  const [status, setStatus] = useState('loading')
  const [lessons, setLessons] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    setStatus('loading')
    getLessons(chapterSlug)
      .then((data) => {
        setLessons(data)
        setStatus('ok')
      })
      .catch((err) => {
        setError(err.message)
        setStatus('error')
      })
  }, [chapterSlug])

  if (status === 'loading') {
    return <p className="hint">Wczytywanie lekcji...</p>
  }

  if (status === 'error') {
    return <p className="error">Nie udalo sie wczytac lekcji: {error}</p>
  }

  return (
    <div>
      <Link to="/" className="back-link">
        &larr; Wszystkie rozdzialy
      </Link>
      <h2>{chapterSlug}</h2>
      <ol className="lesson-list">
        {lessons.map((lesson) => (
          <li key={lesson.slug} className="lesson-list__item">
            <span className="lesson-list__title">{lesson.title}</span>
            <span className="lesson-list__badge">tresc w przygotowaniu</span>
          </li>
        ))}
      </ol>
    </div>
  )
}
