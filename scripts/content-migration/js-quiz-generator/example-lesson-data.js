const { apply } = require('./lib.js');
const c = String.raw;
const C = (code, expl, wrong) => ['c', code, expl, wrong];
const T = (q, correct, wrong, expl) => ['t', q, correct, wrong, expl];

const ex = [
  ['Napisz obiekt prostokat = { szerokosc: 4, wysokosc: 5, pole() { ... } }. Metoda pole() zwraca szerokosc * wysokosc przez this. Wypisz prostokat.pole().', 'Wewnatrz metody odczytuj pola przez this.szerokosc i this.wysokosc.',
    c`const prostokat = {
  szerokosc: 4,
  wysokosc: 5,
  pole() {
    return this.szerokosc * this.wysokosc;
  }
};

console.log(prostokat.pole()); // -> 20`],
  ['Napisz obiekt kalkulator = { wynik: 0, dodaj(n) {...}, odejmij(n) {...} }, w ktorym obie metody zwracaja this. Wykonaj lancuch kalkulator.dodaj(10).odejmij(3) i wypisz kalkulator.wynik.', 'Zwrocenie this pozwala wywolywac kolejne metody jedna po drugiej (method chaining).',
    c`const kalkulator = {
  wynik: 0,
  dodaj(n) {
    this.wynik += n;
    return this;
  },
  odejmij(n) {
    this.wynik -= n;
    return this;
  }
};

kalkulator.dodaj(10).odejmij(3);
console.log(kalkulator.wynik); // -> 7`],
  ['Napisz obiekt uzytkownik = { imie: "Ala", nazwisko: "Nowak" } z metodami pelneImie() (laczy imie i nazwisko) oraz przedstaw(), ktora wywoluje this.pelneImie() i zwraca "Nazywam sie ...". Wypisz wynik przedstaw().', 'Jedna metoda obiektu moze wywolac inna przez this.nazwaMetody().',
    c`const uzytkownik = {
  imie: "Ala",
  nazwisko: "Nowak",
  pelneImie() {
    return this.imie + " " + this.nazwisko;
  },
  przedstaw() {
    return "Nazywam sie " + this.pelneImie();
  }
};

console.log(uzytkownik.przedstaw()); // -> "Nazywam sie Ala Nowak"`],
  ['Napisz obiekt firma = { nazwa: "Acme", dzial: { nazwa: "Kadry", opis() { ... } } }. Metoda opis() zwraca this.nazwa. Wypisz firma.dzial.opis() i wyjasnij w komentarzu, do ktorego obiektu odnosi sie this.', 'this to obiekt bezposrednio przed kropka wywolania - tutaj firma.dzial, a nie firma.',
    c`const firma = {
  nazwa: "Acme",
  dzial: {
    nazwa: "Kadry",
    opis() {
      return this.nazwa;
    }
  }
};

console.log(firma.dzial.opis()); // -> "Kadry"
// this to obiekt tuz przed ostatnia kropka (firma.dzial), a nie zewnetrzny obiekt firma.`],
  ['Napisz obiekt listaZakupow = { produkty: [], dodaj(produkt) {...}, ile() {...} }. Dodaj dwa produkty i wypisz wynik ile().', 'Metoda dodaj() wola this.produkty.push(produkt), a ile() zwraca this.produkty.length.',
    c`const listaZakupow = {
  produkty: [],
  dodaj(produkt) {
    this.produkty.push(produkt);
  },
  ile() {
    return this.produkty.length;
  }
};

listaZakupow.dodaj("chleb");
listaZakupow.dodaj("mleko");
console.log(listaZakupow.ile()); // -> 2`],
  ['Zadeklaruj zmienna imie = "Globalne" oraz obiekt osoba = { imie: "Lokalne", pokaz() {...} }. Napisz metode pokaz() zwracajaca this.imie i druga, pokazBezThis(), zwracajaca samo imie (bez this). Porownaj wyniki.', 'this.imie odczytuje pole obiektu, a samo imie - zmienna z otaczajacego zasiegu.',
    c`const imie = "Globalne";
const osoba = {
  imie: "Lokalne",
  pokaz() {
    return this.imie;
  },
  pokazBezThis() {
    return imie;
  }
};

console.log(osoba.pokaz());        // -> "Lokalne"
console.log(osoba.pokazBezThis()); // -> "Globalne"`],
  ['Napisz obiekt osoba = { imie: "Ola", wiek: 30, czyStarszy(inna) {...} }. Metoda czyStarszy(inna) zwraca true, gdy this.wiek > inna.wiek. Przetestuj z obiektem { wiek: 25 } i { wiek: 40 }.', 'this to obiekt, na ktorym wywolano metode, a inna to obiekt przekazany jako argument.',
    c`const osoba = {
  imie: "Ola",
  wiek: 30,
  czyStarszy(inna) {
    return this.wiek > inna.wiek;
  }
};

console.log(osoba.czyStarszy({ wiek: 25 })); // -> true
console.log(osoba.czyStarszy({ wiek: 40 })); // -> false`],
  ['Napisz obiekt przelacznik = { wlaczony: false, przelacz() {...} }. Metoda przelacz() odwraca wartosc pola wlaczony i zwraca nowy stan. Wywolaj ja trzy razy i wypisz wyniki.', 'Uzyj this.wlaczony = !this.wlaczony.',
    c`const przelacznik = {
  wlaczony: false,
  przelacz() {
    this.wlaczony = !this.wlaczony;
    return this.wlaczony;
  }
};

console.log(przelacznik.przelacz()); // -> true
console.log(przelacznik.przelacz()); // -> false
console.log(przelacznik.przelacz()); // -> true`],
  ['Utworz pusty obiekt zwierze = { imie: "Rex" } i dopisz mu potem metode przedstaw jako function() zwracajaca "Jestem " + this.imie. Wywolaj ja.', 'Metody mozna dopisywac do obiektu w dowolnym momencie - this i tak zalezy od obiektu przed kropka.',
    c`const zwierze = { imie: "Rex" };
zwierze.przedstaw = function () {
  return "Jestem " + this.imie;
};

console.log(zwierze.przedstaw()); // -> "Jestem Rex"`],
  ['Napisz obiekt licznik = { wartosc: 0, historia: [], zwieksz() {...}, reset() {...} }. Metoda zwieksz() zwieksza wartosc o 1 i zapisuje ja w this.historia, a reset() zeruje wartosc. Wywolaj zwieksz() trzy razy, potem reset(), i wypisz licznik.wartosc oraz licznik.historia.', 'W obu metodach odwoluj sie do pol przez this.',
    c`const licznik = {
  wartosc: 0,
  historia: [],
  zwieksz() {
    this.wartosc++;
    this.historia.push(this.wartosc);
  },
  reset() {
    this.wartosc = 0;
  }
};

licznik.zwieksz();
licznik.zwieksz();
licznik.zwieksz();
licznik.reset();
console.log(licznik.wartosc);  // -> 0
console.log(licznik.historia); // -> [1, 2, 3]`],
  ['Napisz w trybie strict ("use strict") funkcje sprawdzThis() zwracajaca this === undefined. Wywolaj ja samodzielnie i zapisz w komentarzu wynik.', 'W trybie strict funkcja wywolana bez obiektu przed kropka dostaje this rowne undefined.',
    c`"use strict";

function sprawdzThis() {
  return this === undefined;
}

console.log(sprawdzThis()); // -> true`],
  ['Napisz obiekt osoba = { imie: "Ewa", powiedz() {...} } i wywolaj metode trzema sposobami: osoba.powiedz(), osoba["powiedz"]() oraz (osoba.powiedz)(). Sprawdz, czy w kazdym przypadku this to obiekt osoba.', 'Wszystkie trzy zapisy wywoluja metode "na obiekcie", wiec this jest ustawione poprawnie.',
    c`const osoba = {
  imie: "Ewa",
  powiedz() {
    return "Czesc, jestem " + this.imie;
  }
};

console.log(osoba.powiedz());     // -> "Czesc, jestem Ewa"
console.log(osoba["powiedz"]());  // -> "Czesc, jestem Ewa"
console.log((osoba.powiedz)());   // -> "Czesc, jestem Ewa"`],
  ['Napisz obiekt oceny = { lista: [], dodaj(o) {...}, srednia() {...} }. Metoda dodaj() dopisuje ocene do this.lista, a srednia() zwraca srednia arytmetyczna (uzyj reduce). Dodaj oceny 4, 5, 3 i wypisz srednia.', 'srednia = suma / this.lista.length, a sume policzysz przez this.lista.reduce((a, b) => a + b, 0).',
    c`const oceny = {
  lista: [],
  dodaj(o) {
    this.lista.push(o);
  },
  srednia() {
    const suma = this.lista.reduce((a, b) => a + b, 0);
    return suma / this.lista.length;
  }
};

oceny.dodaj(4);
oceny.dodaj(5);
oceny.dodaj(3);
console.log(oceny.srednia()); // -> 4`],
  ['Napisz funkcje fabryczna stworzOsobe(imie), ktora zwraca obiekt { imie, przedstaw() {...} }, gdzie przedstaw() zwraca "Jestem " + this.imie. Utworz dwie osoby i wywolaj przedstaw() na kazdej.', 'Kazdy obiekt z fabryki ma wlasne pole imie, a this wskazuje na ten, na ktorym wywolano metode.',
    c`function stworzOsobe(imie) {
  return {
    imie,
    przedstaw() {
      return "Jestem " + this.imie;
    }
  };
}

const ala = stworzOsobe("Ala");
const jan = stworzOsobe("Jan");
console.log(ala.przedstaw()); // -> "Jestem Ala"
console.log(jan.przedstaw()); // -> "Jestem Jan"`],
  ['Napisz obiekt sklep = { nazwa: "Piekarnia", czyTo() {...} }, gdzie metoda czyTo() zwraca this === sklep. Wypisz wynik jej wywolania.', 'Porownanie this === sklep potwierdza, ze this to dokladnie ten sam obiekt.',
    c`const sklep = {
  nazwa: "Piekarnia",
  czyTo() {
    return this === sklep;
  }
};

console.log(sklep.czyTo()); // -> true`],
  ['Napisz funkcje stworzLicznik(), ktora zwraca obiekt { n: 0, zwieksz() {...} } (zwieksz() zwieksza n o 1 i zwraca je). Utworz dwa liczniki a i b, wywolaj a.zwieksz() dwa razy oraz b.zwieksz() raz, i wypisz wyniki.', 'Kazdy licznik ma wlasne pole n - stan nie jest wspoldzielony.',
    c`function stworzLicznik() {
  return {
    n: 0,
    zwieksz() {
      this.n++;
      return this.n;
    }
  };
}

const a = stworzLicznik();
const b = stworzLicznik();
console.log(a.zwieksz()); // -> 1
console.log(a.zwieksz()); // -> 2
console.log(b.zwieksz()); // -> 1`],
  ['Napisz obiekt termostat = { temperatura: 20, podnies(o) {...}, obniz(o) {...} } z metodami zwracajacymi this. Wykonaj lancuch termostat.podnies(5).obniz(2).podnies(1) i wypisz temperature.', 'Kazda metoda musi konczyc sie return this, zeby mozna bylo wywolac nastepna.',
    c`const termostat = {
  temperatura: 20,
  podnies(o) {
    this.temperatura += o;
    return this;
  },
  obniz(o) {
    this.temperatura -= o;
    return this;
  }
};

termostat.podnies(5).obniz(2).podnies(1);
console.log(termostat.temperatura); // -> 24`],
  ['Utworz tablice liczby = [1, 2, 3], dopisz jej metode suma jako function() zwracajaca sume elementow przez this.reduce. Wywolaj liczby.suma().', 'Tablica to tez obiekt - w metodzie wywolanej na tablicy this to ta tablica.',
    c`const liczby = [1, 2, 3];
liczby.suma = function () {
  return this.reduce((a, b) => a + b, 0);
};

console.log(liczby.suma()); // -> 6`],
  ['Napisz obiekt uzytkownik = { imie: "Tomek", klucze() {...} }, w ktorym metoda klucze() zwraca Object.keys(this). Wypisz wynik i wyjasnij w komentarzu, dlaczego na liscie jest takze sama metoda.', 'Metoda jest wlasciwoscia obiektu, wiec Object.keys(this) zwroci takze jej nazwe.',
    c`const uzytkownik = {
  imie: "Tomek",
  klucze() {
    return Object.keys(this);
  }
};

console.log(uzytkownik.klucze()); // -> ["imie", "klucze"]
// Metoda klucze tez jest wlasciwoscia obiektu, wiec pojawia sie na liscie.`],
  ['Napisz obiekt baza = { przedstaw() { return this.imie; } }. Utworz na jego podstawie Object.create(baza) obiekt osoba, ustaw osoba.imie = "Zed" i wywolaj osoba.przedstaw(). Wyjasnij w komentarzu, dlaczego this to osoba, a nie baza.', 'this zalezy od obiektu przed kropka w wywolaniu, a nie od miejsca, gdzie metoda jest zdefiniowana.',
    c`const baza = {
  przedstaw() {
    return this.imie;
  }
};

const osoba = Object.create(baza);
osoba.imie = "Zed";

console.log(osoba.przedstaw()); // -> "Zed"
// Metoda jest zdefiniowana w bazie, ale wywolana na osobie - this to obiekt przed kropka (osoba).`],
  ['Napisz obiekt konto = { saldo: 100, wplac(kwota) {...}, wyplac(kwota) {...}, stan() {...} }. Metoda wyplac() nie pozwala zejsc ponizej zera - zwraca wtedy false i nie zmienia salda. Sprawdz wyplate 30 i 500.', 'Przed odjeciem porownaj this.saldo z kwota.',
    c`const konto = {
  saldo: 100,
  wplac(kwota) {
    this.saldo += kwota;
  },
  wyplac(kwota) {
    if (kwota > this.saldo) return false;
    this.saldo -= kwota;
    return true;
  },
  stan() {
    return this.saldo;
  }
};

console.log(konto.wyplac(30));  // -> true
console.log(konto.wyplac(500)); // -> false
console.log(konto.stan());      // -> 70`],
];

