import { useEffect, useRef, useState } from 'react'
import { getPointsRules } from '../api'

export default function PointsHelp({ onClose }) {
  const dialog = useRef(null)
  const [rules, setRules] = useState(null)
  const [error, setError] = useState(false)
  const [retry, setRetry] = useState(0)
  useEffect(() => {
    const previousOverflow = document.body.style.overflow
    document.body.style.overflow = 'hidden'
    dialog.current.showModal()
    const element = dialog.current
    return () => { element.close(); document.body.style.overflow = previousOverflow }
  }, [])
  useEffect(() => {
    let cancelled = false
    setError(false)
    getPointsRules().then(data => { if (!cancelled) setRules(data) })
      .catch(() => { if (!cancelled) setError(true) })
    return () => { cancelled = true }
  }, [retry])
  return <dialog ref={dialog} className="points-help" aria-labelledby="points-help-title" onCancel={onClose}>
    <header><div><p className="eyebrow">Spokojna, regularna nauka</p><h2 id="points-help-title">Jak działają punkty?</h2></div>
      <button type="button" onClick={onClose} aria-label="Zamknij instrukcję punktów">×</button></header>
    {!rules && !error && <p role="status" className="hint">Wczytywanie zasad…</p>}
    {error && <p role="alert">Nie udało się pobrać zasad. <button type="button" onClick={() => setRetry(value => value + 1)}>Spróbuj ponownie</button></p>}
    {rules && <>
      <p>1 gwiazdka to 1 punkt. Twój wynik łączy ważne gwiazdki ze wszystkich kursów.</p>
      <ol className="points-steps">
        <li><span>★</span><div><strong>Pierwsze zaliczenie zostaje na zawsze</strong><p>Quiz z co najmniej {rules.minQuestions} pytaniami i wynikiem co najmniej {rules.passPercent}% daje pierwszą gwiazdkę. Checkbox oznacza przerobienie lekcji i nie przyznaje punktów.</p></div></li>
        <li><span>★★</span><div><strong>Powtórka utrwala wiedzę</strong><p>Po co najmniej {rules.firstReviewHours} godzinach kolejny zaliczony quiz daje drugą gwiazdkę. Utrzymuje się ona przez {rules.twoStarDays} dni.</p></div></li>
        <li><span>★★★</span><div><strong>Trzeci poziom ma dłuższą ważność</strong><p>Po co najmniej {rules.secondReviewDays} dniach od zdobycia drugiej gwiazdki możesz zdobyć trzecią. Utrzymuje się przez {rules.threeStarDays} dni. Odnawiasz ją zaliczonym quizem po co najmniej {rules.renewalDays} dniach od ostatniego policzonego potwierdzenia.</p></div></li>
      </ol>
      <div className="points-help__note">Po upływie ważności tracisz po jednej gwiazdce, nigdy pierwszej. Po spadku z 3★ do 2★ rozpoczyna się kolejne {rules.twoStarDays} dni ważności drugiego poziomu.</div>
      <p className="points-help__footnote">Powtórka wykonana zbyt wcześnie nie zmienia poziomu ani jego terminu. Lista pilnych powtórek pokazuje punkty wygasające w ciągu {rules.warningDays} dni — nie oznacza to obowiązku powtarzania każdej lekcji co tydzień.</p>
    </>}
    <button type="button" className="points-help__done" onClick={onClose}>Rozumiem</button>
  </dialog>
}
