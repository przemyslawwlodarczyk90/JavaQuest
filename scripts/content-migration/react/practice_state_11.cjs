const pagination=require('./practice_state_08.cjs').paginate;
const model='export type Product=Readonly<{id:number;name:string;brand:string;category:string;priceGrosze:number;rating:number;inStock:boolean}>;\nexport const SORT_OPTIONS=[{value:"price-asc",label:"Cena rosnąco"},{value:"price-desc",label:"Cena malejąco"},{value:"name",label:"Nazwa A–Z"},{value:"rating",label:"Ocena malejąco"}] as const;\nexport type SortOption=(typeof SORT_OPTIONS)[number]["value"];\nexport type Filters=Readonly<{query:string;category:string;onlyInStock:boolean}>;\nexport type Cart=Readonly<Partial<Record<number,number>>>;';
const logic='const COMPARE:Record<SortOption,(a:Product,b:Product)=>number>={"price-asc":(a,b)=>a.priceGrosze-b.priceGrosze,"price-desc":(a,b)=>b.priceGrosze-a.priceGrosze,name:(a,b)=>a.name.localeCompare(b.name,"pl"),rating:(a,b)=>b.rating-a.rating};\nexport function visibleProducts(products:readonly Product[],filters:Filters,sort:SortOption):Product[]{const q=filters.query.trim().toLowerCase();return products.filter(product=>(filters.category==="ALL"||product.category===filters.category)&&(!filters.onlyInStock||product.inStock)&&(q===""||product.name.toLowerCase().includes(q)||product.brand.toLowerCase().includes(q))).toSorted(COMPARE[sort]);}\nexport function categoriesOf(products:readonly Product[]):string[]{return [...new Set(products.map(product=>product.category))].toSorted((a,b)=>a.localeCompare(b,"pl"));}\nconst pln=new Intl.NumberFormat("pl-PL",{style:"currency",currency:"PLN",useGrouping:"always"});\nexport function formatPrice(grosze:number):string{if(!Number.isSafeInteger(grosze)||grosze<0)throw new RangeError("Cena musi być nieujemną bezpieczną liczbą groszy");return pln.format(grosze/100);}\nexport function addToCart(cart:Cart,product:Product):Cart{if(!product.inStock)return cart;return {...cart,[product.id]:(cart[product.id]??0)+1};}\nexport function cartCount(cart:Cart):number{return Object.values(cart).reduce<number>((sum,count)=>sum+(count??0),0);}\nexport function cartTotal(cart:Cart,products:readonly Product[]):number{return products.reduce((sum,product)=>sum+(cart[product.id]??0)*product.priceGrosze,0);}\nexport function isSortOption(value:string):value is SortOption{return SORT_OPTIONS.some(option=>option.value===value);}';
const data='import type {Product} from "./model";\nconst CATEGORIES=["Laptopy","Monitory","Akcesoria","Audio"] as const;\nconst NAMES=["Laptop","Monitor","Akcesorium","Słuchawki"] as const;\nconst BRANDS=["Lumen","Orbis","Zenit","Polar"] as const;\nexport const products:readonly Product[]=Array.from({length:57},(_,index)=>({id:index+1,name:`${NAMES[index%4]} ${100+index}`,brand:BRANDS[(index*7)%4],category:CATEGORIES[index%4],priceGrosze:4999+((index*3779)%600000),rating:1+((index*13)%41)/10,inStock:index%5!==0}));';
const stars='export function RatingStars({rating}:{rating:number}){const safe=Math.min(5,Math.max(0,Number.isFinite(rating)?rating:0));const full=Math.round(safe);return <span role="img" aria-label={`Ocena ${safe.toLocaleString("pl-PL",{minimumFractionDigits:1,maximumFractionDigits:1})} z 5`}><span aria-hidden="true">{Array.from({length:5},(_,index)=>index<full?"★":"☆").join("")}</span></span>;}';
const card='import type {Product} from "./model";\nimport {formatPrice} from "./logic";\nimport styles from "./Catalog.module.css";\n'+stars+'\nexport function ProductCard({product,onAdd}:{product:Product;onAdd:(id:number)=>void}){return <article className={styles.card}><h2>{product.name}</h2><p>{product.brand}</p><p>{formatPrice(product.priceGrosze)}</p><RatingStars rating={product.rating}/>{!product.inStock&&<p className={styles.unavailable}>Brak</p>}<button type="button" disabled={!product.inStock} onClick={()=>onAdd(product.id)}>Dodaj do koszyka</button></article>;}';
const catalog='import {useState} from "react";\nimport type {Product,SortOption,Cart} from "./model";\nimport {SORT_OPTIONS} from "./model";\nimport {visibleProducts,categoriesOf,addToCart,cartCount,isSortOption} from "./logic";\nimport {paginate} from "./pagination";\nimport {ProductCard} from "./ProductCard";\nimport styles from "./Catalog.module.css";\nexport function Catalog({products}:{products:readonly Product[]}){const [query,setQuery]=useState("");const [category,setCategory]=useState("ALL");const [onlyInStock,setOnlyInStock]=useState(false);const [sort,setSort]=useState<SortOption>("price-asc");const [page,setPage]=useState(1);const [cart,setCart]=useState<Cart>({});const categories=categoriesOf(products);const visible=visibleProducts(products,{query,category,onlyInStock},sort);const result=paginate(visible,page,12);const count=cartCount(cart);function resetFilters(){setQuery("");setCategory("ALL");setOnlyInStock(false);setPage(1);}function handleAdd(id:number){const product=products.find(item=>item.id===id);if(product?.inStock)setCart(previous=>addToCart(previous,product));}return <main className={styles.catalog}><header><h1>Katalog</h1><p aria-live="polite">Koszyk: {count} szt.</p></header><section className={styles.filters} aria-label="Filtry produktów"><label>Szukaj po nazwie lub marce<input type="search" value={query} onChange={event=>{setQuery(event.currentTarget.value);setPage(1);}}/></label><label>Kategoria<select value={category} onChange={event=>{setCategory(event.currentTarget.value);setPage(1);}}><option value="ALL">Wszystkie</option>{categories.map(value=><option key={value} value={value}>{value}</option>)}</select></label><label><input type="checkbox" checked={onlyInStock} onChange={event=>{setOnlyInStock(event.currentTarget.checked);setPage(1);}}/>Tylko dostępne</label><label>Sortowanie<select value={sort} onChange={event=>{const next=event.currentTarget.value;if(isSortOption(next)){setSort(next);setPage(1);}}}>{SORT_OPTIONS.map(option=><option key={option.value} value={option.value}>{option.label}</option>)}</select></label></section><p aria-live="polite">{visible.length===0?"Brak wyników":`Produkty ${result.from}–${result.to} z ${visible.length}`}</p>{visible.length===0?<section><p>Brak produktów spełniających kryteria</p><button type="button" onClick={resetFilters}>Wyczyść filtry</button></section>:<div className={styles.grid}>{result.pageItems.map(product=><ProductCard key={product.id} product={product} onAdd={handleAdd}/>)}</div>}<nav aria-label="Strony katalogu"><button type="button" disabled={result.currentPage===1} onClick={()=>setPage(result.currentPage-1)}>Poprzednia</button><span>Strona {result.currentPage} z {result.pageCount}</span><button type="button" disabled={result.currentPage===result.pageCount} onClick={()=>setPage(result.currentPage+1)}>Następna</button></nav></main>;}';
const project={
 'src/model.ts':model,'src/logic.ts':'import type {Product,Filters,SortOption,Cart} from "./model";\nimport {SORT_OPTIONS} from "./model";\n'+logic,
 'src/pagination.ts':pagination,'src/data.ts':data,'src/ProductCard.tsx':card,'src/Catalog.tsx':catalog,
 'src/Catalog.module.css':'.catalog{max-width:75rem;margin:0 auto;padding:var(--space-4);color:var(--text);}.filters{display:flex;flex-wrap:wrap;gap:var(--space-4);margin-block:var(--space-4);}.filters label{display:flex;align-items:center;gap:var(--space-2);flex-wrap:wrap;}.grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(min(100%,15rem),1fr));gap:var(--space-4);}.card{background:var(--surface);color:var(--text);border:1px solid var(--border);border-radius:var(--radius);padding:var(--space-4);}.card h2{font-size:var(--font-lg);}.unavailable{color:var(--color-danger);} .catalog button,.catalog input,.catalog select{font:inherit;padding:var(--space-2);background:var(--surface);color:var(--text);border:1px solid var(--border);border-radius:var(--radius);}.catalog button:disabled{opacity:0.5;cursor:not-allowed;}.catalog button:focus-visible,.catalog input:focus-visible,.catalog select:focus-visible{outline:2px solid var(--color-primary);outline-offset:2px;}.catalog nav{display:flex;align-items:center;gap:var(--space-4);margin-top:var(--space-4);flex-wrap:wrap;}',
 'src/tokens.css':require('./practice_style_03.cjs').tokens,
 'src/App.tsx':'import "./tokens.css";\nimport {products} from "./data";\nimport {Catalog} from "./Catalog";\nexport default function App(){return <Catalog products={products}/>;}',
 'src/main.tsx':'import {createRoot} from "react-dom/client";\nimport App from "./App";\nconst container=document.getElementById("root");if(!container)throw Error("Brak root");createRoot(container).render(<App/>);'
};
const standalone=model+'\n'+logic;
const exercises=[
 ['Zdefiniuj Product, Filters, SortOption i Cart dla katalogu.','Cena jest całkowitą liczbą groszy, koszyk mapą ilości per id.',model],
 ['Przygotuj generator 57 produktów z oceną w zakresie 1–5. Popraw wzór mogący dawać 5.9.','Modulo 41 daje kroki od 0 do 40, które po podzieleniu przez 10 i dodaniu 1 mieszczą się do 5.','Użyj rating:1+((index*13)%41)/10. Wzór z modulo 50 może dać 5.9 i nie pasuje do skali pięciogwiazdkowej. Sprawdź wszystkie wygenerowane oceny testem, nie tylko pierwszą.'],
 ['Rozpisz dokładnie sześć stanów Catalog i wartości wyliczane.','Produkty są wejściem, a wynik pipeline nie jest dodatkowym stanem.','Stany: query, category, onlyInStock, sort, page, cart. Wyliczane: categories, visible, pageCount/currentPage/range/pageItems, cartCount i ewentualna suma koszyka. Stała liczba 12 nie wymaga useState.'],
 ['Napisz case-insensitive wyszukiwanie po nazwie lub marce z trim.','Oba pola porównuj z tą samą normalizacją.',standalone],
 ['Dodaj kategorię ALL i tylko dostępne; połącz warunki filtrów.','Każde ograniczenie musi być spełnione.',standalone],
 ['Wylicz unikalne kategorie z całej listy produktów.','Filtr nie powinien przypadkowo usuwać opcji kategorii ze swojego źródła.',standalone],
 ['Napisz cztery komparatory w Record<SortOption,...>.','Cena i ocena są liczbami, nazwa wymaga polskiego porządku.',standalone],
 ['Sortuj wynik bez mutacji produktów.','ToSorted zwraca nową tablicę.',standalone],
 ['Połącz visibleProducts i generyczne paginate z rozmiarem 12.','Pipeline to filtr→sort→strona.',standalone+'\n'+pagination+'\nexport function productPage(products:readonly Product[],filters:Filters,sort:SortOption,page:number){return paginate(visibleProducts(products,filters,sort),page,12);}'],
 ['Podaj zakresy dla 57 produktów po 12.','Ostatnia strona jest niepełna.','Pięć stron: 1–12, 13–24, 25–36, 37–48, 49–57. Ostatnia ma 9 produktów.'],
 ['Napisz formatPrice z Intl.NumberFormat i ceną w groszach.','Sprawdź cenę wejściową i dziel przez 100 tylko do formatowania.',standalone],
 ['Wyjaśnij, dlaczego dokładne spacje w sformatowanej walucie mogą być nierozdzielające.','Nie testuj formatowania przez założenie zwykłego ASCII space.','Intl może używać spacji nierozdzielających między grupami i walutą. Testuj formatToParts albo świadomie normalizuj rodzaj białych znaków. Wartość 129900 gr odpowiada 1 299,00 zł, ale zapis znaków odstępu zależy od danych locale.'],
 ['Napisz RatingStars z dokładną etykietą liczbową i zaokrąglonymi pięcioma ikonami.','Czytnik nie musi czytać pięciu dekoracyjnych znaków.',stars],
 ['Zaprojektuj test ocen 0,3.2,4.8,5,NaN i 5.9 dla warstwy prezentacji.','Dane źródłowe powinny być walidowane; komponent dodatkowo ogranicza wizualizację.','RatingStars pokazuje 0→0 gwiazdek,3.2→3,4.8→5,5→5. NaN ma bezpieczny wynik 0, a 5.9 zostaje ograniczone do 5 w prezentacji. Generator produktów i walidacja domeny nadal powinny dostarczać poprawne dane.'],
 ['Zbuduj ProductCard: nazwa, marka, PLN, ocena, Brak i przycisk.','Niedostępność ma tekst i natywny disabled.','Patrz kompletny ProductCard.tsx w rozwiązaniu końcowym. Użyj istniejącego modelu Product, formatPrice oraz RatingStars. OnAdd otrzymuje product.id, a brak magazynu ustawia disabled={!product.inStock} oraz widoczny napis Brak.'],
 ['Aktualizuj koszyk niemutowalnie przez funkcyjny setter.','Ilość dla brakującego id zaczyna się od 0.',standalone],
 ['Odrzuć dodanie niedostępnego produktu także w logice.','Natywne disabled jest interfejsem, guard jest kontraktem operacji.',standalone],
 ['Policz koszyk jako sumę sztuk, nie liczbę różnych kluczy.','Dwa identyczne produkty to dwie sztuki.',standalone],
 ['Policz wartość koszyka w całkowitych groszach.','Nie sumuj wcześniej zaokrąglonych napisów PLN.',standalone],
 ['Resetuj page po query, kategorii, dostępności i sortowaniu.','Resety należą do handlerów tych zmian.','W każdym z czterech handlerów ustaw odpowiednią wartość i setPage(1). Nie dodawaj efektu obserwującego filtry. W Pagerze używaj result.currentPage, bo zewnętrzne dane także mogą zmniejszyć liczbę stron.'],
 ['Zdefiniuj Wyczyść filtry bez kasowania koszyka.','Wyjaśnij, czy sortowanie jest częścią tej akcji.','W tym wariancie resetuje query="",category="ALL",onlyInStock=false,page=1. Sort i cart pozostają. Jeśli produkt wymaga także resetu sortu, nazwij to i zaimplementuj jawnie.'],
 ['Wyświetl pusty stan z resetem i poprawnym zakresem zero.','Brak wyników nie jest błędem pobierania.','Pokaż Brak produktów spełniających kryteria oraz Wyczyść filtry. Nie twórz opisu Produkty 1–0. Kontrolki brzegowe jednej pustej strony są wyłączone.'],
 ['Zachowaj key=product.id po zmianie filtrów i sortowania.','Tożsamość nie zależy od aktualnego indeksu.','Mapuj pageItems do ProductCard z key={product.id}. Nie używaj indeksu strony ani losowego key. OnAdd nadal dostaje jawnie product.id; key nie jest zwykłym propsem.'],
 ['Zaprojektuj test niezależności koszyka od filtrowania.','Filtr zmienia widoczność, nie zawartość cart.','Dodaj dwie sztuki produktu, ukryj go filtrem kategorii, sprawdź niezmienioną sumę sztuk. Wyczyść filtry: karta wraca, koszyk pozostaje. Niedostępnego produktu nie da się dodać zwykłą aktywacją.'],
 ['Wykaż błędem TS nieobsługiwane sortowanie discount.','Unia pochodzi z jednej listy opcji.',model+'\n// @ts-expect-error Brak opcji discount\nconst invalid:SortOption="discount";'],
 ['Zaprojektuj test ceny, rankingu i stabilnego remisu.','Równa cena nie oznacza tego samego produktu.','Dla dwóch równych cen zachowaj względny porządek wejścia przy komparatorze 0. Oceny sortuj po wartości liczbowej, nie liczbie zaokrąglonych gwiazdek. Nazwy sprawdź z polskimi literami.'],
 ['Zaplanuj test strony 5 po zawężeniu do jednej kategorii.','Zmiana filtra wraca na 1.','Przejdź na stronę 5, zmień kategorię: page=1, widoczne pierwsze 12 dopasowań lub mniej, zakres i przyciski zgodne z nowym total. Sprawdź także brak wyników i reset.'],
 ['Zaprojektuj walidację danych wejściowych przed użyciem katalogu.','Typy nie chronią przed dowolną odpowiedzią zewnętrzną.','Sprawdź unikalne id, nieujemne bezpieczne całkowite priceGrosze, skończone rating w skali 0–5 oraz poprawne teksty i boolean inStock. Dane generatora mają oceny 1–5. Walidacja nie powinna polegać na as Product[].'],
 ['Wyjaśnij ograniczenia lokalnej listy i koszyka w tym bossie.','Nie obiecuj trwałości ani autoryzacji przez disabled.','Stan znika po odmontowaniu lub odświeżeniu bez osobnego zapisu. Katalog działa na lokalnych danych; potwierdzanie cen i dostępności w rzeczywistym sklepie wymaga serwera. To ćwiczenie mechaniki UI, nie pełny checkout.'],
 ['Oddaj kompletny Catalog: sześć stanów, czyste funkcje, karty i pipeline.','Projekt ma działać bez redundantnego stanu wyników.','Pliki projektu:\n'+Object.entries(project).map(([file,code])=>`--- ${file} ---\n${code}`).join('\n\n')],
];
// Samodzielne rozwiązanie karty, uzupełnione po definicji wspólnych fragmentów.
exercises[14][2]=standalone+'\n'+stars+'\nexport function ProductCard({product,onAdd}:{product:Product;onAdd:(id:number)=>void}){return <article><h2>{product.name}</h2><p>{product.brand}</p><p>{formatPrice(product.priceGrosze)}</p><RatingStars rating={product.rating}/>{!product.inStock&&<p>Brak</p>}<button type="button" disabled={!product.inStock} onClick={()=>onAdd(product.id)}>Dodaj do koszyka</button></article>;}';
const quiz=`
Ile stanów wymaga model katalogu tego bossa?|6|4|8|12|Query,category,onlyInStock,sort,page,cart.
Czy produkty przekazane w props muszą być kopiowane do kolejnego stanu?|Nie|Tak|Tylko w TSX|Tylko dla 57|Katalog nie edytuje źródłowej listy.
Czy visibleProducts powinno być osobnym stanem synchronizowanym efektem?|Nie|Tak|Tylko w JSX|Tylko w TSX|To wynik filtrów i sortowania.
Czy licznik koszyka jest niezależnym stanem?|Nie|Tak|Tylko przy 0|Tylko w JSX|Wylicza się z zawartości cart.
Co przechowuje cart?|Id produktów i ilości|Wyłącznie nazwy kart|Wyłącznie sumę PLN|Wynik filtrowania|To źródło danych koszyka.
Czy liczba różnych kluczy cart zawsze równa się liczbie sztuk?|Nie|Tak|Tylko w TSX|Tylko dla dostępnych|Jeden klucz może mieć ilość większą od 1.
Po jakich polach działa tekst wyszukiwania?|Nazwa lub marka|Wyłącznie id|Wyłącznie cena|Tylko kategoria|To jawny kontrakt bossa.
Czy query jest trimowane i lowercased do porównania?|Tak|Nie|Tylko w TSX|Tylko dla brand|Skrajne spacje i wielkość liter nie mają ograniczać dopasowania.
Czy lowercase usuwa polskie diakrytyki?|Nie|Tak|Tylko w JSX|Tylko przy nazwie|To odrębne wymaganie normalizacji.
Czy filtr kategorii i dostępności łączy się z query przez AND?|Tak|Nie|Tylko w TSX|Tylko dla ALL|Produkt musi spełniać wszystkie aktywne ograniczenia.
Co oznacza onlyInStock=false?|Brak ograniczenia dostępności|Tylko niedostępne|Wyłącznie dostępne|Pusty katalog|Wyłączony filtr nie usuwa produktów.
Co oznacza category=ALL?|Brak ograniczenia kategorii|Nową kategorię każdego produktu|Wyłączenie wszystkich filtrów|Sort po marce|Pozostałe filtry nadal działają.
Z czego wyliczać opcje kategorii w tym wariancie?|Z całej listy produktów|Z aktualnej strony wyłącznie|Z query|Z koszyka|Opcje nie znikają przypadkowo po zawężeniu widoku.
Czy Set może usunąć powtórzenia nazw kategorii?|Tak|Nie|Tylko w TSX|Tylko dla 4|Zbiór przechowuje unikalne wartości.
Jaką kolejność ma pipeline?|Filtr→sort→strona|Strona→filtr→sort|Sort wyłącznie strony|Koszyk→strona→usunięcie|Najpierw wyznacza się cały pasujący porządek.
Ile produktów jest na stronie w bossie?|12|10|25|50|To stały rozmiar widoku.
Czy rozmiar 12 musi mieć useState, jeśli nie ma kontrolki zmiany?|Nie|Tak|Tylko w JSX|Tylko w TSX|Stała nie jest decyzją zmienianą w UI.
Ile stron daje 57 produktów po 12?|5|4|6|12|Ostatni fragment ma 9 rekordów.
Jaki jest zakres drugiej strony przy 57 produktach?|13–24|12–23|1–12|25–36|Numeracja użytkownika zaczyna się od 1.
Jaki jest zakres ostatniej strony?|49–57|49–60|48–56|45–57|To ograniczamy do liczby rekordów.
Czy zmiana sortowania wraca na stronę 1 w bossie?|Tak|Nie|Tylko w TSX|Tylko przy rating|Wymaganie obejmuje każdy wybór sortu.
Czy reset strony po filtrze wymaga useEffect?|Nie|Tak|Tylko w JSX|Tylko dla kategorii|To część handlera zmiany.
Co przechowywać jako sort przy czterech opcjach dropdown?|Jedną wartość SortOption|Obowiązkowo dwa niezależne stany pola i kierunku|Całą listę wyników|Każdy comparator osobno w stanie|Wybór już opisuje pole i kierunek.
Czy price-asc porównuje napisy sformatowanej waluty?|Nie, liczbowe grosze|Tak|Tylko w TSX|Tylko dla PLN|Prezentacja nie jest danymi sortowania.
Czy rating sortuje po zaokrąglonej liczbie gwiazdek?|Nie, po surowej poprawnej ocenie|Tak|Tylko w JSX|Tylko przy 5|Różne oceny mogą mieć tę samą liczbę ikon.
Jak sortować polskie nazwy?|LocaleCompare z pl|Wyłącznie po długości|Zawsze po id|Zwykłym JSON.stringify|Potrzebny jest językowy porządek nazw.
Czy toSorted zmienia źródłowe products?|Nie|Tak|Tylko w TSX|Tylko dla price|Zwraca nowy kontener.
Czy ProductCard powinien mieć key=index strony?|Nie|Tak|Tylko w JSX|Tylko przy 12|Tożsamość karty wynika z id produktu.
Czy key jest zwykłym propsem onAdd?|Nie|Tak|Tylko w TSX|Tylko dla number|Id potrzebne callbackowi przekazuje się jawnie.
W jakiej jednostce przechowywać ceny w modelu?|Całkowite grosze|Sformatowane napisy PLN|Zawsze zmiennoprzecinkowe złote|CSS pixels|Operacje kwot wykonuje się na liczbach całkowitych.
Kiedy dzielić grosze przez 100?|Przy formatowaniu do złotych|Przed każdą operacją koszyka|Nigdy|Przy generowaniu id|Wewnętrzna suma pozostaje w groszach.
Jakiego narzędzia używa formatPrice?|Intl.NumberFormat dla pl-PL i PLN|Wyłącznie konkatenacji z przecinkiem|Date.parse|LocaleCompare|Formatowanie waluty uwzględnia locale.
Czy wynik Intl może zawierać spacje nierozdzielające?|Tak|Nie|Tylko w TSX|Tylko przy 0|Nie należy zakładać wyłącznie ASCII space.
Czy cena -1 gr jest poprawna w przyjętym formatPrice?|Nie|Tak|Tylko w JSX|Tylko dla promocji|Kontrakt helpera wymaga nieujemnej ceny.
Czy ułamkowe grosze przejdą walidację formatPrice?|Nie|Tak|Tylko w TSX|Tylko przy małych cenach|Wymagany jest safe integer.
Czy generator ocen z modulo 50 może dać więcej niż 5?|Tak|Nie|Tylko w JSX|Tylko w TSX|Wzór 1+reszta/10 może dojść do 5.9.
Dlaczego wariant generatora używa modulo 41?|By uzyskać maksymalnie 5.0|By zwiększyć cenę|By tworzyć 41 kategorii|By zmienić id|Reszta jest od 0 do 40.
Czy walidacja domeny i ograniczanie wizualizacji gwiazdek to to samo?|Nie|Tak|Tylko w TSX|Tylko dla rating|Prezentacja nie powinna zastępować poprawnych danych wejściowych.
Czy pięć dekoracyjnych znaków gwiazdek wystarczy jako dokładny opis oceny 4.8?|Nie|Tak|Tylko w JSX|Tylko w dark|Etykieta liczbowa zachowuje precyzję oceny.
Po co aria-hidden na znakach gwiazdek przy liczbowej nazwie?|By nie dublować dekoracji w czytniku|By usunąć ocenę wizualnie|By zmienić sort|By dodać produkt|Semantyczna nazwa jest na nadrzędnym elemencie.
Co pokaże niedostępna karta?|Tekst Brak i disabled przy dodawaniu|Wyłącznie czerwony kolor|Zawsze ukryty produkt|Błąd serwera|Status ma tekst oraz zachowanie kontrolki.
Czy sam disabled stanowi autoryzację dodawania w prawdziwym sklepie?|Nie|Tak|Tylko w TSX|Tylko dla koszyka|To zachowanie UI, nie zabezpieczenie serwera.
Czy addToCart w rozwiązaniu przyjmie niedostępny produkt?|Nie|Tak|Tylko w JSX|Tylko przy pustym cart|Logika też ma guard dostępności.
Jak dodać sztukę bez mutacji?|Nowy obiekt z ilością poprzednią plus 1|Przypisanie cart[id]++ na starym stanie|Push do produktu|Zmiana id|Updater korzysta z poprzedniej zawartości.
Czy szybkie dwa dodania powinny używać formy funkcyjnej setCart?|Tak|Nie|Tylko w TSX|Tylko dla id=1|Każda aktualizacja zależy od poprzedniej ilości.
Czy filtrowanie powinno usuwać produkty z koszyka?|Nie|Tak|Tylko w JSX|Tylko gdy znikają z bieżącej strony|To odrębne źródło stanu.
Czy Wyczyść filtry kasuje cart w przyjętym wariancie?|Nie|Tak|Tylko w TSX|Tylko przy pustym wyniku|Reset dotyczy filtrów i strony.
Czy Wyczyść filtry resetuje sort w przyjętym wariancie?|Nie|Tak|Tylko w JSX|Tylko dla rating|Zakres resetu jest jawnie określony.
Czy brak wyników powinien pokazać Produkty 1–0?|Nie|Tak|Tylko w TSX|Tylko przy page=1|Potrzebny jest poprawny pusty opis.
Co powinien oferować pusty stan?|Komunikat i reset filtrów|Usunięcie koszyka|Automatyczne pobranie dowolnych danych|Ukrycie całego nagłówka|Użytkownik ma drogę do szerszego wyniku.
Czy lista kategorii musi być stanem?|Nie|Tak|Tylko w JSX|Tylko przy Set|Można ją wyliczyć z produktów.
Czy cartCount musi wymagać efektu po każdej zmianie cart?|Nie|Tak|Tylko w TSX|Tylko przy 0|To zwykła suma bieżących ilości.
Czy cartTotal sumuje sformatowane napisy waluty?|Nie|Tak|Tylko w JSX|Tylko dla PLN|Sumuje całkowite kwoty jednostkowe razy ilości.
Czy TypeScript sam zweryfikuje zewnętrzne Product[] w runtime?|Nie|Tak|Tylko w strict|Tylko po as|Dane zewnętrzne mogą wymagać walidacji.
Czy as Product[] jest walidatorem cen i ocen?|Nie|Tak|Tylko w TSX|Tylko dla generatora|Asercja nie wykonuje sprawdzenia wartości.
Czy lokalny koszyk bez zapisu przetrwa każde odświeżenie?|Nie|Tak|Tylko w JSX|Tylko z Partial|Trwałość wymaga osobnego mechanizmu.
Czy kontrolowany select sortowania daje wartość DOM string?|Tak|Nie|Tylko w TSX|Tylko dla price|Guard może zawęzić ją do SortOption.
Czy nieznane sortowanie discount powinno przejść guard?|Nie|Tak|Tylko w JSX|Tylko w TSX|Dozwolone opcje są jawne.
Czy poprawny statyczny HTML dowodzi działania koszyka i nawigacji stron?|Nie|Tak|Tylko dla 57|Tylko w TSX|Interakcje i aktualizacje trzeba sprawdzić osobno.
Co potwierdza ukończenie bossa?|Spójny katalog, filtry, sort, strony i niemutowalny koszyk|Sama siatka kart|Tylko generator danych|Wyłącznie brak błędów składni|Wszystkie wymagania muszą działać razem.
`.trim().split('\n').map(line=>line.split('|'));
const sample='export const products:readonly Product[]=[{id:1,name:"Ładowarka",brand:"Lumen",category:"Akcesoria",priceGrosze:129900,rating:4.5,inStock:true},{id:2,name:"Monitor",brand:"Orbis",category:"Monitory",priceGrosze:89999,rating:4.8,inStock:false},{id:3,name:"Audio Mini",brand:"Zenit",category:"Audio",priceGrosze:4999,rating:3.2,inStock:true},{id:4,name:"Laptop",brand:"Polar",category:"Laptopy",priceGrosze:349900,rating:4.8,inStock:true},{id:5,name:"Monitor Pro",brand:"Lumen",category:"Monitory",priceGrosze:129900,rating:4.1,inStock:true}];';
const cases=[
 ['Cena rosnąco','ids(visibleProducts(products,all,"price-asc"))','3,2,1,5,4','4,1,5,2,3','1,2,3,4,5','3,1,5,4'],
 ['Cena malejąco','ids(visibleProducts(products,all,"price-desc"))','4,1,5,2,3','3,2,1,5,4','5,1,4,2,3','1,2,3,4,5'],
 ['Nazwa po polsku','ids(visibleProducts(products,all,"name"))','3,4,1,2,5','1,2,3,4,5','4,3,1,5,2','5,2,1,4,3'],
 ['Ocena malejąco z remisem','ids(visibleProducts(products,all,"rating"))','2,4,1,5,3','4,2,1,5,3','3,5,1,2,4','1,2,3,4,5'],
 ['Marka Lumen','ids(visibleProducts(products,{...all,query:" LUMEN "},"price-asc"))','1,5','1','5','Brak'],
 ['Nazwa Monitor','ids(visibleProducts(products,{...all,query:"monitor"},"price-asc"))','2,5','5','2','Brak'],
 ['Monitor tylko dostępny','ids(visibleProducts(products,{...all,query:"monitor",onlyInStock:true},"price-asc"))','5','2,5','2','Brak'],
 ['Kategoria Audio','ids(visibleProducts(products,{...all,category:"Audio"},"name"))','3','Brak','1','4'],
 ['Kategoria Monitory','ids(visibleProducts(products,{...all,category:"Monitory"},"price-desc"))','5,2','2,5','5','Brak'],
 ['Dostępne produkty','ids(visibleProducts(products,{...all,onlyInStock:true},"price-asc"))','3,1,5,4','3,2,1,5,4','1,3,4,5','Brak'],
 ['Brak dopasowania','ids(visibleProducts(products,{...all,query:"telefon"},"name"))','Brak','1,2,3,4,5','3','2'],
 ['Nieusuwane diakrytyki','ids(visibleProducts(products,{...all,query:"lad"},"name"))','Brak','1','4','1,4'],
 ['Polskie litery uppercase','ids(visibleProducts(products,{...all,query:"ŁAD"},"name"))','1','Brak','4','1,4'],
 ['Nieznana kategoria','ids(visibleProducts(products,{...all,category:"Telefony"},"name"))','Brak','1,2,3,4,5','1','2'],
 ['Unikalne kategorie','categoriesOf(products).join(",")','Akcesoria,Audio,Laptopy,Monitory','Monitory,Audio,Laptopy,Akcesoria','Audio,Monitory','ALL,Akcesoria,Audio,Laptopy,Monitory'],
 ['Suma sztuk','cartCount(cart)','3','2','1','0'],
 ['Suma groszy','cartTotal(cart,products)','264799','134899','259800','2647.99'],
 ['Ilość pierwszego produktu','cart[1]','2','1','3','undefined'],
 ['Ilość trzeciego produktu','cart[3]','1','3','2','undefined'],
 ['Liczba różnych pozycji','Object.keys(cart).length','2','3','1','0'],
 ['Pusty koszyk','cartCount({})','0','1','NaN','undefined'],
 ['Odmowa niedostępnego','Object.is(addToCart(cart,products[1]),cart)','true','false','undefined','null'],
 ['Dodatkowa dostępna sztuka','cartCount(addToCart(cart,products[0]))','4','3','2','1'],
 ['Niemutowany stary koszyk','(()=>{addToCart(cart,products[0]);return cart[1];})()','2','3','1','undefined'],
 ['Nowa wersja koszyka','Object.is(addToCart(cart,products[0]),cart)','false','true','undefined','null'],
 ['Pierwsza strona małej listy','paginate(products,1,12).pageItems.length','5','12','1','0'],
 ['Druga strona małej listy po clamping','paginate(products,2,12).currentPage','1','2','0','5'],
 ['Zakres drugiej strony 57','range57(2)','13/24/5/12','12/23/5/12','13/24/4/12','25/36/5/12'],
 ['Zakres ostatniej strony 57','range57(5)','49/57/5/9','49/60/5/12','48/56/5/9','49/57/4/9'],
 ['Page poza końcem 57','range57(99)','49/57/5/9','1189/1200/5/0','1/12/5/12','Brak'],
 ['Prawidłowy sort','isSortOption("rating")','true','false','rating','undefined'],
 ['Nieznany sort','isSortOption("discount")','false','true','discount','undefined'],
 ['Format ceny 129900','formatPrice(129900).replace(/\\s/g," ")','1 299,00 zł','129900 zł','129,90 zł','1 299 zł'],
 ['Format ceny 4999','formatPrice(4999).replace(/\\s/g," ")','49,99 zł','4 999,00 zł','499,90 zł','49 zł'],
 ['Format zera','formatPrice(0).replace(/\\s/g," ")','0,00 zł','0 zł','NaN zł','0,01 zł'],
 ['Suma pustego koszyka','cartTotal({},products)','0','NaN','undefined','null'],
 ['Wyszukiwanie i kategoria łącznie','ids(visibleProducts(products,{...all,query:"lumen",category:"Monitory"},"name"))','5','1,5','1','Brak'],
 ['Wyszukiwanie i brak dostępności','ids(visibleProducts(products,{...all,query:"orbis",onlyInStock:true},"name"))','Brak','2','5','1,2'],
 ['Zachowana kolejność źródła','(()=>{visibleProducts(products,all,"price-asc");return ids(products);})()','1,2,3,4,5','3,2,1,5,4','4,1,5,2,3','3,4,1,2,5'],
 ['Kwota jednej dodatkowej sztuki','cartTotal(addToCart({},products[2]),products)','4999','49.99','129900','0'],
];
const codeBase=standalone+'\n'+pagination+'\n'+sample+'\nconst all:Filters={query:"",category:"ALL",onlyInStock:false};const cart=addToCart(addToCart(addToCart({},products[0]),products[0]),products[2]);function ids(items:readonly Product[]):string{return items.map(product=>product.id).join(",")||"Brak";}function range57(page:number):string{const result=paginate(Array.from({length:57},(_,index)=>index),page,12);return [result.from,result.to,result.pageCount,result.pageItems.length].join("/");}';
for(const [label,expr,a,b,c,d] of cases)quiz.push([`${label}: jaki wynik wypisze logika katalogu?`,a,b,c,d,'Filtry dotyczą nazwy/marki, kategorii i dostępności. Sortowanie poprzedza paginację. Koszyk liczy sztuki i całkowite grosze bez mutacji.',codeBase+'\nconsole.log('+expr+');']);
module.exports={exercises,quiz,model,logic,data,stars,card,catalog,project,sample,standalone};
