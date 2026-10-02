import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getLessons } from '../api'
import { useProgress } from '../useProgress'
import MasteryStars from '../components/MasteryStars'

export default function LessonListPage() {
  const { chapterSlug } = useParams()
  const { isCompleted, countInChapter, setLessonCompleted, masteryOf, countMasteredInChapter } = useProgress()
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

  // Miekka progresja zamiast blokad: wszystkie lekcje sa otwarte, a podpowiadamy pierwsza (w kolejnosci
  // kursu), ktorej wiedza nie jest jeszcze potwierdzona quizem.
  const recommendedSlug = lessons.find((lesson) => masteryOf(chapterSlug, lesson.slug).stars === 0)?.slug

  const homePath = chapterSlug.startsWith('_js_') ? '/js' : chapterSlug.startsWith('_lx_') ? '/linux' : '/'

  return (
    <div>
      <Link to={homePath} className="back-link">
        &larr; Wszystkie rozdziały
      </Link>
      <h2>{chapterSlug}</h2>
      <p className="hint">
        Zrobione: {countInChapter(chapterSlug)} / {lessons.length} &middot; z gwiazdką:{' '}
        {countMasteredInChapter(chapterSlug)} / {lessons.length}
      </p>
      <ol className="lesson-list">
        {lessons.map((lesson) => {
          const mastery = masteryOf(chapterSlug, lesson.slug)
          return (
            <li
              key={lesson.slug}
              className={`lesson-list__item ${isCompleted(chapterSlug, lesson.slug) ? 'lesson-list__item--done' : ''}`}
            >
              <Link to={`/rozdzial/${chapterSlug}/${lesson.slug}`} className="lesson-list__title">
                {lesson.title}
              </Link>
              {lesson.slug === recommendedSlug && <span className="lesson-list__next">Proponowana następna</span>}
              <MasteryStars stars={mastery.stars} reviewSuggested={mastery.reviewSuggested} />
              <input
                type="checkbox"
                className="lesson-list__check"
                checked={isCompleted(chapterSlug, lesson.slug)}
                onChange={(event) => setLessonCompleted(chapterSlug, lesson.slug, event.target.checked)}
                aria-label={`Zrobiłem lekcję: ${lesson.title}`}
                title="Zrobiłem tę lekcję"
              />
            </li>
          )
        })}
      </ol>
    </div>
  )
}
