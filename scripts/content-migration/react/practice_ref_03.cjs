// API: https://react.dev/reference/react/useImperativeHandle
const model=`export type EditorHandle={focus:()=>void;insertText:(text:string)=>void;clear:()=>void};export function insertAt(value:string,text:string,start:number,end:number){const left=Math.max(0,Math.min(value.length,Math.trunc(start)));const right=Math.max(left,Math.min(value.length,Math.trunc(end)));return {value:value.slice(0,left)+text+value.slice(right),caret:left+text.length};}`;
const editor=`import {useId,useImperativeHandle,useLayoutEffect,useRef} from "react";import type {Ref} from "react";
${model}
export function RichEditor({value,onChange,readOnly=false,ref}:{value:string;onChange:(value:string)=>void;readOnly?:boolean;ref?:Ref<EditorHandle>}){const area=useRef<HTMLTextAreaElement>(null);const pending=useRef<{value:string;caret:number}|null>(null);const id=useId();useImperativeHandle(ref,()=>({focus(){area.current?.focus();},insertText(text){const node=area.current;if(!node||readOnly)return;const next=insertAt(value,text,node.selectionStart,node.selectionEnd);pending.current=next;onChange(next.value);},clear(){if(readOnly)return;pending.current=null;onChange("");}}),[value,onChange,readOnly]);useLayoutEffect(()=>{const selection=pending.current;if(!selection)return;pending.current=null;if(selection.value!==value)return;area.current?.focus();area.current?.setSelectionRange(selection.caret,selection.caret);});return <div><label htmlFor={id}>Opis ticketu</label><textarea id={id} ref={area} value={value} readOnly={readOnly} onChange={event=>onChange(event.currentTarget.value)} rows={6}/></div>;}`;
const field=`import {useState} from "react";
${editor}
const SNIPPETS=["Kroki do odtworzenia: ","Oczekiwany rezultat: ","Aktualny rezultat: "];
export function DescriptionField(){const [text,setText]=useState("");const editor=useRef<EditorHandle>(null);const [readOnly,setReadOnly]=useState(false);return <section><h2>Opis</h2>{SNIPPETS.map(snippet=><button type="button" key={snippet} disabled={readOnly} onClick={()=>editor.current?.insertText(snippet)}>Dodaj {snippet}</button>)}<button type="button" onClick={()=>editor.current?.focus()}>Fokus opisu</button><button type="button" disabled={readOnly} onClick={()=>editor.current?.clear()}>Wyczyść opis</button><label><input type="checkbox" checked={readOnly} onChange={event=>setReadOnly(event.currentTarget.checked)}/>Tylko do odczytu</label><RichEditor ref={editor} value={text} onChange={setText} readOnly={readOnly}/><p>Znaków: {text.length}</p></section>;}`;
const shake=`import {useEffect,useImperativeHandle,useRef} from "react";import type {ReactNode,Ref} from "react";export type AttentionHandle={attention:()=>void};export function AttentionBox({children,ref}:{children:ReactNode;ref?:Ref<AttentionHandle>}){const node=useRef<HTMLDivElement>(null);const animation=useRef<Animation|null>(null);useEffect(()=>()=>{animation.current?.cancel();animation.current=null;},[]);useImperativeHandle(ref,()=>({attention(){animation.current?.cancel();animation.current=null;if(!node.current||window.matchMedia("(prefers-reduced-motion: reduce)").matches)return;const current=node.current.animate([{transform:"translateX(0)"},{transform:"translateX(-4px)"},{transform:"translateX(4px)"},{transform:"translateX(0)"}],{duration:250});animation.current=current;void current.finished.catch(()=>{}).then(()=>{if(animation.current===current)animation.current=null;});}}),[]);return <div ref={node}>{children}</div>;}`;
const exercises=[
['Zdefiniuj publiczny EditorHandle z trzema metodami.','Nie eksportuj elementu textarea jako całego API.',model],
['Przyjmij ref?:Ref<EditorHandle> jako prop komponentu React 19.','ForwardRef nie jest wymagane w tym przykładzie.',editor],
['Trzymaj prywatny ref do HTMLTextAreaElement.','Rodzic widzi uchwyt, komponent widzi node.',editor],
['Zbuduj focus jako null-safe metodę uchwytu.','Brak node nie powinien powodować wyjątku.',editor],
['Napisz insertAt jako czystą funkcję operacji na stringu.','Logikę tekstu można testować bez DOM.',model],
['Zastąp zaznaczony fragment przez wstawiany tekst.','SelectionStart i selectionEnd określają zakres.',model],
['Obsłuż zwinięte zaznaczenie jako wstawienie.','Start=end niczego nie usuwa.',model],
['Zwróć caret za nowym fragmentem.','Offset to start+text.length.',model],
['Aktualizuj tekst przez onChange, nie przez node.value kontrolowanego pola.','Value pozostaje w źródle rodzica.',editor],
['Zapamiętaj oczekiwany tekst i caret w refie pending.','Odtwarzanie kursora musi poczekać na commit wartości.',editor],
['Odtwórz selectionRange w useLayoutEffect po zatwierdzeniu tekstu.','Nie obiecuj gwarancji commitu przez pojedynczy requestAnimationFrame.',editor],
['Nie ustawiaj kursora, jeżeli rodzic zatwierdził inny tekst niż oczekiwany.','Pending nie może udawać, że transformacja rodzica nie istnieje.',editor],
['Czyść pending po próbie zastosowania.','Kolejny render nie powinien ponawiać starego przesunięcia.',editor],
['Dodaj value, onChange i readOnly do deps uchwytu.','Metody nie mają zamykać starych props.',editor],
['Zaimplementuj clear przez onChange pustego stringa.','Nie usuwaj textarea z DOM.',editor],
['Użyj readOnly jako props, nie imperatywnej metody setReadOnly.','To deklaratywny stan komponentu.',editor],
['Zablokuj insertText i clear w trybie readOnly.','Sam disabled przycisku rodzica nie zabezpiecza API uchwytu.',editor],
['Zbuduj DescriptionField z przyciskami szablonów.','Rodzic nie zna selectionStart ani prywatnego refa.',field],
['Dodaj dostępne etykiety i type=button do szablonów.','Przycisk w formularzu nie powinien przypadkowo wysyłać.',field],
['Wyświetl licznik znaków z text rodzica.','Nie odczytuj prywatnego DOM edytora w renderze.',field],
['Sprawdź typ niedozwolonego odczytu value z uchwytu.','Rodzic zna tylko wybrane metody.',model+'\nexport function wrong(handle:EditorHandle){\n// @ts-expect-error: brak value w publicznym API\nreturn handle.value;}'],
['Wyjaśnij, że typ nie jest granicą bezpieczeństwa złośliwego kodu.','Uchwyt jest kontraktem projektu.','Ograniczone API oddziela odpowiedzialności i ukrywa szczegóły w normalnym użyciu. TypeScript nie jest mechanizmem sandboxu ani zabezpieczeniem przed kodem celowo obchodzącym typy i dostęp do DOM.'],
['Nie obiecuj stałej tożsamości handle przy zmianie deps.','Nowe wartości mogą utworzyć nowy uchwyt.',editor],
['Zbuduj AttentionHandle udostępniające jednorazową animację.','Nie eksportuj całego div.',shake],
['Anuluj poprzednią animację przed nową.','Jeden ref przechowuje bieżący zasób.',shake],
['Uwzględnij reduced motion przy attention.','Ważny błąd musi mieć też tekstowy komunikat.',shake],
['Posprzątaj animację po odmontowaniu.','Cancel może odrzucić finished Promise.',shake],
['Obsłuż odrzucenie animation.finished po cancel.','Nie zostawiaj nieobsłużonej Promise.',shake],
['Rozróżnij metodę jednorazowego działania od trwałego stanu open/readOnly.','Props zwykle lepiej opisują stan.','Fokus i wstawienie przy kursorze są imperatywne. Trwałe readOnly lub open powinno mieć właściciela i przepływ danych przez props. Nie twórz drugiego niezsynchronizowanego stanu ukrytego za metodami handle.'],
['Przetestuj zakres wstawienia, najnowsze props, readonly oraz cleanup animacji.','Rzeczywisty kursor i fokus sprawdź w DOM.',field],
];
const concepts=[
['Co udostępnia useImperativeHandle?','Wybrany obiekt API w refie','Zawsze cały DOM','Nowe props rodzica','Automatyczny store'],
['Jaki ref przekazuje rodzic?','Ref<EditorHandle>','Zawsze Ref<HTMLDivElement>','Wyłącznie number','State string'],
['Gdzie jest prawdziwa textarea?','W prywatnym refie komponentu','W publicznym handle automatycznie','W typie rodzica','W key'],
['Czy focus musi obsłużyć null?','Tak','Nie','Tylko po readOnly','Tylko przy string'],
['Jak zmieniać tekst kontrolowany?','Przez onChange właściciela','Przypisać dowolne node.value i zignorować state','InnerHTML rodzica','Usunąć element'],
['Co oznacza start=end?','Wstawienie bez usuwania zakresu','Usunięcie całej treści','Zawsze błąd','Wyczyszczenie pola'],
['Co dzieje się dla zaznaczonego zakresu?','Zostaje zastąpiony wstawionym tekstem','Zawsze dopisujemy na koniec','Ignorujemy zaznaczenie','Resetujemy rodzica'],
['Gdzie przywrócić kursor po zmianie kontrolowanego value?','Po commicie zatwierdzonej wartości','Przed onChange zawsze','W czystym renderze','W module'],
['Czy requestAnimationFrame zawsze gwarantuje commit React?','Nie','Tak','Tylko dla textarea','Tylko w TSX'],
['Dlaczego pending zawiera oczekiwany tekst?','Aby nie ustawiać caret na innej transformacji rodzica','Aby zastąpić value','Aby zapisać backend','Aby zmienić id'],
['Co jeśli rodzic normalizuje tekst inaczej?','Komponent musi respektować uzgodniony kontrakt zamiast zakładać ten sam wynik','Zawsze ustaw dowolny kursor','Zignoruj rodzica','Usuń deps'],
['Czy metody handle mogą zamknąć stare value?','Tak, przy złych deps','Nie','Tylko w JSX','Tylko przy ref DOM'],
['Jakie deps ma uchwyt w przykładzie?','Value, onChange i readOnly','Tylko []','Zawsze cały window','Tylko privateRef.current'],
['Czy nowa funkcja onChange może zmienić uchwyt?','Tak','Nie','Tylko przy className','Tylko w SSR'],
['Co robi clear?','Zgłasza pusty tekst do właściciela','Usuwa node','Zmienia style','Resetuje cały App'],
['Czy clear powinno omijać readOnly?','Nie w tym kontrakcie','Tak zawsze','Tylko przy ref','Tylko w TSX'],
['Dlaczego readOnly jest propsem?','Opisuje trwały stan UI','Nie można ustawić go w DOM','Wymaga Promise','Jest jednorazową animacją'],
['Czy open modala zwykle powinno mieć deklaratywnego właściciela?','Tak','Nie','Tylko z handle','Tylko dla div'],
['Po co publiczny typ handle?','Dokumentuje i sprawdza wąski kontrakt','Gwarantuje brak każdego błędu runtime','Zapewnia autoryzację','Wykonuje metody'],
['Czy handle z trzema metodami ma automatycznie pole style?','Nie','Tak','Tylko po focus','Tylko w React 19'],
['Czy typ handle tworzy sandbox bezpieczeństwa?','Nie','Tak','Tylko z readonly','Tylko w TSX'],
['Czy handle musi mieć stabilną tożsamość niezależnie od deps?','Nie','Tak','Tylko z onChange','Tylko przy null'],
['Czy useImperativeHandle wywołujemy warunkowo?','Nie','Tak','Tylko w readOnly','Tylko gdy ref nie jest null'],
['Czy useImperativeHandle w React 19 wymaga forwardRef?','Nie dla ref jako props','Tak zawsze','Tylko dla textarea','Tylko w Vite'],
['Czy starszy forwardRef sam w sobie jest błędem?','Nie','Tak','Tylko przy focus','Tylko w TSX'],
['Po co anulować starą animację?','Uniknąć nakładania zasobów tego samego działania','Zresetować wszystkie dane','Zmienić handle w state','Usunąć callback'],
['Czy reduced motion powinno być uwzględnione?','Tak','Nie','Tylko dla audio','Tylko w development'],
['Co może zrobić cancel z animation.finished?','Odrzucić Promise','Zawsze zwrócić JSX','Zapisać storage','Usunąć props'],
['Czy animacja zastępuje tekst błędu?','Nie','Tak','Tylko przy ref','Tylko przy readonly'],
['Co test funkcji insertAt może potwierdzić?','Operację na tekście i caret, nie rzeczywisty focus DOM','Całą obsługę przeglądarki','Brak każdego migania','Zgodność backendu'],
];
const quiz=concepts.map(row=>[...row,'Uchwyt opisuje wąskie działania, podczas gdy tekst i trwałe opcje pozostają w deklaratywnym modelu props. Deps zapewniają aktualne zamknięcia metod.']);quiz.push(...require('./practice_ref_02.cjs').quiz.slice(0,30).map(row=>['Powtórka granicy DOM i ref: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(let position=0;position<=4;position++){add('Wstawienie pozycja '+position,`insertAt("abcd","X",${position},${position}).value`,'abcd'.slice(0,position)+'X'+'abcd'.slice(position));add('Caret pozycja '+position,`insertAt("abcd","X",${position},${position}).caret`,position+1);}
for(const [label,expr,a]of [['Zamiana środka','insertAt("abcd","X",1,3).value','aXd'],['Zamiana całości','insertAt("abcd","X",0,4).value','X'],['Usunięcie zakresu','insertAt("abcd","",1,3).value','ad'],['Caret usunięcie','insertAt("abcd","",1,3).caret',1],['Pusty input','insertAt("","ABC",0,0).value','ABC'],['Caret pusty input','insertAt("","ABC",0,0).caret',3],['Ujemny start clamp','insertAt("abcd","X",-2,0).value','Xabcd'],['Za duży start','insertAt("abcd","X",99,99).value','abcdX'],['Za duży end','insertAt("abcd","X",2,99).value','abX'],['End przed start','insertAt("abcd","X",3,1).value','abcXd'],['End przed start caret','insertAt("abcd","X",3,1).caret',4],['Tekst wieloznakowy','insertAt("abcd","XYZ",1,2).value','aXYZcd'],['Caret wieloznakowy','insertAt("abcd","XYZ",1,2).caret',4],['Puste wstawienie','insertAt("abcd","",2,2).value','abcd'],['Długość po zastąpieniu','insertAt("abcd","XYZ",1,3).value.length',5],['Start ułamek','insertAt("abcd","X",1.8,1.8).value','aXbcd'],['Całość usunięta','insertAt("abcd","",0,4).value.length',0],['Caret po usunięciu całości','insertAt("abcd","",0,4).caret',0],['Dwa wstawienia','insertAt(insertAt("ab","X",1,1).value,"Y",2,2).value','aXYb'],['Oryginał string','(()=>{const value="abcd";insertAt(value,"X",1,1);return value;})()','abcd'],['Nowy obiekt wyniku','insertAt("ab","X",1,1)===insertAt("ab","X",1,1)',false],['Typ caret','typeof insertAt("ab","X",1,1).caret','number'],['Typ value','typeof insertAt("ab","X",1,1).value','string'],['Offset UTF16','insertAt("ab","😀",1,1).caret',3],['Emoji wynik','insertAt("ab","😀",1,1).value','a😀b'],['Pusty w pustym','insertAt("","",0,0).caret',0],['Długość clear model','insertAt("opis","",0,4).value.length',0],['Po końcu clamp caret','insertAt("ab","X",10,20).caret',3],['Tylko koniec','insertAt("ab","X",2,2).value','abX'],['Tylko początek','insertAt("ab","X",0,0).value','Xab']])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1','abcd','abXcd'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik operacji edytora?',a,...wrong,'Wynik zastępuje zakres [start,end), a caret wskazuje koniec wstawionego fragmentu. Offsety JavaScript i textarea są liczone w jednostkach UTF-16.',model+'\nconsole.log('+expr+');']);}
const project={'src/Editor.tsx':field,'src/Attention.tsx':shake,'src/main.tsx':'import {createRoot} from "react-dom/client";import {DescriptionField} from "./Editor";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<main><h1>Wąskie API edytora</h1><DescriptionField/></main>);'};
module.exports={model,editor,field,shake,exercises,quiz,project};
