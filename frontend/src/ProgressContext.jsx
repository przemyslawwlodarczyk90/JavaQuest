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

  useEffect(() => {
    let cancelled = false
    if (userId === null) {
      // wylogowanie/zmiana konta: nie pokazuj postepu poprzedniej osoby
      setCompleted(new Set())
      return undefined
    }
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
  }, [userId])

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
    () => ({ isCompleted, countInChapter, setLessonCompleted }),
    [isCompleted, countInChapter, setLessonCompleted],
  )
  return <ProgressContext.Provider value={value}>{children}</ProgressContext.Provider>
}
