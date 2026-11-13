// Obliczenia do lekcji _ns_02/12 (architektury Wi-Fi): narzut beaconow, budzet PoE, liczba AP.
const round = (x, d = 1) => Math.round(x * 10 ** d) / 10 ** d;
// Beacon co 102,4 ms (100 TU) => ~9,77 beaconu/s na SSID na AP. Czas nadania beaconu przy danej predkosci podstawowej
// (dlugosc ramki w bajtach * 8 / predkosc) + staly narzut preambuly/odstepow ~ 50 us (model dydaktyczny).
const BEACONS_PER_S = 1000 / 102.4;
const beaconAirtimePct = (ssids, apsHeard, bytes, rateMbps) => round(ssids * apsHeard * BEACONS_PER_S * ((bytes * 8) / rateMbps + 50) / 1e6 * 100, 1);
const POE = { '802.3af (PoE)': 15.4, '802.3at (PoE+)': 30, '802.3bt typ 3 (PoE++)': 60, '802.3bt typ 4': 90 };
const apsForClients = (clients, perAp) => Math.ceil(clients / perAp);
const apsForArea = (area, cell) => Math.ceil(area / cell);
module.exports = { round, BEACONS_PER_S, beaconAirtimePct, POE, apsForClients, apsForArea };
