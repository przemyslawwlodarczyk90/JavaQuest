// Ilustracje do lekcji _ns_03/04_CidrAndSubnetting.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_04_cidr_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { parse, split, vlsm, vpcPlan, summarize, ipInt } = require('./ns03_04_cidr_model.cjs');
const files = {};
const PAL = [[C.net, C.netFill], [C.ok, C.okFill], [C.host, C.hostFill], [C.vio, C.vioFill], [C.no, C.noFill]];

// 1. Podzial: kazdy bit prefiksu wiecej = polowa
{
  const W = 900, H = 400, x0 = 50, x1 = 850;
  let b = text(W / 2, 30, 'Podział sieci: +1 bit prefiksu = 2 razy więcej podsieci, każda o połowę mniejsza', { size: 17, bold: true, anchor: 'middle' });
  [16, 17, 18, 19, 20].forEach((p, r) => {
    const parts = split('10.20.0.0/16', p);
    const w = (x1 - x0) / parts.length, y = 56 + r * 64;
    parts.forEach((c, i) => {
      const [col, fill] = PAL[i % 2 === 0 ? r % 5 : (r + 1) % 5];
      b += box(x0 + i * w, y, w - 2, 44, fill, col, 3);
      if (parts.length <= 8) b += text(x0 + i * w + w / 2, y + 20, c, { anchor: 'middle', size: parts.length > 4 ? 10 : 13, bold: true, mono: true });
      if (parts.length <= 8) b += text(x0 + i * w + w / 2, y + 36, `${parse(c).hosts.toLocaleString('pl-PL')} hostów`, { anchor: 'middle', size: 10, fill: C.muted });
    });
    if (parts.length > 8) b += box((x0 + x1) / 2 - 300, y + 8, 600, 28, C.card, C.line, 6) + text((x0 + x1) / 2, y + 27, `16 × /20: 10.20.0.0, 10.20.16.0, 10.20.32.0 … 10.20.240.0 (po 4094 hosty)`, { anchor: 'middle', size: 12, bold: true });
    b += text(x0 - 6, y + 27, `/${p}`, { anchor: 'end', size: 12, bold: true });
  });
  b += text(W / 2, 388, 'Granice podsieci /N zawsze są wielokrotnością jej rozmiaru (2^(32−N)) — nie da się zacząć /20 od 10.20.8.0.', { anchor: 'middle', size: 12, fill: C.no, bold: true });
  files['ns03-04-split.svg'] = svg(W, H, 'Sieć 10.20.0.0/16 dzielona kolejno na dwie /17, cztery /18, osiem /19 i szesnaście /20; każdy dodatkowy bit prefiksu podwaja liczbę podsieci i zmniejsza o połowę ich rozmiar. Granice podsieci są wielokrotnościami ich rozmiaru.', b);
}

