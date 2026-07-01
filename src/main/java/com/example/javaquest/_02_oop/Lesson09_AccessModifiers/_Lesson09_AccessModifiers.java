package com.example.javaquest._02_oop.Lesson09_AccessModifiers;

public class _Lesson09_AccessModifiers {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔐 MODYFIKATORY DOSTĘPU – PRZEGLĄD
         * ============================================================
         * Java ma 4 poziomy dostępu:
         *
         * ┌─────────────┬──────────┬─────────┬────────────┬────────────┐
         * │  Modyfikator│ Ta klasa │ Pakiet  │ Podklasa   │ Wszędzie   │
         * ├─────────────┼──────────┼─────────┼────────────┼────────────┤
         * │  private    │    ✅    │   ❌    │     ❌     │     ❌     │
         * │  (brak)     │    ✅    │   ✅    │     ❌*    │     ❌     │
         * │  protected  │    ✅    │   ✅    │     ✅     │     ❌     │
         * │  public     │    ✅    │   ✅    │     ✅     │     ✅     │
         * └─────────────┴──────────┴─────────┴────────────┴────────────┘
         *  * package-private nie jest dostępny przez dziedziczenie z innego pakietu
         */

        /*
         * ============================================================
         * 🔴 private – DOSTĘP TYLKO W KLASIE
         * ============================================================
         * Najsilniejsza ochrona. Pola private są niedostępne z zewnątrz.
         * Dostęp przez gettery/settery (enkapsulacja).
         */

        SecretBox box = new SecretBox("super tajny sekret");
        // box.secret = "hacking"; // ❌ błąd kompilacji
        System.out.println("Przez getter: " + box.getSecret());
        System.out.println("Czy tajny? " + box.isSecret());

        /*
         * ============================================================
         * 🟡 package-private (brak modyfikatora) – DOSTĘP W PAKIECIE
         * ============================================================
         * Domyślny poziom gdy nie piszemy żadnego modyfikatora.
         * Widoczne dla klas w tym samym pakiecie.
         * Podklasy z innego pakietu NIE mają dostępu.
         */

        PackageHelper helper = new PackageHelper();
        helper.packageMethod(); // dostępne – jesteśmy w tym samym pakiecie
        System.out.println("Pakietowe pole: " + helper.packageField);

        /*
         * ============================================================
         * 🟠 protected – DOSTĘP W PAKIECIE + PODKLASY
         * ============================================================
         * Dostęp jak package-private, PLUS dziedziczone przez podklasy
         * nawet z innych pakietów.
         *
         * Używane gdy chcemy, by podklasy miały dostęp do pól/metod
         * nadklasy, ale nie chcemy ich ujawniać publicznie.
         */

        Child child = new Child();
        child.showParentData(); // protected field dziedziczone
        child.greet();

        /*
         * ============================================================
         * 🟢 public – DOSTĘP WSZĘDZIE
         * ============================================================
         * Widoczne ze wszystkich klas we wszystkich pakietach.
         * Używane dla API klasy – metod i stałych, które chcemy udostępnić.
         */

        PublicService service = new PublicService();
        service.execute(); // metoda publiczna
        System.out.println("Wersja: " + PublicService.VERSION);

        /*
         * ============================================================
         * 🔹 MODYFIKATORY NA KLASACH
         * ============================================================
         * Klasy najwyższego poziomu (top-level) mogą być:
         * - public  → widoczna z każdego pakietu
         * - (brak)  → widoczna tylko w swoim pakiecie
         *
         * Klasy wewnętrzne (nested) mogą mieć wszystkie 4 modyfikatory.
         *
         * ⚠️ W jednym pliku .java może być TYLKO JEDNA klasa public
         * i musi mieć taką samą nazwę jak plik.
         */

        /*
         * ============================================================
         * 🔹 DOBRE PRAKTYKI
         * ============================================================
         * 1. Pola: zawsze private (enkapsulacja)
         * 2. Metody pomocnicze: private (implementacja wewnętrzna)
         * 3. Metody chronione: protected (dla podklas)
         * 4. API klasy: public (udostępnione na zewnątrz)
         * 5. Zasada minimum: ujawnij tylko tyle, ile konieczne
         *
         * Reguła: zacznij od private, zwiększaj dostęp tylko gdy potrzeba.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * private     → tylko klasa
         * (brak)      → klasa + pakiet
         * protected   → klasa + pakiet + podklasy (też z innych pakietów)
         * public      → wszędzie
         *
         * Dla klas top-level: tylko public lub package-private (brak).
         */
    }
}

class SecretBox {
    private String secret; // tylko ta klasa ma dostęp

    SecretBox(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public boolean isSecret() {
        return secret != null && !secret.isEmpty();
    }

    private void internalMethod() { // tylko wewnętrznie
        System.out.println("Tajna operacja na: " + secret);
    }
}

class PackageHelper {
    String packageField = "dostępne w pakiecie"; // package-private pole
    int packageNumber = 42;

    void packageMethod() { // package-private metoda
        System.out.println("Metoda pakietowa – pole: " + packageField);
    }
}

class Parent {
    protected String name = "Rodzic"; // dostępne dla podklas

    protected void greet() {
        System.out.println("Witaj od rodzica: " + name);
    }
}

class Child extends Parent {
    void showParentData() {
        // Dostęp do protected pola z nadklasy
        System.out.println("Pole odziedziczone: " + name);
    }

    @Override
    protected void greet() {
        super.greet(); // wywołanie protected metody nadklasy
        System.out.println("I dodatkowe pozdrowienie od dziecka!");
    }
}

class PublicService {
    public static final String VERSION = "1.0.0"; // publiczna stała

    public void execute() {
        System.out.println("PublicService.execute() – wersja " + VERSION);
        privateHelper(); // private helper używany wewnętrznie
    }

    private void privateHelper() {
        System.out.println("[wewnętrzna logika]");
    }
}
