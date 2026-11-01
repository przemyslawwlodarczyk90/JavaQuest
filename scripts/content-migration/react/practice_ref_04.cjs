// Wzorzec: https://www.w3.org/WAI/ARIA/apg/patterns/tabs/
const model=`export function nextTab(ids:readonly string[],current:string,key:string,orientation:"horizontal"|"vertical"="horizontal",dir:"ltr"|"rtl"="ltr"):string|null{if(!ids.length)return null;if(key==="Home")return ids[0];if(key==="End")return ids[ids.length-1];let step=0;if(orientation==="vertical")step=key==="ArrowDown"?1:key==="ArrowUp"?-1:0;else step=key==="ArrowRight"?(dir==="ltr"?1:-1):key==="ArrowLeft"?(dir==="ltr"?-1:1):0;if(!step)return null;const index=ids.indexOf(current);return index<0?ids[0]:ids[(index+step+ids.length)%ids.length];}`;
const tabs=`import {useId,useRef,useState} from "react";import type {KeyboardEvent,ReactNode} from "react";
${model}
export type Tab<T extends string>={id:T;label:string;content:ReactNode;disabled?:boolean};export type TabsProps<T extends string>={tabs:readonly Tab<T>[];label:string;value?:NoInfer<T>;defaultValue?:NoInfer<T>;onChange?:(id:T)=>void;orientation?:"horizontal"|"vertical";dir?:"ltr"|"rtl"};
export function Tabs<T extends string>({tabs,label,value,defaultValue,onChange,orientation="horizontal",dir="ltr"}:TabsProps<T>){const [internal,setInternal]=useState<T|null>(()=>defaultValue??tabs.find(tab=>!tab.disabled)?.id??null);const prefix=useId();const buttons=useRef(new Map<T,HTMLButtonElement>());if(new Set(tabs.map(tab=>tab.id)).size!==tabs.length)throw Error("Powtórzone ID zakładki");const enabled=tabs.filter(tab=>!tab.disabled);const requested=value===undefined?internal:value;const active=enabled.some(tab=>tab.id===requested)?requested:enabled[0]?.id??null;function select(id:T){if(!enabled.some(tab=>tab.id===id))return;if(value===undefined)setInternal(id);if(id!==active)onChange?.(id);}function keyDown(event:KeyboardEvent<HTMLButtonElement>,id:T){if(event.ctrlKey||event.altKey||event.metaKey||event.nativeEvent.isComposing)return;const next=nextTab(enabled.map(tab=>tab.id),id,event.key,orientation,dir);if(next===null)return;const tab=enabled.find(tab=>tab.id===next);if(!tab)return;event.preventDefault();select(tab.id);buttons.current.get(tab.id)?.focus();}const tabId=(id:T)=>prefix+"-tab-"+encodeURIComponent(id);const panelId=(id:T)=>prefix+"-panel-"+encodeURIComponent(id);return <div dir={dir}><div role="tablist" aria-label={label} aria-orientation={orientation}>{tabs.map(tab=><button key={tab.id} ref={node=>{if(!node)return;buttons.current.set(tab.id,node);return ()=>{if(buttons.current.get(tab.id)===node)buttons.current.delete(tab.id);};}} type="button" role="tab" id={tabId(tab.id)} aria-selected={tab.id===active} aria-controls={panelId(tab.id)} tabIndex={tab.id===active?0:-1} disabled={tab.disabled} onClick={()=>select(tab.id)} onKeyDown={event=>keyDown(event,tab.id)}>{tab.label}</button>)}</div>{tabs.map(tab=><div key={tab.id} role="tabpanel" id={panelId(tab.id)} aria-labelledby={tabId(tab.id)} hidden={tab.id!==active} tabIndex={0}>{tab.content}</div>)}{!enabled.length&&<p>Brak dostępnych zakładek.</p>}</div>;}`;
const demo=`${tabs}
export type DetailTab="details"|"activity"|"history";function DraftPanel(){const [text,setText]=useState("");return <label>Roboczy komentarz<textarea value={text} onChange={event=>setText(event.currentTarget.value)}/></label>;}
export function TicketDetails({ticketId,description}:{ticketId:number;description:string}){const [tab,setTab]=useState<DetailTab>("details");return <section><h2>Ticket #{ticketId}</h2><button type="button" onClick={()=>setTab("activity")}>Przejdź do Aktywności</button><Tabs<DetailTab> label="Sekcje ticketu" value={tab} onChange={setTab} tabs={[{id:"details",label:"Szczegóły",content:<p>{description}</p>},{id:"activity",label:"Aktywność",content:<DraftPanel/>},{id:"history",label:"Historia",content:<p>Historia zmian statusu.</p>}]}/></section>;}
export function TabsDemo(){const [ticketId,setTicketId]=useState(0);return <main><h1>Zakładki</h1><button type="button" onClick={()=>setTicketId(id=>id===0?43:0)}>Zmień ticket i zresetuj kartę</button><TicketDetails key={ticketId} ticketId={ticketId} description={"Opis ticketu #"+ticketId}/><Tabs label="Tryb niekontrolowany" defaultValue="b" tabs={[{id:"a",label:"A",content:<p>A</p>},{id:"b",label:"B",content:<p>B</p>}]}/></main>;}`;
const exercises=[
['Zdefiniuj generyczne Tab<T> i TabsProps<T>.','ID są unią stringów, a panele to ReactNode.',tabs],
['Użyj value i onChange dla wariantu kontrolowanego.','Nie twórz drugiej kopii aktywnego props.',tabs],
['Użyj defaultValue dla wariantu niekontrolowanego.','Wartość początkowa nie jest bieżącym sterowaniem.',tabs],
['Rozpoznawaj tryb przez value===undefined.','Nie używaj truthiness ID.',tabs],
['Nie przełączaj trybu kontrolowanego w trakcie życia instancji.','Wybierz świadomy kontrakt API.','Przykład wybiera właściciela według obecności value. Rodzic powinien konsekwentnie sterować value albo pozostawić stan komponentowi. Przy zmianie trybu ustal reset lub nową instancję, zamiast liczyć na przypadkowy stan internal.'],
['Obsłuż pustą listę bez odczytu tabs[0].id.','Wszystkie hooki nadal są wywołane.',tabs],
['Obsłuż zakładki disabled w aktywacji i nawigacji.','Nie ustawiaj fokusu na wyłączonej kontrolce.',tabs],
['Odrzuć powtórzone identyfikatory danych.','Key i powiązania ARIA muszą być jednoznaczne.',tabs],
['Nadaj tablist dostępną nazwę.','Użyj aria-label lub odpowiedniego aria-labelledby.',tabs],
['Nadaj przyciskom role=tab oraz aria-selected.','Każda zakładka ma natywny button.',tabs],
['Połącz tab z panelem przez aria-controls.','Id panelu istnieje w tej samej instancji.',tabs],
['Połącz panel z tabem przez aria-labelledby.','Panel ma role=tabpanel.',tabs],
['Użyj useId jako prefiksu instancji.','Dwie listy zakładek nie mogą duplikować DOM ID.',tabs],
['Zbuduj roving tabindex: aktywna 0, pozostałe -1.','Tab przechodzi przez listę jako jeden przystanek.',tabs],
['Dodaj ArrowRight i ArrowLeft z zawijaniem.','Po ostatniej wracamy do pierwszej.',tabs],
['Dodaj Home i End.','Przechodzą na skraje dostępnych zakładek.',tabs],
['Nie przejmuj ArrowUp i ArrowDown w poziomej liście.','Pozostaw przewijanie strony zgodnie ze wzorcem.',tabs],
['Dodaj pionową orientację z ArrowDown/ArrowUp.','Aria-orientation musi odpowiadać logice.',tabs],
['Uwzględnij dir=rtl dla poziomego kierunku strzałek.','Kierunek prezentacji jest częścią kontraktu.',tabs],
['Przenieś fokus do nowej zakładki po nawigacji.','Same aria-selected nie zmienia rzeczywistego fokusu.',tabs],
['Trzymaj mapę przycisków w refie z cleanup.','Nie czytaj mapy w renderze do wyliczania widoku.',tabs],
['Zostaw panele zamontowane przez hidden.','Szkic komentowania zachowuje stan.',tabs],
['Dodaj tabIndex panelu, gdy potrzebny jest wejściowy fokus jego treści.','Przykład nadaje panelom 0; hidden usuwa nieaktywne z nawigacji.',tabs],
['Przełącz na Aktywność z przycisku rodzica.','Wariant kontrolowany pozwala sterować widokiem z zewnątrz.',demo],
['Zresetuj kartę do Szczegółów po wyborze innego ticketu.','Nowy key karty tworzy nową lokalną instancję.',demo],
['Sprawdź zachowanie niepoprawnego lub wyłączonego value.','Przykład pokazuje pierwszą dostępną zakładkę.','Fallback jest wyliczany z danych, ale sam nie wywołuje onChange podczas renderu. Jeśli lista jest dynamicznie edytowana, produkt potrzebuje także polityki przywracania fokusu po usunięciu aktywnej kontrolki.'],
['Wyjaśnij wybór automatycznej i ręcznej aktywacji.','To różne wzorce klawiatury.','Ten przykład aktywuje po nawigacji strzałkami. Dla paneli o zauważalnym opóźnieniu rozważ wariant ręczny: strzałki przesuwają fokus, Enter lub Space zatwierdzają panel. Nie obiecuj ręcznego trybu bez osobnej implementacji.'],
['Sprawdź literówkę value przy jawnej unii ID.','NoInfer zapobiega poszerzeniu T przez samo błędne value.',tabs+'\nexport function BadTab(){return (\n// @ts-expect-error: nieznany identyfikator\n<Tabs<"a"|"b"> label="Test" value="c" tabs={[{id:"a",label:"A",content:null},{id:"b",label:"B",content:null}]}/>); }'],
['Nie używaj role=tab do zwykłych przycisków filtrujących bez paneli.','Wzorzec ma konkretne relacje i klawiaturę.','Dla przełącznika filtra listy często lepsza jest nazwana grupa button z aria-pressed. Tablist wiąże zakładki z panelami i wymaga odpowiedniej nawigacji. Same role nie dodają zachowania.'],
['Przetestuj mysz, Tab, strzałki, Home/End oraz zachowanie szkicu.','Pełna obsługa klawiatury i czytnika wymaga testu przeglądarkowego.',demo],
];
const concepts=[
['Co opisuje tablist?','Nazwaną grupę zakładek związanych z panelami','Dowolny filtr bez paneli','Każdą listę ul','Tylko CSS'],
['Jaka rola należy do zakładki?','Tab','Tabpanel','Dialog','Status'],
['Jaka rola należy do treści zakładki?','Tabpanel','Tablist','Button zawsze','Alert obowiązkowo'],
['Co wskazuje aria-controls zakładki?','Id jej panelu','Sam tekst etykiety','Key React','Nazwę callbacku'],
['Co wskazuje aria-labelledby panelu?','Id jego zakładki','Wszystkie inputy','Wyłącznie parent','Dowolny className'],
['Co wyznacza aria-selected?','Aktywną zakładkę','Fokus DOM zawsze automatycznie','Widoczność całej strony','Status sieci'],
['Co oznacza roving tabindex?','Jeden tab ma 0, pozostałe -1','Każdy tab ma 0','Wszystkie zawsze -1','Wyłącznie panel ma ref'],
['Jak Tab wchodzi do tablist?','Na aktywną zakładkę w tym wzorcu','Na każdą po kolei','Zawsze na ostatnią','Zawsze poza listę'],
['Co robi ArrowRight w poziomym ltr?','Przechodzi do następnej dostępnej','Zawsze przewija pionowo','Zamyka kartę','Wysyła formularz'],
['Co robi Left na pierwszej zakładce ltr?','Zawija do ostatniej dostępnej','Zawsze nic','Usuwa panel','Przechodzi do body'],
['Co robi Home?','Pierwsza dostępna zakładka','Zawsze reload','Ostatni panel','Zamknięcie modala'],
['Co robi End?','Ostatnia dostępna zakładka','Zawsze pierwsza','Usunięcie szkicu','Zapis API'],
['Czy pozioma lista przejmuje Up/Down?','Nie w tym wzorcu','Tak zawsze','Tylko dla disabled','Tylko w TypeScript'],
['Co zmienia vertical?','Używa Up/Down do nawigacji','Usuwa wszystkie role','Zawsze zmienia state globalnie','Wyłącza keyboard'],
['Co określa kontrolowany aktywny tab?','Value rodzica','DefaultValue po każdym renderze','Map refów','Pierwszy string w module'],
['Co robi defaultValue?','Inicjalizuje wariant lokalny','Stale nadpisuje wybór','Zawsze tworzy nową instancję','Wywołuje onChange w renderze'],
['Czy hidden odmontowuje panel?','Nie','Tak','Tylko z role','Tylko w JSX'],
['Co robi warunkowe usunięcie panelu?','Może usunąć jego lokalny stan','Zawsze zapisuje szkic','Tylko zmienia opacity','Zachowuje wszystkie efekty bez zmian'],
['Czy same role dodają obsługę strzałek?','Nie','Tak','Tylko dla button','Tylko w React 19'],
['Czy disabled tab powinien być celem nawigacji tego przykładu?','Nie','Tak','Tylko przy Home','Tylko w rtl'],
['Jak zapobiec duplikatom DOM id dwóch instancji?','Unikalnym prefiksem useId','Samym id danych w obu','Zawsze indeksem 0','Nazwą komponentu'],
['Czy generic T zawsze ochroni przed poszerzeniem typu przez value?','Potrzebny jest odpowiedni kontrakt, np. NoInfer lub jawne T','Tak niezależnie od deklaracji','Tylko w JSX','Nigdy można typować'],
['Kiedy ręczna aktywacja bywa lepsza?','Gdy panel ma zauważalne opóźnienie','Zawsze dla statycznego tekstu','Tylko gdy brak button','Tylko po reset'],
['Czy przykład implementuje ręczną aktywację?','Nie, aktywuje podczas nawigacji','Tak automatycznie','Tylko z defaultValue','Tylko w rtl'],
['Czy fokus i wybór są tym samym mechanizmem?','Nie','Tak','Tylko w TSX','Tylko przy tabIndex=0'],
['Co jeśli rodzic kontrolowany nie zmieni value po onChange?','Widok pozostaje pod jego kontrolą','Komponent powinien go potajemnie nadpisać','Zawsze crash','Reset całej aplikacji'],
['Czy pusta lista powinna czytać tabs[0].id?','Nie','Tak','Tylko gdy readonly','Tylko w React 19'],
['Czy zmiana danych tabs automatycznie gwarantuje dobry fokus po usunięciu kontrolki?','Nie, wymaga polityki i testu','Tak','Tylko z Map','Tylko w TSX'],
['Czy tabpanel z tekstem może potrzebować tabIndex=0?','Tak','Nie','Tylko w hidden','Tylko na serwerze'],
['Jak potwierdzić czytnik i klawiaturę?','Testem w przeglądarce z odpowiednimi narzędziami','Samym tsc','Tylko JSON','Liczeniem role'],
];
const quiz=concepts.map(row=>[...row,'Zakładki to pełny wzorzec semantyki, relacji, fokusu i nawigacji. Kontrola stanu oraz zachowanie paneli są osobnymi decyzjami.']);quiz.push(...require('./practice_ref_02.cjs').quiz.slice(0,30).map(row=>['Powtórka fokusu zakładek: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const current of ['a','b','c'])for(const key of ['ArrowRight','ArrowLeft','Home','End']){const ids=['a','b','c'];const i=ids.indexOf(current);add('LTR '+current+' '+key,`nextTab(ids,${JSON.stringify(current)},${JSON.stringify(key)})`,key==='Home'?'a':key==='End'?'c':ids[(i+(key==='ArrowRight'?1:-1)+3)%3]);}
for(const current of ['a','c'])for(const key of ['ArrowDown','ArrowUp','ArrowRight','ArrowLeft']){const ids=['a','b','c'];const i=ids.indexOf(current);add('Vertical '+current+' '+key,`nextTab(ids,${JSON.stringify(current)},${JSON.stringify(key)},"vertical")??"brak"`,key==='ArrowDown'?ids[(i+1)%3]:key==='ArrowUp'?ids[(i+2)%3]:'brak');}
for(const [label,expr,a]of [['RTL right','nextTab(ids,"a","ArrowRight","horizontal","rtl")','c'],['RTL left','nextTab(ids,"a","ArrowLeft","horizontal","rtl")','b'],['Pusta lista','nextTab([],"a","ArrowRight")??"brak"','brak'],['Jedna right','nextTab(["a"],"a","ArrowRight")','a'],['Jedna left','nextTab(["a"],"a","ArrowLeft")','a'],['Nieznane ID','nextTab(ids,"x","ArrowRight")','a'],['Nieobsługiwane Tab','nextTab(ids,"a","Tab")??"brak"','brak'],['Nieobsługiwane Enter','nextTab(ids,"a","Enter")??"brak"','brak'],['Poziome Down','nextTab(ids,"a","ArrowDown")??"brak"','brak'],['Poziome Up','nextTab(ids,"a","ArrowUp")??"brak"','brak'],['Ominięty disabled','nextTab(["a","c"],"a","ArrowRight")','c'],['Home w pionie','nextTab(ids,"c","Home","vertical")','a'],['End w pionie','nextTab(ids,"a","End","vertical")','c'],['Brak mutacji','(()=>{nextTab(ids,"a","ArrowRight");return ids.join(",");})()','a,b,c'],['Case klawisza','nextTab(ids,"a","arrowright")??"brak"','brak'],['Home puste','nextTab([],"a","Home")??"brak"','brak'],['End puste','nextTab([],"a","End")??"brak"','brak'],['Pojedyncze Home','nextTab(["a"],"a","Home")','a'],['Pojedyncze End','nextTab(["a"],"a","End")','a'],['RTL koniec','nextTab(ids,"c","ArrowLeft","horizontal","rtl")','a']])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);for(const [label,expr,a]of cases){const wrong=['a','b','c','brak','false','true'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki cel nawigacji zakładek?',a,...wrong,'Helper otrzymuje już dostępne identyfikatory, stosuje kierunek i zawijanie. Null oznacza brak obsługi klawisza lub pustą listę.',model+'\nconst ids=["a","b","c"];console.log('+expr+');']);}
const project={'src/Tabs.tsx':demo,'src/main.tsx':'import {createRoot} from "react-dom/client";import {TabsDemo} from "./Tabs";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<TabsDemo/>);'};
module.exports={model,tabs,demo,exercises,quiz,project};
