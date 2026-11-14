// Ilustracje do lekcji _ns_03/10_IcmpPingTraceroute.
// Uzycie: node scripts/content-migration/netsec/lessons/ns03_10_icmp_svg.cjs frontend/public/lesson-assets/netsec
const { C, svg, text, box, line, writeSvgs } = require('./lib.cjs');
const { ICMP, MTR, analyze } = require('./ns03_10_icmp_model.cjs');
const files = {};

// 1. Traceroute
{
  const W = 900, H = 420;
  let b = text(W / 2, 30, 'Traceroute: wysyłaj z TTL = 1, 2, 3… i zbieraj komunikaty Time Exceeded', { size: 17, bold: true, anchor: 'middle' });
  const nodes = ['laptop', 'R1', 'R2', 'R3', 'serwer'];
  nodes.forEach((n, i) => { const x = 70 + i * 190; b += box(x - 50, 60, 100, 40, i === 0 ? C.netFill : i === 4 ? C.okFill : C.hostFill, i === 0 ? C.net : i === 4 ? C.ok : C.host, 8) + text(x, 86, n, { anchor: 'middle', bold: true }); if (i < 4) b += line(x + 52, 80, x + 138, 80, { w: 2 }); });
  for (let t = 1; t <= 4; t++) {
    const y = 120 + t * 54, xEnd = 70 + t * 190;
    b += text(20, y + 4, `TTL=${t}`, { mono: true, size: 12, bold: true });
    b += line(80, y, xEnd - 54, y, { arrow: true, color: C.net, w: 2 });
    b += line(xEnd - 54, y + 14, 80, y + 14, { arrow: true, color: t < 4 ? C.no : C.ok, dash: true });
    b += text(t < 4 ? xEnd - 46 : xEnd - 60, t < 4 ? y + 4 : y - 8, t < 4 ? `TTL→0 w ${nodes[t]}: ICMP Time Exceeded (typ 11)` : 'cel: Port Unreachable (UDP) / Echo Reply (ICMP)', { size: 11, fill: t < 4 ? C.no : C.ok, bold: true, anchor: t < 4 ? undefined : 'end' });
  }
  b += text(40, 404, 'Każdy skok pokazuje adres routera, który odesłał ICMP, i czas tam i z powrotem (3 próby). Gwiazdka * = brak odpowiedzi w czasie.', { size: 12 });
  files['ns03-10-traceroute.svg'] = svg(W, H, 'Mechanizm traceroute: pakiet z TTL 1 wygasa w R1, który odsyła ICMP Time Exceeded; TTL 2 wygasa w R2; TTL 3 w R3; pakiet z TTL 4 dociera do serwera, który odpowiada Port Unreachable dla UDP lub Echo Reply dla ICMP. Gwiazdka oznacza brak odpowiedzi.', b);
}

// 2. Typy ICMP i czego nie blokowac
{
  const W = 900, H = 450;
  let b = text(W / 2, 30, 'Najważniejsze komunikaty ICMP (IPv4) — i których nie wolno blokować', { size: 17, bold: true, anchor: 'middle' });
  const must = new Set(['3/4', '11/0', '3/13', '3/1', '3/0', '3/3']);
  ICMP.forEach(([t, c, n, d], i) => {
    const y = 52 + i * 32, key = `${t}/${c}`, crit = key === '3/4';
    b += box(40, y, 820, 28, crit ? C.noFill : must.has(key) ? C.okFill : C.card, crit ? C.no : must.has(key) ? C.ok : C.line, 4);
    b += text(52, y + 19, `${t}/${c}`, { mono: true, bold: true, size: 12 }) + text(110, y + 19, n, { size: 12, bold: crit }) + text(850, y + 19, d, { size: 11, anchor: 'end', fill: C.muted });
  });
  b += text(40, 392, 'Czerwony: blokada „fragmentation needed” = PMTUD black hole (TLS wisi, ping działa) — lekcja 11.', { size: 12, bold: true, fill: C.no });
  b += text(40, 414, 'Zielone: komunikaty błędów potrzebne do diagnozy; Echo można ograniczać (rate limit), ale nie wycinać wszystkiego.', { size: 12 });
  b += text(40, 436, 'IPv6: ICMPv6 jest obowiązkowy (ND, RA, Packet Too Big) — blokada całego ICMPv6 wyłącza IPv6 (RFC 4890).', { size: 12, bold: true });
  files['ns03-10-types.svg'] = svg(W, H, 'Tabela typów ICMP: 0 Echo Reply, 3/0 net unreachable, 3/1 host unreachable, 3/3 port unreachable, 3/4 fragmentation needed (nie wolno blokować — PMTUD), 3/13 administratively prohibited, 5 Redirect, 8 Echo Request, 11/0 TTL exceeded, 11/1 reassembly time exceeded. ICMPv6 jest obowiązkowy.', b);
}

