import { createContext, useContext } from 'react'

// Osobny plik od AuthProvider (AuthContext.jsx), zeby ten zawieral wylacznie komponent (fast refresh).
export const AuthContext = createContext(null)

export function useAuth() {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth musi byc uzyty wewnatrz AuthProvider')
  }
  return context
}
