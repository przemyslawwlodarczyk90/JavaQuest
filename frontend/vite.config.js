import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'node:path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  // Kazdy "npm run build" ma ladowac wynik PROSTO do src/main/resources/static,
  // skad Spring Boot domyslnie serwuje statyczne zasoby pod "/" (bez zadnej
  // dodatkowej konfiguracji Springa) - to jest cel tej konfiguracji.
  build: {
    outDir: resolve(import.meta.dirname, '../src/main/resources/static'),
    emptyOutDir: true,
  },
  server: {
    // Podczas "npm run dev" wywolania /api/** przekazujemy do lokalnie
    // uruchomionego Spring Boota (domyslny port 8080), zeby dev-server Vite
    // dzialal razem z backendem bez CORS-a.
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
