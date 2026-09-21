// Cienka warstwa nad fetch dla API platformy JavaQuest. Wzgledne sciezki "/api/..."
// dzialaja bez zmian zarowno w dev (proxy Vite -> localhost:8082, patrz vite.config.js)
// jak i w buildzie produkcyjnym (ten sam origin, bo frontend jest serwowany przez ten
// sam Spring Boot, ktory wystawia API).
//
// Caly /api/** (poza /api/auth/register|login|confirm) wymaga tokenu JWT - dolaczamy go
// automatycznie do kazdego zapytania.

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

async function request(path, { method = 'GET', body } = {}) {
  const headers = {}
  const token = getStoredToken()
  if (token) {
    headers.Authorization = `Bearer ${token}`
  }
  if (body !== undefined) {
    headers['Content-Type'] = 'application/json'
  }

  const response = await fetch(path, {
    method,
    headers,
    body: body === undefined ? undefined : JSON.stringify(body),
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
  return response.json()
}

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

export function getQuiz(chapterSlug, lessonSlug) {
  return getJson(lessonPath(chapterSlug, lessonSlug, 'quiz'))
}

export function getCriticalTopics() {
  return getJson('/api/critical-topics')
}
