const TYPE_META = {
  ANALOGY: { icon: '🎮', label: 'Analogia' },
  CONCEPT: { icon: '🔹', label: 'Pojęcie' },
  CODE_EXAMPLE: { icon: '💻', label: 'Przykład kodu' },
  DIAGRAM: { icon: '🗺️', label: 'Schemat' },
}

export default function TheoryView({ blocks }) {
  if (blocks.length === 0) {
    return <p className="placeholder">Treść teoretyczna tej lekcji jest w przygotowaniu.</p>
  }

  return (
    <div className="theory-blocks">
      {blocks.map((block, index) => {
        const meta = TYPE_META[block.type] ?? { icon: '📄', label: block.type }
        return (
          <article key={index} className={`theory-block theory-block--${block.type.toLowerCase()}`}>
            <div className="theory-block__header">
              <span className="theory-block__icon">{meta.icon}</span>
              <span className="theory-block__label">{meta.label}</span>
            </div>
            <h3>{block.heading}</h3>
            <p>{block.body}</p>
            {block.code && <pre className="theory-block__code">{block.code}</pre>}
          </article>
        )
      })}
    </div>
  )
}
