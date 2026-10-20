import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getLessons, getChapters } from '../api'
import { useProgress } from '../useProgress'
import MasteryStars from '../components/MasteryStars'

export default function LessonListPage() {
  const { chapterSlug } = useParams()
  const { isCompleted, countInChapter, setLessonCompleted, masteryOf, countMasteredInChapter } = useProgress()
  const [status, setStatus] = useState('loading')
  const [lessons, setLessons] = useState([])
  const [error, setError] = useState('')
  const [chapterTitle, setChapterTitle] = useState('Lekcje rozdziału')

  useEffect(() => {
    let cancelled = false
    setStatus('loading')
    setChapterTitle('Lekcje rozdziału')
    const track = chapterSlug.startsWith('_js_') ? 'JAVASCRIPT' : chapterSlug.startsWith('_lx_') ? 'LINUX' : chapterSlug.startsWith('_ocp_') ? 'OCP' : 'JAVA'
    getChapters(track).then(chapters => {
      if (!cancelled) setChapterTitle(chapters.find(chapter => chapter.slug === chapterSlug)?.title ?? 'Lekcje rozdziału')
    }).catch(() => {})
    getLessons(chapterSlug)
      .then((data) => {
        if (cancelled) return
        setLessons(data)
        setStatus('ok')
      })
      .catch((err) => {
        if (cancelled) return
        setError(err.message)
        setStatus('error')
      })
    return () => { cancelled = true }
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

  const homePath = chapterSlug.startsWith('_js_') ? '/js' : chapterSlug.startsWith('_lx_') ? '/linux' : chapterSlug.startsWith('_ocp_') ? '/ocp' : '/'

  return (
    <div>
      <Link to={homePath} className="back-link">
        &larr; Wszystkie rozdziały
      </Link>
      <h2>{chapterTitle}</h2>
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
              className={`lesson-list__item ${lesson.slug === recommendedSlug ? 'lesson-list__item--next' : ''} ${isCompleted(chapterSlug, lesson.slug) ? 'lesson-list__item--done' : ''}`}
            >
              <div className="lesson-list__body"><Link to={`/rozdzial/${chapterSlug}/${lesson.slug}`} className="lesson-list__title">
                {lesson.title}
              </Link>
              {lesson.slug === recommendedSlug && <span className="lesson-list__next">Proponowana następna</span>}
              </div>
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
