// Model do lekcji _ns_03/15 (FHRP): czasy przelaczenia VRRPv3/HSRP, wirtualne adresy MAC, wybor aktywnego.
// VRRPv3 (RFC 5798): Master_Down_Interval = 3 * Advertisement_Interval + Skew_Time,
//   Skew_Time = ((256 - Priority) * Advertisement_Interval) / 256
function vrrpFailover(advMs, backupPrio) {
  const skew = ((256 - backupPrio) * advMs) / 256;
  return { skew: Math.round(skew * 10) / 10, masterDown: Math.round((3 * advMs + skew) * 10) / 10 };
}
// HSRP: aktywny uznany za martwy po holdtime (domyslnie hello 3 s, hold 10 s)
const hsrpFailover = (holdMs) => holdMs;

const hex2 = (n) => n.toString(16).padStart(2, '0');
const vrrpMac = (vrid) => `00:00:5e:00:01:${hex2(vrid)}`;          // IPv4; dla IPv6: 00:00:5e:00:02:XX
const vrrp6Mac = (vrid) => `00:00:5e:00:02:${hex2(vrid)}`;
const hsrp1Mac = (group) => `00:00:0c:07:ac:${hex2(group)}`;       // grupy 0–255
const hsrp2Mac = (group) => { const h = group.toString(16).padStart(3, '0'); return `00:00:0c:9f:f${h[0]}:${h.slice(1)}`; }; // grupy 0–4095

// Wybor aktywnego/mastera: najwyzszy priorytet, przy remisie najwyzszy adres IP (VRRP: wlasciciel adresu = 255)
const ipNum = (ip) => ip.split('.').reduce((a, o) => a * 256 + Number(o), 0);
function electActive(routers) {
  return [...routers].filter((r) => r.up !== false).sort((a, b) => b.prio - a.prio || ipNum(b.ip) - ipNum(a.ip))[0]?.name ?? null;
}
// Preempt: czy router z wyzszym priorytetem przejmie role po powrocie
function afterReturn(current, returning, preempt) {
  if (!preempt) return current.name;
  return returning.prio > current.prio || (returning.prio === current.prio && ipNum(returning.ip) > ipNum(current.ip)) ? returning.name : current.name;
}

// Przerwa widziana przez klienta: czas wykrycia + czas aktualizacji ARP (GARP od nowego mastera = natychmiast) + ewentualne ponowne polaczenie TCP
const clientGap = (detectMs, garp = true, arpStaleMs = 30000) => detectMs + (garp ? 0 : arpStaleMs);

module.exports = { vrrpFailover, hsrpFailover, vrrpMac, vrrp6Mac, hsrp1Mac, hsrp2Mac, electActive, afterReturn, clientGap, ipNum };