// 3. Piec wynikow mtr
{
  const W = 900, H = 560;
  let b = text(W / 2, 28, 'Pięć wyników mtr: gdzie jest problem, a gdzie router tylko ignoruje ICMP?', { size: 17, bold: true, anchor: 'middle' });
  Object.entries(MTR).forEach(([k, hops], r) => {
    const y = 48 + r * 102;
    b += box(30, y, 840, 94, C.card, C.line, 8) + text(44, y + 22, k, { bold: true, size: 18, fill: C.net });
    hops.forEach((h, i) => {
      const x = 80 + i * 120;
      const lossCol = h.loss >= 2 ? C.no : C.ok;
      b += box(x, y + 10, 110, 44, h.loss >= 2 ? C.noFill : C.bg, lossCol, 4);
      b += text(x + 55, y + 26, h.host, { anchor: 'middle', size: 10, bold: true }) + text(x + 55, y + 44, `${h.loss}% · ${h.avg} ms`, { anchor: 'middle', size: 11, mono: true, fill: lossCol });
    });
    b += text(44, y + 78, '→ ' + analyze(hops).join('; '), { size: 11, bold: true, fill: C.ink });
  });
  files['ns03-10-mtr.svg'] = svg(W, H, 'Pięć wyników mtr z analizą: A — 40% strat tylko na jednym skoku pośrednim, cel bez strat: rate limiting ICMP; B — około 12% strat od skoku 4 do celu: realny problem; C — skok opóźnienia o 90 ms na łączu transatlantyckim utrzymany do celu: realne opóźnienie; D — wysokie opóźnienie tylko na jednym routerze: wolne ICMP; E — duża zmienność już od pierwszego skoku: Wi-Fi lub przeciążenie lokalne.', b);
}

// 4. Tryby traceroute
{
  const W = 900, H = 392;
  let b = text(W / 2, 30, 'traceroute UDP, ICMP i TCP — który przejdzie przez firewall?', { size: 18, bold: true, anchor: 'middle' });
  const rows = [
    ['UDP (domyślny Linux traceroute)', 'UDP na porty 33434+; koniec = ICMP Port Unreachable', 'firewalle często blokują wysokie porty UDP → gwiazdki na końcu', C.host, C.hostFill],
    ['ICMP (Windows tracert, traceroute -I)', 'ICMP Echo Request; koniec = Echo Reply', 'blokowany tam, gdzie ktoś „wyciął ping”', C.vio, C.vioFill],
    ['TCP (traceroute -T -p 443, tcptraceroute)', 'SYN na port usługi; koniec = SYN-ACK lub RST', 'przechodzi tam, gdzie działa usługa — najlepszy do HTTPS/baz', C.ok, C.okFill],
    ['mtr (traceroute + ping ciągły)', 'statystyka strat i opóźnień per skok (mtr -T -P 443 -c 100 -r)', 'raport do zgłoszenia u operatora: mtr -rwzbc 100', C.net, C.netFill],
  ];
  rows.forEach(([h, how, note, col, fill], i) => {
    const y = 52 + i * 78;
    b += box(40, y, 820, 70, fill, col, 8) + text(56, y + 22, h, { bold: true, fill: col, size: 14 }) + text(56, y + 42, how, { size: 12 }) + text(56, y + 60, note, { size: 12, fill: C.muted });
  });
  b += text(40, 372, 'Windows: tracert / Test-NetConnection -TraceRoute; pathping = tracert + statystyka strat (odpowiednik mtr).', { size: 12 });
  files['ns03-10-tracemodes.svg'] = svg(W, H, 'Porównanie trybów traceroute: UDP na porty 33434 i wyżej kończony Port Unreachable, często blokowany; ICMP Echo kończony Echo Reply; TCP SYN na port usługi kończony SYN-ACK lub RST, przechodzi tam, gdzie działa usługa; mtr łączy traceroute z ciągłym pomiarem strat i opóźnień.', b);
}

writeSvgs(process.argv[2], files);