const q = [
  // ---------- metody i this ----------
  C(c`
const o = {
  imie: "Ala",
  powiedz() {
    return "Czesc " + this.imie;
  }
};
console.log(o.powiedz());`, 'Metoda jest wywołana przez obiekt o, więc this wskazuje na o, a this.imie to "Ala".'),
  C(c`
function powiedz() {
  return "Czesc " + this.imie;
}
const a = { imie: "Ala", powiedz };
const b = { imie: "Jan", powiedz };
console.log(a.powiedz(), b.powiedz());`, 'Jedna funkcja przypisana do dwóch obiektów daje dwa różne wyniki - this zależy od obiektu, na którym wywołano metodę.'),
  C(c`
const o = {
  x: 0,
  inc() {
    this.x++;
  }
};
o.inc();
o.inc();
o.inc();
console.log(o.x);`, 'Każde wywołanie zwiększa pole x tego samego obiektu, na którym metoda została wywołana.'),
  C(c`
const k = {
  wartosc: 0,
  dodaj(n) {
    this.wartosc += n;
    return this;
  }
};
console.log(k.dodaj(2).dodaj(3).wartosc);`, 'Metoda zwraca this, czyli sam obiekt, więc można łączyć wywołania w łańcuch (method chaining).'),
  C(c`
const k = {
  wartosc: 0,
  dodaj(n) {
    this.wartosc += n;
  }
};
try {
  k.dodaj(2).dodaj(3);
} catch (e) {
  console.log(e.name);
}`, 'Metoda nic nie zwraca (undefined), więc drugie .dodaj() wywoływane jest na undefined - to TypeError. Łańcuchowanie wymaga return this.'),
  C(c`
const firma = {
  nazwa: "Acme",
  dzial: {
    nazwa: "Kadry",
    opis() {
      return this.nazwa;
    }
  }
};
console.log(firma.dzial.opis());`, 'this to obiekt bezpośrednio przed ostatnią kropką: firma.dzial, więc odczytywana jest jego nazwa - "Kadry".'),
  C(c`
const imie = "Globalne";
const o = {
  imie: "Lokalne",
  a() { return this.imie; },
  b() { return imie; }
};
console.log(o.a(), o.b());`, 'this.imie to pole obiektu ("Lokalne"), a samo imie to zmienna z zasięgu zewnętrznego ("Globalne"). Pola obiektu nie są zmiennymi.'),
  C(c`
const o = {
  x: 4,
  podwoj(n) { return n * 2; },
  wynik() {
    return this.podwoj(this.x);
  }
};
console.log(o.wynik());`, 'Metoda wywołuje inną metodę tego samego obiektu przez this.podwoj(...) - this.x to 4, więc wynik to 8.'),
  C(c`
const o = {
  x: 4,
  podwoj(n) { return n * 2; },
  wynik() {
    return podwoj(this.x);
  }
};
try {
  console.log(o.wynik());
} catch (e) {
  console.log(e.name);
}`, 'Bez this nazwa podwoj jest szukana jako zmienna w zasięgu, a nie jako metoda obiektu - powstaje ReferenceError.'),
  C(c`
const o = {
  a: 1,
  b: this.a
};
console.log(o.b);`, 'this w samym literale obiektu (poza metodą) nie wskazuje na ten obiekt - odnosi się do zewnętrznego kontekstu, gdzie właściwość a nie istnieje.'),
  C(c`
"use strict";
function f() {
  return this;
}
console.log(f());`, 'W trybie strict samodzielne wywołanie funkcji daje this równe undefined (a nie obiekt globalny).'),
  C(c`
"use strict";
function f() {
  return this.x;
}
try {
  f();
} catch (e) {
  console.log(e.name);
}`, 'this jest undefined, więc odczyt this.x to odczyt właściwości z undefined - TypeError.'),
  C(c`
function f() {
  return this === globalThis;
}
console.log(f());`, 'W trybie luźnym (bez use strict) samodzielne wywołanie daje obiekt globalny jako this - stąd true. To dlatego tryb strict jest bezpieczniejszy.'),
  C(c`
const o = {
  imie: "Ala",
  f() { return this.imie; }
};
console.log((o.f)(), o["f"]());`, 'Zapis (o.f)() oraz o["f"]() nadal wywołuje metodę na obiekcie o, więc this jest poprawne. Liczy się to, że wywołanie odbywa się "na obiekcie".'),
  C(c`
const a = { n: "a", f() { return this.n; } };
const b = { n: "b", f: a.f };
console.log(a.f(), b.f());`, 'Do obiektu b trafiła ta sama funkcja, ale wywołana jako b.f() ma this równe b - stąd "b".'),
  C(c`
const o = { czyTo() { return this === o; } };
console.log(o.czyTo());`, 'this w metodzie wywołanej na obiekcie to dokładnie ten obiekt - porównanie przez === daje true.'),
  C(c`
const o = {
  zwroc() { return this; }
};
console.log(o.zwroc() === o);`, 'Metoda zwraca this, czyli obiekt, na którym została wywołana - tożsamy z o.'),
  C(c`
const t = [1, 2, 3];
t.suma = function () {
  return this.reduce((a, b) => a + b, 0);
};
console.log(t.suma());`, 'Tablica jest obiektem - w metodzie wywołanej na tablicy this to ta tablica, więc reduce sumuje jej elementy.'),
  C(c`
const t = [];
t.f = function () { return this === t; };
console.log(t.f(), [function () { return this === t; }][0]());`, 'W pierwszym wywołaniu this to tablica t. Drugie wywołanie idzie przez element innej tablicy (this to ta nowa tablica), więc porównanie z t jest fałszywe.'),
  C(c`
const lista = [
  { n: "a", f() { return this.n; } },
  { n: "b", f() { return this.n; } }
];
console.log(lista.map(o => o.f()));`, 'Każdy obiekt w tablicy wywołuje metodę na sobie, więc this.n zwraca inne wartości.'),
  C(c`
const o = { x: 1, f() { return this.x; } };
o.x = 5;
console.log(o.f());`, 'this.x jest odczytywane w chwili wywołania - widać już zmienioną wartość 5.'),
  C(c`
const o = {
  x: 1,
  usun() {
    delete this.x;
  }
};
o.usun();
console.log(o.x);`, 'delete this.x usuwa właściwość z obiektu, na którym wywołano metodę - potem odczyt daje undefined.'),
  C(c`
const a = { przekaz(b) { b.pole = "ustawione"; return this === a; } };
const b = {};
console.log(a.przekaz(b), b.pole);`, 'this to a (obiekt przed kropką), a argument b jest oddzielnym obiektem, którego pole zostaje ustawione.'),
  C(c`
const baza = {
  przedstaw() { return this.imie; }
};
const os = Object.create(baza);
os.imie = "Zed";
console.log(os.przedstaw());`, 'Metoda leży w bazie, ale jest wywołana na os - this to os, więc odczytywane jest jego pole imie ("Zed").'),
  C(c`
const baza = {
  przedstaw() { return this.imie; }
};
console.log(baza.przedstaw());`, 'Wywołanie na obiekcie baza daje this równe baza, który nie ma pola imie, więc wynik to undefined (bez błędu).'),
  C(c`
const o = {
  a: 2,
  get podwojone() {
    return this.a * 2;
  }
};
console.log(o.podwojone);`, 'Getter wywołany na obiekcie ma this równe temu obiektowi, więc this.a to 2.'),
  C(c`
function stworz() {
  return {
    n: 0,
    inc() {
      this.n++;
      return this.n;
    }
  };
}
const a = stworz();
const b = stworz();
console.log(a.inc(), a.inc(), b.inc());`, 'Każdy obiekt ma własne pole n, więc liczniki nie wpływają na siebie: 1, 2 dla a i 1 dla b.'),
  C(c`
const f = function () { return this.v; };
const o1 = { v: 1, f };
const o2 = { v: 2, f };
console.log(o1.f() + o2.f());`, 'Ta sama funkcja wywołana raz na o1 (1) i raz na o2 (2) daje razem 3.'),
  C(c`
const obj = {
  a: { b: { f() { return this === obj.a.b; } } }
};
console.log(obj.a.b.f());`, 'this to obiekt przed ostatnią kropką - obj.a.b, a nie obj ani obj.a.'),
  C(c`
const lista = [{ n: 1, f() { return this.n; } }];
console.log(lista[0].f());`, 'lista[0] jest obiektem, na którym wywołano metodę, więc this.n to 1.'),
  C(c`
const o = {
  a: 1,
  b: 2,
  opis() {
    return this.a + "-" + this.b;
  }
};
console.log(o.opis());`, 'Metoda skleja dwa pola obiektu odczytane przez this: "1-2".'),
  C(c`
const o = {
  a: 1,
  klucze() {
    return Object.keys(this);
  }
};
console.log(o.klucze());`, 'Metoda jest własnością obiektu, więc Object.keys(this) zwraca także jej nazwę.'),
  C(c`
const baza = {
  lista: [],
  dodaj(x) { this.lista.push(x); }
};
const a = { ...baza };
const b = { ...baza };
a.dodaj(1);
console.log(b.lista.length);`, 'Spread kopiuje płytko - a.lista i b.lista wskazują tę samą tablicę, więc dodanie elementu przez a widać także w b.'),
  C(c`
const o = {
  zwroc() { return this; }
};
console.log(o.zwroc() === o.zwroc());`, 'Za każdym razem this to ten sam obiekt o, więc porównanie zwraca true.'),
  C(c`
const o = {
  n: 5,
  f() { return () => this.n; }
};
console.log(o.f()());`, 'Funkcja strzałkowa wewnątrz metody zapamiętuje this metody (o), więc zwraca 5. Szczegóły w lekcji o funkcjach strzałkowych.'),
  C(c`
"use strict";
[1, 2].forEach(function () {
  console.log(this === undefined);
});`, 'Callback wywołany przez forEach jest zwykłą funkcją bez obiektu przed kropką, więc w trybie strict this to undefined.'),
  C(c`
"use strict";
const o = {
  n: 1,
  f() {
    function w() {
      return this;
    }
    return w();
  }
};
console.log(o.f());`, 'Funkcja zagnieżdżona w metodzie jest wywoływana samodzielnie (w()), więc nie dziedziczy this metody - w trybie strict to undefined.'),
  C(c`
const o = {
  a: 1,
  f() {
    return typeof this.a + " " + typeof this.f;
  }
};
console.log(o.f());`, 'this to obiekt o: pole a jest liczbą, a f jest funkcją.'),
  C(c`
const a = { wartosc: 10, pokaz() { return this.wartosc; } };
const b = { wartosc: 20 };
b.pokaz = a.pokaz;
a.wartosc = 99;
console.log(b.pokaz(), a.pokaz());`, 'this zależy od obiektu w chwili wywołania: b.pokaz() czyta b.wartosc (20), a a.pokaz() czyta już zmienione a.wartosc (99).'),
  C(c`
const o = { imie: "Ola", f() { return this.imie.length; } };
console.log(o.f());`, 'this.imie to "Ola", więc długość napisu wynosi 3.'),
  C(c`
const stos = {
  el: [],
  dodaj(x) { this.el.push(x); return this; },
  ile() { return this.el.length; }
};
console.log(stos.dodaj("a").dodaj("b").dodaj("c").ile());`, 'Trzy wywołania dodaj() są połączone w łańcuch, bo każde zwraca this, a na końcu ile() zwraca liczbę elementów.'),
  C(c`
const o = { x: 1 };
function f() { return this.x; }
o.f = f;
console.log(o.f(), typeof f);`, 'Po przypisaniu funkcji do właściwości można ją wywołać jako metodę o.f() - wtedy this to o (wynik 1). Sama f pozostaje funkcją.'),
  C(c`
const a = { f() { return this; } };
const b = { g: a.f };
console.log(b.g() === b, b.g() === a);`, 'Metoda wywołana jako b.g() ma this równe b, a nie a, mimo że funkcja pochodzi z obiektu a.'),
  C(c`
const o = {
  historia: [],
  akcja(nazwa) {
    this.historia.push(nazwa);
    return this;
  }
};
o.akcja("start").akcja("stop");
console.log(o.historia);`, 'Obie akcje trafiają do tablicy tego samego obiektu, bo metody zwracają this.'),
  C(c`
const o = {
  f() { return this.g(); },
  g() { return "g wywolane"; }
};
console.log(o.f());`, 'Metoda f wywołuje g przez this.g(), więc obie są znalezione na tym samym obiekcie.'),
  C(c`
const o = {
  ustaw(v) { this.dane = v; }
};
o.ustaw(7);
console.log(o.dane);`, 'Przypisanie do this.dane tworzy nową właściwość na obiekcie, na którym wywołano metodę.'),
  C(c`
const a = { n: 1, f() { return this.n; } };
const b = Object.create(a);
b.n = 2;
console.log(a.f(), b.f());`, 'Ta sama metoda wywołana na a daje 1, a na b (który dziedziczy po a, ale ma własne n) daje 2.'),
  C(c`
const o = {
  liczby: [1, 2, 3],
  suma() {
    let s = 0;
    for (const n of this.liczby) s += n;
    return s;
  }
};
console.log(o.suma());`, 'this.liczby to tablica obiektu, po której iterujemy for...of - suma to 6.'),
  C(c`
const o = {
  tekst: "abc",
  wielkie() { return this.tekst.toUpperCase(); }
};
console.log(o.wielkie().length);`, 'Metoda zwraca napis zbudowany z pola obiektu (this.tekst), a length wynosi 3.'),
  C(c`
const a = { f() { return this; } };
const wynik = a.f();
wynik.dodane = true;
console.log(a.dodane);`, 'this to sam obiekt a, więc dopisanie właściwości do zwróconego obiektu zmienia oryginał.'),
  C(c`
const obj = { imie: "Kasia" };
function przedstaw() { return "Jestem " + this.imie; }
obj.przedstaw = przedstaw;
console.log(obj.przedstaw());`, 'Funkcja zdefiniowana poza obiektem i przypisana jako metoda używa this z chwili wywołania - "Jestem Kasia".'),
  C(c`
const o = {
  n: 1,
  f() {
    this.n = this.n + 1;
    return this.n;
  }
};
console.log(o.f(), o.f(), o.n);`, 'Każde wywołanie zwiększa to samo pole: 2, potem 3, a końcowa wartość n to 3.'),
  C(c`
const o = { f() { return this; } };
console.log(o.f() === o, typeof o.f());`, 'Metoda zwraca this (obiekt o), więc porównanie daje true, a typeof wyniku to "object".'),
];

