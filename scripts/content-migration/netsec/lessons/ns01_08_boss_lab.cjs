// Wspolny model labu Boss „Packet Journey” (_ns_01/08): adresy, MAC, porty i wartosci pol w trzech punktach przechwytywania.
const NODES = {
  client: { name: 'laptop (klient)', ip: '10.10.1.10', mac: '02:42:0a:0a:01:0a' },
  rlan: { name: 'router — interfejs LAN', ip: '10.10.1.1', mac: '02:42:0a:0a:01:01' },
  rwan: { name: 'router — interfejs WAN', ip: '10.20.0.2', mac: '02:42:0a:14:00:02' },
  pwan: { name: 'proxy — interfejs zewnętrzny', ip: '10.20.0.10', mac: '02:42:0a:14:00:0a' },
  papp: { name: 'proxy — interfejs aplikacyjny', ip: '10.30.0.10', mac: '02:42:0a:1e:00:0a' },
  app: { name: 'Spring Boot', ip: '10.30.0.20', mac: '02:42:0a:1e:00:14' },
};
const CLIENT_PORT = 45678, PROXY_EPH = 38210, TTL0 = 64;

// Pakiet z zadaniem HTTP w kazdym punkcie przechwytywania
const POINTS = [
  { id: 'P1', where: 'LAN: między laptopem a routerem', srcMac: NODES.client.mac, dstMac: NODES.rlan.mac, srcIp: NODES.client.ip, dstIp: NODES.pwan.ip, srcPort: CLIENT_PORT, dstPort: 80, ttl: TTL0, xff: '—', conn: 'połączenie 1 (klient ↔ proxy)' },
  { id: 'P2', where: 'WAN: między routerem a proxy', srcMac: NODES.rwan.mac, dstMac: NODES.pwan.mac, srcIp: NODES.rwan.ip, dstIp: NODES.pwan.ip, srcPort: CLIENT_PORT, dstPort: 80, ttl: TTL0 - 1, xff: '—', conn: 'połączenie 1 (po NAT)' },
  { id: 'P3', where: 'APP: między proxy a aplikacją', srcMac: NODES.papp.mac, dstMac: NODES.app.mac, srcIp: NODES.papp.ip, dstIp: NODES.app.ip, srcPort: PROXY_EPH, dstPort: 8080, ttl: TTL0, xff: NODES.rwan.ip, conn: 'połączenie 2 (proxy ↔ aplikacja)' },
];

const COMPOSE = `# docker-compose.yml — lab „Packet Journey” (tylko lokalnie, sieci izolowane)
services:
  client:
    image: nicolaka/netshoot
    cap_add: [NET_ADMIN]
    command: sh -c "ip route replace default via 10.10.1.1 && sleep infinity"
    networks: { lan: { ipv4_address: 10.10.1.10 } }
  router:
    image: nicolaka/netshoot
    cap_add: [NET_ADMIN]
    sysctls: { net.ipv4.ip_forward: 1 }
    command: >
      sh -c "nft add table ip nat &&
             nft add chain ip nat post '{ type nat hook postrouting priority 100; }' &&
             nft add rule ip nat post oifname eth1 masquerade && sleep infinity"
    networks:
      lan: { ipv4_address: 10.10.1.1 }     # eth0
      wan: { ipv4_address: 10.20.0.2 }     # eth1 (kolejność interfejsów sprawdź: ip -br addr)
  proxy:
    image: caddy:2
    command: caddy reverse-proxy --from :80 --to app:8080
    networks:
      wan: { ipv4_address: 10.20.0.10 }
      app: { ipv4_address: 10.30.0.10 }
  app:
    image: javaquest/lab-users:1.0          # Spring Boot z GET /users/{id}, port 8080
    networks: { app: { ipv4_address: 10.30.0.20 } }

networks:
  lan: { internal: true, ipam: { config: [{ subnet: 10.10.1.0/24, gateway: 10.10.1.254 }] } }
  wan: { internal: true, ipam: { config: [{ subnet: 10.20.0.0/24, gateway: 10.20.0.254 }] } }
  app: { internal: true, ipam: { config: [{ subnet: 10.30.0.0/24, gateway: 10.30.0.254 }] } }`;

module.exports = { NODES, POINTS, CLIENT_PORT, PROXY_EPH, TTL0, COMPOSE };
