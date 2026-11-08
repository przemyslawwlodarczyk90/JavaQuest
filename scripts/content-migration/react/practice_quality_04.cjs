const {lesson}=require('./practice_kit.cjs');
const plJson={tickets:{title:"Tickety",open_one:"{{count}} otwarty ticket",open_few:"{{count}} otwarte tickety",open_many:"{{count}} otwartych ticketów",open_other:"{{count}} otwartego ticketu",assignedTo:"Przypisany do {{name}}",assignedAt:"Przypisany do {{name}} {{date}}",help:"Przeczytaj <guide>poradnik</guide>"}};
const enJson={tickets:{title:"Tickets",open_one:"{{count}} open ticket",open_other:"{{count}} open tickets",assignedTo:"Assigned to {{name}}",assignedAt:"Assigned to {{name}} on {{date}}",help:"Read the <guide>guide</guide>"}};
const i18nFile=String.raw`import {createInstance,type i18n as I18n} from "i18next";
import {initReactI18next} from "react-i18next";
import pl from "./locales/pl/common.json";
import en from "./locales/en/common.json";

export const supported=["pl","en"] as const;
export type Language=typeof supported[number];
export const STORAGE_KEY="devdesk:lang";

export const resources={pl:{common:pl},en:{common:en}} as const;

export function pickLanguage(saved:string|null,browser:readonly string[]=[]):Language{
  const candidates=[saved,...browser].filter((value):value is string=>Boolean(value));
  for(const candidate of candidates){
    const short=candidate.split("-")[0].toLowerCase();
    if((supported as readonly string[]).includes(short))return short as Language;
  }
  return "pl";
}

export async function createI18n(language:Language="pl",onMissing?:(key:string)=>void):Promise<I18n>{
  const instance=createInstance();
  await instance.use(initReactI18next).init({
    resources,
    lng:language,
    fallbackLng:"en",
    defaultNS:"common",
    interpolation:{escapeValue:false},
    saveMissing:Boolean(onMissing),
    missingKeyHandler:onMissing?(_languages,_namespace,key)=>onMissing(key):undefined,
  });
  return instance;
}

export function connectDocument(instance:I18n,storage:Pick<Storage,"setItem">,root:{lang:string}){
  const sync=(language:string)=>{
    root.lang=language;
    storage.setItem(STORAGE_KEY,language);
  };
  sync(instance.language);
  instance.on("languageChanged",sync);
  return ()=>instance.off("languageChanged",sync);
}`;
const header=String.raw`import {useTranslation} from "react-i18next";

export function TicketsHeader({openCount,lastSync}:{openCount:number;lastSync:Date}){
  const {t,i18n}=useTranslation();
  const dateFormat=new Intl.DateTimeFormat(i18n.language,{dateStyle:"medium",timeStyle:"short",timeZone:"UTC"});
  return (
    <header>
      <h1>{t("tickets.title")}</h1>
      <p>{t("tickets.open",{count:openCount})}</p>
      <p><time dateTime={lastSync.toISOString()}>{dateFormat.format(lastSync)}</time></p>
      <select aria-label="Język / Language" value={i18n.language} onChange={event=>i18n.changeLanguage(event.currentTarget.value)}>
        <option value="pl">Polski</option>
        <option value="en">English</option>
      </select>
    </header>
  );
}`;
const help=String.raw`import {Trans} from "react-i18next";

export function HelpLine(){
  return <p><Trans i18nKey="tickets.help" components={{guide:<a href="/help"/>}}/></p>;
}`;
const format=String.raw`export function formatMoney(amount:number,currency:string,locale:string){
  return new Intl.NumberFormat(locale,{style:"currency",currency}).format(amount);
}

export function formatRelative(date:Date,locale:string,now=new Date()){
  const minutes=Math.round((date.getTime()-now.getTime())/60000);
  const relative=new Intl.RelativeTimeFormat(locale,{numeric:"auto"});
  if(Math.abs(minutes)<60)return relative.format(minutes,"minute");
  const hours=Math.round(minutes/60);
  if(Math.abs(hours)<24)return relative.format(hours,"hour");
  return relative.format(Math.round(hours/24),"day");
}

export function formatList(items:readonly string[],locale:string){
  return new Intl.ListFormat(locale,{style:"long",type:"conjunction"}).format(items);
}

export function pluralCategory(count:number,locale:string){
  return new Intl.PluralRules(locale).select(count);
}`;
const keys=String.raw`type Tree={[key:string]:string|Tree};

export function flatten(tree:Tree,prefix=""):string[]{
  return Object.entries(tree).flatMap(([key,value])=>
    typeof value==="string"?[prefix+key]:flatten(value,prefix+key+"."));
}

// Formy liczby mnogiej zależą od języka, więc porównujemy klucz bazowy, a wymagane formy liczymy z Intl.PluralRules.
const SUFFIX=/_(zero|one|two|few|many|other)$/;
const base=(key:string)=>key.replace(SUFFIX,"");

export function requiredForms(locale:string){
  return new Intl.PluralRules(locale).resolvedOptions().pluralCategories;
}

export function compareLocales(sources:Record<string,Tree>){
  const flat=Object.fromEntries(Object.entries(sources).map(([locale,tree])=>[locale,flatten(tree)]));
  const all=new Set(Object.values(flat).flat().map(base));
  const problems:string[]=[];
  for(const [locale,list] of Object.entries(flat)){
    const present=new Set(list);
    const bases=new Set(list.map(base));
    for(const key of all)if(!bases.has(key))problems.push(locale+": brak "+key);
    const plural=new Set(list.filter(key=>SUFFIX.test(key)).map(base));
    for(const key of plural)for(const form of requiredForms(locale)){
      if(!present.has(key+"_"+form))problems.push(locale+": brak "+key+"_"+form);
    }
  }
  return problems.sort();
}`;
const declaration=String.raw`// i18next.d.ts
import "i18next";
import type pl from "./locales/pl/common.json";

declare module "i18next" {
  interface CustomTypeOptions {
    defaultNS:"common";
    resources:{common:typeof pl};
  }
}`;
const tests=String.raw`import {expect,test,vi} from "vitest";
import {render,screen,waitFor} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {I18nextProvider} from "react-i18next";
import {connectDocument,createI18n,pickLanguage,STORAGE_KEY} from "./i18n";
import {TicketsHeader} from "./TicketsHeader";
import {HelpLine} from "./HelpLine";
import {compareLocales,flatten,requiredForms} from "./localeKeys";
import {formatList,formatMoney,formatRelative,pluralCategory} from "./format";
import pl from "./locales/pl/common.json";
import en from "./locales/en/common.json";

const sync=new Date("2026-03-05T10:00:00Z");

test("polskie formy liczby mnogiej dla 1, 2, 5, 22 i 25",async()=>{
  const i18n=await createI18n("pl");
  expect(i18n.t("tickets.open",{count:1})).toBe("1 otwarty ticket");
  expect(i18n.t("tickets.open",{count:2})).toBe("2 otwarte tickety");
  expect(i18n.t("tickets.open",{count:5})).toBe("5 otwartych ticketów");
  expect(i18n.t("tickets.open",{count:22})).toBe("22 otwarte tickety");
  expect(i18n.t("tickets.open",{count:25})).toBe("25 otwartych ticketów");
  expect(i18n.t("tickets.open",{count:0})).toBe("0 otwartych ticketów");
  expect(i18n.t("tickets.open",{count:1.5})).toBe("1.5 otwartego ticketu");
});

test("angielski ma dwie formy",async()=>{
  const i18n=await createI18n("en");
  expect(i18n.t("tickets.open",{count:1})).toBe("1 open ticket");
  expect(i18n.t("tickets.open",{count:22})).toBe("22 open tickets");
});

test("nagłówek zmienia język, a data pochodzi z Intl",async()=>{
  const i18n=await createI18n("pl"),user=userEvent.setup();
  render(<I18nextProvider i18n={i18n}><TicketsHeader openCount={2} lastSync={sync}/></I18nextProvider>);
  expect(screen.getByRole("heading",{name:"Tickety"})).toBeVisible();
  expect(screen.getByText("2 otwarte tickety")).toBeVisible();
  expect(screen.getByText(/5 mar 2026/)).toHaveAttribute("datetime","2026-03-05T10:00:00.000Z");
  await user.selectOptions(screen.getByRole("combobox"),"en");
  await waitFor(()=>expect(screen.getByRole("heading",{name:"Tickets"})).toBeVisible());
  expect(screen.getByText("2 open tickets")).toBeVisible();
  expect(screen.getByText(/Mar 5, 2026/)).toBeVisible();
});

test("zmiana języka ustawia lang i zapisuje wybór, a sprzątanie odłącza",async()=>{
  const i18n=await createI18n("pl"),root={lang:""},storage={setItem:vi.fn()};
  const stop=connectDocument(i18n,storage,root);
  expect(root.lang).toBe("pl");
  await i18n.changeLanguage("en");
  expect(root.lang).toBe("en");
  expect(storage.setItem).toHaveBeenLastCalledWith(STORAGE_KEY,"en");
  stop();
  await i18n.changeLanguage("pl");
  expect(root.lang).toBe("en");
});

test("wybór języka: zapis ma pierwszeństwo, potem przeglądarka, potem domyślny",()=>{
  expect(pickLanguage("en",["pl-PL"])).toBe("en");
  expect(pickLanguage(null,["en-GB","pl"])).toBe("en");
  expect(pickLanguage("xx",["de-DE"])).toBe("pl");
  expect(pickLanguage(null)).toBe("pl");
});

test("brak tłumaczenia przechodzi na język zapasowy lub zgłasza klucz",async()=>{
  const missing:string[]=[];
  const i18n=await createI18n("pl",key=>missing.push(key));
  expect(i18n.t("tickets.nieMa" as never)).toBe("tickets.nieMa");
  expect(missing).toContain("tickets.nieMa");
});

test("interpolacja nie ucieka podwójnie, a React sam escapuje tekst",async()=>{
  const i18n=await createI18n("pl");
  const text=i18n.t("tickets.assignedTo",{name:"<b>Ala</b>"});
  expect(text).toBe("Przypisany do <b>Ala</b>");
  render(<p>{text}</p>);
  expect(screen.getByText("Przypisany do <b>Ala</b>").querySelector("b")).toBeNull();
});

test("Trans wstawia komponent w tłumaczone zdanie",async()=>{
  const i18n=await createI18n("en");
  render(<I18nextProvider i18n={i18n}><HelpLine/></I18nextProvider>);
  expect(screen.getByRole("link",{name:"guide"})).toHaveAttribute("href","/help");
  expect(screen.getByRole("paragraph")).toHaveTextContent("Read the guide");
});

test("formaty Intl zależą od języka",()=>{
  const clean=(value:string)=>value.replace(/\s/g," ");
  expect(clean(formatMoney(1234.5,"EUR","de-DE"))).toBe("1.234,50 €");
  expect(clean(formatMoney(1234.5,"PLN","pl-PL"))).toBe("1234,50 zł");
  const now=new Date("2026-03-05T12:00:00Z");
  expect(formatRelative(new Date("2026-03-04T12:00:00Z"),"pl",now)).toBe("wczoraj");
  expect(formatRelative(new Date("2026-03-05T11:30:00Z"),"en",now)).toBe("30 minutes ago");
  expect(formatRelative(new Date("2026-03-05T15:00:00Z"),"en",now)).toBe("in 3 hours");
  expect(formatList(["Ala","Ola","Ela"],"pl")).toBe("Ala, Ola i Ela");
  expect(formatList(["Ala","Ola","Ela"],"en")).toBe("Ala, Ola, and Ela");
  expect(pluralCategory(22,"pl")).toBe("few");
});

test("porównanie plików wykrywa brak klucza i brak formy",()=>{
  expect(compareLocales({pl,en})).toEqual([]);
  expect(compareLocales({pl:{a:"x",b:"y"},en:{a:"x"}})).toEqual(["en: brak b"]);
  expect(compareLocales({pl:{n_one:"x",n_other:"y"},en:{n_one:"x",n_other:"y"}})).toEqual(["pl: brak n_few","pl: brak n_many"]);
  expect(flatten({a:{b:"x",c:{d:"y"}}})).toEqual(["a.b","a.c.d"]);
  expect([...requiredForms("en")].sort()).toEqual(["one","other"]);
});`;
const exercises=[
['Wyciągnij teksty nagłówka listy do pliku tłumaczeń.','Komponent zna klucz, a nie treść.',header],
['Skonfiguruj i18next z fallbackiem i przestrzenią common.','Do testów używaj osobnej instancji zamiast globalnej.',i18nFile],
['Uzasadnij escapeValue:false w konfiguracji.','Kto escapuje wartości przy wstawianiu do JSX?','React escapuje tekst przy renderze, więc włączone escapowanie w i18next dałoby podwójne encje, np. &lt;b&gt;. To ustawienie jest bezpieczne tylko wtedy, gdy wynik t() trafia do JSX jako tekst, a nie do dangerouslySetInnerHTML.'],
['Zapisz polskie formy liczby mnogiej.','Polski potrzebuje one, few, many i other.',JSON.stringify(plJson,null,2)],
['Zapisz angielskie tłumaczenia z dwiema formami.','Klucze form liczby mnogiej nie muszą być identyczne w każdym języku.',JSON.stringify(enJson,null,2)],
['Pokaż liczbę otwartych ticketów przez klucz z count.','Nie wybieraj formy przez własny warunek w komponencie.',header],
['Przetestuj formy dla 1, 2, 5, 22 i 25.','Dodaj też 0 i wartość ułamkową.',tests],
['Formatuj datę przez Intl.DateTimeFormat z języka i18n.','W teście ustal strefę czasową, aby wynik był powtarzalny.',header],
['Dodaj element time z datetime.','Maszyna dostaje wartość ISO, a człowiek format lokalny.',header],
['Dodaj przełącznik języka z nazwami w tych językach.','Etykieta powinna być zrozumiała w obu językach.',header],
['Ustaw lang dokumentu i zapamiętuj wybór języka.','Podłącz się pod zdarzenie languageChanged.',i18nFile],
['Odłącz nasłuch przy sprzątaniu.','Funkcja powinna zwracać wywołanie off.',i18nFile],
['Dobierz język startowy z zapisu i przeglądarki.','en-GB trzeba sprowadzić do en, a nieobsługiwany język odrzucić.',i18nFile],
['Zastąp sklejanie zdania interpolacją całego zdania.','Szyk należy do tłumacza.','// Źle: t("assigned")+" "+name+" "+t("at")+" "+date\n// Dobrze: t("tickets.assignedAt",{name,date})\n// pl: "Przypisany do {{name}} {{date}}"\n// en: "Assigned to {{name}} on {{date}}"\n// Tłumacz widzi całe zdanie i może zmienić szyk albo dodać przyimek.'],
['Wstaw link do tłumaczonego zdania przez Trans.','Tłumacz decyduje, gdzie w zdaniu jest link.',help],
['Formatuj kwoty przez Intl.NumberFormat.','Waluta to dana, a sposób zapisu zależy od języka.',format],
['Napisz czas względny z progami minut, godzin i dni.','numeric:"auto" daje słowo „wczoraj”.',format],
['Sformatuj listę osób przez Intl.ListFormat.','Spójnik i przecinki zależą od języka.',format],
['Sprawdź kategorię liczby przez Intl.PluralRules.','To ten sam mechanizm, którego używa i18next.',format],
['Dodaj typowane klucze przez CustomTypeOptions.','Język podstawowy jest źródłem typu.',declaration],
['Opisz, jak wykryjesz literówkę w JSX bez typów.','W JSX surowy klucz pojawi się na ekranie.','Włącz saveMissing z własnym missingKeyHandler w trybie deweloperskim oraz w testach. Log lub błąd testu wskaże klucz. Dodaj też test porównujący pliki językowe, bo sam handler działa tylko dla klucza, który ktoś wywołał.'],
['Zgłaszaj brakujące klucze w testach.','missingKeyHandler dostaje klucz.',i18nFile],
['Spłaszcz drzewo tłumaczeń do listy kluczy.','Rekurencja buduje ścieżki z kropką.',keys],
['Porównaj pliki językowe z uwzględnieniem form liczby mnogiej.','Sufiksy zależą od języka, więc porównuj klucze bazowe.',keys],
['Oblicz wymagane formy z Intl.PluralRules.','Nie zapisuj listy form ręcznie dla każdego języka.',keys],
['Dodaj sprawdzanie plików językowych do CI.','Test ma paść przy brakującym kluczu lub formie.',tests],
['Przetestuj Trans jako prawdziwy link.','Zapytaj o rolę link, nie o surowy HTML.',tests],
['Zaplanuj namespaces dla DevDesk.','Podziel tłumaczenia według funkcji i ładowania.','common: przyciski i ogólne komunikaty. tickets: lista i szczegóły. settings: ustawienia. Osobne pliki można ładować leniwie z trasą. Nie twórz namespace na każdy komponent, bo tłumacz straci kontekst, a import plików urośnie.'],
['Wymień teksty, których nie tłumaczysz kluczem.','Treść od użytkownika to nie zasób aplikacji.','Treści ticketów i komentarzy pochodzą od użytkowników, więc nie są zasobem i18n. Nazwy własne, np. DevDesk, zwykle zostają. Dane liczbowe i daty nie są kluczami, tylko wartościami formatowanymi przez Intl. Numery błędów z backendu mapuj na klucze po stronie klienta.'],
['Sprawdź kryterium akceptacji lekcji.','Lista zachowań z rzeczywistych testów.','Lista i szczegóły działają po polsku i angielsku. Wybór języka przetrwa odświeżenie. Liczba mnoga jest poprawna dla 1, 2, 5, 22, 25. Daty i kwoty używają Intl. html lang odpowiada językowi. Test porównuje klucze i formy, więc CI wykryje braki.'],
];
const concepts=[
['Co oznacza i18n?','Przygotowanie aplikacji do wielu języków i regionów','Automatyczne tłumaczenie Google','Nazwa biblioteki Reacta','Skrót testów integracyjnych','Liczba 18 to liczba liter między i a n w internationalization.'],
['Czym różni się lokalizacja od internacjonalizacji?','Lokalizacja dostosowuje treść do konkretnego języka i regionu','To synonimy bez wyjątku','Lokalizacja dotyczy tylko GPS','i18n dotyczy tylko walut','i18n przygotowuje kod, a l10n wypełnia go dla regionu.'],
['Co jest kluczem tłumaczenia?','Identyfikator tekstu, np. tickets.title','Hasło do API','Sam przetłumaczony tekst','Skrót języka','Treść znajduje się w plikach tłumaczeń.'],
['Po co przestrzenie nazw?','Dzielą tłumaczenia według funkcji i pozwalają ładować je osobno','Zmieniają język strony','Szyfrują tłumaczenia','Zastępują fallback','Każda funkcja może mieć swój plik.'],
['Co robi fallbackLng?','Wskazuje język używany, gdy brakuje tłumaczenia','Zmienia język przeglądarki','Wyłącza tłumaczenia','Wybiera walutę','Lepszy angielski tekst niż surowy klucz.'],
['Co oznacza interpolacja?','Wstawianie wartości do tekstu','Sortowanie kluczy','Zamianę języka','Kompresję plików','Składnia {{name}} wstawia wartość.'],
['Ile form liczby mnogiej ma polski według Intl.PluralRules?','Cztery','Dwie','Sześć','Jedną','one, few, many i other.'],
['Ile form liczby mnogiej ma angielski?','Dwie','Cztery','Sześć','Jedną','one i other.'],
['Do której kategorii należy po polsku liczba 22?','few','one','many','other','Końcówki 2, 3 i 4 poza 12–14 to few.'],
['Do której kategorii należy po polsku liczba 12?','many','few','one','other','12–14 to wyjątek od reguły few.'],
['Do której kategorii należy po polsku liczba 1,5?','other','one','few','many','Ułamki mają osobną kategorię.'],
['Jak i18next wybiera wariant liczby mnogiej?','Na podstawie count i Intl.PluralRules','Przez dopisanie -ów','Przez losowanie','Przez sprawdzenie długości klucza','Sufiks klucza pochodzi z kategorii Intl.'],
['Co się stanie, gdy podasz sufiks bez count?','Nie zostanie wybrana forma mnoga: t musi dostać count','Tłumaczenie zostanie zgadnięte','React zgłosi błąd renderu','i18next pobierze count z DOM','Forma mnoga zależy od przekazanej liczby.'],
['Czemu ustawiamy escapeValue:false w React?','React sam escapuje tekst przy renderze','Aby wyłączyć bezpieczeństwo','Bo tak jest szybciej w CSS','Aby włączyć HTML w każdym tłumaczeniu','Podwójne escapowanie zepsułoby znaki.'],
['Jaki hook daje funkcję t w komponencie?','useTranslation','useLocale','useI18n','useTextKey','To API react-i18next.'],
['Do czego służy komponent Trans?','Do wstawienia komponentów lub znaczników w tłumaczone zdanie','Do tłumaczenia dat','Do przełączania języka','Do ładowania plików JSON','Tłumacz zachowuje szyk zdania.'],
['Dlaczego nie sklejasz zdań z kawałków?','Szyk i odmiana zależą od języka','Sklejanie jest wolne','React tego zabrania','JSON nie obsługuje spacji','Tłumacz musi widzieć całe zdanie.'],
['Co oznacza saveMissing?','Wywołuje obsługę brakującego klucza','Zapisuje dane użytkownika','Wysyła język do serwera','Zapisuje tłumaczenia automatycznie do JSON zawsze','Pomaga wykryć braki podczas pracy i testów.'],
['Skąd biorą się typy kluczy w TSX?','Z pliku języka podstawowego przez CustomTypeOptions','Z CSS','Z bazy danych','Z testów','Pozostałe języki sprawdza się osobnym skryptem.'],
['Co jest wymagane w tsconfig, by importować JSON?','resolveJsonModule','allowJs tylko','noImplicitAny','jsx:preserve','Bez tego TS nie wczyta typów z JSON.'],
['Co pokaże JSX przy literówce w kluczu bez typów?','Zwykle surowy klucz','Pusty ekran','Wyjątek kompilacji','Tekst z innego języka losowo','Literówkę widać dopiero w działaniu.'],
['Czy typ kluczy sprawdza, że angielski ma wszystkie klucze?','Nie, typy powstają z jednego języka','Tak, zawsze','Tak, dzięki React','Tak, ale tylko w produkcji','Potrzebny jest osobny test plików.'],
['Czemu porównanie kluczy uwzględnia język?','Różne języki mają różne wymagane formy mnogie','Bo klucze zawsze są takie same','Bo JSON jest sortowany','Bo tak wymaga npm','Brak _few w angielskim nie jest błędem.'],
['Co formatuje Intl.DateTimeFormat?','Daty i czas według języka i regionu','Waluty','Liczby rzymskie w kluczach','Pliki JSON','Rozwiązuje problem ręcznych zapisów dat.'],
['Co formatuje Intl.NumberFormat?','Liczby, waluty i procenty','Tylko daty','Tylko CSS','Tylko klucze','Separatory i symbol waluty zależą od języka.'],
['Co formatuje Intl.RelativeTimeFormat?','Czas względny, np. „wczoraj”','Waluty','Daty ISO','Liczbę mnogą','numeric:"auto" może dać słowo zamiast liczby.'],
['Co formatuje Intl.ListFormat?','Listy z poprawnym spójnikiem','Tablice w JSON','Tylko listy HTML','Listy zależności','Polski i angielski różnią się interpunkcją.'],
['Czy kwota 1234,50 ma być zapisana w tłumaczeniu?','Nie: wstawiasz wartość sformatowaną przez Intl','Tak, w każdym języku','Tak, jako klucz','Tak, jako tekst bez waluty','Kwota jest daną, a format zależy od języka.'],
['Czym różni się waluta od języka w formatowaniu?','To osobne dane: EUR można pokazać po polsku lub niemiecku','Język decyduje o walucie','Waluta decyduje o języku','Nie ma różnicy','Nie przeliczaj kursu tylko dlatego, że użytkownik zmienił język.'],
['Czemu nie kodować strefy czasowej w teście na domyślną?','Wynik zależy od maszyny i CI','Bo Intl nie obsługuje stref','Bo Vitest ją blokuje','Bo daty są zabronione','W teście podaj timeZone, aby był powtarzalny.'],
['Czym jest pl-PL względem pl?','Język z regionem','Zupełnie inny język','Kod waluty','Identyfikator czytnika','Format BCP 47 pozwala określić region.'],
['Kiedy region ma znaczenie?','Np. en-US i en-GB inaczej zapisują daty','Nigdy','Tylko dla CSS','Tylko dla walut','Sam język nie zawsze wystarcza.'],
['Co ustawić po zmianie języka dla dostępności?','Atrybut lang elementu html','tabIndex body','title każdego przycisku','aria-live body','Czytnik dobiera dzięki niemu wymowę.'],
['Który wariant przechowywania języka jest prosty i wystarczający?','localStorage lub ustawienie konta','Plik JSON w paczce','Cookie z tokenem','Stan modułu bez trwałości','Wybór powinien przetrwać odświeżenie.'],
['Jak traktować język z przeglądarki?','Jako podpowiedź, po sprawdzeniu listy obsługiwanych','Jako bezwzględny wybór','Jako token','Jako lokalizację GPS','Nieobsługiwany język trzeba zastąpić domyślnym.'],
['Co zrobić z pustym lub nieznanym zapisem języka?','Pominąć go i przejść do kolejnego źródła','Wyrzucić wyjątek','Wczytać wszystkie tłumaczenia jako klucze','Wyłączyć aplikację','To dane z zewnątrz, więc trzeba je sprawdzić.'],
['Czy można bezpiecznie zamienić t na tekst w dangerouslySetInnerHTML?','Nie, gdy wstawiasz wartości od użytkownika','Tak, zawsze','Tak, dzięki i18next','Tak, gdy escapeValue:false','Trans zastępuje taki HTML bezpieczniejszą składnią.'],
['Jak dodać link wewnątrz zdania?','Komponentem Trans z mapą components','Sklejając trzy klucze','Wstawiając HTML do t i innerHTML','Dodając osobny p','Szyk pozostaje w rękach tłumacza.'],
['Jakie ryzyko ma dosłowny klucz „ok”?','Ten sam tekst może wymagać różnych tłumaczeń w różnych miejscach','Klucz jest za krótki dla JSON','Nie zadziała w TS','Brak ryzyka','Kontekst decyduje o tłumaczeniu.'],
['Czemu tekst na przyciskach bywa dłuższy po niemiecku?','Języki mają różną długość słów, więc układ nie może zakładać szerokości','Bo font jest inny','Bo Intl dodaje spacje','Bo i18next powiela tekst','Testuj układ na najdłuższych tłumaczeniach.'],
['Co to pseudolokalizacja?','Sztuczny język z wydłużonym tekstem do wykrycia problemów','Tłumaczenie maszynowe','Zmiana strefy czasu','Automatyczna walidacja JSON','Pokazuje teksty niepodpięte pod i18n i problemy układu.'],
['Co oznacza RTL w kontekście i18n?','Języki pisane od prawej do lewej','React Testing Library zawsze','Rate limit','Zmianę kolejności Tab w każdej aplikacji','Tu to right-to-left, np. arabski, wymagający dir="rtl".'],
['Jakie właściwości CSS pomagają przy RTL?','Logiczne, np. margin-inline-start','Tylko float:left','Tylko position:absolute','Właściwości CSS nie mają znaczenia','Logiczne właściwości odwracają się z kierunkiem.'],
['Co powinny zawierać wiadomości błędów backendu?','Kody mapowane na klucze w kliencie','Zawsze polskie zdania','Stos wywołań','Nazwy klas Java','Kod jest stabilny i niezależny od języka.'],
['Czy nazwy statusów z API tłumaczysz bezpośrednio?','Mapujesz stabilną wartość na klucz','Pokazujesz surowe OPEN','Tłumaczysz w bazie','Wstawiasz HTML','Wartość techniczna nie jest tekstem interfejsu.'],
['Co jest błędne w t("count_"+n)?','Własna logika liczby mnogiej zamiast count','Nazwa funkcji','Brak namespace','Użycie plusa w JS jest zabronione','Reguły języka i tak wymagają kategorii, a nie wartości.'],
['Czemu klucze dynamiczne są ryzykowne?','Narzędzia i typy nie znajdą wszystkich użyć','Spowalniają React','Są niezgodne z JSON','Wyłączają fallback','Lepiej mapować wartość na znany klucz.'],
['Jak ładować tłumaczenia w dużej aplikacji?','Leniwie, per przestrzeń nazw, np. z Suspense','Zawsze wszystkie w jednym ogromnym pliku','Tylko z CDN bez cache','Przez inline CSS','Plik języka ma koszt transferu tak jak kod.'],
['Czy cała aplikacja musi się przeładować po zmianie języka?','Nie, komponenty z useTranslation renderują się ponownie','Tak','Tak, ze względu na JSON','Tak, poza trybem deweloperskim','Hook reaguje na zdarzenie zmiany języka.'],
['Czym jest test porównujący pliki?','Skryptem wykrywającym brakujące tłumaczenia','Testem kontrastu','Zamiennikiem tłumacza','Zamiennikiem Intl','Prosty i bardzo skuteczny element CI.'],
['Co jest prawdą o tłumaczeniu maszynowym w pipeline?','Pomaga w szkicu, ale wymaga przeglądu','Zawsze poprawne','Zastępuje typy','Niemożliwe do użycia','Kontekst produktu nadal jest ważny.'],
['Jak dopasować liczebnik 0 w polskim?','Forma many, np. „0 otwartych ticketów”','Forma one','Forma few','Brak formy','Intl.PluralRules przypisuje 0 do many.'],
['Co jest celem atrybutu time z datetime?','Maszynowo czytelna wartość obok lokalnego tekstu','Styl daty','Wyłączenie Intl','Tłumaczenie klucza','Tekst widoczny i wartość techniczna to dwie rzeczy.'],
['Gdzie tworzyć Intl.DateTimeFormat?','Może być w komponencie, a przy dużych listach warto ją zmemoizować','Tylko na serwerze','Tylko w JSON','Tylko w CSS','Tworzenie formatera ma pewien koszt.'],
['Jaki problem ma Date.toLocaleDateString() bez języka?','Wynik zależy od środowiska, a SSR może dać niezgodność','Nie zwraca tekstu','Nie działa w Chrome','Wymaga Reacta','Podawaj język jawnie i pamiętaj o strefie.'],
['Co jest potrzebne, aby test nagłówka widział język?','Provider z instancją i18n lub zainicjowana instancja','Tylko mock useState','Prawdziwa przeglądarka','Serwer Next','Jawna instancja izoluje testy od siebie.'],
['Dlaczego w testach tworzymy osobne instancje i18n?','Aby zmiana języka nie wyciekała między testami','Bo singleton nie działa','Bo JSON jest ciężki','Bo RTL tego wymaga','Globalna instancja to współdzielony stan.'],
['Co oznacza „klucz bazowy” w porównaniu?','Klucz bez sufiksu formy mnogiej','Pierwszy klucz pliku','Klucz domyślnego języka','Klucz najkrótszy','Dzięki temu _few nie zgłasza fałszywych braków w en.'],
['Które zdanie o sufiksie _zero jest poprawne?','W polskim Intl nie ma kategorii zero, więc 0 dostaje many','0 zawsze używa _zero','Sufiks działa tylko w polskim','_zero jest zabronione w JSON','Kategorie wynikają z reguł językowych, a nie z samej liczby.'],
['Która wartość liczby ma być przekazywana do t?','Sama liczba w count, a nie sformatowany tekst','Tekst „5 ticketów”','Nazwa języka','Kategoria Intl','Reguły liczą się z wartości liczbowej.'],
];
const headerProbe=String.raw`import {createI18n,pickLanguage} from "./i18n";
import {formatList,formatMoney,formatRelative,pluralCategory} from "./format";
import {compareLocales,flatten,requiredForms} from "./localeKeys";
const space=(value:string)=>value.replace(/\s/g," ");
const now=new Date("2026-03-05T12:00:00Z");`;
const pl=(n)=>`(await createI18n("pl")).t("tickets.open",{count:${n}})`;
const en=(n)=>`(await createI18n("en")).t("tickets.open",{count:${n}})`;
const probeRows=[
['Polska forma dla 1',pl(1),'1 otwarty ticket','Liczba 1 to forma one.',['1 otwartych ticketów','1 otwarte tickety','1 otwartego ticketu']],
['Polska forma dla 2',pl(2),'2 otwarte tickety','Liczba 2 to forma few.',['2 otwartych ticketów','2 otwarty ticket','2 otwartego ticketu']],
['Polska forma dla 5',pl(5),'5 otwartych ticketów','Liczba 5 to forma many.',['5 otwarte tickety','5 otwarty ticket','5 otwartego ticketu']],
['Polska forma dla 12',pl(12),'12 otwartych ticketów','12 to wyjątek: końcówka 2 nie wystarcza.',['12 otwarte tickety','12 otwarty ticket','12 otwartego ticketu']],
['Polska forma dla 22',pl(22),'22 otwarte tickety','22 wraca do formy few.',['22 otwartych ticketów','22 otwarty ticket','22 otwartego ticketu']],
['Polska forma dla 21',pl(21),'21 otwartych ticketów','Tylko samo 1 używa formy one.',['21 otwarty ticket','21 otwarte tickety','21 otwartego ticketu']],
['Polska forma dla 0',pl(0),'0 otwartych ticketów','Intl przypisuje 0 do many.',['0 otwarty ticket','0 otwarte tickety','0 otwartego ticketu']],
['Polska forma dla 1,5',pl(1.5),'1.5 otwartego ticketu','Ułamek używa formy other.',['1.5 otwartych ticketów','1.5 otwarty ticket','1.5 otwarte tickety']],
['Angielska forma dla 1',en(1),'1 open ticket','one.',['1 open tickets','1 otwarty ticket','tickets.open']],
['Angielska forma dla 2',en(2),'2 open tickets','other.',['2 open ticket','2 otwarte tickety','tickets.open']],
['Angielska forma dla 0',en(0),'0 open tickets','Angielskie 0 to other.',['0 open ticket','0 otwartych ticketów','tickets.open']],
['Interpolacja nazwy','(await createI18n("en")).t("tickets.assignedTo",{name:"Ala"})','Assigned to Ala','Wartość trafia w miejsce {{name}}.',['Assigned to {{name}}','Przypisany do Ala','tickets.assignedTo']],
['Szyk w angielskim zdaniu','(await createI18n("en")).t("tickets.assignedAt",{name:"Ala",date:"Monday"})','Assigned to Ala on Monday','Przyimek należy do tłumaczenia, a nie do kodu.',['Assigned to Ala Monday','Przypisany do Ala Monday','Ala Monday']],
['Klucz bez tłumaczenia','(await createI18n("pl")).t("tickets.brak" as never)','tickets.brak','Surowy klucz to objaw braku.',['pusty tekst','undefined','Tickets']],
['Wybór języka: zapis en','pickLanguage("en",["pl"])','en','Jawny wybór wygrywa.',['pl','de','brak']],
['Wybór języka: przeglądarka en-GB','pickLanguage(null,["en-GB"])','en','Region jest pomijany przy dopasowaniu.',['pl','en-GB','brak']],
['Wybór języka: nieobsługiwany de','pickLanguage("de",["fr"])','pl','Domyślny język, gdy nie ma dopasowania.',['de','fr','en']],
['Wybór języka: wielkie litery','pickLanguage("EN")','en','Normalizacja zapobiega fałszywemu brakowi.',['pl','EN','brak']],
['Kategoria 1 po polsku','pluralCategory(1,"pl")','one','Tylko samo 1.',['few','many','other']],
['Kategoria 3 po polsku','pluralCategory(3,"pl")','few','2–4.',['one','many','other']],
['Kategoria 14 po polsku','pluralCategory(14,"pl")','many','12–14 to wyjątek.',['few','one','other']],
['Kategoria 24 po polsku','pluralCategory(24,"pl")','few','Końcówki 2–4 poza 12–14.',['many','one','other']],
['Kategoria 1 po angielsku','pluralCategory(1,"en")','one','Jedna forma pojedyncza.',['few','many','other']],
['Kategoria 24 po angielsku','pluralCategory(24,"en")','other','Angielski nie ma few.',['few','many','one']],
['Liczba form po polsku','requiredForms("pl").length',4,'one, few, many, other.'],
['Liczba form po angielsku','requiredForms("en").length',2,'one, other.'],
['Kwota EUR po niemiecku','space(formatMoney(1234.5,"EUR","de-DE"))','1.234,50 €','Separatory wynikają z języka.',['€1,234.50','1,234.50 €','1234.5 EUR']],
['Kwota EUR po angielsku','formatMoney(1234.5,"EUR","en-GB")','€1,234.50','Symbol stoi przed kwotą.',['1.234,50 €','1234,50 €','EUR 1234.5']],
['Kwota PLN po polsku','space(formatMoney(1234.5,"PLN","pl-PL"))','1234,50 zł','Polski nie grupuje czterocyfrowych kwot.',['1 234,50 zł','1,234.50 zł','PLN 1234.5']],
['Czas: dzień wstecz po polsku','formatRelative(new Date("2026-03-04T12:00:00Z"),"pl",now)','wczoraj','numeric:"auto" używa słowa.',['1 dzień temu','jutro','24 godziny temu']],
['Czas: dzień naprzód po angielsku','formatRelative(new Date("2026-03-06T12:00:00Z"),"en",now)','tomorrow','Słowo zamiast liczby.',['in 1 day','yesterday','in 24 hours']],
['Czas: 30 minut wstecz po polsku','formatRelative(new Date("2026-03-05T11:30:00Z"),"pl",now)','30 minut temu','Poniżej godziny używamy minut.',['pół godziny temu','za 30 minut','30 minutes ago']],
['Czas: 3 godziny naprzód po angielsku','formatRelative(new Date("2026-03-05T15:00:00Z"),"en",now)','in 3 hours','Dodatnia różnica to przyszłość.',['3 hours ago','in 180 minutes','in 3 hour']],
['Lista po polsku','formatList(["Ala","Ola","Ela"],"pl")','Ala, Ola i Ela','Polski nie stawia przecinka przed „i”.',['Ala, Ola, i Ela','Ala, Ola and Ela','Ala Ola Ela']],
['Lista po angielsku','formatList(["Ala","Ola","Ela"],"en")','Ala, Ola, and Ela','Angielski format używa przecinka przed and.',['Ala, Ola and Ela','Ala, Ola i Ela','Ala Ola Ela']],
['Lista dwuelementowa po polsku','formatList(["Ala","Ola"],"pl")','Ala i Ola','Bez przecinka.',['Ala, Ola','Ala, i Ola','Ala and Ola']],
['Spłaszczanie kluczy','flatten({a:{b:{c:"x"}}})[0]','a.b.c','Ścieżka łączy poziomy kropką.',['c','a,b,c','a']],
['Porównanie: brak klucza w en','compareLocales({pl:{a:"x",b:"y"},en:{a:"x"}})[0]','en: brak b','Skrypt wskazuje język i klucz.',['pl: brak b','en: brak a','brak']],
['Porównanie: polski bez form few i many','compareLocales({pl:{n_one:"x",n_other:"y"},en:{n_one:"x",n_other:"y"}}).length',2,'Forma wymagana zależy od języka.'],
['Porównanie: en bez few','compareLocales({pl:{n_one:"a",n_few:"b",n_many:"c",n_other:"d"},en:{n_one:"a",n_other:"d"}}).length',0,'Brak _few w angielskim nie jest błędem.'],
];
const project={
'src/i18n.ts':i18nFile,'src/TicketsHeader.tsx':header,'src/HelpLine.tsx':help,'src/format.ts':format,'src/localeKeys.ts':keys,
'src/locales/pl/common.json':JSON.stringify(plJson,null,2)+'\n','src/locales/en/common.json':JSON.stringify(enJson,null,2)+'\n',
'src/i18next.d.ts':declaration,'src/i18n.test.tsx':tests,
'README.md':'DevDesk: i18n. npm install, npm test. Instancja i18next powstaje przez createI18n, aby testy się nie przenikały. W aplikacji wywołaj connectDocument(i18n,localStorage,document.documentElement).\n',
};
const mutants=[
{file:'src/locales/pl/common.json',before:'"open_few": "{{count}} otwarte tickety",',after:''},
{file:'src/i18n.ts',before:'root.lang=language;',after:''},
{file:'src/i18n.ts',before:'return ()=>instance.off("languageChanged",sync);',after:'return ()=>{};'},
{file:'src/i18n.ts',before:'.split("-")[0].toLowerCase()',after:''},
{file:'src/TicketsHeader.tsx',before:'{count:openCount}',after:'{count:1}'},
{file:'src/TicketsHeader.tsx',before:'new Intl.DateTimeFormat(i18n.language,',after:'new Intl.DateTimeFormat("pl",'},
{file:'src/format.ts',before:'{numeric:"auto"}',after:'{}'},
{file:'src/format.ts',before:'if(Math.abs(hours)<24)',after:'if(Math.abs(hours)<1)'},
{file:'src/localeKeys.ts',before:'.map(base));\n  const problems',after:');\n  const problems'},
{file:'src/localeKeys.ts',before:'for(const key of plural)for(const form of requiredForms(locale)){',after:'for(const key of plural)for(const form of ["one","other"]){'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header:headerProbe});
