// Źródła API: https://zod.dev/basics https://zod.dev/api https://github.com/react-hook-form/resolvers
const schema=`import {z} from "zod";
export const ticketSchema=z.object({title:z.string().trim().min(5,"Minimum 5 znaków.").max(120,"Maksimum 120 znaków."),description:z.string(),priority:z.enum(["LOW","MEDIUM","HIGH","CRITICAL"]),assignee:z.enum(["anna","piotr","kasia"]).nullable(),estimateHours:z.number().nonnegative("Czas nieujemny.")}).refine(values=>values.priority!=="CRITICAL"||values.description.trim()!=="",{message:"Opis wymagany.",path:["description"]});
export type Input=z.input<typeof ticketSchema>;export type Output=z.output<typeof ticketSchema>;export type Values=z.infer<typeof ticketSchema>;
export const empty:Input={title:"",description:"",priority:"MEDIUM",assignee:null,estimateHours:0};
export function parseDraft(raw:string|null):Output|null{if(raw===null)return null;try{const input:unknown=JSON.parse(raw);const result=ticketSchema.safeParse(input);return result.success?result.data:null;}catch{return null;}}
export function readDraft(storage:Pick<Storage,"getItem">):Output|null{try{return parseDraft(storage.getItem("ticket-draft"));}catch{return null;}}
export function saveDraft(storage:Pick<Storage,"setItem">,input:unknown):boolean{const result=ticketSchema.safeParse(input);if(!result.success)return false;try{storage.setItem("ticket-draft",JSON.stringify(result.data));return true;}catch{return false;}}`;
const form=`import {useId} from "react";import {useForm} from "react-hook-form";import {zodResolver} from "@hookform/resolvers/zod";
${schema}
export function SchemaForm({onCreate}:{onCreate:(values:Output)=>Promise<void>}){const id=useId();const {register,handleSubmit,reset,setError,clearErrors,formState:{errors,isSubmitting}}=useForm<Input,unknown,Output>({resolver:zodResolver(ticketSchema),defaultValues:empty});async function submit(values:Output){clearErrors("root.server");try{await onCreate(values);reset();}catch{setError("root.server",{type:"server",message:"Nie udało się zapisać."});}}const errorProps=(field:keyof Input)=>({"aria-invalid":errors[field]?true:undefined,"aria-describedby":errors[field]?id+"-"+field+"-error":undefined});const error=(field:keyof Input)=>errors[field]?<p id={id+"-"+field+"-error"}>{errors[field]?.message}</p>:null;return <form noValidate onSubmit={handleSubmit(submit)}><label htmlFor={id+"-title"}>Tytuł</label><input id={id+"-title"} {...register("title")} {...errorProps("title")}/>{error("title")}<label htmlFor={id+"-description"}>Opis</label><textarea id={id+"-description"} {...register("description")} {...errorProps("description")}/>{error("description")}<label htmlFor={id+"-priority"}>Priorytet</label><select id={id+"-priority"} {...register("priority")} {...errorProps("priority")}>{["LOW","MEDIUM","HIGH","CRITICAL"].map(priority=><option key={priority}>{priority}</option>)}</select>{error("priority")}<label htmlFor={id+"-assignee"}>Osoba</label><select id={id+"-assignee"} {...register("assignee",{setValueAs:value=>value===""?null:value})} {...errorProps("assignee")}><option value="">Nieprzypisany</option>{["anna","piotr","kasia"].map(person=><option key={person}>{person}</option>)}</select>{error("assignee")}<label htmlFor={id+"-hours"}>Czas</label><input id={id+"-hours"} type="number" step="any" {...register("estimateHours",{valueAsNumber:true})} {...errorProps("estimateHours")}/>{error("estimateHours")}{errors.root?.server&&<p role="alert">{errors.root.server.message}</p>}<button type="submit" disabled={isSubmitting}>{isSubmitting?"Zapisywanie…":"Utwórz"}</button><button type="button" disabled={isSubmitting} onClick={()=>reset()}>Wyczyść</button></form>;}`;
const transform=`import {z} from "zod";import {useForm} from "react-hook-form";import {zodResolver} from "@hookform/resolvers/zod";
export const hoursSchema=z.object({hours:z.string().trim().min(1,"Podaj czas.").transform(value=>Number(value)).pipe(z.number().nonnegative("Czas nieujemny."))});export type HoursInput=z.input<typeof hoursSchema>;export type HoursOutput=z.output<typeof hoursSchema>;
export function HoursForm(){const {register,handleSubmit,formState:{errors}}=useForm<HoursInput,unknown,HoursOutput>({resolver:zodResolver(hoursSchema),defaultValues:{hours:""}});return <form onSubmit={handleSubmit(values=>console.log(values.hours.toFixed(1)))} noValidate><label>Czas<input {...register("hours")} aria-invalid={errors.hours?true:undefined}/></label>{errors.hours&&<p role="alert">{errors.hours.message}</p>}<button type="submit">Zapisz</button></form>;}`;
const exercises=[
['Zdefiniuj schemat ticketu z regułami i typem wyprowadzonym przez infer.','Nie utrzymuj równolegle ręcznej kopii typu.',schema],
['Trimuj tytuł przed min i max.','Spacje brzegowe nie spełniają minimum.',schema],
['Zachowaj surowy opis, ale sprawdzaj trim dla CRITICAL.','Transformacje są częścią kontraktu danych.',schema],
['Ogranicz priority do czterech wartości enum.','Typ string nie wystarcza w runtime.',schema],
['Dopuść assignee=null lub jedną z trzech osób.','Nullable nie oznacza optional.',schema],
['Odrzuć NaN, Infinity i ujemny czas schematem liczbowym.','Zod 4 number odrzuca wartości nieskończone.',schema],
['Dodaj refine zależny od priority i description.','Path przypisuje komunikat do opisu.',schema],
['Użyj safeParse i zawężenia success.','Data jest dostępna w gałęzi sukcesu.',schema+'\nexport function accept(input:unknown){const result=ticketSchema.safeParse(input);return result.success?result.data.title:null;}'],
['Pokaż parse z obsługą błędu.','Parse rzuca przy niepoprawnych danych.',schema+'\nexport function parseOrNull(input:unknown):Output|null{try{return ticketSchema.parse(input);}catch{return null;}}'],
['Przechowuj JSON.parse jako unknown przed sprawdzeniem schematu.','Rzutowanie as Values nie waliduje.',schema],
['Obsłuż niepoprawną składnię JSON w parseDraft.','SafeParse nie łapie wyjątku wcześniejszego JSON.parse.',schema],
['Obsłuż brak wpisu localStorage bez wyjątku.','GetItem zwraca null.',schema],
['Obsłuż wyjątek getItem, np. blokadę dostępu do storage.','Try/catch obejmuje także odczyt.',schema],
['Waliduj szkic przed zapisaniem i zwróć boolean sukcesu.','To przykład zapisu tylko poprawnych szkiców.',schema],
['Obsłuż wyjątek setItem bez utraty bieżącego UI.','Zapełniony lub niedostępny storage nie oznacza poprawnego zapisu.',schema],
['Rozróżnij szkic roboczy od poprawnego modelu wysłania.','Jeśli chcesz zapisywać niepełne pola, zaprojektuj osobny schemat.','Przykład saveDraft zapisuje wyłącznie kompletny poprawny ticket. Do zapisu niepełnej pracy użyj osobnego schematu roboczego, wersji formatu i bezpiecznej migracji. Partial nie wyłącza automatycznie wszystkich reguł pól ani nie definiuje polityki brakujących danych.'],
['Podłącz zodResolver do useForm.','Reguły biznesowe pozostają w ticketSchema.',form],
['Usuń z register duplikaty minLength i validate.','Register nadal podłącza pole i może wykonywać konwersję.',form],
['Konwertuj pusty select osoby na null.','Enum nullable nie akceptuje pustego stringa.',form],
['Użyj valueAsNumber dla czasu i pozwól schematowi odrzucić NaN.','Generyk useForm sam nie konwertuje DOM.',form],
['Pokaż błędy ze schematu z aria-describedby.','Error musi mieć istniejące id.',form],
['Poczekaj na zapis przed resetem i zachowaj wartości po odrzuceniu.','Walidacja danych i błąd serwera to różne ścieżki.',form],
['Wyprowadź osobno input i output dla schematu transformującego tekst w liczbę.','Z.infer oznacza output.',transform],
['Skonfiguruj useForm<Input,unknown,Output> dla transformacji.','OnValid otrzymuje wynik resolvera.',transform],
['Nie zamieniaj pustego czasu bezmyślnie na zero przez coercion.','Number("") to zero.',transform],
['Sprawdź zachowanie nieznanego klucza w zwykłym z.object.','Wynik standardowo pomija nieznane pola.',schema+'\nconsole.log(ticketSchema.safeParse({...empty,title:"Gotowy",admin:true}));'],
['Odrzuć nieznane klucze przez strictObject w osobnym schemacie.','To świadoma polityka kontraktu.','import {z} from "zod";const schema=z.strictObject({title:z.string()});console.log(schema.safeParse({title:"API",extra:true}).success);'],
['Zamień błąd walidacji loginu w fieldErrors przez flattenError.','Zod 4 udostępnia funkcję z.flattenError.','import {z} from "zod";const login=z.object({email:z.email("Adres niepoprawny"),password:z.string().min(8,"Minimum 8 znaków")});export function validateLogin(input:unknown){const result=login.safeParse(input);return result.success?{data:result.data,errors:{}}:{data:null,errors:z.flattenError(result.error).fieldErrors};}'],
['Wyjaśnij ograniczenie współdzielenia schematu klienta i serwera.','Zaufanie nie wynika z wcześniejszej walidacji.','Ten sam moduł reguł można współdzielić, jeśli środowiska i zależności na to pozwalają. Serwer nadal uruchamia walidację na otrzymanych danych i sprawdza uprawnienia oraz reguły zależne od bazy. Poprawny kształt ticketu nie oznacza uprawnienia do jego zapisu.'],
['Przetestuj uszkodzony JSON, złe typy, granice i poprawny wynik po trim.','Oceniaj wynik parsowania, nie sam input.',schema],
];
const concepts=[
['Co wykonuje schemat Zod?','Sprawdza dane w runtime','Tylko tworzy typ TS','Instaluje React','Automatycznie wysyła HTTP'],
['Co zwraca parse dla poprawnych danych?','Wynik zgodny ze schematem po transformacjach','Zawsze true','Zawsze wejściową referencję','Listę błędów'],
['Co robi parse przy błędzie walidacji?','Rzuca błąd','Zwraca false zawsze','Zwraca pusty obiekt','Resetuje formularz'],
['Jak odróżnić sukces safeParse?','Sprawdzić result.success','Sprawdzić Boolean(result)','Sprawdzić result.length','Wywołać result.trim()'],
['Czy safeParse obejmuje wcześniejszy JSON.parse?','Nie','Tak zawsze','Tylko dla null','Tylko w TSX'],
['Co oznacza z.infer<typeof schema>?','Typ wyniku schematu','Dowolny input','Funkcję walidacji','Promise zawsze'],
['Kiedy input i output mogą się różnić?','Gdy schemat transformuje dane','Nigdy','Tylko z React','Tylko po reset'],
['Jak wyprowadzić wejście schematu?','Z.input<typeof schema>','Z.key<typeof schema>','Typeof schema.input()','Schema.props'],
['Co robi trim w schemacie?','Transformuje wynikowy tekst','Tylko opisuje błąd','Mutuje input string','Usuwa wszystkie wewnętrzne spacje'],
['Dlaczego trim przed min?','Minimum dotyczy tekstu po przycięciu','Zapewnia niepusty opis','Konwertuje do number','Nadaje key'],
['Co wnosi path w refine obiektu?','Wskazuje pole komunikatu','Wysyła żądanie','Zmienia folder','Sortuje wartości'],
['Czy nullable oznacza możliwość braku pola?','Nie, dopuszcza null','Tak zawsze','Tylko dla string','Tylko w dev'],
['Czy z.number akceptuje tekst 2?','Nie bez konwersji','Tak automatycznie','Tylko po min','Tylko w obiekcie'],
['Jak działa z.coerce.number dla pustego tekstu?','Jak Number, może dać zero','Zawsze null','Zawsze błąd required','Zawsze undefined'],
['Co robi zodResolver?','Łączy wynik schematu z RHF','Instaluje zod','Zastępuje React','Zapisuje localStorage'],
['Czy z resolverem można zostawić konwersje register?','Tak, jeśli odpowiadają kontraktowi input','Nie, register zabronione','Tylko dla title','Tylko po submit'],
['Co przekazać do onCreate przy transformacjach?','Poprawny output resolvera','Surowy obiekt bez walidacji','Sam schemat','Same błędy'],
['Jak wczytać uszkodzony szkic bez awarii?','Obsłużyć JSON.parse i walidować unknown','Użyć as Values','Wywołać trim na wszystkim','Zignorować typy i wyjątki'],
['Czy getItem może rzucić?','Tak, obsłuż błąd dostępu','Nie nigdy','Tylko dla liczb','Tylko z JSON'],
['Co oznacza false z saveDraft w przykładzie?','Dane niepoprawne lub zapis storage nieudany','Na pewno błąd sieci','Na pewno brak użytkownika','Zapisano pusty szkic'],
['Czy schema poprawnego ticketu zapisze każdą niepełną pracę?','Nie, wymaga spełnienia reguł','Tak zawsze','Tylko w localStorage','Tylko bez infer'],
['Co zrobić z niepełnymi szkicami?','Zaprojektować osobny kontrakt roboczy','Wyłączyć wszystkie kontrole','Udawać poprawny Ticket','Nadpisać pola techniczne'],
['Co zwykle robi z.object z nieznanymi kluczami?','Pomija je w wyniku','Zawsze rzuca','Zawsze zachowuje','Zamienia w null'],
['Jak odrzucać nieznane klucze?','Użyć z.strictObject','Użyć as const na input','Użyć Boolean(input)','Użyć nullable'],
['Co daje flattenError?','Płaskie komunikaty formErrors i fieldErrors','Nowy formularz','Wartości DOM','Konwersję tekstu na liczbę'],
['Czy infer potwierdza minimum długości każdego stringa w runtime?','Nie, trzeba uruchomić schemat','Tak','Tylko po import type','Tylko dla enum'],
['Czy klient zastępuje walidację serwera?','Nie','Tak z Zod','Tak z resolverem','Tak z infer'],
['Czy poprawny schemat potwierdza uprawnienia?','Nie, to osobna kontrola','Tak','Tylko przy strictObject','Tylko przy refine title'],
['Jak obsłużyć async refine?','Użyć asynchronicznego parsowania','Zawsze zwykłe parse','Tylko JSON.parse','Usunąć await'],
['Gdzie powinny być wspólne reguły danych formularza?','W jednym schemacie używanym przez integracje','W trzech rozbieżnych kopiach','Tylko w placeholder','Wyłącznie w CSS'],
];
const quiz=concepts.map(row=>[...row,'Schemat działa w runtime; typy opisują kontrakt kompilatora. Parsowanie może też transformować dane, a błędy składni JSON i storage wymagają osobnej obsługi.']);
quiz.push(...require('./practice_form_07.cjs').quiz.slice(0,30).map(row=>['Powtórka integracji schematu: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const n of [0,4,5,6,119,120,121])add('Tytuł '+n,`ticketSchema.safeParse({...base,title:"a".repeat(${n})}).success`,n>=5&&n<=120);
for(const priority of ['LOW','MEDIUM','HIGH','CRITICAL','PILNE',''])add('Priority '+JSON.stringify(priority),`ticketSchema.safeParse({...base,priority:${JSON.stringify(priority)}}).success`,['LOW','MEDIUM','HIGH','CRITICAL'].includes(priority));
for(const person of [null,'anna','piotr','kasia','','adam'])add('Assignee '+JSON.stringify(person),`ticketSchema.safeParse({...base,assignee:${JSON.stringify(person)}}).success`,person===null||['anna','piotr','kasia'].includes(person));
for(const value of ['0','0.5','-1','NaN','Infinity','"2"'])add('Hours '+value,`ticketSchema.safeParse({...base,estimateHours:${value}}).success`,['0','0.5'].includes(value));
for(const [label,expr,a]of [
['Trim wyniku','ticketSchema.parse({...base,title:"  Gotowy  "}).title','Gotowy'],['Nie mutuje wejścia','(()=>{const value={...base,title:"  Gotowy  "};ticketSchema.parse(value);return value.title.length;})()',10],['Opis zachowany','ticketSchema.parse({...base,description:"  opis  "}).description.length',8],['CRITICAL pusty','ticketSchema.safeParse({...base,priority:"CRITICAL",description:""}).success',false],['CRITICAL spacje','ticketSchema.safeParse({...base,priority:"CRITICAL",description:"   "}).success',false],['Brak JSON','parseDraft(null)===null',true],['Popsuty JSON','parseDraft("{")===null',true],['Zły JSON kształt','parseDraft("[]")===null',true],['Poprawny szkic','parseDraft(JSON.stringify(base))?.title','Gotowy'],['Nieznane pole usunięte','"admin" in ticketSchema.parse({...base,admin:true})',false],['Nowy wynik','ticketSchema.parse(base)===base',false],['Coerce pusty','z.coerce.number().parse("")',0],['Strict nieznany','z.strictObject({title:z.string()}).safeParse({title:"API",extra:1}).success',false],['Nullable brak pola','z.object({a:z.string().nullable()}).safeParse({}).success',false],['Nullable null','z.object({a:z.string().nullable()}).safeParse({a:null}).success',true],
])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);
for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik schematu Zod?',a,...wrong,'Sprawdź reguły, transformacje i success. ParseDraft oddzielnie chroni parsowanie JSON; niepoprawny zapis zwraca null.',schema+'\nconst base:Input={title:"Gotowy",description:"Opis",priority:"MEDIUM",assignee:null,estimateHours:0};console.log('+expr+');']);}
const project={'src/Form.tsx':form,'src/Transform.tsx':transform,'src/main.tsx':'import {createRoot} from "react-dom/client";import {SchemaForm} from "./Form";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<main><h1>Formularz ze schematem</h1><SchemaForm onCreate={async values=>{console.log(values);}}/></main>);'};
module.exports={schema,form,transform,exercises,quiz,project};
