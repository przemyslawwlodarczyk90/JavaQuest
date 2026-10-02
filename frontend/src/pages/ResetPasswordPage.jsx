import { useState } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { resetPassword } from '../api'

function ResetPasswordPage() {
  const [params] = useSearchParams()
  const token = params.get('token')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [error, setError] = useState(null)
  const [fieldErrors, setFieldErrors] = useState({})
  const [submitting, setSubmitting] = useState(false)
  const [done, setDone] = useState(null)

  if (!token) {
    return (
      <section className="auth-card">
        <h2>Reset hasła</h2>
        <p className="auth-error" role="alert">
          Brak tokenu w linku. Poproś o nowy link do resetu hasła.
        </p>
        <p className="auth-switch">
          <Link to="/zapomniane-haslo">Poproś o nowy link</Link>
        </p>
      </section>
    )
  }

  async function handleSubmit(event) {
    event.preventDefault()
    setError(null)
    setFieldErrors({})
    if (password !== confirmPassword) {
      setError('Podane hasła różnią się od siebie.')
      return
    }
    setSubmitting(true)
    try {
      const data = await resetPassword(token, password)
      setDone(data.message)
    } catch (err) {
      setError(err.message)
      setFieldErrors(err.fieldErrors ?? {})
    } finally {
      setSubmitting(false)
    }
  }

  if (done) {
    return (
      <section className="auth-card">
        <h2>Hasło zmienione</h2>
        <p className="auth-notice">{done}</p>
        <p className="auth-switch">
          <Link to="/logowanie">Przejdź do logowania</Link>
        </p>
      </section>
    )
  }

  return (
    <section className="auth-card">
      <h2>Ustaw nowe hasło</h2>
      <form className="auth-form" onSubmit={handleSubmit} noValidate>
        <label className="auth-field">
          Nowe hasło (min. 8 znaków)
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            autoComplete="new-password"
            required
          />
          {fieldErrors.newPassword && <span className="auth-field__error">{fieldErrors.newPassword}</span>}
        </label>
        <label className="auth-field">
          Powtórz nowe hasło
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            autoComplete="new-password"
            required
          />
        </label>
        {error && Object.keys(fieldErrors).length === 0 && (
          <p className="auth-error" role="alert">
            {error}
          </p>
        )}
        <button type="submit" className="auth-button" disabled={submitting}>
          {submitting ? 'Zapisywanie…' : 'Ustaw nowe hasło'}
        </button>
      </form>
    </section>
  )
}

export default ResetPasswordPage
