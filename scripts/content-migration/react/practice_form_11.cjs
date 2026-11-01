const schema=`import {z} from "zod";
export const TEAMS=["support","sales","engineering"] as const;
export const registrationSchema=z.object({login:z.string().regex(/^[a-z0-9-]{3,20}$/,"Login: 3–20 małych liter, cyfr lub myślników."),email:z.email("Podaj poprawny e-mail."),password:z.string().min(10,"Minimum 10 znaków.").regex(/[0-9]/,"Dodaj cyfrę.").regex(/[A-Z]/,"Dodaj wielką literę."),confirmPassword:z.string(),role:z.enum(["agent","viewer"]),team:z.union([z.enum(TEAMS),z.literal("")]),terms:z.boolean().refine(value=>value,"Zaakceptuj regulamin.")}).refine(values=>values.password===values.confirmPassword,{message:"Hasła muszą być identyczne.",path:["confirmPassword"]}).refine(values=>values.role!=="agent"||values.team!=="",{message:"Wybierz zespół.",path:["team"]});
export type Values=z.infer<typeof registrationSchema>;export const initial:Values={login:"",email:"",password:"",confirmPassword:"",role:"viewer",team:"",terms:false};
export function passwordStrength(password:string):"słabe"|"średnie"|"silne"{const points=Number(password.length>=10)+Number(password.length>=14)+Number(/[0-9]/.test(password))+Number(/[A-Z]/.test(password))+Number(/[^A-Za-z0-9]/.test(password));return points>=5?"silne":points>=3?"średnie":"słabe";}
export type RegistrationResult={ok:true;login:string}|{ok:false;field:"login";message:string};
export async function registerOnServer(input:unknown,wait:(ms:number)=>Promise<void>=ms=>new Promise(resolve=>setTimeout(resolve,ms))):Promise<RegistrationResult>{const values=registrationSchema.parse(input);await wait(1000);if(["anna","piotr","admin"].includes(values.login))return {ok:false,field:"login",message:"Ten login jest zajęty."};return {ok:true,login:values.login};}`;
const form=`import {useId,useRef,useState} from "react";import {useForm} from "react-hook-form";import {zodResolver} from "@hookform/resolvers/zod";
${schema}
export function RegistrationForm({submit=registerOnServer}:{submit?:(values:Values)=>Promise<RegistrationResult>}){const id=useId();const busy=useRef(false);const [registered,setRegistered]=useState<string|null>(null);const {register,handleSubmit,watch,setValue,trigger,setError,setFocus,clearErrors,formState:{errors,isSubmitting}}=useForm<Values>({resolver:zodResolver(registrationSchema),defaultValues:initial,mode:"onTouched",reValidateMode:"onChange"});const role=watch("role");const password=watch("password");const strength=passwordStrength(password);const info=(field:keyof Values)=>({"aria-invalid":errors[field]?true:undefined,"aria-describedby":errors[field]?id+"-"+field+"-error":undefined});const message=(field:keyof Values)=>errors[field]?<p id={id+"-"+field+"-error"}>{errors[field]?.message}</p>:null;async function onValid(values:Values){if(busy.current)return;busy.current=true;clearErrors("root.server");try{const result=await submit({...values,team:values.role==="viewer"?"":values.team});if(result.ok){setRegistered(result.login);}else{setError(result.field,{type:"server",message:result.message});setFocus(result.field);}}catch{setError("root.server",{type:"server",message:"Nie udało się utworzyć konta. Spróbuj ponownie."});}finally{busy.current=false;}}if(registered!==null)return <section><h1>Witaj, {registered}!</h1><p role="status">Konto utworzone.</p></section>;return <form noValidate onSubmit={handleSubmit(onValid)}><h1>Załóż konto w DevDesk</h1><label htmlFor={id+"-login"}>Login</label><input id={id+"-login"} autoComplete="username" readOnly={isSubmitting} {...register("login",{onChange:()=>clearErrors("login")})} {...info("login")}/>{message("login")}<label htmlFor={id+"-email"}>E-mail</label><input id={id+"-email"} type="email" autoComplete="email" readOnly={isSubmitting} {...register("email")} {...info("email")}/>{message("email")}<label htmlFor={id+"-password"}>Hasło</label><input id={id+"-password"} type="password" autoComplete="new-password" readOnly={isSubmitting} {...register("password",{deps:["confirmPassword"]})} {...info("password")}/>{message("password")}<p aria-live="polite">Siła hasła: {strength}</p><label htmlFor={id+"-confirm"}>Powtórz hasło</label><input id={id+"-confirm"} type="password" autoComplete="new-password" readOnly={isSubmitting} {...register("confirmPassword")} {...info("confirmPassword")}/>{message("confirmPassword")}<fieldset disabled={isSubmitting}><legend>Rola</legend>{(["agent","viewer"] as const).map(value=><label key={value}><input type="radio" value={value} {...register("role",{onChange:event=>{if(event.target.value==="viewer")setValue("team","");void trigger("team");}})}/>{value==="agent"?"Agent":"Obserwator"}</label>)}</fieldset>{role==="agent"&&<><label htmlFor={id+"-team"}>Zespół</label><select id={id+"-team"} disabled={isSubmitting} {...register("team")} {...info("team")}><option value="">Wybierz zespół</option>{TEAMS.map(team=><option key={team}>{team}</option>)}</select>{message("team")}</>}<label><input type="checkbox" disabled={isSubmitting} {...register("terms")} {...info("terms")}/>Akceptuję regulamin</label>{message("terms")}{errors.root?.server&&<p role="alert">{errors.root.server.message}</p>}<button type="submit" disabled={isSubmitting}>{isSubmitting?"Tworzenie konta…":"Załóż konto"}</button></form>;}`;
const exercises=[
['Zbuduj registrationSchema i wyprowadź Values przez z.infer.','Wszystkie reguły danych mają jedno źródło.',schema],
['Ogranicz login do 3–20 małych liter, cyfr i myślników.','Nie zamieniaj automatycznie danych bez określenia kontraktu.',schema],
['Sprawdź granice długości loginu 2, 3, 20 i 21.','Regex obejmuje cały tekst.',schema],
['Waliduj format e-maila przez z.email.','To walidacja formatu, nie potwierdzenie własności skrzynki.',schema],
['Dodaj reguły hasła: 10 znaków, cyfra, wielka litera.','To reguły briefu; nie deklaruj uniwersalnej oceny bezpieczeństwa.',schema],
['Przypnij niezgodność haseł do confirmPassword.','Refine obiektu z path.',schema],
['Zdefiniuj role agent/viewer i listę dopuszczonych zespołów.','Nie przyjmuj dowolnego stringa jako zespołu.',schema],
['Wymagaj zespołu tylko dla agenta.','Viewer z pustym team jest poprawny.',schema],
['Wymagaj rzeczywistego boolean true dla regulaminu.','String true nie jest akceptacją w tym schemacie.',schema],
['Napisz passwordStrength jako funkcję danych.','Pięć jawnych kryteriów, bez useEffect.',schema],
['Wyświetl wskaźnik siły z watch(password) i aria-live=polite.','To dane wyliczane, nie drugi stan.',form],
['Ustaw walidację onTouched, aby po blur poprawki działały na bieżąco.','OnBlur i reValidateMode dotyczą różnych faz.',form],
['Dodaj etykiety i unikalne identyfikatory wszystkich pól tekstowych.','UseId tworzy powiązania instancji.',form],
['Powiąż błędy z aria-invalid i aria-describedby.','Id komunikatu istnieje tylko z komunikatem.',form],
['Umieść radio w fieldset z legend.','Nazwy zapewnia register(role).',form],
['Pokaż team wyłącznie dla agent.','Ukrycie i kontrakt danych muszą być zgodne.',form],
['Po przełączeniu na viewer wyczyść team i odśwież jego błąd.','Nowa rola nie wymaga zespołu.',form],
['Nie używaj globalnego shouldUnregister bez uwzględnienia wymaganego team w schemacie.','Brak klucza i pusty string to różne dane.','Schemat wymaga team jako pustego stringa albo znanego zespołu. Jeśli odmontowane pole zostanie wyrejestrowane i usunięte z values, schemat może odrzucić brak nawet dla viewer. Przykład zachowuje pole w modelu i czyści team; inna polityka wymaga dopasowania schematu.'],
['Zasymuluj sekundę opóźnienia i listę zajętych loginów.','Wstrzykiwany wait pozwala testować bez spania.',schema],
['Waliduj także wejście symulowanego serwera.','Klient nie jest granicą zaufania.',schema],
['Wyprowadź dyskryminowaną unię RegistrationResult.','Field odczytaj dopiero po !ok.',schema],
['Przypnij błąd zajętego loginu do errors.login.','SetError z typem server.',form],
['Przenieś fokus na login po odpowiedzi o zajętości.','SetFocus potrzebuje ref kontrolki.',form],
['Pokaż ogólny błąd transportu bez usuwania wartości.','Catch nie resetuje formularza.',form],
['Podczas zapisu pokaż Tworzenie konta i zablokuj przycisk.','Pending nie oznacza sukcesu.',form],
['Dodaj synchroniczną blokadę ref na czas operacji.','Guard uzupełnia UI; idempotencja backendu to osobny temat.',form],
['Po sukcesie zastąp formularz ekranem Witaj.','Nie wyświetlaj ani nie loguj hasła.',form],
['Nie usuwaj spacji z hasła ani nie zapisuj go w localStorage.','Hasło jest dokładnym sekretem, nie tytułem ticketu.','Schemat nie trimuje password ani confirmPassword. Wskaźnik jest heurystyką z briefu, a nie pomiarem odporności hasła. Przykład nie loguje i nie utrwala hasła; rzeczywisty system wymaga bezpiecznej obsługi po stronie serwera.'],
['Sprawdź czy pierwszy błąd i błąd loginu są osiągalne klawiaturą.','Wykonaj test w przeglądarce, nie tylko SSR.',form],
['Przetestuj wszystkie dziewięć kryteriów bossa dla obu ról.','Dodaj zajęty login, błędny transport i ponowną próbę.',form],
];
const concepts=[
['Gdzie jest reguła zgodności haseł?','Refine obiektu z path confirmPassword','Tylko w CSS','W key formularza','Wyłącznie w label'],
['Kiedy zespół jest wymagany?','Dla agenta','Dla każdego viewer','Tylko przy pustym loginie','Nigdy'],
['Czy ukrycie select usuwa obowiązek poprawnego modelu?','Nie','Tak','Tylko w RHF','Tylko po blur'],
['Czy shouldUnregister może zmienić kształt values?','Tak, usunąć pole z danych','Nie','Tylko dla CSS','Tylko dla button'],
['Dlaczego team ma znane wartości?','Aby odrzucić nieznane zespoły','Aby zapisać hasło','Aby generować ID','Aby uniknąć label'],
['Czy format e-maila potwierdza własność skrzynki?','Nie','Tak','Tylko z z.email','Tylko z resolverem'],
['Jak liczyć siłę hasła w przykładzie?','Czystą funkcją z bieżącej wartości','Efektem i osobnym stanem','Wyłącznie raz po montażu','Na podstawie ID'],
['Czy siła silne oznacza spełnienie każdej polityki bezpieczeństwa?','Nie, to heurystyka briefu','Tak','Tylko w TSX','Tylko przy 14 znakach'],
['Czy trimować hasło jak tytuł?','Nie zmieniaj sekretu automatycznie','Tak zawsze','Tylko confirmPassword','Tylko przy resecie'],
['Gdzie umieścić zajęty login?','Jako błąd login przez setError','W password','W role','W key'],
['Jak ustawić fokus na zajętym loginie?','SetFocus(login)','SetValue(role)','Reset całego formularza','Zmienić className'],
['Czy disabled przycisku zastępuje serwerową idempotencję?','Nie','Tak','Tylko z ref','Tylko w production'],
['Po co blokada useRef?','Natychmiastowo odrzuca równoległe wejście w zapis','Przechowuje hasło trwale','Waliduje e-mail','Zastępuje wszystkie errors'],
['Co pokazujemy po sukcesie?','Ekran powitania z loginem','Hasło w pre','Cały sekretny payload','Zawsze pusty ekran'],
['Co zrobić po błędzie transportu?','Zachować wartości i pokazać możliwość ponowienia','Wymazać hasło bez informacji','Pokazać sukces','Zamknąć stronę'],
['Co gwarantuje infer?','Typ zgodny z output schematu','Sukces żądania','Unikalność loginu w bazie','Działający fokus'],
['Kto ostatecznie potwierdza dostępność loginu?','Serwer z odpowiednią kontrolą zapisu','Sam regex klienta','Placeholder','Wskaźnik hasła'],
['Czy wynik ok=false ma pole login sukcesu?','Nie, gałąź błędu ma field i message','Tak zawsze','Tylko dla admin','Tylko w JSX'],
['Jakiej zgody oczekuje schema terms?','Boolean true','String true','String on','Liczba 1'],
['Gdzie grupować role radio?','W fieldset z legend','W zwykłym span bez opisu','W textarea','W placeholder'],
['Dlaczego użyto onTouched?','Po pierwszym blur pole może walidować się przy poprawianiu','Aby nie sprawdzać niczego','Aby wysłać po każdym znaku','Aby zastąpić resolver'],
['Czy błąd serwera jest regułą długości loginu?','Nie, to wynik operacji','Tak','Tylko gdy login krótki','Tylko dla Zod'],
['Czy wyjątek oznacza zajęty login?','Nie, osobno obsługujemy nieudaną operację','Tak zawsze','Tylko przy Promise','Tylko w catch'],
['Dlaczego wait jest zależnością funkcji demo?','Pozwala testować wynik bez realnego opóźnienia','Włącza szyfrowanie','Zmienia enum','Przechowuje błędy'],
['Czy logować payload rejestracji do konsoli?','Nie, zawiera hasło','Tak obowiązkowo','Tylko po sukcesie','Tylko w TSX'],
['Czy zapisywać password do localStorage jako szkic?','Nie','Tak','Tylko gdy checkbox true','Tylko w JSON'],
['Co sprawdzić klawiaturą?','Kolejność, fokus błędów i obsługę radio','Tylko kolory','Tylko importy','Wyłącznie build'],
['Co daje autoComplete=new-password?','Wskazówkę dla obsługi nowych haseł','Walidację serwera','Wymuszenie 14 znaków','Blokadę autofill wszędzie'],
['Czy przy zmianie roli należy świadomie rozstrzygnąć team?','Tak, ukrycie oraz payload muszą mieć spójną politykę','Nie','Tylko po build','Tylko dla admin'],
['Czy mock serwera tworzy trwałe konto?','Nie, to symulacja przepływu','Tak','Tylko dla anna','Tylko dla agent'],
];
const quiz=concepts.map(row=>[...row,'Boss łączy schemat danych, zależne pola oraz osobne wyniki zapisu. Typy i walidacja klienta nie zastępują serwera ani testu interakcji.']);
quiz.push(...require('./practice_form_08.cjs').quiz.slice(0,30).map(row=>['Powtórka schematów rejestracji: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const login of ['ab','abc','anna','a'.repeat(20),'a'.repeat(21),'Anna','a_b','a-b','a1b',' a-b'])add('Login '+JSON.stringify(login),`registrationSchema.safeParse({...base,login:${JSON.stringify(login)}}).success`,/^[a-z0-9-]{3,20}$/.test(login));
for(const [password,valid]of [['abcdefghij',false],['ABCDEFGHIJ',false],['Abcdefghi1',true],['Abcdefgh1',false],['abcdefgh12',false],['ABCDEFGHI1',true]])add('Hasło '+password,`registrationSchema.safeParse({...base,password:${JSON.stringify(password)},confirmPassword:${JSON.stringify(password)}}).success`,valid);
for(const role of ['agent','viewer'])for(const team of ['','support','sales','engineering','unknown'])add('Rola i team '+role+'/'+team,`registrationSchema.safeParse({...base,role:${JSON.stringify(role)},team:${JSON.stringify(team)}}).success`,team!=='unknown'&&(role!=='agent'||team!==''));
for(const [label,expr,a]of [['Niezgodność','registrationSchema.safeParse({...base,confirmPassword:"inne"}).success',false],['Terms false','registrationSchema.safeParse({...base,terms:false}).success',false],['Terms string','registrationSchema.safeParse({...base,terms:"true"}).success',false],['Email zły','registrationSchema.safeParse({...base,email:"nie-email"}).success',false],['Pusty team viewer','registrationSchema.safeParse(base).success',true],['Siła puste','passwordStrength("")','słabe'],['Siła warunki podstawowe','passwordStrength("Abcdefghi1")','średnie'],['Siła komplet','passwordStrength("Abcdefghijklm1!")','silne'],['Siła same małe','passwordStrength("abcdefghijklmn")','słabe'],['Login case sensitive','registrationSchema.safeParse({...base,login:"ADMIN"}).success',false],['Brak terms','registrationSchema.safeParse({...base,terms:undefined}).success',false],['Brak team','registrationSchema.safeParse({...base,team:undefined}).success',false],['Typ infer runtime','typeof registrationSchema.parse(base).terms','boolean'],['Wynik nie współdzieli referencji','registrationSchema.parse(base)===base',false]])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);
for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik walidacji rejestracji?',a,...wrong,'Zastosuj schemat do konkretnych danych. Dostępność loginu jest osobnym wynikiem zapisu, a wskaźnik hasła ma własne jawne kryteria.',schema+'\nconst base:Values={login:"nowy-agent",email:"anna@example.com",password:"Abcdefghi1",confirmPassword:"Abcdefghi1",role:"viewer",team:"",terms:true};console.log('+expr+');']);}
const project={'src/Registration.tsx':form,'src/main.tsx':'import {createRoot} from "react-dom/client";import {RegistrationForm} from "./Registration";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<main><RegistrationForm/></main>);'};
module.exports={schema,form,exercises,quiz,project};
