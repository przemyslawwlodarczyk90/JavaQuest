import { useEffect, useMemo, useState } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { getTableOfContents } from '../api'
import { useAuth } from '../useAuth'
import { useProgress } from '../useProgress'
import './TableOfContentsPage.css'

export const TOC_TRACKS = [
  { track: 'JAVA', label: 'Java' },
  { track: 'JAVASCRIPT', label: 'JavaScript' },
  { track: 'REACT', label: 'React' },
  { track: 'LINUX', label: 'Linux' },
  { track: 'OCP', label: 'OCP' },
  { track: 'NETWORKING', label: 'Sieci i Cyberbezpieczeństwo' },
]

// Tor z adresu strony - przycisk "Spis treści" otwiera spis toru, w którym aktualnie jesteś.
export function trackOfPath(pathname) {
  if (pathname.includes('_ns_') || pathname.startsWith('/sieci')) return 'NETWORKING'
  if (pathname.includes('_ocp_') || pathname.startsWith('/ocp')) return 'OCP'
  if (pathname.includes('_lx_') || pathname.startsWith('/linux')) return 'LINUX'
  if (pathname.includes('_react_') || pathname.startsWith('/react')) return 'REACT'
  if (pathname.includes('_js_') || pathname.startsWith('/js')) return 'JAVASCRIPT'
  return 'JAVA'
}

// Polska odmiana: 1 lekcja, 2-4 lekcje (ale 12-14 lekcji), 5+ lekcji.
function plural(count, one, few, many) {
  if (count === 1) return `${count} ${one}`
  const lastTwo = count % 100
  const last = count % 10
  return `${count} ${last >= 2 && last <= 4 && (lastTwo < 12 || lastTwo > 14) ? few : many}`
}

const normalize = (text) => text.toLocaleLowerCase('pl').normalize('NFD').replace(/[̀-ͯ]/g, '').replace(/ł/g, 'l')

