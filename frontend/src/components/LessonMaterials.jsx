import { useEffect, useRef, useState } from 'react'
import './LessonMaterials.css'
import {
  getLessonMaterials, addLessonLink, editLessonLink, deleteLessonLink,
  uploadLessonAttachment, deleteLessonAttachment, downloadLessonAttachment,
} from '../api'

function MaterialIcon({ file = false }) {
  return <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" aria-hidden="true">
    {file ? <path d="M14 3H6a1 1 0 0 0-1 1v16a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V8l-5-5Zm0 0v6h5M8 13h8M8 17h5" /> : <><path d="m10 13 4-4M8 15l-1 1a3.5 3.5 0 0 1-5-5l4-4a3.5 3.5 0 0 1 5 0M13 9l1-1a3.5 3.5 0 0 1 5 5l-4 4a3.5 3.5 0 0 1-5 0" /></>}
  </svg>
}

function linkHost(url) {
  try { return new URL(url).hostname.replace(/^www\./, '') } catch { return '' }
}

function MaterialDialog({ action, busy, error, onClose, onConfirm }) {
  const ref = useRef(null)
  const edit = action.type === 'edit'
  const [title, setTitle] = useState(action.item.title)
  const [url, setUrl] = useState(action.item.url)
  useEffect(() => {
    const dialog = ref.current
    dialog.showModal()
    return () => dialog.close()
  }, [])
  return (
    <dialog ref={ref} className="materials-dialog" aria-labelledby="material-dialog-title"
      onCancel={(event) => { event.preventDefault(); if (!busy) onClose() }}>
      <form onSubmit={(event) => { event.preventDefault(); onConfirm({ title, url }) }}>
        <h3 id="material-dialog-title">{edit ? 'Edytuj link' : 'Usunąć materiał?'}</h3>
        {edit ? <>
          <label>Tytuł<input autoFocus required maxLength={200} value={title} onChange={(event) => setTitle(event.target.value)} disabled={busy} /></label>
          <label>Adres linku<input required type="url" maxLength={2048} value={url} onChange={(event) => setUrl(event.target.value)} disabled={busy} /></label>
          <p className="hint">Zapisz, aby potwierdzić zmiany.</p>
        </> : <p>Czy na pewno chcesz usunąć „{action.item.title}”? Tej operacji nie można cofnąć.</p>}
        {error && <p role="alert" className="error">{error}</p>}
        <div className="materials-actions">
          <button type="button" onClick={onClose} disabled={busy} autoFocus={!edit}>Anuluj</button>
          <button type="submit" disabled={busy} className={edit ? '' : 'material-delete'}>
            {busy ? 'Zapisywanie…' : edit ? 'Zapisz zmiany' : 'Usuń'}
          </button>
        </div>
      </form>
    </dialog>
  )
}

