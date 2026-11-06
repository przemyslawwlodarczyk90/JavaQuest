// Lekcja 16/03: React Compiler. Prawdziwy babel-plugin-react-compiler 1.0: wynik kompilacji (pominięcia, dyrektywy)
// i zachowanie w czasie działania (pliki *.compiled.tsx przechodzą przez kompilator w Vitest).
const config=String.raw`import {defineConfig} from "vitest/config";
import {transformAsync} from "@babel/core";
// Pliki *.compiled.tsx przechodzą przez React Compiler (jak w @vitejs/plugin-react z babel.plugins), reszta — nie.
export default defineConfig({
  plugins:[{
    name:"react-compiler-dla-wybranych",
    enforce:"pre",
    async transform(code:string,id:string){
      if(!/\.compiled\.tsx$/.test(id))return null;
      const result=await transformAsync(code,{filename:id,babelrc:false,configFile:false,
        presets:[["@babel/preset-typescript",{isTSX:true,allExtensions:true}]],
        plugins:[["babel-plugin-react-compiler",{target:"19"}]]});
      return result?.code?{code:result.code,map:null}:null;
    },
  }],
  test:{environment:"jsdom",include:["src/**/*.test.ts","src/**/*.test.tsx"],setupFiles:["./src/setup.ts"],restoreMocks:true},
});`;
const compileHelper=String.raw`import {transformSync} from "@babel/core";
export type CompilerOptions={compilationMode?:"infer"|"annotation"|"all";sources?:(filename:string)=>boolean;panicThreshold?:"none"|"critical_errors"|"all_errors"};
export type CompileResult={compiled:boolean;cacheSize:number|null;code:string};
// Kompiluje źródło komponentu jak wtyczka w Vite i mówi, czy kompilator go zoptymalizował.
export function compileComponent(source:string,options:CompilerOptions={},filename="src/Komponent.tsx"):CompileResult{
  const code=transformSync(source,{filename,babelrc:false,configFile:false,
    presets:[["@babel/preset-typescript",{isTSX:true,allExtensions:true}]],
    plugins:[["babel-plugin-react-compiler",{target:"19",...options}]]})?.code??"";
  const size=code.match(/_c\((\d+)\)/);
  return {compiled:code.includes("react/compiler-runtime"),cacheSize:size?Number(size[1]):null,code};
}`;
const format=String.raw`import type {Status,Ticket} from "./types";
// Liczniki wywołań — test sprawdza, czy kompilator zapamiętał wynik.
export const calls={format:0,stats:0};
export function resetCalls(){calls.format=0;calls.stats=0;}
export function formatTitle(title:string):string{calls.format+=1;return title.toUpperCase();}
export function countStatuses(tickets:readonly Ticket[]):Partial<Record<Status,number>>{
  calls.stats+=1;
  return tickets.reduce<Partial<Record<Status,number>>>((acc,t)=>({...acc,[t.status]:(acc[t.status]??0)+1}),{});
}`;
const types=String.raw`export type Status="OPEN"|"IN_PROGRESS"|"CLOSED";
export type Ticket={id:number;title:string;status:Status};
export function makeTickets(count:number):Ticket[]{
  const statuses:Status[]=["OPEN","IN_PROGRESS","CLOSED"];
  return Array.from({length:count},(_,i)=>({id:i+1,title:"Zgłoszenie "+(i+1),status:statuses[i%3]}));
}`;
const tableSource=String.raw`import {useState} from "react";
import {countStatuses,formatTitle} from "./format";
import type {Ticket} from "./types";
// Bez memo, useMemo i useCallback — memoizację dodaje (albo nie) kompilator.
function TicketRow({ticket,selected,onSelect}:{ticket:Ticket;selected:boolean;onSelect:(id:number)=>void}){
  return <tr className={selected?"row row--selected":"row"} onClick={()=>onSelect(ticket.id)}>
    <td>{ticket.id}</td>
    <td>{formatTitle(ticket.title)}</td>
  </tr>;
}
export function TicketTable({tickets}:{tickets:readonly Ticket[]}){
  const [selectedId,setSelectedId]=useState<number|null>(null);
  const [note,setNote]=useState("");
  const stats=countStatuses(tickets);
  return <>
    <input aria-label="Notatka" value={note} onChange={e=>setNote(e.target.value)}/>
    <p>Otwarte: {stats.OPEN??0}</p>
    <table><tbody>
      {tickets.map(t=><TicketRow key={t.id} ticket={t} selected={t.id===selectedId} onSelect={setSelectedId}/>)}
    </tbody></table>
  </>;
}`;
const samples=String.raw`// Próbki źródeł do kompilacji w testach (jak w teorii).
export const goodList="export function GoodList({tickets}){const sorted=tickets.toSorted((a,b)=>a.id-b.id);return <ul>{sorted.map(t=><li key={t.id}>{t.title}</li>)}</ul>;}";
export const refInRender="import {useRef} from \"react\";export function Counter(){const renders=useRef(0);renders.current+=1;return <p>{renders.current}</p>;}";
export const propAssign="export function Title(props){props.title=props.title.trim();return <h1>{props.title}</h1>;}";
export const optOut="export function Legacy({a}){\"use no memo\";return <p>{a}</p>;}";
export const optIn="export function Fresh({a}){\"use memo\";return <p>{a}</p>;}";
export const plain="export function Hello({name}){return <p>{name}</p>;}";
export const withUseMemo="import {useMemo} from \"react\";export function Count({list}){const n=useMemo(()=>list.length,[list]);return <p>{n}</p>;}";`;
const compileTests=String.raw`import {expect,test} from "vitest";
import {compileComponent} from "./compile";
import {goodList,optIn,optOut,plain,propAssign,refInRender,withUseMemo} from "./samples";
test("czysty komponent zostaje skompilowany",()=>{
  const result=compileComponent(goodList);
  expect(result.compiled).toBe(true);
  expect(result.code).toContain("react/compiler-runtime");
});
test("odczyt i zapis refa w renderze — kompilator pomija komponent",()=>{
  expect(compileComponent(refInRender).compiled).toBe(false);
});
test("przypisanie do props — kompilator pomija komponent",()=>{
  expect(compileComponent(propAssign).compiled).toBe(false);
});
test("panicThreshold all_errors zamienia pominięcie w błąd budowania",()=>{
  expect(()=>compileComponent(refInRender,{panicThreshold:"all_errors"})).toThrow();
});
test("dyrektywa use no memo wyłącza kompilację komponentu",()=>{
  expect(compileComponent(optOut).compiled).toBe(false);
});
test("tryb annotation kompiluje tylko komponenty z use memo",()=>{
  expect(compileComponent(plain,{compilationMode:"annotation"}).compiled).toBe(false);
  expect(compileComponent(optIn,{compilationMode:"annotation"}).compiled).toBe(true);
});
test("opcja sources ogranicza kompilację do wybranych plików",()=>{
  const onlyFeatures={sources:(file:string)=>/[\\/]features[\\/]/.test(file)};
  expect(compileComponent(plain,onlyFeatures,"src/legacy/Hello.tsx").compiled).toBe(false);
  expect(compileComponent(plain,onlyFeatures,"src/features/Hello.tsx").compiled).toBe(true);
});
test("rozmiar pamięci podręcznej: jedna wartość wejściowa i jeden wynik",()=>{
  expect(compileComponent(plain).cacheSize).toBe(2);
});
test("ręczny useMemo zostaje zastąpiony memoizacją kompilatora",()=>{
  const {code,compiled}=compileComponent(withUseMemo);
  expect(compiled).toBe(true);
  expect(code).not.toContain("useMemo(");
});`;
const runtimeTests=String.raw`import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {beforeEach,expect,test} from "vitest";
import {calls,resetCalls} from "./format";
import {TicketTable as CompiledTable} from "./TicketTable.compiled";
import {TicketTable as PlainTable} from "./TicketTable.plain";
import {makeTickets} from "./types";
const tickets=makeTickets(12);
beforeEach(()=>resetCalls());
test("z kompilatorem: kliknięcie wiersza nie formatuje tytułów ponownie",async()=>{
  const user=userEvent.setup();
  render(<CompiledTable tickets={tickets}/>);
  expect(calls.format).toBe(12);
  await user.click(screen.getByText("ZGŁOSZENIE 3"));
  expect(calls.format).toBe(12);
  expect(screen.getByText("ZGŁOSZENIE 3").closest("tr")).toHaveClass("row--selected");
});
test("bez kompilatora: kliknięcie formatuje wszystkie tytuły od nowa",async()=>{
  const user=userEvent.setup();
  render(<PlainTable tickets={tickets}/>);
  await user.click(screen.getByText("ZGŁOSZENIE 3"));
  expect(calls.format).toBe(24);
});
test("z kompilatorem: pisanie w notatce nie przelicza statystyk",async()=>{
  const user=userEvent.setup();
  render(<CompiledTable tickets={tickets}/>);
  await user.type(screen.getByLabelText("Notatka"),"abc");
  expect(calls.stats).toBe(1);
  expect(screen.getByText("Otwarte: 4")).toBeInTheDocument();
});
test("bez kompilatora: każdy znak przelicza statystyki",async()=>{
  const user=userEvent.setup();
  render(<PlainTable tickets={tickets}/>);
  await user.type(screen.getByLabelText("Notatka"),"abc");
  expect(calls.stats).toBe(4);
});
test("nowa lista ticketów przelicza statystyki także z kompilatorem",()=>{
  const view=render(<CompiledTable tickets={tickets}/>);
  view.rerender(<CompiledTable tickets={makeTickets(3)}/>);
  expect(calls.stats).toBe(2);
  expect(screen.getByText("Otwarte: 1")).toBeInTheDocument();
});`;
const header=String.raw`import {transformSync} from "@babel/core";
function compile(code:string,options:Record<string,unknown>={},filename="src/Komponent.tsx"):string{
  return transformSync(code,{filename,babelrc:false,configFile:false,presets:[["@babel/preset-typescript",{isTSX:true,allExtensions:true}]],plugins:[["babel-plugin-react-compiler",{target:"19",...options}]]})?.code??"";
}
function verdict(code:string,options:Record<string,unknown>={},filename?:string):string{return compile(code,options,filename).includes("react/compiler-runtime")?"skompilowany":"pominięty";}
void verdict;
`;
const exercises=[
  ['Włącz React Compiler w konfiguracji Vite przez wtyczkę React.','babel.plugins w @vitejs/plugin-react.',String.raw`// vite.config.ts
// import { defineConfig } from 'vite';
// import react from '@vitejs/plugin-react';
// export default defineConfig({
//   plugins: [react({ babel: { plugins: ['babel-plugin-react-compiler'] } })],
// });
// npm install -D babel-plugin-react-compiler
export {};`],
  ['Skonfiguruj Vitest tak, żeby pliki *.compiled.tsx przechodziły przez kompilator.','Wtyczka Vite z enforce: "pre" i transformAsync z Babel.',config],
  ['Napisz compileComponent zwracający, czy komponent został skompilowany, i rozmiar pamięci podręcznej.','Skompilowany kod importuje react/compiler-runtime.',compileHelper],
  ['Sprawdź testem, że czysty GoodList z toSorted jest kompilowany.','toSorted nie mutuje propsa.',compileTests],
  ['Sprawdź testem, że odczyt refa w renderze powoduje pominięcie komponentu.','Ref nie jest wartością renderu.',compileTests],
  ['Sprawdź testem, że przypisanie do props powoduje pominięcie komponentu.','props.title = … łamie niemutowalność.',compileTests],
  ['Zamień pominięcie w błąd budowania opcją panicThreshold.','all_errors — przydatne w CI.',compileTests],
  ['Wyłącz kompilację jednego komponentu dyrektywą „use no memo”.','Dyrektywa na początku ciała funkcji.',String.raw`export function LegacyChart({points}:{points:number[]}){
  "use no memo"; // tymczasowo: biblioteka wykresów mutuje przekazaną tablicę
  return <svg>{points.map((y,i)=><circle key={i} cx={i*10} cy={y} r={2}/>)}</svg>;
}`],
  ['Włącz kompilator stopniowo w trybie annotation i dyrektywie „use memo”.','compilationMode: "annotation".',compileTests],
  ['Ogranicz kompilację do katalogu features opcją sources.','sources: (filename) => boolean.',compileTests],
  ['Usuń ręczną memoizację z tabeli ticketów i zostaw kompilatorowi.','Bez memo, useMemo i useCallback.',tableSource],
  ['Udowodnij testem, że z kompilatorem kliknięcie nie formatuje tytułów ponownie.','Licznik wywołań formatTitle.',runtimeTests],
  ['Porównaj z tym samym kodem bez kompilatora.','Ten sam plik źródłowy w dwóch wersjach.',runtimeTests],
  ['Udowodnij testem, że pisanie w notatce nie przelicza statystyk z kompilatorem.','countStatuses zależy tylko od tickets.',runtimeTests],
  ['Sprawdź, że nowa lista przelicza statystyki także z kompilatorem.','Memoizacja według zależności, nie „na zawsze”.',runtimeTests],
  ['Popraw BadList z teorii, żeby był czysty.','toSorted zamiast sort, bez refa w renderze.',String.raw`type Ticket={id:number;title:string};
export function GoodList({tickets}:{tickets:readonly Ticket[]}){
  const sorted=tickets.toSorted((a,b)=>a.id-b.id); // nowa tablica, props nietknięte
  return <ul>{sorted.map(t=><li key={t.id}>{t.title}</li>)}</ul>;
}`],
  ['Przenieś licznik renderów z refa w renderze do efektu.','Zapis do refa w efekcie jest dozwolony.',String.raw`import {useEffect,useRef} from "react";
export function RenderCounter({onCount}:{onCount:(n:number)=>void}){
  const renders=useRef(0);
  useEffect(()=>{renders.current+=1;onCount(renders.current);}); // poza renderem
  return <p>Licznik w konsoli</p>;
}`],
  ['Zastąp zmienną modułu modyfikowaną w renderze stanem lub refem w efekcie.','Globalna mutacja w renderze → pominięcie.',String.raw`import {useState} from "react";
// ŹLE: let clicks = 0; function C() { clicks++; ... } — kompilator pomija taki komponent.
export function ClickCounter(){
  const [clicks,setClicks]=useState(0);
  return <button type="button" onClick={()=>setClicks(c=>c+1)}>Kliknięcia: {clicks}</button>;
}`],
  ['Zamień przypisanie do props na zmienną lokalną.','Props są tylko do odczytu.',String.raw`export function Title({title}:{title:string}){
  const clean=title.trim(); // zamiast props.title = props.title.trim()
  return <h1>{clean}</h1>;
}`],
  ['Skonfiguruj ESLint z regułami kompilatora.','eslint-plugin-react-hooks w wersji z regułami kompilatora.',String.raw`// eslint.config.js
// import reactHooks from "eslint-plugin-react-hooks";
// export default [reactHooks.configs.flat.recommended];
// Zalecany zestaw zgłasza m.in. odczyt refów w renderze, mutacje i warunkowe hooki —
// czyli to, przez co kompilator pomija komponent.
export {};`],
  ['Dodaj skrypt CI, który przerywa budowanie, gdy kompilator pominie komponent.','panicThreshold: "all_errors" tylko w CI.',String.raw`// vite.config.ts (fragment)
// const ci = process.env.CI === "true";
// react({ babel: { plugins: [["babel-plugin-react-compiler", { panicThreshold: ci ? "all_errors" : "none" }]] } })
export {};`],
  ['Zostaw ręczne useMemo tam, gdzie dokumentuje kosztowne obliczenie lub zależność efektu.','Intencja w kodzie.',String.raw`import {useEffect,useMemo} from "react";
export function SyncFilter({status,subscribe}:{status:string;subscribe:(filter:{status:string})=>()=>void}){
  // Jawny useMemo dokumentuje: efekt ma się restartować tylko po zmianie statusu.
  const filter=useMemo(()=>({status}),[status]);
  useEffect(()=>subscribe(filter),[filter,subscribe]);
  return <p>Filtr: {status}</p>;
}`],
  ['Napisz hook wywołujący useState, który kompilator zoptymalizuje.','Nazwa zaczyna się od use i hook wywołuje hooki.',String.raw`import {useState} from "react";
export function useToggle(initial=false){
  const [on,setOn]=useState(initial);
  return [on,()=>setOn(o=>!o)] as const; // kompilator zapamięta funkcję przełączania
}`],
  ['Pokaż, że kompilator usuwa typy — TSX optymalizowany jak JSX.','Wynik nie zawiera adnotacji typów.',String.raw`import {compileComponent} from "./compile";
const tsx="export function Hello({name}:{name:string}){return <p>{name}</p>;}";
const jsx="export function Hello({name}){return <p>{name}</p>;}";
export const sameCache=compileComponent(tsx).cacheSize===compileComponent(jsx).cacheSize; // true`],
  ['Odczytaj w wyniku kompilacji, jak działa pamięć podręczna $.','if ($[0] !== name) { … } else { t1 = $[1]; }',String.raw`// import { c as _c } from "react/compiler-runtime";
// export function Hello(t0) {
//   const $ = _c(2);                // dwa sloty: wejście name i wynik JSX
//   const { name } = t0;
//   let t1;
//   if ($[0] !== name) { t1 = <p>{name}</p>; $[0] = name; $[1] = t1; }
//   else { t1 = $[1]; }             // to samo name → ten sam element → React pomija render poddrzewa
//   return t1;
// }
export {};`],
  ['Wyjaśnij, dlaczego memo na wierszu staje się zbędne z kompilatorem.','Stabilne props i zapamiętany JSX.',String.raw`// Kompilator zapamiętuje element <TicketRow …/> i jego props w rodzicu oraz wyniki obliczeń w wierszu.
// Dziecko dostaje te same referencje (onSelect = setter, ticket z tej samej tablicy),
// a ciężkie wywołania (formatTitle) są pomijane przy tych samych argumentach.
export {};`],
  ['Zapisz decyzję o sposobie włączenia kompilatora w README.','Globalnie dla nowych projektów, stopniowo dla istniejących.',String.raw`// README — React Compiler
// Decyzja: włączony globalnie (projekt nowy, lint bez naruszeń).
// Wyjątki: "use no memo" w LegacyChart (biblioteka mutuje dane) — do usunięcia po aktualizacji.
// CI: panicThreshold "all_errors" — pominięty komponent przerywa budowanie.
export {};`],
  ['Pokaż komponent, który kompilator pominie przez warunkowy hook, i popraw go.','Hook przed warunkiem.',String.raw`import {useState} from "react";
// ŹLE: if (editable) { const [draft, setDraft] = useState(""); } — kompilator pominie komponent.
export function Note({editable}:{editable:boolean}){
  const [draft,setDraft]=useState("");
  if(!editable)return <p>Tylko odczyt</p>;
  return <textarea aria-label="Notatka" value={draft} onChange={e=>setDraft(e.target.value)}/>;
}`],
  ['Zmierz Profilerem tabelę z kompilatorem i porównaj z ręczną memoizacją.','Ten sam scenariusz: kliknięcie wiersza w 2000 ticketach.',String.raw`// 1. Gałąź z kompilatorem: Profiler → kliknięcie wiersza → czas commitu, „Memo ✨” przy komponentach.
// 2. Gałąź z ręcznym memo/useCallback: ten sam scenariusz.
// 3. Porównaj czasy; oczekiwanie: porównywalne. Zapisz wynik w README.
export {};`],
  ['Wyjaśnij, dlaczego kompilator nie jest wymówką dla brudnego kodu.','Pomija, a nie naprawia.',String.raw`// Kompilator nie poprawia kodu łamiącego zasady Reacta — po cichu go pomija.
// Bez lintera część aplikacji jest zoptymalizowana, a część nie, i trudno ustalić dlaczego.
// Czystość, niemutowalność i zasady hooków są teraz także warunkiem wydajności.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Czym jest React Compiler?','Wtyczką budowania, która automatycznie memoizuje komponenty i hooki','Nowym silnikiem renderowania w przeglądarce','Zamiennikiem TypeScriptu','Biblioteką stanu','Działa jako wtyczka Babel.');
q('Co zapamiętuje kompilator?','Wyniki obliczeń, JSX i funkcje według wartości, od których zależą','Tylko komponenty w memo','Tylko stan','Wyniki zapytań sieciowych','Automatyczny odpowiednik useMemo i useCallback.');
q('Na jakim poziomie kompilator memoizuje?','Pojedynczych wyrażeń — dokładniej niż ręczny kod','Tylko całych komponentów','Tylko plików','Tylko modułów','Także tam, gdzie ręcznie byłoby niewygodnie.');
q('Jaki jest warunek optymalizacji komponentu przez kompilator?','Przestrzeganie zasad Reacta: czystość, niemutowalność, zasady hooków','Użycie TSX','Użycie klas','Brak stanu','Kod łamiący zasady jest pomijany.');
q('Co kompilator robi z komponentem łamiącym zasady?','Pomija go — zostawia bez optymalizacji','Naprawia go','Usuwa go z bundla','Zamienia na klasę','Bezpiecznie, bez zmiany działania.');
q('Jak zainstalować kompilator?','npm install -D babel-plugin-react-compiler','npm install react-compiler-runtime -g','Jest wbudowany w react-dom','npx react compile','Potem konfiguracja wtyczki React.');
q('Gdzie dodać kompilator w projekcie Vite?','W babel.plugins wtyczki @vitejs/plugin-react','W tsconfig.json','W package.json "react"','W index.html','react({ babel: { plugins: ["babel-plugin-react-compiler"] } }).');
q('Jak DevTools oznacza komponenty zoptymalizowane przez kompilator?','„Memo ✨”','„Compiled”','Gwiazdką w Profilerze','Nie oznacza','Widoczne w drzewie komponentów.');
q('Co zgłasza ESLint z regułami kompilatora?','Kod, którego kompilator nie może bezpiecznie zoptymalizować','Brak średników','Brak typów','Za długie pliki','Np. odczyt refów w renderze.');
q('Jak włączyć kompilator stopniowo dla wybranych katalogów?','Opcją sources','Opcją jsx','Flagą --partial','Nie da się','sources: (filename) => boolean.');
q('Jak włączyć kompilator tylko dla wybranych komponentów?','compilationMode: "annotation" i dyrektywa "use memo"','"use client"','"use strict"','memo(Komponent)','Tryb opt-in.');
q('Do czego służy dyrektywa "use no memo"?','Wyłącza kompilację jednego komponentu lub hooka','Wyłącza memo w całej aplikacji','Wyłącza StrictMode','Wyłącza render','Tymczasowe obejście problemu.');
q('Co robi panicThreshold: "all_errors"?','Zamienia pominięcie komponentu w błąd budowania','Wyłącza kompilator','Ignoruje błędy','Tylko ostrzega w konsoli','Przydatne w CI.');
q('Jaka jest domyślna reakcja kompilatora na problem w komponencie?','Pominięcie komponentu bez przerywania budowania','Przerwanie budowania','Usunięcie komponentu','Błąd w czasie działania','panicThreshold domyślnie "none".');
q('Z jakiego modułu importuje skompilowany kod?','react/compiler-runtime','react-dom/compiler','babel-runtime','react/memo','Funkcja c tworzy pamięć podręczną komponentu.');
q('Czym jest $ = _c(2) w skompilowanym kodzie?','Pamięcią podręczną komponentu na 2 sloty','Liczbą renderów','Licznikiem efektów','Identyfikatorem komponentu','Sloty na wejścia i wyniki.');
q('Ile slotów pamięci ma skompilowany Hello({ name }) zwracający <p>{name}</p>?','2','1','4','0','Wejście name i wynik JSX.');
q('Co robi if ($[0] !== name) w skompilowanym kodzie?','Sprawdza, czy wejście się zmieniło; jeśli nie, zwraca zapamiętany wynik','Rzuca błąd','Planuje render','Czyści stan','Porównanie referencyjne.');
q('Dlaczego zwrócenie tego samego elementu pomaga Reactowi?','Ten sam element pozwala pominąć render poddrzewa','Bo usuwa DOM','Bo wyłącza efekty','Nie pomaga','Bailout przy identycznych elementach.');
q('Czy kompilator korzysta z typów TypeScript?','Nie — analizuje przepływ wartości po usunięciu typów','Tak, wymaga TSX','Tylko z readonly','Tylko z generykami','JSX i TSX są optymalizowane tak samo.');
q('Jak typy pomagają kompilatorowi pośrednio?','readonly i brak mutacji łatwiej spełniają zasady Reacta','Przyspieszają kompilację','Są wymagane','Nie pomagają','Mniej pominiętych komponentów.');
q('Co kompilator 1.0 zrobi z odczytem i zapisem refa w renderze?','Pominie komponent','Skompiluje go','Rzuci błąd w czasie działania','Usunie ref','Ref nie jest wartością renderu.');
q('Co kompilator zrobi z przypisaniem props.title = … w renderze?','Pominie komponent','Skompiluje go','Skopiuje props','Rzuci błąd w przeglądarce','Props są tylko do odczytu.');
q('Czy kompilator wykrywa każdą mutację w renderze?','Nie — np. tickets.sort na rozpakowanym propsie zostaje skompilowane, dlatego potrzebny jest linter i dyscyplina','Tak, każdą','Tylko w TSX','Tylko w trybie annotation','Kompilator zakłada przestrzeganie zasad.');
q('Co kompilator robi z ręcznym useMemo?','Zastępuje je własną memoizacją (wywołanie useMemo znika z wyniku)','Zostawia bez zmian i dodaje drugą warstwę','Rzuca błąd','Pomija komponent','Istniejącego useMemo nie musisz usuwać.');
q('Czy trzeba usuwać istniejące memo i useMemo po włączeniu kompilatora?','Nie, kompilator działa razem z nimi','Tak, inaczej nie zadziała','Tak, bo spowalniają','Tylko useCallback','Nowego kodu nie trzeba nimi obkładać.');
q('Czy kompilator kompiluje zwykłą funkcję z małej litery zwracającą JSX?','Nie — w trybie infer rozpoznaje komponenty po nazwie z wielkiej litery','Tak, zawsze','Tylko z "use memo"','Tylko w TSX','helper() to nie komponent.');
q('Czy kompilator kompiluje hook useToggle wywołujący useState?','Tak','Nie','Tylko w trybie annotation','Tylko z memo','Hooki też są optymalizowane.');
q('Czy kompilator kompiluje funkcję useX, która nie wywołuje żadnego hooka?','Nie — w trybie infer nie uznaje jej za hook do optymalizacji','Tak','Tylko z JSX','Tylko w TSX','Wymaga sygnałów: nazwa i użycie hooków lub JSX.');
q('Czy kompilator optymalizuje komponenty klasowe?','Nie','Tak','Tylko PureComponent','Tylko w React 19','Działa na komponentach funkcyjnych i hookach.');
q('Co kompilator zrobi z komponentem modyfikującym zmienną modułu w renderze?','Pominie go','Skompiluje','Zamieni zmienną na stan','Rzuci błąd w przeglądarce','To efekt uboczny w renderze.');
q('Co kompilator zrobi z warunkowym hookiem?','Pominie komponent','Skompiluje','Przeniesie hook przed warunek','Doda key','Zasady hooków są warunkiem.');
q('Czy komponent z try/catch w renderze może zostać skompilowany?','Tak (kompilator 1.0)','Nie, nigdy','Tylko w TSX','Tylko z "use memo"','Wynik potwierdzony w próbce.');
q('Czy komponent zapisujący do refa w efekcie zostanie skompilowany?','Tak — efekt to dozwolone miejsce na mutacje','Nie','Tylko w annotation','Tylko bez zależności','Problemem jest ref w renderze.');
q('Co da kompilator tabeli, której wiersz wywołuje formatTitle(ticket.title)?','Przy tym samym tytule formatTitle nie zostanie wywołane ponownie','Nic','Wywoła formatTitle dwa razy','Usunie wywołanie','Wynik zapamiętany według argumentu.');
q('Dlaczego onSelect={setSelectedId} nie wymaga useCallback?','Setter jest stabilny','Bo kompilator go kopiuje','Bo jest async','Wymaga zawsze','Kompilator i tak zachowuje stabilne referencje.');
q('Czy kompilator przeliczy statystyki po wpisaniu znaku w niezwiązanym polu?','Nie, jeśli zależą tylko od tickets','Tak, zawsze','Tylko w produkcji','Tylko bez TSX','Zależność to lista ticketów.');
q('Czy kompilator przeliczy statystyki po otrzymaniu nowej tablicy ticketów?','Tak — zmieniła się zależność','Nie, nigdy','Tylko z useMemo','Tylko w dev','Memoizacja według zależności.');
q('Kiedy ręczne useMemo nadal ma sens z kompilatorem?','Gdy dokumentuje kosztowne obliczenie lub kontroluje zależność efektu','Zawsze','Nigdy','Tylko w klasach','Jawna intencja w kodzie.');
q('Co z kodem, którego kompilator nie optymalizuje?','Poprawić go albo zastosować ręczną memoizację','Zignorować','Przepisać na klasy','Wyłączyć React','Najlepiej poprawić zasady.');
q('Czy kompilator działa w projektach bez Babel?','Potrzebuje integracji z narzędziem budowania (Babel lub wspierane wtyczki)','Tak, działa w przeglądarce','Tylko w Node','Nie działa nigdzie','W Vite przez wtyczkę React z Babel.');
q('Co oznacza target: "19" w opcjach kompilatora?','Wersję Reacta, dla której generowany jest kod','Wersję ECMAScript','Wersję Node','Wersję TypeScript','Dla 17/18 potrzebny pakiet react-compiler-runtime.');
q('Jak sprawdzić, czy komponent został skompilowany, bez DevTools?','Szukać importu react/compiler-runtime w wyniku kompilacji','Liczyć linie kodu','Sprawdzić rozmiar pliku','Nie da się','Tak działa compileComponent z lekcji.');
q('Co jest typową przyczyną pominięcia komponentu w istniejącym kodzie?','Mutacje w renderze, odczyt refów w renderze, warunkowe hooki','Za dużo komponentów','Użycie TSX','Użycie kontekstu','Linter wskaże miejsca.');
q('Co zrobić najpierw po włączeniu kompilatora w istniejącym projekcie?','Uruchomić lint i poprawić zgłoszone naruszenia zasad','Usunąć wszystkie testy','Usunąć memo bez sprawdzania','Wyłączyć StrictMode','Potem usuwać ręczną memoizację.');
q('Czy kompilator zmienia zachowanie poprawnego kodu?','Nie — zmienia tylko to, kiedy wartości są liczone ponownie','Tak, zmienia logikę','Tak, usuwa efekty','Tak, zmienia kolejność hooków','Poprawny kod działa identycznie.');
q('Co może się zmienić w zachowaniu kodu łamiącego zasady po kompilacji?','Np. nieaktualne wartości, gdy mutacja nie została wykryta','Nic, nigdy','Szybszy render zawsze','Zmiana nazw','Stąd linter i panicThreshold w CI.');
q('Czy kompilator zastępuje pomiar wydajności?','Nie — dalej mierzysz Profilerem','Tak','Tylko w produkcji','Tylko dla list','Np. wirtualizacja to osobny problem.');
q('Czy kompilator rozwiązuje problem renderowania 10 000 węzłów DOM?','Nie — potrzebna wirtualizacja lub paginacja','Tak','Tylko z memo','Tylko w TSX','Lekcja 7.');
q('Czy kompilator przyspiesza ciężkie obliczenie przy zmienionych danych?','Nie — przy zmianie zależności i tak liczy ponownie','Tak','Tylko z useMemo','Tylko w workerze','Pomaga tylko przy powtórzeniach.');
q('Co zrobić z komponentem korzystającym z biblioteki mutującej obiekty?','Rozważyć "use no memo" lub poprawić integrację','Usunąć bibliotekę zawsze','Owinąć w klasę','Nic','Kompilator zakłada niemutowalność.');
q('Czy "use no memo" powinno zostać na zawsze?','Nie — to tymczasowe obejście z komentarzem dlaczego','Tak','Tylko w testach','Tylko w TSX','Usuń po poprawie kodu.');
q('Gdzie umieścić dyrektywę "use memo"?','Na początku ciała funkcji komponentu lub hooka','Na końcu pliku','W package.json','W JSX','Jak "use strict".');
q('Co oznacza compilationMode: "infer"?','Kompilator sam rozpoznaje komponenty i hooki po nazwach i użyciu','Kompiluje wszystko','Tylko z dyrektywą','Nic nie kompiluje','Tryb domyślny.');
q('Co oznacza compilationMode: "all"?','Kompiluje wszystkie funkcje najwyższego poziomu','Tylko komponenty','Tylko hooki','Nic','Rzadko używany.');
q('Jak kompilator obsługuje funkcję strzałkową przypisaną do const Badge = ({label}) => …?','Kompiluje ją jak komponent (nazwa z wielkiej litery)','Pomija zawsze','Wymaga memo','Wymaga function','Liczy się nazwa i JSX.');
q('Ile slotów pamięci ma komponent zwracający stały JSX <h1>x</h1>?','1','0','2','3','Stały element tworzony raz.');
q('Co robi kompilator ze stałym JSX bez zależności?','Tworzy go raz i zwraca ten sam element przy kolejnych renderach','Tworzy przy każdym renderze','Usuwa go','Zamienia na string','Odpowiednik stałej modułu.');
q('Czy kompilator działa z StrictMode?','Tak — StrictMode dalej ujawnia problemy w dev','Nie','Tylko w produkcji','Wyłącza StrictMode','Niezależne mechanizmy.');
q('Dlaczego zasady czystości są teraz także warunkiem wydajności?','Kod nieczysty jest pomijany przez kompilator','Bo React wolniej renderuje nieczyste komponenty','Bo TS tego wymaga','Nie są','Pominięty komponent nie dostaje memoizacji.');
q('Jak nazywa się pakiet runtime kompilatora dla React 17 i 18?','react-compiler-runtime','react-dom/compiler','babel-runtime','react-memo','React 19 ma runtime wbudowany.');
q('Czy kompilator zapamiętuje funkcje strzałkowe w JSX, np. onClick={() => onSelect(id)}?','Tak, według wartości, które funkcja czyta','Nie','Tylko z useCallback','Tylko w TSX','Dziecko dostaje stabilny callback.');
q('Co jest kryterium akceptacji tej lekcji?','Lint bez naruszeń, tabela bez ręcznej memoizacji z porównywalnym czasem, decyzja w README','Usunięcie Profilera','Kompilacja wszystkich plików w trybie all','Przejście na klasy','Potwierdzone pomiarem.');
q('Dlaczego test lekcji porównuje ten sam plik z kompilatorem i bez?','Żeby różnica wynikała wyłącznie z kompilatora','Bo kompilator wymaga dwóch plików','Dla szybkości','Bez powodu','Kontrolowany eksperyment.');
q('Czy kompilator zmienia liczbę wywołań funkcji komponentu dziecka?','Dziecko może się nadal wykonać, ale jego zapamiętane fragmenty i obliczenia są pomijane; z zapamiętanym elementem rodzic pomija render dziecka','Zawsze zmniejsza do zera','Zawsze zwiększa','Nie wpływa na nic','Zależy od stabilności elementów i props.');
q('Jakie ustawienie panicThreshold warto mieć lokalnie podczas pracy?','"none" — pominięcie nie blokuje pracy, a linter informuje','"all_errors" zawsze','Wyłączony kompilator','"critical_errors" w produkcji obowiązkowo','W CI można zaostrzyć.');
q('Co pokazuje porównanie formatTitle z kompilatorem i bez po kliknięciu wiersza (12 wierszy)?','Z kompilatorem 0 dodatkowych wywołań, bez — 12','Oba po 12','Oba po 0','Z kompilatorem 24','Wynik z testu lekcji.');
q('Co pokazuje porównanie countStatuses przy wpisaniu 3 znaków w notatce?','Z kompilatorem 1 wywołanie łącznie, bez — 4','Oba po 4','Oba po 1','Z kompilatorem 3','Montowanie + 3 znaki bez kompilatora.');
q('Czy kompilator wpływa na rozmiar bundla?','Nieznacznie zwiększa kod komponentów o logikę pamięci podręcznej','Zmniejsza dwukrotnie','Nie zmienia ani bajta','Usuwa React','Zysk w czasie działania.');
q('Jak kompilator traktuje props i stan?','Jako niemutowalne — mutacja łamie jego założenia','Jako mutowalne','Kopiuje je głęboko','Zamraża Object.freeze','Stąd toSorted zamiast sort.');
q('Dlaczego toSorted zamiast sort w GoodList?','toSorted zwraca nową tablicę i nie mutuje propsa','Bo sort nie istnieje','Bo toSorted jest szybsze','Bez różnicy','ES2023.');
q('Czy można używać kompilatora razem z ręcznym memo na wybranych komponentach?','Tak','Nie','Tylko w annotation','Tylko w TSX','Współpracują.');
q('Co ułatwia kompilator w nowym kodzie?','Brak decyzji „memoizować czy nie” przy każdym komponencie','Brak testów','Brak typów','Brak hooków','Kod wraca do najprostszej postaci.');
q('Co zrobić, gdy jeden ekran jest wolny mimo kompilatora?','Zmierzyć Profilerem i sprawdzić, czy jego komponenty nie zostały pominięte','Dodać memo wszędzie','Wyłączyć kompilator','Przepisać na klasy','Brak „Memo ✨” to wskazówka.');
q('Czy kompilator memoizuje komponenty z setState wywołanym warunkowo w renderze?','Tak (kompilator 1.0 skompilował taki przykład)','Nie, nigdy','Tylko z useMemo','Tylko w annotation','To wciąż wzorzec wymagający ostrożności.');
q('Co jest wynikiem uruchomienia kompilatora na wersji TSX i JSX tego samego komponentu?','Ten sam rozmiar pamięci podręcznej i ta sama logika','TSX ma więcej slotów','JSX nie jest kompilowany','TSX nie jest kompilowany','Typy są usuwane przed analizą.');
const probes=[
  ['Czysty GoodList z toSorted','skompilowany','pominięty','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function GoodList({tickets}){const sorted=tickets.toSorted((a,b)=>a.id-b.id);return <ul>{sorted.map(t=><li key={t.id}>{t.title}</li>)}</ul>;}'));`],
  ['Odczyt i zapis refa w renderze','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('import {useRef} from "react";export function Counter(){const renders=useRef(0);renders.current+=1;return <p>{renders.current}</p>;}'));`],
  ['Przypisanie do props','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function Title(props){props.title="x";return <h1>{props.title}</h1>;}'));`],
  ['Dyrektywa use no memo','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function Legacy({a}){"use no memo";return <p>{a}</p>;}'));`],
  ['Funkcja z małej litery zwracająca JSX','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function helper({a}){return <p>{a}</p>;}'));`],
  ['Hook wywołujący useState','skompilowany','pominięty','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('import {useState} from "react";export function useToggle(){const [on,setOn]=useState(false);return [on,()=>setOn(o=>!o)];}'));`],
  ['Funkcja use… bez hooków i JSX','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function useOpen(list){return list.filter(t=>t.open);}'));`],
  ['Warunkowy hook','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('import {useState} from "react";export function Note({on}){if(on){useState(0);}return <p/>;}'));`],
  ['Komponent klasowy','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('import {Component} from "react";export class Panel extends Component{render(){return <p/>;}}'));`],
  ['Mutacja zmiennej modułu w renderze','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('let count=0;export function Clicks(){count++;return <p>{count}</p>;}'));`],
  ['Tryb annotation bez dyrektywy','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function Hello({name}){return <p>{name}</p>;}',{compilationMode:"annotation"}));`],
  ['Tryb annotation z use memo','skompilowany','pominięty','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function Hello({name}){"use memo";return <p>{name}</p>;}',{compilationMode:"annotation"}));`],
  ['Komponent jako funkcja strzałkowa','skompilowany','pominięty','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export const Badge=({label})=>{return <b>{label}</b>;};'));`],
  ['tickets.sort na rozpakowanym propsie','skompilowany','pominięty','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function BadList({tickets}){tickets.sort((a,b)=>a.id-b.id);return <ul>{tickets.map(t=><li key={t.id}>{t.title}</li>)}</ul>;}'));`],
  ['Ref zmieniany w efekcie','skompilowany','pominięty','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('import {useEffect,useRef} from "react";export function Tracker(){const r=useRef(0);useEffect(()=>{r.current+=1;});return <p/>;}'));`],
  ['Rozmiar pamięci Hello({ name })','2','1','3','0',String.raw`console.log(compile('export function Hello({name}){return <p>{name}</p>;}').match(/_c\((\d+)\)/)?.[1]);`],
  ['Rozmiar pamięci dla stałego JSX','1','0','2','3',String.raw`console.log(compile('export function Title(){return <h1>DevDesk</h1>;}').match(/_c\((\d+)\)/)?.[1]);`],
  ['Czy w wyniku zostało wywołanie useMemo(','false','true','undefined','błąd',String.raw`console.log(String(compile('import {useMemo} from "react";export function Count({list}){const n=useMemo(()=>list.length,[list]);return <p>{n}</p>;}').includes("useMemo(")));`],
  ['Czy wynik TSX zawiera adnotację typu','false','true','undefined','błąd',String.raw`console.log(String(compile('export function Hello({name}:{name:string}){return <p>{name}</p>;}').includes(":string")));`],
  ['Opcja sources wyklucza plik legacy','pominięty','skompilowany','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function Hello({name}){return <p>{name}</p>;}',{sources:(file:string)=>/[\\/]features[\\/]/.test(file)},"src/legacy/Hello.tsx"));`],
  ['Opcja sources obejmuje plik features','skompilowany','pominięty','błąd kompilacji','pusty wynik',String.raw`console.log(verdict('export function Hello({name}){return <p>{name}</p>;}',{sources:(file:string)=>/[\\/]features[\\/]/.test(file)},"src/features/Hello.tsx"));`],
  ['panicThreshold all_errors przy refie w renderze','błąd kompilacji','pominięty','skompilowany','pusty wynik',String.raw`try{console.log(verdict('import {useRef} from "react";export function Counter(){const r=useRef(0);r.current+=1;return <p>{r.current}</p>;}',{panicThreshold:"all_errors"}));}catch{console.log("błąd kompilacji");}`],
  ['Nazwa funkcji po kompilacji','true','false','undefined','błąd',String.raw`console.log(String(compile('export function Hello({name}){return <p>{name}</p>;}').includes("function Hello(")));`],
  ['Moduł runtime w imporcie','react/compiler-runtime','react-dom/compiler','react/memo','brak',String.raw`console.log(compile('export function Hello({name}){return <p>{name}</p>;}').match(/from "([^"]+)"/)?.[1]??"brak");`],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia babel-plugin-react-compiler 1.0 (target 19) w Vitest.',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'vitest.config.ts':config,'src/types.ts':types,'src/format.ts':format,'src/compile.ts':compileHelper,'src/samples.ts':samples,
  'src/TicketTable.compiled.tsx':tableSource,'src/TicketTable.plain.tsx':tableSource,'src/compile.test.ts':compileTests,'src/runtime.test.tsx':runtimeTests};
const mutants=[
  {file:'vitest.config.ts',before:'/\\.compiled\\.tsx$/',after:'/\\.nigdy$/'},
  {file:'src/compile.ts',before:'compiled:code.includes("react/compiler-runtime")',after:'compiled:true'},
  {file:'src/compile.ts',before:'{target:"19",...options}',after:'{target:"19"}'},
  {file:'src/compile.ts',before:'const size=code.match(/_c\\((\\d+)\\)/);',after:'const size=null as RegExpMatchArray|null;'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
