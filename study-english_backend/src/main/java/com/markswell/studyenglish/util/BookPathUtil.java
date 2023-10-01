package com.markswell.studyenglish.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.nio.file.Files.list;

public class BookPathUtil {

    public static Stream<Path> getBooks(String path) throws IOException {
        return list(Path.of(path))
                .filter(b -> b.toString().contains("_book_"))
                .sorted();
    }

}
