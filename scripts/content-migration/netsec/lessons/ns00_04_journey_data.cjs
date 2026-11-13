// Wspolny model „podrozy jednego zadania” dla lekcji _ns_00/04 (kroki, czasy, rozdzialy, typowe bledy).
const STEPS = [
  { n: 1, name: 'Parsowanie URL', where: 'przeglądarka / klient', ms: 0.1, ch: 6, what: 'schemat https, host api.javaquest.local, port 443 (domyślny), ścieżka /users/42', err: 'zły schemat lub port, literówka w hoście', layer: 'L7' },
  { n: 2, name: 'Cache i HSTS', where: 'przeglądarka', ms: 0.2, ch: 6, what: 'czy odpowiedź jest w cache HTTP; czy domena ma HSTS (wymuszenie HTTPS)', err: 'nieaktualna odpowiedź z cache, ostrzeżenie HSTS', layer: 'L7' },
  { n: 3, name: 'Rozwiązanie nazwy (DNS)', where: 'resolver systemu → serwer DNS', ms: 12, ch: 5, what: 'zapytanie A/AAAA o api.javaquest.local, odpowiedź z adresem i TTL', err: 'UnknownHostException, zły rekord, stary cache', layer: 'L7 (DNS)' },
  { n: 4, name: 'Trasa i ARP', where: 'jądro systemu', ms: 1, ch: 2, what: 'tablica routingu → brama domyślna; ARP o MAC bramy', err: 'No route to host, brak odpowiedzi ARP', layer: 'L2–L3' },
  { n: 5, name: 'Handshake TCP', where: 'jądro klienta ↔ serwera', ms: 20, ch: 4, what: 'SYN, SYN-ACK, ACK na port 443 (1 RTT)', err: 'Connection refused, connect timed out', layer: 'L4' },
  { n: 6, name: 'Handshake TLS 1.3', where: 'biblioteka TLS', ms: 21, ch: 7, what: 'ClientHello (SNI, ALPN h2), certyfikat, klucze sesji (1 RTT)', err: 'SSLHandshakeException, PKIX, wygasły certyfikat', layer: 'L6' },
  { n: 7, name: 'Żądanie HTTP/2', where: 'klient → reverse proxy', ms: 1, ch: 6, what: 'ramki HEADERS: GET /users/42, nagłówki, ciasteczko sesji', err: '400, 413, problemy z HTTP/2', layer: 'L7' },
  { n: 8, name: 'Reverse proxy', where: 'Nginx / Caddy / ALB', ms: 2, ch: 11, what: 'zakończenie TLS, routing do backendu, X-Forwarded-For, nowe połączenie', err: '502, 503, 504', layer: 'L7' },
  { n: 9, name: 'Tomcat i filtry', where: 'JVM: Tomcat, Spring Security', ms: 3, ch: 12, what: 'wątek z puli, parsowanie HTTP, łańcuch filtrów, uwierzytelnienie', err: '401, 403, wyczerpana pula wątków', layer: 'aplikacja' },
  { n: 10, name: 'DispatcherServlet i kontroler', where: 'Spring MVC', ms: 1, ch: 6, what: 'mapowanie /users/{id}, walidacja, wywołanie serwisu', err: '404, 400, 500 z wyjątku', layer: 'aplikacja' },
  { n: 11, name: 'HikariCP i protokół PostgreSQL', where: 'JDBC → baza', ms: 6, ch: 12, what: 'połączenie z puli, zapytanie SELECT … WHERE id = $1, wynik', err: 'SQLTransientConnectionException, timeout zapytania', layer: 'L7 (PG)' },
  { n: 12, name: 'Odpowiedź i droga powrotna', where: 'serializacja JSON → proxy → klient', ms: 8, ch: 6, what: 'Jackson → HTTP 200 → proxy → TLS → klient, render w przeglądarce', err: 'błędy serializacji, ucięta odpowiedź, timeouty', layer: 'L7' },
];
const TOTAL = STEPS.reduce((a, s) => a + s.ms, 0);
module.exports = { STEPS, TOTAL };
