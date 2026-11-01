const sorting=require('./practice_state_07.cjs');
const model=sorting.model.replace('assignee:string|null','assignee:string|null;status:"OPEN"|"IN_PROGRESS"|"CLOSED"');
const paginate='export type PageResult<T>={pageItems:T[];currentPage:number;pageCount:number;from:number;to:number};\nexport function paginate<T>(items:readonly T[],page:number,pageSize:number):PageResult<T>{if(!Number.isSafeInteger(page))throw new RangeError("Page musi być bezpieczną liczbą całkowitą");if(!Number.isSafeInteger(pageSize)||pageSize<=0)throw new RangeError("PageSize musi być dodatnią bezpieczną liczbą całkowitą");const pageCount=Math.max(1,Math.ceil(items.length/pageSize));const currentPage=Math.min(Math.max(1,page),pageCount);const start=(currentPage-1)*pageSize;return {pageItems:items.slice(start,start+pageSize),currentPage,pageCount,from:items.length===0?0:start+1,to:Math.min(start+pageSize,items.length)};}';
const pager='export function Pager({page,pageCount,onPageChange}:{page:number;pageCount:number;onPageChange:(page:number)=>void}){return <nav aria-label="Paginacja"><button type="button" disabled={page<=1} onClick={()=>{if(page>1)onPageChange(page-1);}}>Poprzednia</button>{Array.from({length:pageCount},(_,index)=>index+1).map(number=><button key={number} type="button" aria-label={`Strona ${number}`} aria-current={number===page?"page":undefined} onClick={()=>onPageChange(number)}>{number}</button>)}<button type="button" disabled={page>=pageCount} onClick={()=>{if(page<pageCount)onPageChange(page+1);}}>Następna</button></nav>;}';
const size='export const PAGE_SIZES=[10,25,50] as const;\nexport type PageSize=(typeof PAGE_SIZES)[number];\nexport function PageSizeSelect({value,onChange}:{value:PageSize;onChange:(size:PageSize)=>void}){return <label>Wierszy na stronę<select value={value} onChange={event=>{const parsed=Number(event.currentTarget.value);const next=PAGE_SIZES.find(size=>size===parsed);if(next!==undefined)onChange(next);}}>{PAGE_SIZES.map(size=><option key={size} value={size}>{size}</option>)}</select></label>;}';
const range='export function RangeInfo({from,to,total}:{from:number;to:number;total:number}){return <p aria-live="polite">{total===0?"Brak wyników":`Wiersze ${from}–${to} z ${total}`}</p>;}';
const main='import {useState} from "react";\n'+model+'\n'+sorting.helpers+'\n'+sorting.header+'\n'+paginate+'\n'+pager+'\n'+size+'\n'+range+'\nexport function Main({tickets}:{tickets:readonly Ticket[]}){const [query,setQuery]=useState("");const [status,setStatus]=useState<"ALL"|Ticket["status"]>("ALL");const [sort,setSort]=useState<SortState>({key:"createdAt",direction:"desc"});const [page,setPage]=useState(1);const [pageSize,setPageSize]=useState<PageSize>(10);const filtered=tickets.filter(ticket=>(status==="ALL"||ticket.status===status)&&ticket.title.toLowerCase().includes(query.trim().toLowerCase()));const sorted=sortTickets(filtered,sort);const result=paginate(sorted,page,pageSize);const statuses=["ALL","OPEN","IN_PROGRESS","CLOSED"] as const;const columns=[{key:"id",label:"Id"},{key:"title",label:"Tytuł"},{key:"priority",label:"Priorytet"},{key:"createdAt",label:"Utworzono"}] as const;return <main><label>Szukaj w tytule<input value={query} onChange={event=>{setQuery(event.currentTarget.value);setPage(1);}}/></label><label>Status<select value={status} onChange={event=>{const raw=event.currentTarget.value;const next=statuses.find(value=>value===raw);if(next!==undefined){setStatus(next);setPage(1);}}}>{statuses.map(value=><option key={value} value={value}>{value}</option>)}</select></label><PageSizeSelect value={pageSize} onChange={next=>{setPageSize(next);setPage(1);}}/><RangeInfo from={result.from} to={result.to} total={filtered.length}/><table><thead><tr>{columns.map(column=><SortHeader key={column.key} label={column.label} column={column.key} sort={sort} onSort={key=>{setSort(current=>nextSort(current,key));setPage(1);}}/>)}</tr></thead><tbody>{result.pageItems.length===0?<tr><td colSpan={4}>Brak wyników</td></tr>:result.pageItems.map(ticket=><tr key={ticket.id}><td>{ticket.id}</td><td>{ticket.title}</td><td>{ticket.priority}</td><td>{ticket.createdAt}</td></tr>)}</tbody></table><Pager page={result.currentPage} pageCount={result.pageCount} onPageChange={setPage}/></main>;}';
const exercises=[
 ['Napisz generyczne paginate<T> dla readonly listy i numerów stron od 1.','Wynik ma zachować typ elementów.',paginate],
 ['Oblicz pageCount dla total=23 i pageSize=10.','Użyj ceil i co najmniej jednej pustej strony.','PageCount=3. Zakresy stron to 1–10, 11–20 i 21–23.'],
 ['Obsłuż pustą listę jako currentPage=1, pageCount=1, from=to=0.','Nie pokazuj zakresu 1–0.',paginate],
 ['Ogranicz page poniżej 1 i powyżej pageCount.','Całkowite zero i liczby ujemne przyjmują pierwszą stronę.',paginate],
 ['Odrzuć NaN, Infinity i ułamkowy numer strony.','Kontrakt helpera wymaga bezpiecznej liczby całkowitej.',paginate],
 ['Odrzuć pageSize<=0, ułamek i wartości niefinitych rozmiarów.','Dzielenie przez zero nie jest poprawną paginacją.',paginate],
 ['Przelicz numer strony na indeks start dokładnie w jednym miejscu.','Indeks tablicy zaczyna się od 0.','Start=(currentPage-1)*pageSize. Dla strony 1 start=0, dla strony 2 i rozmiaru 10 start=10. Slice kończy się przed drugim indeksem.'],
 ['Zwróć ostatnią niepełną stronę przez slice.','Slice nie dopisuje nieistniejących rekordów.',paginate],
 ['Dodaj from i to do wyniku helpera.','From jest numerem dla człowieka, nie indeksem. ',paginate],
 ['Zbuduj Pager z poprzednią, numerami i następną stroną.','Przyjmij poprawne currentPage i umiarkowaną liczbę stron.',pager],
 ['Wyłącz Poprzednia na pierwszej i Następna na ostatniej stronie.','Natywny disabled to więcej niż szary styl.',pager],
 ['Oznacz aktywny numer aria-current=page.','Nie używaj niepoprawnego tokenu strona.',pager],
 ['Nadaj nawigacji nazwę Paginacja i numerom nazwy Strona N.','Semantyka pomaga odróżnić kontrolki.',pager],
 ['Zbuduj PageSizeSelect dla 10,25,50 z walidacją wartości DOM.','Number(raw) nie jest samo walidacją listy opcji.',size],
 ['Dodaj RangeInfo z Brak wyników dla total=0.','Zakres ma odpowiadać filtrowanej liście.',range],
 ['Połącz pipeline filtr→sort→paginate.','Stronę wycinaj z całego uporządkowanego wyniku.',main],
 ['Zresetuj page=1 po zmianie query bez efektu synchronizującego.','To reakcja na zdarzenie edycji.',main],
 ['Zresetuj page=1 po zmianie statusu.','Zmiana listy wyników może zmniejszyć liczbę stron.',main],
 ['Zresetuj page=1 po zmianie pageSize.','Zachowanie ma być jawne i przewidywalne.',main],
 ['Zdecyduj o resecie po zmianie sortowania i zaimplementuj go w handlerze.','W tym wariancie nowy porządek startuje na pierwszej stronie.',main],
 ['Przekaż Pager currentPage, nie nieaktualne żądane page.','Po zmianie danych helper może ograniczyć numer.',main],
 ['Wyjaśnij, czy clamping currentPage automatycznie zmienia stan page.','Wartość wyliczana i źródłowy stan są odrębne.','Nie. Paginate zwraca ograniczone currentPage, a żądane page może pozostać większe. Reset w handlerach i używanie currentPage w Pagerze zapobiegają niespójnemu widokowi. Zewnętrzne zmiany danych mogą wymagać osobnej świadomej polityki.'],
 ['Zaplanuj test 120 rekordów przy rozmiarach 10,25,50.','Uwzględnij ostatnią niepełną stronę.','Rozmiar 10: 12 stron, ostatnia 111–120. Rozmiar 25: 5 stron, ostatnia 101–120. Rozmiar 50: 3 strony, ostatnia 101–120.'],
 ['Wyjaśnij błąd paginowania przed filtrowaniem.','Wybrany fragment może nie zawierać dopasowań z innych stron.','Najpierw odrzuć niedopasowane rekordy, potem uporządkuj wszystkie pasujące, a dopiero na końcu wytnij stronę. Inaczej strony i liczba wyników mogą mieć dziury oraz pomijać pasujące rekordy.'],
 ['Wyjaśnij błąd sortowania wyłącznie pageItems.','Użytkownik zwykle oczekuje globalnej kolejności wyniku.','Sortowanie tylko fragmentu nie daje globalnie pierwszych lub ostatnich rekordów. Najpierw sortuj cały wynik filtrów, potem paginuj.'],
 ['Zapisz JSX/JSDoc helper paginacji liczb z walidacją.','Kod JS ma ten sam kontrakt liczbowy.','/** @param {readonly number[]} items @param {number} page @param {number} pageSize */\nexport function paginateNumbers(items,page,pageSize){if(!Number.isSafeInteger(page)||!Number.isSafeInteger(pageSize)||pageSize<=0)throw new RangeError("Parametry");const count=Math.max(1,Math.ceil(items.length/pageSize));const current=Math.min(Math.max(1,page),count);const start=(current-1)*pageSize;return items.slice(start,start+pageSize);}'],
 ['Wykaż błędem TS niedozwolone aria-current=strona.','Typ ARIA używa określonych tokenów.','export function Invalid(){\n// @ts-expect-error Niepoprawny token aria-current\nreturn <button aria-current="strona">1</button>;\n}'],
 ['Zaprojektuj test niemutowania oraz zachowania typu i referencji elementów.','Slice tworzy nową tablicę, ale nie kopiuje rekordów.','Zamroź wejście, wywołaj paginate, sprawdź niezmienioną kolejność i nowe pageItems. Wybrane obiekty mogą mieć te same referencje co oryginały. TS powinien zachować typ obiektów w wyniku generyka.'],
 ['Wyjaśnij ograniczenie Pager renderującego wszystkie numery przy bardzo wielu stronach.','To celowo prosty wariant dla niewielkiego pageCount.','Tysiące przycisków są trudne do obsługi i kosztowne. Przy dużej liczbie stron zastosuj okno numerów z pierwszą/ostatnią stroną i przerwami albo inny model nawigacji. Helper paginate może pozostać ten sam.'],
 ['Oddaj Main z filtrami, sortem, wyborem rozmiaru, zakresem i Pagerem.','W stanie są decyzje użytkownika, wyniki są wyliczane.',main],
];
const quiz=`
Od jakiej liczby numerowane są strony w tej lekcji?|1|0|-1|10|To konwencja interfejsu użytkownika.
Od jakiej liczby zaczynają się indeksy tablicy?|0|1|-1|10|Przeliczenie odbywa się w helperze.
Co jest stanem paginacji?|Page i pageSize|Zawsze pageItems i from|Wyłącznie pageCount|Wszystkie indeksy|Reszta wynika z danych i tych decyzji.
Czy pageCount musi mieć osobny setter?|Nie|Tak|Tylko w TSX|Tylko przy 120 rekordach|To wynik total i pageSize.
Jak liczyć liczbę stron dla dodatniego rozmiaru?|Ceil(total/pageSize) z co najmniej 1|Floor zawsze|Total*pageSize|Page+pageSize|Ostatni niepełny fragment też jest stroną.
Dlaczego używamy ceil zamiast floor?|Niepełna ostatnia strona musi pozostać osiągalna|Bo floor mutuje listę|React wymaga ceil|Dla nowych id|To kwestia zakresu wyników.
Ile stron ma 23 rekordy przy 10 na stronę?|3|2|4|23|Dwie pełne i jedna niepełna.
Ile stron ma pusty wynik w przyjętym modelu?|1 pusta|0 zawsze|-1|NaN|UI ma jeden bezpieczny pusty stan.
Jaki from ma pusty wynik?|0|1|PageSize|-1|Nie pokazujemy nieistniejącego pierwszego wiersza.
Jaki to ma pusty wynik?|0|1|10|-1|Zakres jest pusty.
Jak obliczyć start dla currentPage od 1?|Odjąć 1 i pomnożyć przez pageSize|Page razy pageSize|Page plus pageSize|Page minus pageSize|To przejście na indeks od zera.
Co jest końcowym indeksem slice w helperze?|Start+pageSize bez włączenia końca|Start+pageSize włącznie|Zawsze total+1|Zawsze pageCount|Slice ma prawy koniec wyłączny.
Czy slice dopisuje elementy poza końcem tablicy?|Nie|Tak|Tylko w TSX|Tylko dla 10|Zwróci krótszy ostatni fragment.
Po co ograniczyć requested page do pageCount?|By po zmniejszeniu danych nie wyświetlić nieistniejącej strony|By zmienić id|By usunąć filtry|By sortować|CurrentPage jest bezpiecznym numerem do widoku.
Czy całkowite page=0 jest w przyjętym helperze ograniczane do 1?|Tak|Nie, zawsze błąd|Tylko w TSX|Tylko dla pustej listy|Kontrakt dopuszcza clamping całkowitych numerów.
Czy ułamkowe page jest odrzucane?|Tak|Nie|Tylko w JSX|Tylko powyżej 1|Strona ma być bezpiecznym integerem.
Czy pageSize=0 jest poprawne?|Nie|Tak|Tylko dla pustej listy|Tylko w TSX|Rozmiar musi być dodatni.
Czy pageSize=-10 jest poprawne?|Nie|Tak|Tylko przy desc|Tylko w JSX|Ujemny rozmiar nie spełnia kontraktu.
Czy NaN jest poprawnym numerem strony?|Nie|Tak|Tylko w TSX|Tylko dla pustej listy|Nie jest bezpieczną liczbą całkowitą.
Czy Infinity jest poprawnym rozmiarem w tym helperze?|Nie|Tak|Tylko w JSX|Tylko dla total=0|Kontrakt wymaga safe integer.
Co daje Number.isSafeInteger przy walidacji?|Kontrolę całkowitości i bezpiecznej reprezentacji|Walidację statusu|Nowe id|Zamrożenie listy|Dotyczy parametrów liczbowych.
Czy walidacja TypeScript number wyklucza NaN runtime?|Nie|Tak|Tylko w strict|Tylko w TSX|Number obejmuje też wartości wymagające walidacji runtime.
Czy paginate jest generyczne?|Tak|Nie|Tylko dla Ticket|Tylko dla number|Zachowuje typ elementów.
Jaki typ elementów ma pageItems po paginate(users,...)?|Typ użytkownika ze źródła|Zawsze number|Zawsze string|Any obowiązkowo|Generyk nie gubi struktury danych.
Czy readonly wejścia wymaga mutacji przy slice?|Nie|Tak|Tylko w TSX|Tylko dla obiektów|Slice tworzy nowy kontener.
Czy pageItems ma te same obiekty co wybrany fragment źródła?|Może mieć te same referencje|Zawsze głębokie kopie|Zawsze nowe id|Zawsze stringi|Paginacja nie zmienia rekordów.
Czy paginate powinno zmieniać kolejność źródła?|Nie|Tak|Tylko w JSX|Tylko przy ostatniej stronie|Kolejność ustalono wcześniej.
Jaki jest poprawny pipeline widoku?|Filtr, sort, strona|Strona, filtr, sort|Sort tylko strony|Strona, usuwanie źródła|Najpierw wybieramy cały pasujący zbiór i jego kolejność.
Co jest problemem paginowania przed filtrem?|Można pominąć pasujące rekordy z innych fragmentów|Filter przestaje istnieć|React usuwa key|Status staje się liczbą|Wynik nawigacji nie odpowiada całemu zbiorowi dopasowań.
Co jest problemem sortowania tylko strony?|Brak globalnego porządku wyników|Sort zawsze rzuca błąd|Nie można użyć dat|Znika total|Każdy fragment jest uporządkowany osobno zamiast całej listy.
Z czego liczyć total w opisie zakresu widoku filtrowanego?|Z długości przefiltrowanego wyniku|Z długości query|Zawsze ze źródła bez filtrowania|Z pageCount|Opis dotyczy zbioru, który paginujemy.
Czy strona 2 przy 10 zaczyna się od wiersza 11?|Tak|Nie|Tylko w TSX|Tylko przy 23 rekordach|Start indeksu wynosi 10.
Czy helper powinien pokazać 21–30 przy total=23 na trzeciej stronie?|Nie, 21–23|Tak zawsze|Tylko w JSX|Tylko przy 10|To ograniczamy do faktycznej długości.
Czy zmiana query powinna resetować stronę w przyjętym UX?|Tak do 1|Nie nigdy|Tylko przy braku wyników|Zawsze do ostatniej|Nowe wyszukiwanie zaczyna się od pierwszych dopasowań.
Gdzie resetować page po edycji filtra?|W handlerze zdarzenia|W obowiązkowym efekcie po każdym renderze|W CSS|W komparatorze|To bezpośredni skutek tej akcji.
Czy prosty reset strony wymaga useEffect?|Nie|Tak|Tylko w TSX|Tylko przy ALL|Handler może ustawić obie wartości.
Czy zmiana pageSize resetuje stronę w rozwiązaniu?|Tak|Nie|Tylko do 25|Tylko w JSX|Kontrakt widoku ustawia page=1.
Czy zmiana sortowania resetuje stronę w rozwiązaniu?|Tak|Nie|Tylko przy createdAt|Tylko dla desc|To jawna decyzja wariantu Main.
Czy currentPage po clamping automatycznie aktualizuje stan page?|Nie|Tak|Tylko w TSX|Tylko przy total=0|To wartość pochodna, nie setter.
Co przekazać do Pager po clamping?|CurrentPage|Niekorygowany page zawsze|Start indeksu|From|Kontrolki mają operować na widocznym numerze.
Czy Poprzednia ma być disabled na pierwszej stronie?|Tak|Nie|Tylko dla total=0|Tylko w TSX|Nie ma wcześniejszego fragmentu.
Czy Następna ma być disabled na ostatniej?|Tak|Nie|Tylko dla total=0|Tylko w JSX|Nie ma kolejnego fragmentu.
Czy obie kontrolki brzegowe są disabled przy jednej stronie?|Tak|Nie|Tylko dla 10|Tylko w TSX|Jest jednocześnie pierwsza i ostatnia.
Co oznacza aria-current=page?|Aktualnie oglądaną stronę|Kierunek sortowania|Stan disabled|Liczbę wyników|Token opisuje pozycję w nawigacji.
Czy aria-current=strona jest poprawnym tokenem?|Nie|Tak|Tylko w JSX|Tylko przy polskiej etykiecie|Wartości ARIA nie są tłumaczonymi etykietami.
Po co nav z nazwą Paginacja?|Rozpoznawalny obszar nawigacji|Zmiana pageCount|Wyłączenie CSS|Nowy stan|Semantyka i nazwa wyjaśniają cel kontrolek.
Czy Pager powinien używać button do lokalnej akcji bez adresu URL?|Tak w tym wariancie|Nigdy|Tylko w TSX|Tylko przy 1|Przy routingu można zaprojektować linki, ale tu zmieniamy lokalny stan.
Czy same szare klasy zapewnią disabled?|Nie|Tak|Tylko w JSX|Tylko dla prev|Potrzebny jest rzeczywisty stan natywnej kontrolki.
Czy ręczne wywołanie handlera testuje natywny disabled?|Nie|Tak|Tylko w TSX|Tylko dla pagera|To omija przeglądarkową aktywację.
Czy PageSizeSelect powinien walidować wynik Number(raw) względem opcji?|Tak|Nie|Tylko przy 50|Tylko w JSX|DOM daje tekst, a API dopuszcza określone wartości.
Jakie rozmiary dopuszcza PAGE_SIZES?|10,25,50|1,2,3|10,20,30|Każdą liczbę|Lista jest źródłem opcji i typu.
Czy tekst abc w select powinien przekazać NaN do stanu rozmiaru?|Nie|Tak|Tylko w TSX|Tylko przy empty|Guard listy odrzuca nieprawidłową wartość.
Czy RangeInfo powinno mówić Brak wyników przy total=0?|Tak|Nie|Tylko w TSX|Tylko dla page=0|To sensowny opis pustego zbioru.
Co daje aria-live=polite opisowi zakresu?|Możliwość ogłoszenia zmiany bez agresywnego przerywania|Automatyczne przejście na stronę|Sortowanie|Zamrożenie listy|To mechanizm dostępności, nie logika paginacji.
Czy bardzo duży pageCount powinien zawsze tworzyć tysiące przycisków?|Nie|Tak|Tylko w TSX|Tylko z Array.from|Potrzebne jest ograniczone okno lub inny model.
Czy proste renderowanie wszystkich numerów jest akceptowalne dla kilkunastu stron?|Może być|Nigdy|Tylko w JSX|Zawsze dla dowolnej liczby|Trzeba dostosować do skali i potrzeb.
Czy test ostatniej strony powinien obejmować niepełny fragment?|Tak|Nie|Tylko w TSX|Tylko przy 23|To typowe miejsce błędu off-by-one.
Czy reset filtrów musi odtwarzać usunięte dane?|Nie|Tak|Tylko w JSX|Tylko dla ALL|Zmiana widoku nie resetuje źródła.
Czy lokalna paginacja jest tym samym co pobieranie stron z API?|Nie|Tak|Tylko w TSX|Tylko z pageSize|Tutaj wszystkie dane są już lokalnie.
Co potwierdza ukończenie paginacji?|Poprawne zakresy, brzegi, pipeline i resety|Tylko dwa przyciski|Wyłącznie slice|Tylko brak błędów TS|Wymagania obejmują dane i zachowanie UI.
`.trim().split('\n').map(line=>line.split('|'));
const metaCases=[
 ['Pusty wynik',0,1,10,'1/1/0/0/0','0/0/0/0/0','1/1/1/0/0','1/1/1/10/10'],
 ['Jeden rekord',1,1,10,'1/1/1/1/1','1/1/0/1/1','1/1/1/10/10','1/2/1/1/1'],
 ['Dokładnie dziesięć',10,1,10,'1/1/1/10/10','1/2/1/10/10','1/1/0/9/10','1/1/1/9/9'],
 ['Jedenasty rekord na początku',11,1,10,'1/2/1/10/10','1/1/1/10/10','1/2/1/11/11','2/2/11/11/1'],
 ['Jedenasty na drugiej',11,2,10,'2/2/11/11/1','1/2/1/10/10','2/2/10/11/2','2/2/11/20/10'],
 ['Pierwsza z trzech',23,1,10,'1/3/1/10/10','1/2/1/10/10','1/3/0/9/10','1/3/1/23/23'],
 ['Druga z trzech',23,2,10,'2/3/11/20/10','2/3/10/19/10','2/3/11/23/13','1/3/1/10/10'],
 ['Ostatnia niepełna',23,3,10,'3/3/21/23/3','3/3/21/30/10','3/3/20/22/3','2/3/11/20/10'],
 ['Żądanie poza końcem',23,99,10,'3/3/21/23/3','99/3/981/990/0','1/3/1/10/10','2/3/11/20/10'],
 ['Żądanie zero',23,0,10,'1/3/1/10/10','0/3/0/0/0','3/3/21/23/3','1/3/0/9/10'],
 ['Ujemny numer',23,-2,10,'1/3/1/10/10','-2/3/0/0/0','3/3/21/23/3','2/3/11/20/10'],
 ['Rozmiar większy niż dane',4,3,10,'1/1/1/4/4','3/1/21/4/0','1/1/1/10/10','1/0/0/0/0'],
 ['Sto dwadzieścia po dziesięć',120,12,10,'12/12/111/120/10','12/12/110/119/10','1/12/1/10/10','12/11/111/120/10'],
 ['Sto dwadzieścia po dwadzieścia pięć',120,5,25,'5/5/101/120/20','5/5/101/125/25','4/5/76/100/25','5/4/101/120/20'],
 ['Sto dwadzieścia po pięćdziesiąt',120,3,50,'3/3/101/120/20','3/3/101/150/50','2/3/51/100/50','3/2/101/120/20'],
 ['Dokładnie pięćdziesiąt',50,2,25,'2/2/26/50/25','2/3/26/50/25','2/2/25/49/25','1/2/1/25/25'],
 ['Jeden ponad pięćdziesiąt',51,3,25,'3/3/51/51/1','3/3/50/51/2','3/2/51/51/1','2/3/26/50/25'],
 ['Rozmiar jeden',3,2,1,'2/3/2/2/1','2/3/1/2/2','1/3/1/1/1','2/2/2/2/1'],
 ['Ostatni przy rozmiarze jeden',3,3,1,'3/3/3/3/1','3/3/2/3/2','2/3/2/2/1','3/2/3/3/1'],
 ['Pusty z dużym numerem',0,99,25,'1/1/0/0/0','99/1/0/0/0','1/0/0/0/0','1/1/1/0/0'],
 ['Pusty z ujemnym numerem',0,-1,50,'1/1/0/0/0','-1/1/0/0/0','1/1/1/0/0','0/0/0/0/0'],
 ['Dziewięć przy dziesięciu',9,1,10,'1/1/1/9/9','1/1/1/10/10','1/2/1/9/9','1/1/0/8/9'],
 ['Dwadzieścia dokładnie',20,2,10,'2/2/11/20/10','2/3/11/20/10','2/2/10/19/10','2/2/11/21/11'],
 ['Dwadzieścia jeden',21,3,10,'3/3/21/21/1','3/3/21/30/10','2/3/11/20/10','3/2/21/21/1'],
];
const metaHelper='function meta(total:number,page:number,size:number):string{const result=paginate(Array.from({length:total},(_,index)=>index+1),page,size);return [result.currentPage,result.pageCount,result.from,result.to,result.pageItems.length].join("/");}';
for(const [label,total,page,pageSize,a,b,c,d] of metaCases)quiz.push([`${label}: jaki wynik currentPage/pageCount/from/to/liczbaElementów wypisze kod?`,a,b,c,d,'Zakres liczymy od jedynki, pusty wynik ma 0–0, a żądana strona jest ograniczana do istniejącego zakresu.',paginate+'\n'+metaHelper+`\nconsole.log(meta(${total},${page},${pageSize}));`]);
const itemCases=[
 ['Pierwszy fragment','paginate([1,2,3,4,5],1,2).pageItems.join(",")','1,2','2,3','1,2,3','3,4'],
 ['Środkowy fragment','paginate([1,2,3,4,5],2,2).pageItems.join(",")','3,4','2,3','4,5','1,2'],
 ['Ostatni fragment','paginate([1,2,3,4,5],3,2).pageItems.join(",")','5','4,5','3,4','Brak'],
 ['Po clampingu','paginate([1,2,3,4,5],20,2).pageItems.join(",")','5','Brak','1,2','3,4'],
 ['Zero do pierwszej','paginate([1,2,3,4,5],0,2).pageItems.join(",")','1,2','Brak','5','3,4'],
 ['Zachowanie oryginału','(()=>{const values=[1,2,3];paginate(values,2,2);return values.join(",");})()','1,2,3','3','1,2','2,3'],
 ['Nowy kontener','(()=>{const values=[1,2,3];return Object.is(paginate(values,1,10).pageItems,values);})()','false','true','undefined','null'],
 ['Typowe obiekty','paginate([{id:7},{id:8}],2,1).pageItems[0].id','8','7','2','1'],
];
for(const [label,expr,a,b,c,d] of itemCases)quiz.push([`${label}: co wypisze wybór pageItems?`,a,b,c,d,'PageItems jest wycinkiem bez zmiany wejściowej tablicy.',paginate+'\nconsole.log('+expr+');']);
const invalidCases=[['Zerowy rozmiar','1','0'],['Ujemny rozmiar','1','-1'],['Ułamkowy rozmiar','1','2.5'],['Niefinity rozmiar','1','Infinity'],['NaN strony','NaN','10'],['Ułamkowa strona','1.5','10'],['Niefinity numer','Infinity','10'],['Niebezpieczny integer','Number.MAX_SAFE_INTEGER+1','10']];
for(const [label,page,pageSize] of invalidCases)quiz.push([`${label}: jaki wynik obsługi błędu wypisze kod?`,'RangeError','Brak błędu','TypeError','NaN','Helper jawnie odrzuca parametry spoza kontraktu; walidacja działa także dla pustej listy.',paginate+`\ntry{paginate([],${page},${pageSize});console.log("Brak błędu");}catch(error){console.log(error instanceof RangeError?"RangeError":"TypeError");}`]);
module.exports={exercises,quiz,model,paginate,pager,size,range,main};
