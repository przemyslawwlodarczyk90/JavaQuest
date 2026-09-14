import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { getCriticalTopics } from '../api'

// Kolejnosc i polskie etykiety kategorii - JSON trzyma tylko klucz (patrz
// src/main/resources/platform/critical-topics.json), zeby nie duplikowac tekstu
// w dwoch miejscach.
const CATEGORY_LABELS = [
  ['java', 'Core Java'],
  ['spring', 'Spring (Core / Boot / MVC / Data)'],
  ['database', 'Bazy danych i SQL'],
  ['testing', 'Testowanie'],
  ['tools', 'Narzędzia i warsztat'],
  ['security', 'Bezpieczeństwo'],
  ['architecture', 'Architektura i dobre praktyki'],
  ['messaging', 'Messaging i mikroserwisy'],
  ['observability', 'Observability'],
  ['cloud', 'Cloud i infrastruktura'],
  ['algorithms', 'Algorytmy i struktury danych'],
  ['other', 'Pozostałe biblioteki i narzędzia'],
]

const PRIORITY_META = {
  critical: { label: 'Krytyczny', rank: 0, className: 'priority-badge--critical' },
  'very-important': { label: 'Bardzo ważny', rank: 1, className: 'priority-badge--very-important' },
  important: { label: 'Ważny', rank: 2, className: 'priority-badge--important' },
}

const MASTERED_STORAGE_KEY = 'javaquest.krytyczne.opanowane'

function loadMastered() {
  try {
    const raw = localStorage.getItem(MASTERED_STORAGE_KEY)
    return raw ? new Set(JSON.parse(raw)) : new Set()
  } catch {
    return new Set()
  }
}

function saveMastered(set) {
  try {
    localStorage.setItem(MASTERED_STORAGE_KEY, JSON.stringify([...set]))
  } catch {
    // localStorage niedostepny (np. prywatne okno) - checkbox po prostu nie przetrwa odswiezenia
  }
}

function TopicRow({ topic, mastered, onToggleMastered }) {
  const priority = PRIORITY_META[topic.priority] ?? PRIORITY_META.important
  const isCovered = Boolean(topic.chapterSlug)
  const href = isCovered
    ? topic.lessonSlug
      ? `/rozdzial/${topic.chapterSlug}/${topic.lessonSlug}`
      : `/rozdzial/${topic.chapterSlug}`
    : null

  return (
    <li className={`topic-row${mastered ? ' topic-row--mastered' : ''}`}>
      <label className="topic-row__checkbox">
        <input
          type="checkbox"
          checked={mastered}
          onChange={() => onToggleMastered(topic.id)}
          aria-label={`Oznacz "${topic.title}" jako opanowany`}
        />
      </label>

      <span className={`priority-badge ${priority.className}`}>{priority.label}</span>

      <div className="topic-row__body">
        {isCovered ? (
          <Link to={href} className="topic-row__title topic-row__title--linked">
            {topic.title}
          </Link>
        ) : (
          <span className="topic-row__title">{topic.title}</span>
        )}
        {topic.note && <p className="topic-row__note">{topic.note}</p>}
      </div>

      <span className={`coverage-badge ${isCovered ? 'coverage-badge--covered' : 'coverage-badge--gap'}`}>
        {isCovered ? 'w kursie' : 'do opracowania'}
      </span>
    </li>
  )
}

export default function CriticalTopicsPage() {
  const [status, setStatus] = useState('loading')
  const [topics, setTopics] = useState([])
  const [error, setError] = useState('')
  const [mastered, setMastered] = useState(() => loadMastered())

  useEffect(() => {
    getCriticalTopics()
      .then((data) => {
        setTopics(data)
        setStatus('ok')
      })
      .catch((err) => {
        setError(err.message)
        setStatus('error')
      })
  }, [])

  function toggleMastered(id) {
    setMastered((prev) => {
      const next = new Set(prev)
      if (next.has(id)) {
        next.delete(id)
      } else {
        next.add(id)
      }
      saveMastered(next)
      return next
    })
  }

  const grouped = useMemo(() => {
    const byCategory = new Map()
    for (const topic of topics) {
      const list = byCategory.get(topic.category) ?? []
      list.push(topic)
      byCategory.set(topic.category, list)
    }
    for (const list of byCategory.values()) {
      list.sort((a, b) => {
        const rankA = PRIORITY_META[a.priority]?.rank ?? 99
        const rankB = PRIORITY_META[b.priority]?.rank ?? 99
        return rankA - rankB
      })
    }
    return byCategory
  }, [topics])

  const coveredCount = topics.filter((t) => t.chapterSlug).length
  const masteredCount = topics.filter((t) => mastered.has(t.id)).length

  if (status === 'loading') {
    return <p className="hint">Wczytywanie tematów krytycznych...</p>
  }

  if (status === 'error') {
    return <p className="error">Nie udało się wczytać tematów krytycznych: {error}</p>
  }

  return (
    <div className="critical-topics">
      <Link to="/" className="back-link">
        &larr; Wszystkie rozdziały
      </Link>

      <h2>Tematy krytyczne</h2>
      <p className="critical-topics__intro">
        Lista tematów uznanych za krytyczne lub bardzo ważne przy rekrutacji na Java Developera,
        opracowana na podstawie raportu rynkowego. Temat z linkiem jest już opracowany w kursie -
        kliknięcie przenosi do właściwej lekcji. Temat oznaczony „do opracowania” nie ma jeszcze
        odpowiednika w kursie.
      </p>

      <div className="critical-topics__summary">
        <span>
          W kursie: <strong>{coveredCount}</strong> / {topics.length}
        </span>
        <span>
          Opanowane przez Ciebie: <strong>{masteredCount}</strong> / {topics.length}
        </span>
      </div>

      {CATEGORY_LABELS.filter(([key]) => grouped.has(key)).map(([key, label]) => (
        <section key={key} className="topic-category">
          <h3 className="topic-category__title">{label}</h3>
          <ul className="topic-list">
            {grouped.get(key).map((topic) => (
              <TopicRow
                key={topic.id}
                topic={topic}
                mastered={mastered.has(topic.id)}
                onToggleMastered={toggleMastered}
              />
            ))}
          </ul>
        </section>
      ))}
    </div>
  )
}
