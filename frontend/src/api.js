// Cienka warstwa nad fetch dla API platformy JavaQuest. Wzgledne sciezki "/api/..."
// dzialaja bez zmian zarowno w dev (proxy Vite -> localhost:8080, patrz vite.config.js)
// jak i w buildzie produkcyjnym (ten sam origin, bo frontend jest serwowany przez ten
// sam Spring Boot, ktory wystawia API).

async function getJson(path) {
  const response = await fetch(path)
  if (!response.ok) {
    throw new Error(`${path} -> HTTP ${response.status}`)
  }
  return response.json()
}

export function getChapters() {
  return getJson('/api/chapters')
}

export function getLessons(chapterSlug) {
  return getJson(`/api/chapters/${encodeURIComponent(chapterSlug)}/lessons`)
}

function lessonPath(chapterSlug, lessonSlug, suffix) {
  return `/api/chapters/${encodeURIComponent(chapterSlug)}/lessons/${encodeURIComponent(lessonSlug)}/${suffix}`
}

export function getTheory(chapterSlug, lessonSlug) {
  return getJson(lessonPath(chapterSlug, lessonSlug, 'theory'))
}

export function getExercises(chapterSlug, lessonSlug) {
  return getJson(lessonPath(chapterSlug, lessonSlug, 'exercises'))
}

export function getQuiz(chapterSlug, lessonSlug) {
  return getJson(lessonPath(chapterSlug, lessonSlug, 'quiz'))
}
