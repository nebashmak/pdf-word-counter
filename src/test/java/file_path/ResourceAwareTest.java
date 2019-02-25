package file_path;

import file_path.exceptions.NotPdfException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceAwareTest {

    private InputStream inputByteArray;
    private ByteArrayOutputStream outputByteArray;
    private Runnable pathReader;
    private Thread thread;
    private String result;

    private static final String VALID_PATH = "src/test/resources/testPdf.pdf";
    private static final String INVALID_PATH = "src/test/resources/invalidName.pdf";
    private static final String NOT_PDF = "src/test/resources/notPdf.txt";

    private void initPathReader(byte[] array) {
        inputByteArray = new ByteArrayInputStream(array);
        outputByteArray = new ByteArrayOutputStream(200);
        pathReader = () -> result = ResourceAware.getPath(inputByteArray, outputByteArray);
        thread = new Thread(pathReader);
        thread.start();
    }

    @AfterEach
    public void stopPathReader() {
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

    @Test
    @DisplayName("Получение валидного пути без исключений не вызывает зацикливания")
    public void validReturningPathWithoutExceptionDontTriggersLoopTest() throws Exception {
        initPathReader(VALID_PATH.getBytes());
        Thread.sleep(200);
        assertEquals(VALID_PATH, result);
    }

    @Test
    @DisplayName("Получение невалидного пути к вызывает повторение цикла")
    public void pathToInvalidFileTriggersLootTest() throws Exception {
        initPathReader(INVALID_PATH.getBytes());
        Thread.sleep(200);
        assertNull(result);
        assertTrue(outputByteArray.toString().contains("Файл не найден, попробуйте еще раз"));
    }

    @Test
    @DisplayName("Получение пути к не pdf файлу вызывает повторение цикла")
    public void pathToNotPdfFileTriggersLootTest() throws Exception {
        initPathReader(NOT_PDF.getBytes());
        Thread.sleep(200);
        assertNull(result);
        assertTrue(outputByteArray.toString().contains("Пожалуйста укажите файл pdf формата"));
    }
}
