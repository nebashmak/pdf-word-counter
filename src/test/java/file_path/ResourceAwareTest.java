package file_path;

import file_path.exceptions.NotPdfException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourceAwareTest {

    private InputStream inputByteArray;
    private ByteArrayOutputStream outputByteArray;
    private Runnable pathReader;
    private Thread thread;
    private String result;

    private static final String VALID_PATH = "src/test/resources/testPdf.pdf";
    private static final String INVALID_PATH = "src/test/resources/invalidName.pdf";
    private static final String NOT_PDF = "src/test/resources/notPdf.txt";

    public void initPathReader(byte[] array) {
        inputByteArray = new ByteArrayInputStream(array);
        outputByteArray = new ByteArrayOutputStream(200);
        pathReader = () -> result = ResourceAware.getPath(inputByteArray, outputByteArray);
        thread = new Thread(pathReader);
        thread.start();
    }

//    @AfterEach
    public void stopPathReader() {
        thread.interrupt();
        thread = null;
    }

    @Test
    @DisplayName("Валидный путь к файлу не вызывает ошибок")
    public void validPathDoesNotThrowsExceptionsTest() throws Exception {
        assertEquals(VALID_PATH, ResourceAware.getPathFromReader(
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream(VALID_PATH.getBytes())))));
    }

    @Test
    @DisplayName("Невалидный путь к файлу выбрасывает FileNotFoundException")
    public void invalidPathThrowsExceptionTest() {
        FileNotFoundException ex = assertThrows(FileNotFoundException.class, () -> ResourceAware.getPathFromReader(
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream(INVALID_PATH.getBytes())))));
        assertEquals("Файл не найден, попробуйте еще раз", ex.getMessage());
    }

    @Test
    @DisplayName("Путь к файлу отличному от pdf выбрасывает NotPdfException")
    public void notPdfFileThrowsExceptionTest() {
        NotPdfException ex = assertThrows(NotPdfException.class, () -> ResourceAware.getPathFromReader(
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream(NOT_PDF.getBytes())))));
        assertEquals("Пожалуйста укажите файл pdf формата", ex.getMessage());
    }
}
