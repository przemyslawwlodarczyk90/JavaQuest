const {lesson}=require('./practice_kit.cjs');
const button=String.raw`import type {ComponentProps} from "react";

type ButtonProps=ComponentProps<"button">&{
  /** Wygląd przycisku. danger tylko dla akcji nieodwracalnych. */
  variant?:"primary"|"secondary"|"danger"|"ghost";
  /** Rozmiar: sm w tabelach, md domyślnie, lg w pustych stanach. */
  size?:"sm"|"md"|"lg";
  /** Pokazuje spinner, ustawia aria-busy i blokuje ponowne kliknięcie. */
  loading?:boolean;
};

/**
 * Podstawowy przycisk DevDesk.
 *
 * Używaj zamiast gołego button: dba o type="button", stan ładowania i style wariantów.
 */
export function Button({variant="primary",size="md",loading=false,disabled,children,...rest}:ButtonProps){
  return (
    <button
      type="button"
      className={"btn btn--"+variant+" btn--"+size}
      disabled={disabled||loading}
      aria-busy={loading||undefined}
      {...rest}
    >
      {loading&&<span className="btn__spinner" aria-hidden="true"/>}
      {children}
    </button>
  );
}`;
const stories=String.raw`import type {Meta,StoryObj} from "@storybook/react-vite";
import {fn} from "storybook/test";
import {Button} from "./Button";

const meta={
  title:"DevDesk/Button",
  component:Button,
  tags:["autodocs"],
  parameters:{layout:"centered"},
  args:{children:"Zapisz",variant:"primary",size:"md",onClick:fn()},
  argTypes:{onClick:{table:{category:"Zdarzenia"}}},
} satisfies Meta<typeof Button>;
export default meta;

type Story=StoryObj<typeof meta>;

export const Primary:Story={};
export const Danger:Story={args:{variant:"danger",children:"Usuń ticket"}};
export const Loading:Story={args:{loading:true,children:"Zapisywanie…"}};
export const LongLabel:Story={args:{children:"Przypisz ticket do osoby z najmniejszą liczbą otwartych zgłoszeń w tym zespole"}};
export const AllVariants:Story={
  render:args=>(
    <div style={{display:"flex",gap:8}}>
      {(["primary","secondary","danger","ghost"] as const).map(variant=>(
        <Button key={variant} {...args} variant={variant}>{variant}</Button>
      ))}
    </div>
  ),
};`;
const preferences=String.raw`import {createContext,useContext,type ReactNode} from "react";

const PreferencesContext=createContext<{density:string}|null>(null);

export function PreferencesProvider({children}:{children:ReactNode}){
  return <PreferencesContext.Provider value={{density:"comfortable"}}>{children}</PreferencesContext.Provider>;
}

export function usePreferences(){
  const value=useContext(PreferencesContext);
  if(!value)throw Error("Brak PreferencesProvider");
  return value;
}`;
const preview=String.raw`import type {Preview} from "@storybook/react-vite";
import {PreferencesProvider} from "./preferences";

const preview:Preview={
  globalTypes:{
    theme:{
      description:"Motyw DevDesk",
      toolbar:{icon:"mirror",items:[{value:"light",title:"Jasny"},{value:"dark",title:"Ciemny"}]},
    },
  },
  initialGlobals:{theme:"light"},
  decorators:[
    (Story,context)=>(
      <div data-testid="theme" data-theme={context.globals.theme as string} style={{padding:16}}>
        <PreferencesProvider>
          <Story/>
        </PreferencesProvider>
      </div>
    ),
  ],
  parameters:{
    controls:{matchers:{color:/(background|color)$/i}},
    a11y:{test:"error"},
  },
};

export default preview;`;
const table=String.raw`import {useQuery} from "@tanstack/react-query";
import {usePreferences} from "./preferences";

export type TicketRow={id:number;title:string;assignee:string|null};

async function loadTickets():Promise<TicketRow[]>{
  const response=await fetch(new URL("/api/tickets",window.location.href));
  if(!response.ok)throw Error("Serwer zwrócił "+response.status);
  return response.json();
}

export function TicketTablePage(){
  const {density}=usePreferences();
  const query=useQuery({queryKey:["tickets"],queryFn:loadTickets});
  if(query.isPending)return <p role="status">Ładowanie ticketów…</p>;
  if(query.isError)return <p role="alert">Nie udało się pobrać ticketów</p>;
  if(query.data.length===0)return <p>Brak ticketów</p>;
  return (
    <table data-density={density}>
      <thead><tr><th scope="col">Tytuł</th><th scope="col">Przypisany</th></tr></thead>
      <tbody>
        {query.data.map(ticket=>(
          <tr key={ticket.id}><td>{ticket.title}</td><td>{ticket.assignee??"Nieprzypisany"}</td></tr>
        ))}
      </tbody>
    </table>
  );
}`;
const tableStories=String.raw`import type {Meta,StoryObj} from "@storybook/react-vite";
import {QueryClient,QueryClientProvider} from "@tanstack/react-query";
import {http,HttpResponse,delay} from "msw";
import {TicketTablePage,type TicketRow} from "./TicketTablePage";

export const sampleTickets:TicketRow[]=[
  {id:1,title:"Awaria logowania",assignee:"Ala"},
  {id:2,title:"Bardzo długi tytuł ticketu ".repeat(8).trim(),assignee:null},
];

const meta={
  title:"DevDesk/TicketTablePage",
  component:TicketTablePage,
  decorators:[
    Story=>(
      <QueryClientProvider client={new QueryClient({defaultOptions:{queries:{retry:false}}})}>
        <Story/>
      </QueryClientProvider>
    ),
  ],
  parameters:{layout:"fullscreen"},
} satisfies Meta<typeof TicketTablePage>;
export default meta;

type Story=StoryObj<typeof meta>;

export const WithData:Story={parameters:{msw:{handlers:[http.get("*/api/tickets",()=>HttpResponse.json(sampleTickets))]}}};
export const Empty:Story={parameters:{msw:{handlers:[http.get("*/api/tickets",()=>HttpResponse.json([]))]}}};
export const Loading:Story={parameters:{msw:{handlers:[http.get("*/api/tickets",async()=>{await delay("infinite");return HttpResponse.json([]);})]}}};
export const ServerError:Story={parameters:{msw:{handlers:[http.get("*/api/tickets",()=>HttpResponse.json({message:"Błąd serwera"},{status:500}))]}}};`;
const catalog=String.raw`export type ComponentEntry={
  name:string;
  description?:string;
  stories:readonly string[];
  requiredStates:readonly string[];
  usesData:boolean;
};

const DATA_STATES=["Loading","Empty","ServerError"];

export function missingStories(entry:ComponentEntry){
  const required=[...entry.requiredStates,...(entry.usesData?DATA_STATES:[])];
  return required.filter(state=>!entry.stories.includes(state));
}

export function catalogProblems(entries:readonly ComponentEntry[]){
  return entries.flatMap(entry=>[
    ...(entry.description?.trim()?[]:[entry.name+": brak opisu"]),
    ...(entry.stories.length?[]:[entry.name+": brak historii"]),
    ...missingStories(entry).map(state=>entry.name+": brak stanu "+state),
  ]);
}

// Wynik zależy od projektu, a nie od mody: mała aplikacja nie musi płacić kosztu katalogu.
export function storybookWorthIt({sharedComponents,consumers,hardStates}:{sharedComponents:number;consumers:number;hardStates:boolean}){
  if(sharedComponents===0)return "nie";
  if(sharedComponents>=15||consumers>=2)return "tak";
  return hardStates?"lekko":"nie";
}`;
const lab=String.raw`import {render} from "@testing-library/react";
import {composeStories,setProjectAnnotations} from "@storybook/react";
import preview from "./preview";
import * as buttonStories from "./Button.stories";

setProjectAnnotations(preview);

export const buttons=composeStories(buttonStories);

export function observeButton(name:keyof typeof buttons){
  const Story=buttons[name] as React.ComponentType;
  const view=render(<Story/>);
  try{
    const button=view.container.querySelector("button")!;
    return {
      text:button.textContent,
      className:button.className,
      disabled:button.disabled,
      busy:button.getAttribute("aria-busy")??"brak",
      count:view.container.querySelectorAll("button").length,
      theme:view.getByTestId("theme").getAttribute("data-theme"),
    };
  }finally{view.unmount();}
}`;
const tests=String.raw`import {afterAll,afterEach,beforeAll,expect,test} from "vitest";
import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {composeStories,composeStory,setProjectAnnotations} from "@storybook/react";
import {setupServer} from "msw/node";
import preview from "./preview";
import * as buttonStories from "./Button.stories";
import * as tableStories from "./TicketTablePage.stories";
import {catalogProblems,missingStories,storybookWorthIt} from "./catalog";

setProjectAnnotations(preview);
const {Primary,Danger,Loading,AllVariants}=composeStories(buttonStories);
const table=composeStories(tableStories);
const server=setupServer();
beforeAll(()=>server.listen({onUnhandledFrame:"error"}));
afterEach(()=>server.resetHandlers());
afterAll(()=>server.close());

test("historia dziedziczy args z meta i wywołuje onClick",async()=>{
  render(<Primary/>);
  await userEvent.setup().click(screen.getByRole("button",{name:"Zapisz"}));
  expect(Primary.args.onClick).toHaveBeenCalledOnce();
});

test("historia nadpisuje tylko to, czym się różni",()=>{
  render(<Danger/>);
  const button=screen.getByRole("button",{name:"Usuń ticket"});
  expect(button).toHaveClass("btn--danger");
  expect(button).toHaveClass("btn--md");
});

test("stan ładowania blokuje przycisk i ukrywa spinner przed czytnikiem",()=>{
  render(<Loading/>);
  const button=screen.getByRole("button",{name:"Zapisywanie…"});
  expect(button).toBeDisabled();
  expect(button).toHaveAttribute("aria-busy","true");
  expect(button.querySelector(".btn__spinner")).toHaveAttribute("aria-hidden","true");
});

test("render pokazuje wszystkie warianty",()=>{
  render(<AllVariants/>);
  expect(screen.getAllByRole("button").map(button=>button.textContent)).toEqual(["primary","secondary","danger","ghost"]);
});

test("dekorator z preview dodaje motyw i Provider",()=>{
  render(<Primary/>);
  expect(screen.getByTestId("theme")).toHaveAttribute("data-theme","light");
  const Dark=composeStory(buttonStories.Primary,buttonStories.default,{initialGlobals:{theme:"dark"}});
  render(<Dark/>);
  expect(screen.getAllByTestId("theme")[1]).toHaveAttribute("data-theme","dark");
});

test("historia danych: wiersze i brak przypisania",async()=>{
  server.use(...table.WithData.parameters.msw.handlers);
  render(<table.WithData/>);
  expect(await screen.findByRole("cell",{name:"Awaria logowania"})).toBeVisible();
  expect(screen.getByRole("cell",{name:"Nieprzypisany"})).toBeVisible();
  expect(screen.getByRole("table")).toHaveAttribute("data-density","comfortable");
});

test("historia pusta i błąd serwera",async()=>{
  server.use(...table.Empty.parameters.msw.handlers);
  const view=render(<table.Empty/>);
  expect(await screen.findByText("Brak ticketów")).toBeVisible();
  view.unmount();
  server.resetHandlers(...table.ServerError.parameters.msw.handlers);
  render(<table.ServerError/>);
  expect(await screen.findByRole("alert")).toHaveTextContent("Nie udało się");
});

test("historia ładowania nie kończy żądania",async()=>{
  server.use(...table.Loading.parameters.msw.handlers);
  render(<table.Loading/>);
  expect(await screen.findByRole("status")).toHaveTextContent("Ładowanie");
});

test("model katalogu wskazuje brakujące stany i opisy",()=>{
  const entry={name:"Tabela",stories:["Loading"],requiredStates:["Default"],usesData:true};
  expect(missingStories(entry)).toEqual(["Default","Empty","ServerError"]);
  expect(catalogProblems([{...entry,description:" "}])).toContain("Tabela: brak opisu");
  expect(catalogProblems([{name:"X",description:"Opis",stories:[],requiredStates:[],usesData:false}])).toEqual(["X: brak historii"]);
  expect(storybookWorthIt({sharedComponents:0,consumers:1,hardStates:true})).toBe("nie");
  expect(storybookWorthIt({sharedComponents:20,consumers:1,hardStates:false})).toBe("tak");
  expect(storybookWorthIt({sharedComponents:5,consumers:1,hardStates:true})).toBe("lekko");
});`;
const mdx=String.raw`{/* Button.mdx */}
import {Meta,Canvas,Controls} from "@storybook/addon-docs/blocks";
import * as ButtonStories from "./Button.stories";

<Meta of={ButtonStories}/>

# Przyciski: zasady użycia

Wariantu danger używaj wyłącznie do akcji nieodwracalnych.

<Canvas of={ButtonStories.Danger}/>
<Controls/>

Przycisk z samą ikoną musi mieć aria-label.`;
const exercises=[
['Dodaj komentarze JSDoc do Button.','Opis komponentu i każdego propa trafia do dokumentacji.',button],
['Dopasuj Button do wymagań stanu ładowania.','disabled, aria-busy i ukryty spinner.',button],
['Napisz historie z args zamiast JSX.','Controls zmienia args na żywo.',stories],
['Odziedzicz wspólne args z meta.','Każda historia zmienia tylko to, czym się różni.',stories],
['Rejestruj kliknięcia przez fn().','Wywołanie zobaczysz w panelu Actions i w teście.',stories],
['Użyj satisfies Meta<typeof Button>.','Sprawdza args, a nie gubi typu meta.',stories],
['Dodaj historię AllVariants z własnym render.','Przekaż args, a nadpisz tylko wariant.',stories],
['Dodaj historię długiej etykiety.','To stan trudny, a nie „ładny” przykład.',stories],
['Włącz autodocs i sprawdź tabelę props.','W TSX tabela powstaje z typów.','Tag autodocs generuje stronę z opisu JSDoc, tabeli props i historii. Sprawdź, że unia wariantów stała się listą w kontrolce select. argTypes dopisuj tylko tam, gdzie chcesz zmienić kontrolkę, opis albo kategorię, jak dla onClick.'],
['Porównaj utrzymanie dokumentacji JSX i TSX.','Skąd tabela bierze dozwolone wartości?','W TSX tabela powstaje z typów i JSDoc, więc nowy wariant pojawi się sam. W JSX trzeba go powtórzyć w argTypes lub PropTypes, a brak aktualizacji po cichu rozjedzie dokumentację z komponentem.'],
['Skonfiguruj preview z przełącznikiem motywu.','globalTypes dodaje pasek narzędzi.',preview],
['Napisz dekorator z motywem i Providerem.','Dekorator ma odtwarzać prawdziwy układ aplikacji.',preview],
['Oznacz naruszenia dostępności jako błąd historii.','Parametr a11y.test dotyczy dodatku a11y.',preview],
['Przygotuj Provider ustawień do użycia w dekoratorze.','Komponent bez Providera powinien rzucić błąd.',preferences],
['Napisz stronę tabeli z czterema stanami danych.','Ładowanie, błąd, pusto, dane.',table],
['Napisz historie danych z handlerami MSW.','Każda historia ma własną odpowiedź serwera.',tableStories],
['Dodaj dekorator z QueryClient bez ponawiania.','Bez retry:false błąd pojawi się po opóźnieniu.',tableStories],
['Dodaj dane z długim tytułem i brakiem osoby.','Historia nie powinna kłamać idealnymi danymi.',tableStories],
['Napisz stronę MDX z zasadami użycia.','Tekst łączy się z żywymi historiami.',mdx],
['Użyj composeStories w testach.','Nie powtarzaj przygotowania props.',tests],
['Podłącz adnotacje projektu do testów.','setProjectAnnotations dodaje dekoratory i parametry z preview.',lab],
['Przetestuj motyw dla globalnej wartości dark.','composeStory przyjmuje konfigurację projektu.',tests],
['Użyj handlerów historii w testach.','Parametry msw stają się danymi do server.use.',tests],
['Sprawdź w teście historię błędu i pustą.','Te same scenariusze co w dokumentacji.',tests],
['Napisz model wymaganych stanów katalogu.','Komponent z danymi ma trzy dodatkowe stany.',catalog],
['Wskaż komponenty bez opisu i historii.','Lista problemów da się sprawdzić w CI.',catalog],
['Oceń, czy Storybook opłaca się w trzech projektach.','Liczy się koszt utrzymania.','Biblioteka współdzielona przez kilka zespołów: tak. Średni projekt: lekko, tylko katalog ui/. Mały projekt z kilkoma ekranami i bez komponentów wielokrotnego użytku: nie, wystarczą testy komponentów.'],
['Dodaj Storybook do CI.','Historia, która się nie buduje, jest gorsza niż jej brak.','Uruchamiaj storybook build oraz testy composeStories przy każdym scaleniu, a statyczny wynik zapisuj jako artefakt do przeglądu. Dzięki temu dokumentacja nie rozjedzie się z kodem i każdy może obejrzeć stany bez backendu.'],
['Wypisz pułapki historii.','Dane i dekoratory.','Idealne dane ukrywają problemy układu, więc dodaj 200 znaków, brak osoby, 0 i 10 000 wyników, długie słowa oraz rolę bez uprawnień. Logika tylko w dekoratorze sprawia, że komponent działa w Storybooku, a w aplikacji nie.'],
['Sprawdź kryterium akceptacji lekcji.','Dokumentacja, stany, motyw, CI.','Każdy komponent ui/ ma opis i tabelę props; historie obejmują stany trudne; przełącznik motywu działa wszędzie; panel dostępności nie zgłasza naruszeń; storybook build i testy oparte na historiach przechodzą w CI.'],
];
const concepts=[
['Czym jest historia w Storybooku?','Nazwanym stanem komponentu','Plikiem CSS','Testem E2E','Commitem','Historia opisuje jeden konkretny przypadek.'],
['Czym są args?','Props historii zapisane jako dane','Argumenty wiersza poleceń','Zmienne CSS','Parametry serwera','Dzięki nim działa panel Controls.'],
['Co robi panel Controls?','Pozwala zmieniać args na żywo','Uruchamia testy','Zapisuje kod','Publikuje Storybook','To żywy podgląd stanów.'],
['Do czego służy argTypes?','Opisuje kontrolki, wartości i kategorie args','Zmienia typy TS','Pisze testy','Generuje backend','Uzupełnia to, czego nie da się wywnioskować.'],
['Co robi fn() ze storybook/test?','Tworzy funkcję rejestrującą wywołania','Wyłącza handlery','Uruchamia historię','Tworzy Provider','Wywołania widać w panelu Actions i w testach.'],
['Czym jest dekorator?','Komponentem opakowującym historię','Adnotacją TS','Pluginem Vite','Klasą CSS','Używany do motywu, Providerów i układu.'],
['Czym są parameters?','Ustawieniami narzędzi historii','Props komponentu','Zmiennymi env','Parametrami trasy','Np. layout czy opcje dodatków.'],
['Co robi tag autodocs?','Generuje stronę dokumentacji','Uruchamia testy','Tworzy changelog','Wyłącza kontrolki','Łączy opis, tabelę props i historie.'],
['Skąd autodocs bierze opis komponentu?','Z komentarza JSDoc','Z README','Z nazwy pliku','Z commitów','Ten sam tekst widzi IDE.'],
['Skąd tabela props bierze dane w TSX?','Z typów i komentarzy','Tylko z argTypes','Z CSS','Z testów','docgen czyta typy komponentu.'],
['Skąd tabela props bierze dane w JSX?','Z argTypes, PropTypes i analizy kodu','Z typów TS','Z package.json','Z ESLint','Trzeba utrzymywać to ręcznie.'],
['Co daje satisfies Meta<typeof Button>?','Sprawdza zgodność, zachowując dokładny typ','Rzutuje na any','Wyłącza typy','Generuje args','StoryObj<typeof meta> korzysta z zachowanego typu.'],
['Jak dziedziczą się args?','Domyślne z meta, nadpisywane w historii','Historia kasuje wszystkie','Tylko z preview','Nie dziedziczą się','Każda historia pokazuje tylko różnicę.'],
['Co zawiera plik preview?','Konfigurację wspólną dla historii','Tylko listę historii','Kod produkcyjny','Testy E2E','Dekoratory i parametry globalne.'],
['Co zawiera plik main?','Listę historii, dodatki i framework','Dekoratory','Dane MSW','Args','To konfiguracja budowania Storybooka.'],
['Do czego służy globalTypes?','Do globalnych przełączników na pasku narzędzi','Do typów TS','Do ról ARIA','Do tras','Motyw i język to typowe przykłady.'],
['Skąd dekorator czyta motyw?','Z context.globals','Z localStorage zawsze','Z props historii','Z document.title','Globals są dostępne w kontekście.'],
['Co oznacza layout:"centered"?','Wyśrodkowanie historii w podglądzie','Wyśrodkowanie tekstu','Wyłączenie CSS','Tryb pełnoekranowy','To parametr podglądu.'],
['Kiedy layout:"fullscreen"?','Dla całych stron','Dla pojedynczego przycisku','Nigdy','Tylko w testach','Strony potrzebują całej szerokości.'],
['Do czego służy msw-storybook-addon?','Daje historiom własne handlery MSW','Generuje dane losowe','Zastępuje backend produkcyjny','Mierzy wydajność','Każda historia pokazuje inny stan API.'],
['Po co retry:false w historii?','Aby błąd pojawił się od razu','Aby wyłączyć fetch','Aby przyspieszyć Vite','Aby pominąć cache w produkcji','Domyślne ponowienia opóźniają stan błędu.'],
['Dlaczego QueryClient w dekoratorze powinien być nowy?','Historie nie współdzielą wtedy cache','Bo starego nie można użyć','Bo React tego wymaga','Bo MSW tego wymaga','Współdzielony cache ukryłby stan ładowania.'],
['Co robi delay("infinite")?','Odpowiedź nigdy nie przychodzi, więc widać ładowanie','Czeka sekundę','Wyłącza MSW','Odrzuca żądanie','Stabilny sposób na historię ładowania.'],
['Co pozwala zrobić MDX?','Połączyć tekst z żywymi historiami','Tylko pisać CSS','Tylko pisać testy','Tylko pisać Markdown bez komponentów','Dla wzorców użycia i zasad.'],
['Co robi Canvas w MDX?','Pokazuje historię z kodem','Rysuje wykres','Wyłącza kontrolki','Testuje kontrast','Odnosi się do historii przez of.'],
['Co robi Meta of={...}?','Przypina stronę MDX do komponentu','Tworzy nowy Provider','Eksportuje dane','Ustawia motyw','Bez tego strona nie ma powiązania.'],
['Co robi composeStories?','Zamienia historie w komponenty z args i dekoratorami','Publikuje Storybook','Tworzy historie z testów','Uruchamia przeglądarkę','To tzw. portable stories.'],
['Co robi setProjectAnnotations?','Podłącza preview do historii poza Storybookiem','Publikuje preview','Wyłącza dekoratory','Tworzy projekt npm','Bez tego dekoratory globalne nie działają w testach.'],
['Dlaczego warto używać historii w testach?','Nie powtarzasz przygotowania danych','Testy są wtedy zbędne','Storybook staje się szybszy','Pozwala pominąć typy','Scenariusz jest w jednym miejscu.'],
['Czy historia sama jest testem?','Staje się nim dopiero z asercją, play lub testem','Tak, zawsze','Nie, nigdy','Tylko w CI','Render bez sprawdzeń mówi niewiele.'],
['Do czego służy funkcja play?','Do interakcji i asercji uruchamianych po renderze','Do odtwarzania wideo','Do startu serwera','Do budowania dokumentacji','Przenosi test interakcji do historii.'],
['Co oznacza storybook build?','Zbudowanie statycznej wersji katalogu','Uruchomienie testów','Wdrożenie aplikacji','Aktualizację pakietów','Sprawdza też, czy historie się kompilują.'],
['Czemu historia, która się nie buduje, jest zła?','Dokumentacja przestaje być wiarygodna','Bo psuje produkcję','Bo usuwa testy','Bo zwiększa paczkę aplikacji','CI powinno temu zapobiegać.'],
['Czy Storybook trafia do paczki produkcyjnej aplikacji?','Nie, o ile aplikacja nie importuje historii','Tak, zawsze','Tak, przez dekoratory','Tylko w Next','Historie są osobnym wejściem budowania.'],
['Kiedy Storybook się nie opłaca?','Mała aplikacja bez komponentów wielokrotnego użytku','Biblioteka współdzielona','Design system','Wiele trudnych stanów','Koszt utrzymania historii przewyższy zysk.'],
['Kiedy Storybook szczególnie pomaga?','Stany trudne do odtworzenia w aplikacji','Skrypty SQL','Migracje bazy','Konfiguracja Nginx','Błąd serwera czy brak danych masz od razu.'],
['Jakie dane powinna pokazać historia?','Także trudne: długie teksty i braki','Tylko idealne','Tylko puste','Tylko liczby okrągłe','Inaczej komponent wygląda lepiej niż w produkcji.'],
['Co jest błędem w dekoratorze z innymi danymi niż aplikacja?','Komponent działa tylko w Storybooku','Jest za krótki','Spowalnia testy','Nie kompiluje się','Dekorator ma odtwarzać prawdziwe Providery.'],
['Co sprawdza dodatek a11y?','Reguły axe na każdej historii','Tylko typy','Rozmiar paczki','Kontrakt API','Także tu axe nie wykryje wszystkiego.'],
['Co oznacza a11y:{test:"error"}?','Naruszenia oznaczają historię jako błędną','Wyłącza a11y','Pokazuje ostrzeżenie w konsoli produkcji','Włącza czytnik','Pozwala traktować je jako błąd testów.'],
['Czy Controls nadaje się do testów?','Nie, to narzędzie eksploracji','Tak, zastępuje testy','Tak, zapisuje asercje','Tak, w CI','Stan ustawiony ręcznie znika po odświeżeniu.'],
['Jak nazwać historie?','Według stanu: Loading, Empty, ServerError','Test1, Test2','Według autora','Według daty','Nazwa ma mówić, co pokazuje.'],
['Gdzie trzymać plik historii?','Obok komponentu','W osobnym repozytorium','W dist','W public','Łatwiej go utrzymać razem z komponentem.'],
['Co pokaże spinner w przycisku czytnikowi?','Nic: ma aria-hidden, a stan niesie aria-busy','Jego klasę','Nazwę pliku','Komunikat „spinner”','Dekoracja nie powinna być czytana.'],
['Dlaczego Button ma type="button" przed {...rest}?','Można je świadomie nadpisać, np. submit','Tak wymaga React','Rest nie działa inaczej','Przez TS','Kolejność decyduje o wygranej wartości.'],
['Jaki problem rozwiązuje katalog ui/?','Kilka wersji tego samego komponentu w projekcie','Brak backendu','Brak testów E2E','Brak CSS','Wspólne API zmniejsza niespójność.'],
['Co jest głównym kosztem Storybooka?','Utrzymanie historii i konfiguracji','Licencja','Paczka produkcyjna','Brak obsługi TS','Dlatego warto dobrać zakres.'],
['Czy Storybook działa bez backendu?','Tak, z danymi udawanymi przez MSW','Nie','Tylko w Next','Tylko z bazą danych','Projektant obejrzy stany bez środowiska.'],
['Jaki atrybut HTML ma odnosić się do nagłówka kolumny?','scope="col" na th','for na td','role="grid" zawsze','data-col','Tabela ma być dostępna także w historii.'],
['Co sprawdzisz w dokumentacji po dodaniu wariantu?','Czy pojawił się w tabeli i historii AllVariants','Czy wzrósł coverage','Czy zmienił się favicon','Nic','W TSX tabela zaktualizuje się sama.'],
['Czym są loaders w historii?','Funkcjami pobierającymi dane przed renderem','Spinnerami','Pluginami Vite','Trasami','Przydatne, gdy dane przygotowujesz asynchronicznie.'],
['Co robi tags:["!autodocs"] na historii?','Wyklucza ją z autodocs','Dodaje do autodocs','Usuwa z testów zawsze','Blokuje kontrolki','Tagi pozwalają sterować widocznością.'],
['Czy jeden Storybook może objąć kilka pakietów monorepo?','Tak, przez wzorce w stories','Nie','Tylko z Next','Tylko w JS','Przydatne przy współdzielonej bibliotece.'],
['Jaki jest pożytek z Chromatic lub podobnych narzędzi?','Porównanie wizualne historii','Testy SQL','Monitoring błędów','Sanityzacja','Historie są naturalnym wejściem testów wizualnych.'],
['Czemu nie zaczynać od całych stron?','Strony wymagają więcej danych, a korzyść daje najpierw ui/','Strony są niemożliwe','Storybook ich nie wspiera','MSW ich nie obsługuje','Zacznij od wspólnych komponentów.'],
['Co powinien zawierać opis komponentu?','Kiedy go używać, a kiedy nie','Nazwisko autora','Historię commitów','Wynik benchmarku','Dokumentacja ma pomóc w podjęciu decyzji.'],
['Jak dokumentować przycisk ikonowy?','Zasadą i przykładem aria-label w MDX','Nie dokumentować','Tylko obrazem','Tylko w README głównym','Złe i dobre przykłady są najbardziej czytelne.'],
['Co robi render w historii?','Daje własny JSX zamiast domyślnego','Wyłącza args','Uruchamia test','Zmienia meta','Przydatne przy wielu instancjach naraz.'],
['Co zrobi composeStory z initialGlobals?','Wyrenderuje historię z podanymi wartościami globalnymi','Zapisze je w localStorage','Zmieni preview dla wszystkich','Nic','Test może sprawdzić konkretny motyw.'],
['Czego nie zastąpi Storybook?','Testów integracji z prawdziwym backendem i E2E','Testów jednostkowych funkcji? Tak, wszystkich','Pisania komponentów','Całego CI','To dokumentacja i izolowany podgląd, a nie pełna aplikacja.'],
];
const header=String.raw`import {observeButton} from "./storyLab";
import {catalogProblems,missingStories,storybookWorthIt} from "./catalog";
import {sampleTickets} from "./TicketTablePage.stories";
const base={name:"T",stories:[] as string[],requiredStates:[] as string[],usesData:false};`;
const probeRows=[
['Jaki tekst ma historia Primary?','observeButton("Primary").text','Zapisz','Tekst pochodzi z args w meta.',['Usuń ticket','Primary','brak']],
['Jaki tekst ma historia Danger?','observeButton("Danger").text','Usuń ticket','Historia nadpisuje children.',['Zapisz','Danger','brak']],
['Jaką klasę wariantu ma Danger?','observeButton("Danger").className','btn btn--danger btn--md','Rozmiar jest odziedziczony z meta.',['btn btn--primary btn--md','btn btn--danger','btn--danger']],
['Jaką klasę ma Primary?','observeButton("Primary").className','btn btn--primary btn--md','Oba args pochodzą z meta.',['btn btn--md','btn btn--danger btn--md','btn']],
['Czy Loading jest zablokowany?','observeButton("Loading").disabled',true,'Stan ładowania blokuje kliknięcia.'],
['Czy Primary jest zablokowany?','observeButton("Primary").disabled',false,'Brak loading i disabled.'],
['Jakie aria-busy ma Loading?','observeButton("Loading").busy','true','Stan zajętości jest ogłaszany.',['false','brak','loading']],
['Jakie aria-busy ma Primary?','observeButton("Primary").busy','brak','Wartość undefined nie tworzy atrybutu.',['false','true','undefined']],
['Ile przycisków ma AllVariants?','observeButton("AllVariants").count',4,'Po jednym na wariant.'],
['Jaki tekst ma pierwszy przycisk AllVariants?','observeButton("AllVariants").text','primary','Render nadpisuje children.',['Zapisz','danger','ghost']],
['Jaki motyw ma historia domyślnie?','observeButton("Primary").theme','light','Z initialGlobals w preview.',['dark','brak','system']],
['Czy LongLabel zachowuje pełny tekst?','observeButton("LongLabel").text.length>50',true,'Trudny stan, a nie skrócony przykład.'],
['Ile problemów ma kompletny wpis?','catalogProblems([{...base,description:"Opis",stories:["D"]}]).length',0,'Opis i historia są obecne.'],
['Ile problemów ma wpis bez opisu i historii?','catalogProblems([base]).length',2,'Dwa osobne problemy.'],
['Jaki problem ma opis ze spacji?','catalogProblems([{...base,description:"  ",stories:["D"]}])[0]','T: brak opisu','Spacje nie są opisem.',['T: brak historii','brak','T']],
['Jaki problem ma wpis bez historii?','catalogProblems([{...base,description:"O"}])[0]','T: brak historii','Dokumentacja bez stanów to nie katalog.',['T: brak opisu','brak','T']],
['Ile stanów brakuje komponentowi z danymi bez historii?','missingStories({...base,usesData:true}).length',3,'Ładowanie, pusto, błąd.'],
['Który stan pierwszy brakuje przy danych?','missingStories({...base,usesData:true})[0]','Loading','Kolejność z listy stanów danych.',['Empty','ServerError','Default']],
['Ile stanów brakuje komponentowi bez danych?','missingStories(base).length',0,'Brak wymagań.'],
['Ile stanów brakuje po dodaniu Loading?','missingStories({...base,usesData:true,stories:["Loading"]}).length',2,'Zostały pusto i błąd.'],
['Czy wymagany stan własny jest sprawdzany?','missingStories({...base,requiredStates:["LongLabel"]})[0]','LongLabel','Komponent może mieć własne wymagania.',['Loading','Default','brak']],
['Ile problemów dają dwa złe wpisy?','catalogProblems([base,{...base,name:"U"}]).length',4,'Każdy wpis ma dwa problemy.'],
['Prefiks problemu drugiego wpisu','catalogProblems([{...base,name:"U"}])[0].split(":")[0]','U','Problem wskazuje komponent.',['T','brak','description']],
['Decyzja: brak wspólnych komponentów','storybookWorthIt({sharedComponents:0,consumers:5,hardStates:true})','nie','Bez wspólnych komponentów nie ma czego dokumentować.',['tak','lekko','brak']],
['Decyzja: 15 wspólnych komponentów','storybookWorthIt({sharedComponents:15,consumers:1,hardStates:false})','tak','Duży katalog się opłaca.',['nie','lekko','brak']],
['Decyzja: 2 odbiorców','storybookWorthIt({sharedComponents:3,consumers:2,hardStates:false})','tak','Współdzielenie uzasadnia dokumentację.',['nie','lekko','brak']],
['Decyzja: mały projekt z trudnymi stanami','storybookWorthIt({sharedComponents:5,consumers:1,hardStates:true})','lekko','Tylko wspólne ui.',['nie','tak','brak']],
['Decyzja: mały projekt bez trudnych stanów','storybookWorthIt({sharedComponents:5,consumers:1,hardStates:false})','nie','Koszt przewyższa zysk.',['lekko','tak','brak']],
['Decyzja: 14 komponentów, 1 odbiorca, trudne stany','storybookWorthIt({sharedComponents:14,consumers:1,hardStates:true})','lekko','Próg dużego katalogu jest wyżej.',['tak','nie','brak']],
['Decyzja: 14 komponentów, 1 odbiorca, proste stany','storybookWorthIt({sharedComponents:14,consumers:1,hardStates:false})','nie','Bez trudnych stanów wystarczą testy.',['tak','lekko','brak']],
['Ile ticketów ma próbka?','sampleTickets.length',2,'Jeden zwykły, jeden trudny.'],
['Czy próbka zawiera brak przypisania?','sampleTickets.some(t=>t.assignee===null)',true,'Historia nie może kłamać.'],
['Czy próbka zawiera długi tytuł?','sampleTickets.some(t=>t.title.length>100)',true,'Układ musi wytrzymać długi tekst.'],
['Ile stanów wymaga tabela z własnym Default?','missingStories({...base,usesData:true,requiredStates:["Default"]}).length',4,'Własny stan plus trzy stany danych.'],
['Czy stan jest dublowany przy wymaganym Loading?','missingStories({...base,usesData:true,requiredStates:["Loading"]}).filter(s=>s==="Loading").length',2,'Model nie usuwa powtórzeń: wymagaj danych przez usesData, a nie ręcznie.'],
['Czy opis null-owy liczy się jak brak?','catalogProblems([{...base,description:undefined,stories:["D"]}]).length',1,'Brak opisu to jeden problem.'],
['Co zwraca lista pustego katalogu?','catalogProblems([]).length',0,'Pusty katalog nie ma problemów, więc zakres sprawdzaj osobno.'],
['Czy Primary ma atrybut aria-busy z wartością false?','observeButton("Primary").busy==="false"',false,'loading||undefined usuwa atrybut.'],
['Czy klasa ghost pojawia się w Primary?','observeButton("Primary").className.includes("ghost")',false,'Ghost to inny wariant.'],
['Czy klasa md pojawia się w Loading?','observeButton("Loading").className.includes("btn--md")',true,'Rozmiar jest dziedziczony.'],
];
const project={
'src/Button.tsx':button,'src/Button.stories.tsx':stories,'src/preferences.tsx':preferences,'src/preview.tsx':preview,'src/TicketTablePage.tsx':table,'src/TicketTablePage.stories.tsx':tableStories,'src/catalog.ts':catalog,'src/Button.mdx.txt':mdx,'src/storyLab.tsx':lab,'src/stories.test.tsx':tests,
'README.md':'DevDesk: katalog komponentów. npm install, npm test. Testy używają tych samych historii przez composeStories i handlerów MSW z parametrów. Plik preview leży w src dla prostoty przykładu; w projekcie umieść go w .storybook. Pasek narzędzi, panel a11y, strony autodocs i MDX sprawdź po uruchomieniu storybook dev.\n',
};
const mutants=[
{file:'src/Button.tsx',before:'disabled={disabled||loading}',after:'disabled={disabled}'},
{file:'src/Button.tsx',before:'aria-busy={loading||undefined}',after:''},
{file:'src/Button.stories.tsx',before:'variant:"danger",',after:''},
{file:'src/preview.tsx',before:'context.globals.theme as string',after:'"light"'},
{file:'src/preview.tsx',before:'initialGlobals:{theme:"light"}',after:'initialGlobals:{theme:"dark"}'},
{file:'src/TicketTablePage.tsx',before:'if(!response.ok)throw Error("Serwer zwrócił "+response.status);',after:''},
{file:'src/TicketTablePage.tsx',before:'ticket.assignee??"Nieprzypisany"',after:'ticket.assignee'},
{file:'src/TicketTablePage.tsx',before:'if(query.data.length===0)return <p>Brak ticketów</p>;',after:''},
{file:'src/TicketTablePage.stories.tsx',before:'{defaultOptions:{queries:{retry:false}}}',after:'{}'},
{file:'src/catalog.ts',before:'...(entry.usesData?DATA_STATES:[])',after:''},
{file:'src/catalog.ts',before:'entry.description?.trim()',after:'entry.description'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
