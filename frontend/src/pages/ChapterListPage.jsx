import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getChapters } from '../api'

export default function ChapterListPage() {
  const [status, setStatus] = useState('loading')
  const [chapters, setChapters] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    getChapters()
      .then((data) => {
        setChapters(data)
        setStatus('ok')
      })
      .catch((err) => {
        setError(err.message)
        setStatus('error')
      })
  }, [])

  if (status === 'loading') {
    return <p className="hint">Wczytywanie rozdziałów...</p>
  }

  if (status === 'error') {
    return <p className="error">Nie udało się wczytać rozdziałów: {error}</p>
  }

  return (
    <div className="chapter-grid">
      {chapters.map((chapter) => (
        <Link key={chapter.slug} to={`/rozdzial/${chapter.slug}`} className="chapter-card">
          <span className="chapter-card__slug">{chapter.slug}</span>
          <h2>{chapter.title}</h2>
          <span className="chapter-card__count">{chapter.lessonCount} lekcji</span>
        </Link>
      ))}
    </div>
  )
}
