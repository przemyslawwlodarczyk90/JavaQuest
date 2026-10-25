// Run with node scripts/verify_motivational_quotes.mjs. No application/user data is accessed.
import { readFile } from 'node:fs/promises'

const source = await readFile(new URL('../src/main/java/com/example/javaquest/platform/Utill/MotivationalQuotes.java', import.meta.url), 'utf8')
const sources = new Map([...source.matchAll(/Source ([A-Z]+) = new Source\("[^"]+", "([^"]+)"/g)].map(match => [match[1], match[2]]))
const quotes = [...source.matchAll(/q\((\d+), ([A-Z]+), "([^"]+)", "([^"]+)"\)/g)]
if (quotes.length !== 50) throw new Error(`Expected 50 quotes, got ${quotes.length}`)
function normalize(value) {
  return value.replace(/<a[^>]*class="[^"]*(?:annotation|anchor)[^"]*"[^>]*>[\s\S]*?<\/a>/g, ' ').replace(/<[^>]*>/g, ' ').replace(/&(?:[a-z]+|#\d+|#x[\da-f]+);/gi, ' ')
    .normalize('NFKD').replace(/ł/g, 'l').replace(/Ł/g, 'L').replace(/[^a-z0-9]/gi, '').toLowerCase()
}
const pages = new Map()
for (const [author, url] of sources) {
  const response = await fetch(url, { signal: AbortSignal.timeout(30000) })
  if (!response.ok) throw new Error(`${url}: ${response.status}`)
  pages.set(author, normalize(await response.text()))
}
let failures = 0
for (const [, id, author, text] of quotes) {
  if (!pages.get(author)?.includes(normalize(text))) {
    console.error(`Quote ${id} (${author}) does not match its source: ${text}`)
    failures++
  }
}
if (failures) throw new Error(`${failures} source mismatches`)
console.log(`Verified ${quotes.length} excerpts against ${sources.size} source editions.`)
