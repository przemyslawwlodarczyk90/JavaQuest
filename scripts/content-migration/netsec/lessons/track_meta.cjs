// Metadane toru do generatorow lekcji: pelny plan i krotkie nazwy rozdzialow.
const plan = [...require('../plan_part1_sieci.cjs'), ...require('../plan_part2_sieci_infrastruktura.cjs'), ...require('../plan_part3_cyberbezpieczenstwo.cjs')];
const SHORT = ['jak działa ten tor', 'fundamenty i modele warstw', 'Ethernet i switching', 'adresacja IP i routing', 'TCP, UDP i QUIC', 'DNS', 'HTTP', 'TLS i PKI', 'protokoły aplikacyjne', 'Linux networking', 'Docker, Kubernetes, AWS', 'proxy i load balancing', 'sieci w Javie',
  'infrastruktura, Wi-Fi, WAN', 'operacje i automatyzacja',
  'fundamenty bezpieczeństwa', 'tożsamość i dostęp', 'kryptografia stosowana', 'Windows i Active Directory', 'Linux: telemetria i detekcja', 'zagrożenia sieciowe', 'architektura bezpieczeństwa', 'hardening i podatności', 'AppSec i API (OWASP 2025)', 'DevSecOps i łańcuch dostaw', 'chmura i Kubernetes', 'logi, SIEM i SOC', 'detection engineering', 'analiza ruchu i NSM', 'threat intelligence', 'threat hunting', 'phishing i poczta', 'analiza malware', 'reagowanie na incydenty', 'informatyka śledcza (DFIR)', 'automatyzacja SOC i AI', 'bezpieczeństwo AI i LLM', 'purple team', 'GRC: ryzyko i zgodność', 'studia przypadków', 'egzaminy i kariera'];
if (SHORT.length !== plan.length) throw new Error('SHORT ' + SHORT.length);
module.exports = { plan, SHORT };
