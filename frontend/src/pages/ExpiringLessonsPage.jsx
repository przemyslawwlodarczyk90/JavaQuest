import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getExpiringLessons } from '../api'
import { useProgress } from '../useProgress'
import MasteryStars from '../components/MasteryStars'

const names = { JAVA: 'Java', JAVASCRIPT: 'JavaScript', REACT: 'React', LINUX: 'Linux', OCP: 'OCP Java 21', NETWORKING: 'Sieci' }

export default function ExpiringLessonsPage() {
  const { summary } = useProgress()
  const [lessons, setLessons] = useState([])
  const [status, setStatus] = useState('loading')
  const [retry, setRetry] = useState(0)
  useEffect(() => {
    let cancelled = false
    setStatus('loading')
    getExpiringLessons().then(data => { if (!cancelled) { setLessons(data); setStatus('ok') } })
      .catch(() => { if (!cancelled) setStatus('error') })
    return () => { cancelled = true }
  }, [retry, summary])
  return <section className="expiring-lessons">
    <Link to="/" className="back-link">← Wszystkie kursy</Link>
    <p className="eyebrow">Zadbaj o utrwaloną wiedzę</p><h2>Pilne powtórki</h2>
    <p className="expiring-intro">Punkty z tych lekcji wygasną w ciągu 7 dni. Najwcześniejsze terminy są na górze. Zaliczenie quizu pozwala odnowić poziom.</p>
    {status === 'loading' && <p className="hint" role="status">Wczytywanie powtórek…</p>}
    {status === 'error' && <div role="alert"><p>Nie udało się pobrać lekcji.</p><button type="button" onClick={() => setRetry(value => value + 1)}>Spróbuj ponownie</button></div>}
    {status === 'ok' && lessons.length === 0 && <div className="expiring-empty"><h3>Masz spokojny tydzień</h3><p>Żadne punkty nie wygasają w najbliższych 7 dniach. Możesz kontynuować naukę w swoim tempie.</p><Link to="/">Przejdź do kursów →</Link></div>}
    {status === 'ok' && lessons.length > 0 && <ol className="expiring-list">{lessons.map(lesson => <li key={`${lesson.chapterSlug}/${lesson.lessonSlug}`}>
      <div className="expiring-list__body"><span className="expiring-list__course">{names[lesson.track] ?? lesson.track} · {lesson.chapterTitle}</span><Link to={`/rozdzial/${lesson.chapterSlug}/${lesson.lessonSlug}`}>{lesson.title}</Link><MasteryStars stars={lesson.stars} reviewSuggested /></div>
      <div className="expiring-list__deadline"><strong>{lesson.daysRemaining === 0 ? 'Dzisiaj' : lesson.daysRemaining === 1 ? 'W ciągu doby' : `Za ${lesson.daysRemaining} dni`}</strong><span>{lesson.pointsAtRisk} pkt do odnowienia</span></div>
    </li>)}</ol>}
  </section>
}
