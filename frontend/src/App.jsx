import { Link, Route, Routes, useLocation } from 'react-router-dom'
import ChapterListPage from './pages/ChapterListPage'
import LessonListPage from './pages/LessonListPage'
import LessonDetailPage from './pages/LessonDetailPage'
import CriticalTopicsPage from './pages/CriticalTopicsPage'
import ErrorBoundary from './components/ErrorBoundary'
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

function App() {
  return (
    <main className="app">
      <header className="app-header">
        <Link to="/" className="app-header__link">
          <h1>JavaQuest</h1>
        </Link>
        <TrackSwitcher />
        <Link to="/krytyczne" className="app-header__critical-button">
          Krytyczne
        </Link>
      </header>

      <ErrorBoundary>
        <Routes>
          <Route path="/" element={<ChapterListPage track="JAVA" />} />
          <Route path="/js" element={<ChapterListPage track="JAVASCRIPT" />} />
          <Route path="/krytyczne" element={<CriticalTopicsPage />} />
          <Route path="/rozdzial/:chapterSlug" element={<LessonListPage />} />
          <Route path="/rozdzial/:chapterSlug/:lessonSlug" element={<LessonDetailPage />} />
        </Routes>
      </ErrorBoundary>
    </main>
  )
}

export default App
