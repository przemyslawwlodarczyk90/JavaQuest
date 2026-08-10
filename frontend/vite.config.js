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
    // uruchomionego Spring Boota, zeby dev-server Vite dzialal razem z
    // backendem bez CORS-a. Port 8082, NIE domyslny 8080 - na tej maszynie
    // 8080 jest trwale zajety przez systemowy "AgentService" (patrz
    // JavaQuestApplication.java), wiec backend startuje na 8082.
    proxy: {
      '/api': {
        target: 'http://localhost:8082',
        changeOrigin: true,
      },
    },
  },
})
