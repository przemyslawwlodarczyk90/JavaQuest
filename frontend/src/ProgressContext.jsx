import { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import * as api from './api'
import { useAuth } from './useAuth'
import { ProgressContext } from './useProgress'

const keyOf = (chapterSlug, lessonSlug) => `${chapterSlug}/${lessonSlug}`

function withLesson(set, key, completed) {
  const next = new Set(set)
  if (completed) {
    next.add(key)
  } else {
    next.delete(key)
  }
  return next
}

// Lekcje zaznaczone jako zrobione przez ZALOGOWANEGO uzytkownika. Jeden wspolny stan dla listy rozdzialow,
// listy lekcji i widoku lekcji - zaznaczenie w jednym miejscu od razu widac w pozostalych.
export function ProgressProvider({ children }) {
  const { user } = useAuth()
  const userId = user?.id ?? null
  const [progressOwner, setProgressOwner] = useState(userId)
  const currentAccount = progressOwner === userId && userId !== null
  const [completed, setCompleted] = useState(() => new Set())
  // Mastery (gwiazdki) - osobna informacja od checkboxa: checkbox to deklaracja "przerobilem",
  // gwiazdki to potwierdzona quizem, starzejaca sie wiedza (liczona na serwerze, bez dat).
  const [mastery, setMastery] = useState(() => new Map())
  const [summaryState, setSummary] = useState(null)
  const account = useRef(userId)
  account.current = userId
  const summary = summaryState?.userId === userId ? summaryState.data : null
  const refreshSummary = useCallback(async () => {
    if (userId === null) return
    try {
      const data = await api.getLearningSummary()
      if (account.current === userId) setSummary({ userId, data })
    } catch { /* Keep learning available when the summary cannot be loaded. */ }
  }, [userId])

  const refreshMastery = useCallback(() => {
    if (userId === null) return Promise.resolve()
    return Promise.all([refreshSummary(), api
      .getMastery()
      .then((list) => {
        if (account.current === userId) setMastery(new Map(list.map((m) => [keyOf(m.chapterSlug, m.lessonSlug), m])))
      })])
      .catch(() => {
        // brak gwiazdek nie moze blokowac nauki
      })
  }, [userId, refreshSummary])

  useEffect(() => {
    if (userId === null) return undefined
    const refresh = () => { if (document.visibilityState === 'visible') refreshMastery() }
    const timer = window.setInterval(refresh, 60000)
    window.addEventListener('focus', refresh)
    return () => { window.clearInterval(timer); window.removeEventListener('focus', refresh) }
  }, [userId, refreshMastery])

  useEffect(() => {
    let cancelled = false
    setCompleted(new Set())
    setMastery(new Map())
    setProgressOwner(userId)
    setSummary(null)
    if (userId === null) {
      // wylogowanie/zmiana konta: nie pokazuj postepu poprzedniej osoby
      return undefined
    }
    refreshMastery()
    api
      .getProgress()
      .then((list) => {
        if (!cancelled) {
          setCompleted(new Set(list.map((p) => keyOf(p.chapterSlug, p.lessonSlug))))
        }
      })
      .catch(() => {
        // brak postepu nie moze blokowac nauki - checkboxy po prostu zaczna od pustych
      })
    return () => {
      cancelled = true
    }
  }, [userId, refreshMastery])

  const isCompleted = useCallback((chapterSlug, lessonSlug) => currentAccount && completed.has(keyOf(chapterSlug, lessonSlug)), [completed, currentAccount])

  const countInChapter = useCallback(
    (chapterSlug) => {
      if (!currentAccount) return 0
      let count = 0
      for (const key of completed) {
        if (key.startsWith(`${chapterSlug}/`)) {
          count += 1
        }
      }
      return count
    },
    [completed, currentAccount],
  )

  const masteryOf = useCallback(
    (chapterSlug, lessonSlug) => (currentAccount && mastery.get(keyOf(chapterSlug, lessonSlug))) || { stars: 0, reviewSuggested: false },
    [mastery, currentAccount],
  )

  const countMasteredInChapter = useCallback(
    (chapterSlug) => {
      if (!currentAccount) return 0
      let count = 0
      for (const [key, m] of mastery) {
        if (key.startsWith(`${chapterSlug}/`) && m.stars > 0) {
          count += 1
        }
      }
      return count
    },
    [mastery, currentAccount],
  )

  const pointsInChapter = useCallback((chapterSlug) => {
    if (!currentAccount) return 0
    let points = 0
    for (const m of mastery.values()) if (m.chapterSlug === chapterSlug) points += m.stars
    return points
  }, [mastery, currentAccount])

  // Optymistycznie: checkbox reaguje natychmiast, a przy bledzie serwera wraca do poprzedniego stanu.
  const setLessonCompleted = useCallback(async (chapterSlug, lessonSlug, value) => {
    const key = keyOf(chapterSlug, lessonSlug)
    setCompleted((prev) => withLesson(prev, key, value))
    try {
      await api.setLessonCompleted(chapterSlug, lessonSlug, value)
      if (account.current !== userId) return true
      await refreshSummary()
      return true
    } catch {
      if (account.current === userId) setCompleted((prev) => withLesson(prev, key, !value))
      return false
    }
  }, [refreshSummary, userId])

  const value = useMemo(
    () => ({ summary, pointsInChapter, isCompleted, countInChapter, setLessonCompleted, masteryOf, countMasteredInChapter, refreshMastery }),
    [summary, pointsInChapter, isCompleted, countInChapter, setLessonCompleted, masteryOf, countMasteredInChapter, refreshMastery],
  )
  return <ProgressContext.Provider value={value}>{children}</ProgressContext.Provider>
}