export default function LessonMaterials({ chapter, lesson }) {
  const [expanded, setExpanded] = useState(false)
  const drawer = useRef(null)
  useEffect(() => {
    if (!expanded) { drawer.current.close(); return undefined }
    const previousOverflow = document.body.style.overflow
    document.body.style.overflow = 'hidden'
    drawer.current.showModal()
    return () => { document.body.style.overflow = previousOverflow }
  }, [expanded])
  const [items, setItems] = useState([])
  const [loading, setLoading] = useState(true)
  const [busy, setBusy] = useState(false)
  const [error, setError] = useState('')
  const [modalError, setModalError] = useState('')
  const [action, setAction] = useState(null)
  const [title, setTitle] = useState('')
  const [url, setUrl] = useState('')
  const [file, setFile] = useState(null)
  const fileInput = useRef(null)
  const linkComposer = useRef(null)
  const mounted = useRef(true)
  useEffect(() => {
    mounted.current = true
    let cancelled = false
    getLessonMaterials(chapter, lesson).then((data) => {
      if (!cancelled) { setItems(data); setError('') }
    }).catch((err) => { if (!cancelled) setError(err.message) })
      .finally(() => { if (!cancelled) setLoading(false) })
    return () => { cancelled = true; mounted.current = false }
  }, [chapter, lesson])

  async function run(work, modal = false) {
    if (busy) return
    setBusy(true)
    setError('')
    setModalError('')
    try {
      await work()
    } catch (err) {
      if (mounted.current) { if (modal) setModalError(err.message); else setError(err.message) }
    } finally {
      if (mounted.current) setBusy(false)
    }
  }
  const attachments = items.filter((item) => item.kind === 'ATTACHMENT')
  const links = items.filter((item) => item.kind === 'LINK')
  function open(type, item) { setModalError(''); setAction({ type, item }) }
  async function confirm(body) {
    await run(async () => {
      const { item, type } = action
      if (type === 'edit') {
        const updated = await editLessonLink(chapter, lesson, item.id, body)
        if (mounted.current) setItems((current) => current.map((entry) => entry.id === item.id ? updated : entry))
      } else {
        if (item.kind === 'LINK') await deleteLessonLink(chapter, lesson, item.id)
        else await deleteLessonAttachment(chapter, lesson, item.id)
        if (mounted.current) setItems((current) => current.filter((entry) => entry.id !== item.id))
      }
      if (mounted.current) setAction(null)
    }, true)
  }
  async function download(item) {
    await run(async () => {
      const blob = await downloadLessonAttachment(item.url)
      if (!mounted.current) return
      const objectUrl = URL.createObjectURL(blob)
      const anchor = document.createElement('a')
      anchor.href = objectUrl
      anchor.download = item.title
      document.body.append(anchor)
      anchor.click()
      anchor.remove()
      setTimeout(() => URL.revokeObjectURL(objectUrl), 1000)
    })
  }
  return (
    <>
    <button type="button" className="materials-trigger" aria-haspopup="dialog" aria-expanded={expanded}
      aria-controls="materials-drawer" onClick={() => setExpanded(true)}>Moje materiały <span aria-hidden="true">↗</span></button>
    <dialog ref={drawer} id="materials-drawer" className="materials-drawer" aria-labelledby="my-materials-title"
      onClose={() => setExpanded(false)} onCancel={(event) => { if (busy) event.preventDefault() }}>
    <section className="lesson-materials">
      <header className="materials-header">
        <div><span className="materials-eyebrow">Biblioteka lekcji</span><h3 id="my-materials-title">Moje materiały</h3></div>
        <button type="button" className="materials-close" onClick={() => setExpanded(false)} disabled={busy} aria-label="Zamknij moje materiały">×</button>
      </header>
      <p className="materials-private">Prywatne · widoczne tylko dla Ciebie</p>
      {loading ? <p className="hint">Wczytywanie materiałów…</p> : <>
        <details className="materials-section" open>
          <summary><span className="materials-section__icon"><MaterialIcon file /></span><span>Załączniki</span><span className="materials-count">{attachments.length}</span></summary>
          <div className="materials-section__content">
          <ul className="materials-list">
            {attachments.map((item) => <li key={item.id}>
              <div className="material-entry"><MaterialIcon file /><button type="button" className="material-download" onClick={() => download(item)} disabled={busy}>{item.title}</button></div>
              <button type="button" onClick={() => open('delete', item)} disabled={busy} aria-label={'Usuń załącznik ' + item.title}>Usuń</button>
            </li>)}
          </ul>
          {!attachments.length && <p className="materials-empty">Zapisz tutaj notatki i pliki do tej lekcji.</p>}
          <form className="materials-form" onSubmit={(event) => {
            event.preventDefault()
            if (!file) return
            run(async () => {
              const item = await uploadLessonAttachment(chapter, lesson, file)
              if (!mounted.current) return
              setItems((current) => [...current, item])
              setFile(null)
              fileInput.current.value = ''
            })
          }}>
            <label className={`materials-file ${file ? 'materials-file--selected' : ''}`}>
              <input ref={fileInput} type="file" required disabled={busy} aria-label="Wybierz załącznik" onChange={(event) => setFile(event.target.files[0] ?? null)} />
              <span className="materials-file__icon"><MaterialIcon file /></span>
              <span><strong>{file ? file.name : 'Wybierz plik'}</strong><small>{file ? 'Kliknij, aby zmienić plik' : 'Notatki, dokumenty i inne załączniki'}</small></span>
              <span aria-hidden="true">＋</span>
            </label>
            {file && <button className="materials-primary" type="submit" disabled={busy}>{busy ? 'Przesyłanie…' : 'Zapisz załącznik'}</button>}
          </form>
          </div>
        </details>
        <details className="materials-section" open>
          <summary><span className="materials-section__icon"><MaterialIcon /></span><span>Moje linki</span><span className="materials-count">{links.length}</span></summary>
          <div className="materials-section__content">
          <ul className="materials-list">
            {links.map((item) => <li key={item.id}>
              <div className="material-entry"><span className="material-entry__icon"><MaterialIcon /></span><a href={item.url} target="_blank" rel="noopener noreferrer"><strong>{item.title}<span aria-hidden="true"> ↗</span></strong><small>{linkHost(item.url)}</small></a></div>
              <div className="materials-actions">
                <button type="button" onClick={() => open('edit', item)} disabled={busy} aria-label={'Edytuj link ' + item.title}>Edytuj</button>
                <button type="button" className="materials-remove" onClick={() => open('delete', item)} disabled={busy} aria-label={'Usuń link ' + item.title}>Usuń</button>
              </div>
            </li>)}
          </ul>
          {!links.length && <p className="materials-empty">Zachowaj artykuły i filmy, do których chcesz wrócić.</p>}
          <details ref={linkComposer} className="materials-composer">
          <summary>＋ Dodaj link</summary>
          <form className="materials-form" onSubmit={(event) => {
            event.preventDefault()
            run(async () => {
              const item = await addLessonLink(chapter, lesson, { title, url })
              if (!mounted.current) return
              setItems((current) => [...current, item]); setTitle(''); setUrl('')
              linkComposer.current.open = false
              linkComposer.current.querySelector('summary').focus()
            })
          }}>
            <label>Tytuł<input required maxLength={200} placeholder="Np. Wyjaśnienie z przykładami" value={title} onChange={(event) => setTitle(event.target.value)} disabled={busy} /></label>
            <label>Adres artykułu lub filmu<input required type="url" maxLength={2048} placeholder="https://…" value={url} onChange={(event) => setUrl(event.target.value)} disabled={busy} /></label>
            <button className="materials-primary" type="submit" disabled={busy}>{busy ? 'Zapisywanie…' : 'Zapisz link'}</button>
          </form>
          </details>
          </div>
        </details>
      </>}
      {error && <p role="alert" className="error">{error}</p>}
      {action && <MaterialDialog action={action} busy={busy} error={modalError} onClose={() => setAction(null)} onConfirm={confirm} />}
    </section>
    </dialog>
    </>
  )
}

