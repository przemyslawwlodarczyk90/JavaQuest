import { Link, Route, Routes } from 'react-router-dom'
import ChapterListPage from './pages/ChapterListPage'
import LessonListPage from './pages/LessonListPage'
import LessonDetailPage from './pages/LessonDetailPage'
import CriticalTopicsPage from './pages/CriticalTopicsPage'
import ErrorBoundary from './components/ErrorBoundary'
import './App.css'

function App() {
  return (
    <main className="app">
      <header className="app-header">
        <Link to="/" className="app-header__link">
          <h1>JavaQuest</h1>
        </Link>
        <p className="subtitle">Platforma edukacyjna oparta na kursie Java.</p>
        <Link to="/krytyczne" className="app-header__critical-button">
          Krytyczne
        </Link>
      </header>

      <ErrorBoundary>
        <Routes>
          <Route path="/" element={<ChapterListPage />} />
          <Route path="/krytyczne" element={<CriticalTopicsPage />} />
          <Route path="/rozdzial/:chapterSlug" element={<LessonListPage />} />
          <Route path="/rozdzial/:chapterSlug/:lessonSlug" element={<LessonDetailPage />} />
        </Routes>
      </ErrorBoundary>
    </main>
  )
}

export default App
