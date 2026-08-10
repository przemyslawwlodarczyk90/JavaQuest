import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [status, setStatus] = useState('loading')
  const [message, setMessage] = useState('')

  useEffect(() => {
    fetch('/api/hello')
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP ${response.status}`)
        }
        return response.text()
      })
      .then((text) => {
        setMessage(text)
        setStatus('ok')
      })
      .catch((error) => {
        setMessage(error.message)
        setStatus('error')
      })
  }, [])

  return (
    <main className="app">
      <h1>JavaQuest</h1>
      <p className="subtitle">
        Frontend React (Vite), zbudowany do <code>src/main/resources/static</code>
        i serwowany przez Spring Boota.
      </p>

      <section className="status-card" data-status={status}>
        <span className="status-label">Backend (/api/hello):</span>
        <span className="status-value">
          {status === 'loading' && 'lacze sie...'}
          {status === 'ok' && message}
          {status === 'error' && `blad: ${message}`}
        </span>
      </section>
    </main>
  )
}

export default App
