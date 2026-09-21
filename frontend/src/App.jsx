import { Link, Navigate, Outlet, Route, Routes, useLocation } from 'react-router-dom'
import ChapterListPage from './pages/ChapterListPage'
import LessonListPage from './pages/LessonListPage'
import LessonDetailPage from './pages/LessonDetailPage'
import CriticalTopicsPage from './pages/CriticalTopicsPage'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import ConfirmPage from './pages/ConfirmPage'
import ErrorBoundary from './components/ErrorBoundary'
import { useAuth } from './useAuth'
import './App.css'

function TrackSwitcher() {
  const location = useLocation()
  const isJavaScript = location.pathname.startsWith('/js')

  return (
    <nav className="track-switcher">
      <Link to="/" className={`track-switcher__button ${isJavaScript ? '' : 'active'}`}>
        Java
      </Link>
      <Link to="/js" className={`track-switcher__button ${isJavaScript ? 'active' : ''}`}>
        JavaScript
      </Link>
    </nav>
  )
}

// Bramka: wszystko pod tym elementem wymaga zalogowania. To wygoda UI - realna ochrone danych
// robi backend (caly /api/** zwraca 401 bez tokenu, patrz SecurityConfig.java).
function RequireAuth() {
  const { user, status } = useAuth()
  const location = useLocation()

  if (status === 'loading') {
    return <p className="hint">Ładowanie…</p>
  }
  if (!user) {
    return <Navigate to="/logowanie" replace state={{ from: location.pathname + location.search }} />
  }
  return <Outlet />
}

function App() {
  const { user, logout } = useAuth()

  return (
    <main className="app">
      <header className="app-header">
        {user && (
          <div className="app-header__user">
            <span>
              {user.firstName} {user.lastName}
            </span>
            <button type="button" className="app-header__logout" onClick={logout}>
              Wyloguj
            </button>
          </div>
        )}
        <Link to="/" className="app-header__link">
          <h1>JavaQuest</h1>
        </Link>
        {user && (
          <>
            <TrackSwitcher />
            <Link to="/krytyczne" className="app-header__critical-button">
              Krytyczne
            </Link>
          </>
        )}
      </header>

      <ErrorBoundary>
        <Routes>
          <Route path="/logowanie" element={<LoginPage />} />
          <Route path="/rejestracja" element={<RegisterPage />} />
          <Route path="/potwierdz" element={<ConfirmPage />} />
          <Route element={<RequireAuth />}>
            <Route path="/" element={<ChapterListPage track="JAVA" />} />
            <Route path="/js" element={<ChapterListPage track="JAVASCRIPT" />} />
            <Route path="/krytyczne" element={<CriticalTopicsPage />} />
            <Route path="/rozdzial/:chapterSlug" element={<LessonListPage />} />
            <Route path="/rozdzial/:chapterSlug/:lessonSlug" element={<LessonDetailPage />} />
          </Route>
        </Routes>
      </ErrorBoundary>
    </main>
  )
}

export default App
