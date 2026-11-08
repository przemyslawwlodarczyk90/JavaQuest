const {lesson}=require('./practice_kit.cjs');
const main=String.raw`// electron/main.js (proces główny, Node)
const {app,BrowserWindow,ipcMain,dialog,Notification}=require("electron");
const fs=require("node:fs/promises");
const path=require("node:path");

function createWindow(){
  const win=new BrowserWindow({
    width:1280,
    height:800,
    webPreferences:{
      preload:path.join(__dirname,"preload.js"),
      contextIsolation:true,
      nodeIntegration:false,
      sandbox:true,
    },
  });
  if(app.isPackaged)win.loadFile(path.join(__dirname,"../dist/index.html"));
  else win.loadURL("http://localhost:5173");
}

ipcMain.handle("report:save",async(_event,csv)=>{
  if(typeof csv!=="string"||csv.length>5_000_000)throw new Error("Nieprawidłowe dane raportu");
  const {canceled,filePath}=await dialog.showSaveDialog({defaultPath:"raport.csv"});
  if(canceled||!filePath)return null;
  await fs.writeFile(filePath,csv,"utf8");
  return filePath;
});

ipcMain.handle("notify",(_event,title,body)=>{
  new Notification({title:String(title),body:String(body)}).show();
});

app.whenReady().then(createWindow);
app.on("window-all-closed",()=>{if(process.platform!=="darwin")app.quit();});`;
const preload=String.raw`// electron/preload.js: jedyny most między stroną a procesem głównym
const {contextBridge,ipcRenderer}=require("electron");

contextBridge.exposeInMainWorld("desktop",{
  saveReport:csv=>ipcRenderer.invoke("report:save",csv),
  notify:(title,body)=>ipcRenderer.invoke("notify",title,body),
});`;
const exportButton=String.raw`import {useState} from "react";

declare global {
  interface Window {
    desktop?:{
      saveReport:(csv:string)=>Promise<string|null>;
      notify:(title:string,body:string)=>Promise<void>;
    };
  }
}

function downloadInBrowser(csv:string){
  const url=URL.createObjectURL(new Blob([csv],{type:"text/csv"}));
  const link=Object.assign(document.createElement("a"),{href:url,download:"raport.csv"});
  link.click();
  URL.revokeObjectURL(url);
}

export function ExportButton({buildCsv}:{buildCsv:()=>string}){
  const [status,setStatus]=useState("");

  async function handleExport(){
    const csv=buildCsv();
    if(window.desktop){
      try{
        const path=await window.desktop.saveReport(csv);
        setStatus(path?"Zapisano: "+path:"Anulowano zapis");
        if(path)await window.desktop.notify("DevDesk","Raport zapisany");
      }catch{
        setStatus("Nie udało się zapisać raportu");
      }
    }else{
      downloadInBrowser(csv);
      setStatus("Pobieranie rozpoczęte");
    }
  }

  return (
    <div>
      <button type="button" onClick={handleExport}>Eksportuj CSV</button>
      <span role="status">{status}</span>
    </div>
  );
}`;
const logs=String.raw`// features/logs/useLogStats.ts (w projekcie Tauri)
import {invoke} from "@tauri-apps/api/core";
import {useState} from "react";

export function useLogStats(){
  const [errors,setErrors]=useState<number|null>(null);

  async function analyze(log:string){
    const count=await invoke<number>("count_errors",{log});
    setErrors(count);
  }

  return {errors,analyze};
}

// src-tauri/src/lib.rs
// #[tauri::command]
// fn count_errors(log: String) -> usize {
//     log.lines().filter(|line| line.contains("ERROR")).count()
// }`;
const audit=String.raw`export type WebPreferences={preload?:string;contextIsolation?:boolean;nodeIntegration?:boolean;sandbox?:boolean;webSecurity?:boolean};

export function auditWebPreferences(prefs:WebPreferences){
  const problems:string[]=[];
  if(prefs.contextIsolation!==true)problems.push("contextIsolation musi być true");
  if(prefs.nodeIntegration!==false)problems.push("nodeIntegration musi być false");
  if(prefs.sandbox!==true)problems.push("sandbox powinien być true");
  if(prefs.webSecurity===false)problems.push("webSecurity wyłączone");
  return problems;
}

// Tekstowy przegląd preload: wystawiaj konkretne funkcje, a nie ipcRenderer.
export function auditPreload(source:string){
  const problems:string[]=[];
  if(/exposeInMainWorld\([^,]+,\s*ipcRenderer\s*\)/.test(source))problems.push("preload wystawia cały ipcRenderer");
  if(/exposeInMainWorld\([^,]+,\s*require\b/.test(source))problems.push("preload wystawia moduł Node");
  if(/invoke\(\s*[A-Za-z_]\w*\s*[,)]/.test(source))problems.push("kanał IPC podawany przez stronę");
  return problems;
}

export function validateReport(value:unknown,limit=5_000_000){
  if(typeof value!=="string")return "Raport musi być tekstem";
  if(value.length>limit)return "Raport jest zbyt duży";
  return null;
}

// Most jest granicą jak API HTTP: strona może być niezaufana, więc kanał sprawdzamy wprost.
export function createBridge<Handlers extends Record<string,(...args:never[])=>unknown>>(handlers:Handlers){
  return {
    invoke(channel:string,...args:unknown[]){
      if(!Object.hasOwn(handlers,channel))throw new Error("Nieznany kanał: "+channel);
      const handler=handlers[channel] as (...input:unknown[])=>unknown;
      return handler(...args);
    },
  };
}

export type Capability={identifier:string;permissions:readonly string[]};

export function auditCapability(capability:Capability,needed:readonly string[]){
  const problems:string[]=[];
  for(const permission of capability.permissions){
    if(permission.endsWith(":default")&&permission.startsWith("fs"))problems.push("szerokie uprawnienie systemu plików: "+permission);
    if(!needed.includes(permission))problems.push("niepotrzebne uprawnienie: "+permission);
  }
  for(const permission of needed)if(!capability.permissions.includes(permission))problems.push("brak uprawnienia: "+permission);
  return problems;
}

export type DesktopNeed={systemFiles?:boolean;tray?:boolean;globalShortcut?:boolean;team?:"js"|"rust";sizeMatters?:boolean};

export function chooseDesktop(need:DesktopNeed){
  if(!need.systemFiles&&!need.tray&&!need.globalShortcut)return "PWA";
  if(need.sizeMatters&&need.team==="rust")return "Tauri";
  return "Electron";
}

export function allowNavigation(url:string,allowedOrigin:string){
  try{
    return new URL(url).origin===allowedOrigin;
  }catch{
    return false;
  }
}`;
const tests=String.raw`import {afterEach,expect,test,vi} from "vitest";
import {fireEvent,render,screen,waitFor} from "@testing-library/react";
import {allowNavigation,auditCapability,auditPreload,auditWebPreferences,chooseDesktop,createBridge,validateReport} from "./desktopAudit";
import {ExportButton} from "./ExportButton";
import {readFileSync} from "node:fs";
import {resolve} from "node:path";

afterEach(()=>{delete window.desktop;vi.restoreAllMocks();});

const read=(file:string)=>readFileSync(resolve(process.cwd(),file),"utf8");

test("na pulpicie eksport używa mostu i powiadomienia",async()=>{
  const saveReport=vi.fn(async()=>"C:/raport.csv"),notify=vi.fn(async()=>{});
  window.desktop={saveReport,notify};
  render(<ExportButton buildCsv={()=>"a,b"}/>);
  fireEvent.click(screen.getByRole("button"));
  await waitFor(()=>expect(screen.getByRole("status")).toHaveTextContent("Zapisano: C:/raport.csv"));
  expect(saveReport).toHaveBeenCalledWith("a,b");
  expect(notify).toHaveBeenCalledWith("DevDesk","Raport zapisany");
});

test("anulowanie zapisu nie wysyła powiadomienia",async()=>{
  const notify=vi.fn(async()=>{});
  window.desktop={saveReport:async()=>null,notify};
  render(<ExportButton buildCsv={()=>"a"}/>);
  fireEvent.click(screen.getByRole("button"));
  await waitFor(()=>expect(screen.getByRole("status")).toHaveTextContent("Anulowano zapis"));
  expect(notify).not.toHaveBeenCalled();
});

test("błąd mostu jest pokazany",async()=>{
  window.desktop={saveReport:async()=>{throw new Error("x");},notify:async()=>{}};
  render(<ExportButton buildCsv={()=>"a"}/>);
  fireEvent.click(screen.getByRole("button"));
  await waitFor(()=>expect(screen.getByRole("status")).toHaveTextContent("Nie udało się"));
});

test("w przeglądarce ten sam kod pobiera plik",async()=>{
  URL.createObjectURL=vi.fn(()=>"blob:raport");
  URL.revokeObjectURL=vi.fn();
  const click=vi.spyOn(HTMLAnchorElement.prototype,"click").mockImplementation(()=>{});
  render(<ExportButton buildCsv={()=>"a"}/>);
  fireEvent.click(screen.getByRole("button"));
  await waitFor(()=>expect(screen.getByRole("status")).toHaveTextContent("Pobieranie rozpoczęte"));
  expect(click).toHaveBeenCalledOnce();
  expect(URL.revokeObjectURL).toHaveBeenCalledWith("blob:raport");
});

test("ustawienia okna",()=>{
  expect(auditWebPreferences({contextIsolation:true,nodeIntegration:false,sandbox:true})).toEqual([]);
  expect(auditWebPreferences({})).toHaveLength(3);
  expect(auditWebPreferences({contextIsolation:true,nodeIntegration:true,sandbox:true})).toEqual(["nodeIntegration musi być false"]);
  expect(auditWebPreferences({contextIsolation:true,nodeIntegration:false,sandbox:true,webSecurity:false})).toEqual(["webSecurity wyłączone"]);
  const source=read("electron/main.cjs");
  expect(source).toContain("contextIsolation:true");
  expect(source).toContain("nodeIntegration:false");
  expect(source).toContain("sandbox:true");
});

test("preload lekcji przechodzi przegląd, a niebezpieczny nie",()=>{
  expect(auditPreload(read("electron/preload.cjs"))).toEqual([]);
  expect(auditPreload('contextBridge.exposeInMainWorld("ipc",ipcRenderer)')).toEqual(["preload wystawia cały ipcRenderer"]);
  expect(auditPreload('contextBridge.exposeInMainWorld("fs",require("fs"))')).toEqual(["preload wystawia moduł Node"]);
  expect(auditPreload('exposeInMainWorld("d",{call:channel=>ipcRenderer.invoke(channel)})')).toEqual(["kanał IPC podawany przez stronę"]);
});

test("walidacja danych w procesie głównym",()=>{
  expect(validateReport("a")).toBeNull();
  expect(validateReport(5)).toBe("Raport musi być tekstem");
  expect(validateReport("abc",2)).toBe("Raport jest zbyt duży");
  expect(read("electron/main.cjs")).toContain('typeof csv!=="string"');
});

test("most odrzuca nieznane kanały i pola prototypu",()=>{
  const bridge=createBridge({add:(a:number,b:number)=>a+b});
  expect(bridge.invoke("add",1,2)).toBe(3);
  expect(()=>bridge.invoke("exec","rm")).toThrow("Nieznany kanał");
  expect(()=>bridge.invoke("constructor")).toThrow("Nieznany kanał");
  expect(()=>bridge.invoke("__proto__")).toThrow("Nieznany kanał");
});

test("uprawnienia Tauri",()=>{
  const need=["notification:default","dialog:allow-save"];
  expect(auditCapability({identifier:"main",permissions:need},need)).toEqual([]);
  expect(auditCapability({identifier:"main",permissions:["dialog:allow-save"]},need)).toEqual(["brak uprawnienia: notification:default"]);
  expect(auditCapability({identifier:"main",permissions:[...need,"fs:default"]},need)).toEqual(["szerokie uprawnienie systemu plików: fs:default","niepotrzebne uprawnienie: fs:default"]);
});

test("wybór technologii i blokada nawigacji",()=>{
  expect(chooseDesktop({})).toBe("PWA");
  expect(chooseDesktop({systemFiles:true,team:"js"})).toBe("Electron");
  expect(chooseDesktop({systemFiles:true,team:"rust",sizeMatters:true})).toBe("Tauri");
  expect(chooseDesktop({systemFiles:true,team:"js",sizeMatters:true})).toBe("Electron");
  expect(allowNavigation("https://app.example/a","https://app.example")).toBe(true);
  expect(allowNavigation("https://zly.example","https://app.example")).toBe(false);
  expect(allowNavigation("nie adres","https://app.example")).toBe(false);
  expect(allowNavigation("https://app.example.zly.pl","https://app.example")).toBe(false);
});`;
const exercises=[
['Porównaj Electron i Tauri.','Silnik, rozmiar, backend.','Electron dołącza Chromium i Node: duży instalator, ten sam silnik wszędzie, dojrzały ekosystem. Tauri używa systemowego WebView i Rusta: małe pliki, model uprawnień, ale różne silniki do przetestowania. Dla Reacta to ten sam interfejs z innym mostem.'],
['Skonfiguruj okno Electrona bezpiecznie.','contextIsolation, brak Node, sandbox.',main],
['Rozróżnij tryb deweloperski i spakowany.','Serwer Vite albo plik z dist.',main],
['Waliduj dane w procesie głównym.','Typy nie istnieją w czasie działania.',main],
['Zapisz raport przez natywne okno.','Obsłuż anulowanie.',main],
['Napisz wąski preload.','Tylko konkretne funkcje.',preload],
['Nie wystawiaj całego ipcRenderer.','Strona mogłaby wywołać dowolny kanał.',preload],
['Zadeklaruj typ mostu.','Opcjonalne pole wymusza sprawdzenie.',exportButton],
['Użyj tego samego komponentu w przeglądarce i na pulpicie.','Rozgałęzienie po window.desktop.',exportButton],
['Obsłuż anulowanie i błąd mostu.','Oba są normalnymi stanami.',exportButton],
['Przetestuj komponent z atrapą mostu.','window.desktop to dane testu.',tests],
['Przetestuj pobieranie w przeglądarce.','URL.createObjectURL trzeba podmienić.',tests],
['Napisz przegląd ustawień okna.','Brak wartości jest też problemem.',audit],
['Napisz przegląd preload.','Wykryj cały ipcRenderer i dowolny kanał.',audit],
['Napisz most z listą kanałów.','Object.hasOwn chroni przed __proto__.',audit],
['Zablokuj nawigację do obcych stron.','setWindowOpenHandler i will-navigate.','win.webContents.setWindowOpenHandler(()=>({action:"deny"}));\nwin.webContents.on("will-navigate",(event,url)=>{if(!allowNavigation(url,APP_ORIGIN))event.preventDefault();});\nDozwolone adresy porównuj przez origin, a nie startsWith.'],
['Napisz funkcję sprawdzającą źródło adresu.','startsWith przepuści app.example.zly.pl.',audit],
['Wywołaj polecenie Tauri.','invoke z pakietu @tauri-apps/api.',logs],
['Dodaj polecenie w Rust.','Funkcja z atrybutem tauri::command.',logs],
['Sprawdź minimalne uprawnienia Tauri.','Bez nich wywołanie jest odrzucane.',audit],
['Ustaw zakres uprawnień do plików.','Tylko potrzebny katalog.','W pliku capabilities podaj zakres, np. tylko folder dokumentów, zamiast szerokiego dostępu. Dodaj CSP w konfiguracji i waliduj dane w poleceniach Rust. Każde nowe uprawnienie opisz w przeglądzie kodu.'],
['Dobierz technologię do wymagań.','PWA, jeśli wystarcza.',audit],
['Porównaj rozmiar i pamięć.','Zmierz, nie cytuj.','Zbuduj oba instalatory dla tej samej aplikacji, zapisz rozmiar i zużycie pamięci po kilku minutach na tym samym systemie. Wyniki zależą od aplikacji i WebView, więc nie opieraj decyzji na liczbach z cudzego porównania.'],
['Dodaj ikonę w zasobniku.','Tray w procesie głównym.','// electron/main.js\nconst tray=new Tray(iconPath);\ntray.setToolTip("DevDesk");\ntray.setContextMenu(Menu.buildFromTemplate([{label:"Pokaż",click:()=>win.show()},{label:"Zamknij",role:"quit"}]));\nTrzymaj referencję do tray, aby garbage collector jej nie usunął.'],
['Dodaj powiadomienie o krytycznym tickecie.','Zdarzenie SSE trafia przez most.','Renderer odbiera zdarzenie i wywołuje window.desktop.notify, a proces główny pokazuje Notification. Treść traktuj jako tekst. Sprawdź to na każdym systemie, bo zasady powiadomień się różnią.'],
['Zadbaj o aktualizacje.','Stary Chromium to luka.','Podpisuj instalatory (Windows) i notaryzuj (macOS). Użyj electron-updater lub wtyczki updater w Tauri. Pilnuj aktualizacji Electrona: luka w dołączonym Chromium dotyczy wszystkich użytkowników, dopóki nie zaktualizują aplikacji.'],
['Oceń skutki XSS na pulpicie.','Co zmienia dostęp do systemu?','XSS przestaje być tylko kradzieżą sesji. Z nodeIntegration lub szerokim mostem skrypt może czytać pliki i uruchamiać programy. Dlatego renderer traktujesz jak niezaufaną stronę, a CSP i sanityzacja z rozdziału 19 nadal obowiązują.'],
['Znajdź błędy w tej konfiguracji.','Sprawdź preferencje i preload.','nodeIntegration:true daje Node stronie. contextIsolation:false łączy konteksty. exposeInMainWorld("ipc",ipcRenderer) wystawia wszystkie kanały. Brak walidacji w ipcMain.handle. Ładowanie treści z HTTP bez CSP.'],
['Zbuduj instalator.','electron-builder lub Forge.','npx electron-builder --win lub electron-forge make. Sprawdź rozmiar, uruchom na czystej maszynie i przetestuj aktualizację. Sam build nie jest podpisany, więc system pokaże ostrzeżenie: certyfikat to osobny krok.'],
['Sprawdź kryterium akceptacji lekcji.','Bezpieczne okno, eksport, powiadomienia, brak Node.','Aplikacja ma bezpieczne ustawienia okna, eksport otwiera natywne okno zapisu, a w przeglądarce zwykłe pobieranie, powiadomienia są natywne, a renderer nie ma dostępu do Node ani do surowego IPC.'],
];
const concepts=[
['Co łączy Electron i Tauri?','Interfejs web w oknie i most do systemu','Backend w Rust','Dołączony Chromium','Backend w Node','React nie wie, że działa na pulpicie.'],
['Co dołącza Electron?','Chromium i Node','Tylko WebView','Tylko Rust','Nic','Stąd duży rozmiar.'],
['Czego używa Tauri?','Systemowego WebView i Rusta','Chromium i Node','Tylko Node','Canvas','Stąd małe pliki.'],
['Jaka jest wada Tauri przy WebView?','Różne silniki na różnych systemach','Duży rozmiar','Brak uprawnień','Brak React','Testuj na każdym.'],
['Jaka jest wada Electrona?','Rozmiar i zużycie pamięci','Różne silniki','Brak Node','Brak ekosystemu','Zaletą jest ten sam silnik.'],
['Czym jest proces główny?','Node tworzący okna i obsługujący system','React','Preload','Service worker','Działa poza stroną.'],
['Czym jest renderer?','Twój React w oknie','Node','Rust','Instalator','Traktuj jak niezaufaną stronę.'],
['Czym jest preload?','Skrypt mostu między stroną a procesem głównym','Plik CSS','Test','Instalator','Wystawia wąskie API.'],
['Co oznacza IPC?','Komunikację między procesami','Protokół HTTP','Plik konfiguracyjny','Format danych','invoke i handle.'],
['Co robi contextBridge?','Bezpiecznie wystawia funkcje stronie','Otwiera okno','Kompiluje kod','Tworzy instalator','exposeInMainWorld.'],
['Co robi contextIsolation?','Oddziela kontekst preload od strony','Blokuje sieć','Wyłącza React','Szyfruje','Strona nie dostanie obiektów Electrona.'],
['Co robi nodeIntegration:false?','Strona nie ma require ani Node','Wyłącza Reacta','Blokuje preload','Blokuje okno','Podstawowa ochrona.'],
['Co robi sandbox:true?','Ogranicza proces renderera','Tworzy testy','Włącza Node','Blokuje IPC','Dodatkowa warstwa.'],
['Czym grozi nodeIntegration:true?','XSS może czytać pliki i uruchamiać programy','Wolniejszym buildem','Błędem CSS','Niczym','Najgroźniejszy skrót.'],
['Czym grozi wystawienie całego ipcRenderer?','Strona może wywołać dowolny kanał','Większą paczką','Błędem typów','Niczym','Wystawiaj konkretne funkcje.'],
['Czemu walidować w procesie głównym?','Typy nie istnieją w działaniu, a strona może być niezaufana','Dla CSS','Dla SEO','Bez powodu','Jak backend waliduje żądania.'],
['Do czego służy ipcMain.handle?','Obsługa wywołania z odpowiedzią','Wysyłka HTTP','Rysowanie okna','Logowanie','Para z ipcRenderer.invoke.'],
['Co zwraca invoke?','Promise','Liczbę','Element DOM','Plik','Trzeba obsłużyć błąd.'],
['Co wybrać do nawigacji: loadFile czy loadURL w produkcji?','loadFile z dist','loadURL do localhost','Dowolny adres','Żadne','Serwer Vite działa tylko w dev.'],
['Jak zablokować nowe okna?','setWindowOpenHandler','CSS','useEffect','nodeIntegration','Decyduje proces główny.'],
['Jak zablokować nawigację do obcych stron?','Zdarzenie will-navigate','CSS','tsc','Sentry','preventDefault dla złego źródła.'],
['Jak porównać adres z dozwolonym?','Przez URL i origin','startsWith','includes','Przez długość','startsWith przepuści podobne domeny.'],
['Czemu CSP nadal ma znaczenie?','Ogranicza skutki XSS','Nie ma znaczenia','Zastępuje sandbox','Tylko dla PWA','Ładowanie treści też podlega zasadom.'],
['Co oznacza opcjonalne window.desktop?','Kod działa też w przeglądarce','Brak typów','Brak mostu w Electronie','Tylko testy','Rozgałęzienie po obecności.'],
['Co daje współdzielony plik typów mostu?','Zmiana kanału to błąd po obu stronach','Walidację w działaniu','Mniejszą paczkę','Szyfrowanie','W JSX literówka wychodzi po kliknięciu.'],
['Czego nie da plik typów?','Walidacji danych w działaniu','Podpowiedzi','Błędu kompilacji','Dokumentacji','Dlatego proces główny waliduje.'],
['Czemu Object.hasOwn w moście?','Nie przepuści constructor ani __proto__','Dla szybkości','Dla CSS','Bez powodu','Zwykłe in sprawdza też prototyp.'],
['Jak nazywa się funkcja backendu w Tauri?','Polecenie (command)','Handler CSS','Hook','Trasa','Atrybut tauri::command.'],
['Jak wywołać polecenie z Reacta?','invoke z @tauri-apps/api/core','fetch','ipcRenderer','require','Argumenty w obiekcie.'],
['Jak dać typ wyniku invoke?','invoke<number>','as any','Nie da się','Przez CSS','Typ nie jest sprawdzany w działaniu.'],
['Czym są capabilities?','Deklaracje dozwolonych uprawnień','Testy','Style','Instalatory','Domyślnie zamknięty model.'],
['Co się stanie bez capability?','Wywołanie jest odrzucane','Działa normalnie','Aplikacja się zamyka','Pojawia się reklama','Model domyślnie zamknięty.'],
['Co zrobić z uprawnieniami do plików?','Ograniczyć zakres, np. do folderu','Dać wszystkie','Wyłączyć walidację','Trzymać w CSS','Minimalne uprawnienia.'],
['Czy Tauri 2 działa na telefonach?','Tak, Android i iOS','Nie','Tylko Android','Tylko iOS','Ten sam kod może trafić dalej.'],
['Kiedy desktop ma sens?','Funkcje systemu i długie sesje','Prosta strona','Zawsze','Nigdy','Inaczej PWA wystarczy.'],
['Kiedy PWA wystarczy?','Instalacja, powiadomienia, offline','Globalne skróty','Zasobnik','Dowolne pliki systemowe','Mniej pracy i brak instalatorów.'],
['Kiedy Electron?','Zespół JS i ten sam silnik wszędzie','Tylko Rust','Rozmiar najważniejszy','Nigdy','Bogaty ekosystem.'],
['Kiedy Tauri?','Rozmiar, uprawnienia i gotowość na Rust','Zawsze','Gdy brak Rusta i pośpiech','Nigdy','Trzeba przetestować WebView.'],
['Czym jest podpisany instalator?','Plik z certyfikatem wydawcy','Plik zaszyfrowany','Zip','Skrót','Bez tego system ostrzega.'],
['Czym jest notaryzacja na macOS?','Sprawdzenie przez Apple przed dystrybucją','Szyfrowanie dysku','Test UI','Sklep','Wymagana dla bezproblemowego uruchomienia.'],
['Czemu aktualizacje są ważne?','Stary Chromium to luka u wszystkich','Tylko dla ikon','Dla SEO','Bez powodu','Automatyczne aktualizacje to podstawa.'],
['Co robi electron-updater?','Automatyczne aktualizacje','Testy','Podpis','Build CSS','Część electron-builder.'],
['Czym różni się main od renderer w testach?','Renderer testujesz jak React, a main osobno','Niczym','Main nie da się testować','Renderer wymaga Electrona zawsze','Atrapa mostu wystarcza dla komponentu.'],
['Czym testować całość Electrona?','Playwright dla Electrona','Tylko jsdom','Tylko tsc','Sentry','Uruchamia prawdziwą aplikację.'],
['Co pokazuje atrapa window.desktop?','Logikę komponentu, a nie prawdziwy IPC','Prawdziwy zapis pliku','Instalator','Podpis','Trzeba też uruchomić aplikację.'],
['Co zrobić z anulowaniem okna zapisu?','Zwrócić null i pokazać stan','Rzucić wyjątek krytyczny','Zapisać do temp','Zamknąć aplikację','To normalny wynik.'],
['Gdzie zapisać dane raportu w przeglądarce?','Pobranie przez Blob i link','Plik w systemie bez zgody','localStorage zawsze','Cookie','Bez dostępu do systemu plików.'],
['Po co revokeObjectURL?','Zwolnić pamięć','Usunąć plik z dysku','Zablokować link','Dla CSS','Blob URL trzyma dane.'],
['Czy w preload wolno używać ipcRenderer.invoke(channel)?','Nie z kanałem od strony','Zawsze','Tylko w dev','Tylko w TS','Kanał ma być stały w kodzie preload.'],
['Jak zachować się na macOS po zamknięciu okien?','Aplikacja zwykle zostaje','Zawsze kończy pracę','Wyłącza system','Usuwa dane','Stąd warunek platformy.'],
['Co zrobić z danymi z SSE w powiadomieniu?','Traktować jako tekst','Wstawić jako HTML','Wykonać','Zapisać do main.js','Dane z sieci są niezaufane.'],
['Co oznacza „jedna aplikacja, dwa środowiska”?','Ten sam interfejs w przeglądarce i pulpicie','Dwa kody','Dwa frameworki','Brak testów','Dzięki sprawdzeniu mostu.'],
['Czym jest electron-vite?','Narzędziem łączącym Electron z Vite','Silnikiem przeglądarki','Testem','Pakietem CSS','Uproszcza konfigurację.'],
['Czym jest Electron Forge?','Narzędziem do budowania i pakowania','Silnikiem','Biblioteką stylów','Serwerem','Alternatywa dla electron-builder.'],
['Co robi Tray?','Ikona w zasobniku systemowym','Lista w React','Pasek przewijania','Cache','Trzeba trzymać referencję.'],
['Co robi globalShortcut?','Skrót działający poza aplikacją','Skrót w formularzu','Styl','Trasę','Niedostępny w przeglądarce.'],
['Czy XSS jest tu groźniejszy niż w przeglądarce?','Tak, przy dostępie do systemu','Nie','Tylko w Tauri','Nie istnieje','Stąd restrykcyjne ustawienia.'],
['Jak traktować zdalne treści?','Tylko HTTPS z CSP, najlepiej lokalne pliki','Dowolny adres','HTTP','Bez ograniczeń','Zmniejsza ryzyko.'],
['Co sprawdzić przed wyborem technologii?','Prototyp z pomiarem rozmiaru i pamięci','Liczbę gwiazdek','Modę','Kolor logo','Dane z własnej aplikacji.'],
['Co jest głównym celem lekcji?','Bezpieczne połączenie interfejsu z systemem','Przepisanie Reacta','Nowe style','Usunięcie Vite','Most jest granicą bezpieczeństwa.'],
];
const header=String.raw`import {allowNavigation,auditCapability,auditPreload,auditWebPreferences,chooseDesktop,createBridge,validateReport} from "./desktopAudit";
const safe={contextIsolation:true,nodeIntegration:false,sandbox:true};
const bridge=createBridge({add:(a:number,b:number)=>a+b});
const call=(name:string)=>{try{return String(bridge.invoke(name,1,2));}catch(e){return (e as Error).message;}};`;
const probeRows=[
['Bezpieczne ustawienia','auditWebPreferences(safe).length',0,'Wszystkie wymagane wartości.'],
['Puste ustawienia','auditWebPreferences({}).length',3,'Brak wartości jest też problemem.'],
['nodeIntegration true','auditWebPreferences({...safe,nodeIntegration:true})[0]','nodeIntegration musi być false','Node w stronie.',['contextIsolation musi być true','sandbox powinien być true','brak']],
['contextIsolation false','auditWebPreferences({...safe,contextIsolation:false})[0]','contextIsolation musi być true','Konteksty połączone.',['nodeIntegration musi być false','sandbox powinien być true','brak']],
['sandbox false','auditWebPreferences({...safe,sandbox:false})[0]','sandbox powinien być true','Mniej ochrony.',['nodeIntegration musi być false','contextIsolation musi być true','brak']],
['webSecurity false','auditWebPreferences({...safe,webSecurity:false})[0]','webSecurity wyłączone','Wyłącza ochronę źródeł.',['nodeIntegration musi być false','sandbox powinien być true','brak']],
['webSecurity brak','auditWebPreferences(safe).includes("webSecurity wyłączone")',false,'Domyślnie włączone.'],
['Preload z funkcjami','auditPreload(\'exposeInMainWorld("d",{save:c=>ipcRenderer.invoke("report:save",c)})\').length',0,'Stały kanał.'],
['Preload z całym ipcRenderer','auditPreload(\'exposeInMainWorld("ipc",ipcRenderer)\')[0]','preload wystawia cały ipcRenderer','Dowolny kanał.',['preload wystawia moduł Node','kanał IPC podawany przez stronę','brak']],
['Preload z fs','auditPreload(\'exposeInMainWorld("fs",require("fs"))\')[0]','preload wystawia moduł Node','Pełny dostęp do plików.',['preload wystawia cały ipcRenderer','kanał IPC podawany przez stronę','brak']],
['Preload z kanałem od strony','auditPreload(\'exposeInMainWorld("d",{call:ch=>ipcRenderer.invoke(ch)})\')[0]','kanał IPC podawany przez stronę','Strona wybiera kanał.',['preload wystawia cały ipcRenderer','preload wystawia moduł Node','brak']],
['Walidacja tekstu','String(validateReport("a"))','null','Poprawny raport.',['Raport musi być tekstem','Raport jest zbyt duży','a']],
['Walidacja liczby','validateReport(5)','Raport musi być tekstem','Zły typ.',['Raport jest zbyt duży','null','5']],
['Walidacja obiektu','validateReport({})','Raport musi być tekstem','Zły typ.',['Raport jest zbyt duży','null','object']],
['Walidacja rozmiaru','validateReport("abc",2)','Raport jest zbyt duży','Limit chroni pamięć.',['Raport musi być tekstem','null','abc']],
['Walidacja na granicy','String(validateReport("ab",2))','null','Równy limit przechodzi.',['Raport jest zbyt duży','Raport musi być tekstem','ab']],
['Most: znany kanał','call("add")','3','Handler działa.',['Nieznany kanał: add','1','undefined']],
['Most: nieznany','call("exec")','Nieznany kanał: exec','Nie przechodzi.',['3','exec','undefined']],
['Most: constructor','call("constructor")','Nieznany kanał: constructor','Object.hasOwn nie patrzy na prototyp.',['3','function Object() { [native code] }','undefined']],
['Most: toString','call("toString")','Nieznany kanał: toString','To samo.',['[object Object]','3','undefined']],
['Czy operator in widzi constructor?','"constructor" in {add(){}}',true,'Dlatego nie używamy in.'],
['Czy hasOwn widzi constructor?','Object.hasOwn({add(){}},"constructor")',false,'Tylko własne pola.'],
['Uprawnienia zgodne','auditCapability({identifier:"m",permissions:["a:x"]},["a:x"]).length',0,'Dokładnie to, co potrzebne.'],
['Brak uprawnienia','auditCapability({identifier:"m",permissions:[]},["a:x"])[0]','brak uprawnienia: a:x','Wywołanie zostanie odrzucone.',['niepotrzebne uprawnienie: a:x','szerokie uprawnienie systemu plików: a:x','brak']],
['Nadmiarowe uprawnienie','auditCapability({identifier:"m",permissions:["b:y"]},[])[0]','niepotrzebne uprawnienie: b:y','Minimalny zakres.',['brak uprawnienia: b:y','szerokie uprawnienie systemu plików: b:y','brak']],
['Szerokie fs','auditCapability({identifier:"m",permissions:["fs:default"]},["fs:default"])[0]','szerokie uprawnienie systemu plików: fs:default','Nawet gdy zadeklarowane jako potrzebne.',['niepotrzebne uprawnienie: fs:default','brak uprawnienia: fs:default','brak']],
['Technologia bez funkcji systemu','chooseDesktop({})','PWA','Mniej pracy.',['Electron','Tauri','brak']],
['Technologia z plikami i JS','chooseDesktop({systemFiles:true,team:"js"})','Electron','Znany stos.',['PWA','Tauri','brak']],
['Technologia z zasobnikiem','chooseDesktop({tray:true})','Electron','Domyślnie w modelu.',['PWA','Tauri','brak']],
['Technologia: rozmiar i Rust','chooseDesktop({globalShortcut:true,team:"rust",sizeMatters:true})','Tauri','Spełnione warunki.',['PWA','Electron','brak']],
['Technologia: rozmiar bez Rusta','chooseDesktop({systemFiles:true,team:"js",sizeMatters:true})','Electron','Brak kompetencji.',['PWA','Tauri','brak']],
['Nawigacja w tym samym źródle','allowNavigation("https://a.example/x","https://a.example")',true,'Ten sam origin.'],
['Nawigacja do obcej domeny','allowNavigation("https://b.example","https://a.example")',false,'Inny origin.'],
['Podobna domena','allowNavigation("https://a.example.zly.pl","https://a.example")',false,'Tu startsWith by zawiódł.'],
['Czy startsWith przepuści podobną domenę?','"https://a.example.zly.pl".startsWith("https://a.example")',true,'Dlatego porównujemy origin.'],
['Inny protokół','allowNavigation("http://a.example","https://a.example")',false,'Protokół należy do origin.'],
['Inny port','allowNavigation("https://a.example:444","https://a.example")',false,'Port należy do origin.'],
['Niepoprawny adres','allowNavigation("x","https://a.example")',false,'Wyjątek obsłużony.'],
['Adres javascript','allowNavigation("javascript:alert(1)","https://a.example")',false,'Inne źródło.'],
['Origin adresu','new URL("https://a.example:8443/x").origin','https://a.example:8443','Schemat, host i port.',['https://a.example','a.example:8443','https://a.example:8443/x']],
];
const project={
'src/desktopAudit.ts':audit,'src/ExportButton.tsx':exportButton,'src/desktop.test.tsx':tests,'electron/main.cjs':main,'electron/preload.cjs':preload,'tauri/useLogStats.ts.txt':logs,
'README.md':'DevDesk Desktop: fragmenty. npm install, npm test. Komponent ExportButton jest testowany z atrapą mostu, a pliki Electrona przechodzą przegląd tekstowy. Electron ani Tauri nie są tu uruchamiane: okno zapisu, powiadomienia, zasobnik i instalator sprawdź w prawdziwej aplikacji, np. przez Playwright dla Electrona.\n',
};
const mutants=[
{file:'src/desktopAudit.ts',before:'if(prefs.nodeIntegration!==false)problems.push("nodeIntegration musi być false");',after:''},
{file:'src/desktopAudit.ts',before:'if(/exposeInMainWorld\\([^,]+,\\s*ipcRenderer\\s*\\)/.test(source))problems.push("preload wystawia cały ipcRenderer");',after:''},
{file:'src/desktopAudit.ts',before:'if(!Object.hasOwn(handlers,channel))',after:'if(!(channel in handlers))'},
{file:'src/desktopAudit.ts',before:'if(value.length>limit)return "Raport jest zbyt duży";',after:''},
{file:'src/desktopAudit.ts',before:'new URL(url).origin===allowedOrigin',after:'url.startsWith(allowedOrigin)'},
{file:'src/ExportButton.tsx',before:'if(path)await window.desktop.notify("DevDesk","Raport zapisany");',after:'await window.desktop.notify("DevDesk","Raport zapisany");'},
{file:'src/ExportButton.tsx',before:'URL.revokeObjectURL(url);',after:''},
{file:'src/ExportButton.tsx',before:'setStatus("Nie udało się zapisać raportu");',after:''},
{file:'electron/main.cjs',before:'contextIsolation:true',after:'contextIsolation:false'},
{file:'electron/preload.cjs',before:'contextBridge.exposeInMainWorld("desktop",{\n  saveReport:csv=>ipcRenderer.invoke("report:save",csv),\n  notify:(title,body)=>ipcRenderer.invoke("notify",title,body),\n});',after:'contextBridge.exposeInMainWorld("desktop",ipcRenderer);'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
