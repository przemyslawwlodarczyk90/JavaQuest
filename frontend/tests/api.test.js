import test from 'node:test'
import assert from 'node:assert/strict'
import {
  uploadLessonAttachment, downloadLessonAttachment, deleteLessonAttachment, editLessonLink,
} from '../src/api.js'

test('materials API keeps JWT, multipart boundaries, binary downloads and empty delete responses', async (context) => {
  const originalFetch = globalThis.fetch
  const originalStorage = globalThis.localStorage
  context.after(() => { globalThis.fetch = originalFetch; globalThis.localStorage = originalStorage })
  globalThis.localStorage = { getItem: () => 'private-token' }
  let sent
  globalThis.fetch = async (path, options) => {
    sent = { path, ...options }
    return new Response(JSON.stringify({ id: '123', title: 'note.txt' }), { status: 201 })
  }
  await uploadLessonAttachment('chapter', 'lesson', new Blob(['private data']))
  assert.equal(sent.headers.Authorization, 'Bearer private-token')
  assert.equal(sent.headers['Content-Type'], undefined)
  assert.ok(sent.body instanceof FormData)
  assert.equal(await sent.body.get('file').text(), 'private data')

  globalThis.fetch = async (path, options) => {
    sent = { path, ...options }
    return new Response('private data', { headers: { 'Content-Type': 'application/octet-stream' } })
  }
  const file = await downloadLessonAttachment('/api/attachments/123')
  assert.equal(await file.text(), 'private data')
  assert.equal(sent.headers.Authorization, 'Bearer private-token')

  globalThis.fetch = async (path, options) => {
    sent = { path, ...options }
    return new Response(null, { status: 204 })
  }
  assert.equal(await deleteLessonAttachment('chapter', 'lesson', '123'), null)
  assert.equal(sent.method, 'DELETE')

  globalThis.fetch = async (path, options) => {
    sent = { path, ...options }
    return new Response(JSON.stringify({ title: 'Updated' }))
  }
  await editLessonLink('chapter', 'lesson', '123', { title: 'Updated', url: 'https://example.org' })
  assert.equal(sent.method, 'PUT')
  assert.equal(sent.headers['Content-Type'], 'application/json')
  assert.equal(JSON.parse(sent.body).title, 'Updated')

  globalThis.fetch = async () => new Response(JSON.stringify({ message: 'Plik jest za duży.' }), { status: 413 })
  await assert.rejects(uploadLessonAttachment('chapter', 'lesson', new Blob(['data'])),
    (error) => error.status === 413 && error.message === 'Plik jest za duży.')
})

