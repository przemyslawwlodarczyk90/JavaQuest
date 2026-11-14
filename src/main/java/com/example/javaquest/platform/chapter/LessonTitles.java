package com.example.javaquest.platform.chapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Polskie tytuly lekcji z pliku {@code platform/lesson-titles-pl.txt} (sekcje {@code [slugRozdzialu]},
 * potem wiersze {@code slugLekcji=Tytul}). Lekcja bez wpisu dostaje tytul wygenerowany ze sluga
 * przez {@link LessonSlugTitles#humanize(String)}.
 */
final class LessonTitles {

    static final String RESOURCE = "platform/lesson-titles-pl.txt";

    private final Map<String, String> titles;

    private LessonTitles(Map<String, String> titles) {
        this.titles = titles;
    }

    static LessonTitles load() {
        InputStream in = LessonTitles.class.getClassLoader().getResourceAsStream(RESOURCE);
        if (in == null) return new LessonTitles(Map.of());
        try (var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return new LessonTitles(parse(reader));
        } catch (IOException e) {
            throw new UncheckedIOException("Nie udalo sie wczytac " + RESOURCE, e);
        }
    }

    static Map<String, String> parse(BufferedReader reader) throws IOException {
        var result = new HashMap<String, String>();
        String chapter = null;
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.strip();
            if (line.isEmpty() || line.startsWith("#")) continue;
            if (line.startsWith("[") && line.endsWith("]")) {
                chapter = line.substring(1, line.length() - 1);
                continue;
            }
            int eq = line.indexOf('=');
            if (chapter == null || eq <= 0) continue;
            String title = line.substring(eq + 1).strip();
            if (!title.isEmpty()) result.put(key(chapter, line.substring(0, eq).strip()), title);
        }
        return result;
    }

    boolean hasTitle(String chapterSlug, String lessonSlug) {
        return titles.containsKey(key(chapterSlug, lessonSlug));
    }

    String titleOf(String chapterSlug, String lessonSlug) {
        String title = titles.get(key(chapterSlug, lessonSlug));
        return title != null ? title : LessonSlugTitles.humanize(lessonSlug);
    }

    private static String key(String chapterSlug, String lessonSlug) {
        return chapterSlug + "/" + lessonSlug;
    }
}
