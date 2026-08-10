# JavaQuest — frontend

Projekt React (Vite). `npm run build` **zawsze** ląduje w
`../src/main/resources/static` (patrz `vite.config.js`, `build.outDir`) — skąd
Spring Boot (`com.example.javaquest.web.JavaQuestApplication`) domyślnie
serwuje statyczne zasoby pod `/`, bez żadnej dodatkowej konfiguracji.

## Rozwój

```bash
npm install
npm run dev      # dev-server Vite na http://localhost:5173, proxy /api -> :8080
```

## Build produkcyjny

```bash
npm run build     # -> ../src/main/resources/static (nadpisuje poprzednią zawartość)
```

Backend uruchamiasz osobno (patrz katalog główny repo):

```bash
$env:JAVA_HOME = "C:\Users\kapit\.jdks\openjdk-25.0.2"
.\mvnw.cmd spring-boot:run
```

Wynik `npm run build` **nie jest** commitowany do repo (patrz `.gitignore`:
`/src/main/resources/static/`) — to artefakt budowania, nie źródło. Po
sklonowaniu repo od zera trzeba go zbudować ręcznie przed uruchomieniem
backendu, inaczej `/` zwróci 404 (samo API pod `/api/**` będzie działać).
