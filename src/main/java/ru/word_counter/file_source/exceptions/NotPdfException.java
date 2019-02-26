package ru.word_counter.file_source.exceptions;

import java.io.IOException;

public class NotPdfException extends IOException {
    public NotPdfException(String message) {
        super(message);
    }
}