// 2. VLSM
{
  const W = 900, H = 360, x0 = 50, x1 = 850, X = (o) => x0 + (o / 256) * (x1 - x0);
  const v = vlsm('192.168.10.0/24', [{ name: 'Biuro', hosts: 100 }, { name: 'Magazyn', hosts: 50 }, { name: 'Serwery', hosts: 20 }, { name: 'Zarządzanie', hosts: 10 }, { name: 'Łącze P2P', hosts: 2 }]);
  let b = text(W / 2, 30, 'VLSM: 192.168.10.0/24 podzielone według potrzeb — od największej podsieci', { size: 17, bold: true, anchor: 'middle' });
  const base = ipInt('192.168.10.0');
  v.result.forEach((r, i) => {
    const p = parse(r.cidr), s = p.net - base, e = s + p.size;
    const [col, fill] = PAL[i];
    b += box(X(s), 60, X(e) - X(s) - 1, 70, fill, col, 3);
    if (p.size >= 32) b += text((X(s) + X(e)) / 2, 90, r.name, { anchor: 'middle', bold: true, fill: col, size: 13 }) + text((X(s) + X(e)) / 2, 110, `/${p.prefix}`, { anchor: 'middle', size: 12, mono: true });
    b += text(x0, 172 + i * 24, `${r.name}: potrzeba ${r.need} → ${r.cidr} (${r.usable} hostów: ${r.first} – ${r.last})`, { size: 13, fill: col, bold: i === 0 });
  });
  b += box(X(v.used), 60, X(256) - X(v.used), 70, C.card, C.line, 3) + text((X(v.used) + X(256)) / 2, 100, 'wolne', { anchor: 'middle', size: 10, fill: C.muted });
  b += text(x0, 52, '.0', { size: 10, fill: C.muted }) + text(X(128), 52, '.128', { size: 10, fill: C.muted, anchor: 'middle' }) + text(X(192), 52, '.192', { size: 10, fill: C.muted, anchor: 'middle' }) + text(x1, 52, '.255', { size: 10, fill: C.muted, anchor: 'end' });
  b += text(x0, 310, `Zajęte ${v.used} z 256 adresów, wolne ${v.free} (192.168.10.244/30, .248/29) — na kolejne łącza P2P.`, { size: 13 });
  b += text(x0, 334, 'Bez VLSM (wszystko /25): tylko 2 podsieci. Kolejność „od największej” gwarantuje wyrównanie granic.', { size: 13, bold: true, fill: C.ok });
  files['ns03-04-vlsm.svg'] = svg(W, H, 'Podział 192.168.10.0/24 metodą VLSM: Biuro 100 hostów → /25 (.0–.127), Magazyn 50 → /26 (.128), Serwery 20 → /27 (.192), Zarządzanie 10 → /28 (.224), łącze punkt-punkt 2 → /30 (.240); wolne 12 adresów.', b);
}

// 3. VPC
{
  const W = 900, H = 450;
  const v = vpcPlan();
  let b = text(W / 2, 30, 'Zadanie z planu: VPC 10.20.0.0/16 w 3 strefach dostępności (AZ)', { size: 18, bold: true, anchor: 'middle' });
  b += box(30, 48, 840, 340, C.card, C.net, 12) + text(46, 70, 'VPC 10.20.0.0/16 (65 536 adresów)', { bold: true, fill: C.net });
  const tierCol = { public: [C.ok, C.okFill, 'publiczna (ALB, NAT GW)'], private: [C.host, C.hostFill, 'prywatna (aplikacje, EKS)'], db: [C.vio, C.vioFill, 'bazy danych (RDS)'] };
  ['a', 'b', 'c'].forEach((az, i) => {
    const x = 50 + i * 272;
    b += box(x, 84, 256, 290, C.bg, C.line, 10) + text(x + 128, 106, `AZ eu-central-1${az}`, { anchor: 'middle', bold: true });
    ['public', 'private', 'db'].forEach((tier, j) => {
      const e = v.plan.find((p) => p.az === az && p.tier === tier);
      const [col, fill, label] = tierCol[tier];
      const y = 120 + j * 84;
      b += box(x + 14, y, 228, 72, fill, col, 8) + text(x + 128, y + 22, label, { anchor: 'middle', size: 12, bold: true, fill: col }) + text(x + 128, y + 44, e.cidr, { anchor: 'middle', mono: true, size: 14, bold: true }) + text(x + 128, y + 62, `${e.aws.toLocaleString('pl-PL')} adresów w AWS (−5)`, { anchor: 'middle', size: 11 });
    });
  });
  b += text(46, 410, `Zajęte ${v.used.toLocaleString('pl-PL')} adresów, wolne ${v.free.toLocaleString('pl-PL')} — w tym ${v.reserve} w rezerwie na nowe strefy i warstwy.`, { size: 12 });
  b += text(46, 432, 'Prywatne największe (pody EKS zużywają po adresie na pod), publiczne małe (tylko balansery i NAT), bazy najmniejsze.', { size: 12, bold: true });
  files['ns03-04-vpc.svg'] = svg(W, H, 'Projekt VPC 10.20.0.0/16 w trzech strefach: w każdej strefie podsieć publiczna /22 (10.20.96.0, .100.0, .104.0), prywatna /19 (10.20.0.0, .32.0, .64.0) i bazodanowa /24 (10.20.112.0, .113.0, .114.0); AWS rezerwuje 5 adresów w każdej podsieci; 10.20.128.0/17 zostaje w rezerwie.', b);
}

