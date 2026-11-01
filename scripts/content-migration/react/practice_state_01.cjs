const row='type Ticket={id:number;title:string;status:"OPEN"|"CLOSED"};\ntype RowProps={ticket:Ticket;onSelect:(id:number)=>void;onClose:(id:number)=>void};\nexport function TicketRow({ticket,onSelect,onClose}:RowProps){return <tr onClick={()=>onSelect(ticket.id)}><td>{ticket.id}</td><td><button type="button" onClick={event=>{event.stopPropagation();onSelect(ticket.id);}}>{ticket.title}</button></td><td><button type="button" disabled={ticket.status==="CLOSED"} onClick={event=>{event.stopPropagation();onClose(ticket.id);}}>Zamknij</button></td></tr>;}';
const table=row+'\nexport function TicketTable({tickets,onSelect,onClose}:{tickets:readonly Ticket[];onSelect:(id:number)=>void;onClose:(id:number)=>void}){return <table><thead><tr><th scope="col">Id</th><th scope="col">Tytuł</th><th scope="col">Akcje</th></tr></thead><tbody>{tickets.length===0?<tr><td colSpan={3}>Brak ticketów</td></tr>:tickets.map(ticket=><TicketRow key={ticket.id} ticket={ticket} onSelect={onSelect} onClose={onClose}/>)}</tbody></table>;}';
const search='import type {KeyboardEvent} from "react";\nexport function Search({onQuery,onClear}:{onQuery:(query:string)=>void;onClear:()=>void}){function handleKeyDown(event:KeyboardEvent<HTMLInputElement>){if(event.key==="Escape"&&!event.nativeEvent.isComposing)onClear();}return <label>Szukaj<input type="search" onChange={event=>onQuery(event.currentTarget.value)} onKeyDown={handleKeyDown}/></label>;}';
const form='import type {FormEvent} from "react";\nexport function CreateForm({onCreate}:{onCreate:(title:string)=>void}){function handleSubmit(event:FormEvent<HTMLFormElement>){event.preventDefault();const data=new FormData(event.currentTarget);const value=data.get("title");if(typeof value==="string"&&value.trim())onCreate(value.trim());}return <form onSubmit={handleSubmit}><label>Tytuł<input name="title" required/></label><button type="submit">Dodaj</button></form>;}';
const copy='export function CopyButton({url,onCopied,onError}:{url:string;onCopied:()=>void;onError:(message:string)=>void}){async function handleCopy(){try{await navigator.clipboard.writeText(url);onCopied();}catch{onError("Nie udało się skopiować linku");}}return <button type="button" onClick={handleCopy}>Kopiuj link</button>;}';
const exercises=[
 ['Napisz przycisk wywołujący onExport dopiero po kliknięciu.','Przekaż funkcję, nie wynik.','export function ExportButton({onExport}:{onExport:()=>void}){return <button type="button" onClick={onExport}>Eksport CSV</button>;}'],
 ['Przekaż id=42 do onSelect przez lambdę.','Lambda odkłada wywołanie do zdarzenia.','export function SelectButton({onSelect}:{onSelect:(id:number)=>void}){return <button type="button" onClick={()=>onSelect(42)}>Wybierz 42</button>;}'],
 ['Popraw onClick={console.log("Edytuj",id)}.','Log nie powinien wykonywać się w renderze.','export function EditButton({id}:{id:number}){return <button type="button" onClick={()=>console.log("Edytuj",id)}>Edytuj</button>;}'],
 ['Nazwij handler handleClose i prop onClose, przekazując id.','Konwencja nazewnictwa pomaga rozpoznać odpowiedzialność.','export function CloseButton({id,onClose}:{id:number;onClose:(id:number)=>void}){function handleClose(){onClose(id);}return <button type="button" onClick={handleClose}>Zamknij</button>;}'],
 ['Napisz TicketRow z kliknięciem wiersza i przyciskiem zamknięcia bez wyboru wiersza.','StopPropagation dotyczy propagacji, nie domyślnego submitu.',row],
 ['Zapewnij wybór ticketu klawiaturą przez prawdziwy przycisk z tytułem.','Nie zakładaj, że tr jest focusowalny.',row],
 ['Zapobiegaj podwójnemu onSelect po kliknięciu przycisku tytułu w klikalnym wierszu.','Zatrzymaj bubbling przed wywołaniem callbacku.',row],
 ['Wyłącz przycisk zamknięcia dla CLOSED przez natywny disabled.','Sam kolor nie wystarczy.',row],
 ['Przekaż onSelect i onClose przez TicketTable do każdego wiersza.','Tabela nie powinna zmieniać statusu props.',table],
 ['Zbuduj Search zgłaszający tekst i Escape do rodzica, bez własnego stanu.','Aktualną wartość inputa odczytaj przez currentTarget.',search],
 ['Wyodrębnij handler onChange z typem ChangeEvent<HTMLInputElement>.','Typ zdarzenia importuj z react.','import type {ChangeEvent} from "react";\nexport function Input({onValue}:{onValue:(value:string)=>void}){function handleChange(event:ChangeEvent<HTMLInputElement>){onValue(event.currentTarget.value);}return <input aria-label="Wartość" onChange={handleChange}/>;}'],
 ['Wyodrębnij handler przycisku z MouseEvent<HTMLButtonElement>.','Nie używaj generyka na globalnym DOM MouseEvent.','import type {MouseEvent} from "react";\nexport function Action(){function handleClick(event:MouseEvent<HTMLButtonElement>){console.log(event.currentTarget.type);}return <button type="button" onClick={handleClick}><span>Akcja</span></button>;}'],
 ['Wyjaśnij target i currentTarget przy kliknięciu span w button.','Punkt powstania i element handlera mogą się różnić.','Target może wskazywać span, currentTarget wskazuje button obsługujący handler. Dlatego dane samego przycisku odczytuj z currentTarget, zamiast zakładać, że target zawsze jest button.'],
 ['Zbuduj formularz z onSubmit, preventDefault i odczytem FormData.','OnSubmit obsłuży również odpowiedni submit klawiaturą.',form],
 ['Wyjaśnij, dlaczego stopPropagation nie zastępuje preventDefault w formularzu.','To dwa różne mechanizmy.','StopPropagation zatrzymuje dalszą propagację zdarzenia. PreventDefault blokuje domyślną akcję, jeśli zdarzenie jest anulowalne. Zatrzymanie bubbling nie jest samo w sobie anulowaniem wysłania formularza.'],
 ['Zapisz link z warunkowym anulowaniem nawigacji.','Prawdziwa nawigacja powinna zachować href.','export function TicketLink({href,blocked,onBlocked}:{href:string;blocked:boolean;onBlocked:()=>void}){return <a href={href} onClick={event=>{if(blocked){event.preventDefault();onBlocked();}}}>Otwórz ticket</a>;}'],
 ['Napisz obsługę Enter w polu tekstowym bez reakcji podczas kompozycji IME.','Sprawdź nativeEvent.isComposing.','export function QuickSearch({onSearch}:{onSearch:(query:string)=>void}){return <input aria-label="Szukaj" onKeyDown={event=>{if(event.key==="Enter"&&!event.nativeEvent.isComposing){event.preventDefault();onSearch(event.currentTarget.value);}}}/>;}'],
 ['Odczytaj checkbox przez checked, nie value.','Kontrakt callbacku powinien przyjąć boolean.','export function ActiveFilter({onChange}:{onChange:(checked:boolean)=>void}){return <label><input type="checkbox" onChange={event=>onChange(event.currentTarget.checked)}/>Tylko aktywne</label>;}'],
 ['Odczytaj select jako tekst i przekaż wybraną wartość.','Wartość DOM nie jest automatycznie unią domenową.','export function RoleFilter({onChange}:{onChange:(role:string)=>void}){return <label>Rola<select onChange={event=>onChange(event.currentTarget.value)}><option value="all">Wszystkie</option><option value="admin">Admin</option></select></label>;}'],
 ['Zamień dataset.id na liczbę po sprawdzeniu, że to niepusty dodatni bezpieczny integer.','Dataset przechowuje tekst lub undefined.','export function DeleteButton({id,onDelete}:{id:number;onDelete:(id:number)=>void}){return <button type="button" data-id={id} onClick={event=>{const raw=event.currentTarget.dataset.id;if(!raw?.trim())return;const parsed=Number(raw);if(Number.isSafeInteger(parsed)&&parsed>0)onDelete(parsed);}}>Usuń</button>;}'],
 ['Napisz CopyButton z poprawną obsługą odrzuconego Promise schowka.','Nie zakładaj zawsze dostępnego Clipboard API.',copy],
 ['Przechwyć wartość inputa przed await zamiast później odczytywać currentTarget.','CurrentTarget opisuje aktywną obsługę zdarzenia.','export function AsyncInput({onCheck}:{onCheck:(value:string)=>Promise<void>}){return <input aria-label="Sprawdź wartość" onBlur={async event=>{const value=event.currentTarget.value;await onCheck(value);}}/>;}'],
 ['Wyjaśnij, czy współczesny React DOM wymaga persist dla każdego odroczonego odczytu zdarzenia.','Pooling i czas życia currentTarget to różne zagadnienia.','Współczesny React DOM nie stosuje starego poolingu wymagającego persist. Nadal warto skopiować potrzebne wartości przed await, ponieważ currentTarget jest związany z wykonywaniem handlera, a DOM może się zmienić.'],
 ['Zbuduj komponent akcji z domenowymi onArchive i onExport zamiast ujawniania zdarzeń DOM rodzicowi.','Callbacki komponentów można nazywać według sensu biznesowego.','export function Actions({id,onArchive,onExport}:{id:number;onArchive:(id:number)=>void;onExport:()=>void}){return <div><button type="button" onClick={()=>onArchive(id)}>Archiwizuj</button><button type="button" onClick={onExport}>Eksportuj</button></div>;}'],
 ['Zaprojektuj test capture/bubble z rodzicem i dzieckiem zatrzymującym propagację.','Stop w bubble nie cofa handlera capture, który już wykonał się wcześniej.','Oczekuj najpierw capture rodzica, potem handlera dziecka. Gdy dziecko zatrzyma propagację, bubble rodzica nie powinien wykonać się w tym scenariuszu. Sprawdź to rzeczywistym zdarzeniem w DOM, nie samym ręcznym wywołaniem funkcji.'],
 ['Wykaż błędem TS, że callback onSelect otrzymuje number, nie Ticket.','Parametr onSelect nie ma title.',row+'\n// @ts-expect-error Parametr id jest liczbą\nconst invalid=<TicketRow ticket={{id:1,title:"API",status:"OPEN"}} onSelect={id=>console.log(id.title)} onClose={()=>{}}/>;'],
 ['Zapisz JSX/JSDoc przycisk zamknięcia z onClose(id).','Nie wstawiaj adnotacji TS do JSX.','/** @param {{id:number,onClose:(id:number)=>void}} props */\nexport function CloseButton({id,onClose}){return <button type="button" onClick={()=>onClose(id)}>Zamknij</button>;}'],
 ['Zaplanuj test disabled bez ręcznego wywoływania handlera.','Test musi respektować natywne zachowanie kontrolki.','Wyrenderuj CLOSED w przeglądarce, sprawdź disabled i spróbuj normalnej aktywacji użytkownika. OnClose nie powinien się wykonać. Ręczne props.onClick omija ten mechanizm i nie jest dowodem działania disabled.'],
 ['Zaplanuj test formularza: klik submit oraz Enter, pusty tekst i tekst ze spacjami.','Walidacja natywna może zablokować submit przed handlerem.','Dla poprawnej wartości sprawdź pojedynczy onCreate z trim. Sprawdź oba sposoby wysłania. Dla pustego required uwzględnij blokadę natywną; dodatkowo bezpośredni test logiki handlera powinien odrzucać pusty wynik trim. Nie oczekuj tego samego przebiegu zdarzeń przy nieważnym formularzu.'],
 ['Oddaj tabelę z semantycznym wyborem, zamykaniem, pustym stanem i callbackami rodzica.','Dane pozostają niezmienione; na razie akcje mogą logować zamiar.',table+'\nexport function Example(){return <TicketTable tickets={[{id:42,title:"API",status:"OPEN"},{id:43,title:"Eksport",status:"CLOSED"}]} onSelect={id=>console.log("wybrano",id)} onClose={id=>console.log("zamknij",id)}/>;}'],
];
const quiz=`
Jak zapisać obsługę kliknięcia w JSX?|onClick|onclick jako podstawowy prop React|on-click|clickHandler obowiązkowo|Props zdarzeń używają camelCase.
Co przekazać do onClick?|Funkcję|Wynik zwykłego wywołania handlera|Napis polecenia JS|Id elementu|React wywoła funkcję w odpowiedzi na zdarzenie.
Co robi onClick={handleClose()} podczas renderu?|Wywołuje handleClose od razu|Odkłada wywołanie do kliknięcia|Zawsze tworzy handler|Automatycznie anuluje bubbling|Nawiasy wykonują funkcję jako wyrażenie JSX.
Jak odłożyć onSelect(id) do kliknięcia?|Opakować w lambdę|Wykonać podczas renderu|Wpisać do key|Wpisać w className|Lambda przekazuje dane przy zdarzeniu.
Czy lambda inline jest sama w sobie błędem wydajności?|Nie|Tak zawsze|Tylko w TSX|Tylko w tabeli|To normalny zapis; optymalizacja wymaga uzasadnienia pomiarem.
Jak zwykle nazwać lokalny handler zamknięcia?|handleClose|useClose obowiązkowo|closeType|onCSS|Konwencja handle opisuje funkcję obsługi.
Jak zwykle nazwać prop powiadamiający o zamknięciu?|onClose|handleCSS|closeValue|key|Konwencja on opisuje callback komponentu.
Czy nazwy onArchive i onExport na własnym komponencie są dozwolone?|Tak|Nie|Tylko onClick|Tylko w TSX|API komponentu może opisywać zdarzenia domenowe.
Co oznacza event.target?|Miejsce powstania zdarzenia|Zawsze element z handlerem|Zawsze button|Zawsze input|Kliknięcie może powstać na potomku.
Co oznacza event.currentTarget w handlerze?|Element obsługujący dany handler|Zawsze najgłębszy potomek|Zawsze document|Id callbacku|To element, którego dane zwykle chcemy odczytać.
Przy kliknięciu span w button z handlerem na button currentTarget wskazuje co?|Button|Span zawsze|Body zawsze|Null zawsze w czasie handlera|Handler należy do przycisku.
Czy target zawsze ma typ HTMLButtonElement w handlerze przycisku?|Nie|Tak|Tylko w TSX|Tylko z MouseEvent|Zdarzenie może pochodzić z potomka.
Co robi stopPropagation?|Zatrzymuje dalszą propagację|Zawsze anuluje nawigację|Zawsze anuluje submit|Wyłącza przycisk|Propagacja jest odrębna od domyślnej akcji.
Co robi preventDefault?|Anuluje domyślną akcję anulowalnego zdarzenia|Zawsze zatrzymuje bubbling|Zmienia status ticketu|Usuwa handler|Działanie dotyczy domyślnej reakcji przeglądarki.
Czy preventDefault automatycznie blokuje handler rodzica?|Nie|Tak|Tylko w JSX|Tylko dla button|Bubbling wymaga osobnej decyzji.
Czy stopPropagation automatycznie blokuje wysłanie formularza?|Nie|Tak|Tylko w TSX|Tylko z FormEvent|Zatrzymanie propagacji nie zastępuje anulowania akcji.
Co może zrobić kliknięcie Zamknij bez stopPropagation w klikalnym wierszu?|Wywołać także wybór wiersza|Usunąć wszystkie tickety|Wyłączyć CSS|Zmienić key|Click może bąbelkować do tr.
Dlaczego przycisk tytułu w klikalnym wierszu także zatrzymuje bubbling?|By nie wykonać onSelect dwa razy|By zmienić id|By zablokować wszystkie kliknięcia|By usunąć tekst|Ten sam zamiar obsługuje już przycisk.
Czy tr z onClick jest domyślnie osiągalny przez Tab?|Nie|Tak|Tylko z className|Tylko z id|Ważna akcja potrzebuje dostępnej kontrolki.
Jak udostępnić wybór ticketu klawiaturze?|Dodać prawdziwy button lub link|Nadać wyłącznie kolor hover|Użyć losowego key|Zmienić title|Semantyczna kontrolka daje odpowiednie zachowanie.
Czy link i button mają dokładnie tę samą obsługę Spacji?|Nie|Tak|Tylko w TSX|Tylko w dark|Natywne zachowania zależą od rodzaju elementu.
Po co type=button w zwykłej akcji?|Unika przypadkowego submitu|Tworzy fokus|Zmienia rolę ticketu|Włącza bubbling|Przycisk w formularzu może domyślnie wysyłać formularz.
Co oznacza type=submit?|Przycisk wysyłający formularz|Zwykłą akcję bez formularza zawsze|Wyłączenie przycisku|Kasowanie pola|Submit jest odrębną semantyką.
Gdzie obsłużyć wysłanie całego formularza?|W onSubmit formularza|Wyłącznie w onClick jednego przycisku|W key inputa|W renderze|OnSubmit obejmuje również odpowiedni submit klawiaturą.
Co pobiera FormData(event.currentTarget) w handlerze formularza?|Dane kontrolek formularza|Wszystkie props React|Wyłącznie stan useState|CSS pól|FormData działa na rzeczywistym formularzu.
Czy input bez name trafi jako nazwane pole do FormData?|Nie|Tak|Tylko w TSX|Tylko dla required|Atrybut name identyfikuje pole formularza.
Jaki typ może mieć FormData.get?|String, plik lub null|Zawsze number|Zawsze boolean|Zawsze Ticket|Przed użyciem tekstu trzeba sprawdzić wartość.
Po co trim przy tytule?|Usuwa skrajne białe znaki|Usuwa wszystkie spacje w środku|Nadaje id|Zmienia string w number|Pomaga odrzucić wpis z samych spacji.
Czy required może zablokować submit przed handlerem?|Tak|Nie|Tylko w TSX|Tylko w React 19|Natywna walidacja wpływa na przebieg zdarzeń.
Który prop obsługuje zmianę tekstu inputa w typowym React?|onChange|onSubmit inputa|onCss|onValueDOM obowiązkowo|React udostępnia onChange dla bieżących zmian pola.
Co odczytać dla tekstu inputa?|currentTarget.value|currentTarget.checked jako tekst|event.key|event.type jako zawartość|Value zawiera tekst kontrolki.
Co odczytać dla checkboxa jako boolean?|currentTarget.checked|currentTarget.value jako boolean|event.key|event.type|Checked odzwierciedla zaznaczenie.
Czy value checkboxa i checked to to samo?|Nie|Tak|Tylko w TSX|Tylko dla label|Value jest wartością tekstową, checked stanem logicznym.
Jak odczytać wybraną wartość select?|currentTarget.value|currentTarget.checked|event.key|event.nativeEvent jako string|Wartość select jest tekstem.
Czy tekst select automatycznie staje się dowolną unią domenową TS?|Nie|Tak|Tylko w strict|Tylko dla role|Może wymagać walidacji lub zawężenia.
Jaki prop obsługuje klawiaturę przed domyślną reakcją?|onKeyDown|onMouseEnter|onSubmit na input|onCss|Keydown pozwala rozpoznać klawisz.
Jak rozpoznać Escape?|event.key === Escape jako napis|event.value === Escape|event.type === Escape zawsze|event.id === Escape|Key opisuje użyty klawisz.
Po co sprawdzać nativeEvent.isComposing przy Enter?|By nie przerywać kompozycji IME|By wykryć klik myszy|By sprawdzić rolę|By wygenerować id|Enter może być częścią wprowadzania tekstu.
Czy Escape w dziecku zawsze powinno zatrzymywać propagację?|Nie, zależy od projektu|Tak zawsze|Tylko w TSX|Nigdy|Decyzja zależy od współpracy kontrolek i rodziców.
Skąd importować React MouseEvent<HTMLButtonElement>?|Z react|Z globalnego DOM jako generyk|Z clsx|Z pliku CSS|React ma własne typy zdarzeń.
Czy globalny DOM MouseEvent jest tym samym typem co React MouseEvent?|Nie|Tak|Tylko w TSX|Tylko dla span|To odrębne modele typów.
Jaki typ opisuje zmianę inputa w React?|ChangeEvent<HTMLInputElement>|KeyboardEvent<HTMLTableElement>|FormEvent<HTMLButtonElement> zawsze|CSSProperties|Generyk wskazuje element kontrolki.
Jaki typ opisuje submit formularza?|FormEvent<HTMLFormElement>|MouseEvent<HTMLElement> wyłącznie|ChangeEvent<HTMLInputElement>|ReactNode|Typ odpowiada elementowi z onSubmit.
Czy inline lambda w JSX może dostać typ event kontekstowo?|Tak|Nie|Tylko w Java|Tylko bez Reacta|Prop zdarzenia udostępnia oczekiwany typ.
Czy osobny parametr e w zwykłej funkcji zawsze dostanie typ z późniejszego użycia JSX?|Nie|Tak|Tylko w strict|Tylko dla button|Osobna deklaracja może potrzebować adnotacji lub typu funkcji.
Co udostępnia nativeEvent?|Oryginalne zdarzenie DOM|Zawsze obiekt Ticket|Wyłącznie id przycisku|CSS modułu|React opakowuje zdarzenie w swoje API.
Czy wszystkie zdarzenia React bąbelkują identycznie?|Nie|Tak|Tylko w TSX|Tylko z CSS|Istnieją różnice i wyjątki, np. onScroll.
Czy onScroll w React propaguje się zwyczajnie jak onClick do rodzica?|Nie|Tak|Tylko w JSX|Tylko w tabeli|Nie należy zakładać identycznej propagacji scroll i click.
Kiedy działa onClickCapture rodzica względem bubble dziecka?|Wcześniej|Zawsze później|Nigdy|Tylko po preventDefault|Faza capture poprzedza bubble.
Czy stopPropagation w bubble dziecka cofa wykonany capture rodzica?|Nie|Tak|Tylko w TSX|Tylko dla button|Nie można cofnąć wcześniejszego wywołania.
Czy return false jest zalecanym sposobem blokowania domyślnej akcji w React?|Nie|Tak|Tylko w JSX|Tylko dla linku|Należy jawnie użyć odpowiedniej metody zdarzenia.
Czy callback komponentu musi przyjmować event DOM?|Nie|Tak|Tylko w TSX|Tylko w tabeli|Może przyjmować semantyczne dane, np. id.
Co otrzymuje onSelect w TicketRow?|Liczbowe id|Cały Ticket zawsze|MouseEvent zawsze|Status zawsze|Kontrakt callbacku jest jawny.
Czy id w onSelect ma pole title?|Nie|Tak|Tylko w JSX|Tylko dla 42|Id jest liczbą.
Czy onClose powinno mutować ticket.status z props wewnątrz wiersza?|Nie|Tak|Tylko dla CLOSED|Tylko gdy TS pozwala|Dziecko zgłasza zamiar rodzicowi.
Czy kliknięcie callbacku samo odświeży tabelę bez zmiany stanu lub danych?|Nie|Tak|Tylko w TSX|Tylko w React 19|Logowanie zamiaru nie aktualizuje automatycznie props.
Czy disabled trzeba sprawdzić prawdziwą interakcją?|Tak|Nie|Tylko przy CLOSED|Tylko dla testów CSS|Ręczne wywołanie funkcji omija natywne zachowanie.
Czy props.onClick() na disabled elemencie odtwarza natywny klik użytkownika?|Nie|Tak|Tylko w TSX|Tylko w JSX|To bezpośrednie wywołanie JS.
Czy nowoczesny React DOM wymaga rutynowego event.persist z powodu starego poolingu?|Nie|Tak|Tylko w TSX|Tylko dla focus|Stare zalecenie nie opisuje obecnego React DOM.
Czy brak starego poolingu gwarantuje użyteczny currentTarget po await?|Nie|Tak|Tylko w TSX|Tylko dla input|Czas obsługi i stan DOM to odrębne kwestie.
Czy warto skopiować value przed await?|Tak|Nie|Tylko w JSX|Tylko dla checkboxa|Dane potrzebne później zostają jawnie zachowane.
Czy schowek może odrzucić writeText?|Tak|Nie|Tylko dla pustego tekstu|Tylko w TSX|Dostęp może być ograniczony lub operacja może się nie udać.
Czy handler schowka powinien obsłużyć błąd?|Tak|Nie|Tylko w JSX|Tylko przy https|Użytkownik powinien dostać sensowny wynik akcji.
Czy kopiowanie linku podczas renderu jest poprawnym skutkiem kliknięcia?|Nie|Tak|Tylko w TSX|Tylko bez API|Akcja powinna nastąpić w odpowiedzi na zdarzenie.
Czy link do nawigacji powinien mieć prawidłowy href?|Tak|Nie|Tylko w JSX|Tylko dla ticketu|Semantyka nawigacji ma znaczenie dla przeglądarki i użytkownika.
Czy preventDefault linku zmienia href w DOM?|Nie|Tak|Tylko w TSX|Tylko w React 19|Anuluje akcję bieżącego zdarzenia.
Czy dataset.id jest z definicji liczbą?|Nie|Tak|Tylko w TSX|Tylko gdy prop id jest number|Dataset udostępnia tekst lub undefined.
Czy Number("") daje NaN?|Nie, daje 0|Tak|Daje null|Rzuca TypeError|Pustą wartość trzeba sprawdzić przed konwersją identyfikatora.
Co sprawdzić po Number(raw) dla dodatniego id?|Bezpieczny integer większy od zera|Tylko truthy|Wyłącznie długość tekstu|Tylko znak procenta|Id powinien spełniać kontrakt domeny.
Czy Number.isSafeInteger odrzuca NaN?|Tak|Nie|Tylko w TSX|Tylko dla null|NaN nie jest bezpieczną liczbą całkowitą.
Czy JSX i TSX mają odmienną propagację kliknięć z powodu typów?|Nie|Tak|Tylko w tabeli|Tylko dla button|Typy nie zmieniają mechanizmu runtime.
Czy JSDoc może opisać onClose(id) w JSX?|Tak|Nie|Tylko po zmianie na TSX|Tylko bez React|Komentarz może dostarczyć kontrakt dla narzędzi.
Czy hover powinien być jedyną wskazówką interakcji ważnej akcji?|Nie|Tak|Tylko na desktopie|Tylko w TSX|Klawiatura i urządzenia bez hover też wymagają obsługi.
Czy można wywołać kilka callbacków w jednym handlerze świadomie?|Tak|Nie|Tylko w JSX|Tylko w React 19|Handler jest zwykłą funkcją; kolejność powinna być zamierzona.
Czy stopPropagation blokuje sam callback już wywołany wcześniej?|Nie|Tak|Tylko w TSX|Tylko dla close|Metoda nie cofa wykonanych instrukcji.
Czy disabled to mechanizm autoryzacji operacji backendu?|Nie|Tak|Tylko dla admina|Tylko z TS|To stan kontrolki UI, nie zabezpieczenie serwera.
Czy aria-disabled samo zachowuje się identycznie jak natywne disabled na button?|Nie|Tak|Tylko w JSX|Tylko w dark|Aria opisuje stan, a natywne zachowanie wymaga odpowiedniego elementu i logiki.
Czy z onChange bez value można zacząć od pola niekontrolowanego?|Tak|Nie|Tylko w JSX|Tylko dla search|Samo zgłaszanie zmian nie wymusza kontrolowanego value.
Czy handler powinien odczytywać bieżącą wartość inputa zamiast stałego domyślnego tekstu?|Tak|Nie|Tylko w TSX|Tylko dla search|Event udostępnia aktualny stan kontrolki.
Czy zmienna lokalna z value zmienia się magicznie po zmianie DOM?|Nie|Tak|Tylko w JSX|Tylko w TSX|Kopia prymitywnego tekstu jest konkretną wartością.
Czy event.type i event.key znaczą to samo?|Nie|Tak|Tylko w TSX|Tylko dla Escape|Type opisuje rodzaj zdarzenia, key klawisz.
Czy onMouseEnter jest odpowiednim jedynym handlerem akcji zapisu?|Nie|Tak|Tylko w JSX|Tylko na desktopie|Ważna akcja powinna mieć świadomą aktywację i obsługę klawiatury.
Czy bezwarunkowe preventDefault w każdym handlerze jest dobrą zasadą?|Nie|Tak|Tylko w TSX|Tylko dla button|Należy zachować potrzebne domyślne zachowania.
Czy bezwarunkowe stopPropagation w każdym dziecku ułatwia zawsze integrację?|Nie|Tak|Tylko w JSX|Tylko w tabeli|Może blokować potrzebne zachowania rodziców.
Czy handler wiersza może pozostać wygodą dla myszy obok dostępnego przycisku?|Tak|Nie|Tylko w TSX|Tylko dla 42|Podstawowa akcja musi być dostępna także innym metodom obsługi.
Czy funkcję handlera wolno deklarować wewnątrz komponentu?|Tak|Nie|Tylko na poziomie modułu|Tylko w JSX|To normalny sposób dostępu do props danego renderu.
Czy handler wewnątrz komponentu jest tym samym co definiowanie nowego typu komponentu w renderze?|Nie|Tak|Tylko w TSX|Tylko przy lambda|To odmienne mechanizmy Reacta.
Czy nazwa callbacku onExport musi odpowiadać natywnemu eventowi DOM?|Nie|Tak|Tylko w JSX|Tylko w TSX|Własny komponent definiuje własne API.
Czy Main może na tym etapie tylko logować wybrane id?|Tak|Nie|Tylko w TSX|Tylko dla OPEN|Zmiana stanu jest tematem kolejnych lekcji.
Czy readonly tickets pomaga opisać kontrakt listy?|Tak|Nie|Tylko w JSX|Tylko dla pustej listy|Ogranicza modyfikacje kontenera w analizie TS.
Czy readonly samo głęboko zamraża ticket w runtime?|Nie|Tak|Tylko w strict|Tylko dla status|To ograniczenie typu, nie Object.freeze.
Czy Test callbacku powinien sprawdzić liczbę wywołań i argument?|Tak|Nie|Tylko w TSX|Tylko dla close|Podwójne wywołanie może wynikać z błędnej propagacji.
Czy test propagacji można zastąpić osobnym wywołaniem dziecka i rodzica bez modelu DOM?|Nie w pełni|Tak zawsze|Tylko dla click|Tylko w JSX|To nie odtwarza mechanizmu event dispatch w React/DOM.
Czy widoczny fokus przycisków nadal jest potrzebny w lekcji zdarzeń?|Tak|Nie|Tylko przy close|Tylko w dark|Działanie handlera nie wystarczy do używalnej nawigacji.
Czy pusta tabela powinna renderować tr bez td z komunikatem?|Nie|Tak|Tylko w TSX|Tylko w JSX|Treść tabeli powinna zachować poprawną strukturę.
Po co colSpan=3 w pustym stanie przykładowej tabeli?|Komunikat obejmuje trzy kolumny|Dodaje trzy rekordy|Zmienia callback|Włącza sortowanie|ColSpan dotyczy struktury tabeli.
Czy błędny onSelect z id.title powinien zostać świadomie sprawdzony przez TS?|Tak|Nie|Tylko w JSX|Tylko w tabeli bez CSS|Typ callbacku pozwala wykryć pomylenie danych.
Czy poprawny typecheck dowodzi możliwości zapisu schowka na urządzeniu użytkownika?|Nie|Tak|Tylko przy https|Tylko w TSX|To zależy od środowiska runtime i uprawnień przeglądarki.
Czy kryterium lekcji wymaga już trwałego zapisu zmian ticketów?|Nie|Tak|Tylko w TSX|Tylko dla CLOSED|Lekcja koncentruje się na zgłaszaniu i obsłudze zdarzeń.
Co potwierdza ukończenie lekcji zdarzeń?|Poprawne callbacki, propagacja, formularze i dostępne akcje|Samo pojawienie się button|Tylko log w renderze|Wyłącznie brak błędów składni|Liczy się zachowanie po rzeczywistej interakcji.
`.trim().split('\n').map(line=>line.split('|'));
module.exports={exercises,quiz,row,table,search,form,copy};
