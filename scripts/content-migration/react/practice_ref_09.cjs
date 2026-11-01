const refs=require('./practice_ref_01.cjs'),dom=require('./practice_ref_02.cjs'),imperative=require('./practice_ref_03.cjs'),tabs=require('./practice_ref_04.cjs'),modal=require('./practice_ref_05.cjs'),focus=require('./practice_ref_06.cjs'),undo=require('./practice_ref_07.cjs'),floating=require('./practice_ref_08.cjs');
const counter=`import {useRef,useState} from "react";export function ClickStats(){const [visible,setVisible]=useState(0);const clicks=useRef(0);const [reported,setReported]=useState<number|null>(null);return <section><p>Stan: {visible}</p><p>Ostatni raport refu: {reported??"brak"}</p><button type="button" onClick={()=>{clicks.current++;setVisible(value=>value+1);}}>Klik</button><button type="button" onClick={()=>{clicks.current++;}}>Tylko ref</button><button type="button" onClick={()=>setReported(clicks.current)}>Pokaż ref</button></section>;}`;
const field=`import {useId,type ComponentPropsWithRef} from "react";type Props=Omit<ComponentPropsWithRef<"input">,"id">&{label:string};export function TextField({label,...input}:Props){const id=useId();return <label htmlFor={id}>{label}<input {...input} id={id}/></label>;}`;
const chat=`import {useLayoutEffect,useRef,useState} from "react";export type Message={id:number;text:string};export function FollowingChat({messages}:{messages:readonly Message[]}){const end=useRef<HTMLDivElement>(null);const [follow,setFollow]=useState(true);useLayoutEffect(()=>{if(follow)end.current?.scrollIntoView({block:"nearest",behavior:"auto"});},[messages,follow]);return <section><label><input type="checkbox" checked={follow} onChange={event=>setFollow(event.currentTarget.checked)}/>Śledź nowe wiadomości</label><ol>{messages.map(message=><li key={message.id}>{message.text}</li>)}</ol><div ref={end}/></section>;}`;
const exercises=[
['Trening 1: pokaż różnicę między kliknięciem aktualizującym stan i sam ref.','W JSX czytaj stan lub raport zapisany przez handler.',counter],
['Trening 2: skopiuj prywatny licznik do stanu dopiero na żądanie.','Ostatni raport ma pozostać snapshotem.',counter],
['Trening 3: usuń efekt uboczny z funkcji aktualizującej stan licznika.','Ref zwiększaj w handlerze przed czystym updaterem.',counter],
['Trening 4: dodaj anulowalną wysyłkę opóźnioną.','Timer należy do instancji i wymaga cleanup.',refs.delayed],
['Trening 5: wyślij najświeższy tekst zmieniony po zaplanowaniu wysyłki.','Aktualizuj ref tekstu w handlerze zmiany.',refs.delayed],
['Trening 6: zablokuj drugi równoległy timer wysyłki.','Sprawdź uchwyt oraz pending.',refs.delayed],
['Trening 7: anuluj autosave przed zaplanowaniem nowszego.','Jedna aktywna operacja dla instancji.',refs.autosave],
['Trening 8: pokaż błąd synchronicznego autosave.','Callback void w tej wersji nie oznacza async await.',refs.autosave],
['Trening 9: zresetuj zegar formularza po udanym zapisie.','Wartość startu 0 jest poprawna, sentinel to null.',refs.timer],
['Trening 10: zbuduj TextField przekazujące ref jako prop React 19.','ComponentPropsWithRef zachowuje natywne props.',field],
['Trening 11: zapewnij unikalne powiązania label w dwóch TextField.','useId należy do instancji.',field],
['Trening 12: dodaj globalny skrót wyszukiwarki z ochroną pól edycji.','Nie przechwytuj pisania ani IME.',dom.search],
['Trening 13: przewiń do nowego komentarza dopiero po jego commit.','Poczekaj aż props zawierają ID nowego rekordu.',dom.comments],
['Trening 14: dodaj jawny przełącznik śledzenia nowych wiadomości.','Ręczne wyłączenie zatrzymuje autoprzewijanie.',chat],
['Trening 15: wykonuj scroll na sentinel po renderze wiadomości.','Rodzic przekazuje nową tablicę po zmianie.',chat],
['Trening 16: zarejestruj elementy listy w Map po stabilnym ID.','Callback ref musi sprzątnąć właściwy węzeł.',dom.list],
['Trening 17: obsłuż wybór ID=0 i pustą listę.','Nie testuj ID przez if(id).',dom.list],
['Trening 18: ukryj textarea za uchwytem focus/insertText/clear.','Nie udostępniaj rodzicowi całego DOM.',imperative.editor],
['Trening 19: po wstawieniu tekstu przywróć caret dopiero po akceptacji value.','Nie ustawiaj selekcji przed commit.',imperative.editor],
['Trening 20: wyłącz metody modyfikujące przy readOnly.','Imperatywne wejście musi respektować ten sam kontrakt.',imperative.editor],
['Trening 21: połącz Tabs z zachowaniem wpisanych danych ukrytych paneli.','Ukrywaj hidden zamiast odmontowywać.',tabs.tabs],
['Trening 22: pomijaj wyłączoną zakładkę przy strzałkach.','Roving tabindex obejmuje tylko aktywne elementy.',tabs.tabs],
['Trening 23: pokaż klientowy portal bez dostępu do document podczas SSR.','Pierwszy render pozostaje pusty.',modal.portal],
['Trening 24: otwórz natywny dialog przez deklaratywny open.','showModal wywołuj po commit, nie w renderze.',modal.modal],
['Trening 25: odróżnij kliknięcie tła od przeciągnięcia tekstu poza dialog.','Początek i koniec gestu muszą być na tle.',modal.modal],
['Trening 26: zbuduj nawigację j/k ograniczoną do listy.','Enter pozostaw natywnemu przyciskowi.',focus.nav],
['Trening 27: zachowaj kolejność po cofnięciu dwóch usunięć w odwrotnej kolejności.','Źródłowa kolejność pozostaje kanoniczna.',undo.model],
['Trening 28: zatrzymaj deadline toasta do końca hover i fokusu.','Osobne przyczyny pauzy nie mogą się wzajemnie kasować.',undo.board],
['Trening 29: umieść akcje w niemodalnym popoverze z nawigacją Tab.','Nie przypisuj roli menu do niekompletnego widgetu.',floating.panel],
['Trening 30: dodaj tooltip obsługujący fokus i Escape.','Tooltip jest opisem, nie kontenerem dla linków.',floating.tooltip],
];
const quiz=[];for(const [topic,source]of [['Ref i timer',refs],['DOM i scroll',dom],['Uchwyt',imperative],['Tabs',tabs],['Portal i modal',modal],['Fokus',focus],['Undo',undo],['Popover',floating]])quiz.push(...source.quiz.slice(0,12).map(row=>['Trening przekrojowy — '+topic+': '+row[0],...row.slice(1)]));quiz.push(...refs.quiz.filter(row=>row[6]).slice(0,4).map(row=>['Trening kodu refów: '+row[0],...row.slice(1)]));
const project={'src/counter.tsx':counter,'src/field.tsx':field,'src/chat.tsx':chat,'src/tabs.tsx':tabs.tabs,'src/modal.tsx':modal.modal,'src/main.tsx':`import {StrictMode,useState} from "react";import {createRoot} from "react-dom/client";import {ClickStats} from "./counter";import {TextField} from "./field";import {FollowingChat,type Message} from "./chat";import {Tabs} from "./tabs";import {NativeModal} from "./modal";function App(){const [open,setOpen]=useState(false);const [messages,setMessages]=useState<Message[]>([]);const [text,setText]=useState("");return <main><h1>Trening refów i portali</h1><ClickStats/><TextField label="Szkic" value={text} onChange={event=>setText(event.currentTarget.value)}/><button type="button" onClick={()=>{const message=text.trim();if(!message)return;setMessages(items=>[...items,{id:items.reduce((max,item)=>Math.max(max,item.id),-1)+1,text:message}]);setText("");}}>Dodaj wiadomość</button><FollowingChat messages={messages}/><button type="button" onClick={()=>setOpen(true)}>Otwórz ćwiczenie</button><NativeModal open={open} title="Ćwiczenie zakładek" onClose={()=>setOpen(false)}><Tabs label="Ćwiczenie" tabs={[{id:"a",label:"Dane",content:<TextField label="Notatka"/>},{id:"b",label:"Podgląd",content:<p>Wróć do Dane: wpis ma pozostać.</p>}]}/></NativeModal></main>;}createRoot(document.getElementById("root")!).render(<StrictMode><App/></StrictMode>);`};module.exports={counter,field,chat,exercises,quiz,project};