// 4. Nakladanie i agregacja
{
  const W = 900, H = 400;
  let b = text(W / 2, 30, 'Nakładające się zakresy: Docker, VPN i sieć domowa — oraz agregacja tras', { size: 17, bold: true, anchor: 'middle' });
  const rows = [
    ['VPN firmowy', '172.16.0.0/12', C.vio, C.vioFill],
    ['Docker bridge', '172.17.0.0/16', C.no, C.noFill],
    ['sieci docker compose', '172.18.0.0/16 …', C.no, C.noFill],
    ['Kubernetes (pody)', '10.244.0.0/16', C.ok, C.okFill],
    ['VPC w chmurze', '10.20.0.0/16', C.net, C.netFill],
  ];
  const lo = ipInt('172.16.0.0'), hi = ipInt('172.32.0.0'), X = (n) => 60 + ((n - lo) / (hi - lo)) * 780;
  [rows[0], rows[1], rows[2]].forEach(([n, c, col, fill], i) => {
    const p = parse(c.replace(' …', ''));
    const w = i === 2 ? X(p.net + p.size * 14) - X(p.net) : X(p.net + p.size) - X(p.net);
    const bw = Math.max(w, 30), narrow = bw < 260;
    b += box(X(p.net), 54 + i * 44, bw, 34, fill, col, 4) + text(narrow ? X(p.net) + bw + 8 : X(p.net) + 8, 76 + i * 44, `${n}: ${c}`, { size: 12, bold: true, fill: col });
  });
  b += text(60, 200, 'Laptop z Dockerem łączy się z VPN 172.16.0.0/12 → trasa do 172.17.x.x wskazuje na docker0, a nie na tunel:', { size: 13 });
  b += text(60, 222, 'serwery firmy z tego zakresu są „niewidoczne”. Rozwiązanie: daemon.json "bip"/"default-address-pools" poza zakresami firmy.', { size: 13, bold: true, fill: C.no });
  const s1 = summarize(['10.1.4.0/24', '10.1.5.0/24', '10.1.6.0/24', '10.1.7.0/24']);
  const s2 = summarize(['10.1.4.0/24', '10.1.9.0/24']);
  b += box(60, 248, 780, 136, C.card, C.line, 8);
  b += text(80, 274, 'Agregacja (summarization): ogłoś jedną trasę zamiast wielu', { bold: true, size: 14 });
  b += text(80, 300, `10.1.4.0/24 + 10.1.5.0/24 + 10.1.6.0/24 + 10.1.7.0/24  →  ${s1.cidr}  (dokładnie, 0 nadmiarowych adresów)`, { mono: true, size: 12, fill: C.ok });
  b += text(80, 326, `10.1.4.0/24 + 10.1.9.0/24  →  ${s2.cidr}  (obejmuje też ${s2.extra.toLocaleString('pl-PL')} cudzych adresów!)`, { mono: true, size: 12, fill: C.no });
  b += text(80, 356, 'Agreguj tylko bloki ciągłe i wyrównane — inaczej przyciągasz ruch do sieci, których nie masz (czarna dziura).', { size: 12, bold: true });
  files['ns03-04-overlap.svg'] = svg(W, H, `Nakładanie się zakresów: VPN firmowy 172.16.0.0/12 zawiera sieć Dockera 172.17.0.0/16 i sieci compose 172.18.0.0/16 i dalsze, co przechwytuje ruch do firmy. Agregacja: cztery sieci 10.1.4–7.0/24 dają dokładnie ${s1.cidr}, a 10.1.4.0/24 z 10.1.9.0/24 dają ${s2.cidr} z ${s2.extra} nadmiarowymi adresami.`, b);
}

writeSvgs(process.argv[2], files);
