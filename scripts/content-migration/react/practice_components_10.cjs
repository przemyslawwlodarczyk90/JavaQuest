// Boss rozdziału: samodzielne rozwiązania małych etapów i pełny dashboard.
const model = 'type Role="admin"|"agent"|"viewer";\ntype User={id:number;login:string;name:string|null;role:Role;active:boolean};';
const sample = 'export const users:readonly User[]=[{id:1,login:"anna",name:"Anna Nowak",role:"admin",active:true},{id:2,login:"piotr",name:"Piotr Zieliński",role:"agent",active:true},{id:3,login:"kasia",name:null,role:"agent",active:true},{id:4,login:"tomek",name:"Tomasz Wójcik",role:"viewer",active:false},{id:5,login:"ola",name:"Aleksandra Lis",role:"agent",active:true}];';
const helpers = 'export function displayName(user:User):string{return user.name??user.login;}\nexport function initials(name:string):string{return name.trim().split(/\\s+/).filter(Boolean).slice(0,2).map(word=>word[0]?.toUpperCase()??"").join("");}\nexport function summarize(users:readonly User[]){return {total:users.length,active:users.filter(user=>user.active).length,admins:users.filter(user=>user.role==="admin").length};}';
const badge = 'type Tone="neutral"|"danger"|"info";\nconst ROLE_TONE:Record<Role,Tone>={admin:"danger",agent:"info",viewer:"neutral"};\nexport function Badge({tone="neutral",children}:{tone?:Tone;children:ReactNode}){return <span className={`badge badge--${tone}`}>{children}</span>;}\nexport function RoleBadge({role}:{role:Role}){return <Badge tone={ROLE_TONE[role]}>{role}</Badge>;}';
const panel = 'export function Panel({title,actions,children}:{title:string;actions?:ReactNode;children:ReactNode}){return <section className="panel"><header><h2>{title}</h2>{actions!==null&&actions!==undefined&&<div className="panel__actions">{actions}</div>}</header><div className="panel__body">{children}</div></section>;}';
const stats = 'export function StatCard({label,value}:{label:string;value:number}){return <article className="stat"><h3>{label}</h3><strong>{value}</strong></article>;}\nexport function StatsRow({users}:{users:readonly User[]}){const counts=summarize(users);return <div className="stats"><StatCard label="Użytkownicy" value={counts.total}/><StatCard label="Aktywni" value={counts.active}/><StatCard label="Administratorzy" value={counts.admins}/></div>;}';
const table = 'export function Avatar({name}:{name:string}){return <span className="avatar" aria-hidden="true">{initials(name)}</span>;}\nexport function UserRow({user}:{user:User}){const name=displayName(user);return <tr className={user.active?"user-row":"user-row user-row--inactive"} style={{opacity:user.active?1:0.5}}><td><Avatar name={name}/>{name}{!user.active&&<small> (nieaktywny)</small>}</td><td>{user.login}</td><td><RoleBadge role={user.role}/></td><td>{user.active?"Aktywny":"Nieaktywny"}</td></tr>;}\nexport function UserTable({users}:{users:readonly User[]}){if(users.length===0)return <p>Brak użytkowników</p>;return <table><thead><tr><th scope="col">Użytkownik</th><th scope="col">Login</th><th scope="col">Rola</th><th scope="col">Status</th></tr></thead><tbody>{users.map(user=><UserRow key={user.id} user={user}/>)}</tbody></table>;}';
const dashboard = 'import type {ReactNode} from "react";\n'+model+'\n'+helpers+'\n'+badge+'\n'+panel+'\n'+stats+'\n'+table+'\nexport function UsersPage({users,onInvite}:{users:readonly User[];onInvite:()=>void}){return <main><h1>Użytkownicy</h1><Panel title="Statystyki"><StatsRow users={users}/></Panel><Panel title="Lista użytkowników" actions={<button type="button" onClick={onInvite}>+Zaproś</button>}><UserTable users={users}/></Panel></main>;}';
const exercises = [
 ['Zdefiniuj Role i User dla dashboardu: id, login, nullable name, rola i active. Odrzuć nieznaną rolę na etapie kompilacji.','Rola jest unią literałów, name ma jawne null.',model+'\nconst valid:User={id:1,login:"anna",name:null,role:"admin",active:true};\n// @ts-expect-error Nieznana rola\nconst invalid:Role="owner";'],
 ['Przygotuj pięć rekordów bossa: Anna admin, Piotr agent, kasia agent bez imienia, Tomasz viewer nieaktywny i Aleksandra agent.','Identyfikatory są stałe i niezależne od pozycji na liście.',model+'\n'+sample],
 ['Napisz displayName: name=null ma dać login, ale name="" ma pozostać pustym napisem.','Fallback ma dotyczyć null i undefined, nie wszystkich wartości falsy.',model+'\nexport function displayName(user:User){return user.name??user.login;}'],
 ['Oblicz total, active i admins bez zmieniania wejściowej tablicy użytkowników. Nieaktywny admin nadal liczy się do admins.','Każdy licznik odpowiada odrębnemu kryterium.',model+'\n'+helpers],
 ['Wyznacz statystyki dla pięciu rekordów bossa i dla pustej listy.','Tomasz jest jedynym nieaktywnym, Anna jedynym adminem.','Pięć rekordów: total=5, active=4, admins=1. Pusta lista: total=0, active=0, admins=0. Admins nie oznacza tylko aktywnych administratorów.'],
 ['Zbuduj jeden StatCard dla etykiety i wartości liczbowej; zero powinno pozostać widoczne.','Wartość renderuj bezpośrednio, bez value &&.','export function StatCard({label,value}:{label:string;value:number}){return <article><h3>{label}</h3><strong>{value}</strong></article>;}'],
 ['Złóż StatsRow z trzema instancjami tego samego StatCard.','Wylicz counts raz w danym renderze.',model+'\n'+helpers+'\n'+stats],
 ['Napisz ogólny Badge przyjmujący tone i children, bez wiedzy o rolach użytkownika.','Typ ReactNode importuj przez import type.','import type {ReactNode} from "react";\n'+model+'\n'+badge],
 ['Zdefiniuj pełne mapowanie admin→danger, agent→info, viewer→neutral przez Record.','Brakujące mapowanie ma być błędem TypeScriptu.',model+'\ntype Tone="neutral"|"danger"|"info";\nexport const roleTone:Record<Role,Tone>={admin:"danger",agent:"info",viewer:"neutral"};'],
 ['Zbuduj RoleBadge jako opakowanie ogólnego Badge. Kolor ma wynikać z roli.','Nie duplikuj znaczników span w trzech gałęziach.','import type {ReactNode} from "react";\n'+model+'\n'+badge],
 ['Napisz initials obsługujące wielokrotne spacje, jedno słowo i pusty napis. Maksymalnie dwie litery.','Usuń puste słowa przed pobraniem pierwszych liter.',model+'\n'+helpers],
 ['Podaj inicjały dla Anna Nowak, kasia, Tomasz Wójcik i napisu z samych spacji.','Nazwa kasia pochodzi z loginu po zastosowaniu fallbacku.','Anna Nowak: AN. kasia: K. Tomasz Wójcik: TW. Same spacje: pusty napis. Inicjały nie są identyfikatorem rekordu.'],
 ['Zaprojektuj Avatar z inicjałami jako dekorację obok pełnej nazwy w komórce.','Powtórzonej dekoracyjnej informacji nie trzeba czytać drugi raz.',model+'\n'+helpers+'\nexport function Avatar({name}:{name:string}){return <span className="avatar" aria-hidden="true">{initials(name)}</span>;}'],
 ['Zbuduj UserRow: nazwa z fallbackiem, login, RoleBadge i czytelny status. Nieaktywny ma opacity 0.5 i dopisek.','Wylicz name raz i przekazuj go do Avatar.',dashboard],
 ['Napisz UserTable z nagłówkami i stabilnym key na UserRow. Dla pustej listy zwróć komunikat.','Key należy do bezpośrednich elementów zwracanych przez map.',dashboard],
 ['Wyjaśnij, dlaczego key={user.id} nie zastępuje user przekazanego do UserRow.','Key i dane domenowe służą innym celom.','React wykorzystuje key do identyfikacji rodzeństwa. UserRow otrzymuje rekord przez prop user; key nie trafia do zwykłych props. Jeśli potrzebuje samego id, trzeba przekazać osobny prop.'],
 ['Zbuduj Panel z title, children i opcjonalnym actions. Slot 0 ma być zachowany, null pominięty.','Sprawdzaj null i undefined jawnie.','import type {ReactNode} from "react";\n'+panel],
 ['Złóż UsersPage z dwoma Panelami: statystyki i lista z przyciskiem +Zaproś.','Akcja jest przekazywana do nagłówka jako ReactNode.',dashboard],
 ['Podłącz +Zaproś do callbacku onInvite; kliknięcie ma wywołać go raz, render ani razu.','Przekaż funkcję, nie wynik jej wywołania.',dashboard],
 ['Przygotuj pełny przykład strony z danymi bossa i callbackiem zaproszenia.','Stan formularza zaproszenia nie jest wymagany w tym rozdziale.',dashboard+'\n'+sample+'\nexport function Example(){return <UsersPage users={users} onInvite={()=>console.log("Otwórz zaproszenie")}/>;}'],
 ['Zapisz macierz testów pustego dashboardu: liczniki, tabela i przycisk.','Brak rekordów nie usuwa statystyk ani możliwości zaproszenia.','Oczekuj liczników 0/0/0, komunikatu Brak użytkowników i przycisku +Zaproś. Nie powinno być wierszy danych. Kliknięcie wywołuje onInvite.'],
 ['Przetestuj nieaktywnego administratora: jakie dwa liczniki zwiększy, jakie style i tekst zobaczysz?','Rola i aktywność są niezależnymi wymiarami.','Jeden nieaktywny admin: total=1, active=0, admins=1. Badge danger z admin, opacity 0.5, dopisek (nieaktywny) i status Nieaktywny.'],
 ['Przygotuj plan sprawdzenia, że dashboard nie mutuje danych.','Zamroź zarówno tablicę, jak i każdy rekord.','Zamroź rekordy i tablicę przez Object.freeze, zapisz kopię danych, wyrenderuj dwukrotnie z tymi samymi props i porównaj wynik oraz wejście. Sprawdź, że kolejność id pozostała ta sama. W renderze nie wolno dopisywać użytkownika ani zmieniać active.'],
 ['Popraw sortowanie użytkowników po loginie, aby nie zmieniało props.','Skopiuj kontener przed sort. Zakładamy zwykłe loginy łacińskie.',model+'\nexport function sortedUsers(users:readonly User[]){return [...users].sort((a,b)=>a.login.localeCompare(b.login,"pl"));}'],
 ['Zbuduj wyłącznie aktywny podgląd listy, pozostawiając oryginalny dashboard bez zmian.','Filtrowanie tworzy nową tablicę; nie usuwa danych źródłowych.',dashboard+'\nexport function ActiveUsers({users}:{users:readonly User[]}){return <UserTable users={users.filter(user=>user.active)}/>;}'],
 ['Napisz JSX/JSDoc wersję StatCard z liczbową wartością, bez składni TS w pliku JSX.','Typ props zapisz w komentarzu JSDoc.','/** @param {{label:string,value:number}} props */\nexport function StatCard({label,value}){return <article><h3>{label}</h3><strong>{value}</strong></article>;}'],
 ['Wykaż błędem kompilacji, że UsersPage potrzebuje onInvite będącego funkcją.','Użyj @ts-expect-error nad świadomie błędnym JSX.',dashboard+'\n// @ts-expect-error Callback musi być funkcją\nconst invalid=<UsersPage users={[]} onInvite="invite"/>;'],
 ['Zaprojektuj test stabilności kluczy po usunięciu Piotra z listy.','Porównaj tożsamość pozostałych rekordów, nie indeksy.','Przed usunięciem klucze: 1,2,3,4,5. Po: 1,3,4,5. Kasia zachowuje key=3 mimo przesunięcia. W teście interaktywnym sprawdź, że ewentualny lokalny stan wiersza pozostaje przy właściwym użytkowniku.'],
 ['Podziel finalny dashboard na moduły, nie tworząc cyklicznych zależności.','Dane i typy nie powinny importować komponentów.','types.ts: Role i User; user-utils.ts: displayName, initials, summarize; Badge.tsx: ogólny wygląd; RoleBadge.tsx: mapowanie; Panel.tsx: kompozycja; StatsRow.tsx i StatCard.tsx: statystyki; UserTable.tsx, UserRow.tsx, Avatar.tsx: lista; UsersPage.tsx: złożenie. Typy importuj przez import type.'],
 ['Oddaj kompletne TSX rozwiązanie bossa: pięć rekordów, statystyki, role, fallback, nieaktywni, dwa Panele i callback.','Rozdziel odpowiedzialności komponentów i zachowaj czyste renderowanie.',dashboard+'\n'+sample+'\nexport function App(){return <UsersPage users={users} onInvite={()=>console.log("Zaproś użytkownika")}/>;}'],
];
// Każdy wiersz: pytanie | poprawna | trzy dystraktory | objaśnienie.
const quiz = `
Ile wszystkich kont ma zestaw bossa?|5|4|3|1|Lista wejściowa zawiera pięć rekordów.
Ile kont jest aktywnych w zestawie bossa?|4|5|3|0|Tylko Tomasz jest nieaktywny.
Ilu administratorów ma zestaw bossa?|1|0|3|4|Tylko Anna ma rolę admin.
Jaki login ma użytkownik bez name?|kasia|anna|tomek|ola|Rekord Kasi ma name=null.
Co wyświetli displayName dla Kasi?|kasia|null|undefined|Brak danych|Operator ?? wybiera login przy name=null.
Kto ma nieaktywny wiersz?|Tomasz Wójcik|Anna Nowak|Piotr Zieliński|Aleksandra Lis|Tomasz ma active=false.
Jaki tone dostanie admin?|danger|info|neutral|success|Mapowanie domenowe przypisuje adminowi danger.
Jaki tone dostanie agent?|info|danger|neutral|warning|Agent ma niebieski wariant info.
Jaki tone dostanie viewer?|neutral|danger|info|success|Viewer ma szary wariant neutral.
Jakie inicjały ma Anna Nowak?|AN|AA|NA|AW|Bierzemy pierwsze litery pierwszych dwóch słów.
Jakie inicjały otrzyma kasia po fallbacku?|K|KA|NULL|KS|Login ma jedno słowo, więc inicjał jest jeden.
Jakie inicjały ma Tomasz Wójcik?|TW|WT|TO|WO|Kolejność liter odpowiada kolejności słów.
Jakie są liczniki pustej listy?|0/0/0|1/0/0|0/1/0|Brak liczników|Długość pustej tablicy i wyniki filtrów są zerowe.
Czy pusty dashboard zachowuje przycisk zaproszenia?|Tak, akcja jest w nagłówku Panelu|Nie, przycisk należy do pierwszego wiersza|Tylko dla admina|Tylko przy pięciu kontach|Pusty stan dotyczy listy, a nie możliwości dodania konta.
Jaki komunikat zastąpi pustą tabelę w rozwiązaniu?|Brak użytkowników|Brak ticketów|Ładowanie danych|Nie znaleziono admina|Pusta lista ma jawny komunikat domenowy.
Jaka wartość opacity oznacza nieaktywny wiersz?|0.5|50|0|1.5|Opacity jest bezwymiarową liczbą; 0.5 to połowa krycia.
Jaki dopisek otrzyma nieaktywna nazwa?|(nieaktywny)|(admin)|(usunięty)|(zablokowany na zawsze)|Active=false uzasadnia wyłącznie informację o nieaktywności.
Czy nieaktywny admin zwiększa licznik admins?|Tak|Nie|Tylko z name|Tylko na pierwszej pozycji|Licznik admins opiera się na roli, nie aktywności.
Jakie liczniki da jeden nieaktywny admin?|1/0/1|1/1/1|0/0/1|1/0/0|Total liczy rekord, active go pomija, admins go uwzględnia.
Co zmieni przełączenie Tomasza na active=true w danych wejściowych?|Active wzrośnie do 5|Total wzrośnie do 6|Admins wzrośnie do 2|Zniknie konto Kasi|Zmienia się tylko kryterium aktywności.
Co zmieni rola Oli z agent na admin?|Admins wzrośnie do 2|Total spadnie do 4|Active spadnie do 3|Login przestanie być potrzebny|Rola wpływa na licznik administratorów i badge.
Co da usunięcie Anny z danych bossa?|4/3/0|4/4/1|5/3/0|4/3/1|Usuwamy jedno aktywne konto i jedynego administratora.
Co da usunięcie Tomasza z danych bossa?|4/4/1|4/3/1|5/4/0|4/4/0|Usunięcie nieaktywnego nie zmniejsza active.
Co da lista samych trzech aktywnych agentów?|3/3/0|3/0/3|3/3/3|0/3/0|Żaden agent nie jest adminem.
Co da name="" w displayName używającym ???|Pusty napis|Login|Null|Błąd Reacta|Pusty napis nie jest nullish.
Dlaczego logiczna alternatywa name z login zmienia kontrakt fallbacku?|Zastąpi też pusty napis|Nie obsługuje null|Zawsze zwraca name|Modyfikuje login|Logiczna alternatywa wybiera prawą stronę dla wszystkich wartości falsy.
Który typ poprawnie opisuje name?|string lub null|string wyłącznie|number lub null|boolean|Domena dopuszcza brak imienia jako null.
Który typ poprawnie opisuje active?|boolean|string|number|ReactNode|Aktywność jest wartością logiczną.
Który typ poprawnie opisuje role?|Unia admin, agent, viewer|Dowolny number|Wyłącznie admin|Tablica elementów JSX|Unia ogranicza role do obsługiwanych przypadków.
Co zapewnia Record<Role,Tone> dla mapy kolorów?|Wymaga wpisu dla każdej roli|Zamraża obiekt w runtime|Tworzy CSS|Pobiera role z serwera|Record sprawdza strukturę mapy podczas kompilacji.
Co się stanie po dodaniu owner do Role bez mapowania?|TypeScript zgłosi brak wpisu|React wybierze danger|Mapa sama się uzupełni|Znikną wszystkie role|Pełna mapa Record wymaga nowego klucza.
Który komponent powinien znać ROLE_TONE?|RoleBadge|Ogólny Panel|Ogólny StatCard|Dekoracyjny Avatar|Opakowanie domenowe tłumaczy rolę na wygląd Badge.
Co powinien wiedzieć ogólny Badge?|Tone i children|Pełną bazę użytkowników|Liczbę administratorów|Sposób zapraszania|Badge jest elementem prezentacyjnym.
Po co jeden StatCard użyty trzy razy?|Wspólny wygląd i kontrakt|Aby wszystkie wartości były równe|Aby ukryć zero|Aby mutować liczniki|Ten sam komponent może otrzymać różne dane.
Jaki typ ma value w StatCard?|number|boolean|Role|User[]|Statystyka w tym zadaniu jest liczbą.
Jak renderować value=0 w StatCard?|Bezpośrednio jako value|Przez value && strong|Zastępując zero jedynką|Pomijając cały kafel|Zero jest prawidłowym wynikiem statystyki.
Co zwróci users.filter(u=>u.active).length?|Liczbę aktywnych kont|Liczbę wszystkich ról|Pierwszego aktywnego|Tablicę loginów|Filter wybiera aktywne rekordy, length je liczy.
Czy filter zmienia wejściową tablicę?|Nie|Tak, usuwa odrzuconych|Tak, sortuje rekordy|Tylko gdy jest readonly|Filter tworzy nowy kontener z wybranymi referencjami.
Czy users.sort w renderze jest bezpieczne dla props?|Nie, zmienia tablicę wejściową|Tak, bo zwraca tablicę|Tak, gdy id są liczbami|Tylko dla adminów|Sort działa w miejscu; potrzebna jest kopia lub toSorted.
Jak bezpiecznie sortować bez zmiany kontenera wejściowego?|Skopiować tablicę przed sort|Nadać jej inną nazwę|Zmienić key na indeks|Wywołać sort dwa razy|Nowy kontener odseparowuje zmianę kolejności.
Czy kopia [...users] kopiuje głęboko rekordy?|Nie|Tak, zawsze|Tylko rekordy aktywne|Tylko pola string|Spread tablicy zachowuje referencje do obiektów.
Czy readonly User[] czyni pole active niemutowalnym?|Nie, samo readonly dotyczy kontenera|Tak, głęboko|Tylko przy null|Tylko dla admina|Głęboką niemutowalność trzeba modelować osobno.
Czy kontrakt React pozwala mutować user.active z props?|Nie|Tak, jeśli TS pozwala|Tak, w map|Tak, dla nieaktywnych|Props traktujemy jako niezmienne wejście niezależnie od ograniczeń TS.
Jak przekazać akcję do nagłówka Panelu?|Przez prop actions z JSX|Przez key przycisku|Przez mutację title|Przez globalną tablicę|Slot pozwala komponować dowolny element akcji.
Jak przekazać zawartość Panelu?|Przez children|Przez key|Przez tone|Przez id użytkownika|Zagnieżdżony JSX staje się children.
Jaki typ obsłuży JSX w actions?|ReactNode|Wyłącznie string|Role|number[] wyłącznie|ReactNode obejmuje typowe wartości renderowalne.
Czy actions=0 ma zniknąć w przyjętym Panelu?|Nie|Tak, zawsze|Tylko gdy title puste|Tylko w TSX|Warunek pomija wyłącznie null i undefined.
Czy actions=null tworzy kontener akcji w rozwiązaniu?|Nie|Tak, z napisem null|Tak, z zerem|Tylko przy pustej liście|Jawna kontrola nullish pomija ten slot.
Czy actions=false przejdzie kontrolę różną od null i undefined?|Tak, ale treść false nie jest widoczna|Nie|Wywoła błąd|Zmieni się w napis false|Warunek sprawdza obecność wartości nullish, nie widoczność wszystkich ReactNode.
Ile Paneli wymaga boss?|2|1|3|5|Osobne Panele grupują statystyki i tabelę.
Gdzie należy +Zaproś w strukturze bossa?|W actions Panelu listy|W każdym wierszu|W każdym StatCard|W RoleBadge|Zapraszanie dotyczy listy użytkowników.
Jaki type powinien mieć zwykły przycisk zaproszenia?|button|submit bez względu na kontekst|reset|invite|Button zapobiega przypadkowemu submitowi po umieszczeniu w formularzu.
Jaki typ ma onInvite?|Funkcja bez argumentów zwracająca void|String|User|Role[]|Komponent zgłasza zamiar zaproszenia przez callback.
Co przekazać do onClick, by nie uruchomić akcji podczas renderu?|Referencję onInvite|Wynik onInvite()|Tekst onInvite|Licznik active|Handler powinien być funkcją wywołaną przy zdarzeniu.
Ile razy powinien wykonać się onInvite podczas samego renderu?|0|1|2|Tyle, ilu jest użytkowników|Render opisuje interfejs i nie wykonuje akcji użytkownika.
Czy sam callback otworzy modal, jeśli rodzic nic takiego nie implementuje?|Nie|Tak, React robi to automatycznie|Tylko w TSX|Tylko dla admina|Skutek działania zależy od kodu rodzica.
Co powinien robić UserRow?|Renderować pojedynczego użytkownika|Pobierać całą bazę|Liczyć wszystkie statystyki|Tworzyć nowe id przy renderze|Wiersz ma ograniczoną odpowiedzialność.
Co powinien robić UserTable?|Obsłużyć pusty stan i mapowanie wierszy|Zmieniac role w props|Generować losowe klucze|Przechowywać globalne loginy|Tabela odpowiada za strukturę listy.
Gdzie umieścić key przy users.map(user=><UserRow .../>)?|Na UserRow|Na span wewnątrz Badge|Na h1 strony|Tylko na tbody|Key jest potrzebny na bezpośrednio mapowanym elemencie.
Jaki klucz wybrać dla użytkownika?|user.id|Indeks aktualnej pozycji|Math.random()|Inicjały|Stałe id odzwierciedla tożsamość rekordu.
Czy key dociera jako zwykły prop do UserRow?|Nie|Tak|Tylko gdy jest liczbą|Tylko w trybie produkcyjnym|Key jest specjalnym polem obsługiwanym przez React.
Jakie klucze pozostają po usunięciu Piotra?|1,3,4,5|1,2,3,4|0,1,2,3|2,3,4,5|Pozostałe rekordy zachowują swoje id.
Jaki key zachowa Kasia po przesunięciu na drugą pozycję?|3|2|1|Nowy losowy|Pozycja nie zmienia tożsamości rekordu.
Dlaczego inicjały nie są dobrym key?|Mogą się powtarzać|Są zawsze liczbami|Nie mogą być tekstem|React zabrania liter|Różni użytkownicy mogą mieć te same inicjały.
Dlaczego login jest słabszy od stałego id, jeśli można go edytować?|Zmiana loginu zmieniłaby klucz|Login jest zawsze pusty|React nie przyjmuje napisów|Login nigdy nie jest unikalny|Key powinien zachowywać stabilność w czasie.
Czy key musi być unikalny w całej aplikacji?|Nie, wśród danego rodzeństwa|Tak, globalnie|Tylko w CSS|Tylko między rozdziałami|Niezależne listy mogą używać tych samych wartości.
Czy key renderuje się jako atrybut DOM?|Nie|Tak, zawsze|Tylko dla span|Tylko przy id liczbowym|Key służy Reactowi i nie jest zwykłym atrybutem.
Jak przekazać id osobno, jeśli wiersz potrzebuje go jako prop?|Dodać jawny prop id|Odczytać props.key|Odczytać CSS|Wziąć indeks DOM|Dane potrzebne komponentowi przekazuje się jawnie.
Co daje scope="col" w th?|Wskazuje nagłówek kolumny|Sortuje kolumnę|Nadaje key|Ukrywa tabelę|Semantyka pomaga powiązać nagłówki z danymi.
Które elementy zawierają komórki danych wiersza?|td|th wyłącznie|li|article wyłącznie|Dane tabelaryczne trafiają do td w tr.
Czy Avatar obok pełnej nazwy może być dekoracyjny?|Tak, z aria-hidden=true|Nie, zawsze musi mieć drugi pełny opis|Tylko w pustej tabeli|Tylko dla viewer|Pełna nazwa już przekazuje tę samą informację.
Co znaczy aria-hidden na dekoracyjnym Avatarze?|Pomija go w drzewie dostępności|Ukrywa go wizualnie|Usuwa użytkownika z tabeli|Wyłącza konto|Nie jest to reguła CSS ani stan domenowy.
Czy sam kolor wystarczy do przedstawienia roli?|Nie, badge zawiera też tekst roli|Tak, zawsze|Tylko czerwony|Tylko na desktopie|Tekst zachowuje informację niezależnie od rozpoznania koloru.
Czy samo opacity wystarczy do opisania nieaktywności?|Nie, potrzebny jest też tekst|Tak, zawsze|Tylko przy 0.5|Tylko w TSX|Dopisek i status przekazują znaczenie zmiany wyglądu.
Czy opacity:0.5 wymaga dopisania px?|Nie|Tak|Tylko w React 19|Tylko dla tr|Opacity jest właściwością bez jednostki.
Co zrobić z samymi spacjami w initials?|Zwrócić pusty napis|Zwrócić undefined|Wywołać mutację użytkownika|Użyć losowych liter|Trim i odfiltrowanie pustych słów dają pusty wynik.
Jak traktuje wiele spacji split po wyrażeniu /\\s+/ po trim?|Jako separator słów|Jako wiele widocznych inicjałów|Jak null|Jak błąd TS|Wyrażenie dopasowuje jeden lub więcej białych znaków.
Po co slice(0,2) przy inicjałach?|Ogranicza wynik do dwóch słów|Zmienia nazwę użytkownika|Pomija pierwsze dwa słowa|Sortuje litery|Avatar ma pokazywać najwyżej dwa inicjały.
Czy displayName powinno zapisywać fallback do user.name?|Nie, wystarczy obliczyć wynik|Tak, zawsze|Tylko w renderze tabeli|Tylko dla Kasi|Wyliczenie prezentacji nie powinno mutować wejścia.
Czy summary powinno być globalnym licznikiem zwiększanym przy renderze?|Nie|Tak|Tylko dla adminów|Tylko poza StrictMode|Wynik powinien zależeć od bieżących props, nie liczby renderów.
Czy dwa rendery tych samych danych mają produkować równoważny JSX?|Tak|Nie, muszą zmieniać statystyki|Tylko pierwszy|Tylko przy pustej liście|Czysty komponent jest przewidywalny dla tych samych wejść.
Czy równoważny JSX musi być tym samym obiektem w pamięci?|Nie|Tak|Tylko przy jednym wierszu|Tylko w TSX|Nowe elementy mogą opisywać ten sam wynik interfejsu.
Jak sprawdzić mutację rekordów testem?|Zamrozić tablicę i rekordy|Zamrozić tylko nazwę zmiennej|Dodać losowe key|Wyłączyć TypeScript|Zamrożenie obu poziomów wykrywa więcej przypadkowych zapisów.
Czy Object.freeze samej tablicy głęboko zamraża użytkowników?|Nie|Tak|Tylko adminów|Tylko obiekty z null|Freeze jest płytkie.
Czy snapshot HTML dowodzi zachowania lokalnego stanu po zmianie kolejności?|Nie|Tak, zawsze|Tylko przy pięciu wierszach|Tylko bez JSX|Zachowanie stanu wymaga testu interaktywnego z aktualizacją drzewa.
Jakie dane najlepiej obejmuje test fallbacku?|name=null oraz name=""|Tylko pełne imię|Tylko role admin|Tylko aktywne konta|Te przypadki odróżniają operator nullish od logicznej alternatywy.
Jakie dane najlepiej obejmuje test aktywności?|Aktywne i nieaktywne konto|Tylko Anna|Wyłącznie pusta lista|Tylko agenci|Potrzebne są obie gałęzie warunku.
Jakie dane najlepiej obejmuje test RoleBadge?|Każdą z trzech ról|Tylko admin|Tylko brak roli|Losowy napis|Pełna macierz sprawdza wszystkie mapowania.
Co powinien testować pusty stan poza komunikatem?|Widoczność zer i akcji zaproszenia|Tylko kolor tła|Losowe id|Kolejność nieistniejących kont|Pusty stan dotyczy całej kompozycji, nie jednego napisu.
Czy trzeba użyć useState do obliczenia stats z props?|Nie|Tak, trzy razy|Tylko dla total|Tylko w TSX|Wartości pochodne można policzyć podczas renderu.
Czy boss wymaga pobrania danych przez fetch?|Nie|Tak, do każdego wiersza|Tylko dla nieaktywnych|Tylko w Badge|Dane są przekazywane przez props w zakresie tego rozdziału.
Czy boss wymaga prawdziwej autoryzacji na podstawie roli?|Nie, pokazuje role|Tak, Badge ją zapewnia|Tak, CSS wystarcza|Tak, key jest tokenem|Wyświetlanie etykiety nie implementuje kontroli dostępu.
Co należy importować przez import type w TSX?|ReactNode i typy domenowe|Callback wykonywany przy kliknięciu|Komponent Panel|Tablicę danych runtime|Typy nie są wartościami wykonywanymi w przeglądarce.
Czy plik JSX może zawierać :User[] w deklaracji zmiennej?|Nie|Tak, bez konfiguracji|Tylko przy pustej tablicy|Tylko dla admina|Adnotacje składni TS należą do TS/TSX; w JSX używa się np. JSDoc.
Czy JSDoc może opisać props StatCard w JSX?|Tak|Nie|Tylko po zmianie rozszerzenia na JSON|Tylko bez children|Komentarz JSDoc pozwala narzędziom analizować typy JavaScriptu.
Dlaczego UserRow nie powinien importować UsersPage?|Grozi odwróceniem zależności i cyklem|React zabrania nazw Page|Role przestaną być tekstem|Zmieni to id|Strona składa mniejsze komponenty, które nie muszą znać strony.
Gdzie umieścić funkcję summarize wielokrotnego użytku?|W module funkcji operujących na danych|W CSS|W kluczu elementu|W callbacku onInvite wyłącznie|Funkcja nie potrzebuje zależności od komponentów.
Czy UserTable może być użyte w ActiveUsers z przefiltrowanymi danymi?|Tak|Nie, zna wyłącznie dane bossa|Tylko z mutacją oryginału|Tylko po zmianie ról|Komponent dostaje listę przez props i pozostaje wielokrotnego użytku.
Czy liczba aktywnych kont może przekroczyć total przy poprawnym summarize?|Nie|Tak, gdy admin jest aktywny|Tak, przy trzech agentach|Tak, dla pustej listy|Aktywni są podzbiorem wszystkich rekordów wejściowych.
Co oznacza ukończenie bossa dla podanych danych?|5/4/1, poprawne role, fallback i nieaktywny Tomasz|Tylko poprawny nagłówek|Wyłącznie brak błędów składni|Trzy identyczne StatCard z wartością 5|Gotowy ekran spełnia jednocześnie wymagania danych i kompozycji.
`.trim().split('\n').map(line=>line.split('|'));
module.exports={exercises,quiz,dashboard,model,sample};