export default function TableOfContentsPage() {
  const { user } = useAuth()
  const { isCompleted } = useProgress()
  const [searchParams, setSearchParams] = useSearchParams()
  const track = TOC_TRACKS.some((t) => t.track === searchParams.get('tor')) ? searchParams.get('tor') : 'JAVA'
  const [state, setState] = useState({ track: null, status: 'loading', chapters: [], error: '' })
  const [query, setQuery] = useState('')
  const [open, setOpen] = useState(() => new Set())

  useEffect(() => {
    let cancelled = false
    getTableOfContents(track)
      .then((chapters) => !cancelled && setState({ track, status: 'ok', chapters, error: '' }))
      .catch((err) => !cancelled && setState({ track, status: 'error', chapters: [], error: err.message }))
    return () => { cancelled = true }
  }, [track])

  const chapters = useMemo(() => (state.track === track ? state.chapters : []), [state, track])
  const needle = normalize(query.trim())

  const visible = useMemo(() => {
    if (!needle) return chapters.map((chapter, index) => ({ ...chapter, number: index + 1, matches: chapter.lessons }))
    return chapters
      .map((chapter, index) => {
        const chapterHit = normalize(chapter.title).includes(needle)
        const matches = chapter.lessons.filter((lesson) => chapterHit || normalize(lesson.title).includes(needle))
        return { ...chapter, number: index + 1, matches }
      })
      .filter((chapter) => chapter.matches.length > 0)
  }, [chapters, needle])

  const lessonTotal = chapters.reduce((sum, chapter) => sum + chapter.lessons.length, 0)
  const doneTotal = user
    ? chapters.reduce((sum, chapter) => sum + chapter.lessons.filter((lesson) => isCompleted(chapter.slug, lesson.slug)).length, 0)
    : 0

  function selectTrack(next) {
    setSearchParams({ tor: next })
    setOpen(new Set())
    setQuery('')
  }

  function toggle(slug) {
    setOpen((prev) => {
      const next = new Set(prev)
      if (next.has(slug)) next.delete(slug)
      else next.add(slug)
      return next
    })
  }

  const allOpen = visible.length > 0 && visible.every((chapter) => open.has(chapter.slug))

  return (
    <div className="toc">
      <h2 className="toc__title">Spis treści</h2>

      <div className="toc__tracks" role="tablist" aria-label="Tor kursu">
        {TOC_TRACKS.map((t) => (
          <button
            key={t.track}
            type="button"
            role="tab"
            aria-selected={t.track === track}
            className={`toc__track ${t.track === track ? 'toc__track--active' : ''}`}
            onClick={() => selectTrack(t.track)}
          >
            {t.label}
          </button>
        ))}
      </div>

      {state.track !== track || state.status === 'loading' ? (
        <p className="hint">Wczytywanie spisu treści...</p>
      ) : state.status === 'error' ? (
        <p className="error">Nie udało się wczytać spisu treści: {state.error}</p>
      ) : (
        <>
          <div className="toc__toolbar">
            <input
              type="search"
              className="toc__search"
              placeholder="Szukaj rozdziału lub lekcji…"
              value={query}
              onChange={(event) => setQuery(event.target.value)}
              aria-label="Szukaj w spisie treści"
            />
            {!needle && (
              <button
                type="button"
                className="toc__toggle-all"
                onClick={() => setOpen(allOpen ? new Set() : new Set(visible.map((chapter) => chapter.slug)))}
              >
                {allOpen ? 'Zwiń wszystkie' : 'Rozwiń wszystkie'}
              </button>
            )}
          </div>

          <p className="toc__summary">
            {plural(chapters.length, 'rozdział', 'rozdziały', 'rozdziałów')} · {plural(lessonTotal, 'lekcja', 'lekcje', 'lekcji')}
            {user && <> · zrobione: {doneTotal} / {lessonTotal}</>}
            {needle && <> · pasujące rozdziały: {visible.length}</>}
          </p>

          {visible.length === 0 && <p className="hint">Nic nie pasuje do „{query}”.</p>}

          <ol className="toc__chapters">
            {visible.map((chapter) => {
              const expanded = Boolean(needle) || open.has(chapter.slug)
              const done = user ? chapter.lessons.filter((lesson) => isCompleted(chapter.slug, lesson.slug)).length : 0
              return (
                <li key={chapter.slug} className={`toc-chapter ${expanded ? 'toc-chapter--open' : ''}`}>
                  <div className="toc-chapter__head">
                    <button
                      type="button"
                      className="toc-chapter__toggle"
                      onClick={() => toggle(chapter.slug)}
                      aria-expanded={expanded}
                      aria-label={`${expanded ? 'Zwiń' : 'Rozwiń'} rozdział ${chapter.title}`}
                      disabled={Boolean(needle)}
                    >
                      {expanded ? '−' : '+'}
                    </button>
                    <span className="toc-chapter__number">{chapter.number}.</span>
                    <Link to={`/rozdzial/${chapter.slug}`} className="toc-chapter__title">
                      {chapter.title}
                    </Link>
                    <span className="toc-chapter__count">
                      {user ? `${done} / ${chapter.lessons.length}` : plural(chapter.lessons.length, 'lekcja', 'lekcje', 'lekcji')}
                    </span>
                  </div>
                  {expanded && (
                    <ol className="toc-chapter__lessons">
                      {chapter.matches.map((lesson) => {
                        const index = chapter.lessons.indexOf(lesson) + 1
                        const completed = user && isCompleted(chapter.slug, lesson.slug)
                        return (
                          <li key={lesson.slug} className={`toc-lesson ${completed ? 'toc-lesson--done' : ''}`}>
                            <span className="toc-lesson__number">{chapter.number}.{index}</span>
                            <Link to={`/rozdzial/${chapter.slug}/${lesson.slug}`} className="toc-lesson__title">
                              {lesson.title}
                            </Link>
                            {completed && <span className="toc-lesson__done" title="Zrobiona">✓</span>}
                          </li>
                        )
                      })}
                    </ol>
                  )}
                </li>
              )
            })}
          </ol>
        </>
      )}
    </div>
  )
}
