import { useEffect, useRef, useState } from 'react'
import { Link } from 'react-router-dom'
import { useProgress } from '../useProgress'
import { getMotivationalQuote } from '../api'
import PointsHelp from './PointsHelp'
import './LearningDashboard.css'

const names = { JAVA: 'Java', JAVASCRIPT: 'JavaScript', REACT: 'React', LINUX: 'Linux', OCP: 'OCP Java 21' }

export default function LearningDashboard() {
  const { summary } = useProgress()
  const [help, setHelp] = useState(false)
  const [quote, setQuote] = useState(null)
  const [quoteError, setQuoteError] = useState(false)
  const lastQuote = useRef(null)
  const request = useRef(0)
  useEffect(() => () => { request.current += 1 }, [])

  function onToggle(event) {
    if (event.target !== event.currentTarget) return
    const id = ++request.current
    if (!event.currentTarget.open) return
    setQuote(null)
    setQuoteError(false)
    let previous = lastQuote.current
    try { previous = previous ?? Number(sessionStorage.getItem('javaquest.lastQuote')) } catch { /* optional preference */ }
    getMotivationalQuote(previous).then(data => {
      if (id !== request.current) return
      setQuote(data)
      lastQuote.current = data.id
      try { sessionStorage.setItem('javaquest.lastQuote', String(data.id)) } catch { /* optional preference */ }
    }).catch(() => { if (id === request.current) setQuoteError(true) })
  }

  return <>
    <details className="learning-dashboard dashboard-disclosure" onToggle={onToggle}>
      <summary className="dashboard-toggle"><span className="dashboard-toggle__title"><span className="eyebrow">Twoja nauka</span><strong>Postępy i punkty</strong></span>
        <span className="dashboard-toggle__score">★ {summary?.points ?? '—'} <small>pkt</small></span><span className="dashboard-toggle__chevron" aria-hidden="true">⌄</span></summary>
      <div className="dashboard-body">
        <div className="dashboard-quote" aria-live="polite">
          {quote ? <figure><blockquote>„{quote.text}”</blockquote><figcaption><strong>— {quote.author}</strong></figcaption>
          </figure> : <p className="hint">{quoteError ? 'Cytat jest chwilowo niedostępny.' : 'Wybieram cytat na tę chwilę…'}</p>}
        </div>
        {!summary ? <p className="hint">Podsumowanie postępów jest chwilowo niedostępne.</p> : <>
          <div className="dashboard-stats">
            <div><strong>{summary.points}</strong><span>ważnych punktów</span></div>
            <div><strong>{summary.completedLessons}<small> / {summary.totalLessons}</small></strong><span>ukończonych lekcji</span></div>
            <div><strong>{summary.expiringSoon}</strong><span>punktów wygasa w ciągu 7 dni</span></div>
          </div>
          <div className="course-progress">{summary.courses.map(course => <div key={course.track}>
            <div className="course-progress__label"><strong>{names[course.track] ?? course.track}</strong><span>{course.points} pkt · {course.completedLessons}/{course.totalLessons} lekcji</span></div>
            <progress aria-label={`Postęp ${names[course.track] ?? course.track}`} value={course.completedLessons} max={course.totalLessons || 1} />
          </div>)}</div>
        </>}
        <div className="dashboard-actions"><button type="button" onClick={() => setHelp(true)}>Jak działają punkty?</button><Link to="/powtorki">Zobacz pilne powtórki <span aria-hidden="true">→</span></Link></div>
      </div>
    </details>
    {help && <PointsHelp onClose={() => setHelp(false)} />}
  </>
}
