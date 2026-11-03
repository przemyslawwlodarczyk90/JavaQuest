const auth=require('./practice_backend_02.cjs');
const model=`${auth.model}
export type Permission="ticket:view"|"ticket:create"|"ticket:comment"|"ticket:close"|"ticket:delete"|"ticket:assign"|"ticket:edit"|"admin:view";export type Resource={id:number;createdBy:string;status:"OPEN"|"CLOSED"};export const ROLE_PERMISSIONS:Readonly<Record<User["role"],readonly Permission[]>>={agent:["ticket:view","ticket:create","ticket:comment","ticket:close"],admin:["ticket:view","ticket:create","ticket:comment","ticket:close","ticket:delete","ticket:assign","ticket:edit","admin:view"]};export function can(auth:AuthState,permission:Permission,resource?:Resource):boolean{if(auth.status!=="authenticated")return false;const role=auth.user.role;const granted=ROLE_PERMISSIONS[role]?.includes(permission)??false;if(permission==="ticket:edit")return resource!==undefined&&resource.status==="OPEN"&&(granted||resource.createdBy===auth.user.login);if(permission==="ticket:close")return granted&&resource!==undefined&&resource.status==="OPEN";return granted;}export type Decision="checking"|"login"|"unavailable"|"forbidden"|"allowed";export function decide(auth:AuthState,permission:Permission,resource?:Resource):Decision{if(auth.status==="checking")return "checking";if(auth.status==="anonymous")return "login";if(auth.status==="error"||auth.status==="logout-uncertain")return "unavailable";return can(auth,permission,resource)?"allowed":"forbidden";}export function responseDecision(status:number):"authenticate"|"forbidden"|"other"{return status===401?"authenticate":status===403?"forbidden":"other";}export function createUnauthorizedGuard(version:()=>number,expire:()=>void){return {capture(){const own=version();return (status:number)=>{if(status===401&&version()===own)expire();};}};}`;
const views=`import {type ReactNode,useId} from "react";import {useAuth} from "./provider";import {can,decide,type Permission,type Resource} from "./permissions";export function useCan(){const auth=useAuth();return (permission:Permission,resource?:Resource)=>can(auth.state,permission,resource);}export function Can({permission,resource,children,fallback=null}:{permission:Permission;resource?:Resource;children:ReactNode;fallback?:ReactNode}){return useCan()(permission,resource)?children:fallback;}export function RequirePermission({permission,resource,children}:{permission:Permission;resource?:Resource;children:ReactNode}){const auth=useAuth();const decision=decide(auth.state,permission,resource);if(decision==="checking")return <p role="status">Sprawdzanie uprawnień…</p>;if(decision==="login")return <p>Zaloguj się, aby zobaczyć ten widok.</p>;if(decision==="unavailable")return <p role="alert">Nie można teraz potwierdzić sesji. Prywatny widok pozostaje ukryty.</p>;if(decision==="forbidden")return <section><h2>Brak dostępu</h2><p>Twoje bieżące uprawnienia nie pozwalają otworzyć tego widoku.</p></section>;return children;}export function TicketActions({ticket,onEdit,onDelete,onClose}:{ticket:Resource;onEdit:()=>void;onDelete:()=>void;onClose:()=>void}){const allows=useCan();const reasonId=useId();const edit=allows("ticket:edit",ticket),close=allows("ticket:close",ticket);return <section aria-label={"Akcje ticketu #"+ticket.id}><button type="button" disabled={!edit} aria-describedby={!edit?reasonId:undefined} onClick={()=>{if(allows("ticket:edit",ticket))onEdit();}}>Edytuj</button>{!edit&&<p id={reasonId}>{ticket.status==="CLOSED"?"Zamknięty ticket jest tylko do odczytu.":"Możesz edytować własny ticket lub skorzystać z uprawnień administratora."}</p>}<button type="button" disabled={!close} onClick={()=>{if(allows("ticket:close",ticket))onClose();}}>Zamknij</button><Can permission="ticket:delete" resource={ticket}><button type="button" onClick={()=>{if(allows("ticket:delete",ticket))onDelete();}}>Usuń</button></Can></section>;}`;
const demo=`import {useState} from "react";import {Can,RequirePermission,TicketActions} from "./views";import {Demo as SessionView} from "./sessionView";export function Demo(){const [message,setMessage]=useState("");return <div><h1>Uprawnienia w interfejsie</h1><SessionView/><p>Poniższe akcje są lokalnym podglądem reguł UI. Serwer musi niezależnie egzekwować każdą operację.</p><RequirePermission permission="ticket:view"><h2>Ticket #0</h2><TicketActions ticket={{id:0,createdBy:"anna",status:"OPEN"}} onEdit={()=>setMessage("Podgląd: edycja dozwolona")} onClose={()=>setMessage("Podgląd: zamknięcie dozwolone")} onDelete={()=>setMessage("Podgląd: usunięcie dozwolone")}/></RequirePermission><Can permission="admin:view"><p>Nawigacja administratora jest dostępna.</p></Can><RequirePermission permission="admin:view"><section><h2>Administracja</h2><p>Ten widok jest oddzielnie sprawdzany, niezależnie od ukrycia nawigacji.</p></section></RequirePermission><p role="status">{message}</p></div>;}`;
const contracts=`import type {Permission} from "./permissions";export const valid:Permission="ticket:delete";
// @ts-expect-error literówka nie należy do Permission
const typo:Permission="ticket:delte";`;
const exercises=[
['Zdefiniuj Permission jako unię akcji, a nie dowolny string.','Literówka ma być błędem kompilacji.',model],
['Przygotuj kompletną mapę ról przez Record.','Dodanie nowej roli powinno wymagać decyzji o uprawnieniach.',model],
['Rozdziel globalną zdolność roli i regułę zasobu.','Edycja zależy także od autora i statusu ticketu.',model],
['Odmów wszystkim prywatnym akcjom poza authenticated.','Checking nie jest ani adminem, ani potwierdzonym anonymous.',model],
['Pozwól agentowi edytować własny otwarty ticket.','Porównuj stabilny login z createdBy.',model],
['Odmów agentowi edycji cudzego ticketu.','Samo przypisanie do osoby nie oznacza autorstwa.',model],
['Pozwól adminowi edytować cudzy otwarty ticket.','Reguła jest jawna w jednym miejscu.',model],
['Zablokuj edycję zamkniętego ticketu także adminowi.','To świadoma polityka przykładu, backend musi ją odzwierciedlać.',model],
['Nie przyznawaj ticket:edit bez zasobu.','Brak createdBy nie jest potwierdzeniem własności.',model],
['Pozwól zamykać tylko konkretny otwarty ticket.','Brak zasobu albo CLOSED zwraca false.',model],
['Zachowaj ticket:delete i ticket:assign tylko dla admina.','Nie rozsiewaj sprawdzania role===admin po każdym widoku.',model],
['Zbuduj czystą funkcję can do testowania polityki.','Hook jest tylko adapterem kontekstu.',model],
['Zbuduj useCan korzystające z bieżącego auth.state.','Nie kopiuj uprawnień do osobnego state.',views],
['Dodaj Can z jawnym fallback.','Ukryta akcja nie oznacza ochrony serwera.',views],
['Dodaj RequirePermission dla całego widoku.','Samo ukrycie linku nie zabezpiecza wejścia bezpośredniego.',views],
['Obsłuż checking bez błędnego Brak dostępu.','Użytkownik czeka na ustalenie stanu.',views],
['Obsłuż anonymous komunikatem o logowaniu.','Nie myl go z brakiem uprawnienia zalogowanego.',views],
['Obsłuż błąd sprawdzania i niepewne wylogowanie jako unavailable.','Nie udawaj, że API potwierdziło 403.',model],
['Zablokuj Edytuj z widocznym wyjaśnieniem.','Sam title jest niewystarczającym opisem dla disabled kontrolki.',views],
['Połącz wyjaśnienie przez aria-describedby.','Unikalne ID pochodzi z useId.',views],
['Ukryj Usuń dla ról bez ticket:delete.','Gdy akcja nie powinna być prezentowana, użyj Can.',views],
['Sprawdź regułę ponownie w handlerze jako ochronę UX.','To nadal nie zastępuje autoryzacji endpointu.',views],
['Rozróżnij 401 i 403 w mapie reakcji UI.','403 nie powinno automatycznie wylogowywać.',model],
['Nie wylogowuj nowej sesji przez spóźnione 401 starej.','Przechwyć wersję sesji przy starcie requestu.',model],
['Przetestuj brak uprawnień niezależnie od nawigacji.','Bezpośredni widok wymaga osobnej bramki.',demo],
['Dodaj test negatywny literówki Permission.','@ts-expect-error musi być rzeczywiście wykorzystane.',contracts],
['Sprawdź ID=0, nieznanego autora i zamknięty rekord.','Nie używaj Boolean(id) jako reguły dostępu.',model],
['Sprawdź zmianę roli w trakcie życia interfejsu.','Widok ma użyć nowych danych kontekstu bez lokalnego cache polityki.',views],
['Porównaj mapę frontendową z regułami Spring Security.','Role i własność muszą być egzekwowane na backendzie.',model],
['Spróbuj wykonać niedozwolony request poza przyciskiem UI.','Dopiero odmowa serwera potwierdza ochronę operacji.',demo],
];
const concepts=[
['Po co uprawnienia po stronie UI?','Dopasowują widok i akcje do możliwości użytkownika','Zastępują autoryzację API','Szyfrują requesty','Weryfikują podpis JWT na serwerze'],
['Kto ostatecznie zezwala na operację?','Serwer','Can komponent','CSS','Ukryty przycisk'],
['Dlaczego modelować akcje przez Permission?','Reguły roli są scentralizowane','Żeby dowolny string zawsze działał','Żeby wyłączyć TypeScript','Żeby unikać testów'],
['Co daje Record<Role,Permission[]>?','Wymusza wpis dla każdej roli','Waliduje automatycznie serwer','Wysyła CSRF','Tworzy router'],
['Czy checking pozwala pokazać chronione dane?','Nie','Tak','Tylko przy admin:view','Tylko na chwilę'],
['Czy błąd /me oznacza pewny brak uprawnień?','Nie, może oznaczać niedostępność sprawdzenia','Tak zawsze','Tylko przy TypeError','Tylko w React'],
['Co oznacza 401 w typowym kontrakcie aplikacji?','Potrzebę poprawnego uwierzytelnienia','Udany zapis','Zawsze brak zasobu','Wyłącznie CSRF'],
['Co oznacza 403?','Odmowę wykonania operacji według polityki serwera','Zawsze konieczność wylogowania','Udany logout','Brak JSON'],
['Czy 403 powinno zawsze kasować sesję?','Nie','Tak','Tylko dla admin','Tylko przy DELETE'],
['Dlaczego wersjonować reakcję na 401?','Stara odpowiedź nie może wylogować nowo zalogowanej sesji','Żeby zmienić HTTP status','Żeby pominąć login','Żeby przechować hasło'],
['Co musi znać reguła edycji właściciela?','Stabilną tożsamość użytkownika i autora zasobu','Wyłącznie tytuł ticketu','Wyłącznie indeks wiersza','Kolor przycisku'],
['Czy brak zasobu potwierdza własność?','Nie','Tak','Tylko przy id 0','Tylko dla agent'],
['Czy agent może edytować cudzy ticket w tym modelu?','Nie','Tak zawsze','Tylko jeśli zna ID','Tylko przez Can'],
['Czy admin może edytować zamknięty ticket w tym modelu?','Nie, jest tylko do odczytu','Tak automatycznie','Tylko przez own resource','Tylko w JSX'],
['Czy administrator zawsze musi móc wszystko w każdej aplikacji?','Nie, polityka jest jawna i domenowa','Tak, nazwa roli to gwarancja','Tylko w Spring','Tylko z JWT'],
['Co daje hook useCan?','Adapter bieżącego kontekstu do czystej polityki','Autoryzację backendu','Własny cache serwera','Nową rolę'],
['Dlaczego nie kopiować permissions do state?','Mogłyby się rozjechać z aktualną sesją','Bo React nie obsługuje array','Bo TypeScript zabrania','Bo CSRF tego wymaga'],
['Co robi fallback Can?','Pokazuje uzgodnioną alternatywę przy odmowie','Wywołuje POST','Przyznaje rolę','Ukrywa wszystkie błędy'],
['Czy ukryty link chroni bezpośrednie wejście na ekran?','Nie','Tak','Tylko przy admin','Tylko w TSX'],
['Dlaczego RequirePermission jest osobne od ukrycia nawigacji?','Widok musi sprawdzać własny kontrakt dostępu','Bo router zawsze waliduje role','Bo QueryClient tego wymaga','Bo JSX nie ma children'],
['Co pokazać przy checking?','Status sprawdzania','Błędną odmowę admina','Prywatne dane na chwilę','Losowy widok'],
['Jak nazwać błąd sprawdzania sesji?','Nie można potwierdzić sesji','Na pewno brak uprawnień','Zawsze Zaloguj jako admin','Operacja usunięta'],
['Kiedy disabled z wyjaśnieniem ma sens?','Gdy użytkownik powinien wiedzieć o istniejącej akcji i jej ograniczeniu','Nigdy','Wyłącznie dla sekretnego panelu','Zawsze zamiast autoryzacji'],
['Czy sam title dobrze wyjaśnia disabled przycisk wszystkim użytkownikom?','Nie, lepszy jest widoczny opis','Tak zawsze','Tylko gdy krótki','Tylko w Chrome'],
['Co łączy opis ograniczenia z przyciskiem?','aria-describedby','aria-modal','popoverTarget','aria-checked'],
['Czy handler guard zastępuje kontrolę serwera?','Nie','Tak','Tylko przy can=true','Tylko przy useCan'],
['Czy klient może sfałszować createdBy w request?','Tak, dlatego serwer ustala i weryfikuje autorstwo','Nie, TypeScript chroni API','Tylko bez JSX','Tylko przy id 0'],
['Czy ID=0 musi być niedostępne?','Nie','Tak','Tylko dla admin','Tylko w Java'],
['Co przetestować w polityce?','Role, własność, status zasobu i stany sesji','Tylko wygląd Usuń','Wyłącznie importy','Tylko sukces admina'],
['Co dowodzi ochrony operacji?','Odmowa serwera dla niedozwolonego requestu','Ukrycie przycisku w React','Zielony build','Brak linku w menu'],
];
const quiz=concepts.map(row=>[...row,'Polityka frontendowa służy spójnemu UX. Autoryzację zasobu i operacji egzekwuje serwer.']);quiz.push(...auth.quiz.slice(0,30).map(row=>['Powtórka sesji przed uprawnieniami: '+row[0],...row.slice(1)]));
const cases=[];const permissions=['ticket:view','ticket:create','ticket:comment','ticket:close','ticket:delete','ticket:assign','ticket:edit','admin:view'];for(const [label,state,allowed]of [['Anonymous','{status:"anonymous"}',[]],['Checking','{status:"checking"}',[]],['Agent właściciel','{status:"authenticated",user:{login:"anna",name:"Anna",role:"agent"}}',['ticket:view','ticket:create','ticket:comment','ticket:close','ticket:edit']],['Agent inny','{status:"authenticated",user:{login:"jan",name:"Jan",role:"agent"}}',['ticket:view','ticket:create','ticket:comment','ticket:close']],['Admin','{status:"authenticated",user:{login:"admin",name:"Admin",role:"admin"}}',permissions]])for(const permission of permissions){const answer=String(allowed.includes(permission));cases.push([label+' / '+permission,answer,'null','undefined',String(!allowed.includes(permission)),'Zastosuj stan sesji, mapę roli oraz regułę własności otwartego ticketu.',model+'\nconsole.log(can('+state+',"'+permission+'",{id:0,createdBy:"anna",status:"OPEN"}));']);}quiz.push(...cases);
const project={...auth.project,'src/permissions.ts':model,'src/views.tsx':views,'src/contracts.tsx':contracts,'src/sessionView.tsx':auth.demo,'src/demo.tsx':demo};module.exports={model,views,demo,contracts,exercises,quiz,project};
