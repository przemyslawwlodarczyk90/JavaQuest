const {lesson}=require('./practice_kit.cjs');
const shared=String.raw`export type TicketStatus="OPEN"|"IN_PROGRESS"|"CLOSED";
export type Ticket={id:number;title:string;status:TicketStatus};

// Pakiet wspólny: bez DOM i bez React Native, więc działa w obu aplikacjach.
export function filterTickets(tickets:readonly Ticket[],query:string){
  const text=query.trim().toLocaleLowerCase("pl-PL");
  return text?tickets.filter(ticket=>ticket.title.toLocaleLowerCase("pl-PL").includes(text)):[...tickets];
}

export function ticketMeta(ticket:Ticket){
  return "#"+ticket.id+" · "+ticket.status;
}

export function keyFor(ticket:Ticket){
  return String(ticket.id);
}

export type ApiClient={getTicket:(id:number)=>Promise<Ticket>;closeTicket:(id:number)=>Promise<Ticket>};

export function createApi(baseUrl:string,fetcher:typeof fetch=fetch):ApiClient{
  async function call(path:string,init?:RequestInit){
    const response=await fetcher(baseUrl+path,init);
    if(!response.ok)throw new Error("Serwer zwrócił "+response.status);
    return response.json() as Promise<Ticket>;
  }
  return {
    getTicket:id=>call("/tickets/"+id),
    closeTicket:id=>call("/tickets/"+id,{method:"PATCH",headers:{"Content-Type":"application/json"},body:JSON.stringify({status:"CLOSED"})}),
  };
}`;
const screen=String.raw`// TicketListScreen.tsx
import {useState} from "react";
import {FlatList,Pressable,StyleSheet,Text,TextInput,View} from "react-native";
import {filterTickets,keyFor,ticketMeta,type Ticket} from "@devdesk/shared/tickets";

type Props={tickets:Ticket[];onOpen:(id:number)=>void;refreshing:boolean;onRefresh:()=>void};

export function TicketListScreen({tickets,onOpen,refreshing,onRefresh}:Props){
  const [query,setQuery]=useState("");
  return (
    <View style={styles.screen}>
      <TextInput
        value={query}
        onChangeText={setQuery}
        placeholder="Szukaj ticketu"
        accessibilityLabel="Szukaj ticketu"
        style={styles.search}
      />
      <FlatList
        data={filterTickets(tickets,query)}
        keyExtractor={keyFor}
        refreshing={refreshing}
        onRefresh={onRefresh}
        ListEmptyComponent={<Text style={styles.empty}>Brak ticketów</Text>}
        renderItem={({item})=>(
          <Pressable
            onPress={()=>onOpen(item.id)}
            accessibilityRole="button"
            style={({pressed})=>[styles.row,pressed&&styles.rowPressed]}
          >
            <Text style={styles.title}>{item.title}</Text>
            <Text style={styles.meta}>{ticketMeta(item)}</Text>
          </Pressable>
        )}
      />
    </View>
  );
}

const styles=StyleSheet.create({
  screen:{flex:1,padding:16,backgroundColor:"#fff"},
  search:{borderWidth:1,borderColor:"#cbd5e1",borderRadius:8,padding:12,marginBottom:12},
  row:{paddingVertical:12,borderBottomWidth:1,borderBottomColor:"#e2e8f0"},
  rowPressed:{backgroundColor:"#f1f5f9"},
  title:{fontSize:16,fontWeight:"600"},
  meta:{fontSize:13,color:"#64748b",marginTop:2},
  empty:{textAlign:"center",marginTop:32,color:"#64748b"},
});`;
const details=String.raw`// app/tickets/[id].tsx
import {useLocalSearchParams,Stack} from "expo-router";
import {ActivityIndicator,Pressable,Text,View} from "react-native";
import {useTicket,useUpdateTicket} from "@devdesk/shared/tickets";

export default function TicketDetailsScreen(){
  const {id}=useLocalSearchParams<{id:string}>();
  const {data:ticket,isPending,isError,refetch}=useTicket(Number(id));
  const update=useUpdateTicket();

  if(isPending)return <ActivityIndicator style={{marginTop:32}}/>;
  if(isError||!ticket){
    return (
      <View style={{padding:16}}>
        <Text>Nie udało się wczytać ticketu.</Text>
        <Pressable onPress={()=>refetch()} accessibilityRole="button"><Text>Spróbuj ponownie</Text></Pressable>
      </View>
    );
  }

  return (
    <View style={{padding:16,gap:12}}>
      <Stack.Screen options={{title:"#"+ticket.id}}/>
      <Text style={{fontSize:20,fontWeight:"700"}}>{ticket.title}</Text>
      <Pressable
        onPress={()=>update.mutate({id:ticket.id,status:"CLOSED"})}
        disabled={update.isPending}
        accessibilityRole="button"
        accessibilityState={{disabled:update.isPending}}
      >
        <Text>{update.isPending?"Zamykanie…":"Zamknij ticket"}</Text>
      </Pressable>
    </View>
  );
}`;
const platform=String.raw`const COMPONENTS:Record<string,string>={
  div:"View",span:"Text",p:"Text",button:"Pressable",input:"TextInput",img:"Image",ul:"FlatList",
};

export function nativeFor(tag:string){
  return COMPONENTS[tag]??"brak odpowiednika";
}

// Prosta kontrola tekstu JSX: wyłapuje nawyki z przeglądarki przed uruchomieniem na telefonie.
export function auditRnSource(source:string){
  const problems:string[]=[];
  if(/\bclassName=/.test(source))problems.push("className nie istnieje");
  if(/\bonClick=/.test(source))problems.push("onClick zamiast onPress");
  if(/<(div|span|p|button|input|img)\b/.test(source))problems.push("element DOM w React Native");
  if(/\bwindow\.|\bdocument\./.test(source))problems.push("window lub document nie istnieją");
  if(/AsyncStorage\.setItem\(\s*["'][^"']*token/i.test(source))problems.push("token w AsyncStorage");
  if(/<ScrollView[\s\S]*\.map\(/.test(source))problems.push("długa lista w ScrollView zamiast FlatList");
  if(/<View[^>]*>\s*[^\s<{]/.test(source))problems.push("goły tekst w View");
  return problems;
}

export type Platform="android-emulator"|"ios-simulator"|"device";

// localhost z telefonu oznacza telefon, a nie komputer z backendem.
export function apiBaseUrl(platform:Platform,lanHost?:string,port=8080){
  if(platform==="android-emulator")return "http://10.0.2.2:"+port;
  if(platform==="ios-simulator")return "http://localhost:"+port;
  if(!lanHost)throw new Error("Telefon wymaga adresu komputera w sieci lub tunelu");
  return "http://"+lanHost+":"+port;
}

export function storageFor(value:"token"|"preference"|"cache"){
  return value==="token"?"expo-secure-store":"AsyncStorage";
}

export function listComponent(items:number){
  return items>20?"FlatList":"ScrollView";
}

function parts(version:string){
  return version.split(".").map(Number);
}

// Starsze wersje działają u użytkowników miesiącami, więc API musi je znać.
export function isSupported(appVersion:string,minVersion:string){
  const app=parts(appVersion),min=parts(minVersion);
  for(let i=0;i<Math.max(app.length,min.length);i++){
    const a=app[i]??0,b=min[i]??0;
    if(a!==b)return a>b;
  }
  return true;
}

export type Need={camera?:boolean;push?:boolean;background?:boolean;storePresence?:boolean;instantUpdates?:boolean;offlineRead?:boolean};

export function chooseClient(need:Need){
  if(need.push&&need.background||need.camera&&need.background||need.storePresence)return "React Native";
  return "PWA";
}

// Link głęboki odpowiada plikowi trasy w Expo Router.
export function deepLinkToRoute(url:string){
  const match=/^devdesk:\/\/(.+)$/.exec(url);
  if(!match)return null;
  const [section,id]=match[1].split("/");
  return section==="tickets"&&id?"app/tickets/[id].tsx":null;
}`;
const tests=String.raw`import {expect,test,vi} from "vitest";
import {apiBaseUrl,auditRnSource,chooseClient,deepLinkToRoute,isSupported,listComponent,nativeFor,storageFor} from "./platform";
import {createApi,filterTickets,keyFor,ticketMeta,type Ticket} from "./shared";

const tickets:Ticket[]=[{id:1,title:"Awaria logowania",status:"OPEN"},{id:2,title:"Błąd eksportu",status:"CLOSED"}];

test("wspólna logika działa bez DOM",()=>{
  expect(filterTickets(tickets," AWARIA ").map(ticket=>ticket.id)).toEqual([1]);
  expect(filterTickets(tickets,"").length).toBe(2);
  expect(filterTickets(tickets,"")).not.toBe(tickets);
  expect(ticketMeta(tickets[0])).toBe("#1 · OPEN");
  expect(keyFor(tickets[0])).toBe("1");
});

test("wspólny klient API używa przekazanego adresu",async()=>{
  const fetcher=vi.fn(async()=>new Response(JSON.stringify(tickets[0])));
  const api=createApi("http://10.0.2.2:8080/api",fetcher as unknown as typeof fetch);
  await expect(api.getTicket(1)).resolves.toEqual(tickets[0]);
  expect(fetcher).toHaveBeenCalledWith("http://10.0.2.2:8080/api/tickets/1",undefined);
  await api.closeTicket(1);
  expect((fetcher.mock.calls as unknown[][])[1][1]).toMatchObject({method:"PATCH",body:'{"status":"CLOSED"}'});
  const bad=createApi("/api",vi.fn(async()=>new Response(null,{status:500})) as unknown as typeof fetch);
  await expect(bad.getTicket(1)).rejects.toThrow("500");
});

test("odpowiedniki komponentów",()=>{
  expect(nativeFor("div")).toBe("View");
  expect(nativeFor("button")).toBe("Pressable");
  expect(nativeFor("table")).toBe("brak odpowiednika");
});

test("kontrola źródła wykrywa nawyki z przeglądarki",()=>{
  expect(auditRnSource('<View style={s.a}><Text>ok</Text></View>')).toEqual([]);
  expect(auditRnSource('<View className="a"/>')).toContain("className nie istnieje");
  expect(auditRnSource('<Pressable onClick={f}/>')).toContain("onClick zamiast onPress");
  expect(auditRnSource('<div/>')).toContain("element DOM w React Native");
  expect(auditRnSource('window.alert(1)')).toContain("window lub document nie istnieją");
  expect(auditRnSource('AsyncStorage.setItem("token",t)')).toContain("token w AsyncStorage");
  expect(auditRnSource('<ScrollView>{items.map(f)}</ScrollView>')).toContain("długa lista w ScrollView zamiast FlatList");
  expect(auditRnSource('<View>Tekst</View>')).toContain("goły tekst w View");
});

test("adres API zależy od miejsca uruchomienia",()=>{
  expect(apiBaseUrl("android-emulator")).toBe("http://10.0.2.2:8080");
  expect(apiBaseUrl("ios-simulator",undefined,9000)).toBe("http://localhost:9000");
  expect(apiBaseUrl("device","192.168.1.20")).toBe("http://192.168.1.20:8080");
  expect(()=>apiBaseUrl("device")).toThrow("Telefon wymaga");
});

test("magazyn i lista",()=>{
  expect(storageFor("token")).toBe("expo-secure-store");
  expect(storageFor("preference")).toBe("AsyncStorage");
  expect(listComponent(1000)).toBe("FlatList");
  expect(listComponent(5)).toBe("ScrollView");
});

test("wersja aplikacji a minimalna wersja API",()=>{
  expect(isSupported("2.1.0","2.0.0")).toBe(true);
  expect(isSupported("1.9.9","2.0.0")).toBe(false);
  expect(isSupported("2.10.0","2.9.0")).toBe(true);
  expect(isSupported("2.0","2.0.0")).toBe(true);
});

test("wybór klienta i link głęboki",()=>{
  expect(chooseClient({push:true,background:true})).toBe("React Native");
  expect(chooseClient({camera:true,background:true})).toBe("React Native");
  expect(chooseClient({storePresence:true})).toBe("React Native");
  expect(chooseClient({offlineRead:true,instantUpdates:true})).toBe("PWA");
  expect(deepLinkToRoute("devdesk://tickets/42")).toBe("app/tickets/[id].tsx");
  expect(deepLinkToRoute("devdesk://inne/42")).toBeNull();
  expect(deepLinkToRoute("devdesk://tickets")).toBeNull();
  expect(deepLinkToRoute("https://a.example")).toBeNull();
});`;
const exercises=[
['Wypisz, co przenosi się z Reacta bez zmian.','Wszystko, co nie dotyka DOM.','useState, useEffect, useReducer, kontekst, własne hooki, TanStack Query, Zustand, Zod, logika biznesowa i klient API. Inne są elementy, style, zdarzenia dotyku, nawigacja i dostęp do urządzenia.'],
['Zmapuj elementy DOM na React Native.','Bez div, span i button.',platform],
['Napisz ekran listy z FlatList.','keyExtractor i pusty stan.',screen],
['Dodaj pole wyszukiwania z onChangeText.','Dostajesz od razu tekst.',screen],
['Dodaj stan wciśnięcia.','Pseudoklas nie ma, więc funkcja w style.',screen],
['Dodaj odświeżanie gestem.','refreshing i onRefresh na liście.',screen],
['Dodaj role dostępności.','accessibilityRole i accessibilityLabel.',screen],
['Wyjaśnij różnice stylów.','Flexbox, kaskada, jednostki.','Flexbox jest domyślny, a główna oś to kolumna. Nie ma kaskady: styl tekstu nie przechodzi z View na Text. Liczby to punkty niezależne od gęstości. Wygląd współdzielisz komponentami i tokenami, a nie plikami CSS.'],
['Napisz ekran szczegółów w Expo Router.','Plik app/tickets/[id].tsx.',details],
['Obsłuż ładowanie, błąd i ponowienie.','Te same stany co w wersji webowej.',details],
['Dodaj accessibilityState przy wyłączeniu.','Stan disabled musi być ogłaszany.',details],
['Wydziel wspólny pakiet logiki.','Bez DOM i bez React Native.',shared],
['Napisz klienta API z parametrem adresu.','Adres zależy od środowiska.',shared],
['Dobierz adres API do miejsca uruchomienia.','localhost z telefonu to telefon.',platform],
['Wybierz magazyn dla tokenu.','AsyncStorage nie szyfruje.',platform],
['Wybierz listę dla dużej liczby elementów.','ScrollView renderuje wszystko naraz.',platform],
['Napisz kontrolę nawyków z przeglądarki.','Tekstowy przegląd źródła.',platform],
['Sprawdź zgodność wersji aplikacji.','Aktualizacje ze sklepu trwają.',platform],
['Rozpoznaj trasę z linku głębokiego.','Plik trasy to adres.',platform],
['Zaproponuj monorepo web i mobile.','Co współdzielić, a co nie?','packages/shared: typy, schematy, klient API, hooki danych, logika. apps/web i apps/mobile: osobny interfejs. react-native-web rzadko opłaca się przy dużej istniejącej aplikacji. Narzędzia: pnpm workspaces i ewentualnie Turborepo.'],
['Wybierz między PWA a React Native dla techników.','Push, tło, kamera.','Push, kod QR, zdjęcia i praca w tle są wymaganiami zależnymi od platformy. React Native daje pełny dostęp i obecność w sklepach, kosztem osobnego interfejsu i publikacji. PWA wystarczy, gdy liczy się odczyt offline i prosta instalacja.'],
['Rozpocznij projekt Expo.','Expo Go i kod QR.','npx create-expo-app@latest devdesk-mobile, a potem npx expo start i skan kodu QR w Expo Go. Testowano tu tylko wspólną logikę, więc ten krok wykonaj na telefonie lub w emulatorze.'],
['Dodaj token w bezpiecznym magazynie.','expo-secure-store.','// token.ts (w projekcie Expo)\nimport * as SecureStore from "expo-secure-store";\nawait SecureStore.setItemAsync("token",value);\nconst token=await SecureStore.getItemAsync("token");\nAsyncStorage jest niezaszyfrowany, więc nie nadaje się do sekretów.'],
['Dodaj zdjęcie usterki.','expo-image-picker i FormData.','W handlerze zapisz: const result=await ImagePicker.launchCameraAsync({quality:0.7}); Następnie wyślij plik w FormData. Pytaj o uprawnienie w momencie użycia i obsłuż odmowę. Sprawdź to na prawdziwym urządzeniu, bo symulator nie ma aparatu.'],
['Sprawdź dostępność na urządzeniu.','TalkBack i VoiceOver.','Włącz czytnik w ustawieniach telefonu. Sprawdź nazwy, role i kolejność na liście i szczegółach. Rozmiar celów dotyku co najmniej 44 punkty. Testy jednostkowe tego nie zastąpią.'],
['Zbuduj wersję testową przez EAS.','Bez sklepu.','npm i -g eas-cli, eas build --profile preview --platform android. Plik zainstaluj na telefonie. EAS Update wysyła poprawki JS bez ponownej publikacji, ale tylko w granicach zasad sklepów.'],
['Uwzględnij wsteczną zgodność API.','Kto nadal używa starej wersji?','Użytkownicy miesiącami mają stare wersje. Dodawaj pola bez usuwania, wersjonuj endpointy, zwracaj minimalną wspieraną wersję i pokazuj komunikat o aktualizacji. Usunięcie pola może zepsuć zainstalowaną aplikację.'],
['Znajdź błędy w tym ekranie.','Porównaj z pułapkami.','Tekst poza Text powoduje błąd. className nie istnieje. ScrollView z map renderuje całą listę. Token w AsyncStorage jest niezaszyfrowany. fetch do localhost z telefonu nie trafi do komputera. onClick nie działa, użyj onPress.'],
['Porównaj typy stylów w JS i TS.','Co wychwyci kompilator?','StyleSheet.create w TS wychwyci paddingVertikal i złą wartość fontWeight. W JS błąd po prostu nie zadziała na ekranie. Dlatego projekty Expo domyślnie powstają w TypeScript.'],
['Sprawdź kryterium akceptacji lekcji.','Telefon, dane, pakiet, token, czytnik.','Prototyp działa na telefonie, pokazuje listę i szczegóły z tego samego API, korzysta ze wspólnego pakietu z typami i hookami, trzyma token w bezpiecznym magazynie, a czytnik poprawnie odczytuje listę i przyciski.'],
];
const concepts=[
['Co React Native renderuje?','Natywne widoki platformy','Elementy DOM','Canvas','Obrazki','Ten sam React, inne elementy.'],
['Czym różni się silnik od web?','Model jest ten sam, zmienia się renderer','Inny język','Brak hooków','Brak stanu','Komponenty, props i hooki zostają.'],
['Odpowiednik div?','View','Text','Pressable','Box','Kontener układu.'],
['Gdzie musi być każdy tekst?','W Text','W View','W ScrollView','Gdziekolwiek','Inaczej błąd w czasie działania.'],
['Odpowiednik button?','Pressable','View','Touchable bez niczego','Text','Reaguje na dotyk.'],
['Jakie zdarzenie zamiast onClick?','onPress','onTap','onTouch','onClick','Tak nazywa się w RN.'],
['Odpowiednik input?','TextInput','Input','TextField','Field','Własny komponent platformy.'],
['Co przekazuje onChangeText?','Od razu tekst','Event','Element','Numer','Bez event.target.value.'],
['Co jest na długie listy?','FlatList','ScrollView z map','View z map','Text','Wbudowana wirtualizacja.'],
['Czym grozi ScrollView z map?','Render całej listy naraz','Niczym','Błędem składni','Brakiem kluczy','Zacięcia i pamięć.'],
['Do czego służy keyExtractor?','Podaje klucz elementu listy','Filtruje','Sortuje','Tworzy id','Musi zwrócić tekst.'],
['Czy istnieje window w RN?','Nie','Tak','Tylko w dev','Tylko w iOS','Nie ma DOM.'],
['Czy działa className?','Nie, chyba że biblioteka to dodaje','Tak','Tylko Android','Tylko Expo','NativeWind kompiluje klasy do stylów.'],
['Czym są style w RN?','Obiektami JS','Plikami CSS','Klasami','Atrybutami HTML','StyleSheet.create.'],
['Domyślny flexDirection?','column','row','grid','block','Inaczej niż w przeglądarce.'],
['Czy każdy View używa Flexboxa?','Tak','Nie','Tylko z display','Tylko w iOS','Domyślnie.'],
['Co robi flex:1?','Rozciąga na dostępne miejsce','Ukrywa','Zawija','Daje jeden piksel','Podstawa układów.'],
['Czy styl tekstu dziedziczy się z View?','Nie','Tak','Tylko kolor','Tylko rozmiar','Tylko z Text do zagnieżdżonego Text.'],
['Jak współdzielić wygląd tekstu?','Komponentem AppText i tokenami','Kaskadą','Plikiem CSS','Atrybutem class','Brak dziedziczenia.'],
['Jakie jednostki mają liczby w stylach?','Punkty niezależne od gęstości','Piksele fizyczne','rem','em','Bez px.'],
['Jak obsłużyć stan wciśnięcia?','Funkcja style w Pressable',':active','hover','Klasa','Brak pseudoklas.'],
['Jak odczytać tryb ciemny?','useColorScheme','matchMedia','CSS media','navigator','API React Native.'],
['Do czego służy SafeAreaView?','Omija wycięcia i paski systemowe','Szyfruje','Blokuje dotyk','Tworzy listę','Pochodzi z safe-area-context.'],
['Czym jest NativeWind?','Klasy Tailwind kompilowane do stylów RN','Silnik CSS przeglądarki','Pakiet nawigacji','Serwer','Jedna z bibliotek stylów.'],
['Czym jest Expo?','Zestawem narzędzi i bibliotek dla RN','Nowym językiem','Serwerem baz','Pakietem CSS','Zalecany start.'],
['Czym jest Expo Go?','Aplikacją do uruchomienia projektu przez kod QR','Sklepem','Emulatorem','Pakietem testowym','Szybki start na telefonie.'],
['Czym jest Expo Router?','Trasy z plików w katalogu app','Serwerem API','Biblioteką stylów','Testami','Pod spodem React Navigation.'],
['Jaki plik odpowiada /tickets/42?','app/tickets/[id].tsx','tickets.html','routes.json','app/tickets.css','Trasa dynamiczna.'],
['Jak odczytać parametr trasy?','useLocalSearchParams','useParams z DOM','window.location','useRef','Dla Expo Router.'],
['Czym jest link głęboki?','Adres otwierający ekran aplikacji','Link do sklepu','Adres API','Plik CSS','np. devdesk://tickets/42.'],
['Gdzie trzymać token?','expo-secure-store','AsyncStorage','Stan globalny na zawsze','Plik tekstowy','AsyncStorage nie szyfruje.'],
['Do czego AsyncStorage?','Ustawienia i dane niewrażliwe','Tokeny','Hasła','Klucze API','Prosty magazyn.'],
['Jaki adres ma komputer z emulatora Androida?','10.0.2.2','localhost','127.0.0.1 telefonu','192.168.0.0','Specjalny alias.'],
['Co oznacza localhost z telefonu?','Sam telefon','Komputer','Serwer produkcyjny','Router','Częsta pułapka.'],
['Jak połączyć telefon z lokalnym backendem?','Adres komputera w sieci lub tunel','localhost','Bluetooth','USB bez konfiguracji','Sieć musi to umożliwiać.'],
['Czy można użyć TanStack Query w RN?','Tak','Nie','Tylko w Expo','Tylko w iOS','Logika danych przenosi się.'],
['Czy Zod działa w RN?','Tak','Nie','Tylko w web','Tylko w Node','Czysty JS.'],
['Co współdzielić między web i mobile?','Typy, schematy, API i logikę','Cały interfejs zawsze','Pliki CSS','Komponenty DOM','Interfejs zwykle osobno.'],
['Czym jest react-native-web?','Renderuje komponenty RN w przeglądarce','Zastępuje Reacta','Serwer','Język','Rzadko dla dużej istniejącej aplikacji.'],
['Kiedy PWA wystarczy?','Odczyt offline i prosta instalacja','Praca w tle i pełny push zawsze','Dostęp do wszystkich czujników','Obecność w sklepach','Prostsza dystrybucja.'],
['Kiedy React Native?','Pełny dostęp do urządzenia i sklepy','Prosta strona informacyjna','Jedna tabela','Gdy nie ma zespołu','Ma koszt osobnej aplikacji.'],
['Jaki jest koszt RN?','Osobny interfejs, publikacja i testy na urządzeniach','Brak kosztu','Brak TS','Brak hooków','Trzeba go uwzględnić.'],
['Do czego EAS Build?','Budowanie aplikacji w chmurze','Testy','Hosting API','Style','Pliki do sklepów.'],
['Do czego EAS Update?','Poprawki JS bez publikacji w sklepie','Aktualizacje natywne','Wymiana bazy','Zmiana ikon systemu','W granicach zasad sklepów.'],
['Czy EAS Update zmieni kod natywny?','Nie','Tak','Tylko Android','Tylko iOS','Zmiana natywna wymaga nowego builda.'],
['Czemu API musi być dłużej zgodne?','Użytkownicy długo mają stare wersje','Dla SEO','Bo sklepy tego wymagają zawsze','Bez powodu','Aktualizacja nie jest natychmiastowa.'],
['Jak rozpoznać zbyt starą wersję?','Minimalna wspierana wersja z API','Po kolorze','Po dacie','Nie da się','Pokaż prośbę o aktualizację.'],
['Czym jest accessibilityLabel?','Nazwa dla czytnika','Kolor','Klucz','Ścieżka','Odpowiednik aria-label.'],
['Czym jest accessibilityRole?','Rola elementu','Nazwa','Klasa','Stan','np. button.'],
['Czym jest accessibilityState?','Stan, np. disabled','Rola','Kolor','Nawigacja','Odpowiednik aria-*.'],
['Jaki czytnik na Androidzie?','TalkBack','VoiceOver','NVDA','JAWS','Testuj na urządzeniu.'],
['Jaki czytnik na iOS?','VoiceOver','TalkBack','NVDA','Narrator','Testuj na urządzeniu.'],
['Czy test jsdom sprawdzi widok RN?','Nie, potrzebne narzędzia RN lub urządzenie','Tak','Tak, z RTL DOM','Tak, po buildzie','Tu testujemy wspólną logikę.'],
['Czym testować komponenty RN?','@testing-library/react-native','Tylko Lighthouse','axe-core w jsdom','Playwright na Web','Do E2E Detox lub Maestro.'],
['Czy TS wychwyci paddingVertikal?','Tak, w StyleSheet.create','Nie','Tylko w runtime','Tylko w JS','W JS styl po prostu nie zadziała.'],
['Jak nazwać wynik „Text strings must be rendered within a Text”?','Tekst poza Text','Brak klucza','Błąd sieci','Błąd stylu','Najczęstszy błąd początkujących.'],
['Czy można użyć fetch?','Tak','Nie','Tylko axios','Tylko w iOS','RN dostarcza fetch.'],
['Czy FormData działa do wysyłki zdjęcia?','Tak','Nie','Tylko w web','Tylko z base64','Zgodnie z teorią rozdziału 11.'],
['Kiedy pytać o uprawnienia?','W momencie użycia funkcji','Na starcie o wszystko','Nigdy','W sklepie','Odmowa to normalny stan.'],
['Co jest głównym celem pierwszego prototypu?','Sprawdzenie modelu i wspólnej logiki','Pełna publikacja','Wszystkie funkcje','Własny silnik','Małe ryzyko, szybka odpowiedź.'],
];
const header=String.raw`import {apiBaseUrl,auditRnSource,chooseClient,deepLinkToRoute,isSupported,listComponent,nativeFor,storageFor} from "./platform";
import {filterTickets,keyFor,ticketMeta,type Ticket} from "./shared";
const list:Ticket[]=[{id:1,title:"Awaria",status:"OPEN"},{id:2,title:"Błąd",status:"CLOSED"}];
const problems=(text:string)=>auditRnSource(text).join()||"brak";`;
const N=['View','Text','Pressable','TextInput'];
const probeRows=[
['Odpowiednik div','nativeFor("div")','View','Kontener.',['Text','Pressable','brak odpowiednika']],
['Odpowiednik p','nativeFor("p")','Text','Tekst.',['View','Pressable','brak odpowiednika']],
['Odpowiednik span','nativeFor("span")','Text','Tekst.',['View','Pressable','brak odpowiednika']],
['Odpowiednik button','nativeFor("button")','Pressable','Dotyk.',['View','Text','brak odpowiednika']],
['Odpowiednik input','nativeFor("input")','TextInput','Pole.',['View','Text','brak odpowiednika']],
['Odpowiednik table','nativeFor("table")','brak odpowiednika','Układasz z View lub FlatList.',N.slice(0,3)],
['Filtr: dopasowanie','filterTickets(list,"awaria").length',1,'Bez wielkości liter.'],
['Filtr: spacje','filterTickets(list,"  błąd ")[0].id',2,'Trim.'],
['Filtr: puste zapytanie','filterTickets(list,"").length',2,'Cała lista.'],
['Filtr: brak dopasowania','filterTickets(list,"zzz").length',0,'Pusta lista.'],
['Filtr: kopia','filterTickets(list,"")===list',false,'Nowa tablica.'],
['Opis ticketu','ticketMeta(list[0])','#1 · OPEN','Wspólny format.',['1 OPEN','OPEN','#1']],
['Klucz listy','keyFor(list[1])','2','keyExtractor oczekuje tekstu.',['1','2.0','id']],
['Typ klucza','typeof keyFor(list[0])','string','Nie liczba.',['number','object','undefined']],
['Poprawny ekran','problems("<View style={s}><Text>ok</Text></View>")','brak','Elementy RN.',['goły tekst w View','className nie istnieje','element DOM w React Native']],
['className','problems(\'<View className="a"/>\')','className nie istnieje','Style to obiekty.',['brak','element DOM w React Native','onClick zamiast onPress']],
['onClick','problems("<Pressable onClick={f}/>")','onClick zamiast onPress','Inne zdarzenie.',['brak','className nie istnieje','element DOM w React Native']],
['div','problems("<div/>")','element DOM w React Native','Nie ma DOM.',['brak','className nie istnieje','onClick zamiast onPress']],
['document','problems("document.title")','window lub document nie istnieją','Nie ma tych obiektów.',['brak','element DOM w React Native','onClick zamiast onPress']],
['Token','problems(\'AsyncStorage.setItem("authToken",t)\')','token w AsyncStorage','Niezaszyfrowany.',['brak','className nie istnieje','window lub document nie istnieją']],
['Ustawienie w AsyncStorage','problems(\'AsyncStorage.setItem("motyw",t)\')','brak','Dane niewrażliwe.',['token w AsyncStorage','className nie istnieje','window lub document nie istnieją']],
['Lista w ScrollView','problems("<ScrollView>{a.map(f)}</ScrollView>")','długa lista w ScrollView zamiast FlatList','Render wszystkiego.',['brak','goły tekst w View','className nie istnieje']],
['Goły tekst','problems("<View>Tekst</View>")','goły tekst w View','Błąd w czasie działania.',['brak','długa lista w ScrollView zamiast FlatList','className nie istnieje']],
['Emulator Androida','apiBaseUrl("android-emulator")','http://10.0.2.2:8080','Alias komputera.',['http://localhost:8080','http://127.0.0.1:8080','http://10.0.0.1:8080']],
['Symulator iOS','apiBaseUrl("ios-simulator")','http://localhost:8080','Działa na tym samym komputerze.',['http://10.0.2.2:8080','http://127.0.0.2:8080','http://0.0.0.0:8080']],
['Telefon z adresem','apiBaseUrl("device","192.168.1.5",9000)','http://192.168.1.5:9000','Adres komputera w sieci.',['http://localhost:9000','http://10.0.2.2:9000','http://192.168.1.5:8080']],
['Telefon bez adresu','(()=>{try{apiBaseUrl("device");return "ok";}catch{return "błąd";}})()','błąd','Nie zgadujemy localhost.',['ok','localhost','10.0.2.2']],
['Magazyn tokenu','storageFor("token")','expo-secure-store','Zaszyfrowany.',['AsyncStorage','localStorage','Cookie']],
['Magazyn ustawień','storageFor("preference")','AsyncStorage','Niewrażliwe.',['expo-secure-store','localStorage','Cookie']],
['Lista 21','listComponent(21)','FlatList','Duża lista.',['ScrollView','View','Text']],
['Lista 20','listComponent(20)','ScrollView','Mała lista może być prosta.',['FlatList','View','Text']],
['Wersja nowsza','isSupported("2.1.0","2.0.0")',true,'Spełnia minimum.'],
['Wersja starsza','isSupported("1.9.9","2.0.0")',false,'Poniżej minimum.'],
['Liczby, nie tekst','isSupported("2.10.0","2.9.0")',true,'10 jest większe od 9, choć tekstowo byłoby inaczej.'],
['Krótszy zapis','isSupported("2.0","2.0.0")',true,'Brakujący człon to 0.'],
['Wersja równa','isSupported("2.0.0","2.0.0")',true,'Spełnia.'],
['Klient: push i tło','chooseClient({push:true,background:true})','React Native','Pełny dostęp.',['PWA','Electron','brak']],
['Klient: tylko offline','chooseClient({offlineRead:true})','PWA','Wystarczy service worker.',['React Native','Electron','brak']],
['Trasa z linku','deepLinkToRoute("devdesk://tickets/42")','app/tickets/[id].tsx','Plik trasy.',['app/tickets.tsx','tickets/42','null']],
['Link bez identyfikatora','String(deepLinkToRoute("devdesk://tickets"))','null','Nie ma takiej trasy szczegółów.',['app/tickets/[id].tsx','tickets','undefined']],
];
const project={
'src/shared.ts':shared,'src/platform.ts':platform,'src/platform.test.ts':tests,'mobile/TicketListScreen.tsx.txt':screen,'mobile/app/tickets/[id].tsx.txt':details,
'README.md':'DevDesk Mobile: fragmenty. npm install, npm test. Test obejmuje wspólną logikę i proste reguły, bez DOM i bez React Native. Ekrany w katalogu mobile to pliki tekstowe do wklejenia w projekt Expo: wymagają react-native i expo-router, więc tutaj nie są uruchamiane. Działanie sprawdź na telefonie lub w emulatorze.\n',
};
const mutants=[
{file:'src/shared.ts',before:'.trim()',after:''},
{file:'src/shared.ts',before:'if(!response.ok)throw new Error("Serwer zwrócił "+response.status);',after:''},
{file:'src/shared.ts',before:':[...tickets];',after:':tickets as Ticket[];'},
{file:'src/platform.ts',before:'if(!lanHost)throw new Error("Telefon wymaga adresu komputera w sieci lub tunelu");',after:''},
{file:'src/platform.ts',before:'"http://10.0.2.2:"+port',after:'"http://localhost:"+port'},
{file:'src/platform.ts',before:'value==="token"?"expo-secure-store":"AsyncStorage"',after:'"AsyncStorage"'},
{file:'src/platform.ts',before:'a!==b)return a>b;',after:'a!==b)return String(a)>String(b);'},
{file:'src/platform.ts',before:'if(/<View[^>]*>\\s*[^\\s<{]/.test(source))problems.push("goły tekst w View");',after:''},
{file:'src/platform.ts',before:'section==="tickets"&&id?',after:'section==="tickets"?'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
