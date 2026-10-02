const MAX_STARS = 3

// Mastery lekcji: ★★☆. Celowo bez dat i licznikow - uzytkownik widzi stan wiedzy, nie algorytm.
export default function MasteryStars({ stars = 0, reviewSuggested = false, showHint = true }) {
  const label = `Opanowanie: ${stars} z ${MAX_STARS}`
  return (
    <span className="mastery">
      <span className="mastery__stars" role="img" aria-label={label} title={label}>
        {Array.from({ length: MAX_STARS }, (_, i) => (
          <span key={i} className={i < stars ? 'mastery__star mastery__star--on' : 'mastery__star'} aria-hidden="true">
            {i < stars ? '★' : '☆'}
          </span>
        ))}
      </span>
      {showHint && reviewSuggested && <span className="mastery__review">Warto powtórzyć</span>}
    </span>
  )
}