q.push(
  C(c`
const o = { a: 1, b: 2, opis() { return this.a + " i " + this.b; } };
console.log(o.opis());`, 'Metoda odczytuje dwa pola obiektu przez this i skleja je w napis.'),
  C(c`
const o = { n: 1, a() { return this; }, b() { return this.n; } };
console.log(o.a().b());`, 'a() zwraca this (obiekt o), więc b() jest wywołane na tym samym obiekcie i zwraca jego pole n.'),
  C(c`
const firma = {
  nazwa: "Acme",
  dzial: { opis() { return this.nazwa; } }
};
console.log(firma.dzial.opis());`, 'this to obiekt dzial, który nie ma pola nazwa - obiekt zewnętrzny firma nie jest dostępny przez this, więc wynik to undefined.'),
  C(c`
const o = {
  baza: 10,
  suma(a, b) { return this.baza + a + b; }
};
console.log(o.suma(1, 2));`, 'Metoda łączy argumenty z polem obiektu odczytanym przez this: 10 + 1 + 2.'),
  C(c`
const o = {
  _x: 1,
  set x(v) { this._x = v * 2; },
  get x() { return this._x; }
};
o.x = 5;
console.log(o.x);`, 'Setter i getter mają this równe obiektowi o - setter zapisuje podwojoną wartość w polu _x, a getter ją odczytuje.'),
  C(c`
const o = {
  lista: [],
  dodaj(x) { this.lista.push(x); }
};
const ref = o.lista;
o.dodaj(1);
console.log(ref.length);`, 'ref i o.lista to ta sama tablica (referencja), więc element dodany przez metodę jest widoczny przez ref.'),
  C(c`
const a = { f() { return this.x; } };
const b = { f: a.f };
console.log(b.f());`, 'Metoda wywołana na b szuka pola x w obiekcie b - nie ma go, więc wynik to undefined (bez błędu).'),
  C(c`
const t = [function () { return this.length; }];
console.log(t[0]());`, 'Funkcja wywołana jako element tablicy dostaje this równe tablicy (obiekt przed nawiasem), więc this.length to 1.'),
  C(c`
const o = {
  n: 1,
  zdjecie() { return { n: this.n }; }
};
const s = o.zdjecie();
o.n = 9;
console.log(s.n);`, 'zdjecie() zwraca nowy obiekt z KOPIĄ wartości pola - późniejsza zmiana o.n nie wpływa na s.n.'),
  C(c`
let o = { n: 1, f() { return this.n; } };
const kopia = o;
o = { n: 2, f: kopia.f };
console.log(kopia.f(), o.f());`, 'Zmienna o wskazuje teraz na nowy obiekt, a kopia na stary - każde wywołanie działa na swoim obiekcie.'),
  C(c`
const cel = Object.assign({ n: 5 }, { f() { return this.n; } });
console.log(cel.f());`, 'Object.assign kopiuje metodę f do obiektu cel - wywołana na cel czyta jego pole n.'),
  C(c`
const a = { n: 1, f() { return this.n; } };
const b = { ...a, n: 2 };
console.log(a.f(), b.f());`, 'Spread kopiuje też metodę f do b, ale n jest nadpisane - każdy obiekt zwraca swoje pole.'),
  C(c`
const o = { f() { return 1; } };
delete o.f;
console.log(typeof o.f);`, 'Po usunięciu właściwości odczyt daje undefined, więc typeof zwraca "undefined".'),
  C(c`
const o = {
  x: 7,
  f(n = this.x) { return n; }
};
console.log(o.f(), o.f(1));`, 'Wartość domyślna parametru jest obliczana przy wywołaniu i może używać this - bez argumentu bierze this.x (7).'),
  C(c`
const o = { n: 3, f() { return this.n; } };
console.log(o.f?.(), o.g?.());`, 'Operator ?.() nie zmienia this (f wywołane na o daje 3), a dla nieistniejącej metody g po prostu zwraca undefined bez błędu.'),
  C(c`
const o = {
  f() {
    this.wywolania = (this.wywolania || 0) + 1;
    return this.wywolania;
  }
};
console.log(o.f(), o.f(), o.f());`, 'Pole wywolania jest tworzone przy pierwszym wywołaniu na obiekcie i zwiększane przy kolejnych: 1, 2, 3.'),
  C(c`
const ala = { wiek: 30, starszyOd(inna) { return this.wiek > inna.wiek; } };
const jan = { wiek: 25, starszyOd: ala.starszyOd };
console.log(ala.starszyOd(jan), jan.starszyOd(ala));`, 'Ta sama funkcja, ale this różni się: ala vs jan - 30 > 25 daje true, a 25 > 30 daje false.'),
  C(c`
const o = { f() { return typeof this; } };
console.log(o.f());`, 'this w metodzie to obiekt, więc typeof daje "object".'),
  C(c`
const t = [];
t.czyTab = function () { return Array.isArray(this); };
console.log(t.czyTab());`, 'this w metodzie dopisanej do tablicy to ta tablica, więc Array.isArray zwraca true.'),
  C(c`
function fn() {}
fn.nazwa = "funkcja";
fn.pokaz = function () { return this.nazwa; };
console.log(fn.pokaz());`, 'Funkcje też są obiektami i mogą mieć własne właściwości - w metodzie wywołanej na fn this to fn.'),
  C(c`
const koszyk = {
  pozycje: [{ cena: 5 }, { cena: 7 }],
  suma() {
    return this.pozycje.reduce((s, p) => s + p.cena, 0);
  }
};
console.log(koszyk.suma());`, 'Metoda odczytuje tablicę pozycji przez this i sumuje pola cena: 5 + 7 = 12.'),
  C(c`
const o = {
  dane: [],
  dodaj(x) { this.dane.push(x); return this.dane.length; }
};
console.log(o.dodaj("a"), o.dodaj("b"));`, 'Metoda zwraca aktualną liczbę elementów: po pierwszym dodaniu 1, po drugim 2.'),
  C(c`
const o = {
  a: 1,
  b: 2,
  ile() { return Object.entries(this).length; }
};
console.log(o.ile());`, 'Object.entries(this) wylicza wszystkie własne właściwości obiektu, także samą metodę ile - razem 3.'),
  C(c`
const o = {
  n: 2,
  kwadrat() { return this.n * this.n; },
  szescian() { return this.kwadrat() * this.n; }
};
console.log(o.szescian());`, 'szescian() wywołuje kwadrat() przez this i mnoży przez this.n: 4 * 2 = 8.'),
  C(c`
const o = {
  n: 0,
  dodaj() { this.n += 1; return this; }
};
o.dodaj().dodaj().dodaj();
console.log(o.n);`, 'Trzy połączone w łańcuch wywołania zwiększają to samo pole n trzy razy.'),
);

