package filePath;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceAwareTest {

    private InputStream inputByteArray;
    private ByteArrayOutputStream outputByteArray;
    private Runnable pathReader;
    private Thread thread;
    private String result;

    private static final String VALID_PATH = "src/test/resources/testPdf.pdf";

    public void initPathReader(byte[] array) {
        inputByteArray = new ByteArrayInputStream(array);
        outputByteArray = new ByteArrayOutputStream(200);
        pathReader = () -> result = ResourceAware.getPath(inputByteArray, outputByteArray);
        thread = new Thread(pathReader);
        thread.start();
    }

    @AfterEach
    public void stopPathReader() {
        thread.interrupt();
        thread = null;
    }

    @Test
    public void validPathIsReturnedTest() throws Exception {
        initPathReader(VALID_PATH.getBytes());
        thread.join();
        assertEquals(VALID_PATH, result);
    }
}
