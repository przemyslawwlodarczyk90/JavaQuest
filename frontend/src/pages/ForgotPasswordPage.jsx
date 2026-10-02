import { useState } from 'react'
import { Link } from 'react-router-dom'
import { forgotPassword } from '../api'

// Backend zwraca ZAWSZE ten sam komunikat, niezaleznie czy konto istnieje - patrz
// AuthService.forgotPassword(). Ten formularz nigdy nie pokazuje bledu walidacji "konto nie
// istnieje" z tego samego powodu (nie zdradzac zarejestrowanych adresow).
function ForgotPasswordPage() {
  const [email, setEmail] = useState('')
  const [error, setError] = useState(null)
  const [submitting, setSubmitting] = useState(false)
  const [sent, setSent] = useState(null)

  async function handleSubmit(event) {
    event.preventDefault()
    setError(null)
    setSubmitting(true)
    try {
      const data = await forgotPassword(email)
      setSent(data.message)
    } catch (err) {
      setError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  if (sent) {
    return (
      <section className="auth-card">
        <h2>Sprawdź skrzynkę</h2>
        <p className="auth-notice">{sent}</p>
        <p className="auth-switch">
          <Link to="/logowanie">Przejdź do logowania</Link>
        </p>
      </section>
    )
  }

  return (
    <section className="auth-card">
      <h2>Nie pamiętasz hasła?</h2>
      <p className="hint">Podaj adres e-mail, na który wyślemy link do zresetowania hasła.</p>
      <form className="auth-form" onSubmit={handleSubmit}>
        <label className="auth-field">
          E-mail
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} autoComplete="email" required />
        </label>
        {error && (
          <p className="auth-error" role="alert">
            {error}
          </p>
        )}
        <button type="submit" className="auth-button" disabled={submitting}>
          {submitting ? 'Wysyłanie…' : 'Wyślij link do resetu hasła'}
        </button>
      </form>
      <p className="auth-switch">
        <Link to="/logowanie">Wróć do logowania</Link>
      </p>
    </section>
  )
}

export default ForgotPasswordPage
