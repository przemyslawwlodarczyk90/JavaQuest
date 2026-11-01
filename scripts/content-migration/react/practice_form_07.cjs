const model=`export const PRIORITIES=["LOW","MEDIUM","HIGH","CRITICAL"] as const;export type Priority=(typeof PRIORITIES)[number];export type Values={title:string;description:string;priority:Priority;estimateHours:number};
export const defaults:Values={title:"",description:"",priority:"MEDIUM",estimateHours:0};
export const validateTitle=(value:string):true|string=>value.trim().length<5?"Minimum 5 znaków.":value.trim().length>120?"Maksimum 120 znaków.":true;
export const validateHours=(value:number):true|string=>Number.isFinite(value)&&value>=0?true:"Podaj skończony nieujemny czas.";
export const validateDescription=(value:string,all:Values):true|string=>all.priority!=="CRITICAL"||value.trim()!==""||"Opis wymagany.";`;
const form=`import {useId} from "react";import {useForm} from "react-hook-form";
${model}
export function HookTicketForm({onCreate}:{onCreate:(values:Values)=>Promise<void>}){const id=useId();const {register,handleSubmit,reset,watch,clearErrors,setError,formState:{errors,isSubmitting}}=useForm<Values>({defaultValues:defaults,mode:"onBlur",reValidateMode:"onChange"});const priority=watch("priority");async function onValid(values:Values){clearErrors("root.server");try{await onCreate({...values,title:values.title.trim()});reset();}catch{setError("root.server",{type:"server",message:"Zapis nie powiódł się. Spróbuj ponownie."});}}return <form noValidate onSubmit={handleSubmit(onValid)}><label htmlFor={id+"-title"}>Tytuł</label><input id={id+"-title"} {...register("title",{validate:validateTitle})} aria-invalid={errors.title?true:undefined} aria-describedby={errors.title?id+"-title-error":undefined}/>{errors.title&&<p id={id+"-title-error"}>{errors.title.message}</p>}<label htmlFor={id+"-priority"}>Priorytet</label><select id={id+"-priority"} {...register("priority",{deps:["description"]})}>{PRIORITIES.map(priority=><option key={priority}>{priority}</option>)}</select><label htmlFor={id+"-description"}>Opis {priority==="CRITICAL"?"(wymagany)":"(opcjonalny)"}</label><textarea id={id+"-description"} {...register("description",{validate:validateDescription})} aria-invalid={errors.description?true:undefined} aria-describedby={errors.description?id+"-description-error":undefined}/>{errors.description&&<p id={id+"-description-error"}>{errors.description.message}</p>}<label htmlFor={id+"-hours"}>Czas</label><input id={id+"-hours"} type="number" step="any" {...register("estimateHours",{valueAsNumber:true,validate:validateHours})} aria-invalid={errors.estimateHours?true:undefined} aria-describedby={errors.estimateHours?id+"-hours-error":undefined}/>{errors.estimateHours&&<p id={id+"-hours-error"}>{errors.estimateHours.message}</p>}{errors.root?.server&&<p role="alert">{errors.root.server.message}</p>}<button type="submit" disabled={isSubmitting}>{isSubmitting?"Zapisywanie…":"Utwórz"}</button><button type="button" disabled={isSubmitting} onClick={()=>reset()}>Wyczyść</button></form>;}`;
const controller=`import {useId} from "react";import {Controller,useForm} from "react-hook-form";
${model}
function PriorityPicker({value,onValueChange,onBlur,inputRef,name,id}:{value:Priority;onValueChange:(value:Priority)=>void;onBlur:()=>void;inputRef:(element:HTMLSelectElement|null)=>void;name:string;id:string}){return <select id={id} name={name} value={value} onBlur={onBlur} ref={inputRef} onChange={event=>{const value=event.currentTarget.value;const priority=PRIORITIES.find(priority=>priority===value);if(priority)onValueChange(priority);}}>{PRIORITIES.map(priority=><option key={priority}>{priority}</option>)}</select>;}
export function PriorityForm(){const id=useId();const {control,handleSubmit}=useForm<{priority:Priority}>({defaultValues:{priority:"MEDIUM"}});return <form onSubmit={handleSubmit(values=>console.log(values))}><label htmlFor={id}>Priorytet</label><Controller name="priority" control={control} render={({field})=><PriorityPicker id={id} name={field.name} value={field.value} onValueChange={field.onChange} onBlur={field.onBlur} inputRef={field.ref}/>}/><button type="submit">Zapisz</button></form>;}`;
const tools=`import {useForm} from "react-hook-form";
${model}
export function FormTools(){const {register,setValue,trigger,getValues,reset,formState:{isDirty}}=useForm<Values>({defaultValues:defaults});return <form onSubmit={event=>event.preventDefault()}><label>Tytuł<input {...register("title",{validate:validateTitle})}/></label><button type="button" onClick={()=>setValue("title","Gotowy ticket",{shouldDirty:true,shouldValidate:true,shouldTouch:true})}>Wstaw tytuł</button><button type="button" onClick={()=>{void trigger("title");}}>Sprawdź</button><button type="button" onClick={()=>console.log(getValues("title"))}>Odczytaj</button><button type="button" onClick={()=>reset({...defaults,title:"Wczytany ticket"})}>Wczytaj bazę</button><p>{isDirty?"Zmiany":"Bez zmian"}</p></form>;}`;
const exercises=[
['Zainstaluj react-hook-form i zbuduj formularz useForm<Values>.','Pakiet dołącz w projekcie wykonywanego ćwiczenia.',form],
['Podłącz title przez register ze spreadem name, ref, onChange i onBlur.','Nie nadpisuj bezmyślnie zwróconych handlerów.',form],
['Waliduj długość tytułu po trim w zakresie 5–120.','MinLength surowego tekstu samo nie odrzuci samych spacji.',model],
['Odrzuć NaN po opróżnieniu pola valueAsNumber.','Typ number dopuszcza też NaN.',model],
['Zaakceptuj czas zero oraz nieujemne ułamki.','Number.isFinite i >=0.',model],
['Dodaj regułę opisu zależną od all.priority.','Validate może czytać pozostałe wartości.',form],
['Przy zmianie priority uruchamiaj zależną walidację description.','Deps wskazuje pola zależne od zmienianego pola.',form],
['Ustaw defaultValues dla wszystkich pól.','Nie stosuj undefined jako domyślnej wartości kontrolowanego pola.',form],
['Pokaż teksty błędów w elementach z id i powiąż je z kontrolkami.','Aria-describedby wskazuje istniejący komunikat.',form],
['Skonfiguruj mode=onBlur i reValidateMode=onChange.','Oddziel początkowy moment walidacji od ponownej po submit.',form],
['Użyj handleSubmit zamiast ręcznego odczytu DOM.','OnValid otrzymuje poprawne dane biblioteki.',form],
['Czekaj na Promise onCreate przed reset.','Await utrzymuje isSubmitting podczas zapisu.',form],
['Obsłuż odrzucenie zapisu przez root.server.','HandleSubmit nie połyka wyjątków callbacku.',form],
['Przy błędzie zapisu zachowaj wpisane dane.','Reset umieść tylko w ścieżce sukcesu.',form],
['Pokaż Zapisywanie i wyłącz ponowne wysłanie podczas operacji.','Disabled podczas pracy nie zastępuje komunikatów walidacji.',form],
['Resetuj formularz przyciskiem typu button.','Reset wraca do bazowych defaultValues.',form],
['Obserwuj priority przez watch i zmieniaj opis etykiety.','Watch subskrybuje wartość; getValues służy odczytowi bez subskrypcji.',form],
['Podłącz własny kontrolowany komponent przez Controller.','Adapter mapuje nazwy propsów na field.',controller],
['Przekaż onBlur oraz ref do kontrolowanego pola.','Biblioteka potrzebuje touched i możliwości fokusu.',controller],
['Nie rejestruj tego samego Controller drugi raz przez register.','Jeden mechanizm integracji na pole.',controller],
['Zachowaj natywną obsługę klawiatury w PriorityPicker.','Select jest pełnym przykładem adaptera bez niekompletnego role=radio.',controller],
['Ustaw wartość programowo z shouldDirty, shouldTouch i shouldValidate.','SetValue ma jawne opcje aktualizacji metadanych.',tools],
['Uruchom trigger dla jednego pola i odczytaj wynik Promise.','Trigger nie jest synchronicznym booleanem.','import {useForm} from "react-hook-form";export function TriggerExample(){const {trigger}=useForm<{title:string}>();return <button type="button" onClick={async()=>{const valid=await trigger("title");console.log(valid);}}>Sprawdź</button>;}'],
['Odczytaj getValues bez tworzenia subskrypcji renderowania.','To odczyt na żądanie.',tools],
['Ustaw nową bazę edycji przez reset(noweWartości).','Zwykła zmiana obiektu defaultValues nie jest ciągłą synchronizacją.',tools],
['Sprawdź ochronę nazwy pola przed literówką.','Generyk określa FieldPath.','import {useForm} from "react-hook-form";'+model+'\nexport function TypeCheck(){const {register}=useForm<Values>();\n// @ts-expect-error: nieznane pole\nregister("titel");return null;}'],
['Dodaj dodatkową reakcję onChange w opcjach register.','Nie nadpisuj onChange po spread.','import {useForm} from "react-hook-form";export function LoggedInput(){const {register}=useForm<{title:string}>();return <label>Tytuł<input {...register("title",{onChange:()=>console.log("Zmieniono")})}/></label>;}'],
['Wyjaśnij ograniczenia automatycznego fokusu biblioteki.','Rejestracja musi dotrzeć do fokusowalnej kontrolki.','Fokus po błędnym wysłaniu zależy od podłączonego ref, konfiguracji i dostępności kontrolki. Controller powinien przekazywać field.ref do właściwego elementu. Ukryte lub wyłączone pole oraz adapter bez ref mogą uniemożliwić fokus. Testuj zachowanie w DOM.'],
['Wyjaśnij, dlaczego generyk useForm nie zastępuje walidacji runtime.','Typy nie sprawdzają danych przychodzących.','UseForm<Values> sprawdza ścieżki i typy podczas kompilacji. Nie zamienia automatycznie tekstu w liczbę i nie potwierdza, że dowolny string jest poprawnym Priority. Potrzebne są konwersje, reguły lub schemat; serwer waliduje niezależnie.'],
['Przetestuj sukces, błąd zapisu, puste number i CRITICAL bez opisu.','Sprawdź zachowanie pól oraz fokus w przeglądarce.',form],
];
const concepts=[
['Co zwraca register dla natywnego pola?','Name, ref, onChange i onBlur','Tylko value','Gotowy element input','Promise danych'],
['Po co generyk useForm<Values>?','Sprawdza nazwy i typy pól w TS','Waliduje odpowiedź HTTP','Instaluje reguły automatycznie','Zmienia każdy input w number'],
['Co robi handleSubmit(onValid)?','Waliduje przed wywołaniem onValid','Wywołuje callback zawsze','Resetuje zawsze','Wysyła HTTP bez konfiguracji'],
['Co robi valueAsNumber dla pustego number?','Może dać NaN','Zawsze daje null','Zawsze daje zero','Zawsze daje undefined'],
['Czy number wyklucza NaN w TS?','Nie','Tak','Tylko w strict','Tylko po register'],
['Po co await onCreate?','Czekamy na zapis przed reset i końcem pending','Aby usunąć errors','Aby zmienić ref','Aby wyłączyć TypeScript'],
['Czy handleSubmit automatycznie obsłuży wyjątek callbacku?','Nie, obsłuż go jawnie','Tak, zawsze','Tylko w JSX','Tylko przy noValidate'],
['Gdzie pokazać błąd zapisu?','Np. errors.root.server po setError','Jako nowy key inputa','W defaultValues.title','Wyłącznie w console'],
['Kiedy resetować po async zapisie?','Po potwierdzonym sukcesie','W finally zawsze','Przed await','Po każdym blur'],
['Co robi isSubmitting?','Informuje o trwającym wysyłaniu obsługiwanym przez bibliotekę','Potwierdza sukces serwera','Jest listą błędów','Jest wartością tytułu'],
['Po co defaultValues?','Określają wartości bazowe formularza','Wymuszają same poprawne dane','Blokują zmiany','Zastępują register'],
['Czy nowa referencja defaultValues stale resetuje pola?','Nie, bazowe wartości są cacheowane','Tak zawsze','Tylko dla select','Tylko po submit'],
['Jak przyjąć nowe dane jako bazę edycji?','Reset(noweWartości)','Nowy label','Zmiana className','Zawsze useEffect bez warunku'],
['Co oznacza isDirty?','Zmianę względem wartości bazowych','Liczbę błędów','Trwający zapis','Zamontowanie pola'],
['Czy isDirty oznacza isValid?','Nie','Tak','Tylko po blur','Tylko dla tekstu'],
['Kiedy użyć Controller?','Do adaptera kontrolowanego komponentu zewnętrznego','Do każdego zwykłego div','Do fetch','Do generowania ID'],
['Co przekazać z field, aby działało touched?','OnBlur','Tylko value','Tylko key','Tylko className'],
['Co przekazać, aby umożliwić fokus?','Field.ref do kontrolki','Field.name do CSS','Nowy losowy key','Tylko aria-label'],
['Czy Controller wymaga też register tego samego pola?','Nie','Tak zawsze','Tylko dla select','Tylko przy onBlur'],
['Co robi watch?','Subskrybuje wartości do zależnego UI','Tylko raz czyta dane','Wysyła do API','Nadaje typ Priority'],
['Czym różni się getValues od watch?','Nie tworzy subskrypcji renderowania','Zawsze resetuje','Zawsze waliduje','Usuwa name'],
['Co zwraca trigger?','Promise wyniku walidacji','Zawsze true synchronicznie','Element DOM','Nowy hook'],
['Która opcja setValue aktualizuje dirty?','ShouldDirty','ShouldFetch','ShouldKey','ShouldMount'],
['Jak dodać własny onChange bez zgubienia register?','Przekazać onChange w opcjach register','Nadpisać po spread bez wywołania oryginału','Usunąć ref','Dodać drugi input z tą samą nazwą'],
['Co robi mode=onBlur?','Ustala początkową walidację po blur','Wysyła po blur','Usuwa onChange','Wyłącza errors'],
['Czego dotyczy reValidateMode?','Ponownej walidacji po próbie wysłania','Wersji React','Kolejności importów','Resetu CSS'],
['Dlaczego required nie wystarczy dla spacji w tytule?','Nie zapewnia minimum po trim','Zawsze usuwa spacje','Spacje są number','Wymaga Controller'],
['Co wnosi deps w przykładzie priority?','Wskazuje zależne pole description do ponownej walidacji','Tworzy nowy komponent','Zmienia wszystkie defaults','Generuje ref'],
['Czy biblioteka gwarantuje zero renderów?','Nie; zależy od subskrypcji i komponentów','Tak zawsze','Tak przy watch całego formularza','Tylko w TSX'],
['Czy reguły klienta zwalniają serwer z walidacji?','Nie','Tak','Tylko z RHF','Tylko dla number'],
];
const quiz=concepts.map(row=>[...row,'Narzędzia RHF rozdzielają rejestrację, walidację, subskrypcje i wysyłanie. Sprawdź kontrakt konkretnej funkcji.']);
quiz.push(...require('./practice_form_06.cjs').quiz.slice(0,30).map(row=>['Powtórka przed biblioteką formularzy: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,answer)=>cases.push([label,expr,String(answer)]);
for(const n of [0,1,4,5,6,119,120,121,130])add('Tytuł długości '+n,`validateTitle("a".repeat(${n}))`,n<5?'Minimum 5 znaków.':n>120?'Maksimum 120 znaków.':true);
for(const value of ['0','1','0.5','-1','NaN','Infinity','-Infinity'])add('Czas '+value,`validateHours(${value})`,['-1','NaN','Infinity','-Infinity'].includes(value)?'Podaj skończony nieujemny czas.':true);
for(const priority of ['LOW','MEDIUM','HIGH','CRITICAL'])for(const description of ['','   ','Opis'])add('Opis '+priority+' '+JSON.stringify(description),`validateDescription(${JSON.stringify(description)},{...defaults,priority:${JSON.stringify(priority)}})`,priority==='CRITICAL'&&!description.trim()?'Opis wymagany.':true);
add('Spacje tytułu','validateTitle("     ")','Minimum 5 znaków.');add('Trim tytułu','validateTitle("  Gotowy  ")',true);
for(const expr of ['Number("")','Number("0")','Number("2.5")','Number("-1")','Number.isFinite(NaN)','Number.isFinite(0)','defaults.estimateHours','defaults.priority','defaults.title.length','validateDescription("",defaults)']){const answers={'Number("")':0,'Number("0")':0,'Number("2.5")':2.5,'Number("-1")':-1,'Number.isFinite(NaN)':false,'Number.isFinite(0)':true,'defaults.estimateHours':0,'defaults.priority':'MEDIUM','defaults.title.length':0,'validateDescription("",defaults)':true};add('Konwersja lub baza '+expr,expr,answers[expr]);}
if(cases.length!==40)throw Error('Przypadki '+cases.length);
for(const [label,expr,answer]of cases){const distractors=['false','true','null','undefined','0','1'].filter(value=>value!==answer).slice(0,3);quiz.push([label+': jaki wynik reguły formularza?',answer,...distractors,'Reguła zwraca true dla poprawnych danych lub tekst komunikatu dla niepoprawnych. Konwersję liczby trzeba rozróżnić od jej walidacji.',model+'\nconsole.log('+expr+');']);}
const project={'src/Form.tsx':form,'src/ControllerDemo.tsx':controller,'src/Tools.tsx':tools,'src/main.tsx':'import {createRoot} from "react-dom/client";import {HookTicketForm} from "./Form";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<main><h1>Nowy ticket</h1><HookTicketForm onCreate={async values=>{await new Promise(resolve=>setTimeout(resolve,800));console.log(values);}}/></main>);'};
module.exports={model,form,controller,tools,exercises,quiz,project};
