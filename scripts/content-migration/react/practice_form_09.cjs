// API: https://react.dev/reference/react/useActionState https://react.dev/reference/react-dom/components/form https://react.dev/reference/react-dom/hooks/useFormStatus
const model=`export type CommentState={error:string|null;ok:boolean;text:string;attempt:number};export const initial:CommentState={error:null,ok:false,text:"",attempt:0};
export function readComment(data:FormData):{text:string;error:string|null}{const raw=data.get("text");if(typeof raw!=="string")return {text:"",error:"Podaj tekst komentarza."};const text=raw.trim();return {text:raw,error:text.length<3?"Minimum 3 znaki.":text.length>1000?"Maksimum 1000 znaków.":null};}
export async function runComment(previous:CommentState,data:FormData,save:(text:string)=>Promise<void>):Promise<CommentState>{const read=readComment(data);const attempt=previous.attempt+1;if(read.error)return {error:read.error,ok:false,text:read.text,attempt};try{await save(read.text.trim());return {error:null,ok:true,text:"",attempt};}catch{return {error:"Nie udało się zapisać komentarza.",ok:false,text:read.text,attempt};}}`;
const button=`import {useFormStatus} from "react-dom";
export function SubmitButton(){const {pending}=useFormStatus();return <button type="submit" disabled={pending} aria-busy={pending}>{pending?"Wysyłanie…":"Dodaj komentarz"}</button>;}`;
const form=`import {useActionState,useId,useState} from "react";
${model}
${button}
export function CommentForm({save}:{save:(text:string)=>Promise<void>}){const [text,setText]=useState("");const id=useId();const [state,formAction,pending]=useActionState(async(previous:CommentState,data:FormData):Promise<CommentState>=>{const next=await runComment(previous,data,save);if(next.ok)setText("");return next;},initial);return <form action={formAction}><label htmlFor={id}>Komentarz</label><textarea id={id} name="text" value={text} onChange={event=>setText(event.currentTarget.value)} readOnly={pending} aria-invalid={state.error?true:undefined} aria-describedby={state.error?id+"-error":undefined}/>{state.error&&<p id={id+"-error"} role="alert">{state.error}</p>}{state.ok&&<p role="status">Komentarz zapisany.</p>}<SubmitButton/></form>;}`;
const simple=`export function SimpleActionForm({save}:{save:(text:string)=>Promise<void>}){async function action(data:FormData){const text=data.get("text");if(typeof text!=="string"||text.trim().length<3)throw Error("Niepoprawny komentarz");await save(text.trim());}return <form action={action}><label>Komentarz<textarea name="text"/></label><button type="submit">Dodaj</button></form>;}`;
const project={'src/CommentForm.tsx':form,'src/main.tsx':'import {useState} from "react";import {createRoot} from "react-dom/client";import {CommentForm} from "./CommentForm";function App(){const [comments,setComments]=useState<string[]>([]);return <main><h1>Komentarze</h1><CommentForm save={async text=>{await new Promise(resolve=>setTimeout(resolve,700));if(text.toLowerCase().includes("błąd"))throw Error("Odmowa");setComments(previous=>[...previous,text]);}}/><ol>{comments.map((text,index)=><li key={index}>{text}</li>)}</ol></main>;}const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<App/>);'};
const exercises=[
['Zbuduj prosty form action z asynchroniczną funkcją otrzymującą FormData.','To nie jest SubmitEvent.',simple],
['Dodaj name=text, aby akcja otrzymała treść pola.','Id i label nie zastępują name.',simple],
['Zdefiniuj jawny typ wyniku akcji z error, ok, text i attempt.','Każda gałąź zwraca pełny kształt.',model],
['Odrzuć brakujący wpis oraz File zamiast tekstu.','Nie wykonuj String(file).',model],
['Sprawdzaj minimum 3 i maksimum 1000 po trim.','Surowy tekst zachowaj do korekty.',model],
['Napisz runComment otrzymujące previousState, data i save.','Czysty odczyt oraz jawna zależność zapisu.',model],
['Nie wywołuj save przy błędnej długości.','Walidacja kończy ścieżkę przed await.',model],
['Poczekaj na save przed zwróceniem sukcesu.','Zwrócenie Promise utrzymuje pending.',model],
['Obsłuż odrzucenie save bez zakładania typu rzucanej wartości.','Komunikat jest bezpieczny i stały.',model],
['Zachowaj surową treść w stanie błędu.','Payload do zapisu może być przycięty.',model],
['Po sukcesie zwróć text pusty oraz ok=true.','Poprzedni komunikat błędu znika.',model],
['Zlicz próby na podstawie poprzedniego wyniku.','PreviousState nie jest FormData.',model],
['Podłącz useActionState do form action.','Drugi element krotki jest akcją formularza.',form],
['Pobierz isPending jako trzeci element wyniku hooka.','Nie traktuj stanu wyniku jako boolean.',form],
['Umieść useFormStatus w potomnym SubmitButton.','Hook odczytuje formularz nad komponentem.',button],
['Zaimportuj useFormStatus z react-dom, useActionState z react.','To dwa różne pakiety.',form],
['Pokaż pending i aria-busy na przycisku wysłania.','W czasie pracy przycisk ma disabled.',button],
['Zachowaj tekst po błędzie przez kontrolowaną textarea.','Automatyczny reset akcji dotyczy pól niekontrolowanych.',form],
['Wyczyść kontrolowany tekst dopiero po next.ok.','Nie czyść go w finally.',form],
['Zablokuj edycję tekstu na czas zapisu przez readOnly.','Pole pozostaje w danych formularza.',form],
['Powiąż błąd przez aria-describedby z textarea.','Nie odwołuj się do nieistniejącego elementu.',form],
['Pokaż sukces w role=status, błąd w role=alert.','Role nie zastępują etykiety.',form],
['Wyjaśnij reset po zwróceniu obiektu z error.','React nie interpretuje semantyki twojego obiektu.','Zwrócony {error:"..."} oznacza normalne zakończenie akcji, więc niekontrolowane pola mogą zostać zresetowane. Zachowaj wartości w modelu i przywróć je świadomie albo użyj kontrolowanych pól. Rzucenie wyjątku jest inną ścieżką obsługi, zwykle z Error Boundary.'],
['Wyjaśnij granicę lokalnej akcji i Server Function.','Function action w SPA nie staje się automatycznie backendem.','Lokalna funkcja akcji wykonuje się w kodzie klienta. Zapis na serwer wymaga API lub obsługi Server Functions przez środowisko. Progressive enhancement bez JS nie jest gwarancją każdej lokalnej funkcji action w zwykłym Vite SPA.'],
['Nie dodawaj preventDefault do funkcji action.','Parametr to FormData.','Akcja otrzymuje FormData; nie ma preventDefault. React obsługuje wysłanie funkcji action. Handler onSubmit to odrębny mechanizm, w którym parametr jest zdarzeniem.'],
['Nie wywołuj asynchronicznego dispatchAction poza kontekstem akcji bez transition.','Najprościej przekaż funkcję jako form action.','Użyj form action z wyniku useActionState. Jeśli wywołujesz dispatch programowo, zapewnij kontekst akcji, np. startTransition; zwykłe wywołanie poza nim może nie śledzić pending prawidłowo.'],
['Rozróżnij sekwencyjną kolejkę hooka od gwarancji pojedynczego zapisu w bazie.','Disabled to UX, nie idempotencja backendu.','Kolejka useActionState wykonuje działania po kolei, a disabled ogranicza zwykłe wielokrotne kliknięcia. Serwer nadal może potrzebować klucza idempotencji, zwłaszcza przy ponawianiu żądań i wielu kartach.'],
['Nie przywiązuj draftu komentarza do innego ticketu przypadkowo.','Przy zmianie obiektu użyj świadomej tożsamości formularza.','W rodzicu renderuj CommentForm key={ticket.id} i przekaż save związane z tym ticketem. Jeśli akcja już trwa, zmiana widoku nie anuluje automatycznie operacji sieciowej; wynik musi nadal trafić do właściwego rekordu.'],
['Sprawdź typ akcji z brakującym polem wyniku.','Promise<CommentState> kontroluje wszystkie gałęzie.',model+'\n// @ts-expect-error: brak wymaganych pól stanu\nconst wrong:CommentState={error:null};'],
['Przetestuj krótką treść, błąd serwera i sukces z resetem.','Reset DOM i pending testuj w przeglądarce.',form],
];
const concepts=[
['Jaki argument otrzymuje funkcja form action bez useActionState?','FormData','SubmitEvent','Poprzedni stan','Sam string'],
['Jakie argumenty dostaje akcja useActionState z formularza?','Poprzedni stan i FormData','Tylko zdarzenie','Tylko props','Tylko errors'],
['Co zwraca useActionState?','Stan, funkcję akcji i isPending','Dwa refy','Sam Promise','Errors i register'],
['Skąd importować useActionState?','Z react','Z react-dom','Z zod','Z react-hook-form'],
['Skąd importować useFormStatus?','Z react-dom','Z react','Z node:fs','Z zod'],
['Gdzie wywołać useFormStatus dla formularza?','W komponencie renderowanym wewnątrz niego','W komponencie tworzącym ten form','Wyłącznie poza React','W pliku CSS'],
['Co jest źródłem wyniku state?','Wartość zwrócona przez akcję','Zawsze values DOM','Tylko initialState na zawsze','Props key'],
['Czy FormData ma preventDefault?','Nie','Tak','Tylko w React 19','Tylko w TSX'],
['Co dzieje się z niekontrolowanymi polami po normalnym zakończeniu akcji?','React może je automatycznie zresetować','Zawsze stają się kontrolowane','Zawsze zapisują localStorage','Zmieniają name'],
['Czy zwrócone {error} jest rzuconym wyjątkiem?','Nie','Tak','Tylko dla async','Tylko po await'],
['Jak zachować treść po błędzie w przykładzie?','Kontrolowane pole i reset tylko po ok','Zawsze reset w finally','Usunąć name','Użyć losowego key co render'],
['Co zawiera state.text po błędzie walidacji tekstu?','Surową treść do poprawienia','Zawsze pusty napis','Nowy ID','Promise'],
['Co trafia do save przy poprawnym tekście?','Tekst po trim','Obiekt zdarzenia','File bez sprawdzenia','Poprzedni błąd'],
['Czy save wywoła się dla File w text?','Nie','Tak jako string','Tylko po minLength','Tylko dla pustego File'],
['Po co await save?','Sukces dopiero po zakończeniu operacji','Aby usunąć typy','Aby zmienić name','Aby przełączyć CSS'],
['Czy catch może otrzymać coś innego niż Error?','Tak','Nie','Tylko w JSX','Tylko w sieci'],
['Czy lokalne action staje się Server Function?','Nie','Tak automatycznie','Tylko z async','Tylko z name'],
['Czy Vite SPA z lokalnym action gwarantuje działanie bez JS?','Nie','Tak','Tylko z textarea','Tylko po build'],
['Czy pending potwierdza powodzenie operacji?','Nie, oznacza trwanie pracy','Tak','Tylko w TSX','Tylko dla FormData'],
['Dlaczego disabled na przycisku nie gwarantuje braku duplikatów w bazie?','Nie zastępuje idempotencji serwera','React zawsze duplikuje','FormData usuwa ID','Disabled nie działa nigdy'],
['Jak uruchamia się kilka akcji tego samego useActionState?','Są kolejkowane sekwencyjnie','Zawsze pomijane','Zawsze w losowej kolejności','Każda dostaje initial zamiast previous'],
['Po co readOnly podczas zapisu?','Chroni przed edycją treści w toku operacji','Usuwa pole z FormData','Resetuje state','Waliduje serwer'],
['Czy readOnly textarea pozostaje w danych?','Tak, jeśli ma name i nie jest disabled','Nie nigdy','Tylko bez name','Tylko jako File'],
['Co daje key ticket.id przy CommentForm?','Nową instancję dla innego ticketu','Automatyczne anulowanie HTTP','Globalny cache wszystkich draftów','Walidację treści'],
['Czy odmontowanie automatycznie anuluje zapis sieciowy?','Nie','Tak','Tylko przy key','Tylko po useId'],
['Co sygnalizuje role=status?','Komunikat wyniku dla technologii asystujących','Wymusza zapis','Blokuje input','Nadaje ID'],
['Co sprawdza typ Promise<CommentState>?','Kształt zwracanego wyniku akcji','Czy serwer zaakceptuje tekst','Czy fokus działa','Czy textarea ma style'],
['Czy SSR dowodzi działania automatycznego resetu po akcji?','Nie, potrzebne zachowanie renderera/DOM','Tak','Tylko przy async','Tylko z formStatus'],
['Gdzie walidować dane z FormData?','W akcji i niezależnie na serwerze','Wyłącznie w typie TS','Tylko w placeholder','Tylko w CSS'],
['Jak śledzić pending przy dispatch programowym?','Zapewnić kontekst akcji, np. startTransition','Wywołać podczas renderu','Dodać key=Date.now()','Zamienić action na onClick bez kontekstu'],
];
const quiz=concepts.map(row=>[...row,'Akcje mają własny kontrakt argumentów i wyniku. Pending, reset i obsługa błędów zależą od poprawnego włączenia akcji w drzewo formularza.']);
quiz.push(...require('./practice_form_03.cjs').quiz.filter(row=>!row[6]).slice(0,30).map(row=>['Powtórka danych akcji: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const n of [0,1,2,3,4,999,1000,1001])add('Długość '+n,`readComment(make("a".repeat(${n}))).error??"OK"`,n<3?'Minimum 3 znaki.':n>1000?'Maksimum 1000 znaków.':'OK');
for(const [label,expr,a]of [
['Same spacje','readComment(make("    ")).error','Minimum 3 znaki.'],['Trim do dwóch','readComment(make("  ab  ")).error','Minimum 3 znaki.'],['Trim do trzech','readComment(make(" abc ")).error??"OK"','OK'],['Surowy tekst','readComment(make(" abc ")).text.length',5],['Brak wpisu','readComment(new FormData()).error','Podaj tekst komentarza.'],['Plik zamiast tekstu','(()=>{const d=new FormData();d.set("text",new File(["abc"],"a.txt"));return readComment(d).error;})()','Podaj tekst komentarza.'],['Początkowy attempt','initial.attempt',0],['Początkowe ok','initial.ok',false],['Początkowy error','initial.error===null',true],['Get brak','new FormData().get("text")===null',true],['Tekst pozostaje string','typeof make("abc").get("text")','string'],['Pierwszy wpis','(()=>{const d=make("abc");d.append("text","def");return readComment(d).text;})()','abc'],['Liczba wpisów','(()=>{const d=make("abc");d.append("text","def");return d.getAll("text").length;})()',2],['Set zastępuje','(()=>{const d=make("abc");d.set("text","def");return readComment(d).text;})()','def'],['Stan nie jest true','Boolean(initial)',true],['Długość po trim','"  abc  ".trim().length',3],['Brakujące text nie string','typeof new FormData().get("text")','object'],['Identyfikator nazwy','make("abc").has("text")',true],['Literówka nazwy','make("abc").has("tekst")',false],['Zachowanie wiersza','readComment(make("abc\\ndef")).text.includes("\\n")',true],['Długość wnętrza','readComment(make("a b")).error??"OK"','OK'],['Spacje skrajne','readComment(make("  a  ")).error','Minimum 3 znaki.'],['Tekst cyfr','readComment(make("123")).error??"OK"','OK'],['Pusty string','readComment(make("")).text.length',0],['Brak tekstu wynik','readComment(new FormData()).text.length',0],['Nowy wynik','readComment(make("abc"))===readComment(make("abc"))',false],['Wejście nienaruszone','(()=>{const d=make(" abc ");readComment(d);return d.get("text");})()',' abc '],['Stan spread','({...initial,attempt:2}).attempt',2],['Previous bez mutacji','(()=>{const next={...initial,attempt:initial.attempt+1};return initial.attempt;})()',0],['Komunikat nie nadaje ok','({...initial,error:"Błąd"}).ok',false],['Trim nie zmienia źródła','(()=>{const value=" abc ";value.trim();return value.length;})()',5],['Name z id to nie to samo','make("abc").get("comment")===null',true],
])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);
for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': co odczyta akcja komentarza?',a,...wrong,'Czytnik rozróżnia brak i File od stringa, waliduje tekst po trim, lecz zachowuje surową treść do poprawienia.',model+'\nfunction make(text:string){const data=new FormData();data.set("text",text);return data;}console.log('+expr+');']);}
module.exports={model,button,form,simple,exercises,quiz,project};
