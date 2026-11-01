const model='export const AREAS=["tickets","dashboard","other"] as const;\nexport type Area=(typeof AREAS)[number];\nexport const TAGS=["api","ui","export","auth"] as const;\nexport type Tag=(typeof TAGS)[number];\nexport type Feedback={email:string;area:Area;message:string;contactMe:boolean;tags:Tag[];attachment:File|null};';
const helpers='export function readText(data:FormData,name:string):string|null{const value=data.get(name);return typeof value==="string"?value:null;}\nexport function isArea(value:string):value is Area{return AREAS.some(area=>area===value);}\nexport function isTag(value:string):value is Tag{return TAGS.some(tag=>tag===value);}\nexport function readFeedback(data:FormData):Feedback|null{const email=readText(data,"email");const area=readText(data,"area");const message=readText(data,"message");if(email===null||email.trim()===""||area===null||!isArea(area)||message===null||message.trim()==="")return null;const tags:Tag[]=[];for(const value of data.getAll("tags")){if(typeof value!=="string"||!isTag(value))return null;if(!tags.includes(value))tags.push(value);}const rawFile=data.get("attachment");if(rawFile!==null&&!(rawFile instanceof File))return null;return {email:email.trim(),area,message,contactMe:data.get("contactMe")==="on",tags,attachment:rawFile instanceof File&&rawFile.name!==""?rawFile:null};}\nexport function fileInfo(file:File|null):string{return file?`${file.name} (${Math.round(file.size/1024)} KiB)`:"Brak pliku";}';
const form='import {useId} from "react";\nimport type {FormEvent} from "react";\n'+model+'\n'+helpers+'\nexport function FeedbackForm({onSend,onInvalid}:{onSend:(feedback:Feedback)=>void;onInvalid?:(message:string)=>void}){const id=useId();function handleSubmit(event:FormEvent<HTMLFormElement>){event.preventDefault();const form=event.currentTarget;const data=new FormData(form);const feedback=readFeedback(data);if(feedback===null){onInvalid?.("Sprawdź wymagane pola i wybrane wartości");return;}onSend(feedback);form.reset();}return <form onSubmit={handleSubmit} className="feedback-form"><label htmlFor={`${id}-email`}>E-mail</label><input id={`${id}-email`} name="email" type="email" required/><label htmlFor={`${id}-area`}>Obszar</label><select id={`${id}-area`} name="area" defaultValue="tickets">{AREAS.map(area=><option key={area} value={area}>{area}</option>)}</select><label htmlFor={`${id}-message`}>Opis</label><textarea id={`${id}-message`} name="message" required rows={4}/><label><input type="checkbox" name="contactMe" defaultChecked/>Skontaktujcie się ze mną</label><fieldset><legend>Tagi</legend>{TAGS.map(tag=><label key={tag}><input type="checkbox" name="tags" value={tag}/>{tag}</label>)}</fieldset><label htmlFor={`${id}-file`}>Załącznik</label><input id={`${id}-file`} name="attachment" type="file" accept="image/*,.pdf"/><button type="submit">Wyślij</button></form>;}';
const attachment='import {useId,useState} from "react";\nexport function AttachmentInput(){const [info,setInfo]=useState<string|null>(null);const id=useId();return <div><label htmlFor={id}>Załącznik</label><input id={id} name="attachment" type="file" accept="image/*,.pdf" onChange={event=>{const file=event.currentTarget.files?.[0];setInfo(file?`${file.name} (${Math.round(file.size/1024)} KiB)`:null);}}/>{info!==null&&<small>{info}</small>}</div>;}';
const project={'src/FeedbackForm.tsx':form,'src/App.tsx':'import {useState} from "react";\nimport {FeedbackForm} from "./FeedbackForm";\nimport type {Feedback} from "./FeedbackForm";\nimport "./App.css";\nexport default function App(){const [feedback,setFeedback]=useState<Feedback|null>(null);const [message,setMessage]=useState("");return <main><h1>Zgłoś problem</h1><FeedbackForm onSend={value=>{setFeedback(value);setMessage("Dziękujemy!");}} onInvalid={setMessage}/><p role="status">{message}</p>{feedback&&<section><h2>Przekazane dane</h2><p>{feedback.email} — {feedback.area}</p><p>{feedback.message}</p><p>Kontakt: {feedback.contactMe?"tak":"nie"}</p><p>Tagi: {feedback.tags.join(", ")||"brak"}</p><p>Plik: {feedback.attachment?.name??"brak"}</p></section>}</main>;}','src/App.css':'body{font-family:system-ui,sans-serif;margin:0;}main{max-width:40rem;padding:1rem;margin:auto;}.feedback-form{display:grid;gap:0.5rem;}input,select,textarea,button{font:inherit;padding:0.5rem;}fieldset label{display:inline-flex;align-items:center;gap:0.25rem;margin-right:0.75rem;}input:focus-visible,select:focus-visible,textarea:focus-visible,button:focus-visible{outline:2px solid #2563eb;outline-offset:2px;}','src/main.tsx':'import {createRoot} from "react-dom/client";\nimport App from "./App";\nconst container=document.getElementById("root");if(!container)throw Error("Brak root");createRoot(container).render(<App/>);'};
const exercises=[
 ['Zbuduj FeedbackForm bez stanu React przechowującego wartości pól.','UseId nie czyni pól kontrolowanymi.',form],
 ['Ustaw startowy obszar tickets przez defaultValue.','Bieżącą edycję przechowuje DOM.',form],
 ['Ustaw checkbox kontaktu zaznaczony początkowo przez defaultChecked.','Reset ma przywracać domyślny stan, nie zawsze false.',form],
 ['W handlerze odczytaj currentTarget i utwórz FormData formularza.','Wartości czytasz przy wysłaniu.',form],
 ['Nadaj name wszystkim polom, których dane mają być zebrane.','Id służy etykiecie, name kluczowi danych.',form],
 ['Napisz readText odrzucające File zamiast String(file).','Get zwraca tekst, plik albo null.',model+'\n'+helpers],
 ['Odczytaj boolean kontaktu dla domyślnego checkboxa o wartości on.','Niezaznaczony checkbox nie daje wpisu.',model+'\n'+helpers],
 ['Dodaj kilka checkboxów name=tags i odczytaj getAll.','Pojedyncze get nie zwraca całej grupy.',form],
 ['Odrzuć nieznane tagi i usuń duplikaty w modelu wynikowym.','FormData nie zna unii Tag.',model+'\n'+helpers],
 ['Zademonstruj utratę powtarzających się nazw przy Object.fromEntries.','Obiekt ma jeden slot dla danego klucza.','const data=new FormData();data.append("tags","api");data.append("tags","ui");\nconsole.log(data.getAll("tags"));\nconsole.log(Object.fromEntries(data).tags);'],
 ['Sprawdź literówkę emial w get bez oczekiwania błędu tsc.','Nazwy są zwykłymi stringami.','const data=new FormData();data.set("email","anna@example.test");console.log(data.get("emial"));\n// Wynik: null. Typ FormData nie zna nazw kontrolek tego konkretnego formularza.'],
 ['Napisz readFeedback z kontrolą kształtu danych i znanego area.','To jeszcze nie kompletna walidacja e-maila czy reguł biznesowych.',model+'\n'+helpers],
 ['Nie wysyłaj pustych wymaganych tekstów nawet przy bezpośrednim wywołaniu parsera.','HTML required i parser pełnią różne role.',model+'\n'+helpers],
 ['Dodaj required i type=email do kontrolek.','Testuj także natywną blokadę submitu.',form],
 ['Zbuduj prosty input z minLength/maxLength oraz pole numeru z pattern.','Label nie powinno zostać zastąpione placeholderem.','export function QuickTicket(){return <form onSubmit={event=>event.preventDefault()}><label>Tytuł<input name="title" required minLength={5} maxLength={120}/></label><label>Numer<input name="ticketRef" pattern="DD-[0-9]+" title="Format: DD-123"/></label><button type="submit">Dodaj</button></form>;}'],
 ['Wyjaśnij, czemu natywna walidacja nie jest walidacją dowolnie utworzonego FormData.','Dane można zbudować bez kontrolek.','FormData można utworzyć i uzupełnić programowo. Typy i atrybuty HTML nie sprawdzą automatycznie każdego takiego obiektu. Czytnik danych i późniejsza walidacja schematem muszą mieć jawny kontrakt.'],
 ['Po synchronicznym sukcesie użyj form.reset dla pól niekontrolowanych.','Reset przywraca defaultValue/defaultChecked.',form],
 ['Nie resetuj po błędzie parsowania.','Użytkownik powinien zachować edycję do poprawy.',form],
 ['Wyjaśnij zachowanie resetu dla area=tickets i contactMe defaultChecked.','Nie utożsamiaj reset z wyzerowaniem wszystkiego.','Obszar wraca do tickets, checkbox kontaktu do zaznaczonego stanu. Pola bez własnej wartości domyślnej zwykle wracają do pustych. Plik zostaje wyczyszczony przez reset formularza.'],
 ['Dodaj niekontrolowany input type=file z accept.','Nie ustawiaj programowo niepustej ścieżki.',form],
 ['Napisz AttachmentInput przechowujący tylko metadane, bez kontrolowania wyboru pliku przez value.','Stan tekstu podglądu to nie to samo co kontrolowany file input.',attachment],
 ['Rozróżnij brak pliku, pusty File z brakującej selekcji i rzeczywisty plik 0-bajtowy z nazwą.','Sam size=0 nie oznacza automatycznie braku wyboru.','W tym modelu null i File z pustą nazwą oznaczają brak pliku. Nazwany plik może mieć 0 bajtów i nadal być świadomie wybranym plikiem. Ostateczne dopuszczalne typy i rozmiary określa walidacja.'],
 ['Wyjaśnij, czego accept=image/*,.pdf nie gwarantuje.','To wskazówka wyboru, nie pełne sprawdzenie danych.','Accept pomaga filtrować pliki w pickerze. Nie zastępuje sprawdzenia typu, rozmiaru ani zawartości w odpowiedniej warstwie. Nie traktuj metadanych jako dowodu bezpieczeństwa pliku.'],
 ['Wypisz informację o rozmiarze jako KiB dla dzielenia przez 1024.','Nie nazywaj każdej jednostki 1024 bajtów dziesiętnym KB.',model+'\n'+helpers],
 ['Zaprojektuj test getAll dla pustej i wieloelementowej grupy.','Porównaj wszystkie wartości, nie tylko pierwszą.','Brak zaznaczenia daje []. Api i ui dają dwa wpisy. Dwa wpisy api są nadal obecne w surowym getAll, a czytnik tej lekcji deduplikuje je do jednego tagu. Object.fromEntries nie służy do zachowania tej wielowartościowości.'],
 ['Wyjaśnij pomijanie disabled i niezaznaczonych checkboxów przy konstrukcji FormData z formularza.','Nie każda kontrolka w DOM staje się wpisem.','Zbierane są odpowiednie kontrolki posiadające name. Disabled i niezaznaczone checkboxy są pomijane. Sam readonly nie jest tym samym co disabled. Uwzględnij te reguły w modelu danych.'],
 ['Zaprojektuj test callbacku rzucającego wyjątek przed resetem.','Tak jak przy synchronicznym formularzu kontrolowanym, dalszy kod może się nie wykonać.','Po poprawnym parsowaniu wywołaj onSend, który rzuca. Reset znajdujący się po callbacku nie powinien zostać wykonany tą ścieżką. Dla requestu asynchronicznego potrzebny jest osobny kontrakt oczekiwania i błędu.'],
 ['Porównaj niekontrolowane pole po zmianie defaultValue i kontrolowany edytor rekordów.','Nie zakładaj, że zmiana props automatycznie zastępuje bieżącą edycję.','DefaultValue opisuje wartość domyślną, a nie stałe sterowanie bieżącym tekstem. Dla jawnego przełączania edytowanych danych wybierz kontrolowane value, świadomy reset albo nową instancję przez key. Szczegóły resetu będą osobną lekcją.'],
 ['Zapisz JSX/JSDoc prostego czytnika tekstu z FormData.','Nie zamieniaj File na przypadkowy napis.','/** @param {FormData} data @param {string} name */\nexport function readText(data,name){const value=data.get(name);return typeof value==="string"?value:null;}'],
 ['Oddaj FeedbackForm z powtarzanymi tagami, plikiem, parserem i resetem.','Formularz pól nie potrzebuje stanu wartości w React.','Pliki projektu:\n'+Object.entries(project).map(([file,code])=>`--- ${file} ---\n${code}`).join('\n\n')],
];
const quiz=`
Gdzie żyje bieżąca wartość niekontrolowanego inputa?|W kontrolce DOM|Zawsze w useState|W key|W CSS|React nie podaje stale bieżącego value.
Jak ustalić startowy tekst niekontrolowanego pola?|DefaultValue|Value bez onChange jako edytowalne|Checked|Key jako tekst|DefaultValue jest wartością domyślną.
Jak ustalić początkowe zaznaczenie niekontrolowanego checkboxa?|DefaultChecked|DefaultValue zawsze|Rows|Selected na label|To właściwość stanu początkowego zaznaczenia.
Czy niekontrolowane pole musi mieć onChange, aby dało się pisać?|Nie|Tak|Tylko w TSX|Tylko w JSX|DOM obsługuje edycję.
Czy dodanie handlera onChange bez value automatycznie czyni input kontrolowanym?|Nie|Tak|Tylko w TSX|Tylko przy file|Tryb zależy od sterowania wartością.
Kiedy FormData jest użyteczne?|Gdy dane potrzebne są przy submit|Wyłącznie do CSS|Tylko do kontrolowanych pól|Nigdy w React|Nie trzeba przepisywać każdego znaku przez stan.
Co przekazać do new FormData w handlerze formularza?|HTMLFormElement z currentTarget|Zawsze event.target jako dowolny span|Props dzieci|Key|Konstruktor odczytuje odpowiednie kontrolki formularza.
Po co name na polu?|Klucz danych formularza|Wyłącznie wygląd|Stan fokusu|Id komponentu|Id i name mają różne role.
Czy id bez name gwarantuje wpis w FormData?|Nie|Tak|Tylko w TSX|Tylko przy input|Id służy identyfikacji DOM, nie kluczowi zbierania danych.
Co może zwrócić data.get?|String, File lub null|Zawsze string|Zawsze number|Zawsze boolean|Czytnik musi uwzględnić kształt wyniku.
Czy get(emial) z literówką musi wywołać błąd kompilacji?|Nie|Tak|Tylko w strict|Tylko w TSX|FormData nie zna konkretnego zestawu nazw.
Co zwraca get dla brakującej nazwy?|Null|Pusty string zawsze|Undefined zawsze|False zawsze|Brak wpisu ma jawny wynik null.
Czy String(file) jest dobrą walidacją tekstowego pola e-mail?|Nie|Tak|Tylko w TSX|Tylko przy pustym|Najpierw rozróżnij tekst od pliku.
Co daje readText tej lekcji dla File w polu tekstowym?|Null|Treść pliku|Nazwę jako poprawny email|Losowy tekst|Helper odrzuca niewłaściwy typ.
Jak odczytać wszystkie wpisy o tej samej nazwie?|GetAll|Wyłącznie get|Object.is|Sort|GetAll zachowuje wiele wartości.
Czy Object.fromEntries zachowa wszystkie powtórzenia tags jako tablicę?|Nie|Tak|Tylko w TSX|Tylko przy dwóch|Obiekt ma pojedynczą wartość dla klucza.
Jaką wartość daje zaznaczony checkbox bez jawnego value?|On jako napis|True jako boolean|1 jako number|Checked jako tekst|To domyślna wartość kontrolki formularzowej.
Co daje niezaznaczony checkbox przy get jego nazwy?|Null, bo brak wpisu|False zawsze|Off zawsze|0 zawsze|Nie trafia jako zwykła wartość false.
Czy checkbox value=yes nadal daje on po zaznaczeniu?|Nie|Tak|Tylko w JSX|Tylko w TSX|Jawne value zmienia wartość formularzową.
Czy parser contactMe===on ma kontrakt zależny od wartości kontrolki?|Tak|Nie|Tylko w TSX|Tylko przy defaultChecked|Nie jest uniwersalnym czytnikiem każdego checkboxa.
Czy disabled kontrolki są zwykle zbierane przez FormData formularza?|Nie|Tak|Tylko w TSX|Tylko dla tekstu|Disabled różni się od readonly.
Czy readonly zawsze wyklucza pole z FormData?|Nie|Tak|Tylko w JSX|Tylko przy input|Pole może pozostać właściwą kontrolką formularzową.
Czy required działa także dla pól niekontrolowanych?|Tak|Nie|Tylko w TSX|Tylko z useState|Walidacja HTML nie wymaga kontrolowanego modelu.
Czy type=email może zablokować niewłaściwy submit w przeglądarce?|Tak|Nie|Tylko w JSX|Tylko w React 19|To natywne ograniczenie kontrolki.
Czy native validation zawsze wykonuje się dla ręcznie zbudowanego new FormData()?|Nie|Tak|Tylko w TSX|Tylko przy stringach|Sam obiekt danych nie uruchamia wszystkich reguł formularza.
Czy readFeedback tej lekcji jest pełnym walidatorem wszystkich adresów e-mail?|Nie|Tak|Tylko w TSX|Tylko w JSX|Sprawdza typ i niepustość, a HTML oraz późniejszy schemat mają własne zadania.
Czy parser powinien obsłużyć brak message bez wyjątku?|Tak|Nie|Tylko w TSX|Tylko przy pustym formularzu|Missing data jest normalnym przypadkiem sprawdzania wejścia.
Czy readFeedback akceptuje nieznany obszar?|Nie|Tak|Tylko w JSX|Tylko dla niepustego|Area jest sprawdzane względem listy.
Czy surowy getAll może zawierać pliki zamiast tekstowych tagów?|Tak|Nie|Tylko w TSX|Tylko dla attachment|FormData nie narzuca schematu nazw.
Czy parser tagów powinien sprawdzić każdy wpis?|Tak|Nie|Tylko pierwszy|Tylko w JSX|Nie wystarczy sprawdzić samą tablicę.
Czy brak tagów może być poprawną pustą listą?|Tak|Nie|Tylko w TSX|Tylko przy ALL|Pole tagów jest tu opcjonalne.
Czy deduplikacja tagów parsera zmienia surowe FormData?|Nie|Tak|Tylko w JSX|Tylko przy Set|Wynik jest tworzony osobno.
Czy form.reset zawsze ustawia checkbox na false?|Nie|Tak|Tylko w TSX|Tylko bez value|Wraca do defaultChecked.
Czy form.reset zawsze ustawia select na pusty string?|Nie|Tak|Tylko w JSX|Tylko przy tickets|Wraca do stanu domyślnego, np. tickets.
Czy reset wartości kontrolowanych należy utożsamiać z resetem DOM niekontrolowanych?|Nie|Tak|Tylko w TSX|Tylko dla textarea|Źródło wartości kontrolowanych jest w stanie/props.
Kiedy resetuje się FeedbackForm w rozwiązaniu?|Po normalnym powrocie synchronicznego onSend|Przed parsowaniem zawsze|Po każdej literze|W renderze|Błąd czytania nie usuwa edycji.
Czy parser null prowadzi w rozwiązaniu do resetu?|Nie|Tak|Tylko w TSX|Tylko dla email|Zwraca komunikat i przerywa ścieżkę.
Czy callback void automatycznie oczekuje na zapis pliku na serwerze?|Nie|Tak|Tylko w TSX|Tylko przy file|Upload i stan oczekiwania są poza tym kontraktem.
Czy wyjątek onSend przed resetem przerwie dalsze instrukcje?|Tak|Nie|Tylko w JSX|Tylko w TSX|Handler wymaga świadomej obsługi błędów.
Czy pole file można kontrolować przez dowolny niepusty value ustawiający ścieżkę?|Nie|Tak|Tylko w TSX|Tylko przy pdf|Przeglądarka nie pozwala wybierać dowolnego pliku takim przypisaniem.
Czy wyczyszczenie file przez reset formularza jest dozwolone?|Tak|Nie|Tylko w JSX|Tylko w TSX|Czyszczenie różni się od programowego wybrania pliku.
Czy stan metadanych pliku automatycznie kontroluje input file?|Nie|Tak|Tylko w TSX|Tylko przy onChange|Można pokazywać informacje, a wybór pozostaje niekontrolowany.
Skąd odczytać wybrany plik w onChange?|CurrentTarget.files|CurrentTarget.value jako pełną treść|Key|TextContent|FileList udostępnia pliki.
Czy brak files[0] powinien być obsłużony?|Tak|Nie|Tylko w TSX|Tylko przy pdf|Użytkownik może nie wybrać pliku albo anulować wybór.
Czy size=0 zawsze oznacza brak pliku?|Nie|Tak|Tylko w JSX|Tylko w TSX|Nazwany plik może być pusty.
Jak model tej lekcji traktuje File z pustą nazwą?|Jako brak pliku|Jako obowiązkowy załącznik|Jako email|Jako kategorię|To jawna normalizacja pustego wyboru.
Czy accept jest pełnym zabezpieczeniem typu i zawartości pliku?|Nie|Tak|Tylko w TSX|Tylko dla image|To wskazówka dla interfejsu wyboru.
Czy MIME i nazwa pliku same dowodzą bezpieczeństwa jego zawartości?|Nie|Tak|Tylko w JSX|Tylko przy pdf|Walidacja ma osobne warstwy.
Jak nazwać jednostkę 1024 bajtów używaną przez helper informacji?|KiB|Dziesiętne KB zawsze|Mb|Hz|To binarna jednostka rozmiaru.
Czy File.name jest treścią pliku?|Nie|Tak|Tylko w TSX|Tylko dla txt|To metadane nazwy.
Czy można mieć dwa wpisy o tym samym name w FormData?|Tak|Nie|Tylko w JSX|Tylko przy file|To podstawowy model wielowartościowych pól.
Co robi append dla istniejącej nazwy?|Dodaje kolejny wpis|Zawsze zastępuje wszystkie|Usuwa pole|Sortuje wartości|To różni się od set.
Co robi set dla istniejącej nazwy?|Ustawia pojedynczą wartość zastępując poprzednie wpisy|Dodaje zawsze drugi wpis|Włącza disabled|Mutuje DOM formularza automatycznie|Zmienia obiekt FormData.
Czy ręczna zmiana FormData automatycznie przepisuje wartość do DOM inputa?|Nie|Tak|Tylko w TSX|Tylko przy set|Obiekt danych i kontrolki to odrębne byty.
Czy utworzenie FormData jest tym samym co wysłanie żądania sieciowego?|Nie|Tak|Tylko w JSX|Tylko dla file|To zebranie danych, nie automatyczny upload.
Czy useId w FeedbackForm wymaga stanu dla każdej litery?|Nie|Tak|Tylko w TSX|Tylko dla email|Id służy powiązaniom kontrolek.
Czy placeholder wystarczy zamiast etykiety w niekontrolowanym formularzu?|Nie|Tak|Tylko w JSX|Tylko przy required|Tryb pola nie zmienia potrzeby dostępnej nazwy.
Czy test helpera na FormData dowodzi natywnej walidacji w przeglądarce?|Nie|Tak|Tylko w TSX|Tylko przy email|Potrzebny jest test rzeczywistych kontrolek.
Czy niekontrolowane zawsze jest lepsze od kontrolowanego?|Nie|Tak|Tylko w JSX|Tylko w TSX|Wybór zależy od potrzeby bieżących danych i interakcji.
Co potwierdza ukończenie FormData?|Poprawne odczyty, typy wartości, grupy, reset i obsługa brzegów|Samo new FormData|Wyłącznie get(email)|Tylko brak błędów TS|Model danych i zachowanie formularza muszą się zgadzać.
`.trim().split('\n').map(line=>line.split('|'));
const cases=[
 ['Odczyt email','data.get("email")','anna@example.test','null','undefined','email'],
 ['Literówka email','data.get("emial")','null','anna@example.test','undefined',''],
 ['Brak checkboxa','data.get("contactMe")','null','false','off','on'],
 ['Liczba tagów','data.getAll("tags").length','2','1','0','4'],
 ['Pierwszy tag przez get','data.get("tags")','api','ui','api,ui','null'],
 ['Ostatni tag po fromEntries','Object.fromEntries(data).tags','ui','api','api,ui','null'],
 ['GetAll kolejność','data.getAll("tags").join(",")','api,ui','ui,api','api','ui'],
 ['Brak grupy','data.getAll("missing").length','0','1','null','undefined'],
 ['Czytnik tekstu','readText(data,"email")','anna@example.test','null','undefined','email'],
 ['Czytnik brakującego','readText(data,"missing")','null','','undefined','missing'],
 ['Poprawny obszar','isArea("tickets")','true','false','tickets','undefined'],
 ['Nieznany obszar','isArea("admin")','false','true','admin','undefined'],
 ['Odczyt obszaru','readFeedback(data)?.area','tickets','other','null','undefined'],
 ['Niezaznaczony kontakt','readFeedback(data)?.contactMe','false','true','null','on'],
 ['Normalizowane tagi','readFeedback(data)?.tags.join(",")','api,ui','api','ui','undefined'],
 ['Brak załącznika','readFeedback(data)?.attachment','null','undefined','','File'],
 ['Default on kontaktu','(()=>{const next=copy();next.set("contactMe","on");return readFeedback(next)?.contactMe;})()','true','false','on','null'],
 ['Inne value kontaktu','(()=>{const next=copy();next.set("contactMe","yes");return readFeedback(next)?.contactMe;})()','false','true','yes','null'],
 ['Odrzucenie nieznanego obszaru','(()=>{const next=copy();next.set("area","admin");return readFeedback(next)===null;})()','true','false','undefined','admin'],
 ['Brak wiadomości','(()=>{const next=copy();next.delete("message");return readFeedback(next)===null;})()','true','false','undefined','message'],
 ['Biała wiadomość','(()=>{const next=copy();next.set("message","   ");return readFeedback(next)===null;})()','true','false','undefined','message'],
 ['Pusty email','(()=>{const next=copy();next.set("email","");return readFeedback(next)===null;})()','true','false','undefined','email'],
 ['Trim email','(()=>{const next=copy();next.set("email"," anna@example.test ");return readFeedback(next)?.email;})()','anna@example.test',' anna@example.test ','undefined','null'],
 ['Duplikat tagu','(()=>{const next=copy();next.append("tags","api");return readFeedback(next)?.tags.length;})()','2','3','1','0'],
 ['Nieznany tag','(()=>{const next=copy();next.append("tags","unknown");return readFeedback(next)===null;})()','true','false','undefined','unknown'],
 ['Set zastępuje tagi','(()=>{const next=copy();next.set("tags","auth");return next.getAll("tags").join(",");})()','auth','api,ui,auth','api,auth','ui,auth'],
 ['Append dopisuje tag','(()=>{const next=copy();next.append("tags","auth");return next.getAll("tags").join(",");})()','api,ui,auth','auth','api,auth','ui,auth'],
 ['Delete usuwa grupę','(()=>{const next=copy();next.delete("tags");return next.getAll("tags").length;})()','0','1','2','null'],
 ['Plik w tekstowym polu','(()=>{const next=copy();next.set("email",new File(["x"],"a.txt"));return readText(next,"email");})()','null','a.txt','x','[object File]'],
 ['Nazwany pusty plik','(()=>{const next=copy();next.set("attachment",new File([],"empty.txt"));return readFeedback(next)?.attachment?.name;})()','empty.txt','null','undefined',''],
 ['Pusty wybór pliku','(()=>{const next=copy();next.set("attachment",new File([],""));return readFeedback(next)?.attachment;})()','null','undefined','','File'],
 ['Tekst zamiast pliku','(()=>{const next=copy();next.set("attachment","fake.pdf");return readFeedback(next)===null;})()','true','false','fake.pdf','undefined'],
 ['Wielkość pliku','new File(["abc"],"a.txt").size','3','1','0','1024'],
 ['Informacja bez pliku','fileInfo(null)','Brak pliku','null','undefined','0 KiB'],
 ['Informacja o 1024 bajtach','fileInfo(new File([new Uint8Array(1024)],"a.bin"))','a.bin (1 KiB)','a.bin (1024 KiB)','1 KB','a.bin (0 KiB)'],
 ['Typ wartości tekstowej','typeof data.get("email")','string','object','number','undefined'],
 ['Has brakującego pola','data.has("missing")','false','true','null','undefined'],
 ['Has tagów','data.has("tags")','true','false','null','undefined'],
 ['Parser nie zmienia liczby tagów danych','(()=>{const next=copy();next.append("tags","api");readFeedback(next);return next.getAll("tags").length;})()','3','2','1','0'],
 ['Parser zachowuje wiadomość','readFeedback(data)?.message','Opis problemu','Opis','null','undefined'],
];
const codeBase=model+'\n'+helpers+'\nconst data=new FormData();data.set("email","anna@example.test");data.set("area","tickets");data.set("message","Opis problemu");data.append("tags","api");data.append("tags","ui");function copy():FormData{const next=new FormData();for(const [name,value] of data)next.append(name,value);return next;}';
for(const [label,expr,a,b,c,d] of cases)quiz.push([`${label}: co wypisze czytnik FormData?`,...[a,b,c,d].map(value=>value===''?'Pusty napis':value),'FormData zachowuje wpisy wielowartościowe oraz rozróżnia string, File i brak nazwy. Czytnik tworzy osobny wynik.',codeBase+'\nconsole.log('+expr+');']);
module.exports={exercises,quiz,model,helpers,form,attachment,project};
