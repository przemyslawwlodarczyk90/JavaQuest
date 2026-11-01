const model='export type NewTicketValues={title:string;description:string};\nexport const EMPTY:NewTicketValues={title:"",description:""};\nexport function updateField<K extends keyof NewTicketValues>(current:NewTicketValues,key:K,value:NewTicketValues[K]):NewTicketValues{return {...current,[key]:value};}\nexport function isField(name:string):name is keyof NewTicketValues{return name==="title"||name==="description";}';
const hook='import {useState} from "react";\n'+model+'\nexport function useFormValues(initial:NewTicketValues){const [values,setValues]=useState<NewTicketValues>(()=>({...initial}));function setField<K extends keyof NewTicketValues>(key:K,value:NewTicketValues[K]){setValues(previous=>updateField(previous,key,value));}function reset(){setValues({...initial});}return {values,setField,reset};}';
const form='import {useId,useState} from "react";\nimport type {FormEvent} from "react";\n'+model+'\nexport function NewTicketForm({onCreate,onCancel}:{onCreate:(values:NewTicketValues)=>void;onCancel?:()=>void}){const [form,setForm]=useState<NewTicketValues>(()=>({...EMPTY}));const id=useId();function setField<K extends keyof NewTicketValues>(key:K,value:NewTicketValues[K]){setForm(previous=>updateField(previous,key,value));}function handleSubmit(event:FormEvent<HTMLFormElement>){event.preventDefault();if(form.title.trim()==="")return;onCreate({title:form.title.trim(),description:form.description});setForm({...EMPTY});}return <form onSubmit={handleSubmit}><label htmlFor={`${id}-title`}>Tytuł</label><input id={`${id}-title`} name="title" value={form.title} onChange={event=>setField("title",event.currentTarget.value)} required/><label htmlFor={`${id}-description`}>Opis</label><textarea id={`${id}-description`} name="description" rows={4} value={form.description} onChange={event=>setField("description",event.currentTarget.value)}/><button type="submit">Utwórz ticket</button><button type="button" onClick={onCancel}>Anuluj</button></form>;}';
const shared='import {useState} from "react";\nimport type {ChangeEvent} from "react";\n'+model+'\nexport function SharedFields(){const [form,setForm]=useState({...EMPTY});function handleChange(event:ChangeEvent<HTMLInputElement|HTMLTextAreaElement>){const {name,value}=event.currentTarget;if(isField(name))setForm(previous=>updateField(previous,name,value));}return <div><label>Tytuł<input name="title" value={form.title} onChange={handleChange}/></label><label>Opis<textarea name="description" value={form.description} onChange={handleChange}/></label></div>;}';
const parent=form+'\nexport type Ticket=NewTicketValues&{id:number;status:"OPEN";priority:"MEDIUM";assignee:null;tags:readonly string[];createdAt:string};\nexport function Main(){const [tickets,setTickets]=useState<readonly Ticket[]>([]);const [open,setOpen]=useState(false);function handleCreate(values:NewTicketValues){const createdAt=new Date().toISOString();setTickets(previous=>{const id=previous.reduce((max,ticket)=>Math.max(max,ticket.id),0)+1;const ticket:Ticket={...values,id,status:"OPEN",priority:"MEDIUM",assignee:null,tags:[],createdAt};return [ticket,...previous];});setOpen(false);}return <main><button type="button" onClick={()=>setOpen(true)}>Nowy ticket</button>{open&&<NewTicketForm onCreate={handleCreate} onCancel={()=>setOpen(false)}/>}<ul>{tickets.map(ticket=><li key={ticket.id}>{ticket.title}</li>)}</ul></main>;}';
const dialog='import {useState} from "react";\nimport * as Dialog from "@radix-ui/react-dialog";\nimport {NewTicketForm} from "./NewTicketForm";\nimport type {NewTicketValues} from "./NewTicketForm";\nexport function NewTicketDialog({onCreate}:{onCreate:(values:NewTicketValues)=>void}){const [open,setOpen]=useState(false);return <Dialog.Root open={open} onOpenChange={setOpen}><Dialog.Trigger>Nowy ticket</Dialog.Trigger><Dialog.Portal><Dialog.Overlay/><Dialog.Content><Dialog.Title>Nowy ticket</Dialog.Title><Dialog.Description>Wpisz tytuł i opis zgłoszenia.</Dialog.Description><NewTicketForm onCreate={values=>{onCreate(values);setOpen(false);}} onCancel={()=>setOpen(false)}/></Dialog.Content></Dialog.Portal></Dialog.Root>;}';
const exercises=[
 ['Zdefiniuj osobny NewTicketValues z title i description, niezależnie od pełnego Ticket.','Formularz nie musi przechowywać technicznych pól rekordu.',model],
 ['Utwórz początkowy obiekt pustych tekstów dla obu pól.','Nie przełączaj value pomiędzy undefined i tekstem.',model],
 ['Napisz formularz dwóch pól kontrolowanych z input i textarea.','Stan jest jednym obiektem.',form],
 ['Nadaj etykietom i polom unikalne id przez useId.','Kilka instancji formularza może istnieć na stronie.',form],
 ['Podłącz oba pola przez typowany setField, zachowując drugie pole.','Updater obiektu musi zachować pozostałe dane.',form],
 ['Wyodrębnij updateField z generykiem klucza.','Key i value mają wynikać z modelu formularza.',model],
 ['Zbuduj useFormValues z values, setField i reset.','Hook tworzy stan na najwyższym poziomie.',hook],
 ['Obsłuż wysłanie na form.onSubmit, nie tylko na kliknięciu przycisku.','Ta sama logika powinna obsłużyć właściwy submit klawiaturą.',form],
 ['Anuluj domyślną nawigację i przekaż dane rodzicowi.','Event i payload to różne obiekty.',form],
 ['Ustal kontrakt submit: title trim, description bez zmiany wewnętrznej i skrajnej treści.','Nie normalizuj opisu przez przypadek.',form],
 ['Napisz wspólny handler input/textarea z walidacją name.','String z DOM nie jest automatycznie keyof Values.',shared],
 ['Wyjaśnij, czemu name="titel" może przejść TypeScript przy zwykłym input.','Name na HTML input ma typ string.','Sam typ HTML nie zna kluczy konkretnego obiektu formularza. Handler z [name] może dodać niechciane pole. Per-field setter daje kontrolę klucza, a wspólny handler może użyć guarda isField.'],
 ['Zignoruj nieznany name w wspólnym handlerze zamiast dopisywać nowe pole.','Guard powinien sprawdzić rzeczywistą wartość.',shared],
 ['Wykaż błędem TS setField z kluczem titel.','Użyj czystego updateField, aby test nie wymagał renderu.',model+'\n// @ts-expect-error Nieznany klucz\nconst invalid=updateField(EMPTY,"titel","API");'],
 ['Wykaż błędem TS liczbową wartość title.','Oba pola formularza są tekstowe.',model+'\n// @ts-expect-error Title ma typ string\nconst invalid=updateField(EMPTY,"title",42);'],
 ['Dodaj Utwórz z type=submit i Anuluj z type=button.','Anulowanie nie powinno wysłać formularza.',form],
 ['Czyść kontrolowane pola przez reset stanu po zakończeniu synchronicznego callbacku.','Natywny reset nie jest źródłem wartości tych pól.',form],
 ['Wyjaśnij ograniczenie prostego synchronicznego onCreate:void.','Nie ogłaszaj zapisu na serwerze, którego ten kontrakt nie obsługuje.','Formularz resetuje po normalnym powrocie callbacku. Nie czeka automatycznie na asynchroniczne zapisanie. Dla Promise trzeba zaprojektować pending, błędy i reset po sukcesie w kolejnej lekcji.'],
 ['Sprawdź zachowanie, gdy synchroniczny onCreate rzuci wyjątek.','Instrukcja resetu jest dopiero po callbacku.','Wyjątek przerywa handler przed setForm(EMPTY), więc wpisane wartości nie są resetowane przez tę ścieżkę. To nie jest jeszcze pełna obsługa błędu UI; zaprojektujesz ją osobno.'],
 ['Nie twórz ticketu z tytułu składającego się wyłącznie ze spacji.','Required nie musi odrzucić ciągu samych spacji.',form],
 ['Zbuduj rodzica dodającego rekord na początek listy z polami technicznymi.','Data jest tworzona w handlerze, a numer z poprzedniej listy.',parent],
 ['Wyjaśnij granice lokalnego max+1 w przykładzie rodzica.','Nie utożsamiaj tego z serwerową globalną identyfikacją.','Przykład ma małą lokalną listę dodatnich bezpiecznych id. Przy produkcyjnych danych uwzględnij granicę safe integer i właściwą strategię identyfikatorów. Formularz przekazuje wartości użytkownika, a identyfikacja należy do warstwy tworzącej rekord.'],
 ['Umieść formularz w kontrolowanym dialogu i zamknij po lokalnym utworzeniu.','Anulowanie zamyka bez wywołania onCreate.','Pliki rozwiązania:\n--- NewTicketForm.tsx ---\n'+form+'\n--- NewTicketDialog.tsx ---\n'+dialog],
 ['Zaprojektuj test submit przyciskiem i Enter w tytule.','Textarea ma inną rolę Enter niż pojedyncze pole.','Dla poprawnego tytułu oba sposoby wysyłają dane zgodnie z zachowaniem formularza. Enter w textarea zwykle wstawia nową linię, więc nie traktuj każdego Enter jako submitu. Sprawdź brak przeładowania i pojedynczy callback.'],
 ['Zaprojektuj test Anuluj bez submitu.','Sprawdź rodzaj przycisku i rzeczywiste zdarzenie.','Wypełnij pola, kliknij Anuluj, oczekuj tylko onCancel. OnCreate nie powinno zostać wywołane. Natywny test formularza potwierdza, że type=button nie uruchamia domyślnego submitu.'],
 ['Sprawdź, że payload rodzica jest oddzielnym obiektem od stanu formularza.','Przekazanie świeżego obiektu izoluje przypadkowy zapis rodzica.','Handler tworzy {title:trim,description:...}. Rodzic nie powinien mutować otrzymanych danych, ale test może sprawdzić, że ewentualna zmiana payloadu nie nadpisuje starego obiektu stanu formularza. Reset tworzy także świeży pusty obiekt.'],
 ['Zapisz JSX/JSDoc model callbacku formularza.','Usuń składnię typów TS z wersji JSX.','import {useState} from "react";\n/** @param {{onCreate:(values:{title:string,description:string})=>void}} props */\nexport function Form({onCreate}){const [values,setValues]=useState({title:"",description:""});return <form onSubmit={event=>{event.preventDefault();if(!values.title.trim())return;onCreate({title:values.title.trim(),description:values.description});setValues({title:"",description:""});}}><label>Tytuł<input value={values.title} onChange={event=>{const title=event.currentTarget.value;setValues(previous=>({...previous,title}));}}/></label><label>Opis<textarea value={values.description} onChange={event=>{const description=event.currentTarget.value;setValues(previous=>({...previous,description}));}}/></label><button type="submit">Utwórz</button></form>;}'],
 ['Wyjaśnij, czy zmiana initial w useFormValues sama nadpisze bieżącą edycję.','To argument początkowy, nie ciągła synchronizacja.','Nie. Stan tej samej instancji zachowuje swoje wartości. Reset w tym prostym hooku używa initial z bieżącego renderu. Jeżeli potrzebujesz edycji kolejnych rekordów albo blokady utraty zmian, ustal osobny kontrakt resetowania.'],
 ['Sprawdź niemutowalność EMPTY i poprzedniej wersji form.','UpdateField tworzy nowy obiekt.','Zamroź EMPTY, zmień title przez helper i porównaj: nowa referencja, title nowe, description zachowane, EMPTY nadal dwa puste napisy. Zmiana drugiego pola powinna zachować pierwszy wpis.'],
 ['Oddaj formularz z kontrolowanymi polami, submit/reset i anulowaniem.','Zachowaj jasny synchroniczny kontrakt oraz typy.',form],
];
const project={'src/NewTicketForm.tsx':form,'src/NewTicketDialog.tsx':dialog};
project['src/NewTicketDialog.tsx']=dialog.replace('import {useState} from "react";','import {useState} from "react";\nimport styles from "./Dialog.module.css";').replace('<Dialog.Trigger>','<Dialog.Trigger className={styles.trigger}>').replace('<Dialog.Overlay/>','<Dialog.Overlay className={styles.overlay}/>').replace('<Dialog.Content>','<Dialog.Content className={styles.content}>');
project['src/Dialog.module.css']='.overlay{position:fixed;inset:0;background:rgb(0 0 0 / 0.4);}.content{position:fixed;top:50%;left:50%;transform:translate(-50%,-50%);width:min(30rem,calc(100vw - 2rem));max-height:calc(100dvh - 2rem);overflow:auto;padding:var(--space-4);background:var(--surface);color:var(--text);border:1px solid var(--border);border-radius:var(--radius);}.content form{display:grid;gap:var(--space-2);}.content input,.content textarea,.content button,.trigger{font:inherit;padding:var(--space-2);}.content input:focus-visible,.content textarea:focus-visible,.content button:focus-visible,.trigger:focus-visible{outline:2px solid var(--color-primary);outline-offset:2px;}';
project['src/tokens.css']=require('./practice_style_03.cjs').tokens;
project['src/App.tsx']='import {useState} from "react";\nimport "./tokens.css";\nimport {NewTicketDialog} from "./NewTicketDialog";\nimport type {NewTicketValues} from "./NewTicketForm";\ntype Ticket=NewTicketValues&{id:number;createdAt:string};\nexport default function App(){const [tickets,setTickets]=useState<readonly Ticket[]>([]);function create(values:NewTicketValues){const createdAt=new Date().toISOString();setTickets(previous=>[{...values,id:previous.reduce((max,ticket)=>Math.max(max,ticket.id),0)+1,createdAt},...previous]);}return <main><h1>Zgłoszenia</h1><NewTicketDialog onCreate={create}/><ul>{tickets.map(ticket=><li key={ticket.id}>{ticket.title}</li>)}</ul></main>;}';
project['src/main.tsx']='import {createRoot} from "react-dom/client";\nimport App from "./App";\nconst container=document.getElementById("root");if(!container)throw Error("Brak root");createRoot(container).render(<App/>);';
const quiz=`
Dlaczego typ wartości formularza jest osobny od Ticket?|Formularz zbiera tylko pola użytkownika|Ticket nie może mieć id|React zabrania wspólnych typów|TypeScript nie zna formularzy|Pola techniczne tworzy rodzic lub warstwa danych.
Jakie pola zawiera NewTicketValues?|Title i description|Id i createdAt wyłącznie|Status i priority wyłącznie|Cały DOM|To model prostego formularza.
Czy title powinno zaczynać się jako undefined w kontrolowanym polu?|Nie|Tak|Tylko w TSX|Tylko w JSX|Pusty tekst utrzymuje spójny tryb.
Czy jeden obiekt stanu może obsłużyć dwa pola?|Tak|Nie|Tylko w JSX|Tylko z reducerem|To typowy model niewielkiego formularza.
Czy zmiana title powinna usuwać description?|Nie|Tak|Tylko w TSX|Tylko przy empty|Updater zachowuje pozostałe pola przez spread.
Po co funkcja setField?|Aktualizacja pola z kontrolą nazwy i wartości|Automatyczne wysłanie danych|Generacja CSS|Zmiana key|Typ klucza wiąże operację z modelem.
Co oznacza K extends keyof NewTicketValues?|Dozwolony klucz tego modelu|Dowolny string z DOM|Wyłącznie liczba|Typ eventu|Generyk ogranicza nazwę pola.
Co oznacza NewTicketValues[K]?|Typ wartości wybranego pola|Zawsze event|Nazwa komponentu|Id DOM|Kontrakt zależy od klucza.
Czy name inputa ma z definicji typ keyof twojego formularza?|Nie|Tak|Tylko w strict|Tylko w TSX|HTML name jest napisem.
Czy name=titel musi być błędem tsc przy zwykłym input?|Nie|Tak|Tylko w JSX|Tylko z required|Typ HTML nie zna domenowych nazw.
Co może zrobić niekontrolowany [name] w updaterze obiektu?|Dodać nieznane pole|Automatycznie naprawić literówkę|Zawsze rzucić TypeError|Zmienić JSX w HTML|Computed key działa jak zwykły JavaScript.
Jak bezpieczniej obsłużyć name z DOM?|Guard lub per-field typed setter|Rzutować każdy string bez sprawdzenia|Usunąć wszystkie typy|Nadać nowy key|To ogranicza przypadkowe klucze.
Jaki typ eventu może obsłużyć input i textarea?|ChangeEvent z unią obu elementów|Wyłącznie MouseEvent|CSSProperties|ReactNode|Handler odczytuje zgodne name i value.
Skąd odczytać kontrolkę aktualnego handlera?|CurrentTarget|Key|Props.children|Status|To element, na którym działa handler.
Co jest źródłem tekstu textarea kontrolowanego w React?|Value|Domyślne dzieci HTML jako stale sterowane|ClassName|Key|Textarea używa kontrolowanego value.
Czy rows=4 waliduje długość opisu?|Nie|Tak|Tylko w TSX|Tylko z textarea|Rows określa prezentację pola.
Czy label potrzebuje powiązania z polem?|Tak|Nie|Tylko przy błędzie|Tylko w JSX|Może to być htmlFor/id lub zagnieżdżenie.
Po co useId przy kilku instancjach formularza?|Unikalne powiązania etykiet|Id ticketu biznesowego|Key listy|Cena|To identyfikator instancji UI.
Czy ten sam stały id title można bezpiecznie powtarzać w dokumencie?|Nie|Tak|Tylko w TSX|Tylko w dialogu|Id powinno być unikalne.
Gdzie podpiąć podstawową logikę wysłania?|Form.onSubmit|Wyłącznie onClick przycisku|W renderze|W key|Formularz jest miejscem obsługi submitu.
Co robi preventDefault w handlerze submit?|Anuluje domyślną akcję formularza|Zawsze zatrzymuje bubbling|Tworzy ticket automatycznie|Resetuje pola automatycznie|Dalsze działanie określa kod aplikacji.
Czy React automatycznie blokuje każdy submit tylko przez użycie JSX?|Nie|Tak|Tylko w TSX|Tylko z useState|Trzeba świadomie obsłużyć zachowanie formularza.
Jaki typ ma handleSubmit w TSX?|FormEvent<HTMLFormElement>|MouseEvent<HTMLInputElement>|ChangeEvent<HTMLTextAreaElement>|ReactNode|Event odpowiada formularzowi.
Czy rodzic onCreate dostaje event DOM w przygotowanym API?|Nie, wartości formularza|Tak zawsze|Tylko w TSX|Tylko w JSX|Komponent ukrywa szczegóły eventu.
Dlaczego onCreate dostaje świeży obiekt?|By przekazać jawny payload niezależny od stanu|By zmienić id|By obejść required|By wykonać fetch|Stan nie jest udostępniany jako mutowalny współdzielony obiekt.
Czy title jest trimowane przed callbackiem w rozwiązaniu?|Tak|Nie|Tylko w TSX|Tylko przy pustym|To jawny kontrakt tej wersji.
Czy description jest trimowane w rozwiązaniu?|Nie|Tak|Tylko w JSX|Tylko przy multiline|Opis jest zachowany jak wpisany.
Czy required zawsze odrzuci same spacje jako tytuł?|Nie|Tak|Tylko w TSX|Tylko w textarea|Własny guard trim może uzupełnić walidację.
Czy handler z guardem powinien wywołać onCreate dla białego tytułu?|Nie|Tak|Tylko w JSX|Tylko po kliknięciu|Brak sensownego tytułu zatrzymuje utworzenie.
Czy native validation może zablokować submit jeszcze przed handlerem?|Tak|Nie|Tylko w TSX|Tylko w React 19|Przebieg zdarzeń zależy też od HTML.
Jaki type ma główny przycisk tworzenia?|Submit|Button zawsze|Reset|Create|Wyzwala wysłanie formularza.
Jaki type ma Anuluj?|Button|Submit|Reset obowiązkowo|Cancel|Akcja nie ma wysyłać formularza.
Czy button bez type wewnątrz zwykłego formularza może wysłać formularz?|Tak|Nie|Tylko w TSX|Tylko w JSX|Domyślne zachowanie wymaga uwagi.
Czy type=reset jest głównym sposobem czyszczenia kontrolowanego stanu w tym przykładzie?|Nie|Tak|Tylko w TSX|Tylko w dialogu|Źródło value resetujemy setterem.
Czy setForm do pustych wartości czyści kontrolowane pola po renderze?|Tak|Nie|Tylko w JSX|Tylko bez textarea|Value odzwierciedla nowy stan.
Czy EMPTY należy mutować przy każdym wpisie?|Nie|Tak|Tylko w TSX|Tylko przy title|Aktualizacje tworzą nowe obiekty.
Czy reset musi zwrócić ten sam obiekt co poprzedni stan?|Nie|Tak|Tylko w JSX|Tylko w StrictMode|Świeży pusty obiekt jest poprawny.
Czy prosty callback void oznacza czekanie na sukces requestu?|Nie|Tak|Tylko w TSX|Tylko z form|Asynchroniczny kontrakt wymaga osobnego projektu.
Co stanie się z resetem, gdy synchroniczny onCreate rzuci przed nim wyjątek?|Nie zostanie wykonany tą ścieżką|Wykona się obowiązkowo|Formularz sam się wyczyści zawsze|React zmieni wartości na null|Kolejne instrukcje handlera nie wykonają się po nieobsłużonym wyjątku.
Czy taki wyjątek jest już pełną obsługą błędu dla użytkownika?|Nie|Tak|Tylko w TSX|Tylko w JSX|Komunikat i bezpieczny flow wymagają osobnej obsługi.
Czy identyfikator ticketu powinien być obowiązkowo polem wpisywanym w tym formularzu?|Nie|Tak|Tylko w TSX|Tylko dla lokalnych danych|Pola techniczne tworzy warstwa tworząca rekord.
Gdzie tworzyć timestamp dla nowego ticketu?|W handlerze akcji|W każdym renderze|W czystym updaterze jako zmienny odczyt|W CSS|Czas jest wejściem do czystej aktualizacji listy.
Czy nowy ticket można dodać na początek przez nową tablicę?|Tak|Nie|Tylko w JSX|Tylko z Immer|Prepend nie wymaga mutowania starej listy.
Czy lokalne max+1 zapewnia globalne id wielu klientów?|Nie|Tak|Tylko w TSX|Tylko z ISO|To lokalna uproszczona strategia.
Czy onCancel musi automatycznie tworzyć ticket?|Nie|Tak|Tylko w JSX|Tylko w dialogu|To odrębna akcja bez submitu.
Czy anulowanie zawsze musi czyścić wartości przed zamknięciem?|Nie, to decyzja modelu instancji i UX|Tak zawsze|Tylko w TSX|Tylko w JSX|Odmontowanie lub zachowanie formularza daje różne polityki.
Czy Enter w textarea zwykle służy wpisaniu nowej linii?|Tak|Nie, zawsze submit|Tylko w TSX|Tylko w React|Nie należy traktować wszystkich pól identycznie.
Czy test wyłącznie onClick przycisku obejmuje wszystkie drogi submitu?|Nie|Tak|Tylko w TSX|Tylko z required|Potrzebny jest test całego formularza i klawiatury.
Czy zmiana initial props automatycznie zastąpi stan hooka useFormValues?|Nie|Tak|Tylko w JSX|Tylko dla title|UseState zapamiętuje stan instancji.
Co robi reset w pokazanym useFormValues?|Ustawia kopię initial z bieżącego renderu|Wysyła formularz|Zmienia id ticketu|Usuwa hook|To jawna funkcja oparta na wybranym kontrakcie.
Czy wspólny handler opisany name/value obsługuje już checkbox właściwie bez zmian?|Nie|Tak|Tylko w TSX|Tylko w JSX|Checkbox zwykle wymaga checked, nie value.
Czy wartość textarea i input tekstowego ma typ string?|Tak|Nie|Tylko w JSX|Tylko dla title|To podstawa wspólnego modelu dwóch tekstów.
Czy DOM name jest runtime danymi wymagającymi uwagi mimo typów?|Tak|Nie|Tylko w TSX|Tylko z generic|Guard sprawdza rzeczywistą nazwę.
Czy isField("titel") ma dać true?|Nie|Tak|Tylko w JSX|Tylko dla niepustego|Dozwolone są title i description.
Czy spread przy zmianie pola kopiuje zagnieżdżone dane głęboko?|Nie|Tak|Tylko w TSX|Tylko dla formularza|Ten model jest płaski, ale ogólna semantyka spread pozostaje płytka.
Czy dane formularza można wysłać bez wstawiania surowego HTML?|Tak|Nie|Tylko w JSX|Tylko dla title|Zwykłe wartości i callback wystarczą.
Czy TypeScript zastępuje walidację niepustego tytułu runtime?|Nie|Tak|Tylko w strict|Tylko z keyof|String może być pusty lub biały.
Czy brak błędów kompilacji dowodzi, że Anuluj nie submituje w przeglądarce?|Nie|Tak|Tylko w TSX|Tylko z button|Sprawdź semantykę i rzeczywistą interakcję.
Czy formularz w dialogu nadal potrzebuje nazw i etykiet?|Tak|Nie|Tylko w JSX|Tylko dla textarea|Modal nie zastępuje semantyki pól.
Co potwierdza ukończenie pierwszego formularza?|Kontrolowane pola, submit, payload, reset i anulowanie|Samo form w JSX|Tylko dwa inputy|Wyłącznie log w renderze|Dane i zachowania muszą współpracować.
`.trim().split('\n').map(line=>line.split('|'));
const cases=[
 ['Zmiana tytułu','updateField(EMPTY,"title","API").title','API','','undefined','title'],
 ['Zachowany opis','updateField(base,"title","Nowy").description','Opis','Nowy','','undefined'],
 ['Zmiana opisu','updateField(base,"description","Dwa wiersze").description','Dwa wiersze','Opis','API','undefined'],
 ['Zachowany tytuł','updateField(base,"description","Nowy opis").title','API','Nowy opis','','undefined'],
 ['Stary obiekt po aktualizacji','(()=>{updateField(base,"title","Nowy");return base.title;})()','API','Nowy','','undefined'],
 ['Nowa referencja','Object.is(updateField(base,"title","Nowy"),base)','false','true','undefined','null'],
 ['Dwa pola kolejno','updateField(updateField(EMPTY,"title","API"),"description","Opis").title','API','Opis','','undefined'],
 ['Drugi zapis tego samego pola','updateField(updateField(EMPTY,"title","A"),"title","B").title','B','A','','undefined'],
 ['Poprawny klucz title','isField("title")','true','false','title','undefined'],
 ['Poprawny klucz description','isField("description")','true','false','description','undefined'],
 ['Literówka nazwy','isField("titel")','false','true','titel','undefined'],
 ['Wielkość liter nazwy','isField("Title")','false','true','Title','undefined'],
 ['Pusta nazwa','isField("")','false','true','','undefined'],
 ['Pole techniczne','isField("id")','false','true','id','undefined'],
 ['Title po trim','"  API  ".trim()','API','  API  ','API  ','  API'],
 ['Długość opisu ze spacjami i nową linią','description.length','19','15','17','20'],
 ['Biały tytuł','"   ".trim()===""','true','false','0','undefined'],
 ['Niepusty tytuł','" API ".trim()===""','false','true','API','undefined'],
 ['Długość początkowego title','EMPTY.title.length','0','1','5','undefined'],
 ['Długość początkowego opisu','EMPTY.description.length','0','1','4','undefined'],
 ['Liczba pól modelu','Object.keys(EMPTY).length','2','1','3','0'],
 ['Klucz id nie powstaje automatycznie','Object.hasOwn(EMPTY,"id")','false','true','undefined','null'],
 ['Pusta kopia resetu','JSON.stringify({...EMPTY})','{"title":"","description":""}','{}','null','undefined'],
 ['Reset to nowa referencja','Object.is({...EMPTY},EMPTY)','false','true','undefined','null'],
 ['Niezmieniony EMPTY','(()=>{updateField(EMPTY,"title","API");return EMPTY.title;})()','','API','title','undefined'],
 ['Sekwencja dwóch aktualizacji','JSON.stringify(updateField(updateField(EMPTY,"title","A"),"description","B"))','{"title":"A","description":"B"}','{"title":"B","description":"A"}','{"title":"A"}','{}'],
 ['Wartość tekstowa','typeof base.title','string','number','object','undefined'],
 ['Zachowanie nowej linii','description.includes(String.fromCharCode(10))','true','false','undefined','null'],
 ['Brak trim w helperze','updateField(EMPTY,"title"," API ").title',' API ','API','api','undefined'],
 ['Guard nazwy ze spacją','isField(" title ")','false','true','title','undefined'],
 ['Guard dodatkowego pola','isField("descriptionExtra")','false','true','description','undefined'],
 ['Zastąpienie opisu pustym','updateField(base,"description","").description','','Opis','API','undefined'],
 ['Zachowanie pierwszego pola po pustym opisie','updateField(base,"description","").title','API','','Opis','undefined'],
 ['Payload tytułu','payload.title','API',' API ','api','undefined'],
 ['Payload opisu','payload.description===description','true','false','undefined','null'],
 ['Payload nie jest base','Object.is(payload,base)','false','true','undefined','null'],
 ['Pole status nie należy do formularza','isField("status")','false','true','status','undefined'],
 ['Pole createdAt nie należy do formularza','isField("createdAt")','false','true','createdAt','undefined'],
 ['Zastąpienie tytułu zerem jako tekstem','updateField(EMPTY,"title","0").title','0','','undefined','null'],
 ['Tekst 0 nie jest pusty po trim','"0".trim()===""','false','true','0','undefined'],
];
// Jawnie ustawiony opis wielowierszowy w kodzie, bez zależności od escapowania tabeli przypadków.
const baseCode=model+'\nconst base:NewTicketValues={title:"API",description:"Opis"};const description='+JSON.stringify('  Linia 1'+String.fromCharCode(10)+'Linia 2  ')+';const payload={title:" API ".trim(),description};';
for(const row of cases){const [label,expr,a,b,c,d]=row;quiz.push([`${label}: co wypisze model formularza?`,...[a,b,c,d].map(value=>value===''?'Pusty napis':value),'UpdateField tworzy nowy obiekt i zmienia wskazany klucz; trim występuje dopiero tam, gdzie zapisano go jawnie.',baseCode+'\nconsole.log('+expr+');']);}
module.exports={exercises,quiz,model,hook,form,shared,parent,dialog,project};