// ---------- pytania pojęciowe ----------
q.push(
  T('Dlaczego this w samym literale obiektu (poza jakąkolwiek metodą) nie wskazuje na ten obiekt?', 'Bo this ustalane jest przez sposób wywołania funkcji, a tu żadna funkcja nie jest wywoływana - this pochodzi z otaczającego kontekstu', ['Bo literały obiektów są zawsze puste', 'Bo this w literale jest zawsze równe null', 'Bo silnik JavaScript tworzy obiekt dopiero po zakończeniu programu'], 'Obiekt nie jest jeszcze gotowy w chwili obliczania jego właściwości, a this nie jest jego "adresem" - zależy od kontekstu wywołania.'),
  T('Po co metoda obiektu zwraca this (return this)?', 'Aby można było łączyć wywołania w łańcuch, np. obiekt.a().b().c()', ['Aby przyspieszyć działanie programu', 'Aby zamienić obiekt na napis', 'Aby uniknąć użycia słowa new'], 'Zwrócenie this daje kolejnemu wywołaniu ten sam obiekt, na którym można wywołać następną metodę (method chaining).'),
  T('Który element wywołania decyduje o wartości this w metodzie?', 'Obiekt stojący bezpośrednio przed ostatnią kropką w wywołaniu (np. w a.b.metoda() jest to a.b)', ['Obiekt, w którym metoda została zdefiniowana', 'Pierwszy obiekt na początku całego wyrażenia (a)', 'Nazwa metody'], 'W zapisie a.b.metoda() metodę wywołujemy na obiekcie a.b, więc to on jest this.'),
  T('Dlaczego dwa obiekty utworzone tą samą funkcją fabryczną mają niezależny stan?', 'Bo każde wywołanie fabryki tworzy nowy obiekt z własnymi polami, a this wskazuje na ten, na którym wywołano metodę', ['Bo funkcje fabryczne kopiują cały kod programu', 'Bo this zawsze wskazuje na najnowszy obiekt', 'Bo stan jest przechowywany w przeglądarce'], 'Pola są własnością konkretnego obiektu, a this wybiera ten, do którego się odwołujemy.'),
  T('Czym różni się samodzielne wywołanie funkcji w trybie strict od trybu luźnego, jeśli chodzi o this?', 'W trybie strict this jest undefined, a w luźnym jest to obiekt globalny (window lub globalThis)', ['W trybie strict this to zawsze obiekt window', 'Nie ma żadnej różnicy między trybami', 'W trybie luźnym this jest undefined, a w strict - null'], 'Moduły ES i klasy działają w trybie strict automatycznie, dlatego w nowoczesnym kodzie samodzielne wywołanie daje undefined.'),
  T('Czy nazwa metody (np. "przedstaw") wpływa na wartość this?', 'Nie - this zależy wyłącznie od sposobu wywołania funkcji', ['Tak, metody o nazwie zaczynającej się od "get" mają inne this', 'Tak, this jest tworzone na podstawie nazwy metody', 'Tak, ale tylko przy metodach dłuższych niż jeden znak'], 'Nazwa jest tylko etykietą właściwości. this ustalane jest przy wywołaniu.'),
  T('Czy można przypisać nową wartość do this (this = obiekt)?', 'Nie - this nie jest zmienną i próba przypisania kończy się błędem składni', ['Tak, tak jak do każdej zmiennej', 'Tak, ale tylko wewnątrz metod obiektu', 'Tak, ale tylko w trybie strict'], 'Wartość this ustawia silnik przy wywołaniu. Można ją zmienić tylko pośrednio: przez sposób wywołania albo call/apply/bind (kolejne lekcje).'),
  T('Który zapis poprawnie odczytuje pole imie z wnętrza metody obiektu?', 'this.imie', ['imie', 'obiekt.imie zawsze działa jednakowo, bez względu na this', 'self.imie'], 'Pola obiektu nie są zmiennymi w zasięgu metody - trzeba je odczytać przez obiekt, na którym metoda została wywołana, czyli przez this.'),
  T('Kiedy silnik JavaScript ustala wartość this dla zwykłej funkcji?', 'W momencie wywołania funkcji, na podstawie sposobu jej wywołania', ['W momencie napisania funkcji, na podstawie miejsca definicji', 'Raz, przy uruchomieniu programu', 'W momencie pierwszego użycia słowa this w kodzie'], 'Dlatego this jest "dynamiczne" - ta sama funkcja może mieć różne this przy różnych wywołaniach.'),
  T('Czy metoda zapisana skrótowo (pokaz() { ... }) inaczej traktuje this niż pokaz: function() { ... }?', 'Nie - przy wywołaniu na obiekcie this działa tak samo w obu zapisach', ['Tak, skrótowa zawsze ma this równe undefined', 'Tak, zapis z function nie ma dostępu do this', 'Tak, skrótowa wymaga użycia słowa new'], 'Skrót różni się drobnymi szczegółami (np. nie można jej użyć z new), ale reguła this jest identyczna.'),
  T('Co się dzieje, gdy jedna funkcja zostanie przypisana jako metoda do wielu obiektów?', 'Istnieje jedna funkcja w pamięci, a przy każdym wywołaniu this wskazuje na inny obiekt', ['Każdy obiekt dostaje własny kod funkcji zapisany na stałe', 'Tylko ostatni obiekt zachowuje metodę', 'Silnik zgłasza błąd o duplikacji funkcji'], 'To wygodny sposób na współdzielenie logiki, ale trzeba pamiętać, że this zależy od obiektu przy wywołaniu.'),
  T('Jak w praktyce sprawdzić, czym jest this w danym miejscu?', 'Dodać console.log(this) wewnątrz funkcji i wywołać ją w interesującym nas sposobie', ['Sprawdzić nazwę pliku, w którym funkcja jest zapisana', 'Odczytać wartość this z dokumentacji języka', 'Użyć instrukcji typeof na całej funkcji'], 'Ponieważ this zależy od wywołania, najszybciej podejrzeć je wprost w konsoli.'),
  T('Co zwróci odczyt this.pole w metodzie, gdy obiekt nie ma takiego pola?', 'undefined - odczyt nieistniejącej właściwości obiektu nie rzuca błędu', ['TypeError', 'null', 'ReferenceError'], 'Błąd pojawiłby się dopiero przy odczycie właściwości z undefined lub null, np. gdy this jest undefined (utrata kontekstu).'),
  T('Jak zachowa się this w metodzie tablicy dopisanej do niej (tablica.suma = function() {...}) po wywołaniu tablica.suma()?', 'this będzie tą tablicą, bo tablica jest obiektem stojącym przed kropką', ['this będzie undefined, bo tablice nie mają metod', 'this będzie obiektem Array (konstruktorem)', 'this będzie pierwszym elementem tablicy'], 'Reguła jest identyczna dla każdego obiektu - także dla tablic, funkcji i obiektów wbudowanych.'),
  T('Dlaczego w przykładzie firma.dzial.opis() this to obiekt dzial, a nie firma?', 'Bo metodę wywołano na obiekcie dzial - to on stoi przed ostatnią kropką', ['Bo obiekty zagnieżdżone zawsze ukrywają obiekt zewnętrzny', 'Bo metoda opis() jest zdefiniowana jako ostatnia w pliku', 'Bo this zawsze wskazuje najgłębiej zagnieżdżony obiekt w programie'], 'Nie liczy się zagnieżdżenie w kodzie, tylko to, na jakim obiekcie została wywołana metoda.'),
  T('Które pytanie należy zadać, żeby przewidzieć this w danym miejscu?', 'Jak i na czym ta funkcja jest wywoływana (czy jest obiekt przed kropką)?', ['Gdzie ta funkcja została zdefiniowana w pliku?', 'Jak długa jest ta funkcja?', 'Jak nazywa się zmienna, do której przypisano funkcję?'], 'Definicja funkcji nie ma znaczenia dla this. Liczy się wyłącznie miejsce i sposób wywołania.'),
);

const drop = [];
const q2 = q.filter(it => !(it[0] === 'c' && drop.some(d => it[1].includes(d))));
apply('_js_07_this_i_konteksty/01_ThisBasics.json', { ex, q: q2 }, 30, 100);
