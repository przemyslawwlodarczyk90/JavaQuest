import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getLessons } from '../api'
import { useProgress } from '../useProgress'

export default function LessonListPage() {
  const { chapterSlug } = useParams()
  const { isCompleted, countInChapter, setLessonCompleted } = useProgress()
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
    return <p className="error">Nie udało się wczytać lekcji: {error}</p>
  }

  const homePath = chapterSlug.startsWith('_js_') ? '/js' : '/'

  return (
    <div>
      <Link to={homePath} className="back-link">
        &larr; Wszystkie rozdziały
      </Link>
      <h2>{chapterSlug}</h2>
      <p className="hint">
        Zrobione: {countInChapter(chapterSlug)} / {lessons.length}
      </p>
      <ol className="lesson-list">
        {lessons.map((lesson) => (
          <li
            key={lesson.slug}
            className={`lesson-list__item ${isCompleted(chapterSlug, lesson.slug) ? 'lesson-list__item--done' : ''}`}
          >
            <Link to={`/rozdzial/${chapterSlug}/${lesson.slug}`} className="lesson-list__title">
              {lesson.title}
            </Link>
            <input
              type="checkbox"
              className="lesson-list__check"
              checked={isCompleted(chapterSlug, lesson.slug)}
              onChange={(event) => setLessonCompleted(chapterSlug, lesson.slug, event.target.checked)}
              aria-label={`Zrobiłem lekcję: ${lesson.title}`}
              title="Zrobiłem tę lekcję"
            />
          </li>
        ))}
      </ol>
    </div>
  )
}
