const search='import {useId} from "react";\nexport function SearchInput({value,onChange}:{value:string;onChange:(value:string)=>void}){const id=useId();return <div><label htmlFor={id}>Szukaj</label><input id={id} type="search" value={value} placeholder="Tytuł, tag lub osoba…" onChange={event=>onChange(event.currentTarget.value)} onKeyDown={event=>{if(event.key==="Escape"&&!event.nativeEvent.isComposing){event.preventDefault();onChange("");}}}/>{value.length>0&&<button type="button" aria-label="Wyczyść wyszukiwanie" onClick={()=>onChange("")}>×</button>}</div>;}';
const parent='import {useState} from "react";\n'+search.replace('import {useId} from "react";','import {useId} from "react";')+'\nexport function Main(){const [query,setQuery]=useState("");return <main><SearchInput value={query} onChange={setQuery}/><p>Szukasz: „{query}”</p><button type="button" onClick={()=>setQuery("api")}>Szukaj: api</button></main>;}';
const normalize='export function normalizeTag(value:string):string{return value.toLowerCase().replace(/\\s+/g,"-").slice(0,20);}';
const tag='import {useState,useId} from "react";\n'+normalize+'\nexport function TagInput({onAdd}:{onAdd:(tag:string)=>void}){const [tag,setTag]=useState("");const id=useId();return <form onSubmit={event=>{event.preventDefault();if(tag.length===0)return;onAdd(tag);setTag("");}}><label htmlFor={id}>Nowy tag</label><input id={id} value={tag} onChange={event=>setTag(normalizeTag(event.currentTarget.value))}/><small>{tag.length}/20</small><button type="submit" disabled={tag.length===0}>Dodaj tag</button></form>;}';
const exercises=[
 ['Zbuduj kontrolowany tekst query w Main i wyświetl jego kopię pod polem.','Value i onChange tworzą obieg wartości.',parent],
 ['Wydziel SearchInput o kontrakcie value:string, onChange(string).','Rodzic nie musi znać zdarzenia DOM.',search],
 ['Dodaj przycisk czyszczenia widoczny dla niepustej wartości.','Przycisk akcji ma type=button i czytelną nazwę.',search],
 ['Dodaj Escape czyszczący pole poza kompozycją IME.','Odczytaj key i nativeEvent.isComposing.',search],
 ['Dodaj zewnętrzny przycisk Szukaj: api ustawiający query.','Stan znajduje się we wspólnym rodzicu.',parent],
 ['Zastąp stałe id wyszukiwarki przez useId.','Dwie instancje nie mogą mieć tego samego id.',search],
 ['Napraw value={title}, jeśli title może być undefined.','Utrzymaj konsekwentny tryb kontrolowany.','export function TitleInput({title,onChange}:{title:string|undefined;onChange:(value:string)=>void}){return <label>Tytuł<input value={title??""} onChange={event=>onChange(event.currentTarget.value)}/></label>;}'],
 ['Zapisz kontrolowane pole celowo tylko do odczytu.','Value bez zmiany jest dozwolone, gdy intencję wyrażasz readOnly.','export function ReadOnlyCode({code}:{code:string}){return <label>Kod<input value={code} readOnly/></label>;}'],
 ['Wyjaśnij różnicę value i defaultValue.','Jedna wartość kontroluje bieżące pole, druga daje początek.','Value służy kontrolowanemu polu. DefaultValue ustala wartość początkową niekontrolowanego inputa; późniejsza zmiana props nie jest tym samym co kontrolowanie bieżącego tekstu. Nie podawaj obu jako konkurujących źródeł.'],
 ['Zdiagnozuj value bez aktualizacji w onChange.','Handler musi przekazać tekst do źródła wartości.','Pole value={query} bez aktualizującego onChange będzie odtwarzać query. Jeśli jest edytowalne, zapisz bieżący tekst do stanu. Jeśli ma być tylko do odczytu, użyj readOnly.'],
 ['Wyodrębnij handler tekstowego pola z ChangeEvent<HTMLInputElement>.','Użyj currentTarget, by odczytać element handlera.','import type {ChangeEvent} from "react";\nexport function Search({value,onChange}:{value:string;onChange:(value:string)=>void}){function handleChange(event:ChangeEvent<HTMLInputElement>){onChange(event.currentTarget.value);}return <input aria-label="Szukaj" value={value} onChange={handleChange}/>;}'],
 ['Wyjaśnij, czemu type=number nie zmienia value w typ number.','Typ DOM value nadal jest tekstowy.','CurrentTarget.value jest stringiem także dla input number. Można odczytać valueAsNumber lub jawnie konwertować, lecz pusty albo nieprawidłowy wpis wymaga własnej polityki, bo wynik może być NaN.'],
 ['Zbuduj pole liczby przechowujące surowy tekst i pokazujące poprawną liczbę osobno.','Nie zamieniaj pustego napisu przypadkiem na zero.','import {useState} from "react";\nexport function Amount(){const [raw,setRaw]=useState("");const parsed=raw.trim()===""?null:Number(raw);const valid=parsed!==null&&Number.isFinite(parsed);return <label>Ilość<input type="number" value={raw} onChange={event=>setRaw(event.currentTarget.value)}/><span>{valid?parsed:"Brak poprawnej liczby"}</span></label>;}'],
 ['Napisz normalizeTag: lowercase, białe znaki na myślnik, maksymalnie 20 jednostek stringa.','Przyjmij dokładnie ten kontrakt bez automatycznego trim.',normalize],
 ['Zbuduj TagInput z normalizacją i licznikiem długości.','Kontrakt demonstracyjny może wpływać na pozycję kursora podczas edycji.',tag],
 ['Dodaj obsługę Enter przez submit formularza tagu, bez podwójnego onKeyDown+onSubmit.','Jedna droga dodania ogranicza dublowanie akcji.',tag],
 ['Po poprawnym dodaniu tagu wyczyść kontrolowany stan.','OnAdd dostaje dotychczasowy tekst, setter ustawia pusty następny stan.',tag],
 ['Nie dodawaj pustego tagu i oznacz przycisk disabled.','Sprawdzenie w handlerze uzupełnia stan kontrolki.',tag],
 ['Wyjaśnij, czy normalizacja samego ciągu spacji daje pusty tag w przyjętej funkcji.','Replace nie jest tym samym co trim.','Nie. Ciąg białych znaków zmieni się w myślnik. Jeśli domena zabrania takiego tagu, dodaj jawnie walidację lub trim w nowym kontrakcie. Nie przypisuj funkcji właściwości, których nie implementuje.'],
 ['Zaprojektuj wariant normalizowania dopiero przy submit dla lepszej swobody wpisywania.','Pole może trzymać raw, a dodawany tag być wartością wyliczoną.','import {useState} from "react";\n'+normalize+'\nexport function DeferredTag({onAdd}:{onAdd:(tag:string)=>void}){const [raw,setRaw]=useState("");return <form onSubmit={event=>{event.preventDefault();const next=normalizeTag(raw.trim());if(!next)return;onAdd(next);setRaw("");}}><label>Tag<input value={raw} onChange={event=>setRaw(event.currentTarget.value)}/></label><button type="submit">Dodaj</button></form>;}'],
 ['Przekaż TagInput callback dodający tag niemutowalnie do wybranego ticketu.','Stan listy i wybór pozostają w rodzicu.',tag+'\ntype Ticket={id:number;title:string;tags:readonly string[]};\nexport function TagsEditor({initialTickets,selectedId}:{initialTickets:readonly Ticket[];selectedId:number|null}){const [tickets,setTickets]=useState(initialTickets);const selected=tickets.find(ticket=>ticket.id===selectedId)??null;if(!selected)return <p>Wybierz ticket</p>;return <section><p>{selected.tags.join(", ")}</p><TagInput key={selected.id} onAdd={newTag=>setTickets(previous=>previous.map(ticket=>ticket.id===selected.id&&!ticket.tags.includes(newTag)?{...ticket,tags:[...ticket.tags,newTag]}:ticket))}/></section>;}'],
 ['Wyjaśnij, dlaczego query nie powinno żyć wyłącznie w SearchInput, gdy filtr i przycisk są obok.','Potrzebny jest wspólny właściciel danych.','Tabela i zewnętrzny przycisk potrzebują tej samej wartości. Trzymaj query w Main, przekaż value i callback do SearchInput. Nie utrzymuj drugiej niezależnej kopii query w dziecku.'],
 ['Zapisz JSX/JSDoc kontrolowany SearchInput.','Props onChange przyjmuje tekst, nie event.','/** @param {{value:string,onChange:(value:string)=>void}} props */\nexport function SearchInput({value,onChange}){return <label>Szukaj<input type="search" value={value} onChange={event=>onChange(event.currentTarget.value)}/></label>;}'],
 ['Wykaż błędem TS próbę przekazania liczby do tekstowego onChange.','Nie rzutuj danych na any.','type Props={onChange:(value:string)=>void};\nexport function Example({onChange}:Props){function change(){\n// @ts-expect-error Oczekiwany tekst\nonChange(42);\n}return <button type="button" onClick={change}>Zmień</button>;}'],
 ['Zaplanuj test zgodności query, inputa i tekstu pod polem.','Sprawdzaj wpisywanie, czyszczenie i programowe ustawianie.','Wpisz react, sprawdź value i opis. Kliknij wyczyść, sprawdź pusty stan i brak przycisku czyszczenia. Ustaw api z zewnętrznego przycisku, potem Escape. W przeglądarce uwzględnij zachowanie natywnego type=search.'],
 ['Zaprojektuj test normalizowania podczas edycji środka tekstu i IME.','To więcej niż prosty snapshot końcowego napisu.','Sprawdź wstawianie, usuwanie i wklejanie w środku, pozycję kursora oraz kompozycję IME. Gdy transformacja przeszkadza, przechowuj raw i normalizuj przy zatwierdzeniu albo zaprojektuj odpowiednią obsługę kompozycji.'],
 ['Wyjaśnij, czemu debouncing aktualizacji value to inny problem niż opóźnianie drogiego wyszukiwania.','Widoczny tekst powinien aktualizować się zgodnie z wpisywaniem.','Value kontrolowanego pola aktualizuj bezpośrednio z onChange. Jeśli wyszukiwanie jest drogie, oddziel tekst edycji od opóźnianego efektu lub przetwarzania. Nie zamrażaj celowo tekstu inputa czekaniem na API.'],
 ['Zaprojektuj test dwóch SearchInput bez powtarzających się id.','Jedna etykieta powinna wskazywać właściwe pole.','Wyrenderuj dwie instancje, sprawdź unikalne id i zgodne htmlFor. Zmiana jednej wartości ma zależeć od jej właściciela stanu; wspólne lub odrębne sterowanie powinno być świadomą decyzją.'],
 ['Wyjaśnij, czego brak w samym query, zanim wyszukiwarka zacznie filtrować tabelę.','Kolejna lekcja zajmie się danymi wyliczanymi.','Kontrolowane pole zapisuje tekst, ale samo nie usuwa ani nie filtruje rekordów. Potrzebne jest wyliczenie widocznej listy z danych i query. Nie mutuj źródłowych ticketów przy samym wpisywaniu.'],
 ['Oddaj kontrolowaną wyszukiwarkę, zewnętrzne api i TagInput z dodawaniem.','Zachowaj jeden stan query i jawny kontrakt normalizacji.',parent+'\n'+tag.replace('import {useState,useId} from "react";\n','')+'\nexport function Demo(){return <main><Main/><TagInput onAdd={value=>console.log(value)}/></main>;}'],
];
const quiz=`
Co kontroluje bieżącą wartość tekstowego inputa?|Value pochodzące ze stanu lub kontrolujących props|Wyłącznie placeholder|Key|Nazwa komponentu|Wartość jest źródłem prawdy dla pola kontrolowanego.
Co uzupełnia value w edytowalnym kontrolowanym polu?|OnChange aktualizujące źródło wartości|Wyłącznie className|Tylko aria-label|Losowy key|Bez zmiany wartości React odtwarza przekazany tekst.
Czy kontrolowany input musi mieć lokalny useState wewnątrz SearchInput?|Nie|Tak|Tylko w JSX|Tylko w TSX|Może być sterowany value i callbackiem rodzica.
Co przyjmuje onChange w API SearchInput tej lekcji?|String|Zawsze ChangeEvent|Number|Ticket|Komponent ukrywa szczegóły zdarzenia DOM.
Czy natywne onChange inputa i domenowe onChange(string) są tym samym kontraktem?|Nie|Tak|Tylko w JSX|Tylko w strict|Komponent tłumaczy event na tekst.
Czy wpisywanie do pola samo zmienia kontrolujący props bez callbacku rodzica?|Nie|Tak|Tylko w TSX|Tylko przy type=search|Rodzic musi zaktualizować swoje źródło.
Do czego służy defaultValue?|Początku niekontrolowanego pola|Stałego sterowania bieżącym tekstem|Nadania label|Generowania id|Nie jest zamiennikiem kontrolowanego value.
Czy podawać value i defaultValue jako dwa konkurujące źródła?|Nie|Tak|Tylko w JSX|Tylko dla search|Tryb pola powinien być spójny.
Czy undefined jako value może spowodować przejście trybów po późniejszym tekście?|Tak|Nie|Tylko w TSX|Tylko w StrictMode|Początkowy brak wartości różni się od kontrolowanego napisu.
Jak zachować kontrolowany tekst przy brakującym title?|Użyć fallbacku pustego napisu|Przekazać undefined aż do kliknięcia|Zmienić key co znak|Usunąć onChange|Value powinno zachować odpowiedni typ i tryb.
Czy null jest dobrym bieżącym value tekstowego pola kontrolowanego?|Nie|Tak|Tylko w JSX|Tylko w dark|Użyj tekstu, np. pustego fallbacku.
Czy pole z value może być świadomie tylko do odczytu?|Tak, z readOnly|Nie|Tylko w TSX|Tylko bez label|To poprawny jawny kontrakt.
Czy readOnly i disabled są tym samym zachowaniem?|Nie|Tak|Tylko w JSX|Tylko dla search|Różnią się m.in. dostępnością interakcji i formularzowym zachowaniem.
Czy placeholder zastępuje label?|Nie|Tak|Tylko w TSX|Tylko dla search|Pole potrzebuje właściwej nazwy.
Po co useId w wielokrotnie używanym SearchInput?|Unikalne powiązanie label/input|Id rekordu|Key listy|Stan query|Id należy do instancji kontrolki.
Czy dwie instancje mogą bezpiecznie używać stałego id ticket-search w jednym dokumencie?|Nie|Tak|Tylko w JSX|Tylko przy różnych value|Id nie powinno się powtarzać.
Kiedy pokazuje się przycisk czyszczenia w rozwiązaniu?|Dla value.length>0|Zawsze|Tylko dla api|Tylko w TSX|To widok pochodny od kontrolowanej wartości.
Czy same spacje spełniają value.length>0?|Tak|Nie|Tylko w JSX|Tylko w TSX|Długość surowego tekstu nie jest długością trim.
Jak wyczyścić kontrolowane pole?|Ustawić jego źródło na pusty string|Zmienić tylko placeholder|Zmienić key bez potrzeby|Zmutować props.value|Pusty tekst wraca do value po renderze.
Czy Escape może zgłosić onChange("")?|Tak|Nie|Tylko dla number|Tylko w TSX|To jawna akcja czyszczenia komponentu.
Po co uwzględnić kompozycję IME przy specjalnym klawiszu?|By nie przerywać wprowadzania tekstu|By ukryć label|By utworzyć key|By wymusić uppercase|Klawisz może być częścią kompozycji.
Czy type=search ma identyczne natywne szczegóły we wszystkich przeglądarkach?|Nie należy tego zakładać|Tak|Tylko w TSX|Tylko w JSX|Natywne kontrolki i czyszczenie wymagają testu środowiska.
Czy przycisk czyszczenia powinien przypadkiem submitować formularz?|Nie|Tak|Tylko w JSX|Tylko dla api|Dla akcji użyj type=button.
Czy znak × sam zawsze daje dobrą nazwę dostępną przycisku?|Nie|Tak|Tylko w TSX|Tylko z search|Opis aria-label wyjaśnia sens akcji.
Gdzie przechować query potrzebne tabeli i przyciskowi bocznemu?|We wspólnym rodzicu|W odizolowanym dziecku bez callbacku|W CSS|W globalnym let bez Reacta|Wspólny właściciel pozwala sterować jednym źródłem.
Czy zewnętrzny setQuery("api") może wypełnić kontrolowany input?|Tak|Nie|Tylko w JSX|Tylko po focus|Value pochodzi z tego stanu.
Czy trzeba utrzymywać drugą niezależną kopię query w SearchInput?|Nie|Tak|Tylko w TSX|Tylko dla api|Dublowanie utrudnia synchronizację.
Jaki typ ma currentTarget.value w input type=number?|String|Number zawsze|Boolean|Ticket|Typ HTML nie zmienia typu właściwości value.
Co może zwrócić valueAsNumber dla niepoprawnego lub pustego wpisu?|NaN|Zawsze 0|Null zawsze|Pusty string|Konwersja wymaga polityki walidacji.
Czy Number("") oznacza brak liczby?|Nie, wynosi 0|Tak|Zawsze NaN|Rzuca wyjątek|Pusty surowy tekst warto sprawdzić osobno.
Czy Number.isFinite jest użyteczne przy wyniku konwersji?|Tak|Nie|Tylko w TSX|Tylko dla null|Odrzuca m.in. NaN i nieskończoność.
Dlaczego czasem warto przechować raw number input jako string?|Pozwala reprezentować pusty i przejściowy wpis|Bo number nigdy nie działa|By uniknąć label|By mutować DOM|Stan edycji nie zawsze jest już poprawną liczbą domenową.
Czy string "5" plus 1 daje liczbę 6?|Nie, daje tekst 51|Tak|Tylko w TSX|Tylko w JSX|Operator + z tekstem konkatenatuje.
Czy TS pozwoli zwykle zapisać value:string do settera number?|Nie|Tak|Tylko w strict|Tylko z React 19|Typy wykrywają niezgodność.
Co robi normalizeTag w tej lekcji z wielkimi literami?|Zmienia na małe|Usuwa wszystkie litery|Zmienia na liczby|Nadaje id|To jawna transformacja kontraktu.
Co robi normalizeTag z ciągiem białych znaków?|Zamienia na jeden myślnik|Usuwa zawsze cały tag|Zostawia wszystkie spacje|Zmienia na przecinek|Wyrażenie dopasowuje jeden lub więcej białych znaków.
Czy normalizeTag w podstawowym wariancie wykonuje trim?|Nie|Tak|Tylko w TSX|Tylko dla api|Nie przypisuj funkcji dodatkowego zachowania.
Czy sam ciąg spacji po tej normalizacji może dać myślnik?|Tak|Nie|Tylko w JSX|Tylko w TSX|Replace działa także na skrajne białe znaki.
Jaki limit ma wynik normalizeTag?|20 jednostek stringa|20 rekordów|120 znaków|Bez limitu|Slice ogranicza długość tekstu.
Czy string.length zawsze oznacza liczbę postrzeganych przez człowieka znaków?|Nie|Tak|Tylko w TSX|Tylko dla emoji|UTF-16 i grafemy to różne poziomy opisu.
Czy transformacja przy każdym onChange może wpływać na kursor?|Tak|Nie|Tylko w JSX|Tylko przy uppercase|Edycja środka i kompozycja wymagają uwagi.
Co jest alternatywą dla transformacji podczas wpisywania?|Przechowywanie raw i normalizacja przy zatwierdzeniu|Mutacja inputa poza React bez synchronizacji|Usunięcie value|Losowy key|Pozwala zachować swobodniejszą edycję.
Jak w przykładzie TagInput obsługiwane jest Enter?|Przez submit formularza|Przez dwa identyczne handlery obowiązkowo|Przez key listy|Przez useEffect|Jedna ścieżka dodania ogranicza podwójne wywołanie.
Czy onKeyDown Enter i onSubmit bez koordynacji mogą dodać tag dwa razy?|Tak|Nie|Tylko w TSX|Tylko w JSX|Trzeba świadomie wybrać ścieżkę obsługi.
Co powinno nastąpić po poprawnym onAdd(tag)?|Wyczyszczenie stanu tag|Mutacja ticket props|Usunięcie label|Reset całej aplikacji|Kontrolowane pole dostaje pusty tekst.
Czy pusty tag powinien przejść przez guard dodania?|Nie|Tak|Tylko w TSX|Tylko w JSX|Handler sprawdza pustą wartość.
Czy disabled na pustym przycisku zastępuje każdy guard logiki?|Nie|Tak|Tylko w TSX|Tylko w formularzu|Logikę można wywołać inną drogą, dlatego kontrola pozostaje jawna.
Czy dodanie tagu powinno mutować tags z props?|Nie|Tak|Tylko w JSX|Tylko dla wybranego|Nowa tablica tagów zachowuje poprzednie dane.
Czy można ignorować duplikat tagu przez includes?|Tak w przyjętym kontrakcie|Nie|Tylko w TSX|Tylko dla api|Lista może zachować unikalność wartości.
Czy samo kontrolowanie query filtruje tabelę automatycznie?|Nie|Tak|Tylko w TSX|Tylko dla type=search|Potrzebne jest osobne wyliczenie widocznych rekordów.
Czy wpisanie query powinno usuwać źródłowe tickety?|Nie|Tak|Tylko w JSX|Tylko dla pustego query|Filtrowanie jest widokiem danych.
Czy debounce drogiego wyszukiwania musi opóźniać value inputa?|Nie|Tak|Tylko w TSX|Tylko z API|Stan edycji można aktualizować natychmiast, oddzielając drogie działanie.
Czy aktualizacja kontrolowanego tekstu dopiero po odpowiedzi API jest zwykle dobrym sposobem wpisywania?|Nie|Tak|Tylko dla api|Tylko w JSX|Może powodować odtwarzanie starej wartości i złą edycję.
Czy callback onChange(string) może być bezpośrednio setterem useState<string>?|Tak|Nie|Tylko w JSX|Tylko z any|Setter akceptuje tekst potrzebny callbackowi.
Czy nazwa onChange w API własnego komponentu musi oznaczać event DOM?|Nie|Tak|Tylko w TSX|Tylko przy input|Kontrakt określa komponent.
Czy JSDoc może opisać value i callback tekstowy w JSX?|Tak|Nie|Tylko bez Reacta|Tylko po zmianie rozszerzenia|Narzędzia mogą analizować typy JS z komentarzy.
Czy test samego początkowego HTML dowodzi działania wpisywania?|Nie|Tak|Tylko w TSX|Tylko z search|Potrzebna jest aktualizacja po zdarzeniu.
Czy po wyczyszczeniu do pustego tekstu ma zniknąć przycisk czyszczenia?|Tak|Nie|Tylko w TSX|Tylko w dark|Widok zależy od value.length.
Czy wpisywanie do jednej niezależnie kontrolowanej instancji musi zmieniać drugą?|Nie|Tak|Tylko w JSX|Tylko w TSX|Właściciele stanu mogą być odrębni.
Co potwierdza ukończenie kontrolowanego wyszukiwania?|Zgodność pola i query, czyszczenie, ustawienie api i dodawanie tagu|Samo placeholder|Wyłącznie onChange z logiem|Tylko brak błędów TS|Wymagane są widoczne efekty sterowania.
`.trim().split('\n').map(line=>line.split('|'));
const normalizeCases=[
 ['Wielkie API','API','api','API','Api',''],['Dwa słowa','API UI','api-ui','api ui','API-UI','apiui'],
 ['Wiele spacji','api   ui','api-ui','api---ui','api ui','apiui'],['Tabulator','api\tui','api-ui','api ui','apiui','API-UI'],
 ['Nowa linia','api\nui','api-ui','apiui','api ui','API-UI'],['Pusty tekst','','','-','null','undefined'],
 ['Same spacje','   ','-','','---','undefined'],['Spacja przed API',' API','-api','api',' API','-API'],
 ['Spacja po API','API ','api-','api','API ','API-'],['Spacje z obu stron',' API ','-api-','api','-API-','api-'],
 ['Gotowy myślnik','api-ui','api-ui','apiui','api ui','API-UI'],['Podkreślnik','API_UI','api_ui','api-ui','api ui','API_UI'],
 ['Cyfry','API42','api42','api','42','API42'],['Mieszane litery','ReAcT','react','React','REACT','re-ac-t'],
 ['Dokładnie dwadzieścia','ABCDEFGHIJKLMNOPQRST','abcdefghijklmnopqrst','ABCDEFGHIJKLMNOPQRST','abcdefghijklmnopqrs','abcdefghijklmnopqrstu'],
 ['Dwadzieścia jeden','ABCDEFGHIJKLMNOPQRSTU','abcdefghijklmnopqrst','abcdefghijklmnopqrstu','ABCDEFGHIJKLMNOPQRST','abcdefghijklmnopqrs'],
 ['Znaki interpunkcyjne','API!','api!','api','API!','api-'],['Dwa myślniki','API--UI','api--ui','api-ui','apiui','API--UI'],
 ['Cyfry ze spacją','API 42','api-42','api42','API-42','api 42'],['Polskie litery','ŻÓŁĆ','żółć','zolc','ŻÓŁĆ','żółć-'],
];
for(const [label,input,a,b,c,d] of normalizeCases)quiz.push([`${label}: jaki dokładnie tekst wypisze normalizeTag?`,...[a,b,c,d].map(value=>value===''?'Pusty napis':value),'Funkcja wykonuje lowercase, zamienia ciągi białych znaków na myślnik i stosuje slice(0,20); nie wykonuje trim.',normalize+'\nconsole.log(normalizeTag('+JSON.stringify(input)+'));']);
const valueCases=[
 ['Fallback undefined','undefinedValue??""','Pusty napis','undefined','null','0'],
 ['Fallback null','nullValue??""','Pusty napis','null','undefined','0'],
 ['Zachowanie istniejącego tekstu','query??""','api','Pusty napis','null','undefined'],
 ['Długość api','query.length','3','2','0','4'],
 ['Widoczność clear dla api','query.length>0','true','false','undefined','null'],
 ['Widoczność clear dla pustego','"".length>0','false','true','undefined','null'],
 ['Liczba z tekstu','Number("5")+1','6','51','5','NaN'],
 ['Tekst plus liczba','"5"+1','51','6','5','NaN'],
 ['Pusta konwersja','Number("")','0','NaN','null','undefined'],
 ['Wynik niepoprawnej konwersji','Number.isFinite(Number("abc"))','false','true','NaN','undefined'],
 ['Spacje przed kontrolą raw','raw.trim()===""','true','false','0','NaN'],
 ['Bezpieczna polityka pustego raw','raw.trim()===""?null:Number(raw)','null','0','NaN','undefined'],
 ['Liczba ułamkowa','Number("2.5")','2.5','25','2','NaN'],
 ['Cyfry jako tekst','typeof "42"','string','number','boolean','undefined'],
 ['Konwersja jako liczba','typeof Number("42")','number','string','boolean','undefined'],
 ['NaN i skończoność','Number.isFinite(NaN)','false','true','NaN','null'],
 ['Infinity i skończoność','Number.isFinite(Infinity)','false','true','Infinity','null'],
 ['Zero i skończoność','Number.isFinite(0)','true','false','0','null'],
 ['Normalizacja przy submit z trim','normalizeTag(" API ".trim())','api','-api-','api-','-api'],
 ['Długość wyniku z długiego tagu','normalizeTag("a".repeat(30)).length','20','30','10','0'],
];
const valueBase='const undefinedValue:string|undefined=undefined;const nullValue:string|null=null;const query="api";const raw="   ";';
for(const [label,expr,a,b,c,d] of valueCases)quiz.push([`${label}: jaki wynik wypisze wyrażenie używane przy kontrolowanym polu?`,a,b,c,d,'Oddzielaj surowy tekst, fallback pustego napisu oraz jawną konwersję lub walidację.',normalize+'\n'+valueBase+'\nconsole.log('+expr+');']);
module.exports={exercises,quiz,search,parent,normalize,tag};
