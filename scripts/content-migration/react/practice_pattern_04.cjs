const model=`export type Variant="primary"|"secondary"|"danger";export function classes(variant:Variant,extra?:string):string{return ["btn","btn--"+variant,extra].filter(Boolean).join(" ");}export function buttonType(type:"button"|"submit"|"reset"|undefined):"button"|"submit"|"reset"{return type??"button";}export function safeRel(target:string|undefined,rel:string|undefined):string|undefined{if(target!=="_blank")return rel;return [...new Set([...(rel??"").split(/\\s+/).filter(token=>token&&token!=="opener"),"noopener"])].join(" ");}`;
const button=`import type {ComponentPropsWithRef} from "react";
${model}
type Own={variant?:Variant};export type ButtonProps=Own&((ComponentPropsWithRef<"button">&{as?:"button";href?:never})|(Omit<ComponentPropsWithRef<"a">,"href">&{as:"a";href:string;disabled?:never}));export function Button(props:ButtonProps){if(props.as==="a"){const {as,variant="primary",className,target,rel,...rest}=props;return <a {...rest} target={target} rel={safeRel(target,rel)} className={classes(variant,className)}/>;}const {as,variant="primary",className,type,...rest}=props;return <button {...rest} type={buttonType(type)} className={classes(variant,className)}/>;}`;
const box=`import type {ComponentPropsWithoutRef,ElementType} from "react";type Own<E extends ElementType>={as?:E;className?:string};export type BoxProps<E extends ElementType="div">=Own<E>&Omit<ComponentPropsWithoutRef<NoInfer<E>>,keyof Own<E>>;export function Box<E extends ElementType="div">({as,className,...rest}:BoxProps<E>){const Component:ElementType=as??"div";return <Component {...rest} className={["box",className].filter(Boolean).join(" ")}/>;}`;
const slot=`import {Slot} from "@radix-ui/react-slot";import type {HTMLAttributes,ReactElement,Ref} from "react";export type SlotButtonProps=Omit<HTMLAttributes<HTMLElement>,"children">&{children:ReactElement;ref?:Ref<HTMLElement>;variant?:"primary"|"secondary"};export function SlotButton({children,variant="primary",className,...rest}:SlotButtonProps){return <Slot {...rest} className={["btn","btn--"+variant,className].filter(Boolean).join(" ")}>{children}</Slot>;}`;
const input=`import {useId,type ComponentPropsWithRef} from "react";export type InputProps=ComponentPropsWithRef<"input">&{label:string;error?:string};export function Input({label,error,id,"aria-describedby":describedBy,"aria-invalid":invalid,...rest}:InputProps){const generated=useId();const inputId=id||generated;const errorId=inputId+"-error";const description=[describedBy,error?errorId:undefined].filter(Boolean).join(" ")||undefined;return <div><label htmlFor={inputId}>{label}</label><input {...rest} id={inputId} aria-invalid={error?true:invalid} aria-describedby={description}/>{error&&<p id={errorId}>{error}</p>}</div>;}`;
const contracts=`import {createRef} from "react";import {Button} from "./button";import {Box} from "./box";const buttonRef=createRef<HTMLButtonElement>();const linkRef=createRef<HTMLAnchorElement>();export const valid=<><Button ref={buttonRef}>Akcja</Button><Button as="a" href="/tickets/0" ref={linkRef}>Ticket</Button><Box as="a" href="/help">Pomoc</Box></>;
// @ts-expect-error href nie należy do przycisku
const wrongButton=<Button href="/x">Błąd</Button>;
// @ts-expect-error wymagany href linku
const noHref=<Button as="a">Błąd</Button>;
// @ts-expect-error disabled nie należy do linkowego kontraktu
const disabledLink=<Button as="a" href="/x" disabled>Błąd</Button>;
// @ts-expect-error ref przycisku nie pasuje do linku
const wrongRef=<Button as="a" href="/x" ref={buttonRef}>Błąd</Button>;
// @ts-expect-error bez as Box jest div, który nie ma href
const wrongBox=<Box href="/x"/>;`;
const demo=`import {useRef,useState} from "react";import {Button} from "./button";import {Box} from "./box";import {SlotButton} from "./slot";import {Input} from "./input";export function Demo(){const input=useRef<HTMLInputElement>(null);const [count,setCount]=useState(0);return <main><h1>Polimorfizm i semantyka</h1><Button onClick={()=>setCount(value=>value+1)}>Zapisz lokalnie</Button><p role="status">Akcje: {count}</p><Button as="a" href="#ticket" target="_blank" variant="secondary">Zobacz ticket w nowej karcie</Button><Box as="section" aria-label="Dane"><Input ref={input} label="E-mail" type="email" autoComplete="email" required aria-describedby="help" error="Przykładowy błąd"/><p id="help">Dodatkowy opis użytkownika</p><Button onClick={()=>input.current?.focus()}>Przejdź do pola</Button></Box><SlotButton onClick={event=>{if(!event.defaultPrevented)setCount(value=>value+1);}}><a href="#ticket" className="custom-link">Link przez Slot</a></SlotButton><section id="ticket"><h2>Ticket #0</h2></section></main>;}`;
const exercises=[
['Zaprojektuj Button z rozłącznymi props dla a i button.','Typ ref oraz handlera zależy od gałęzi.',button],
['Wymagaj href dla linkowego wariantu.','Nie renderuj martwego a bez adresu.',button],
['Odrzuć href bez as="a" na etapie TypeScript.','Unia nie powinna dopuszczać sprzecznych props.',contracts],
['Ustaw type="button" jako domyślne dla przycisku.','Użycie w formularzu nie może przypadkiem wysłać danych.',button],
['Zachowaj jawne type submit i reset.','Domyślna wartość nie nadpisuje intencji użytkownika.',button],
['Przekaż ref jako zwykły prop React 19.','ComponentPropsWithRef zachowuje konkretny typ DOM.',button],
['Sprawdź błąd ref przycisku przekazanego do linku.','Negatywne przypadki oznacz @ts-expect-error.',contracts],
['Połącz klasy wariantu i className użytkownika.','Nie gub własnej klasy bazowej.',model],
['Przekaż aria-label, id i data atrybuty do elementu docelowego.','Spread rest pozostaje na konkretnym a albo button.',button],
['Zachowaj semantykę nawigacji przez href.','Środkowy przycisk i kopiowanie adresu są natywnymi funkcjami linku.',demo],
['Nie dopuszczaj disabled w linkowym kontrakcie.','Link nie ma natywnej semantyki disabled.',button],
['Dodaj noopener do linku target blank z zachowaniem innych rel.','Usuń sprzeczny token opener.',model],
['Nie wymuszaj noreferrer bez potrzeby.','To osobna decyzja o przekazywaniu referrera.',model],
['Zbuduj ogólny Box z as i ComponentPropsWithoutRef.','Ten wariant Box jawnie nie przekazuje ref.',box],
['Ogranicz wnioskowanie E do prop as.','NoInfer zapobiega dopasowaniu elementu tylko przez href.',box],
['Usuń kolidujące własne props przez Omit.','className i as należą do kontraktu opakowania.',box],
['Zastosuj Box as section z dostępną nazwą.','Zmiana elementu musi zachować poprawny sens HTML.',demo],
['Sprawdź błąd href w domyślnym Box div.','Test kompilacji ma wykryć niepotrzebnie szeroki typ.',contracts],
['Porównaj jawny Button a/button z ogólnym Box.','Bardziej elastyczne API kosztuje trudniejsze typowanie.',demo],
['Zastosuj biblioteczny Slot do asChild bez dodatkowego elementu DOM.','Zależność przykładu: @radix-ui/react-slot.',slot],
['Zachowaj className dziecka i klasę slotu.','Nie zastępuj klas prostym cloneElement bez łączenia.',slot],
['Przemyśl kolejność handlerów dziecka i slotu.','Handler dziecka działa pierwszy, zewnętrzny sprawdza defaultPrevented.',demo],
['Zachowaj pojedyncze dziecko Slot jako ReactElement.','Typ nie gwarantuje przekazywania props przez dowolny komponent użytkownika.',slot],
['Nie przekazuj type i disabled bezmyślnie do dowolnego Slot dziecka.','Semantykę natywną określa docelowy element.',slot],
['Zbuduj Input z ComponentPropsWithRef input.','Nie wypisuj ręcznie każdego natywnego atrybutu.',input],
['Wygeneruj unikalne ID pola przy braku własnego.','Tekst label nie jest unikalnym identyfikatorem.',input],
['Połącz zewnętrzne aria-describedby z błędem pola.','Nie zgub instrukcji użytkownika komponentu.',input],
['Nie pozwól spread props nadpisać aria-invalid przy rzeczywistym błędzie.','Destrukturyzuj i świadomie połącz wartości.',input],
['Zachowaj aria-invalid użytkownika przy braku lokalnego błędu.','Wrapper nie zna całej walidacji rodzica.',input],
['Sprawdź wynikowy HTML i kontrakty typów przycisków oraz linków.','Build nie zastępuje weryfikacji semantyki.',contracts],
];
const concepts=[
['Po co komponent polimorficzny?','Łączy wspólny wygląd z różnymi elementami semantycznymi','Zamienia każdy link w div','Usuwa HTML','Zastępuje router'],
['Co jest właściwe dla nawigacji?','a z href lub komponent linku routera','button zmieniający location dla każdego linku','div z role bez handlera','span z disabled'],
['Co jest właściwe dla operacji Zapisz?','button','a bez href','role tooltip','section onClick'],
['Dlaczego domyślny type button?','Chroni przed przypadkowym submitem w formularzu','Zmienia link w przycisk','Blokuje wszystkie kliknięcia','Wymusza reset'],
['Czy href jest atrybutem button?','Nie','Tak','Tylko w React 19','Tylko w formularzu'],
['Czy a ma natywne disabled?','Nie','Tak','Tylko przy target','Tylko w TSX'],
['Co daje ComponentPropsWithRef?','Props elementu wraz z właściwym ref','Wyłącznie className','Automatyczny focus trap','Globalny stan'],
['Co daje ComponentPropsWithoutRef?','Props bez ref','Wszystkie props z obowiązkowym ref','Tylko role','Tylko eventy'],
['Po co Omit w polimorficznym API?','Usuwa kolizje z własnymi props','Usuwa wszystkie atrybuty ARIA','Blokuje generics','Kopiuje DOM'],
['Dlaczego NoInfer na props Box?','Typ elementu wynika z as, a nie dopasowania dowolnych props','Żeby wyłączyć TypeScript','Żeby wymusić a','Żeby zapewnić memo'],
['Czy ogólny Box w tej lekcji przekazuje ref?','Nie, jego API jawnie wyklucza ref','Tak do dowolnego DOM','Tylko po useId','Tylko z className'],
['Czy Button tej lekcji przekazuje ref?','Tak, osobno typowany dla a i button','Nie nigdy','Wyłącznie jako string','Tylko przez forwardRef'],
['Czy React 19 wymaga forwardRef dla function component przyjmującego ref?','Nie, ref może być propem','Tak zawsze','Tylko dla a','Tylko w StrictMode'],
['Po co className łączyć zamiast zastępować?','Żeby zachować styl wariantu i klasę konsumenta','Żeby usunąć style','Żeby skopiować event','Żeby zmienić HTML'],
['Co robi Slot?','Łączy props z bezpośrednim dzieckiem','Dodaje obowiązkowy div','Zmienia dziecko w portal','Klonuje wszystkie poddrzewa'],
['Czy cloneElement mutuje oryginalny element?','Nie','Tak','Tylko przy className','Tylko gdy ma ref'],
['Czy cloneElement samo łączy dwa onClick?','Nie, props mogą nadpisać handler','Tak zawsze','Tylko przy button','Tylko w TypeScript'],
['Jaka jest kolejność eventów w użytym Slot?','Handler dziecka przed handlerem slotu','Zawsze slot przed dzieckiem','Losowa','Tylko jeden może istnieć'],
['Czy defaultPrevented samo blokuje dowolny kod zewnętrznego handlera?','Nie, handler musi je respektować','Tak zawsze','Tylko gdy Promise','Tylko w Context'],
['Jakie dziecko wymaga Slot w przykładzie?','Jeden ReactElement','Dowolny string','Zawsze tablica dwóch przycisków','Wyłącznie fragment bez props'],
['Czy dowolny komponent dziecka automatycznie przekazuje props do DOM?','Nie, musi implementować ten kontrakt','Tak','Tylko gdy nazwa duża','Tylko z role'],
['Dlaczego nie generować ID z label?','Dwa pola mogą mieć ten sam label','Label nigdy nie jest stringiem','HTML nie ma ID','React wymaga UUID z serwera'],
['Jak łączyć opis błędu z instrukcją pola?','Dodać oba ID do aria-describedby','Nadpisać instrukcję pustym stringiem','Usunąć label','Zmienić role na alert dla inputa'],
['Czy aria-invalid opisuje treść błędu?','Nie, potrzebny jest komunikat','Tak automatycznie','Tylko dla type email','Tylko z ref'],
['Czy spread props na wrapper div wystarcza dla input ref?','Nie, ref musi trafić na input','Tak','Tylko z className','Tylko po fokusie'],
['Co dodaje safeRel dla target blank?','noopener','Zawsze noreferrer','Wyłącznie opener','aria-modal'],
['Dlaczego usunąć opener przy dodawaniu noopener?','Nie zostawiać sprzecznej intencji w rel','Żeby zmienić href','Żeby usunąć target','Żeby wymusić download'],
['Czy noreferrer i noopener są identyczną decyzją prywatności?','Nie, noreferrer dodatkowo ogranicza referrer','Tak zawsze','Tylko na serwerze','Tylko w TypeScript'],
['Co sprawdza @ts-expect-error?','Że kolejna linia rzeczywiście zgłasza błąd typów','Że kod działa w przeglądarce','Że wszystkie wyjątki są obsłużone','Że Vite uruchomił test DOM'],
['Czy zmiana as gwarantuje poprawną semantykę sama z siebie?','Nie, autor nadal dobiera poprawny element i props','Tak zawsze','Tylko dla div','Tylko z CSS'],
];
const quiz=concepts.map(row=>[...row,'Wygląd nie zmienia semantyki HTML. Typy, przekazywanie props i składanie handlerów muszą odpowiadać docelowemu elementowi.']);quiz.push(...require('./practice_pattern_03.cjs').quiz.slice(0,30).map(row=>['Powtórka kontraktów API: '+row[0],...row.slice(1)]));
const cases=[];function add(label,expr,answer){cases.push([label,expr,String(answer)]);}for(const variant of ['primary','secondary','danger'])for(const extra of [undefined,'custom','x y','btn',''])add('Klasy '+variant+' / '+String(extra),`classes(${JSON.stringify(variant)},${JSON.stringify(extra)??'undefined'})`,['btn','btn--'+variant,extra].filter(Boolean).join(' '));
for(const [label,expr,answer]of [['Typ domyślny','buttonType(undefined)','button'],['Typ button','buttonType("button")','button'],['Typ submit','buttonType("submit")','submit'],['Typ reset','buttonType("reset")','reset'],['Rel bez target','String(safeRel(undefined,undefined))','undefined'],['Rel własny bez target','safeRel(undefined,"author")','author'],['Self rel','safeRel("_self","author")','author'],['Blank pusty','safeRel("_blank",undefined)','noopener'],['Blank author','safeRel("_blank","author")','author noopener'],['Blank już noopener','safeRel("_blank","noopener")','noopener'],['Blank noreferrer','safeRel("_blank","noreferrer")','noreferrer noopener'],['Blank opener','safeRel("_blank","opener")','noopener'],['Blank wiele','safeRel("_blank","author opener external")','author external noopener'],['Blank duplikat','safeRel("_blank","author author")','author noopener'],['Blank białe znaki','safeRel("_blank","  author  ")','author noopener'],['Blank pusty string','safeRel("_blank","")','noopener'],['Self opener','safeRel("_self","opener")','opener'],['Nazwa okna','safeRel("preview","author")','author'],['Kolejność rel','safeRel("_blank","external author")','external author noopener'],['Deduplikacja noopener','safeRel("_blank","noopener noopener")','noopener'],['Rel tab','safeRel("_blank","author\\texternal")','author external noopener'],['Przycisk styl','classes("primary").includes("btn--primary")',true],['Klasa własna','classes("danger","custom").endsWith("custom")',true],['Default nie submit','buttonType(undefined)==="submit"',false],['Blank nie opener','safeRel("_blank","opener")?.split(" ").includes("opener")',false]])add(label,expr,answer);
for(const [label,expr,answer]of cases){const wrong=['true','false','null','undefined','button','submit','noopener'].filter(v=>v!==answer).slice(0,3);quiz.push([label+': jaki wynik helpera polimorficznego UI?',answer,...wrong,'Zastosuj jawne klasy, domyślny typ button albo regułę składania rel.',model+'\nconsole.log('+expr+');']);}
const project={'src/button.tsx':button,'src/box.tsx':box,'src/slot.tsx':slot,'src/input.tsx':input,'src/contracts.tsx':contracts,'src/demo.tsx':demo,'src/main.tsx':`import {StrictMode} from "react";import {createRoot} from "react-dom/client";import {Demo} from "./demo";createRoot(document.getElementById("root")!).render(<StrictMode><Demo/></StrictMode>);`};module.exports={model,button,box,slot,input,contracts,demo,exercises,quiz,project};
