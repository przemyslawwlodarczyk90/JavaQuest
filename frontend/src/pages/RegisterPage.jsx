import { useState } from 'react'
import { Link, Navigate } from 'react-router-dom'
import { register } from '../api'
import { useAuth } from '../useAuth'

function RegisterPage() {
  const { user } = useAuth()
  const [form, setForm] = useState({ firstName: '', lastName: '', email: '', password: '' })
  const [error, setError] = useState(null)
  const [fieldErrors, setFieldErrors] = useState({})
  const [submitting, setSubmitting] = useState(false)
  const [sentTo, setSentTo] = useState(null)

  if (user) {
    return <Navigate to="/" replace />
  }

  const update = (name) => (event) => setForm({ ...form, [name]: event.target.value })

  async function handleSubmit(event) {
    event.preventDefault()
    setError(null)
    setFieldErrors({})
    setSubmitting(true)
    try {
      await register(form.firstName, form.lastName, form.email, form.password)
      setSentTo(form.email)
    } catch (err) {
      setError(err.message)
      setFieldErrors(err.fieldErrors ?? {})
    } finally {
      setSubmitting(false)
    }
  }

  if (sentTo) {
    return (
      <section className="auth-card">
        <h2>Sprawdź skrzynkę</h2>
        <p>
          Wysłaliśmy mail powitalny na adres <strong>{sentTo}</strong>. Kliknij link w wiadomości, aby aktywować konto
          (ważny 24 godziny), a potem się zaloguj.
        </p>
        <p className="auth-switch">
          <Link to="/logowanie">Przejdź do logowania</Link>
        </p>
      </section>
    )
  }

  const field = (name, label, type, autoComplete) => (
    <label className="auth-field">
      {label}
      <input type={type} value={form[name]} onChange={update(name)} autoComplete={autoComplete} required />
      {fieldErrors[name] && <span className="auth-field__error">{fieldErrors[name]}</span>}
    </label>
  )

  return (
    <section className="auth-card">
      <h2>Załóż konto</h2>
      <form className="auth-form" onSubmit={handleSubmit} noValidate>
        {field('firstName', 'Imię', 'text', 'given-name')}
        {field('lastName', 'Nazwisko', 'text', 'family-name')}
        {field('email', 'E-mail', 'email', 'email')}
        {field('password', 'Hasło (min. 8 znaków)', 'password', 'new-password')}
        {error && Object.keys(fieldErrors).length === 0 && (
          <p className="auth-error" role="alert">
            {error}
          </p>
        )}
        <button type="submit" className="auth-button" disabled={submitting}>
          {submitting ? 'Wysyłanie…' : 'Zarejestruj'}
        </button>
      </form>
      <p className="auth-switch">
        Masz już konto? <Link to="/logowanie">Zaloguj się</Link>
      </p>
    </section>
  )
}

export default RegisterPage
