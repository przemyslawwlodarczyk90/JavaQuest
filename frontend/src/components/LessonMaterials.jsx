import { useEffect, useRef, useState } from 'react'
import {
  getLessonMaterials, addLessonLink, editLessonLink, deleteLessonLink,
  uploadLessonAttachment, deleteLessonAttachment, downloadLessonAttachment,
} from '../api'

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
      <button type="button" className="materials-close" onClick={() => setExpanded(false)} disabled={busy} aria-label="Zamknij moje materiały">Zamknij ×</button>
      <h3 id="my-materials-title">Moje materiały</h3>
      <p className="hint">Twoje załączniki i linki do tej lekcji widzisz tylko Ty.</p>
      {loading ? <p className="hint">Wczytywanie materiałów…</p> : <>
        <details>
          <summary>Załączniki ({attachments.length})</summary>
          <ul className="materials-list">
            {attachments.map((item) => <li key={item.id}>
              <button type="button" className="material-download" onClick={() => download(item)} disabled={busy}>{item.title}</button>
              <button type="button" onClick={() => open('delete', item)} disabled={busy} aria-label={'Usuń załącznik ' + item.title}>Usuń</button>
            </li>)}
          </ul>
          {!attachments.length && <p className="hint">Nie masz jeszcze załączników.</p>}
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
            <label>Dodaj plik<input ref={fileInput} type="file" required disabled={busy} onChange={(event) => setFile(event.target.files[0] ?? null)} /></label>
            <button type="submit" disabled={busy || !file}>Dodaj załącznik</button>
          </form>
        </details>
        <details>
          <summary>Moje linki ({links.length})</summary>
          <ul className="materials-list">
            {links.map((item) => <li key={item.id}>
              <a href={item.url} target="_blank" rel="noopener noreferrer">{item.title}</a>
              <div className="materials-actions">
                <button type="button" onClick={() => open('edit', item)} disabled={busy} aria-label={'Edytuj link ' + item.title}>Edytuj</button>
                <button type="button" onClick={() => open('delete', item)} disabled={busy} aria-label={'Usuń link ' + item.title}>Usuń</button>
              </div>
            </li>)}
          </ul>
          {!links.length && <p className="hint">Nie masz jeszcze linków.</p>}
          <form className="materials-form" onSubmit={(event) => {
            event.preventDefault()
            run(async () => {
              const item = await addLessonLink(chapter, lesson, { title, url })
              if (!mounted.current) return
              setItems((current) => [...current, item]); setTitle(''); setUrl('')
            })
          }}>
            <label>Tytuł<input required maxLength={200} value={title} onChange={(event) => setTitle(event.target.value)} disabled={busy} /></label>
            <label>Adres artykułu lub filmu<input required type="url" maxLength={2048} placeholder="https://…" value={url} onChange={(event) => setUrl(event.target.value)} disabled={busy} /></label>
            <button type="submit" disabled={busy}>Dodaj link</button>
          </form>
        </details>
      </>}
      {error && <p role="alert" className="error">{error}</p>}
      {action && <MaterialDialog action={action} busy={busy} error={modalError} onClose={() => setAction(null)} onConfirm={confirm} />}
    </section>
    </dialog>
    </>
  )
}

