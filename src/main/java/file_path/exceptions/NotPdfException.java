package file_path.exceptions;

import java.io.IOException;

public class NotPdfException extends IOException {
    public NotPdfException(String message) {
        super(message);
    }
}
