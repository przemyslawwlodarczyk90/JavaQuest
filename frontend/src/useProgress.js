import { createContext, useContext } from 'react'

// Osobny plik od ProgressProvider (ProgressContext.jsx), zeby ten zawieral wylacznie komponent (fast refresh).
export const ProgressContext = createContext(null)

export function useProgress() {
  const context = useContext(ProgressContext)
  if (!context) {
    throw new Error('useProgress musi byc uzyty wewnatrz ProgressProvider')
  }
  return context
}
