import { useEffect, useRef, useState } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { confirmAccount } from '../api'

// Cel linku z maila powitalnego (/potwierdz?token=...). Token jest jednorazowy, a StrictMode w
// dev odpala efekt dwa razy - bez "requested" drugie wywolanie dostaloby "link juz uzyty".
function ConfirmPage() {
  const [params] = useSearchParams()
  const token = params.get('token')
  const requested = useRef(false)
  const [result, setResult] = useState(
    token ? { state: 'loading' } : { state: 'error', message: 'Brak tokenu w linku.' },
  )

  useEffect(() => {
    if (!token || requested.current) {
      return
    }
    requested.current = true
    confirmAccount(token)
      .then((data) => setResult({ state: 'ok', message: data.message }))
      .catch((err) => setResult({ state: 'error', message: err.message }))
  }, [token])

  return (
    <section className="auth-card">
      <h2>Potwierdzenie konta</h2>
      {result.state === 'loading' && <p className="hint">Aktywuję konto…</p>}
      {result.state === 'ok' && <p className="auth-notice">{result.message}</p>}
      {result.state === 'error' && (
        <p className="auth-error" role="alert">
          {result.message}
        </p>
      )}
      {result.state !== 'loading' && (
        <p className="auth-switch">
          <Link to="/logowanie">Przejdź do logowania</Link>
        </p>
      )}
    </section>
  )
}

export default ConfirmPage
