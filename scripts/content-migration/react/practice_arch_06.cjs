const model=`export type Prefs={login:string;dateFormat:"relative"|"full";density:"comfortable"|"compact"};export const DEFAULT_PREFS:Prefs={login:"anna",dateFormat:"relative",density:"comfortable"};
export function mergePrefs(prefs:Prefs,changes:Partial<Prefs>):Prefs{return {...prefs,...changes};}
export function relativeDate(iso:string,now:number):string{const time=Date.parse(iso);if(!Number.isFinite(time)||!Number.isFinite(now))return "Niepoprawna data";const days=Math.trunc((time-now)/86400000);return new Intl.RelativeTimeFormat("pl-PL",{numeric:"auto"}).format(days,"day");}
export function fullDate(iso:string):string{const time=Date.parse(iso);return Number.isFinite(time)?new Intl.DateTimeFormat("pl-PL",{dateStyle:"medium",timeStyle:"short",timeZone:"UTC"}).format(time):"Niepoprawna data";}`;
const context=`import {createContext,useContext,useState} from "react";import type {ReactNode} from "react";
${model}
export type PrefsContextValue={prefs:Prefs;updatePrefs:(changes:Partial<Prefs>)=>void};
export const PrefsContext=createContext<PrefsContextValue|null>(null);
export function PrefsProvider({children,initialPrefs=DEFAULT_PREFS}:{children:ReactNode;initialPrefs?:Prefs}){const [prefs,setPrefs]=useState<Prefs>(()=>({...initialPrefs}));function updatePrefs(changes:Partial<Prefs>){setPrefs(previous=>mergePrefs(previous,changes));}return <PrefsContext value={{prefs,updatePrefs}}>{children}</PrefsContext>;}
export function usePrefs():PrefsContextValue{const value=useContext(PrefsContext);if(value===null)throw Error("usePrefs wymaga PrefsProvider");return value;}`;
const consumers=`${context}
export function DateCell({iso,now}:{iso:string;now:number}){const {prefs}=usePrefs();const valid=Number.isFinite(Date.parse(iso));const text=prefs.dateFormat==="relative"?relativeDate(iso,now):fullDate(iso);return valid?<time dateTime={iso}>{text}</time>:<span>{text}</span>;}
export function SettingsPanel(){const {prefs,updatePrefs}=usePrefs();return <fieldset><legend>Preferencje</legend><label><input type="checkbox" checked={prefs.density==="compact"} onChange={event=>updatePrefs({density:event.currentTarget.checked?"compact":"comfortable"})}/>Tabela kompaktowa</label><label><input type="checkbox" checked={prefs.dateFormat==="relative"} onChange={event=>updatePrefs({dateFormat:event.currentTarget.checked?"relative":"full"})}/>Daty względne</label></fieldset>;}
export function UserGreeting(){const {prefs}=usePrefs();return <h2>Witaj, {prefs.login}</h2>;}
export function TicketRow({title,iso,now}:{title:string;iso:string;now:number}){const {prefs}=usePrefs();return <tr className={prefs.density==="compact"?"row row--compact":"row"}><td>{title}</td><td><DateCell iso={iso} now={now}/></td></tr>;}`;
const app=`${consumers}
function Screen(){const now=Date.parse("2026-10-29T12:00:00Z");return <main><UserGreeting/><SettingsPanel/><p>Przykład używa stałego czasu odniesienia i UTC dla pełnych dat.</p><table><caption>Tickety</caption><thead><tr><th scope="col">Tytuł</th><th scope="col">Utworzono</th></tr></thead><tbody><TicketRow title="API" iso="2026-10-28T12:00:00Z" now={now}/></tbody></table></main>;}
export default function App(){return <PrefsProvider><Screen/></PrefsProvider>;}`;
const exercises=[
['Zdefiniuj Prefs z loginem, formatem dat i gęstością.','Użyj unii literalnych dla opcji.',model],
['Utwórz createContext<PrefsContextValue|null>(null) poza komponentem.','Null jest sygnałem braku providera.',context],
['Zbuduj PrefsProvider z własnym stanem.','Context transportuje wartość, stan pozostaje w providerze.',context],
['Użyj składni providera React 19 bez .Provider.','Sprawdź wersję projektu wykonującego ćwiczenie.',context],
['Napisz usePrefs rzucające czytelny błąd poza providerem.','Po sprawdzeniu typ wyniku nie zawiera null.',context],
['Napisz mergePrefs bez mutacji wejścia.','Partial pozwala zmienić tylko część ustawień.',model],
['W updatePrefs użyj funkcjonalnego setPrefs.','Dwie aktualizacje przed renderem nie powinny się zgubić.',context],
['Podaj dzieci providera jako ReactNode.','Kompozycja nie wymaga przekazywania prefs przez pośredników.',context],
['Dodaj initialPrefs i nazwij je jako wartość początkową.','Zmiana props nie oznacza ciągłego nadpisywania stanu.',context],
['Zbuduj SettingsPanel odczytujący kontekst.','Checkbox odczytuje checked.',consumers],
['Podłącz przełącznik density do kompaktowego wiersza.','TicketRow czyta najbliższy provider.',consumers],
['Podłącz przełącznik relative/full do DateCell.','Nie kopiuj dateFormat do lokalnego stanu.',consumers],
['Wyświetl login głęboko w UserGreeting bez props drilling.','Tylko konsument potrzebuje usePrefs.',consumers],
['Napisz relativeDate z jawnym now zamiast odczytu zegara w renderze.','Wynik jest deterministyczny dla tych samych danych.',model],
['Określ zasady obliczania względnych dni.','Przykład używa pełnych odstępów 24 godzin, nie dni kalendarzowych.',model],
['Obsłuż przyszłą datę i ujemną różnicę czasu.','Intl.RelativeTimeFormat obsługuje oba kierunki.',model],
['Obsłuż niepoprawną datę bez wyjątku formatowania.','Nie generuj time dateTime z błędnym iso.',consumers],
['Ustal UTC jako jawną strefę pełnego formatowania.','To decyzja przykładu, nie automatyczna strefa użytkownika.',model],
['Usuń props prefs z komponentów, które tylko je przekazywały.','Nie usuwaj innych potrzebnych props.',app],
['Zademonstruj zagnieżdżone providery.','Konsument wybiera najbliższy. ',consumers+'\nexport function Nested(){return <PrefsProvider initialPrefs={{login:"anna",density:"comfortable",dateFormat:"relative"}}><UserGreeting/><PrefsProvider initialPrefs={{login:"piotr",density:"compact",dateFormat:"full"}}><UserGreeting/></PrefsProvider></PrefsProvider>;}'],
['Przetestuj usePrefs bez providera przez render konsumenta.','Oczekuj konkretnego błędu kontraktu.',consumers],
['Sprawdź literówkę density=tiny.','Generyki kontrolują oba końce kontraktu.',context+'\nexport function Wrong(){const {updatePrefs}=usePrefs();\n// @ts-expect-error: nieobsługiwana gęstość\nupdatePrefs({density:"tiny"});return null;}'],
['Wyjaśnij statyczną wartość domyślną createContext.','To fallback, nie dynamiczny magazyn.','Default context jest używany, gdy nad konsumentem brak odpowiadającego providera. Nie jest automatycznie aktualizowany przez stan innej instancji. Wzorzec null pozwala wykryć brak wymaganego providera zamiast po cichu używać ustawień domyślnych.'],
['Nie traktuj providera jako źródła uwierzytelnienia.','Login przykładu jest preferencją danych UI.','Login w tym przykładzie identyfikuje osobę na ekranie. W prawdziwej aplikacji tożsamość i uprawnienia muszą wynikać z systemu sesji, a nie z możliwości ustawienia stringa w kontekście.'],
['Rozróżnij częste filtry ekranu od preferencji wielu ekranów.','Nie wrzucaj całego stanu do jednego kontekstu.','Query tabeli może zostać blisko tabeli. Preferencje formatu i gęstości są wspólne wielu odległym konsumentom. Context nie jest automatycznie najlepszym miejscem dla każdej zmiennej.'],
['Wyjaśnij wpływ nowej referencji value na konsumentów.','Context porównuje value przez Object.is.','Nowy obiekt value może powiadomić konsumentów nawet przy takich samych polach. Memoizacja value i callbacku bywa optymalizacją po pomiarze, ale nie zmienia poprawności i nie zastępuje sensownego podziału kontekstów.'],
['Porównaj kontekst z kompozycją children jako alternatywą.','Nie każdy łańcuch dwóch props to problem.','Dla krótkiej ścieżki zwykłe props są czytelne. Gotowy JSX przekazany jako children może ominąć pośredników. Context jest przydatny, gdy te same dane są potrzebne wielu dalekim konsumentom.'],
['Nie twórz createContext wewnątrz renderu providera.','Tożsamość kontekstu musi być wspólna.',context],
['Przetestuj dwa niezależne providery na stronie.','Każdy ma własny stan początkowy.',app],
['Zbuduj ekran, w którym ustawienia zmieniają daty i gęstość wierszy.','Sprawdź etykiety oraz brak przekazywania prefs przez pośredników.',app],
];
const concepts=[
['Co robi Context?','Dostarcza wartość poddrzewu','Automatycznie zapisuje stan na serwerze','Zastępuje każdy useState','Zamraża dane'],
['Gdzie tworzyć createContext?','Na poziomie modułu','Co render providera','W handlerze checkboxa','W CSS'],
['Co odczytuje useContext?','Najbliższy odpowiedni provider powyżej','Zawsze najwyższy provider','Dowolny provider na stronie','Stan komponentu-dziecka'],
['Po co null jako fallback?','Wykrywa brak wymaganego providera w hooku','Wyłącza React','Usuwa typy','Zapisuje domyślne ustawienia'],
['Czy usePrefs zwraca null w poprawnym użyciu przykładu?','Nie, poza providerem rzuca błąd','Tak zawsze','Tylko po renderze','Tylko dla loginu anna'],
['Czy Context sam zarządza stanem?','Nie, stan może należeć do providera','Tak automatycznie','Tylko w React 19','Tylko z Partial'],
['Jak provider udostępnia value w React 19?','Przez element kontekstu z value','Tylko przez window','Przez props.key','Przez import state'],
['Czy .Provider znany ze starszego kodu nadal oznacza provider?','Tak','Nie','Tylko dla string','Tylko w JSX'],
['Co dzieje się przy zmianie value?','Konsumenci odpowiedniego kontekstu otrzymują aktualizację','Tylko rodzic się zmienia','Wszyscy na stronie zawsze tracą stan','Zmieniają się klucze'],
['Jak porównywana jest wartość kontekstu?','Object.is','Głęboki JSON diff','Tylko porównanie loginu','Zawsze po nazwie komponentu'],
['Czy memo komponentu automatycznie blokuje nowe dane useContext?','Nie','Tak','Tylko dla checkbox','Tylko w TSX'],
['Po co functional updatePrefs?','Scala zmiany z poprzednim stanem','Zamraża value','Resetuje context','Wysyła request'],
['Czy Partial<Prefs> dopuszcza density=tiny?','Nie','Tak','Tylko gdy login pusty','Tylko w dev'],
['Czy typ Partial waliduje dowolny JSON?','Nie','Tak','Tylko strict','Tylko z context'],
['Czy initialPrefs jest stale synchronizowane?','Nie, to wartość inicjalizacji','Tak zawsze','Tylko z useState','Tylko dla dat'],
['Czy każdy stan ma trafić do context?','Nie','Tak','Tylko query','Tylko zaznaczenie'],
['Jakie dane pasują do tego kontekstu?','Preferencje używane w wielu miejscach','Każdy znak jednego lokalnego pola','Tymczasowa pozycja kursora','Dowolny prywatny stan każdego wiersza'],
['Co to prop drilling?','Przekazywanie danych przez pośredników, którzy ich nie używają','Zwykły każdy prop','Dostęp do DOM','Sprawdzanie typów'],
['Jaka alternatywa bywa prostsza?','Props lub kompozycja children','Zawsze globalny store','Losowy key','UseEffect dla każdej wartości'],
['Czy dwa providery mają automatycznie wspólny stan?','Nie','Tak','Tylko z tym samym default','Tylko dla loginu'],
['Który provider wygrywa przy zagnieżdżeniu?','Najbliższy','Zawsze pierwszy w pliku','Zawsze z większym obiektem','Losowy'],
['Czy provider poniżej miejsca useContext wpływa na ten odczyt?','Nie','Tak','Tylko w React 19','Tylko bez memo'],
['Dlaczego jawne now w formatterze?','Zapewnia przewidywalny wynik i testy','Zastępuje provider','Waliduje login','Tworzy strefę automatycznie'],
['Czy pełne odstępy 24 godzin są tym samym co różnica dni kalendarzowych?','Nie','Tak','Tylko w UTC','Tylko dla przyszłości'],
['Jaką strefę ma fullDate przykładu?','UTC','Zawsze lokalną przeglądarki','Zawsze serwera','Losową'],
['Czy stały now sam aktualizuje etykietę z biegiem czasu?','Nie','Tak','Tylko po Intl','Tylko po useContext'],
['Co zrobić z błędną datą?','Pokazać jawny fallback','Wywołać Intl bez sprawdzenia i liczyć na sukces','Zamienić ją w dziś bez informacji','Zmienić login'],
['Czy login w zwykłym kontekście UI dowodzi uprawnień?','Nie','Tak','Tylko z TS','Tylko gdy readonly'],
['Czy useMemo value jest warunkiem poprawności Context?','Nie, to możliwa optymalizacja','Tak zawsze','Tylko dla React 19','Tylko dla Partial'],
['Jak sprawdzić brak providera?','Renderować konsumenta poza nim i oczekiwać błędu hooka','Tylko policzyć importy','Wyłącznie sprawdzić className','Dodać drugi useState'],
];
const quiz=concepts.map(row=>[...row,'Context dostarcza najbliższą wartość w drzewie, a własny hook pilnuje kontraktu. Stan i deterministyczne formatowanie są oddzielnymi odpowiedzialnościami.']);quiz.push(...require('./practice_arch_01.cjs').quiz.slice(0,30).map(row=>['Powtórka przepływu preferencji: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const density of ['comfortable','compact'])for(const format of ['relative','full'])for(const login of ['anna','piotr'])add('Zmiana '+density+'/'+format+'/'+login,`mergePrefs(DEFAULT_PREFS,{density:${JSON.stringify(density)},dateFormat:${JSON.stringify(format)},login:${JSON.stringify(login)}}).login`,login);
for(const days of [-3,-2,-1,0,1,2,3]){const expected={'-3':'3 dni temu','-2':'przedwczoraj','-1':'wczoraj','0':'dzisiaj','1':'jutro','2':'pojutrze','3':'za 3 dni'};add('Względnie '+days,`relativeDate(new Date(now+${days}*86400000).toISOString(),now)`,expected[days]);}
for(const [label,expr,a]of [['Gęstość zmiana','mergePrefs(DEFAULT_PREFS,{density:"compact"}).density','compact'],['Format zachowany','mergePrefs(DEFAULT_PREFS,{density:"compact"}).dateFormat','relative'],['Login zachowany','mergePrefs(DEFAULT_PREFS,{density:"compact"}).login','anna'],['Stary obiekt','(()=>{mergePrefs(DEFAULT_PREFS,{login:"piotr"});return DEFAULT_PREFS.login;})()','anna'],['Nowy obiekt','mergePrefs(DEFAULT_PREFS,{})===DEFAULT_PREFS',false],['Puste changes','mergePrefs(DEFAULT_PREFS,{}).density','comfortable'],['Niepoprawna data','relativeDate("zła",now)','Niepoprawna data'],['Niepoprawny now','relativeDate("2026-10-29T12:00:00Z",NaN)','Niepoprawna data'],['Niepoprawna pełna','fullDate("zła")','Niepoprawna data'],['Godzinę temu','relativeDate(new Date(now-3600000).toISOString(),now)','dzisiaj'],['Za godzinę','relativeDate(new Date(now+3600000).toISOString(),now)','dzisiaj'],['23 godziny','relativeDate(new Date(now-23*3600000).toISOString(),now)','dzisiaj'],['24 godziny','relativeDate(new Date(now-24*3600000).toISOString(),now)','wczoraj'],['Dwa scalenia','mergePrefs(mergePrefs(DEFAULT_PREFS,{login:"piotr"}),{density:"compact"}).login','piotr'],['Wszystkie klucze','Object.keys(mergePrefs(DEFAULT_PREFS,{})).length',3],['Pusty login to string','typeof mergePrefs(DEFAULT_PREFS,{login:""}).login','string'],['Format full','mergePrefs(DEFAULT_PREFS,{dateFormat:"full"}).dateFormat','full'],['Default format','DEFAULT_PREFS.dateFormat','relative'],['Default density','DEFAULT_PREFS.density','comfortable'],['Default login','DEFAULT_PREFS.login','anna'],['Now identyczny','relativeDate(new Date(now).toISOString(),now)','dzisiaj'],['Nie mutuje changes','(()=>{const changes={login:"piotr"};mergePrefs(DEFAULT_PREFS,changes);return Object.keys(changes).length;})()',1],['Data string','typeof fullDate("2026-10-29T12:00:00Z")','string'],['Kopia i poprzednia wartość','mergePrefs(mergePrefs(DEFAULT_PREFS,{density:"compact"}),{density:"comfortable"}).density','comfortable'],['Tylko login','mergePrefs(DEFAULT_PREFS,{login:"kasia"}).dateFormat','relative']])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik preferencji?',a,...wrong,'Scalenie tworzy nowy obiekt, zachowując niezmienione ustawienia. Daty względne używają jawnego czasu i pełnych odstępów 24 godzin.',model+'\nconst now=Date.parse("2026-10-29T12:00:00Z");console.log('+expr+');']);}
const project={'src/App.tsx':app,'src/main.tsx':'import "./style.css";import {createRoot} from "react-dom/client";import App from "./App";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<App/>);','src/style.css':'.row--compact td{padding:0.2rem}.row td{border-bottom:1px solid #ccc}'};
module.exports={model,context,consumers,app,exercises,quiz,project};
