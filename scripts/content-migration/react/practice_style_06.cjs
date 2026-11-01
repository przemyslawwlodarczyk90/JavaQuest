// Przegląd bibliotek: 20 zadań / 60 pytań. Przykład używa Radix Primitives Dialog.
const dialog='import * as Dialog from "@radix-ui/react-dialog";\nimport styles from "./NewTicketDialog.module.css";\nexport function NewTicketDialog({onOpenChange}:{onOpenChange?:(open:boolean)=>void}){return <Dialog.Root onOpenChange={onOpenChange}><Dialog.Trigger className={styles.trigger}>+ Nowy ticket</Dialog.Trigger><Dialog.Portal><Dialog.Overlay className={styles.overlay}/><Dialog.Content className={styles.content}><Dialog.Title>Nowy ticket</Dialog.Title><Dialog.Description>Opisz problem, który zgłaszasz.</Dialog.Description><label htmlFor="new-ticket-title">Tytuł</label><input id="new-ticket-title"/><Dialog.Close className={styles.close}>Anuluj</Dialog.Close></Dialog.Content></Dialog.Portal></Dialog.Root>;}';
const controlled='import * as Dialog from "@radix-ui/react-dialog";\nexport function ConfirmDialog({open,onOpenChange}:{open:boolean;onOpenChange:(open:boolean)=>void}){return <Dialog.Root open={open} onOpenChange={onOpenChange}><Dialog.Trigger>Otwórz</Dialog.Trigger><Dialog.Portal><Dialog.Overlay/><Dialog.Content><Dialog.Title>Potwierdź</Dialog.Title><Dialog.Description>Sprawdź dane przed kontynuacją.</Dialog.Description><Dialog.Close>Zamknij</Dialog.Close></Dialog.Content></Dialog.Portal></Dialog.Root>;}';
const dialogCss='.trigger,.close {padding:var(--space-2) var(--space-4);border:1px solid var(--border);border-radius:var(--radius);background:var(--surface);color:var(--text);}\n.trigger:focus-visible,.close:focus-visible,.content input:focus-visible {outline:2px solid var(--color-primary);outline-offset:2px;}\n.overlay {position:fixed;inset:0;background:rgb(0 0 0 / 0.4);}\n.content {position:fixed;top:50%;left:50%;transform:translate(-50%,-50%);width:min(30rem,calc(100vw - 2rem));max-height:calc(100dvh - 2rem);overflow:auto;padding:var(--space-4);border-radius:var(--radius);background:var(--surface);color:var(--text);}';
const project={
 'src/NewTicketDialog.tsx':dialog.replace('import * as Dialog','import {useId} from "react";\nimport * as Dialog').replace('return <Dialog.Root','const titleId=useId();return <Dialog.Root').replace('htmlFor="new-ticket-title"','htmlFor={titleId}').replace('id="new-ticket-title"','id={titleId}'),
 'src/NewTicketDialog.module.css':dialogCss,
 'src/tokens.css':require('./practice_style_03.cjs').tokens,
 'src/App.tsx':'import "./tokens.css";\nimport {NewTicketDialog} from "./NewTicketDialog";\nexport default function App(){return <main><h1>Tickety</h1><NewTicketDialog/></main>;}'
};
const exercises=[
 ['Porównaj bibliotekę ostylowaną, headless i kod kopiowany do projektu.','Porównuj odpowiedzialności, nie popularność.','Ostylowana daje gotowe komponenty i system wyglądu. Headless dostarcza prymitywy zachowania oraz dostępności, które trzeba poprawnie złożyć i ostylować. Model kopiowanego kodu pozwala edytować lokalne źródła, ale przenosi też odpowiedzialność za ich utrzymanie.'],
 ['Wskaż wymagania trudnego modala poza samym tłem i obramowaniem.','Uwzględnij fokus, klawiaturę i nazwę dostępną.','Otwarcie i zamknięcie, przeniesienie fokusu, ograniczenie interakcji z tłem w trybie modalnym, Tab/Shift+Tab, Escape, przywrócenie fokusu oraz dostępna nazwa i opis. Biblioteka pomaga, ale integrację nadal trzeba testować.'],
 ['Napisz dialog Nowy ticket z Root, Trigger, Portal, Overlay i Content.','Użyj rzeczywistych prymitywów biblioteki.',dialog],
 ['Dodaj Title i Description do dialogu zamiast anonimowego kontenera.','Dialog potrzebuje zrozumiałej nazwy.',dialog],
 ['Przyjmij opcjonalny onOpenChange o typie boolean→void.','Powiadomienie o zmianie to funkcja, nie wynik jej wywołania.',dialog],
 ['Zbuduj kontrolowany dialog open/onOpenChange.','Rodzic odpowiada za aktualizację open.',controlled],
 ['Wyjaśnij różnicę open i defaultOpen.','Jeden kontroluje aktualną wartość, drugi początkową.','Open ustala stan kontrolowany z rodzica. DefaultOpen ustala początek wewnętrznie zarządzanego stanu. Samo przekazanie onOpenChange nie zmienia dialogu w kontrolowany; potrzebna jest także wartość open i obsługa jej aktualizacji.'],
 ['Dodaj Overlay i Content ostylowane przez CSS Modules i tokeny.','Content musi zmieścić się na małym ekranie.','/* NewTicketDialog.module.css */\n'+dialogCss],
 ['Zaplanuj obsługę długiej treści na małym ekranie.','Nie zakładaj stałej wysokości okna.','Użyj szerokości z marginesem od viewportu, max-height i overflow:auto. Sprawdź powiększenie, mały ekran i klawiaturę ekranową. Element zamykający oraz formularz muszą pozostać osiągalne.'],
 ['Zastąp stałe id pola przez useId, gdy na stronie może być kilka dialogów.','Id label i input mają pochodzić z tej samej instancji.',project['src/NewTicketDialog.tsx']],
 ['Użyj Trigger asChild z natywnym button bez zagnieżdżania przycisków.','AsChild komponuje zachowanie z jednym poprawnym dzieckiem.','import * as Dialog from "@radix-ui/react-dialog";\nexport function Example(){return <Dialog.Root><Dialog.Trigger asChild><button type="button">Nowy ticket</button></Dialog.Trigger><Dialog.Portal><Dialog.Content><Dialog.Title>Nowy ticket</Dialog.Title><Dialog.Description>Dodaj dane.</Dialog.Description><Dialog.Close>Zamknij</Dialog.Close></Dialog.Content></Dialog.Portal></Dialog.Root>;}'],
 ['Opisz wymagania własnego Button użytego przez asChild.','Musi współpracować z props i ref biblioteki.','Przekaż wymagane props, event handlery, className oraz ref do właściwego elementu DOM zgodnie z wersją Reacta i kontraktem biblioteki. Nie ignoruj otrzymanego onClick. Unikaj dodatkowego button wewnątrz button.'],
 ['Wyprowadź typ props opakowania Root przez ComponentProps.','Nie przepisuj ręcznie całego API biblioteki.','import type {ComponentProps} from "react";\nimport * as Dialog from "@radix-ui/react-dialog";\ntype RootProps=ComponentProps<typeof Dialog.Root>;\nexport function TicketDialogRoot(props:RootProps){return <Dialog.Root {...props}/>;}'],
 ['Przygotuj test otwarcia i zamknięcia wyłącznie klawiaturą.','Testuj rzeczywisty fokus w przeglądarce.','Tab do Trigger, Enter lub Spacja zgodnie z natywnym przyciskiem, sprawdzenie fokusu wewnątrz, Tab i Shift+Tab po kontrolkach, Escape, potwierdzenie zamknięcia i powrotu fokusu do Trigger. Sprawdź też widoczny fokus w obu motywach.'],
 ['Przygotuj test czytnika ekranu dla dialogu.','Samo role=dialog nie wystarcza do całego testu.','Otwórz dialog i sprawdź ogłoszenie jego nazwy oraz opisu, etykiety pól i sensowną kolejność nawigacji. Sprawdź brak dostępu do zablokowanego tła w modalnym scenariuszu. Nie uznawaj statycznego snapshotu za test czytnika.'],
 ['Porównaj odpowiedzialność za poprawki przy paczce i lokalnie kopiowanym kodzie UI.','W obu przypadkach aktualizacje wymagają przeglądu.','Paczka dostarcza nowe wersje, które trzeba świadomie zaktualizować i sprawdzić. Kod skopiowany do repo można modyfikować lokalnie, ale poprawki upstream nie pojawiają się automatycznie. Śledź źródło, licencję i zmiany.'],
 ['Oceń ryzyko używania trzech dużych bibliotek UI w jednej stronie.','Nie twórz absolutnego zakazu każdego dodatkowego narzędzia.','Możliwe są konflikty motywów, różne zachowania, niespójny wygląd i większe zależności. Preferuj spójną podstawę. Wyspecjalizowany komponent może być uzasadnionym wyjątkiem po ocenie integracji.'],
 ['Wybierz podejście dla panelu wewnętrznego i dla mocno indywidualnej marki.','Podaj kryteria, nie uniwersalnego zwycięzcę.','Panel wewnętrzny z krótkim terminem może skorzystać z gotowego zestawu. Indywidualny design może skorzystać z headless i własnego CSS. Uwzględnij dostępność, wsparcie, licencję, komponenty danych, znajomość zespołu i utrzymanie.'],
 ['Wskaż, czego headless nie załatwia automatycznie w każdej integracji.','Gotowy mechanizm nadal można użyć niepoprawnie.','Nie gwarantuje kontrastu własnych kolorów, poprawnych etykiet biznesowych, sensownej treści ani zgodnego użycia asChild i kontrolowanego stanu. Wymaga testu nawigacji i integracji z aplikacją.'],
 ['Oddaj miniaplikację z dostępną strukturą dialogu, tokenami i unikalnym id pola.','Po buildzie wykonaj test klawiatury w przeglądarce.','Pliki projektu:\n'+Object.entries(project).map(([file,code])=>`--- ${file} ---\n${code}`).join('\n\n')],
];
const quiz=`
Co zwykle dostarcza biblioteka ostylowana?|Gotowe komponenty z wyglądem i motywem|Wyłącznie surowe hooki|Tylko tokeny danych|Brak CSS|Zestaw przyspiesza budowę spójnego interfejsu.
Co opisuje podejście headless?|Prymitywy zachowania z własnym stylem integratora|Brak logiki|Wyłącznie backend|Brak dostępności z zasady|Wygląd i treść pozostają ważną odpowiedzialnością aplikacji.
Co wyróżnia model kopiowanego kodu?|Źródła komponentów trafiają do projektu|Wszystko znika z repo po buildzie|Brak zależności zawsze|Brak obowiązku utrzymania|Lokalny kod daje kontrolę i wymaga aktualizacji.
Czy posiadanie lokalnego kodu usuwa obowiązki licencyjne?|Nie|Tak|Tylko z Tailwind|Tylko w JSX|Trzeba przestrzegać licencji źródła i zależności.
Który przykład reprezentuje gotowy zestaw ostylowany?|MUI|Sam clsx|Sam React useId|Sam CSSProperties|MUI dostarcza komponenty z systemem wyglądu.
Który przykład reprezentuje prymitywy headless?|Radix Primitives|Zwykły plik tokens.css|Prettier|TypeScript compiler|Radix dostarcza m.in. Dialog.
Czy headless gwarantuje dobry kontrast dowolnych własnych kolorów?|Nie|Tak|Tylko w TSX|Tylko w dark|Integrator wybiera kolory i musi je sprawdzić.
Czy biblioteka może zmniejszyć zakres ręcznej implementacji fokusu?|Tak|Nie|Tylko bez Reacta|Tylko w SSR|Prymitywy zawierają gotowe mechanizmy zachowania.
Co jest zadaniem Dialog.Root?|Koordynacja stanu i części dialogu|Nadanie wszystkich kolorów|Wyłącznie obramowanie|Tworzenie API backendu|Root spaja prymitywy.
Co jest zadaniem Dialog.Trigger?|Otwarcie dialogu przez kontrolkę|Wyświetlenie opisu|Nadanie koloru tła|Usunięcie danych|Trigger uczestniczy w obsłudze otwarcia i fokusu.
Co jest zadaniem Dialog.Content?|Zawartość i kontener okna dialogowego|Wyłącznie przycisk otwierający|Globalny reset|Lista statusów|Tu umieszcza się tytuł, opis i kontrolki.
Co jest zadaniem Dialog.Title?|Dostępna nazwa dialogu|Wyłącznie ozdobny cień|Id danych|Stan open|Tytuł pozwala zrozumieć cel okna.
Co jest zadaniem Dialog.Description?|Opis kontekstu dialogu|Wyłącznie zmiana CSS|Ustawienie key|Włączenie dark|Opis uzupełnia nazwę.
Co jest zadaniem Dialog.Close?|Kontrolka zamykająca|Wyłącznie zmiana tytułu|Nowy rekord|Import CSS|Prymityw jest połączony z mechanizmem stanu dialogu.
Co daje Dialog.Portal?|Renderowanie warstwy poza typowym miejscem DOM|Nowy routing|Nowy kontekst danych zawsze|Automatyczny kolor|Portal ułatwia organizację warstw.
Czy portal zmienia hierarchię React w zwykłą niezależną aplikację?|Nie|Tak|Tylko w TSX|Tylko przy Overlay|Położenie DOM i drzewo React to różne rzeczy.
Co jest zadaniem Overlay?|Wizualna warstwa tła okna|Tytuł dialogu|Id formularza|Typ props|Warstwa jest częścią prezentacji modalnej.
Czy sam Overlay div zapewnia pełne zarządzanie fokusem?|Nie|Tak|Tylko z opacity|Tylko w JSX|Potrzebny jest kompletny mechanizm dialogu.
Co oznacza modalny scenariusz?|Ograniczenie interakcji z tłem podczas otwarcia|Wyłącznie wyższy z-index|Brak klawiatury|Wyłącznie ciemne tło|Modalność dotyczy zachowania, nie samego wyglądu.
Czy Escape należy sprawdzić w teście integracji?|Tak|Nie|Tylko przy ciemnym motywie|Tylko w TSX|Należy potwierdzić oczekiwane zamknięcie.
Dokąd zwykle powinien wrócić fokus po zamknięciu w tym przykładzie?|Do Trigger|Na początek body zawsze|Do losowego inputa|Do paska adresu|Powrót pozwala kontynuować nawigację.
Czy Tab i Shift+Tab należy testować wewnątrz modala?|Tak|Nie|Tylko w JSX|Tylko na desktopie|Oba kierunki ujawniają problemy z kolejnością i ograniczeniem fokusu.
Czy statyczny HTML dowodzi poprawnego focus trap?|Nie|Tak|Tylko w TSX|Tylko z Radix|Potrzebna jest interakcja z rzeczywistym środowiskiem DOM.
Czy zwykły tekst tytułu gdziekolwiek na stronie gwarantuje nazwę dialogu?|Nie|Tak|Tylko przy h1|Tylko w dark|Nazwa musi być poprawnie powiązana z dialogiem.
Jak typowany jest onOpenChange?|Funkcja otrzymująca boolean|Funkcja bez argumentów zawsze|string|User[]|Biblioteka przekazuje nowy stan otwarcia.
Czy onOpenChange sam oznacza kontrolowane open?|Nie|Tak|Tylko w JSX|Tylko przy CSS Modules|Kontrolowany wariant wymaga przekazania wartości open.
Co robi defaultOpen?|Ustala początkowy stan niekontrolowany|Stale wymusza aktualny stan|Zamyka przy Escape|Nadaje id|Wartość startowa nie jest sterowaniem każdą aktualizacją.
Co robi open w kontrolowanym dialogu?|Określa aktualny stan od rodzica|Wyłącznie wartość startową|Tworzy CSS|Generuje dane|Rodzic powinien reagować na żądania zmian.
Co grozi, gdy rodzic stale daje open=true i nie aktualizuje stanu?|Dialog może nie zamknąć się zgodnie z żądaniem|Zniknie CSS|React zmieni true na false automatycznie|Zniknie tytuł|Kontrolowana wartość pozostaje źródłem prawdy.
Czy onOpenChange={handler()} przekazuje poprawnie callback?|Nie|Tak|Tylko w TSX|Tylko w Radix|To wywołuje handler podczas renderu.
Co oznacza Trigger asChild?|Złożenie zachowania z przekazanym dzieckiem|Zawsze dodatkowy button wokół dziecka|Usunięcie wszystkich props|Nowy stan globalny|Przy poprawnym użyciu unika się zbędnej kontrolki zewnętrznej.
Czy button wewnątrz button to poprawny cel integracji?|Nie|Tak|Tylko w Radix|Tylko w JSX|Zagnieżdżone interaktywne przyciski są błędem struktury.
Co musi obsłużyć własny komponent użyty przez asChild?|Wymagane props i ref zgodnie z kontraktem|Wyłącznie children|Tylko nazwę CSS|Tylko key|Ignorowanie handlerów lub ref może zepsuć działanie.
Czy własny Button powinien ignorować otrzymany onClick biblioteki?|Nie|Tak|Tylko w dark|Tylko w TSX|Zachowanie prymitywu wymaga przekazania handlerów.
Jak wyprowadzić props Root w TSX?|ComponentProps<typeof Dialog.Root>|CSSProperties<Dialog>|ReactNode.Root|typeof styles|Typy biblioteki opisują kontrakt komponentu.
Czy typy Radix zwykle pochodzą z paczki?|Tak|Nie|Tylko z CSS|Tylko z ReactDOM|Paczka dostarcza deklaracje API.
Czy JSX może korzystać z części podpowiedzi typowanej paczki w edytorze?|Tak|Nie|Tylko po zmianie na JSON|Tylko bez Reacta|Edytor może odczytać deklaracje także przy kodzie JS.
Dlaczego stałe id new-ticket-title jest problemem przy kilku instancjach?|Może się powtórzyć|Nie wolno myślników|React zabrania id|Nie pasuje do CSS Modules|Id powinno być unikalne w dokumencie.
Co daje useId dla label/input?|Id właściwe dla instancji komponentu|Id rekordu z backendu|Key listy|Losowy hash CSS|Służy do powiązań dostępności, nie do tożsamości danych.
Czy useId powinno zastępować ticket.id jako key listy?|Nie|Tak|Tylko w JSX|Tylko z Portal|Key danych powinien wynikać ze stabilnej tożsamości rekordu.
Jak powiązać label z input?|htmlFor odpowiadające id|Wyłącznie identyczna klasa|Key na label|Tylko placeholder|Powiązanie identyfikatorów nadaje kontrolce etykietę.
Czy placeholder zastępuje etykietę pola?|Nie|Tak|Tylko w dialogu|Tylko dla title|Etykieta powinna być trwale dostępna.
Czy Content powinno mieć limit wysokości na małym ekranie?|Tak, z osiągalną przewijaną treścią|Nie, zawsze stałe 900px|Tylko w light|Tylko w TSX|Treść i zamknięcie muszą pozostać dostępne.
Czy samo z-index:9999 rozwiązuje każdy problem modala?|Nie|Tak|Tylko w Vite|Tylko dla Portal|Istotne są także fokus, etykiety, przewijanie i kontekst warstw.
Czy CSS Modules może ostylować części Radix?|Tak|Nie|Tylko z Tailwind|Tylko w JSX|ClassName przekazuje lokalne klasy do prymitywów.
Czy tokeny motywu mogą działać w Content renderowanym do body?|Tak, jeśli dziedziczą się z odpowiedniego przodka np. html|Nigdy|Tylko w JSX|Tylko z MUI|Umiejscowienie zakresu tokenów ma znaczenie przy portalu.
Co może się stać z motywem ustawionym tylko na małym poddrzewie poza Portalem?|Portal może nie odziedziczyć tych CSS zmiennych|React zawsze skopiuje zmienne CSS|Zniknie Root|Wszystko stanie się dark|Dziedziczenie CSS zależy od drzewa DOM.
Czy kontekst React i dziedziczenie CSS są tym samym?|Nie|Tak|Tylko w TSX|Tylko w dialogu|Portal zachowuje kontekst React, ale CSS wynika z położenia DOM.
Czy headless zwalnia z testu czytnika ekranu?|Nie|Tak|Tylko z Radix|Tylko w JSX|Treść i integracja nadal mogą być błędne.
Co sprawdzać czytnikiem przy otwarciu?|Nazwę, opis i etykiety kontrolek|Tylko kolor overlay|Wyłącznie hashe klas|Tylko rozmiar paczki|To elementy rozumienia i obsługi dialogu.
Czy kopiowany kod UI aktualizuje się sam po poprawce upstream?|Nie|Tak|Tylko z Tailwind|Tylko w TSX|Lokalne zmiany wymagają świadomego utrzymania.
Czy aktualizacja paczki UI wymaga testu integracji?|Tak|Nie|Tylko przy major|Tylko dla modala|Nawet zgodna wersja może ujawnić różnice użycia.
Jakie ryzyko daje kilka dużych zestawów UI na stronie?|Niespójne motywy i większe zależności|Zawsze lepszą dostępność|Brak CSS|Automatyczne scalanie tokenów|Koszt i zachowanie wymagają oceny.
Czy jeden zestaw UI oznacza zakaz każdej wyspecjalizowanej biblioteki?|Nie|Tak|Tylko w React|Tylko z MUI|Uzasadniony wyjątek może być właściwy.
Co porównać przy wyborze biblioteki?|Wymagania, licencję, wsparcie i integrację|Wyłącznie liczbę gwiazdek|Tylko wygląd jednej demonstracji|Wyłącznie nazwę|Decyzja dotyczy utrzymania całego projektu.
Czy szybki panel wewnętrzny może skorzystać z ostylowanego zestawu?|Tak|Nie|Tylko bez Reacta|Tylko w TSX|Gotowe wzorce mogą skrócić pracę.
Czy własny design może skorzystać z headless?|Tak|Nie|Tylko z MUI|Tylko z SSR|Prymitywy pozwalają zachować własny wygląd.
Czy nazwa headless znaczy brak zachowania klawiatury?|Nie|Tak|Tylko w JSX|Tylko w dark|Właśnie zachowanie jest istotną częścią prymitywów.
Czy library demo dowodzi poprawności własnej integracji?|Nie|Tak|Tylko dla Dialog|Tylko z TypeScript|Aplikacja może błędnie złożyć lub ostylować komponent.
Co potwierdza ukończenie lekcji?|Dialog ze stylami i poprawną obsługą klawiatury oraz fokusu|Samo zainstalowanie paczki|Tylko ładny overlay|Wyłącznie brak błędów TS|Wynik musi spełniać wymagania zachowania i dostępności.
`.trim().split('\n').map(line=>line.split('|'));
module.exports={exercises,quiz,dialog,controlled,project};
