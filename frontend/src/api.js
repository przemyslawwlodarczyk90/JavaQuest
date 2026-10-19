// Cienka warstwa nad fetch dla API platformy JavaQuest. Wzgledne sciezki "/api/..."
// dzialaja bez zmian zarowno w dev (proxy Vite -> localhost:8082, patrz vite.config.js)
// jak i w buildzie produkcyjnym (ten sam origin, bo frontend jest serwowany przez ten
// sam Spring Boot, ktory wystawia API).
//
// Caly /api/** (poza /api/auth/register|login|confirm|forgot-password|reset-password) wymaga
// tokenu JWT - dolaczamy go automatycznie do kazdego zapytania.

const TOKEN_KEY = 'javaquest.token'

export function getStoredToken() {
  try {
    return localStorage.getItem(TOKEN_KEY)
  } catch {
    return null
  }
}

export function setStoredToken(token) {
  try {
    if (token) {
      localStorage.setItem(TOKEN_KEY, token)
    } else {
      localStorage.removeItem(TOKEN_KEY)
    }
  } catch {
    // localStorage niedostepny (np. prywatne okno) - sesja po prostu nie przetrwa odswiezenia
  }
}

let unauthorizedHandler = null

// AuthContext rejestruje tu funkcje wylogowujaca - wywolywana, gdy serwer odrzuci token
// (wygasl, uzytkownik usuniety, zmieniony sekret), zeby UI wrocilo na ekran logowania.
export function onUnauthorized(handler) {
  unauthorizedHandler = handler
}

export class ApiError extends Error {
  constructor(message, status, fieldErrors = {}) {
    super(message)
    this.status = status
    this.fieldErrors = fieldErrors
  }
}

async function request(path, { method = 'GET', body, responseType = 'json' } = {}) {
  const headers = {}
  const token = getStoredToken()
  if (token) {
    headers.Authorization = `Bearer ${token}`
  }
  const multipart = body instanceof FormData
  if (body !== undefined && !multipart) {
    headers['Content-Type'] = 'application/json'
  }

  const response = await fetch(path, {
    method,
    headers,
    body: body === undefined ? undefined : multipart ? body : JSON.stringify(body),
  })

  if (!response.ok) {
    let data = null
    try {
      data = await response.json()
    } catch {
      // odpowiedz bez JSON-a (np. gole 401 z filtra bezpieczenstwa)
    }
    // 401 z tokenem = sesja niewazna. /api/auth/login sam zwraca 401 za zle haslo - to nie wygasla sesja.
    if (response.status === 401 && token && path !== '/api/auth/login' && unauthorizedHandler) {
      unauthorizedHandler()
    }
    throw new ApiError(data?.message ?? `${path} -> HTTP ${response.status}`, response.status, data?.fieldErrors ?? {})
  }
  if (response.status === 204) return null
  return responseType === 'blob' ? response.blob() : response.json()
}

export const getLessonMaterials = (chapter, lesson) => getJson(lessonPath(chapter, lesson, 'materials'))
export const addLessonLink = (chapter, lesson, body) => request(lessonPath(chapter, lesson, 'links'), { method: 'POST', body })
export const editLessonLink = (chapter, lesson, id, body) => request(lessonPath(chapter, lesson, `links/${id}`), { method: 'PUT', body })
export const deleteLessonLink = (chapter, lesson, id) => request(lessonPath(chapter, lesson, `links/${id}`), { method: 'DELETE' })
export const deleteLessonAttachment = (chapter, lesson, id) => request(lessonPath(chapter, lesson, `attachments/${id}`), { method: 'DELETE' })
export function uploadLessonAttachment(chapter, lesson, file) {
  const body = new FormData()
  body.append('file', file)
  return request(lessonPath(chapter, lesson, 'attachments'), { method: 'POST', body })
}
export const downloadLessonAttachment = (url) => request(url, { responseType: 'blob' })

const getJson = (path) => request(path)

export function login(email, password) {
  return request('/api/auth/login', { method: 'POST', body: { email, password } })
}

export function register(firstName, lastName, email, password) {
  return request('/api/auth/register', { method: 'POST', body: { firstName, lastName, email, password } })
}

export function confirmAccount(token) {
  return request(`/api/auth/confirm?token=${encodeURIComponent(token)}`)
}

export function forgotPassword(email) {
  return request('/api/auth/forgot-password', { method: 'POST', body: { email } })
}

export function resetPassword(token, newPassword) {
  return request('/api/auth/reset-password', { method: 'POST', body: { token, newPassword } })
}

export function getMe() {
  return getJson('/api/auth/me')
}

export function getChapters(track = 'JAVA') {
  return getJson(`/api/chapters?track=${encodeURIComponent(track)}`)
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

// Postep uzytkownika (checkbox "lekcja zrobiona") - prywatny, per konto z tokenu.
export function getProgress() {
  return getJson('/api/progress')
}

export function setLessonCompleted(chapterSlug, lessonSlug, completed) {
  return request(lessonPath(chapterSlug, lessonSlug, 'progress'), { method: 'PUT', body: { completed } })
}

// Mastery (gwiazdki 0-3) liczy serwer z historii zaliczonych quizow. Odpowiedz NIE zawiera zadnych dat -
// tylko {chapterSlug, lessonSlug, stars, reviewSuggested}. Bez "track" - wszystkie tory naraz.
export function getMastery(track) {
  return getJson(track ? `/api/mastery?track=${encodeURIComponent(track)}` : '/api/mastery')
}

export const getLearningSummary = () => getJson('/api/mastery/summary')

export function getMasteryReviews(track) {
  return getJson(track ? `/api/mastery/reviews?track=${encodeURIComponent(track)}` : '/api/mastery/reviews')
}

// Quiz jest losowany i oceniany na serwerze: status -> start podejscia -> odpowiedzi pytanie po pytaniu.
export function getQuizStatus(chapterSlug, lessonSlug) {
  return getJson(lessonPath(chapterSlug, lessonSlug, 'quiz'))
}

export function startQuizAttempt(chapterSlug, lessonSlug) {
  return request(lessonPath(chapterSlug, lessonSlug, 'quiz/attempts'), { method: 'POST' })
}

export function answerQuizQuestion(chapterSlug, lessonSlug, attemptId, position, option) {
  return request(lessonPath(chapterSlug, lessonSlug, `quiz/attempts/${attemptId}/answers`), {
    method: 'POST',
    body: { position, option },
  })
}

export function getCriticalTopics() {
  return getJson('/api/critical-topics')
}
