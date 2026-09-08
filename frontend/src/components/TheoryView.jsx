const TYPE_META = {
  // --- Legacy (Faza 1, lekcje jeszcze niezmigrowane do standardu Etapu 2) ---
  CONCEPT: { icon: '🔹', label: 'Pojęcie' },
  CODE_EXAMPLE: { icon: '💻', label: 'Przykład kodu' },
  DIAGRAM: { icon: '🗺️', label: 'Schemat' },

  // --- Standard Etapu 2 (11 sekcji lekcji) ---
  INTRO: { icon: '🚀', label: 'Wprowadzenie' },
  DEFINITION: { icon: '📌', label: 'Definicja' },
  ANALOGY: { icon: '🎮', label: 'Analogia' },
  VISUAL_EXAMPLE: { icon: '🗺️', label: 'Przykład wizualny' },
  CODE_BASIC: { icon: '💻', label: 'Kod — najprostszy przykład' },
  CODE_PRACTICAL: { icon: '🧩', label: 'Kod — przykład praktyczny' },
  STEP_BY_STEP: { icon: '🔍', label: 'Krok po kroku' },
  USAGE: { icon: '🎯', label: 'Zastosowanie w praktyce' },
  NOTE: { icon: 'ℹ️', label: 'Ważne' },
  PITFALL: { icon: '⚠️', label: 'Częsty błąd / pułapka' },
  CODE_WRONG: { icon: '❌', label: 'Kod — błędny' },
  CODE_RIGHT: { icon: '✅', label: 'Kod — poprawiony' },
  WHEN_TO_USE: { icon: '🧭', label: 'Kiedy używać, a kiedy nie' },
  API_REFERENCE: { icon: '📚', label: 'Katalog metod' },
  SUMMARY: { icon: '📋', label: 'Podsumowanie' },
}

// Typy blokow kodu, ktore - jesli wystapia jeden po drugim - pokazujemy obok siebie na
// szerokim ekranie (dwie kolumny), a jedna pod drugą na waskim (patrz .theory-code-pair w
// App.css). To realizuje wymog "dwa czytelne przyklady kodu" / "blad vs poprawka" obok siebie.
const PAIRABLE_SEQUENCES = [
  ['CODE_BASIC', 'CODE_PRACTICAL'],
  ['CODE_WRONG', 'CODE_RIGHT'],
]

function findPair(blocks, index) {
  const current = blocks[index]
  const next = blocks[index + 1]
  if (!next) return null
  const pair = PAIRABLE_SEQUENCES.find(([first, second]) => current.type === first && next.type === second)
  return pair ? next : null
}

function BlockTile({ block }) {
  const meta = TYPE_META[block.type] ?? { icon: '📄', label: block.type }
  const isCode = block.type === 'CODE_WRONG' || block.type === 'CODE_RIGHT'
  return (
    <article className={`theory-block theory-block--${block.type.toLowerCase()}`}>
      <div className="theory-block__header">
        <span className="theory-block__icon">{meta.icon}</span>
        <span className="theory-block__label">{meta.label}</span>
      </div>
      {block.heading && <h3>{block.heading}</h3>}
      {block.body && <p>{block.body}</p>}
      {block.code && (
        <pre className={`theory-block__code ${isCode ? `theory-block__code--${block.type.toLowerCase()}` : ''}`}>
          {block.code}
        </pre>
      )}
    </article>
  )
}

export default function TheoryView({ blocks }) {
  if (blocks.length === 0) {
    return <p className="placeholder">Treść teoretyczna tej lekcji jest w przygotowaniu.</p>
  }

  const rendered = []
  for (let i = 0; i < blocks.length; i++) {
    const block = blocks[i]
    const pairedWith = findPair(blocks, i)
    if (pairedWith) {
      rendered.push(
        <div key={i} className="theory-code-pair">
          <BlockTile block={block} />
          <BlockTile block={pairedWith} />
        </div>,
      )
      i++ // pominiety, bo juz wyrenderowany w parze
    } else {
      rendered.push(<BlockTile key={i} block={block} />)
    }
  }

  return <div className="theory-blocks">{rendered}</div>
}
