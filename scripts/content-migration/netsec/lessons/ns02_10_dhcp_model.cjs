// Obliczenia do lekcji _ns_02/10 (DHCP i SLAAC): EUI-64, timery dzierzawy, pule adresow.
const eui64 = (mac) => {
  const b = mac.toLowerCase().split(/[:-]/).map((x) => parseInt(x, 16));
  b[0] ^= 0x02; // odwrocenie bitu U/L
  const e = [b[0], b[1], b[2], 0xff, 0xfe, b[3], b[4], b[5]];
  const groups = [0, 2, 4, 6].map((i) => ((e[i] << 8) | e[i + 1]).toString(16));
  return groups.join(':');
};
const linkLocal = (mac) => 'fe80::' + eui64(mac);
const fmtTime = (s) => { const h = Math.floor(s / 3600), m = Math.floor((s % 3600) / 60), r = s % 60; return [h ? `${h} h` : '', m ? `${m} min` : '', r ? `${r} s` : ''].filter(Boolean).join(' ') || '0 s'; };
const timers = (lease) => ({ t1: lease / 2, t2: lease * 7 / 8 });
const hostsIn = (prefix) => 2 ** (32 - prefix) - 2;
const MSG = [[1, 'DHCPDISCOVER'], [2, 'DHCPOFFER'], [3, 'DHCPREQUEST'], [4, 'DHCPDECLINE'], [5, 'DHCPACK'], [6, 'DHCPNAK'], [7, 'DHCPRELEASE'], [8, 'DHCPINFORM']];
const OPT = [[1, 'maska podsieci'], [3, 'brama domyślna (router)'], [6, 'serwery DNS'], [15, 'nazwa domeny'], [42, 'serwery NTP'], [43, 'informacje producenta (np. kontroler Wi-Fi)'], [51, 'czas dzierżawy'], [53, 'typ komunikatu DHCP'], [54, 'identyfikator serwera'], [66, 'nazwa serwera TFTP'], [67, 'nazwa pliku rozruchowego'], [82, 'informacje agenta relay'], [119, 'lista domen wyszukiwania'], [121, 'bezklasowe trasy statyczne']];
module.exports = { eui64, linkLocal, fmtTime, timers, hostsIn, MSG, OPT };
