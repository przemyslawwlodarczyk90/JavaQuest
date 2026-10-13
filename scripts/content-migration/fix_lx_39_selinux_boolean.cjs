const fs=require('fs');
const path='scripts/content-migration/lx_practice/_lx_39_05.txt';
const blocks=fs.readFileSync(path,'utf8').replace(/\r\n/g,'\n').split(/(?=^#EX$|^#Q$)/m);
const exercises=blocks.filter(x=>x.startsWith('#EX\n'));
if(exercises.length!==30)throw Error('Unexpected source');
const old=exercises[23];
if(!old.includes('sudo setsebool httpd_can_network_connect off'))throw Error('Review existing exercise before changing');
const solution="(\n  previous_bool=$(getsebool httpd_can_network_connect | awk '{print $3}')\n  case \"$previous_bool\" in on|off) ;; *) exit 1;; esac\n  restore_bool() { sudo setsebool httpd_can_network_connect \"$previous_bool\"; }\n  trap restore_bool EXIT\n  sudo setsebool httpd_can_network_connect on || exit 1\n  curl -s -o /dev/null -w '%{http_code}\\n' http://127.0.0.1:8008/api/sklep/index.html\n)\ngetsebool httpd_can_network_connect";
const replacement='#EX\nP: Zapisz bieżący stan httpd_can_network_connect, włącz go tymczasowo (bez -P), sprawdź kod HTTP proxy i przywróć dokładnie poprzedni stan, także po błędzie zapytania.\nH: Subshell z trap EXIT przywraca zastany stan; nie zakładaj, że wcześniej było off. To globalny przełącznik polityki, ćwicz wyłącznie na rocky-lab.\nS:\n'+solution+'\n';
let text=blocks.map(x=>x===old?replacement:x).join('');
text=text.replace('P: Potwierdź stan po ćwiczeniach: tryb enforcing, przełącznik wyłączony, brak lokalnych reguł jq-www i portu 8089.','P: Potwierdź stan po ćwiczeniach: tryb enforcing, przełącznik przywrócony do zastanego stanu oraz brak własnych lokalnych reguł jq-www i portu 8089.');
fs.writeFileSync(path,text);

