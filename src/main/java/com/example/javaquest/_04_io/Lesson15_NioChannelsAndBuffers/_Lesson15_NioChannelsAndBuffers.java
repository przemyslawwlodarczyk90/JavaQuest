package com.example.javaquest._04_io.Lesson15_NioChannelsAndBuffers;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class _Lesson15_NioChannelsAndBuffers {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 NIO – KANAŁY (Channel) I BUFORY (Buffer)
         * ============================================================
         * "New I/O" (java.nio, Java 1.4) wprowadziło CAŁKOWICIE INNY model
         * pracy z danymi niż klasyczne strumienie (InputStream/OutputStream):
         *
         * Strumienie (java.io):           Kanały + bufory (java.nio):
         * - dane płyną bajt po bajcie      - dane przesyłane są w BLOKACH
         *   (lub przez bufor wewnętrzny)     przez wspólny bufor (ByteBuffer)
         * - jednokierunkowe (In albo Out)  - FileChannel jest DWUKIERUNKOWY
         * - brak swobodnego przewijania    - można czytać/pisać w dowolnym
         *                                    miejscu pliku (jak RandomAccessFile)
         *
         * FileChannel uzyskuje się z:
         * - FileInputStream/FileOutputStream/RandomAccessFile.getChannel()
         * - FileChannel.open(Path, options...)
         */

        Path tempFile = Files.createTempFile("lesson15", ".dat");
        Path copyFile = Files.createTempFile("lesson15_copy", ".dat");

        try {
            /*
             * ============================================================
             * 🔹 BYTEBUFFER – allocate, put/get, flip, clear, rewind
             * ============================================================
             * ByteBuffer to bufor bajtów z trzema kluczowymi wskaźnikami:
             * - position -> gdzie jestesmy (do zapisu lub odczytu)
             * - limit    -> do jakiego miejsca mozna czytac/pisac
             * - capacity -> calkowity, staly rozmiar bufora
             *
             * allocate(n) -> tworzy bufor o pojemnosci n bajtow
             * put(...)    -> zapisuje dane do bufora (position rosnie)
             * flip()      -> PRZELACZA bufor z trybu zapisu na odczyt
             *                (limit = position, position = 0)
             * get(...)    -> czyta dane z bufora (position rosnie)
             * rewind()    -> position = 0, limit bez zmian (czytaj od nowa)
             * clear()     -> position = 0, limit = capacity (przygotuj do
             *                ponownego zapisu; STARE dane nadal fizycznie
             *                w buforze, ale logicznie "zapomniane")
             */

            System.out.println("=== ByteBuffer – podstawy ===");
            ByteBuffer buffer = ByteBuffer.allocate(64);
            System.out.println("Nowy bufor -> position=" + buffer.position()
                    + ", limit=" + buffer.limit() + ", capacity=" + buffer.capacity());

            buffer.put("Hello NIO".getBytes(StandardCharsets.UTF_8));
            System.out.println("Po put() -> position=" + buffer.position()
                    + ", limit=" + buffer.limit());

            buffer.flip(); // przelacz na tryb odczytu
            System.out.println("Po flip() -> position=" + buffer.position()
                    + ", limit=" + buffer.limit());

            byte[] readBytes = new byte[buffer.remaining()];
            buffer.get(readBytes);
            System.out.println("Odczytano: " + new String(readBytes, StandardCharsets.UTF_8));

            buffer.rewind(); // wroc na poczatek, limit bez zmian - mozna czytac od nowa
            byte[] readAgain = new byte[buffer.limit()];
            buffer.get(readAgain);
            System.out.println("Po rewind(), odczyt ponownie: " + new String(readAgain, StandardCharsets.UTF_8));

            buffer.clear(); // przygotuj bufor do ponownego zapisu
            System.out.println("Po clear() -> position=" + buffer.position()
                    + ", limit=" + buffer.limit());

            System.out.println();

            /*
             * ============================================================
             * 🔍 ZAPIS I ODCZYT PLIKU PRZEZ FileChannel
             * ============================================================
             */

            System.out.println("=== Zapis przez FileChannel ===");
            String text = "Kanaly i bufory NIO - przyklad zapisu.";
            try (FileChannel writeChannel = FileChannel.open(tempFile,
                    StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

                ByteBuffer writeBuffer = ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8));
                int written = writeChannel.write(writeBuffer);
                System.out.println("Zapisano bajtow: " + written);
            }

            System.out.println("=== Odczyt przez FileChannel ===");
            try (FileChannel readChannel = FileChannel.open(tempFile, StandardOpenOption.READ)) {
                ByteBuffer readBuffer = ByteBuffer.allocate((int) readChannel.size());
                readChannel.read(readBuffer);
                readBuffer.flip();

                byte[] data = new byte[readBuffer.remaining()];
                readBuffer.get(data);
                System.out.println("Odczytana zawartosc: " + new String(data, StandardCharsets.UTF_8));
            }

            System.out.println();

            /*
             * ============================================================
             * 🔹 KOPIOWANIE KANAŁ-DO-KANAŁU: transferTo / transferFrom
             * ============================================================
             * FileChannel pozwala przesylac dane BEZPOSREDNIO miedzy dwoma
             * kanalami (czesto z pomoca mechanizmow "zero-copy" systemu
             * operacyjnego) - bez kopiowania danych przez pamiec Javy w
             * postaci tablicy bajtow. To znacznie szybsze przy duzych plikach
             * niz reczne czytanie/pisanie przez strumienie.
             */

            System.out.println("=== transferTo / transferFrom (kopiowanie kanal-do-kanal) ===");
            try (FileChannel source = FileChannel.open(tempFile, StandardOpenOption.READ);
                 FileChannel destination = FileChannel.open(copyFile,
                         StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

                long transferred = source.transferTo(0, source.size(), destination);
                System.out.println("Przeslano bajtow (transferTo): " + transferred);
            }

            System.out.println("Kopia: " + Files.readString(copyFile));

            System.out.println();

            /*
             * ============================================================
             * 🔍 MEMORY-MAPPED FILES – FileChannel.map()
             * ============================================================
             * map() mapuje CZĘŚĆ (lub cały) plik bezpośrednio w pamięci
             * wirtualnej procesu jako MappedByteBuffer. System operacyjny
             * sam zarządza wczytywaniem stron pliku do RAM w miarę potrzeb.
             *
             * Zalety:
             * - bardzo szybki dostęp swobodny (random access) do dużych plików
             * - brak jawnego kopiowania danych z jądra systemu do bufora Javy
             * - świetne np. do baz danych, dużych plików binarnych, indeksów
             *
             * Wady/uwagi:
             * - zajmuje przestrzeń adresową (problem przy bardzo dużych plikach
             *   na 32-bit JVM, mniej istotne na 64-bit)
             * - zwolnienie mapowania jest zależne od GC (brak jawnego unmap())
             */

            System.out.println("=== Memory-mapped file (FileChannel.map) ===");
            MappedByteBuffer mapped;
            try (RandomAccessFile raf = new RandomAccessFile(tempFile.toFile(), "r");
                 FileChannel channel = raf.getChannel()) {

                mapped = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
                byte[] mappedData = new byte[mapped.remaining()];
                mapped.get(mappedData);
                System.out.println("Odczytano przez mapowanie: " + new String(mappedData, StandardCharsets.UTF_8));
            }
            // ⚠️ Na Windowsie mapowany plik pozostaje "zablokowany" dopóki
            // MappedByteBuffer nie zostanie zebrany przez Garbage Collector
            // (Java nie ma jawnego unmap()) - dlatego przed usunieciem pliku
            // pozbywamy sie referencji i prosimy o GC.
            mapped = null;
            System.gc();

            /*
             * ============================================================
             * ⚠️ KIEDY KANAŁY/BUFORY DAJĄ PRZEWAGĘ NAD STRUMIENIAMI?
             * ============================================================
             * - duże pliki (setki MB / GB) - mniej narzutu na kopiowanie danych
             * - kopiowanie plik-do-pliku / socket-do-pliku -> transferTo/From
             *   moze wykorzystac zero-copy na poziomie systemu operacyjnego
             * - potrzebny swobodny (random) dostep do pliku -> FileChannel
             *   pozwala czytac/pisac w dowolnej pozycji (position(long))
             * - operacje wsadowe na blokach danych zamiast bajt-po-bajcie
             *
             * Dla prostych, malych plikow tekstowych zwykle wystarczaja
             * (i sa czytelniejsze) klasyczne strumienie/BufferedReader-Writer.
             */

        } finally {
            // best-effort sprzatanie - na Windowsie mapowany plik moze przez
            // chwile pozostac zajety, dopoki GC nie zwolni MappedByteBuffer,
            // wiec awaria samego sprzatania nie powinna wysadzac lekcji
            deleteQuietly(tempFile);
            deleteQuietly(copyFile);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ByteBuffer: allocate(n), put/get, flip() (zapis->odczyt),
         *   rewind() (czytaj od nowa), clear() (przygotuj do zapisu)
         * - FileChannel: uzyskiwany z FileChannel.open(Path,...) lub
         *   getChannel() na starych strumieniach/RandomAccessFile
         * - transferTo/transferFrom: szybkie kopiowanie kanal-do-kanal
         * - FileChannel.map(): memory-mapped files - mapowanie pliku
         *   bezposrednio w pamieci (MappedByteBuffer)
         * - Kanaly+bufory > strumienie: duze pliki, kopiowanie, random access
         * - Strumienie > kanaly+bufory: prostota, male pliki tekstowe
         */
    }

    /**
     * Usuwa plik ignorujac ewentualny blad (np. plik chwilowo zajety
     * przez system po zwolnieniu memory-mapped buffera na Windowsie).
     */
    private static void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            path.toFile().deleteOnExit();
        }
    }
}
