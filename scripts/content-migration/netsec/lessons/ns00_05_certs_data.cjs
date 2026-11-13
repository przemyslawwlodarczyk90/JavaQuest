// Dane o certyfikatach (stan: listopad 2026, na podstawie researchu z 2026-11-13; zawsze weryfikuj u wydawcy).
const EXAMS = [
  { id: 'N+', name: 'CompTIA Network+', code: 'N10-009', since: 'czerwiec 2024', note: 'N10-010 zapowiedziany', domains: [['Koncepcje sieci', 23, [1, 2, 3, 4, 5, 6, 7, 8]], ['Implementacja sieci', 20, [2, 3, 13]], ['Operacje sieci', 19, [9, 14]], ['Bezpieczeństwo sieci', 14, [13, 20, 21]], ['Troubleshooting sieci', 24, [9, 14]]] },
  { id: 'CCNA', name: 'Cisco CCNA', code: '200-301 v2.0', since: '3.02.2027', note: 'v1.1 do 2.02.2027', domains: [['Infrastruktura i łączność', 25, [1, 2, 3, 13]], ['Switching i dostęp do sieci', 25, [2, 13]], ['Routing IP', 20, [3]], ['Usługi sieciowe i bezpieczeństwo', 20, [5, 8, 20, 21]], ['AI, operacje i zarządzanie siecią', 10, [14]]] },
  { id: 'Sec+', name: 'CompTIA Security+', code: 'SY0-801 (V8)', since: '17.11.2026', note: 'SY0-701 do 11.06.2027 (wersja angielska)', domains: [['Ogólne koncepcje bezpieczeństwa', 16, [15, 16, 17]], ['Zagrożenia, podatności i ataki', 24, [15, 20, 23, 31, 36]], ['Architektura bezpieczeństwa', 19, [21, 24, 25]], ['Operacje bezpieczeństwa', 27, [22, 26, 27, 28, 29, 30, 33, 34, 35]], ['Zarządzanie programem bezpieczeństwa', 14, [38]]] },
  { id: 'CySA+', name: 'CompTIA CySA+', code: 'CS0-004 (V4)', since: '23.06.2026', note: 'następca CS0-003', domains: [['Operacje bezpieczeństwa', 34, [18, 19, 26, 27, 28, 29, 30, 35]], ['Zarządzanie podatnościami', 26, [22, 24]], ['Reagowanie na incydenty i zarządzanie nimi', 24, [32, 33, 34]], ['Raportowanie i komunikacja', 16, [26, 33, 38]]] },
  { id: 'SecAI+', name: 'CompTIA SecAI+', code: 'CY0-001', since: '17.02.2026', note: 'certyfikat rozszerzający', domains: [['Podstawy AI w bezpieczeństwie', 17, [36]], ['Zabezpieczanie systemów AI', 40, [36]], ['AI w operacjach bezpieczeństwa', 24, [35]], ['Ład, ryzyko i zgodność AI', 19, [36, 38]]] },
];
const PRACTICAL = [
  ['BTL1 (Security Blue Team)', 'praktyczny, ok. 24-godzinny egzamin śledczy w środowisku z narzędziami', [26, 28, 31, 32, 33, 34]],
  ['CyberDefenders CCD', 'praktyczne śledztwa: analiza sieci, endpointów, pamięci, SIEM', [26, 28, 32, 34]],
  ['Microsoft SC-200', 'Security Operations Analyst: Defender XDR, Sentinel, KQL', [16, 18, 26, 27]],
  ['GIAC GCIH / GCFA', 'reagowanie na incydenty / informatyka śledcza (zwykle z kursem SANS)', [33, 34]],
  ['ISC2 CSSLP', 'bezpieczny cykl wytwarzania oprogramowania — dla developerów', [23, 24]],
  ['AWS Certified Security – Specialty', 'bezpieczeństwo w AWS: IAM, logi, wykrywanie, ochrona danych', [16, 25]],
  ['CKS (Certified Kubernetes Security Specialist)', 'bezpieczeństwo klastrów Kubernetes (praktyczny)', [10, 25]],
];
module.exports = { EXAMS, PRACTICAL };
