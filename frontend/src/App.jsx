import { Route, Routes } from 'react-router-dom'
import ChapterListPage from './pages/ChapterListPage'
import LessonListPage from './pages/LessonListPage'
import './App.css'

function App() {
  return (
    <main className="app">
      <header className="app-header">
        <h1>JavaQuest</h1>
        <p className="subtitle">Platforma edukacyjna oparta na kursie Java.</p>
      </header>

      <Routes>
        <Route path="/" element={<ChapterListPage />} />
        <Route path="/rozdzial/:chapterSlug" element={<LessonListPage />} />
      </Routes>
    </main>
  )
}

export default App
