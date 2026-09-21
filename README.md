# 🧭 Java Quest

**Java Quest** to moja osobista encyklopedia, repetytorium i zbiór ćwiczeń z języka Java. Projekt edukacyjny, który tworzę lekcja po lekcji, aby usystematyzować wiedzę i rozwijać praktyczne umiejętności programistyczne.

📚 W ramach tego projektu:
- Powtarzam **podstawy Javy** — od typów, pętli, klas, po kolekcje i wyjątki.
- Wchodzę w zaawansowane tematy jak **programowanie obiektowe**, **wzorce projektowe** i **testy jednostkowe**.
- Tworzę **ćwiczenia i zadania**, które pomagają utrwalać teorię w praktyce.
- Rozwijam stopniowo aplikacje z użyciem **Spring**, **Spring Boot**, **Hibernate**, **JPA**, a także innych popularnych narzędzi i bibliotek w ekosystemie Javy.

🧠 Każdy rozdział to folder z lekcjami i zadaniami.

🛠️ Technologie, które będą wykorzystywane:
- Java 17
- Maven
- Spring Boot, Spring Security
- Hibernate / JPA
- JUnit 5, Mockito
- PostgreSQL
- Git

🎯 Cel projektu:
To moja **powtórka** i podręczna **ściąga** — wszystko w jednym miejscu.

---

## 🔐 Platforma: logowanie i uruchomienie

Platforma (`com.example.javaquest.web.JavaQuestApplication`, port **8082**) wymaga zalogowania — cały `/api/**` zwraca 401 bez tokenu JWT, poza `/api/auth/register`, `/login` i `/confirm`.

**Przepływ:** rejestracja (`POST /api/auth/register`) → mail powitalny z linkiem `/potwierdz?token=…` (ważny 24 h) → aktywacja konta → logowanie (`POST /api/auth/login`, zwraca JWT) → `GET /api/auth/me`.

**Konfiguracja** (tylko dla platformy, nie dla lekcji kursu): `src/main/resources/javaquest-platform.properties` — PostgreSQL (`localhost:5432/pgDB`, `user`/`password`), Hibernate `ddl-auto=update`, SMTP Gmail, JWT. Hasło SMTP i sekret JWT trzymaj w `javaquest-platform-secrets.properties` (w `.gitignore`, wzór: `*.example`). Hasło do Gmaila to *hasło aplikacji* z konta Google.

**Baza:** `docker compose up -d` startuje PostgreSQL zgodny z domyślnymi wartościami. Bez Dockera/Postgresa można uruchomić na H2 w pamięci: `--spring.profiles.active=h2`.

**Uruchomienie:** `mvnw.cmd spring-boot:run "-Dspring-boot.run.mainClass=com.example.javaquest.web.JavaQuestApplication"`; frontend: `cd frontend && npm run build` (wynik trafia do `src/main/resources/static`) lub `npm run dev`.

Tabele treści kursu (rozdziały, lekcje, teoria, ćwiczenia, quizy) są przy każdym starcie czyszczone i zasiewane z plików JSON; tabele użytkowników (`app_user`, `confirmation_token`) są trwałe.

---

🚧 Projekt w budowie — stay tuned!
