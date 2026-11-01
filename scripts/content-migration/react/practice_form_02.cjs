const model='export const PRIORITIES=["LOW","MEDIUM","HIGH","CRITICAL"] as const;\nexport type Priority=(typeof PRIORITIES)[number];\nexport const PEOPLE=["anna","piotr","kasia"] as const;\nexport type Person=(typeof PEOPLE)[number];\nexport const KINDS=["BUG","TASK","QUESTION"] as const;\nexport type Kind=(typeof KINDS)[number];\nexport const TAGS=["api","ui","export","auth"] as const;\nexport type Tag=(typeof TAGS)[number];\nexport type Values={title:string;description:string;priority:Priority;assignee:Person|null;kind:Kind;fromCustomer:boolean;tags:readonly Tag[];estimateHours:number|"";dueDate:string};\nexport function emptyValues():Values{return {title:"",description:"",priority:"MEDIUM",assignee:null,kind:"TASK",fromCustomer:false,tags:[],estimateHours:"",dueDate:""};}';
const helpers='export function isPriority(value:string):value is Priority{return PRIORITIES.some(priority=>priority===value);}\nexport function isTag(value:string):value is Tag{return TAGS.some(tag=>tag===value);}\nexport function parseAssignee(value:string):Person|null|undefined{return value===""?null:PEOPLE.find(person=>person===value);}\nexport function parseEstimate(raw:string):number|""|null{if(raw.trim()==="")return "";const value=Number(raw);return Number.isFinite(value)?value:null;}\nexport function validEstimate(value:number|""):boolean{return value===""||(Number.isFinite(value)&&value>=0&&Number.isInteger(value*2));}\nexport function updateValue<K extends keyof Values>(current:Values,key:K,value:Values[K]):Values{return {...current,[key]:value};}\nexport function toggleTag(current:Values,tag:Tag):Values{return {...current,tags:current.tags.includes(tag)?current.tags.filter(value=>value!==tag):[...current.tags,tag]};}\nexport function readSelectedTags(options:ArrayLike<{value:string}>):Tag[]{return [...new Set(Array.from(options,option=>option.value).filter(isTag))];}\nexport type TicketDraft=Omit<Values,"estimateHours">&{estimateHours:number|null};\nexport function toDraft(values:Values):TicketDraft{return {...values,title:values.title.trim(),tags:[...values.tags],estimateHours:values.estimateHours===""?null:values.estimateHours};}';
const fields='import {useId} from "react";\nimport type {Dispatch,SetStateAction} from "react";\n'+model+'\n'+helpers+'\nexport function TicketFields({form,setForm}:{form:Values;setForm:Dispatch<SetStateAction<Values>>}){const id=useId();function set<K extends keyof Values>(key:K,value:Values[K]){setForm(previous=>updateValue(previous,key,value));}return <><label htmlFor={`${id}-priority`}>Priorytet</label><select id={`${id}-priority`} name="priority" value={form.priority} onChange={event=>{const value=event.currentTarget.value;if(isPriority(value))set("priority",value);}}>{PRIORITIES.map(value=><option key={value} value={value}>{value}</option>)}</select><label htmlFor={`${id}-assignee`}>Osoba</label><select id={`${id}-assignee`} name="assignee" value={form.assignee??""} onChange={event=>{const value=parseAssignee(event.currentTarget.value);if(value!==undefined)set("assignee",value);}}><option value="">— nikt —</option>{PEOPLE.map(person=><option key={person} value={person}>{person}</option>)}</select><fieldset><legend>Typ zgłoszenia</legend>{KINDS.map(kind=><label key={kind}><input type="radio" name={`${id}-kind`} value={kind} checked={form.kind===kind} onChange={()=>set("kind",kind)}/>{kind}</label>)}</fieldset><label><input type="checkbox" name="fromCustomer" checked={form.fromCustomer} onChange={event=>set("fromCustomer",event.currentTarget.checked)}/>Zgłoszenie klienta</label><fieldset><legend>Tagi</legend>{TAGS.map(tag=><label key={tag}><input type="checkbox" name="tags" value={tag} checked={form.tags.includes(tag)} onChange={()=>setForm(previous=>toggleTag(previous,tag))}/>{tag}</label>)}</fieldset><label htmlFor={`${id}-estimate`}>Szacowany czas (h)</label><input id={`${id}-estimate`} name="estimateHours" type="number" min={0} step={0.5} value={form.estimateHours} onChange={event=>{const value=parseEstimate(event.currentTarget.value);if(value!==null)set("estimateHours",value);}}/><label htmlFor={`${id}-date`}>Termin</label><input id={`${id}-date`} name="dueDate" type="date" value={form.dueDate} onChange={event=>set("dueDate",event.currentTarget.value)}/></>;}';
const form=fields.replace('import {useId} from "react";','import {useId,useState} from "react";')+'\nexport function FullTicketForm({onCreate}:{onCreate:(values:TicketDraft)=>void}){const [form,setForm]=useState<Values>(emptyValues);const id=useId();return <form onSubmit={event=>{event.preventDefault();if(!form.title.trim()||!validEstimate(form.estimateHours))return;onCreate(toDraft(form));setForm(emptyValues());}}><label htmlFor={`${id}-title`}>Tytuł</label><input id={`${id}-title`} name="title" value={form.title} required onChange={event=>{const value=event.currentTarget.value;setForm(previous=>updateValue(previous,"title",value));}}/><label htmlFor={`${id}-description`}>Opis</label><textarea id={`${id}-description`} name="description" rows={4} value={form.description} onChange={event=>{const value=event.currentTarget.value;setForm(previous=>updateValue(previous,"description",value));}}/><TicketFields form={form} setForm={setForm}/><button type="submit">Utwórz ticket</button></form>;}';
const multi=model+'\n'+helpers+'\nexport function MultiTagSelect({value,onChange}:{value:readonly Tag[];onChange:(tags:Tag[])=>void}){return <label>Tagi<select multiple value={value} onChange={event=>onChange(readSelectedTags(event.currentTarget.selectedOptions))}>{TAGS.map(tag=><option key={tag} value={tag}>{tag}</option>)}</select></label>;}';
const project={'src/TicketForm.tsx':form,'src/App.tsx':'import {useState} from "react";\nimport {FullTicketForm} from "./TicketForm";\nimport type {TicketDraft} from "./TicketForm";\nexport default function App(){const [created,setCreated]=useState<TicketDraft|null>(null);return <main><h1>Nowe zgłoszenie</h1><FullTicketForm onCreate={setCreated}/>{created&&<section><h2>Utworzono</h2><pre>{JSON.stringify(created,null,2)}</pre></section>}</main>;}','src/main.tsx':'import {createRoot} from "react-dom/client";\nimport App from "./App";\nconst container=document.getElementById("root");if(!container)throw Error("Brak root");createRoot(container).render(<App/>);'};
const exercises=[
 ['Zdefiniuj model pól wyboru, tagów, czasu i terminu z właściwymi typami.','Tekst, boolean, null i liczba to różne wartości UI.',model],
 ['Utwórz świeże wartości domyślne: MEDIUM, TASK, nikt, brak tagów i pusty czas.','Fabryka nie współdzieli przypadkowo mutowanej tablicy tags.',model],
 ['Zbuduj kontrolowany select priorytetu z value na select.','Nie ustawiaj selected na każdym option.',fields],
 ['Zawęź string priorytetu przez guard.','Unia w TS nie waliduje dowolnego tekstu DOM.',model+'\n'+helpers],
 ['Zmapuj pustą opcję osoby na null, a null na pusty value select.','Granica DOM i modelu jest jawna.',fields],
 ['Zignoruj nieznany login osoby zamiast rzutować dowolny tekst.','Parser zwraca undefined dla wartości spoza listy.',model+'\n'+helpers],
 ['Zbuduj radio BUG/TASK/QUESTION z checked i wspólną nazwą w danej grupie.','Każda opcja ustawia konkretny literal z listy.',fields],
 ['Dodaj fieldset i legend dla grupy typu.','Legenda nadaje sens całej grupie.',fields],
 ['Obsłuż fromCustomer przez checked i currentTarget.checked.','Value checkboxa nie oznacza boolean.',fields],
 ['Zbuduj grupę checkboxów tagów z członkostwem w tablicy.','Checked wynika z includes.',fields],
 ['Aktualizuj tagi funkcyjnie na najnowszym previous, nie z zamkniętego form.tags.','Dwa zakolejkowane kliknięcia nie powinny się nadpisywać.',fields],
 ['Przetestuj dwa kolejne toggle api i ui z pustego modelu.','Oba tagi mają pozostać. ',model+'\n'+helpers+'\nconsole.log(toggleTag(toggleTag(emptyValues(),"api"),"ui").tags.join(","));'],
 ['Odznacz tag przez filter, bez mutacji poprzedniej tablicy.','Przy ponownym toggle obecny tag znika.',model+'\n'+helpers],
 ['Obsłuż pusty czas jako pusty napis zamiast Number("")=0.','Brak podanej wartości różni się od zera.',model+'\n'+helpers],
 ['Oddziel parsowanie liczby od walidacji min i kroku.','Nie każda skończona liczba jest poprawnym czasem finalnym.',model+'\n'+helpers],
 ['Dodaj min=0 i step=0.5 oraz guard przy submit.','Walidacja HTML i logika callbacku to dwie warstwy.',form],
 ['Wyjaśnij, dlaczego 2.25 nie spełnia kroku 0.5 w tym modelu.','Wielokrotność połowy ma całkowite value*2.','2.25*2=4.5 nie jest całkowite. 2.5*2=5 spełnia krok. Dla innych kroków dziesiętnych trzeba uważać na precyzję float; tutaj połówki są dokładnie reprezentowane.'],
 ['Nadaj grupom radio unikalne nazwy między instancjami TicketFields.','Wewnątrz jednej grupy wszystkie opcje dzielą nazwę.',fields],
 ['Zbuduj kontrolowany textarea opisu.','W React używaj value, a nie stale aktualizowanych dzieci. ',form],
 ['Napisz select multiple z tablicą value i odczytem selectedOptions.','Kolejność pochodzi z opcji DOM, a nie z historii kliknięć.',multi],
 ['Odfiltruj nieznane wartości z selectedOptions i usuń duplikaty.','ReadSelectedTags zwraca wyłącznie Tag[].',model+'\n'+helpers],
 ['Zbuduj kontrolowany input date jako tekst formatu YYYY-MM-DD lub pusty tekst.','Nie przeliczaj go bez potrzeby na strefę czasową.',fields],
 ['Przekształć brak czasu z UI do null w TicketDraft.','Model edycji i payload mogą mieć różny kształt.',model+'\n'+helpers],
 ['Zachowaj wszystkie pola i skopiuj tablicę tags w payloadzie.','Wyślij jawny model, bez mutowania formularza.',model+'\n'+helpers],
 ['Wykaż błędem TS tekst tak w boolean fromCustomer.','Generyk wiąże key z typem pola.',model+'\n'+helpers+'\n// @ts-expect-error FromCustomer wymaga boolean\nconst invalid=updateValue(emptyValues(),"fromCustomer","tak");'],
 ['Wykaż błędem TS unknown priorytet DONE.','To nie jest typ Priority.',model+'\n// @ts-expect-error Niedozwolony priorytet\nconst invalid:Priority="DONE";'],
 ['Zaprojektuj alternatywę z surowym stringiem czasu, gdy edycja ułamków wymaga pełnej swobody.','Parsowanie przy zapisie może być lepsze dla przejściowych stanów edycji.','Trzymaj estimateRaw:string, aktualizuj bez transformacji i dopiero przy submit parsuj oraz waliduj. Rozróżnij pusty tekst, błędny wpis i poprawne zero. To inny poprawny model niż number lub pusty napis.'],
 ['Zaplanuj test klawiatury grup radio i checkboxów.','Testuj prawdziwe natywne elementy.','Tab do grupy, strzałki zmieniają opcję zgodnie z natywnym modelem, Spacja przełącza checkbox. Klik label działa na właściwe pole. Sprawdź legendy, widoczny fokus i izolację nazw grup.'],
 ['Zapisz JSX/JSDoc pola checkbox.','Do callbacku przekaż checked jako boolean.','/** @param {{checked:boolean,onChange:(checked:boolean)=>void}} props */\nexport function CustomerFlag({checked,onChange}){return <label><input type="checkbox" checked={checked} onChange={event=>onChange(event.currentTarget.checked)}/>Zgłoszenie klienta</label>;}'],
 ['Oddaj pełny formularz ze wszystkimi typami pól i poprawnym TicketDraft.','Jeden obiekt stanu jest źródłem kontrolek.','Pliki projektu:\n'+Object.entries(project).map(([file,code])=>`--- ${file} ---\n${code}`).join('\n\n')],
];
const quiz=`
Jak kontrolować checkbox boolean w React?|Checked i onChange z checked|Value jako jedyne źródło zaznaczenia|Selected|Children|Stan zaznaczenia jest logiczny.
Czy value checkboxa ma ten sam sens co checked?|Nie|Tak|Tylko w TSX|Tylko w JSX|Value jest wartością formularzową, checked stanem zaznaczenia.
Jaki typ ma currentTarget.checked?|Boolean|String|Number|File|To właściwość logiczna inputa.
Czy fromCustomer="tak" pasuje do boolean w TS?|Nie|Tak|Tylko w JSX|Tylko w strict|Tekst nie jest wartością logiczną modelu.
Gdzie ustawiać wybraną wartość kontrolowanego select?|Na select przez value|Wyłącznie selected na option|Na label|W key|Select jest kontrolowany jako całość.
Czy wartość pojedynczego select z DOM jest tekstem?|Tak|Nie|Tylko w TSX|Tylko dla priority|Trzeba ją dopasować do domeny.
Co daje guard isPriority?|Zawężenie poprawnej wartości|Automatyczną zmianę CSS|Nowy setter|Dowolne rzutowanie runtime|Guard porównuje z listą dozwolonych priorytetów.
Czy as Priority wykonuje walidację runtime?|Nie|Tak|Tylko w strict|Tylko w TSX|Asercja nie sprawdza tekstu.
Jak zapisać brak assignee w modelu?|Null|Pusty DOM event|Zawsze anna|Undefined obowiązkowo|To jawny brak przypisania.
Jak pokazać null assignee w kontrolowanym select?|Pustym stringiem value|Przez value=null zawsze|Przez key=null|Przez selected na label|Granica UI mapuje brak na pustą opcję.
Co parser osoby zwraca dla pustej opcji?|Null|Undefined|Anna|Pusty obiekt|To poprawny wybór nikt.
Co parser osoby zwraca dla nieznanego loginu?|Undefined jako odrzuconą wartość|Null zawsze|Dowolny string jako Person|Anna|Nieznany tekst różni się od jawnego nikt.
Co łączy opcje jednej grupy radio w DOM?|Wspólna nazwa i właściciel formularza|Wspólny key|Id każdego pola identyczne|Jednakowy tekst|Name określa grupę kontrolki.
Czy id trzech radio ma być identyczne?|Nie|Tak|Tylko w JSX|Tylko z label|Id i nazwa grupy to różne pojęcia.
Jak kontrolować konkretną opcję radio?|Checked porównujące form.kind z opcją|Value bez checked jako jedyne źródło|Selected|Rows|Stan modelu wybiera jedną opcję.
Czy radio callback może ustawić literal z tablicy zamiast rzutować event.value?|Tak|Nie|Tylko w TSX|Tylko w JSX|Zamknięta opcja jest już poprawnym Kind.
Po co fieldset i legend?|Nazwa i struktura grupy pól|Automatyczna walidacja API|Nowe id|Ukrycie pól|Legenda wyjaśnia wspólny sens opcji.
Jak określić zaznaczenie checkboxa tagu?|Form.tags.includes(tag)|Długością title|Wyłącznie value tagu|Key|Członkostwo jest wyliczane z listy.
Jak niemutowalnie odznaczyć tag?|Filter usuwający jego wartość|Splice na stanie|Delete po indeksie bez kopii|Zmiana key|Nowy kontener chroni starą wersję.
Jak niemutowalnie zaznaczyć nowy tag?|Nowa tablica z poprzednimi i tagiem|Push na starej tablicy|Zmiana title|Sort na props|Wynik jest nową wersją tags.
Czy toggle oparte na zamkniętym form.tags może zgubić kolejne zakolejkowane zmiany?|Tak|Nie|Tylko w JSX|Tylko w TSX|Lepiej liczyć z previous wewnątrz updatera.
Czy funkcyjny updater tagów powinien korzystać z previous.tags?|Tak|Nie|Tylko dla api|Tylko w JSX|To najwłaściwsze wejście do kolejnej zmiany.
Co oznacza value select multiple?|Tablicę wartości zaznaczonych|Wyłącznie pierwszy string|Boolean|Stan disabled|Model kontrolowany jest wielowartościowy.
Jak odczytać wszystkie zaznaczone opcje?|Z selectedOptions|Wyłącznie z value pojedynczego select|Z key|Z rows|To kolekcja opcji zaznaczonych w DOM.
Czy selectedOptions opisuje zawsze kolejność kliknięć?|Nie|Tak|Tylko w TSX|Tylko dla tags|Zwykle odpowiada kolejności opcji w kontrolce.
Czy warto odfiltrować nieznane tagi z multi-select?|Tak|Nie|Tylko w JSX|Tylko dla api|DOM i dane zewnętrzne mogą wyjść poza domenę.
Jak kontrolować textarea?|Value i onChange|Selected|Checked|Key jako treść|Textarea jest kontrolką tekstową.
Czy rows określa maksymalną liczbę znaków?|Nie|Tak|Tylko w TSX|Tylko w JSX|To liczba widocznych wierszy.
Jaki typ ma value inputa number odczytany z DOM?|String|Number zawsze|Boolean|Date|Typ pola nie zmienia właściwości value.
Co daje Number("")?|0|NaN|Null|Undefined|Dlatego puste pole trzeba obsłużyć jawnie.
Co może dać valueAsNumber dla pustej kontrolki?|NaN|Zawsze 0|Pusty string|Null zawsze|To inny odczyt niż tekstowe value.
Czy pusty czas i zero godzin to ten sam stan biznesowy?|Nie|Tak|Tylko w TSX|Tylko w JSX|Brak podania różni się od wartości zero.
Jaki model edycji czasu użyto w rozwiązaniu?|Number lub pusty napis|Zawsze boolean|Zawsze Date|Zawsze File|Pozwala zachować jawnie pustą kontrolkę.
Czy parseEstimate samo wymusza nieujemny krok 0.5?|Nie|Tak|Tylko w TSX|Tylko w JSX|Parsowanie i finalna walidacja są odrębne.
Co sprawdza validEstimate?|Brak wartości lub skończoną nieujemną wielokrotność 0.5|Wyłącznie niepusty string|Zawsze dodatnie id|Wyłącznie maksymalną długość|To jawny kontrakt czasu finalnego.
Czy -1 jest skończoną liczbą, ale niepoprawnym finalnym czasem?|Tak|Nie|Tylko w TSX|Tylko w JSX|Skończoność nie oznacza spełnienia minimum.
Czy 2.25 spełnia krok 0.5?|Nie|Tak|Tylko w TSX|Tylko w JSX|Podwojony wynik nie jest całkowity.
Czy 2.5 spełnia krok 0.5?|Tak|Nie|Tylko w TSX|Tylko w JSX|To pięć połówek.
Czy min i step są wyłącznie dokumentacją bez zachowania HTML?|Nie|Tak|Tylko w TSX|Tylko w JSX|Mogą wpływać na natywną walidację i interakcję.
Czy natywna walidacja zastępuje wszystkie sprawdzenia danych callbacku?|Nie|Tak|Tylko w TSX|Tylko przy number|Logikę można wywołać także inną drogą.
Jaki format tekstu ma typowy input date?|YYYY-MM-DD|Zawsze timestamp ISO ze strefą|Dowolny polski opis|Number|Kontrolka przekazuje datę kalendarzową jako tekst.
Czy date value jest automatycznie obiektem Date?|Nie|Tak|Tylko w TSX|Tylko w JSX|To string kontrolki.
Czy datę kalendarzową trzeba zawsze zamieniać na moment UTC podczas edycji?|Nie|Tak|Tylko w TSX|Tylko w JSX|To może zmienić znaczenie; model zależy od domeny.
Czy dueDate="" jest poprawnym pustym stanem opcjonalnego date input?|Tak|Nie|Tylko w TSX|Tylko przy null|Pusty tekst zachowuje tryb kontrolowany.
Czy każdy typ kontrolki da się obsłużyć samym event.value jako tekst?|Nie|Tak|Tylko w TSX|Tylko z form|Checkbox wymaga checked, a inne pola mają własny kontrakt.
Czy Dispatch<SetStateAction<Values>> pozwala dziecku na updater funkcyjny?|Tak|Nie|Tylko w JSX|Tylko dla title|To pełny typ settera useState.
Czy generyczny updateValue pilnuje boolean dla fromCustomer?|Tak|Nie|Tylko w JSX|Tylko przy label|Typ wartości wynika z klucza modelu.
Czy generyk sam waliduje ujemną liczbę estimateHours?|Nie|Tak|Tylko w strict|Tylko w TSX|Number nie koduje wszystkich ograniczeń domenowych.
Co oznacza estimateHours=null w TicketDraft rozwiązania?|Nie podano czasu|Zero godzin|Błąd zawsze|Tekst null|Payload ma jawny brak wartości liczbowej.
Czy UI i payload muszą zawsze mieć identyczny typ pustej wartości?|Nie|Tak|Tylko w TSX|Tylko w JSX|Adapter może mapować pusty tekst na null.
Czy tags w payloadzie są w rozwiązaniu skopiowane?|Tak|Nie|Tylko w JSX|Tylko przy api|Nowa tablica oddziela payload od wersji edycji.
Czy toDraft zmienia oryginalny form?|Nie|Tak|Tylko w TSX|Tylko przy trim|Tworzy nowy obiekt.
Czy emptyValues daje zawsze tę samą referencję tags?|Nie|Tak|Tylko w TSX|Tylko w JSX|Fabryka tworzy nową tablicę przy wywołaniu.
Czy reset powinien przywrócić także checkboxy i radio przez stan?|Tak|Nie|Tylko w TSX|Tylko przy submit|Kontrolki odzwierciedlają nowy model.
Czy sama zmiana CSS checkboxa zastępuje zmianę checked?|Nie|Tak|Tylko w JSX|Tylko w dark|Wygląd i stan kontrolki to różne rzeczy.
Czy strzałki w grupie radio warto testować natywnie?|Tak|Nie|Tylko w TSX|Tylko na desktopie|To istotna część obsługi klawiaturą.
Czy klik label checkboxa powinien działać na właściwe pole?|Tak|Nie|Tylko w TSX|Tylko z id|Powiązanie etykiety jest częścią używalności.
Czy dwie niezależne grupy radio w tym samym formularzu powinny dzielić tę samą nazwę?|Nie|Tak|Tylko w TSX|Tylko w JSX|Mogłyby stać się jedną grupą DOM.
Czy surowy string czasu bywa lepszy dla przejściowej edycji ułamków?|Tak|Nie|Tylko w TSX|Tylko w JSX|Model edycji można oddzielić od modelu liczby po zatwierdzeniu.
Co potwierdza ukończenie lekcji typów pól?|Poprawne value/checked, grupy, puste liczby i payload|Samo dodanie inputów|Tylko opis pól|Wyłącznie brak błędów TS|Wszystkie typy kontrolek muszą aktualizować właściwe dane.
`.trim().split('\n').map(line=>line.split('|'));
const cases=[
 ['Domyślny priorytet','emptyValues().priority','MEDIUM','LOW','HIGH','CRITICAL'],
 ['Domyślny typ','emptyValues().kind','TASK','BUG','QUESTION','undefined'],
 ['Domyślna osoba','emptyValues().assignee','null','anna','undefined',''],
 ['Domyślna flaga','emptyValues().fromCustomer','false','true','on','undefined'],
 ['Domyślny czas','emptyValues().estimateHours','','0','null','NaN'],
 ['Pusta opcja osoby','parseAssignee("")','null','undefined','anna',''],
 ['Istniejąca osoba','parseAssignee("anna")','anna','null','undefined','Anna'],
 ['Nieznana osoba','parseAssignee("adam")','undefined','null','adam','anna'],
 ['Wielkość liter loginu','parseAssignee("Anna")','undefined','anna','null','Anna'],
 ['Poprawny priorytet','isPriority("HIGH")','true','false','HIGH','undefined'],
 ['Nieznany priorytet','isPriority("DONE")','false','true','DONE','undefined'],
 ['Pusty czas parsera','parseEstimate("")','','0','null','NaN'],
 ['Biały czas parsera','parseEstimate("   ")','','0','null','NaN'],
 ['Zero godzin','parseEstimate("0")','0','','null','NaN'],
 ['Połówka','parseEstimate("2.5")','2.5','25','2','null'],
 ['Ujemny czas parsowany','parseEstimate("-1")','-1','null','0','1'],
 ['Niepoprawny tekst czasu','parseEstimate("abc")','null','NaN','0','undefined'],
 ['Infinity czasu','parseEstimate("Infinity")','null','Infinity','0','undefined'],
 ['Pusty czas walidowany','validEstimate("")','true','false','0','null'],
 ['Zero poprawne','validEstimate(0)','true','false','0','null'],
 ['Połówka poprawna','validEstimate(2.5)','true','false','2.5','null'],
 ['Ćwiartka niepoprawna','validEstimate(2.25)','false','true','2.25','null'],
 ['Ujemny niepoprawny','validEstimate(-1)','false','true','-1','null'],
 ['Dwa tagi kolejno','toggleTag(toggleTag(emptyValues(),"api"),"ui").tags.join(",")','api,ui','ui','api',''],
 ['Ponowne odznaczenie','toggleTag(toggleTag(emptyValues(),"api"),"api").tags.length','0','1','2','undefined'],
 ['Brak mutacji starych tagów','(()=>{const first=toggleTag(emptyValues(),"api");toggleTag(first,"ui");return first.tags.join(",");})()','api','api,ui','ui',''],
 ['Guard tagu','isTag("auth")','true','false','auth','undefined'],
 ['Nieznany tag','isTag("backend")','false','true','backend','undefined'],
 ['Wiele zaznaczonych','readSelectedTags([{value:"api"},{value:"ui"}]).join(",")','api,ui','ui,api','api',''],
 ['Nieznany wpis w wielu opcjach','readSelectedTags([{value:"api"},{value:"x"}]).join(",")','api','api,x','x',''],
 ['Duplikaty wartości opcji','readSelectedTags([{value:"api"},{value:"api"}]).length','1','2','0','undefined'],
 ['Brak opcji','readSelectedTags([]).length','0','1','NaN','undefined'],
 ['Pusty czas do payloadu','toDraft(emptyValues()).estimateHours','null','','0','undefined'],
 ['Zero czasu do payloadu','toDraft(updateValue(emptyValues(),"estimateHours",0)).estimateHours','0','null','','undefined'],
 ['Zmiana boolean','updateValue(emptyValues(),"fromCustomer",true).fromCustomer','true','false','on','undefined'],
 ['Brak osoby do DOM','emptyValues().assignee??""','','null','undefined','anna'],
 ['Data jako tekst','updateValue(emptyValues(),"dueDate","2026-10-28").dueDate','2026-10-28','28.10.2026','null','undefined'],
 ['Nowa tablica defaultów','Object.is(emptyValues().tags,emptyValues().tags)','false','true','undefined','null'],
 ['Typ pustego czasu UI','typeof emptyValues().estimateHours','string','number','object','undefined'],
 ['Kopia tagów payloadu','(()=>{const values=toggleTag(emptyValues(),"api");return Object.is(toDraft(values).tags,values.tags);})()','false','true','undefined','null'],
];
const codeBase=model+'\n'+helpers;
for(const [label,expr,a,b,c,d] of cases)quiz.push([`${label}: co wypisze model kontrolek?`,...[a,b,c,d].map(value=>value===''?'Pusty napis':value),'Rozróżniaj tekst DOM, checked boolean, brak osoby, pusty czas UI i walidację danych finalnych.',codeBase+'\nconsole.log('+expr+');']);
module.exports={exercises,quiz,model,helpers,fields,form,multi,project};
