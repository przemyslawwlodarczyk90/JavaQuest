import { Component } from 'react'

// Bez tego komponentu KAZDY nieprzewidziany blad renderowania (np. dane w
// nieoczekiwanym ksztalcie) wywalal cala aplikacje do PUSTEJ, bialej strony -
// React odmontowuje drzewo po nieprzechwyconym bledzie w renderze, a blad
// bylo widac WYLACZNIE w konsoli przegladarki. React wymaga do tego
// komponentu klasowego - funkcyjne komponenty nie moga byc "error boundary".
export default class ErrorBoundary extends Component {
  constructor(props) {
    super(props)
    this.state = { error: null }
  }

  static getDerivedStateFromError(error) {
    return { error }
  }

  componentDidCatch(error, info) {
    console.error('Nieprzechwycony blad w interfejsie:', error, info)
  }

  render() {
    if (this.state.error) {
      return (
        <div className="error-boundary">
          <h2>Ups, coś tu nie zagrało.</h2>
          <p className="hint">
            Ta część widoku napotkała nieoczekiwany błąd zamiast normalnie się wyświetlić.
          </p>
          <button type="button" onClick={() => this.setState({ error: null })}>
            Spróbuj ponownie
          </button>
        </div>
      )
    }
    return this.props.children
  }
}
