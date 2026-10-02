import { useCallback, useEffect, useMemo, useState } from 'react'
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
  const [completed, setCompleted] = useState(() => new Set())
  // Mastery (gwiazdki) - osobna informacja od checkboxa: checkbox to deklaracja "przerobilem",
  // gwiazdki to potwierdzona quizem, starzejaca sie wiedza (liczona na serwerze, bez dat).
  const [mastery, setMastery] = useState(() => new Map())

  const refreshMastery = useCallback(() => {
    return api
      .getMastery()
      .then((list) => setMastery(new Map(list.map((m) => [keyOf(m.chapterSlug, m.lessonSlug), m]))))
      .catch(() => {
        // brak gwiazdek nie moze blokowac nauki
      })
  }, [])

  useEffect(() => {
    let cancelled = false
    if (userId === null) {
      // wylogowanie/zmiana konta: nie pokazuj postepu poprzedniej osoby
      setCompleted(new Set())
      setMastery(new Map())
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

  const isCompleted = useCallback((chapterSlug, lessonSlug) => completed.has(keyOf(chapterSlug, lessonSlug)), [completed])

  const countInChapter = useCallback(
    (chapterSlug) => {
      let count = 0
      for (const key of completed) {
        if (key.startsWith(`${chapterSlug}/`)) {
          count += 1
        }
      }
      return count
    },
    [completed],
  )

  const masteryOf = useCallback(
    (chapterSlug, lessonSlug) => mastery.get(keyOf(chapterSlug, lessonSlug)) ?? { stars: 0, reviewSuggested: false },
    [mastery],
  )

  const countMasteredInChapter = useCallback(
    (chapterSlug) => {
      let count = 0
      for (const [key, m] of mastery) {
        if (key.startsWith(`${chapterSlug}/`) && m.stars > 0) {
          count += 1
        }
      }
      return count
    },
    [mastery],
  )

  // Optymistycznie: checkbox reaguje natychmiast, a przy bledzie serwera wraca do poprzedniego stanu.
  const setLessonCompleted = useCallback(async (chapterSlug, lessonSlug, value) => {
    const key = keyOf(chapterSlug, lessonSlug)
    setCompleted((prev) => withLesson(prev, key, value))
    try {
      await api.setLessonCompleted(chapterSlug, lessonSlug, value)
      return true
    } catch {
      setCompleted((prev) => withLesson(prev, key, !value))
      return false
    }
  }, [])

  const value = useMemo(
    () => ({ isCompleted, countInChapter, setLessonCompleted, masteryOf, countMasteredInChapter, refreshMastery }),
    [isCompleted, countInChapter, setLessonCompleted, masteryOf, countMasteredInChapter, refreshMastery],
  )
  return <ProgressContext.Provider value={value}>{children}</ProgressContext.Provider>
}
