import { useProgress } from '../useProgress'

const names = { JAVA: 'Java', JAVASCRIPT: 'JavaScript', LINUX: 'Linux', OCP: 'OCP Java 21' }

export default function LearningDashboard() {
  const { summary } = useProgress()
  if (!summary) return <p className="hint">Podsumowanie postępów jest chwilowo niedostępne.</p>
  return <section className="learning-dashboard" aria-label="Twoje postępy">
    <div className="dashboard-heading"><div><p className="eyebrow">Twoja nauka</p><h2>Każda lekcja ma znaczenie.</h2></div><span className="hint">Wszystkie kursy</span></div>
    <div className="dashboard-stats">
      <div><strong>{summary.points}</strong><span>ważnych punktów</span></div>
      <div><strong>{summary.completedLessons}<small> / {summary.totalLessons}</small></strong><span>ukończonych lekcji</span></div>
      <div><strong>{summary.expiringSoon}</strong><span>punktów wygasa w ciągu 7 dni</span></div>
    </div>
    <div className="course-progress">{summary.courses.map(course => <div key={course.track}>
      <div className="course-progress__label"><strong>{names[course.track] ?? course.track}</strong><span>{course.points} pkt · {course.completedLessons}/{course.totalLessons} lekcji</span></div>
      <progress aria-label={`Postęp ${names[course.track] ?? course.track}`} value={course.completedLessons} max={course.totalLessons || 1} />
    </div>)}</div>
    <p className="dashboard-note">1 gwiazdka = 1 punkt. Powtórki utrwalają wiedzę i odnawiają wyższe poziomy.</p>
  </section>
}
