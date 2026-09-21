import { useCallback, useEffect, useMemo, useState } from 'react'
import * as api from './api'
import { AuthContext } from './useAuth'

// Stan logowania calej aplikacji. "loading" trwa tylko wtedy, gdy w localStorage jest token, ktory
// trzeba jeszcze zweryfikowac u serwera (/api/auth/me) - dzieki temu odswiezenie strony nie miga
// ekranem logowania zalogowanemu uzytkownikowi.
export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [status, setStatus] = useState(() => (api.getStoredToken() ? 'loading' : 'ready'))

  const clearSession = useCallback(() => {
    api.setStoredToken(null)
    setUser(null)
  }, [])

  useEffect(() => {
    api.onUnauthorized(clearSession)
    if (!api.getStoredToken()) {
      return
    }
    api
      .getMe()
      .then(setUser)
      .catch(clearSession)
      .finally(() => setStatus('ready'))
  }, [clearSession])

  const login = useCallback(async (email, password) => {
    const { token, user: loggedIn } = await api.login(email, password)
    api.setStoredToken(token)
    setUser(loggedIn)
  }, [])

  const value = useMemo(() => ({ user, status, login, logout: clearSession }), [user, status, login, clearSession])
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
