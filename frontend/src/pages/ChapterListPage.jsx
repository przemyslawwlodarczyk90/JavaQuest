import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getChapters } from '../api'
import { useProgress } from '../useProgress'
import LearningDashboard from '../components/LearningDashboard'

export default function ChapterListPage({ track = 'JAVA' }) {
  const { countInChapter, countMasteredInChapter, pointsInChapter } = useProgress()
  const [status, setStatus] = useState('loading')
  const [chapters, setChapters] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    let cancelled = false
    setStatus('loading')
    getChapters(track)
      .then((data) => {
        if (cancelled) return
        setChapters(data)
        setStatus('ok')
      })
      .catch((err) => {
        if (cancelled) return
        setError(err.message)
        setStatus('error')
      })
    return () => { cancelled = true }
  }, [track])

  if (status === 'loading') {
    return <p className="hint">Wczytywanie rozdziałów...</p>
  }

  if (status === 'error') {
    return <p className="error">Nie udało się wczytać rozdziałów: {error}</p>
  }

  return (
    <>
    <LearningDashboard />
    <div className="section-heading"><h2>{({ JAVA: 'Java', JAVASCRIPT: 'JavaScript', REACT: 'React', LINUX: 'Linux', OCP: 'OCP Java 21' })[track] ?? track}</h2><span className="hint">{chapters.length} rozdziałów</span></div>
    <div className="chapter-grid">
      {chapters.map((chapter, index) => (
        <Link key={chapter.slug} to={`/rozdzial/${chapter.slug}`} className="chapter-card">
          <span className="chapter-card__slug">Rozdział {String(index + 1).padStart(2, '0')} <span>★ {pointsInChapter(chapter.slug)} pkt</span></span>
          <h2>{chapter.title}</h2>
          <span className="chapter-card__count">
            {countInChapter(chapter.slug)} / {chapter.lessonCount} lekcji zrobionych
          </span>
          <progress aria-label={`Postęp: ${chapter.title}`} value={countInChapter(chapter.slug)} max={chapter.lessonCount || 1} />
          {countMasteredInChapter(chapter.slug) > 0 && (
            <span className="chapter-card__count">
              ★ {countMasteredInChapter(chapter.slug)} / {chapter.lessonCount} z gwiazdką
            </span>
          )}
        </Link>
      ))}
    </div>
    </>
  )
}
