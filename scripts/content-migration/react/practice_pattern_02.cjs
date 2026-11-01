const model=`export type Role="agent"|"admin";export type TimeLeft={kind:"invalid"}|{kind:"valid";hours:number;minutes:number;overdue:boolean};export function timeLeft(until:string,now:number):TimeLeft{const deadline=Date.parse(until);if(!Number.isFinite(deadline)||!Number.isFinite(now))return {kind:"invalid"};const ms=Math.max(0,deadline-now);return {kind:"valid",hours:Math.floor(ms/3600000),minutes:Math.floor(ms%3600000/60000),overdue:ms===0};}export function allowed(current:Role,required:Role):boolean{return required==="agent"||current==="admin";}`;
const countdown=`import {useEffect,useState,type ReactNode} from "react";
${model}
const systemClock=()=>Date.now();export function useCountdown(until:string,initialNow:number,clock:()=>number=systemClock):TimeLeft{const [now,setNow]=useState(initialNow);useEffect(()=>{setNow(clock());const timer=setInterval(()=>setNow(clock()),1000);return ()=>clearInterval(timer);},[clock]);return timeLeft(until,now);}export function Countdown({until,initialNow,children}:{until:string;initialNow:number;children:(time:TimeLeft)=>ReactNode}){return children(useCountdown(until,initialNow));}`;
const guard=`import {createContext,useContext,type ComponentType,type ReactNode} from "react";export type Role="agent"|"admin";export const RoleContext=createContext<Role|null>(null);export function useCurrentRole():Role{const role=useContext(RoleContext);if(role===null)throw Error("Brak RoleContext");return role;}export function allowed(current:Role,required:Role):boolean{return required==="agent"||current==="admin";}export function RequireRole({role,children}:{role:Role;children:ReactNode}){const current=useCurrentRole();return allowed(current,role)?children:<p>Brak uprawnień do widoku.</p>;}export function withRole<P extends object>(Component:ComponentType<P>,requiredRole:Role){function Guarded(props:P){const current=useCurrentRole();return allowed(current,requiredRole)?<Component {...props}/>:<p>Brak uprawnień do widoku.</p>;}Guarded.displayName="withRole("+(Component.displayName??Component.name??"Component")+")";return Guarded;}`;
const list=`import type {Key,ReactNode} from "react";export function RenderList<T>({items,getKey,renderItem,empty}:{items:readonly T[];getKey:(item:T)=>Key;renderItem:(item:T)=>ReactNode;empty:ReactNode}){if(items.length===0)return empty;return <ul>{items.map(item=><li key={getKey(item)}>{renderItem(item)}</li>)}</ul>;}`;
const demo=`import {useState} from "react";import {Countdown,useCountdown} from "./countdown";import {RoleContext,RequireRole,withRole,type Role} from "./guard";import {RenderList} from "./list";function Reports({title}:{title:string}){return <section><h2>{title}</h2><label>Notatka<input/></label></section>;}const AdminReports=withRole(Reports,"admin");function Deadline({until,initialNow}:{until:string;initialNow:number}){const time=useCountdown(until,initialNow);if(time.kind==="invalid")return <p>Niepoprawny termin</p>;return <p>{time.overdue?"Po terminie":time.hours+" h "+time.minutes+" min"}</p>;}export function Demo({initialNow}:{initialNow:number}){const [role,setRole]=useState<Role>("agent");const until=new Date(initialNow+90000000).toISOString();return <RoleContext value={role}><main><h1>Render props, HOC i hook</h1><p>Ochrona widoku jest prezentacją. Serwer musi niezależnie sprawdzać uprawnienia.</p><label>Rola<select value={role} onChange={event=>{const next=event.currentTarget.value;if(next==="agent"||next==="admin")setRole(next);}}><option value="agent">Agent</option><option value="admin">Admin</option></select></label><Countdown until={until} initialNow={initialNow}>{time=>time.kind==="invalid"?<p>Błędny termin</p>:<strong>{time.overdue?"Po terminie":time.hours+" h "+time.minutes+" min"}</strong>}</Countdown><Deadline until={until} initialNow={initialNow}/><AdminReports title="Raport przez HOC"/><RequireRole role="admin"><Reports title="Raport przez strażnika"/></RequireRole><RenderList items={[{id:0,title:"API"},{id:2,title:"Panel"}]} getKey={ticket=>ticket.id} renderItem={ticket=><span>#{ticket.id}: {ticket.title}</span>} empty={<p>Brak ticketów</p>}/></main></RoleContext>;}`;
const exercises=[
['Zdefiniuj TimeLeft jako rozłączny wynik valid albo invalid.','Nie pozwól NaN udawać poprawnego odliczania.',model],
['Wylicz godziny i minuty z rzeczywistej różnicy czasu.','Nie zmniejszaj licznika o jeden na każde wywołanie timera.',model],
['Uznaj dokładny deadline i czas po nim za overdue.','Ogranicz różnicę do zera.',model],
['Zachowaj liczbę godzin większą niż 24.','To łączna liczba godzin, nie godzina doby.',model],
['Obsłuż niepoprawny termin i niefinitywny now.','Jawny invalid jest czytelniejszy niż NaN.',model],
['Zbuduj useCountdown zwracający dane bez narzucania JSX.','Hook przejmuje subskrypcję, wywołujący wybiera widok.',countdown],
['Czytaj zegar w callbacku interwału.','Opóźniony timer powinien nadrobić upływ czasu.',countdown],
['Posprzątaj interwał po odmontowaniu.','Cleanup ma anulować zasób własnej instancji.',countdown],
['Dodaj wstrzykiwany zegar do sprawdzania odliczania.','Stabilna funkcja clock nie wymaga restartu co render.',countdown],
['Zapewnij wspólny initialNow dla SSR i pierwszego renderu klienta.','Dalsze aktualizacje wystartują po efekcie.',countdown],
['Opakuj hook w Countdown z children jako funkcją.','Typ children to (time:TimeLeft)=>ReactNode.',countdown],
['Wyświetl dwa różne układy przy użyciu tego samego render prop.','Nie dodawaj do Countdown flag bold i compact.',demo],
['Obsłuż kind invalid przed odczytem hours.','Unia wymaga zawężenia.',demo],
['Przepisz odliczanie z render prop na bezpośrednie użycie hooka.','Zachowaj identyczny kontrakt czasu.',demo],
['Zdefiniuj RoleContext i hook zgłaszający brak providera.','Null oznacza brak kontekstu, nie rolę administratora.',guard],
['Dodaj jawną hierarchię: admin spełnia wymaganie agent.','Porównanie stringów === nie opisuje hierarchii.',model],
['Zbuduj RequireRole jako komponent prezentacji widoku.','Uprawnienia do danych nadal sprawdza serwer.',guard],
['Zbuduj generyczny withRole zachowujący props P.','ComponentType<P> opisuje przyjmowany komponent.',guard],
['Ustaw displayName opakowania.','Nazwa pomaga odczytać drzewo DevTools.',guard],
['Utwórz AdminReports na poziomie modułu.','Tworzenie typu HOC w renderze resetowałoby poddrzewo.',demo],
['Przekaż title przez opakowanie bez zmiany API Reports.','HOC nie powinien zgubić wymaganych props.',demo],
['Porównaj warunkowe renderowanie strażnika i HOC.','Obie wersje odmontowują niedostępny widok.',demo],
['Sprawdź dwie instancje Countdown z niezależnymi timerami.','Współdzielona logika hooka nie współdzieli automatycznie stanu.',countdown],
['Zaprojektuj RenderList z renderItem i getKey.','ID domenowe jest kluczem; indeks pozycji nie wystarcza.',list],
['Wyświetl jawny empty slot pustej listy.','RenderList nie musi wymyślać pustego komunikatu.',list],
['Pozwól TypeScript wywnioskować typ item z items.','Wywołujący dostaje pola rekordu w renderItem.',demo],
['Przenieś hook z renderItem do osobnego komponentu wiersza.','Callback renderujący nie jest miejscem wywoływania hooków.',list],
['Opisz ograniczenia HOC dotyczące statycznych właściwości.','Opakowanie nie kopiuje ich automatycznie.',guard],
['Rozdziel ukrycie przycisku od autoryzacji operacji.','Żądanie API wymaga niezależnej kontroli.',guard],
['Porównaj render prop, HOC i hook w działającej aplikacji.','Wybór wzorca zależy od kontraktu kompozycji, nie od samej daty powstania.',demo],
];
const concepts=[
['Co otrzymuje render prop?','Dane do wyrenderowania przez funkcję wywołującego','Zawsze komponent klasy','Wyłącznie CSS','Tylko referencję DOM'],
['Jaki typ ma children w Countdown?','(time: TimeLeft) => ReactNode','ReactNode bez funkcji','string','Promise<void>'],
['Kto wybiera wygląd render prop?','Wywołujący przekazaną funkcję','Wyłącznie hook','Serwer HTTP','Interwał'],
['Czy children jako funkcja jest automatycznie wywoływane przez React?','Nie, robi to Countdown w swoim renderze','Tak zawsze','Tylko w StrictMode','Tylko w JSX'],
['Czy wolno wywołać hook w callbacku render prop?','Nie, wydziel komponent lub hook wywołany na najwyższym poziomie','Tak zawsze','Tylko przy jednym dziecku','Tylko gdy nazwa zaczyna się od use'],
['Czym jest HOC?','Funkcją przyjmującą komponent i zwracającą komponent','Funkcją wyłącznie zwracającą dane hooka','Rodzajem CSS','Atrybutem HTML'],
['Gdzie wywołać withRole(Reports)?','Na poziomie modułu','W każdym renderze Demo','W updaterze stanu','W callbacku map po kliknięciu'],
['Dlaczego tworzenie HOC w renderze szkodzi stanowi?','Tworzy nowy typ komponentu i może powodować remount','Automatycznie mutuje props','Blokuje TypeScript','Wyłącza Context'],
['Co przenosi generyk P w HOC?','Kontrakt props opakowanego komponentu','Wszystkie statyczne pola automatycznie','Uprawnienia backendu','Dowolny typ DOM'],
['Czy HOC automatycznie kopiuje statyczne metody?','Nie','Tak wszystkie','Tylko przy displayName','Tylko w React 19'],
['Po co displayName?','Dla czytelności drzewa narzędzi','Dla autoryzacji API','Dla wymuszenia memo','Dla utworzenia klucza'],
['Co może powodować kolizję wstrzykiwanych props?','Dwa opakowania używające tej samej nazwy','Samo children','Każdy useState','Wyłącznie useId'],
['Co hook zwraca zamiast narzucać render?','Dane i operacje','Zawsze nowy typ komponentu','Wyłącznie klasę','Element DOM poza React'],
['Czy dwie instancje hooka dzielą stan automatycznie?','Nie','Tak','Tylko z identyczną nazwą','Tylko w jednym pliku'],
['Czy render props są nadal użyteczne?','Tak, gdy konsument kontroluje renderowanie','Nie, React ich zabrania','Tylko w klasach','Tylko bez TypeScript'],
['Co robi require role w UI?','Kontroluje prezentację widoku','Zastępuje autoryzację serwera','Szyfruje token','Nadaje rolę na backendzie'],
['Czy admin może spełniać wymaganie agent?','Tak, jeśli polityka tak definiuje','Nigdy','Tylko przez ===','Tylko w HOC'],
['Co zrobić z niepoprawną datą odliczania?','Zwrócić jawny stan invalid','Wyświetlić NaN godzin','Przyznać uprawnienia','Zignorować wszystkie rendery'],
['Dlaczego liczyć deadline minus now?','Timer może być opóźniony lub karta w tle','Bo setInterval jest idealnie precyzyjny','Żeby nie używać liczb','Żeby wymusić SSR'],
['Czy minuty to łączna liczba minut do deadline?','Nie, w tym modelu to reszta po pełnych godzinach','Tak zawsze','Tylko po terminie','Tylko przy invalid'],
['Czy hours może przekroczyć 24?','Tak','Nie','Tylko w UTC','Tylko z render prop'],
['Co oznacza overdue przy równości deadline i now?','true','false','invalid','undefined'],
['Dlaczego initialNow powinno być zgodne w SSR i hydratacji?','Pierwszy wynik ma być taki sam po obu stronach','Żeby nie sprzątać timera','Żeby ukryć children','Żeby zablokować Date.parse'],
['Co może restartować efekt zegara?','Zmiana tożsamości clock','Zmiana koloru CSS','Każda minuta niezależnie od deps','Wyłącznie StrictMode w produkcji'],
['Co cleanup interwału ma anulować?','Uchwyt utworzony przez jego setup','Wszystkie timery strony','Zapisane dane','Rolę użytkownika'],
['Czy setInterval 1000 gwarantuje callback dokładnie co sekundę?','Nie','Tak','Tylko na serwerze','Tylko w React'],
['Jaki klucz daje RenderList dla rekordów?','Wynik getKey oparty na stabilnej tożsamości','Zawsze indeks','Losowanie w renderze','Czas Date.now'],
['Co może zwrócić funkcja renderująca?','Dowolny ReactNode zgodny z kontraktem','Tylko string','Tylko button','Tylko element z ref'],
['Co dzieje się ze stanem Reports po ukryciu przez strażnika?','Poddrzewo jest odmontowane i stan przepada','Zawsze trafia do cache','Automatycznie zostaje w ref','Jest zapisany na serwerze'],
['Czy hook usuwa potrzebę jawnej polityki dostępu?','Nie','Tak','Tylko w TypeScript','Tylko z Context'],
];
const quiz=concepts.map(row=>[...row,'Wzorzec współdzielenia logiki nie zastępuje kontraktu danych, reguł hooków ani autoryzacji serwera.']);quiz.push(...require('./practice_pattern_01.cjs').quiz.slice(0,30).map(row=>['Powtórka kompozycji: '+row[0],...row.slice(1)]));
const cases=[];function add(label,expr,answer){cases.push([label,expr,String(answer)]);}for(const [label,ms]of [['Zero',0],['Minuta',60000],['Godzina',3600000],['Doba',86400000],['Doba i godzina',90000000],['Niepełna minuta',59000],['Przed terminem',-1],['Półtorej godziny',5400000],['Dużo godzin',360000000],['Sekunda',1000]]){const result=(()=>{const remaining=Math.max(0,ms);return [Math.floor(remaining/3600000),Math.floor(remaining%3600000/60000),remaining===0];})();for(const [i,key]of ['hours','minutes','overdue'].entries())add(label+' — '+key,`(()=>{const time=timeLeft(new Date(base+${ms}).toISOString(),base);return time.kind==="valid"?time.${key}:"invalid";})()`,result[i]);}
for(const [label,expr,answer]of [['Zła data','timeLeft("abc",base).kind','invalid'],['NaN now','timeLeft(new Date(base).toISOString(),NaN).kind','invalid'],['Infinity now','timeLeft(new Date(base).toISOString(),Infinity).kind','invalid'],['Agent do agent','allowed("agent","agent")',true],['Agent do admin','allowed("agent","admin")',false],['Admin do agent','allowed("admin","agent")',true],['Admin do admin','allowed("admin","admin")',true],['Strefy równe','timeLeft("2026-10-31T13:00:00+01:00",Date.parse("2026-10-31T12:00:00Z")).kind','valid'],['Pusty termin','timeLeft("",base).kind','invalid'],['Poprawna data','timeLeft(new Date(base+1).toISOString(),base).kind','valid']])add(label,expr,answer);
for(const [label,expr,answer]of cases){const wrong=['true','false','null','0','1','24','invalid','valid'].filter(v=>v!==answer).slice(0,3);quiz.push([label+': jaki wynik?',answer,...wrong,'Wylicz rzeczywistą różnicę czasu, rozpoznaj invalid albo zastosuj jawną hierarchię ról.',model+'\nconst base=Date.parse("2026-10-31T12:00:00Z");console.log('+expr+');']);}
const project={'src/countdown.tsx':countdown,'src/guard.tsx':guard,'src/list.tsx':list,'src/demo.tsx':demo,'src/main.tsx':`import {StrictMode} from "react";import {createRoot} from "react-dom/client";import {Demo} from "./demo";const initialNow=Date.now();createRoot(document.getElementById("root")!).render(<StrictMode><Demo initialNow={initialNow}/></StrictMode>);`};module.exports={model,countdown,guard,list,demo,exercises,quiz,project};
