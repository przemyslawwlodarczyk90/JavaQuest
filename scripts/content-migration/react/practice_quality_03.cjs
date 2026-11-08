const {lesson}=require('./practice_kit.cjs');
const audit=String.raw`export type Method="axe"|"lighthouse"|"keyboard"|"screenReader";
export type Severity="critical"|"serious"|"moderate"|"minor";
export type Finding={screen:string;criterion:string;summary:string;method:Method;severity:Severity;fixed:boolean};
export type Report={screens:readonly string[];runs:readonly {screen:string;method:Method}[];findings:readonly Finding[];limitations:readonly string[]};

export const requiredMethods:readonly Method[]=["axe","keyboard","screenReader"];
const rank:Record<Severity,number>={critical:0,serious:1,moderate:2,minor:3};

// axe zapisuje kryteria jako tagi, np. wcag143 lub wcag2411. Tagi poziomów, np. wcag2aa, nie są kryteriami.
export function criterionFromTag(tag:string){
  const match=/^wcag(\d)(\d)(\d+)$/.exec(tag);
  return match?match[1]+"."+match[2]+"."+match[3]:null;
}

type AxeViolation={id:string;impact?:string|null;help:string;tags:string[];nodes:readonly unknown[]};

export function fromAxe(screen:string,violations:readonly AxeViolation[]):Finding[]{
  return violations.map(violation=>({
    screen,
    criterion:violation.tags.map(criterionFromTag).find(value=>value!==null)??"poza WCAG",
    summary:violation.id+": "+violation.help+" ("+violation.nodes.length+")",
    method:"axe",
    severity:(violation.impact??"moderate") as Severity,
    fixed:false,
  }));
}

export function missingRuns(report:Pick<Report,"screens"|"runs">){
  return report.screens.flatMap(screen=>requiredMethods
    .filter(method=>!report.runs.some(run=>run.screen===screen&&run.method===method))
    .map(method=>screen+":"+method));
}

export function openFindings(findings:readonly Finding[]){
  return findings
    .filter(finding=>!finding.fixed)
    .toSorted((a,b)=>rank[a.severity]-rank[b.severity]||a.screen.localeCompare(b.screen,"pl"));
}

export function canSignOff(report:Report){
  const blockers=openFindings(report.findings).filter(finding=>finding.severity==="critical"||finding.severity==="serious");
  return missingRuns(report).length===0&&blockers.length===0&&report.limitations.length>0;
}

export function renderReport(report:Report){
  const lines=["Zakres: "+report.screens.join(", ")];
  for(const finding of openFindings(report.findings))lines.push("["+finding.severity+"] "+finding.criterion+" "+finding.screen+": "+finding.summary);
  for(const missing of missingRuns(report))lines.push("Brak przebiegu: "+missing);
  for(const limit of report.limitations)lines.push("Ograniczenie: "+limit);
  return lines.join("\n");
}`;
const dialog=String.raw`import {useEffect,useId,useRef,useState,type KeyboardEvent} from "react";

const FOCUSABLE="button:not([disabled]),input:not([disabled]),select:not([disabled]),a[href],[tabindex='0']";

export function AssignDialog({agents,onAssign}:{agents:readonly string[];onAssign:(agent:string)=>void}){
  const [open,setOpen]=useState(false);
  const [agent,setAgent]=useState(agents[0]??"");
  const titleId=useId(),fieldId=useId();
  const trigger=useRef<HTMLButtonElement>(null);
  const panel=useRef<HTMLDivElement>(null);
  const field=useRef<HTMLSelectElement>(null);

  useEffect(()=>{if(open)field.current?.focus();},[open]);

  function close(){
    setOpen(false);
    trigger.current?.focus();
  }

  function onKeyDown(event:KeyboardEvent<HTMLDivElement>){
    if(event.key==="Escape"){close();return;}
    if(event.key!=="Tab")return;
    const items=[...(panel.current?.querySelectorAll<HTMLElement>(FOCUSABLE)??[])];
    const first=items[0],last=items[items.length-1];
    if(event.shiftKey&&document.activeElement===first){event.preventDefault();last.focus();}
    else if(!event.shiftKey&&document.activeElement===last){event.preventDefault();first.focus();}
  }

  return (
    <>
      <button ref={trigger} type="button" onClick={()=>setOpen(true)}>Przypisz</button>
      {open&&(
        <div ref={panel} role="dialog" aria-modal="true" aria-labelledby={titleId} onKeyDown={onKeyDown}>
          <h2 id={titleId}>Przypisz ticket</h2>
          <label htmlFor={fieldId}>Agent</label>
          <select id={fieldId} ref={field} value={agent} onChange={event=>setAgent(event.currentTarget.value)}>
            {agents.map(name=><option key={name}>{name}</option>)}
          </select>
          <button type="button" onClick={close}>Anuluj</button>
          <button type="button" onClick={()=>{onAssign(agent);close();}}>Zapisz</button>
        </div>
      )}
    </>
  );
}`;
const form=String.raw`import {useId,useRef,useState,type FormEvent} from "react";

export function TicketForm({onSubmit}:{onSubmit:(subject:string)=>void}){
  const [subject,setSubject]=useState("");
  const [error,setError]=useState("");
  const id=useId();
  const input=useRef<HTMLInputElement>(null);

  function submit(event:FormEvent){
    event.preventDefault();
    if(!subject.trim()){
      setError("Podaj temat ticketu");
      input.current?.focus();
      return;
    }
    setError("");
    onSubmit(subject.trim());
  }

  return (
    <form aria-label="Nowy ticket" onSubmit={submit} noValidate>
      <label htmlFor={id}>Temat</label>
      <input ref={input} id={id} value={subject} required aria-invalid={Boolean(error)} aria-describedby={error?id+"-error":undefined} onChange={event=>setSubject(event.currentTarget.value)}/>
      <p id={id+"-error"} role="alert">{error}</p>
      <button>Utwórz</button>
    </form>
  );
}`;
const axeTest=String.raw`import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {axe} from "vitest-axe";
import {expect,it} from "vitest";
import {TicketForm} from "./TicketForm";
import {AssignDialog} from "./AssignDialog";

const options={rules:{"color-contrast":{enabled:false},region:{enabled:false}}};

async function summary(container:Element){
  const results=await axe(container,options);
  return results.violations.map(item=>item.id+": "+item.help+" ("+item.nodes.length+")");
}

it("formularz ticketu nie ma naruszeń wykrywalnych przez axe",async()=>{
  const view=render(<TicketForm onSubmit={()=>{}}/>);
  expect(await summary(view.container)).toEqual([]);
});

it("formularz po błędzie walidacji nadal nie ma naruszeń",async()=>{
  const user=userEvent.setup();
  const view=render(<TicketForm onSubmit={()=>{}}/>);
  await user.click(screen.getByRole("button",{name:"Utwórz"}));
  expect(screen.getByRole("alert")).toHaveTextContent("Podaj temat");
  expect(await summary(view.container)).toEqual([]);
});

it("otwarty dialog nie ma naruszeń wykrywalnych przez axe",async()=>{
  const user=userEvent.setup();
  const view=render(<AssignDialog agents={["Ala","Ola"]} onAssign={()=>{}}/>);
  await user.click(screen.getByRole("button",{name:"Przypisz"}));
  expect(await summary(view.container)).toEqual([]);
});`;
const tests=String.raw`import {expect,test,vi} from "vitest";
import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {AssignDialog} from "./AssignDialog";
import {TicketForm} from "./TicketForm";
import {canSignOff,criterionFromTag,fromAxe,missingRuns,openFindings,renderReport,type Finding,type Report} from "./auditModel";

test("dialog dostaje fokus na polu i oddaje go po Escape",async()=>{
  const user=userEvent.setup();
  render(<AssignDialog agents={["Ala"]} onAssign={()=>{}}/>);
  const trigger=screen.getByRole("button",{name:"Przypisz"});
  await user.click(trigger);
  expect(screen.getByRole("dialog",{name:"Przypisz ticket"})).toBeVisible();
  expect(screen.getByRole("combobox",{name:"Agent"})).toHaveFocus();
  await user.keyboard("{Escape}");
  expect(screen.queryByRole("dialog")).toBeNull();
  expect(trigger).toHaveFocus();
});

test("Tab i Shift+Tab nie wychodzą z dialogu",async()=>{
  const user=userEvent.setup();
  render(<><AssignDialog agents={["Ala"]} onAssign={()=>{}}/><a href="/poza">Poza</a></>);
  await user.click(screen.getByRole("button",{name:"Przypisz"}));
  await user.tab({shift:true});
  expect(screen.getByRole("button",{name:"Zapisz"})).toHaveFocus();
  await user.tab();
  expect(screen.getByRole("combobox",{name:"Agent"})).toHaveFocus();
});

test("zapis przekazuje agenta i oddaje fokus",async()=>{
  const user=userEvent.setup(),onAssign=vi.fn();
  render(<AssignDialog agents={["Ala","Ola"]} onAssign={onAssign}/>);
  await user.click(screen.getByRole("button",{name:"Przypisz"}));
  await user.selectOptions(screen.getByRole("combobox"),"Ola");
  await user.click(screen.getByRole("button",{name:"Zapisz"}));
  expect(onAssign).toHaveBeenCalledWith("Ola");
  expect(screen.getByRole("button",{name:"Przypisz"})).toHaveFocus();
});

test("błąd formularza przenosi fokus na pole i opisuje je",async()=>{
  const user=userEvent.setup(),onSubmit=vi.fn();
  render(<TicketForm onSubmit={onSubmit}/>);
  await user.click(screen.getByRole("button",{name:"Utwórz"}));
  const input=screen.getByRole("textbox",{name:"Temat"});
  expect(input).toHaveFocus();
  expect(input).toHaveAttribute("aria-invalid","true");
  expect(input).toHaveAccessibleDescription("Podaj temat ticketu");
  expect(onSubmit).not.toHaveBeenCalled();
  await user.type(input,"  Awaria  ");
  await user.keyboard("{Enter}");
  expect(onSubmit).toHaveBeenCalledWith("Awaria");
  expect(input).toHaveAttribute("aria-invalid","false");
});

const finding=(over:Partial<Finding>):Finding=>({screen:"/tickets",criterion:"1.4.3",summary:"Kontrast",method:"axe",severity:"serious",fixed:false,...over});
const complete=(findings:Finding[]=[]):Report=>({screens:["/tickets"],runs:[{screen:"/tickets",method:"axe"},{screen:"/tickets",method:"keyboard"},{screen:"/tickets",method:"screenReader"}],findings,limitations:["Wykres ma alternatywę dopiero w rozdziale 20"]});

test("tagi axe zamieniają się na kryteria",()=>{
  expect(criterionFromTag("wcag143")).toBe("1.4.3");
  expect(criterionFromTag("wcag2411")).toBe("2.4.11");
  expect(criterionFromTag("wcag2aa")).toBeNull();
  expect(criterionFromTag("best-practice")).toBeNull();
});

test("naruszenie axe staje się pozycją raportu",()=>{
  const [item]=fromAxe("/login",[{id:"label",impact:"critical",help:"Pola mają etykiety",tags:["cat.forms","wcag2a","wcag412"],nodes:[{},{}]}]);
  expect(item).toMatchObject({screen:"/login",criterion:"4.1.2",severity:"critical",method:"axe"});
  expect(item.summary).toContain("(2)");
});

test("brak testu ręcznego blokuje podpis mimo zera naruszeń",()=>{
  const report={...complete(),runs:[{screen:"/tickets",method:"axe" as const}]};
  expect(missingRuns(report)).toEqual(["/tickets:keyboard","/tickets:screenReader"]);
  expect(canSignOff(report)).toBe(false);
  expect(missingRuns({screens:["/a"],runs:[{screen:"/b",method:"axe"}]})).toContain("/a:axe");
});

test("poważny otwarty problem blokuje, a poprawiony lub drobny nie",()=>{
  expect(canSignOff(complete([finding({})]))).toBe(false);
  expect(canSignOff(complete([finding({fixed:true})]))).toBe(true);
  expect(canSignOff(complete([finding({severity:"minor"})]))).toBe(true);
});

test("raport bez opisanych ograniczeń nie jest uczciwy",()=>{
  expect(canSignOff({...complete(),limitations:[]})).toBe(false);
});

test("otwarte problemy są posortowane według wagi",()=>{
  const list=[finding({severity:"minor",summary:"a"}),finding({severity:"critical",summary:"b"}),finding({severity:"moderate",summary:"c",fixed:true})];
  expect(openFindings(list).map(item=>item.summary)).toEqual(["b","a"]);
  expect(list[0].summary).toBe("a");
});

test("raport pokazuje problemy, braki i ograniczenia",()=>{
  const report=renderReport({...complete([finding({})]),runs:[]});
  expect(report).toContain("[serious] 1.4.3 /tickets: Kontrast");
  expect(report).toContain("Brak przebiegu: /tickets:keyboard");
  expect(report).toContain("Ograniczenie: Wykres");
});`;
const playwrightAxe=String.raw`// e2e/a11y.spec.ts
import {test,expect} from "@playwright/test";
import AxeBuilder from "@axe-core/playwright";

const screens=["/tickets","/tickets/42","/tickets/new","/login"];

for(const path of screens){
  test("brak naruszeń WCAG AA: "+path,async({page})=>{
    await page.goto(path);
    const results=await new AxeBuilder({page})
      .withTags(["wcag2a","wcag2aa","wcag21aa","wcag22aa"])
      .analyze();
    expect(results.violations.map(item=>item.id)).toEqual([]);
  });
}`;
const playwrightKeyboard=String.raw`// e2e/keyboard.spec.ts
import {test,expect} from "@playwright/test";

test("modal przypisania: pułapka fokusu i powrót fokusu",async({page})=>{
  await page.goto("/tickets/42");
  const trigger=page.getByRole("button",{name:"Przypisz"});
  await trigger.focus();
  await page.keyboard.press("Enter");
  const dialog=page.getByRole("dialog",{name:"Przypisz ticket"});
  await expect(dialog.getByRole("combobox",{name:"Agent"})).toBeFocused();
  await page.keyboard.press("Escape");
  await expect(dialog).toBeHidden();
  await expect(trigger).toBeFocused();
});`;
const exercises=[
['Rozdziel problemy DevDesk na wykrywalne przez automat i wymagające człowieka.','Sens tekstu i kolejność fokusu nie są regułą składniową.','Automat: brak alt, brak etykiety, niski kontrast w przeglądarce, złe role. Człowiek: czy alt ma sens, czy kolejność fokusu jest logiczna, czy fokus wraca po modalu, czy komunikaty pojawiają się w dobrym momencie, czy Kanban działa z klawiatury.'],
['Uruchom vitest-axe dla formularza ticketu.','Wyłącz w jsdom regułę kontrastu.',axeTest],
['Wypisz raport naruszeń jako czytelne teksty.','Id, opis i liczba elementów powiedzą więcej niż samo false.',axeTest],
['Sprawdź stan formularza także po błędzie walidacji.','Audyt samego stanu początkowego pomija błędy.',axeTest],
['Sprawdź otwarty dialog testem axe.','Zawartość zamkniętej warstwy nie istnieje w DOM.',axeTest],
['Napisz test przeglądarkowy Playwright i axe dla czterech ekranów.','Tylko przeglądarka liczy kontrast i style.',playwrightAxe],
['Napisz test E2E powrotu fokusu po zamknięciu modala.','Sprawdź pole w dialogu oraz przycisk po Escape.',playwrightKeyboard],
['Zbuduj dialog z rolą, nazwą i aria-modal.','Nazwę daje nagłówek przez aria-labelledby.',dialog],
['Przenieś fokus do pierwszego pola po otwarciu dialogu.','Efekt działa po commicie, gdy pole jest już w DOM.',dialog],
['Dodaj pułapkę fokusu w obie strony.','Obsłuż Tab na ostatnim i Shift+Tab na pierwszym elemencie.',dialog],
['Oddaj fokus do przycisku po każdym sposobie zamknięcia.','Escape, Anuluj i Zapisz powinny korzystać z jednej funkcji.',dialog],
['Przetestuj pułapkę fokusu z user.tab.','Sprawdź element poza dialogiem, który nie może dostać fokusu.',tests],
['Przenieś fokus na pole z błędem po wysłaniu formularza.','Użytkownik czytnika dowie się o błędzie w miejscu pracy.',form],
['Powiąż błąd z polem przez aria-describedby.','Identyfikator ustawiaj tylko, gdy błąd istnieje.',form],
['Zamień tagi axe na numery kryteriów WCAG.','Tag wcag2aa to poziom, nie kryterium.',audit],
['Zamień naruszenia axe na pozycje raportu.','Zachowaj liczbę elementów i wagę.',audit],
['Wylicz brakujące przebiegi dla każdego ekranu.','Zero naruszeń nie zastępuje przejścia klawiaturą.',audit],
['Zdefiniuj warunek podpisania raportu.','Brak przebiegów, otwarte poważne problemy i brak ograniczeń blokują podpis.',audit],
['Posortuj otwarte problemy według wagi bez mutacji danych.','Użyj toSorted albo kopii tablicy.',audit],
['Wygeneruj tekst raportu z zakresem, problemami i ograniczeniami.','Raport ma dać się przeczytać bez kodu.',audit],
['Przetestuj, że brak testu ręcznego blokuje podpis.','To kluczowa różnica między audytem a uruchomieniem narzędzia.',tests],
['Porównaj wyniki Lighthouse i axe DevTools.','Te narzędzia mają inne reguły i inaczej raportują wynik.','Lighthouse podaje wynik punktowy i część reguł axe, ale nie sprawdza całości ręcznie. Rozszerzenie axe DevTools pokazuje szczegóły i elementy. Zapisz wersje, ekran oraz stan aplikacji; porównuj listy problemów, a nie punkty. Punktacja nie jest dowodem zgodności.'],
['Napisz listę ręcznej kontroli klawiatury.','Lista ma obejmować otwarte warstwy, a nie tylko stan początkowy.','Kolejność fokusu; widoczny fokus; Escape zamyka warstwy; fokus wraca; brak pułapek poza modalem; Enter i Spacja działają; skróty nie kolidują; tabela i Kanban mają alternatywę dla myszy.'],
['Wypisz, co sprawdzasz czytnikiem na formularzu ticketu.','Nazwa, opis, stan i ogłoszenie.','Nazwę pola i jego wymagalność, powiązany opis i błąd, ogłoszenie błędu po wysłaniu, ogłoszenie sukcesu oraz nazwę dialogu po otwarciu. Zapisz wersję czytnika i przeglądarki, bo wynik zależy od tej pary.'],
['Zmapuj problemy ekranu na kryteria WCAG.','Każda pozycja raportu ma mieć numer kryterium.','Niski kontrast plakietki: 1.4.3. Niewidoczny fokus: 2.4.7. Przycisk bez nazwy: 4.1.2. Fokus nie wraca po modalu: 2.4.3. Toast nieogłaszany: 4.1.3. Błąd bez opisu: 3.3.1.'],
['Określ wagę problemów w raporcie.','Waga zależy od blokady zadania, nie od liczby elementów.','Krytyczny: użytkownik nie może wykonać zadania, np. brak klawiatury w zapisie. Poważny: zadanie jest bardzo trudne, np. fokus ginie po modalu. Umiarkowany: utrudnienie z obejściem. Drobny: niewielka niespójność.'],
['Opisz znane ograniczenia raportu.','Uczciwy raport mówi też, czego nie sprawdzono.','Zakres: cztery ekrany. Nie sprawdzono eksportu PDF ani aplikacji mobilnej. Wykres ma alternatywę dopiero w rozdziale 20. Czytnik: NVDA z Firefoksem; VoiceOver nie był sprawdzony. Raport dotyczy podanej wersji aplikacji.'],
['Dodaj testy dostępności do CI.','Każdy etap wymaga innego środowiska.','Lint a11y i vitest-axe uruchamiaj przy każdym commicie. Playwright z axe i testami klawiatury uruchamiaj na zbudowanej aplikacji przed scaleniem. Testy ręczne nadal wykonuj okresowo i przy dużych zmianach. Zapisuj raport jako artefakt CI.'],
['Wyjaśnij, dlaczego wynik 100 w Lighthouse nie dowodzi dostępności.','Pomyśl o przeciąganiu bez alternatywy.','Wynik mówi tylko o regułach, które automat umie sprawdzić. Kanban wyłącznie na mysz, fokus znikający po akcji i niezrozumiałe teksty alternatywne mogą nadal dostać 100 punktów. Dlatego raport zawiera testy ręczne i ograniczenia.'],
['Sformułuj kryterium akceptacji audytu.','Ma obejmować ekrany, metody i wynik.','Cztery ekrany mają przebieg axe, klawiatury i czytnika; otwarte nie są żadne problemy krytyczne ani poważne; każda pozycja ma kryterium WCAG; testy axe i test powrotu fokusu przechodzą; raport opisuje ograniczenia.'],
];
const concepts=[
['Jaką część problemów dostępności wykrywają narzędzia automatyczne?','Około 30–40%','Niemal wszystkie','Około 90%','Mniej niż 1%','Reszta wymaga ludzkiej oceny.'],
['Czego automat nie sprawdzi?','Czy tekst alternatywny jest sensowny','Czy obraz ma atrybut alt','Czy pole ma etykietę','Czy role są poprawne','Składnia to nie to samo co znaczenie.'],
['Z czego składa się pełny audyt?','Automat, klawiatura, czytnik ekranu i lista kontrolna','Sam Lighthouse','Sam axe','Opinia projektanta','Różne metody wykrywają różne klasy problemów.'],
['Dlaczego jsdom nie sprawdzi kontrastu?','Nie oblicza pełnych stylów i nie rysuje strony','Bo axe nie ma tej reguły','Bo React blokuje style','Bo Vitest wyłącza CSS na stałe','Kontrast wymaga realnej przeglądarki.'],
['Co robi vitest-axe?','Uruchamia axe-core na wyrenderowanym DOM w teście','Uruchamia przeglądarkę','Zastępuje RTL','Naprawia błędy automatycznie','Dostajesz wynik na poziomie komponentu.'],
['Co robi @axe-core/playwright?','Sprawdza strony w prawdziwej przeglądarce','Generuje komponenty','Działa tylko w jsdom','Mierzy LCP','Dzięki temu widzi też kontrast i style.'],
['Co oznacza withTags(["wcag2aa"])?','Ograniczenie reguł do tagów wskazanego poziomu','Włączenie wszystkich reguł','Wyłączenie axe','Wymuszenie trybu ciemnego','Tagi pozwalają wybrać zestaw reguł.'],
['Co zawiera wynik axe w violations?','Reguły naruszone wraz z elementami','Wszystkie sprawdzone reguły','Tylko kod błędu HTTP','Wyłącznie liczbę punktów','Dla każdej reguły masz id, opis i węzły.'],
['Co oznacza pole incomplete w wyniku axe?','Automat nie umiał rozstrzygnąć i potrzebny jest człowiek','Test się nie skończył','Strona nie załadowała się','Reguła jest przestarzała','Nierozstrzygnięty wynik nie jest zaliczeniem.'],
['Czy Lighthouse 100 oznacza zgodność z WCAG?','Nie','Tak','Tak, dla poziomu A','Tak, jeśli działa w CI','To tylko brak wykrywalnych problemów.'],
['Co jest celem listy kontrolnej WCAG AA w audycie?','Objęcie kryteriów, których automat nie sprawdza','Podniesienie wyniku Lighthouse','Zastąpienie testów','Zastąpienie czytnika','Lista porządkuje ręczne sprawdzanie.'],
['Co sprawdza kryterium 2.4.3?','Kolejność fokusu','Kontrast','Napisy','Język strony','Kolejność musi zachowywać sens i obsługę.'],
['Co sprawdza kryterium 2.4.7?','Widoczny fokus','Kontrast tekstu','Etykiety','Kolejność nagłówków','Użytkownik musi widzieć, gdzie jest fokus.'],
['Co sprawdza kryterium 4.1.2?','Nazwę, rolę i wartość','Kontrast','Obsługę klawiatury','Tytuł strony','Technologie wspomagające potrzebują tych danych.'],
['Co sprawdza kryterium 1.4.3?','Minimalny kontrast','Tekst alternatywny','Pułapki klawiaturowe','Czas sesji','To kryterium z progami 4,5:1 i 3:1.'],
['Co sprawdza kryterium 1.1.1?','Alternatywę tekstową','Kolejność fokusu','Język strony','Etykiety pól','Treści nietekstowe wymagają alternatywy.'],
['Co sprawdza kryterium 2.1.1?','Obsługę klawiaturą','Kontrast','Nazwy linków','Tytuł strony','Wszystkie funkcje muszą działać z klawiatury.'],
['Co sprawdza kryterium 2.1.2?','Brak pułapek klawiaturowych','Kontrast','Cel dotyku','Napisy','Fokus nie może utknąć bez wyjścia.'],
['Czy pułapka fokusu w modalu narusza 2.1.2?','Nie, o ile Escape lub przycisk pozwala wyjść','Tak, zawsze','Tak, jeśli modal ma pola','Tak, na poziomie AA','Celowe zamknięcie fokusu w modalu jest poprawne, gdy jest wyjście.'],
['Dokąd powinien iść fokus po otwarciu dialogu?','Do dialogu: na pierwszy sensowny element','Na początek strony','Na tło pod dialogiem','Nigdzie','Użytkownik musi trafić do nowej warstwy.'],
['Dokąd wraca fokus po zamknięciu dialogu?','Do przycisku, który go otworzył','Na body','Na pierwszy link','Na nagłówek strony','Inaczej użytkownik traci pozycję.'],
['Jak nadać dialogowi nazwę?','aria-labelledby do nagłówka','title na body','alt','placeholder','Czytnik ogłasza nazwę po wejściu do dialogu.'],
['Co oznacza aria-modal="true"?','Informuje, że treść poza dialogiem jest niedostępna','Blokuje fokus automatycznie','Ukrywa dialog','Włącza Escape','To tylko opis: pułapkę fokusu trzeba zapewnić osobno lub użyć natywnego dialog.'],
['Czego nie zastąpi test RTL dialogu?','Sprawdzenia realnego odczytu przez czytnik','Sprawdzenia roli','Sprawdzenia fokusu','Sprawdzenia nazwy','jsdom symuluje drzewo, lecz nie sposób czytania.'],
['Który sposób testowania najlepiej znajdzie Kanban tylko na mysz?','Ręczne przejście klawiaturą','Sam axe','tsc','Sprawdzenie koloru','Automat nie wie, że da się tylko przeciągać.'],
['Co jest głównym błędem listy ekranów bez stanów?','Pomija stany błędu, ładowania i otwarte warstwy','Jest za krótka z definicji','Wymaga testów E2E','Nie mieści się w CI','Problem często istnieje tylko w konkretnym stanie.'],
['Kiedy sprawdzisz dialog axe w teście komponentu?','Po otwarciu, gdy jest w DOM','Przed otwarciem','Tylko na produkcji','Tylko w Lighthouse','Reguły działają na obecnym DOM.'],
['Co wpisać przy pozycji raportu?','Ekran, kroki, kryterium, wagę i stan naprawy','Sam skrót „błąd”','Nazwisko autora','Wynik wydajności','Pozycja ma dać się sprawdzić ponownie.'],
['Po co raport opisuje ograniczenia?','Mówi uczciwie, czego nie sprawdzono','Żeby był dłuższy','Żeby obniżyć wymagania prawne','Tylko dla marketingu','Bez tego zero problemów wyglądałoby na pełną gwarancję.'],
['Co jest właściwą reakcją na reguły wyłączone w teście?','Zapisać powód, np. brak stylów w jsdom','Wyłączać wszystko, co przeszkadza','Ukryć to przed zespołem','Nigdy nie zapisywać','Każde wyłączenie to znane ograniczenie.'],
['Jak traktować test axe w CI?','Jako konieczny, ale niewystarczający','Jako dowód zgodności','Jako zamiennik czytnika','Jako opcjonalny żart','Tak samo jak testy jednostkowe nie dowodzą całości.'],
['Co jest najlepszym zakresem pierwszego audytu?','Główne ścieżki użytkownika i ich stany','Jedna strona logowania','Każdy komponent bez ekranów','Tylko strona główna','Liczy się możliwość wykonania zadań.'],
['Co oznacza „problem krytyczny” w raporcie?','Użytkownik nie może wykonać zadania','Występuje na wielu elementach','Jest łatwy do naprawy','Pochodzi z axe','Waga zależy od skutku, nie od narzędzia.'],
['Czy problem z kontrastem na 100 elementach to 100 problemów?','Zwykle jedna przyczyna do naprawy w tokenie','Zawsze 100 oddzielnych zadań','Nie jest to problem','Tylko jeden element ma znaczenie','Pogrupowanie po przyczynie ułatwia naprawę.'],
['Kto powinien wziąć udział w ocenie, gdy to możliwe?','Osoby korzystające z technologii wspomagających','Tylko programista','Tylko dział prawny','Nikt, wystarczy automat','Prawdziwe użycie odsłania to, czego nie widzi lista.'],
['Co oznacza, że raport dotyczy wersji?','Wynik jest ważny dla sprawdzonego wydania i zakresu','Że nigdy nie wygasa','Że dotyczy wszystkich przyszłych zmian','Że nie trzeba testów','Regresje pojawiają się z nowymi komponentami.'],
['Które wyłączenie reguły jest podejrzane?','Wyłączenie image-alt, bo „przeszkadza”','color-contrast w jsdom z opisem','region przy teście fragmentu','Żadne nie wymaga opisu','Reguły wyłączamy z powodu ograniczenia środowiska, nie wygody.'],
['Dlaczego region wyłączamy w teście fragmentu?','Fragment nie jest całą stroną z punktami orientacyjnymi','Bo reguła nie istnieje','Bo jsdom nie zna main','Bo axe działa wtedy szybciej','Tę regułę sprawdzaj na całych ekranach.'],
['Co powinien sprawdzić test klawiatury w dialogu?','Fokus w polu, Tab, Escape i powrót','Tylko widoczność','Tylko kolor','Tylko nazwę pliku','Te zachowania wymagają testu, a axe ich nie ocenia.'],
['Co wybrać dla pola z błędem po wysłaniu?','Fokus na pierwszym polu z błędem i opis przez aria-describedby','Tylko czerwony kolor','Alert przeglądarki','Wyczyszczenie formularza','Użytkownik musi wiedzieć, co poprawić.'],
['Dlaczego role="alert" na stale obecnym p działa w formularzu?','Zmiana tekstu w istniejącym regionie zostaje ogłoszona','Bo element jest pusty','Bo alert blokuje wysłanie','Bo tak działa HTML5 walidacja','Region istnieje przed błędem.'],
['Co robi noValidate na formularzu?','Wyłącza dymki przeglądarki, by użyć własnej walidacji','Wyłącza wysyłanie','Usuwa błędy z DOM','Wyłącza aria-invalid','Sama walidacja i komunikaty należą wtedy do aplikacji.'],
['Czemu warto dodać aria-label do form?','Formularz z nazwą staje się punktem orientacyjnym','To wymóg HTML','Bez tego nie wyśle danych','Aby użyć onSubmit','Rola form jest ujawniana czytnikom po nadaniu nazwy.'],
['Co powinien zawierać test E2E zamiast sleepów?','Oczekiwania na stan, np. toBeFocused','waitForTimeout(5000)','Zrzut ekranu','Stałe czekanie na sieć','Asercje z ponawianiem dają stabilne testy.'],
['Co jest dobrym artefaktem audytu w CI?','Raport z listą naruszeń','Sam komunikat OK','Wynik npm audit','Zrzut pamięci serwera','Artefakt pozwala obejrzeć wynik po czasie.'],
['Jaka kolejność działań jest rozsądna?','Lint i testy szybkie, potem przeglądarka, potem przegląd ręczny','Tylko raz przed wydaniem','Tylko Lighthouse na produkcji','Najpierw raport, potem kod','Tanie sprawdzenia dają szybką informację, droższe wchodzą później.'],
['Co oznacza pokrycie „ekran × metoda” w raporcie?','Dla każdego ekranu wykonano każdą wymaganą metodę','Liczbę linii kodu','Procent testów','Czas ładowania','Macierz odsłania, czego jeszcze nie sprawdzono.'],
['Co jest błędem w tytule raportu „Aplikacja w pełni dostępna”?','Nie można tego udowodnić audytem częściowym','Jest za krótki','Powinien być po angielsku','Nie zawiera numeru wersji, a to jedyny błąd','Raport opisuje zakres, metodę i wynik.'],
['Co z ekranem niedostępnym dla testera, np. eksportem PDF?','Oznaczyć jako poza zakresem','Pominąć bez słowa','Uznać za zgodny','Usunąć funkcję automatycznie','Jawny zakres pozwala zaplanować kolejny etap.'],
['Co sprawdzić na mobilnym czytniku?','Gesty TalkBack lub VoiceOver i rozmiar celów','Tylko kontrast','Tylko typy TS','Tylko Lighthouse','Urządzenia dotykowe mają własny sposób poruszania się.'],
['Co jest prawdą o wynikach dwóch narzędzi?','Mogą się różnić, bo mają różne zestawy reguł i założenia','Zawsze są identyczne','Zawsze wygrywa wyższy wynik','Różnica oznacza błąd narzędzia','Różnice to powód do analizy, a nie do wyboru korzystniejszego.'],
['Czym jest regresja dostępności?','Wcześniej działające zachowanie zepsute nową zmianą','Spadek wyniku SEO','Starsza wersja przeglądarki','Wycofana reguła WCAG','Temu właśnie zapobiegają testy zapisane po audycie.'],
['Jak zapisać naprawę w raporcie?','Oznaczyć fixed i podać test lub sposób sprawdzenia','Usunąć pozycję bez śladu','Napisać „działa”','Zmienić wagę na drobną','Dowód naprawy pozwala powtórzyć sprawdzenie.'],
['Jak traktować problem w bibliotece zewnętrznej?','Zgłosić, obejść i wpisać do raportu jako znany','Nie dotyczy aplikacji','Zignorować, skoro to nie Twój kod','Usunąć wiersz raportu','Dla użytkownika nie ma znaczenia, skąd pochodzi błąd.'],
['Co jest ważne przy kolejności nazw w tagach axe?','Tag wcag143 oznacza 1.4.3, a wcag2aa oznacza poziom','Każdy tag to kryterium','Tagi są losowe','Tag to nazwa elementu','Trzeba rozróżnić oba rodzaje tagów.'],
['Co oznacza impact w naruszeniu axe?','Szacunkową wagę reguły','Czas renderowania','Liczbę testów','Priorytet w backlogu ustalony przez zespół','To punkt wyjścia, a zespół ocenia skutek na ekranie.'],
['Kiedy audyt warto powtórzyć?','Przy dużych zmianach i okresowo','Nigdy','Tylko po zmianie logo','Co minutę','Raport szybko się starzeje wraz z produktem.'],
['Co jest głównym celem pisania testów po audycie?','Zapobieganie powrotowi naprawionych problemów','Podniesienie liczby testów','Zastąpienie kolejnych audytów','Wyłączenie lintera','Test utrwala naprawę tam, gdzie da się ją sprawdzić automatycznie.'],
['Co przetestować po dodaniu nowego motywu kolorystycznego?','Kontrast tokenów w prawdziwej przeglądarce dla każdego motywu','Tylko motyw domyślny','Tylko typy tokenów','Nic, kolory są dekoracją','Każdy motyw ma własne pary kolorów i własne ryzyko 1.4.3.'],
['Który test jest najcenniejszy dla modala w DevDesk?','E2E: fokus w polu, Escape i powrót fokusu','Migawka HTML','Sprawdzenie klasy CSS','Liczba przycisków na stronie','Zachowanie jest głównym ryzykiem tego komponentu.'],
];
const header=String.raw`import {canSignOff,criterionFromTag,fromAxe,missingRuns,openFindings,renderReport,type Finding,type Report} from "./auditModel";
const finding=(over:Partial<Finding>={}):Finding=>({screen:"/tickets",criterion:"1.4.3",summary:"Kontrast",method:"axe",severity:"serious",fixed:false,...over});
const runs=(screen:string,methods:("axe"|"keyboard"|"screenReader")[])=>methods.map(method=>({screen,method}));
const full:Report={screens:["/tickets"],runs:runs("/tickets",["axe","keyboard","screenReader"]),findings:[],limitations:["Bez eksportu PDF"]};`;
const probeRows=[
['Jakie kryterium opisuje tag wcag143?','criterionFromTag("wcag143")','1.4.3','Cyfry oddzielają zasadę, wytyczną i kryterium.',['14.3','wcag143','brak']],
['Jakie kryterium opisuje tag wcag412?','criterionFromTag("wcag412")','4.1.2','To nazwa, rola i wartość.',['41.2','wcag412','brak']],
['Jakie kryterium opisuje tag wcag2411?','criterionFromTag("wcag2411")','2.4.11','Ostatni człon może mieć dwie cyfry.',['24.11','2.41.1','brak']],
['Co zwróci funkcja dla tagu wcag2aa?','criterionFromTag("wcag2aa")','null','To poziom, a nie kryterium.',['2','2.0','wcag2aa']],
['Co zwróci funkcja dla tagu best-practice?','criterionFromTag("best-practice")','null','Dobra praktyka nie jest kryterium WCAG.',['best','0.0.0','pusty tekst']],
['Jakie kryterium dostanie naruszenie label?','fromAxe("/a",[{id:"label",impact:"critical",help:"Etykiety",tags:["wcag2a","wcag412"],nodes:[{}]}])[0].criterion','4.1.2','Pierwszy tag poziomu jest pomijany.',['2.0.0','poza WCAG','wcag2a']],
['Jakie kryterium dostanie reguła bez tagu WCAG?','fromAxe("/a",[{id:"region",help:"Regiony",tags:["best-practice"],nodes:[{}]}])[0].criterion','poza WCAG','Takiej pozycji nie można przypisać do kryterium.',['best-practice','1.1.1','null']],
['Jaka waga bez impact?','fromAxe("/a",[{id:"x",help:"y",tags:[],nodes:[]}])[0].severity','moderate','Brak wagi ma domyślną wartość.',['critical','minor','null']],
['Jaki jest końcowy zapis liczby elementów?','fromAxe("/a",[{id:"x",help:"y",tags:[],nodes:[{},{},{}]}])[0].summary','x: y (3)','Raport łączy id, opis i liczbę węzłów.',['x: y','x (3)','3']],
['Ile braków ma ekran po samym axe?','missingRuns({screens:["/a"],runs:runs("/a",["axe"])}).length',2,'Brakuje klawiatury i czytnika.'],
['Ile braków ma ekran bez przebiegów?','missingRuns({screens:["/a"],runs:[]}).length',3,'Wymagane są trzy metody.'],
['Ile braków mają dwa ekrany bez przebiegów?','missingRuns({screens:["/a","/b"],runs:[]}).length',6,'Macierz to ekrany razy metody.'],
['Ile braków ma kompletny ekran?','missingRuns(full).length',0,'Wszystkie metody wykonano.'],
['Jaki jest pierwszy brak po samym axe?','missingRuns({screens:["/a"],runs:runs("/a",["axe"])})[0]','/a:keyboard','Kolejność wynika z listy wymaganych metod.',['/a:axe','/a:screenReader','brak']],
['Czy przebieg innego ekranu zalicza metodę?','missingRuns({screens:["/a"],runs:runs("/b",["axe","keyboard","screenReader"])}).length',3,'Przebieg jest przypisany do ekranu.'],
['Czy kompletny raport można podpisać?','canSignOff(full)',true,'Są przebiegi, ograniczenia i brak poważnych problemów.'],
['Czy raport bez czytnika można podpisać?','canSignOff({...full,runs:runs("/tickets",["axe","keyboard"])})',false,'Automat i klawiatura nie zastępują czytnika.'],
['Czy raport bez ograniczeń można podpisać?','canSignOff({...full,limitations:[]})',false,'Raport musi mówić, czego nie objął.'],
['Czy otwarty problem poważny blokuje podpis?','canSignOff({...full,findings:[finding()]})',false,'Waga serious blokuje podpis.'],
['Czy otwarty problem krytyczny blokuje podpis?','canSignOff({...full,findings:[finding({severity:"critical"})]})',false,'Użytkownik nie może wykonać zadania.'],
['Czy naprawiony problem poważny blokuje podpis?','canSignOff({...full,findings:[finding({fixed:true})]})',true,'Liczą się tylko otwarte pozycje.'],
['Czy otwarty problem drobny blokuje podpis?','canSignOff({...full,findings:[finding({severity:"minor"})]})',true,'Drobne pozycje zostają w raporcie, ale nie blokują.'],
['Czy otwarty problem umiarkowany blokuje podpis?','canSignOff({...full,findings:[finding({severity:"moderate"})]})',true,'Blokują tylko dwie najwyższe wagi.'],
['Ile otwartych pozycji zwraca lista z jedną naprawioną?','openFindings([finding(),finding({fixed:true})]).length',1,'Naprawione są odfiltrowane.'],
['Która pozycja jest pierwsza po sortowaniu?','openFindings([finding({severity:"minor"}),finding({severity:"critical"})])[0].severity','critical','Sortowanie według wagi.',['minor','serious','moderate']],
['Jak sortowane są pozycje tej samej wagi?','openFindings([finding({screen:"/b"}),finding({screen:"/a"})])[0].screen','/a','Remis rozstrzyga ekran.',['/b','/tickets','brak']],
['Czy sortowanie mutuje listę wejściową?','(()=>{const l=[finding({severity:"minor"}),finding({severity:"critical"})];openFindings(l);return l[0].severity;})()','minor','toSorted i filter tworzą nowe tablice.',['critical','serious','brak']],
['Ile otwartych pozycji ma pusta lista?','openFindings([]).length',0,'Pusta lista daje pusty wynik.'],
['Jak zaczyna się raport?','renderReport(full).split("\\n")[0]','Zakres: /tickets','Najpierw opisany zakres.',['Ograniczenie: Bez eksportu PDF','Brak przebiegu','brak']],
['Ile wierszy ma kompletny raport bez problemów?','renderReport(full).split("\\n").length',2,'Zakres i jedno ograniczenie.'],
['Ile wierszy dodaje jedna otwarta pozycja?','renderReport({...full,findings:[finding()]}).split("\\n").length',3,'Pozycja trafia do raportu.'],
['Czy naprawiona pozycja jest w raporcie?','renderReport({...full,findings:[finding({fixed:true})]}).includes("Kontrast")',false,'Raport pokazuje tu otwarte pozycje.'],
['Czy brak przebiegu widać w raporcie?','renderReport({...full,runs:[]}).includes("Brak przebiegu: /tickets:screenReader")',true,'Braki są jawne.'],
['Jaki jest wiersz problemu?','renderReport({...full,findings:[finding()]}).split("\\n")[1]','[serious] 1.4.3 /tickets: Kontrast','Waga, kryterium, ekran i opis.',['Kontrast','1.4.3','serious']],
['Czy poziom wcag2a jest kryterium?','criterionFromTag("wcag2a")===null',true,'Dopasowanie wymaga trzech członów cyfr.'],
['Czy zero naruszeń axe zapewnia podpis?','canSignOff({screens:["/a"],runs:runs("/a",["axe"]),findings:[],limitations:["x"]})',false,'To główna lekcja audytu.'],
['Ile pozycji powstanie z dwóch naruszeń?','fromAxe("/a",[{id:"x",help:"y",tags:[],nodes:[]},{id:"z",help:"w",tags:[],nodes:[]}]).length',2,'Jedna pozycja na regułę.'],
['Jaką metodę ma pozycja z axe?','fromAxe("/a",[{id:"x",help:"y",tags:[],nodes:[]}])[0].method','axe','Metoda pozwala później odróżnić źródło.',['keyboard','screenReader','lighthouse']],
['Czy pozycja z axe jest na początku naprawiona?','fromAxe("/a",[{id:"x",help:"y",tags:[],nodes:[]}])[0].fixed',false,'Naprawa to osobny krok.'],
['Czy pusta lista ekranów ma braki?','missingRuns({screens:[],runs:[]}).length',0,'Pusty zakres nie ma braków, więc musisz sprawdzić również sam zakres.'],
];
const project={
'src/auditModel.ts':audit,'src/AssignDialog.tsx':dialog,'src/TicketForm.tsx':form,'src/form.a11y.test.tsx':axeTest,'src/audit.test.tsx':tests,'e2e/a11y.spec.txt':playwrightAxe,'e2e/keyboard.spec.txt':playwrightKeyboard,
'README.md':'DevDesk: audyt dostępności. npm test uruchamia axe w jsdom bez kontrastu oraz testy fokusu. Scenariusze w katalogu e2e są zapisane jako tekst: wymagają działającego DevDesk, @playwright/test i @axe-core/playwright, więc nie są uruchamiane w tym projekcie. Czytnik ekranu i Lighthouse wykonaj ręcznie.\n',
};
const mutants=[
{file:'src/auditModel.ts',before:'missingRuns(report).length===0&&',after:''},
{file:'src/auditModel.ts',before:'&&report.limitations.length>0',after:''},
{file:'src/auditModel.ts',before:'||finding.severity==="serious"',after:''},
{file:'src/auditModel.ts',before:'.filter(finding=>!finding.fixed)',after:''},
{file:'src/auditModel.ts',before:'rank[a.severity]-rank[b.severity]||',after:''},
{file:'src/auditModel.ts',before:'run.screen===screen&&',after:''},
{file:'src/AssignDialog.tsx',before:'    trigger.current?.focus();\n',after:''},
{file:'src/AssignDialog.tsx',before:'useEffect(()=>{if(open)field.current?.focus();},[open]);',after:''},
{file:'src/AssignDialog.tsx',before:'event.preventDefault();last.focus();',after:''},
{file:'src/AssignDialog.tsx',before:'event.preventDefault();first.focus();',after:''},
{file:'src/TicketForm.tsx',before:'input.current?.focus();',after:''},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
