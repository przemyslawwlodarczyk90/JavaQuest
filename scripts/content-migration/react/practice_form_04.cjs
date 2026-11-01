const model=`export type Values={title:string;description:string;priority:"LOW"|"MEDIUM"|"CRITICAL";assignee:string|null;estimateHours:number|""};
export type Errors<T>=Partial<Record<keyof T,string>>;
export type Touched<T>=Partial<Record<keyof T,boolean>>;
export const emptyValues=():Values=>({title:"",description:"",priority:"MEDIUM",assignee:null,estimateHours:""});`;
const helpers=`export function validate(values:Values):Errors<Values>{const errors:Errors<Values>={};const title=values.title.trim();if(!title)errors.title="Tytuł jest wymagany.";else if(title.length<5)errors.title="Minimum 5 znaków.";else if(title.length>120)errors.title="Maksimum 120 znaków.";if(values.assignee!==null&&!["anna","piotr","kasia"].includes(values.assignee))errors.assignee="Wybierz osobę z listy.";if(values.estimateHours!==""&&(!Number.isFinite(values.estimateHours)||values.estimateHours<0))errors.estimateHours="Podaj skończony, nieujemny czas.";if(values.priority==="CRITICAL"&&!values.description.trim())errors.description="Ticket krytyczny wymaga opisu.";return errors;}
export function visibleError<T>(errors:Errors<T>,touched:Touched<T>,submitted:boolean,field:keyof T):string|undefined{return touched[field]||submitted?errors[field]:undefined;}
export const ORDER:ReadonlyArray<keyof Values>=["title","description","assignee","estimateHours"];
export function firstError(errors:Errors<Values>):keyof Values|undefined{return ORDER.find(field=>Boolean(errors[field]));}`;
const field=`import {useId} from "react";
export function TextField({label,value,error,hint,onChange,onBlur,name}:{label:string;value:string;error?:string;hint?:string;onChange:(value:string)=>void;onBlur?:()=>void;name:string}){const id=useId();const described=[hint?\`\${id}-hint\`:null,error?\`\${id}-error\`:null].filter(Boolean).join(" ")||undefined;return <div><label htmlFor={id}>{label}</label><input id={id} name={name} value={value} onChange={event=>onChange(event.currentTarget.value)} onBlur={onBlur} aria-invalid={error?true:undefined} aria-describedby={described}/>{hint&&<small id={\`\${id}-hint\`}>{hint}</small>}{error&&<p id={\`\${id}-error\`}>{error}</p>}</div>;}`;
const form=`import {useId,useState} from "react";
${model}
${helpers}
export function ValidatedTicketForm({onCreate}:{onCreate:(values:Values)=>void}){const [values,setValues]=useState(emptyValues);const [touched,setTouched]=useState<Touched<Values>>({});const [submitted,setSubmitted]=useState(false);const id=useId();const errors=validate(values);const show=(field:keyof Values)=>visibleError(errors,touched,submitted,field);const mark=(field:keyof Values)=>setTouched(previous=>({...previous,[field]:true}));const errorProps=(field:keyof Values)=>({"aria-invalid":show(field)?true:undefined,"aria-describedby":show(field)?\`\${id}-\${field}-error\`:undefined});const message=(field:keyof Values)=>show(field)?<p id={\`\${id}-\${field}-error\`}>{show(field)}</p>:null;return <form noValidate onSubmit={event=>{event.preventDefault();setSubmitted(true);const first=firstError(errors);if(first){event.currentTarget.querySelector<HTMLElement>(\`[name="\${first}"]\`)?.focus();return;}onCreate({...values,title:values.title.trim()});}}>{submitted&&Object.keys(errors).length>0&&<p role="alert">Popraw pola: {Object.keys(errors).length}</p>}<label htmlFor={\`\${id}-title\`}>Tytuł</label><input id={\`\${id}-title\`} name="title" value={values.title} onChange={event=>setValues(v=>({...v,title:event.currentTarget.value}))} onBlur={()=>mark("title")} {...errorProps("title")}/>{message("title")}<label htmlFor={\`\${id}-description\`}>Opis</label><textarea id={\`\${id}-description\`} name="description" value={values.description} onChange={event=>{const description=event.currentTarget.value;setValues(v=>({...v,description}));}} onBlur={()=>mark("description")} {...errorProps("description")}/>{message("description")}<label htmlFor={\`\${id}-priority\`}>Priorytet</label><select id={\`\${id}-priority\`} value={values.priority} onChange={event=>{const priority=event.currentTarget.value;if(priority==="LOW"||priority==="MEDIUM"||priority==="CRITICAL")setValues(v=>({...v,priority}));}}><option>LOW</option><option>MEDIUM</option><option>CRITICAL</option></select><label htmlFor={\`\${id}-assignee\`}>Osoba</label><select id={\`\${id}-assignee\`} name="assignee" value={values.assignee??""} onBlur={()=>mark("assignee")} onChange={event=>{const assignee=event.currentTarget.value||null;setValues(v=>({...v,assignee}));}} {...errorProps("assignee")}><option value="">Nieprzypisany</option>{["anna","piotr","kasia"].map(person=><option key={person}>{person}</option>)}</select>{message("assignee")}<label htmlFor={\`\${id}-estimateHours\`}>Czas</label><input id={\`\${id}-estimateHours\`} name="estimateHours" type="number" min={0} step="any" value={values.estimateHours} onChange={event=>{const raw=event.currentTarget.value;setValues(v=>({...v,estimateHours:raw===""?"":Number(raw)}));}} onBlur={()=>mark("estimateHours")} {...errorProps("estimateHours")}/>{message("estimateHours")}<button type="submit">Utwórz</button><button type="button" onClick={()=>{setValues(emptyValues());setTouched({});setSubmitted(false);}}>Wyczyść</button></form>;}`;
// Kopiuj wartości zdarzenia przed updaterem: currentTarget jest związany z wywołaniem handlera.
const safeForm=form.replace('onChange={event=>setValues(v=>({...v,title:event.currentTarget.value}))}','onChange={event=>{const title=event.currentTarget.value;setValues(v=>({...v,title}));}}');
const full=model+'\n'+helpers;
const exercises=[
['Zdefiniuj Values i generyczny typ błędów ograniczony do nazw pól.','Partial pozwala pominąć poprawne pola.',model],
['Napisz czystą funkcję validate dla wszystkich reguł formularza.','Zwracaj nowy obiekt, nie zmieniaj values.',full],
['Rozróżnij pusty tytuł od zbyt krótkiego tytułu.','Najpierw trim i required, następnie else if.',full],
['Sprawdź tytuły długości 4, 5, 120 i 121 znaków po trim.','Granice 5 i 120 są dozwolone.',full+'\nfor(const n of [4,5,120,121])console.log(n,validate({...emptyValues(),title:"a".repeat(n)}).title??"OK");'],
['Nie obcinaj spacji w opisie w wyniku walidacji.','Trim służy sprawdzeniu, nie mutacji.',full+'\nconst value={...emptyValues(),title:"  Poprawny  ",description:"  opis  "};validate(value);console.log(value.description);'],
['Odrzuć osobę spoza anna/piotr/kasia, ale zaakceptuj null.','Pusta kontrolka select jest mapowana na null.',full],
['Pozwól na niewpisany czas oraz zero.','Nie używaj !estimateHours jako testu braku.',full],
['Odrzuć ujemny czas, NaN i obie nieskończoności.','Samo porównanie <0 nie wykryje NaN.',full],
['Dodaj zależność CRITICAL wymaga niepustego opisu.','Reguła czyta dwa pola.',full],
['Napisz visibleError z touched i submitted.','Widoczność jest oddzielona od poprawności.',full],
['Wylicz błędy podczas renderu bez useEffect i setErrors.','To synchroniczne dane pochodne.',safeForm],
['Oznacz pole jako dotknięte po blur przez niemutujący updater.','Pozostałe flagi touched zachowaj.',safeForm],
['Po błędnym submit pokaż wszystkie aktualne błędy.','Ustaw submitted, ale sprawdzaj wyliczone errors.',safeForm],
['Po poprawieniu tytułu usuń jego widoczny błąd bez dodatkowego handlera błędów.','Następny render uruchamia validate.',safeForm],
['Zbuduj TextField z unikalnym id i poprawnym htmlFor.','useId wywołaj na poziomie komponentu.',field],
['Połącz opis pomocniczy oraz błąd w aria-describedby.','Id rozdziel spacją; nie zostawiaj odwołania do nieobecnego elementu.',field],
['Ustaw aria-invalid tylko dla widocznego błędu.','Nie oznaczaj wszystkich pustych pól przy pierwszym renderze.',field],
['Dodaj podsumowanie liczby błędnych pól po submit.','Liczymy klucze obiektu błędów, nie liczbę znaków.',safeForm],
['Pozostaw przycisk wysłania aktywny przy błędnych wartościach.','Użytkownik ma móc ujawnić komunikaty.',safeForm],
['Użyj noValidate przy własnej obsłudze komunikatów.','Nie zwalnia to handlera z blokowania onCreate.',safeForm],
['Ustal jawną kolejność pierwszego błędnego pola.','Nie uzależniaj UX od przypadkowej kolejności dodawania kluczy.',full],
['Przenieś fokus na pierwsze błędne pole w bieżącym formularzu.','Szukaj w event.currentTarget; test fokusu wykonaj w przeglądarce.',safeForm],
['Nie wysyłaj payloadu przy niepustym obiekcie errors.','Submit wywołany programowo też potrzebuje walidacji.',safeForm],
['Po poprawnym submit przekaż osobny payload z przyciętym tytułem.','Opis zachowuje dokładną treść.',safeForm],
['Wyczyść wartości, touched oraz submitted jednym przyciskiem typu button.','Natywny reset nie zastąpi resetu kontrolowanego stanu.',safeForm],
['Zawęź string priorytetu z select przed zapisaniem do unii.','Sprawdź trzy obsługiwane wartości.',safeForm],
['Sprawdź ochronę przed literówką w kluczu Errors<Values>.','Użyj oczekiwanego błędu TypeScript.',model+'\nconst errors:Errors<Values>={};\n// @ts-expect-error: nieistniejące pole\nerrors.titel="Błąd";'],
['Pokaż, dlaczego Boolean(errors) nie sprawdza poprawności.','Nawet pusty obiekt jest truthy.',full+'\nconst errors=validate({...emptyValues(),title:"Gotowy ticket"});console.log(Boolean(errors),Object.keys(errors).length===0);'],
['Sprawdź powtarzalność walidacji na zamrożonych wartościach.','Czysta funkcja może otrzymać obiekt tylko do odczytu w runtime.',full+'\nconst values=Object.freeze({...emptyValues(),title:"Ticket"});console.log(JSON.stringify(validate(values))===JSON.stringify(validate(values)));'],
['Zbuduj kompletną stronę formularza i sprawdź klawiaturą blur, submit i fokus.','Testuj granice oraz CRITICAL bez opisu.',safeForm],
];
const quiz=[];
const concepts=[
['Co zwraca validate dla poprawnych danych?','Pusty obiekt błędów','true zawsze','null obowiązkowo','Nowy formularz','Kontrakt to mapa błędów; brak kluczy oznacza poprawność.'],
['Co przechowuje touched?','Informację o odwiedzonych polach','Treść komunikatów','Wysłany payload','Wartość DOM','Flagi sterują widocznością.'],
['Co oznacza submitted w tym przykładzie?','Podjęto próbę wysłania','Serwer potwierdził zapis','Trwa pobieranie','Każde pole jest poprawne','Próba wysłania ujawnia błędy.'],
['Gdzie liczyć synchroniczne błędy pochodne?','Podczas renderu z bieżących values','Wyłącznie w useEffect','Tylko raz poza komponentem','W CSS','Nie potrzeba drugiego zsynchronizowanego stanu.'],
['Kiedy pokazujemy błąd pola?','Gdy ma błąd i touched[field] lub submitted','Zawsze po montażu','Tylko po sukcesie','Nigdy przed serwerem','Widoczność i wynik walidacji to oddzielne sprawy.'],
['Co robi noValidate?','Wyłącza natywną walidację przy wysłaniu formularza','Wyłącza własny handler','Usuwa typy HTML','Gwarantuje poprawny payload','Własna walidacja musi nadal blokować błędne dane.'],
['Dlaczego zostawić aktywny submit?','Pozwala ujawnić komunikaty i przejść do błędu','Gwarantuje HTTP 200','Omija wszystkie reguły','Włącza automatyczny zapis','Nieuzasadniony disabled może ukryć sposób naprawy.'],
['Jak sprawdzić pusty obiekt błędów?','Object.keys(errors).length===0','Boolean(errors)','errors=== {}','errors.length===0','Obiekt nie ma tablicowej długości.'],
['Co wnosi Partial<Record<keyof Values,string>>?','Opcjonalne komunikaty pod znanymi kluczami','Dowolne nieznane klucze','Walidację runtime danych JSON','Wszystkie pola wymagane','Typ ogranicza nazwy, nie wykonuje reguł.'],
['Czy typ Errors waliduje odpowiedź API?','Nie, potrzebne jest sprawdzenie danych runtime','Tak, automatycznie','Tylko w production','Tylko po minifikacji','Typy nie stanowią bariery runtime.'],
['Dlaczego trim przed długością tytułu?','Spacje brzegowe nie powinny spełniać minimum','Aby zmienić opis','Aby usunąć wszystkie spacje wewnątrz','Aby zmienić litery','Mierzymy znormalizowany tytuł.'],
['Czy validate powinno zmieniać values.title?','Nie, zwraca błędy bez mutacji','Tak, zawsze','Tylko dla CRITICAL','Tylko w StrictMode','Normalizację payloadu można zrobić osobno.'],
['Jaki czas jest poprawny?','0','-1','NaN','Infinity','Zero jest skończoną nieujemną liczbą.'],
['Dlaczego <0 nie wystarczy dla czasu?','Nie wykrywa NaN ani dodatniej Infinity','Odrzuca zero','Odrzuca 2.5','Zawsze zwraca true','Number.isFinite uzupełnia kontrolę.'],
['Co oznacza pusty string czasu?','Brak wpisanej wartości','Zawsze zero godzin','Liczbę -1','Błąd TypeScript','Model pola dopuszcza etap edycji.'],
['Kiedy opis jest wymagany w regułach?','Przy CRITICAL','Przy każdym LOW','Tylko gdy czas pusty','Tylko po resecie','To zależność między polami.'],
['Co musi się stać po zmianie CRITICAL na MEDIUM?','Błąd wymaganego opisu znika przy kolejnej walidacji','Błąd pozostaje na zawsze','Trzeba przeładować stronę','Opis znika z DOM','Aktualne values wyznaczają aktualne błędy.'],
['Po co aria-invalid?','Informuje o błędnym polu','Zastępuje label','Blokuje submit','Nadaje czerwony kolor','To semantyka dostępności.'],
['Co zawiera aria-describedby?','Id elementów z opisami','Sam tekst błędu','Nazwę handlera','Selektor CSS z #','Lista identyfikatorów jest rozdzielona spacjami.'],
['Jak połączyć hint i błąd?','Wskazać oba istniejące id w aria-describedby','Wstawić dwa identyczne id','Usunąć label','Wpisać JSON błędów','Czytnik może skojarzyć obie informacje.'],
['Po co useId w TextField?','Tworzy identyfikator do powiązania etykiety i opisu','Waliduje tytuł','Zastępuje name','Sortuje błędy','Każda instancja pola potrzebuje własnych powiązań.'],
['Czy placeholder zastępuje label?','Nie','Tak zawsze','Tylko dla email','Tylko w React 19','Widoczna etykieta opisuje pole także po wpisaniu treści.'],
['Gdzie kierować fokus po błędnym submit?','Na pierwszą błędną kontrolkę według ustalonej kolejności','Zawsze na body','Na ukryty komunikat','Na koniec strony','Ułatwia to poprawianie błędów.'],
['Dlaczego querySelector w obrębie formularza?','Ogranicza wyszukiwanie do bieżącej instancji','Zwiększa tytuł','Wyłącza CSS','Usuwa potrzebę name','Na stronie mogą być inne formularze.'],
['Co musi sprawdzić test przeglądarkowy?','Rzeczywisty fokus i obsługę klawiatury','Jedynie JSON.stringify(errors)','Wyłącznie liczbę importów','Tylko typ string','SSR nie dowodzi przeniesienia fokusu DOM.'],
['Czy błąd musi być osobnym role=alert przy każdym polu?','Nie; zaplanuj ogłoszenia, aby nie mnożyć komunikatów','Tak, każdy znak musi być ogłoszony','Role zastępuje opis','Alert blokuje wpisywanie','Przykład używa jednego podsumowania alert.'],
['Jak aktualizować touched?','Nowy obiekt z zachowaniem pozostałych flag','Mutować touched w renderze','Zastąpić booleanem','Usunąć wszystkie inne flagi przy blur','Stan wymaga niemutującej aktualizacji.'],
['Czy validacja klienta zabezpiecza serwer?','Nie, serwer waliduje niezależnie','Tak, nie można jej ominąć','Tylko przy noValidate','Tylko dla TSX','Żądanie można utworzyć poza formularzem.'],
['Kiedy wywołać onCreate?','Po sprawdzeniu, że aktualne errors są puste','Przed validate','Dla każdego blur','Po każdym znaku','Błędny payload nie powinien dotrzeć do callbacku.'],
['Jak resetować formularz kontrolowany?','Przywrócić values i flagi widoczności w stanie','Tylko form.reset()','Tylko usunąć błędy z DOM','Tylko zmienić label','Wartości kontrolek wynikają ze stanu.'],
];
quiz.push(...concepts);
// Wyniki kodu sprawdzają granice każdej reguły i kombinacje widoczności, bez losowych danych.
const cases=[];
function add(label,expression,answer,wrong){cases.push([label,expression,String(answer),wrong]);}
for(const n of [0,1,4,5,6,119,120,121,130])add('Długość tytułu '+n,`validate({...base,title:"a".repeat(${n})}).title??"OK"`,n===0?'Tytuł jest wymagany.':n<5?'Minimum 5 znaków.':n>120?'Maksimum 120 znaków.':'OK',['undefined','true','false']);
for(const [label,title,answer] of [['same spacje','   ','Tytuł jest wymagany.'],['brzegi','  abc  ','Minimum 5 znaków.'],['pięć po trim','  abcde  ','OK'],['spacja wewnątrz','ab cd','OK']])add('Tytuł: '+label,`validate({...base,title:${JSON.stringify(title)}}).title??"OK"`,answer,['null','true','false']);
for(const person of [null,'anna','piotr','kasia','','Anna','adam'])add('Osoba '+JSON.stringify(person),`validate({...base,assignee:${JSON.stringify(person)}}).assignee??"OK"`,person===null||['anna','piotr','kasia'].includes(person)?'OK':'Wybierz osobę z listy.',['undefined','true','false']);
for(const value of ['""','0','-0','0.5','-0.5','10','NaN','Infinity','-Infinity'])add('Czas '+value,`validate({...base,estimateHours:${value}}).estimateHours??"OK"`,['-0.5','NaN','Infinity','-Infinity'].includes(value)?'Podaj skończony, nieujemny czas.':'OK',['undefined','true','false']);
for(const priority of ['LOW','MEDIUM','CRITICAL'])for(const description of ['','   ','Opis'])add('Opis '+priority+' '+JSON.stringify(description),`validate({...base,priority:${JSON.stringify(priority)},description:${JSON.stringify(description)}}).description??"OK"`,priority==='CRITICAL'&&!description.trim()?'Ticket krytyczny wymaga opisu.':'OK',['undefined','true','false']);
for(const touched of [false,true])for(const submitted of [false,true])for(const invalid of [false,true])add(`Widoczność touched=${touched}, submitted=${submitted}, błąd=${invalid}`,`visibleError<Values>(${invalid?'{title:"Błąd"}':'{}'},{title:${touched}},${submitted},"title")??"Ukryty"`,invalid&&(touched||submitted)?'Błąd':'Ukryty',['null','true','false']);
for(const keys of [[],['title'],['description'],['assignee'],['estimateHours'],['estimateHours','title'],['assignee','description'],['estimateHours','assignee']])add('Pierwszy błąd '+JSON.stringify(keys),`firstError(${JSON.stringify(Object.fromEntries(keys.map(key=>[key,'Błąd'])))})??"brak"`,['title','description','assignee','estimateHours'].find(key=>keys.includes(key))??'brak',['undefined','true','false']);
add('Liczba błędów początkowych','Object.keys(validate(emptyValues())).length',1,['0','2','3']);
add('Cztery błędne pola','Object.keys(validate({...base,title:"",priority:"CRITICAL",description:"",assignee:"x",estimateHours:-1})).length',4,['0','1','3']);
add('Brak mutacji tytułu','(()=>{const v={...base,title:"  Poprawny  "};validate(v);return v.title.length;})()',12,['8','10','0']);
add('Pusty obiekt jest truthy','Boolean(validate(base))',true,['false','null','undefined']);
add('Poprawność przez klucze','Object.keys(validate(base)).length===0',true,['false','null','undefined']);
add('Błąd innego pola nie oznacza title','visibleError<Values>({description:"Opis"},{title:true},false,"title")??"Ukryty"','Ukryty',['Opis','true','false']);
add('Zero zachowane','({...base,estimateHours:0}).estimateHours',0,['null','undefined','false']);
add('Opis nieprzycinany','(()=>{const v={...base,description:"  opis  "};validate(v);return v.description.length;})()',8,['4','6','0']);
add('Trim nie usuwa środka','" ab cd ".trim().length',5,['4','6','7']);
add('Pusty title ważniejszy niż długość','validate({...base,title:""}).title','Tytuł jest wymagany.',['Minimum 5 znaków.','OK','undefined']);
add('Kolejność nie zależy od kolejności kluczy','firstError({estimateHours:"Czas",title:"Tytuł"})','title',['estimateHours','undefined','description']);
add('Poprawka usuwa błąd','validate({...base,title:"Gotowy"}).title??"OK"','OK',['Minimum 5 znaków.','null','false']);
add('Submitted nie tworzy błędów','visibleError<Values>({}, {}, true, "title")??"brak"','brak',['Błąd','true','false']);
add('Niemutujący touched','(()=>{const old={title:true};const next={...old,description:true};return Object.keys(old).length+":"+Object.keys(next).length;})()','1:2',['2:2','1:1','0:2']);
add('Walidacja powtarzalna','JSON.stringify(validate(base))===JSON.stringify(validate(base))',true,['false','null','undefined']);
add('Pusty input number po konwersji','Number("")',0,['NaN','null','undefined']);
if(cases.length!==70)throw Error('Oczekiwano 70 przypadków, jest '+cases.length);
for(const [label,expression,answer,wrong]of cases)quiz.push([label+': co wypisze kod walidacji?',answer,...wrong,'Zastosuj reguły validate i warunek widoczności do dokładnie podanych danych. Funkcja zwraca nową mapę błędów, a flagi nie zmieniają jej poprawności.',full+'\nconst base:Values={...emptyValues(),title:"Poprawny"};\nconsole.log('+expression+');']);
const project={'src/Form.tsx':safeForm,'src/App.tsx':'import {ValidatedTicketForm} from "./Form";export default function App(){return <main><h1>Nowy ticket</h1><ValidatedTicketForm onCreate={values=>console.log(values)}/></main>;}','src/main.tsx':'import {createRoot} from "react-dom/client";import App from "./App";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<App/>);'};
module.exports={model,helpers,field,form:safeForm,exercises,quiz,project};
