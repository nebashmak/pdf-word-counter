package filePath;

import filePath.exceptions.NotPdfException;

import java.io.*;

import static java.util.Objects.nonNull;

public class ResourceAware {
    private ResourceAware() {
    }

    private static InputStream in = System.in;
    private static OutputStream out = System.out;

    public static void setIn(InputStream in) {
        ResourceAware.in = in;
    }

    public static void setOut(OutputStream out) {
        ResourceAware.out = out;
    }

    public static String getPath() {
        return getPath(in, out);
    }

    public static String getPath(InputStream in, OutputStream out) {
        boolean invalidPath = true;
        BufferedReader pathReader = null;
        PrintStream messageWriter = null;
        String path = null;
        while (invalidPath) {
            try {
                pathReader = new BufferedReader(new InputStreamReader(in));
                messageWriter = new PrintStream(new BufferedOutputStream(out));
                path = pathReader.readLine();
                File file = new File(path);
                if (!file.exists()) {
                    throw new FileNotFoundException();
                }
                if (!file.getName().endsWith(".pdf")) {
                    throw new NotPdfException();
                }
                invalidPath = false;
            } catch (FileNotFoundException ex) {
                messageWriter.println("Файл не найден, попробуйте еще раз");
            } catch (NotPdfException ex) {
                messageWriter.println("Пожалуйста укажите pdf файл");
            } catch (Exception ex) {
                messageWriter.println("Ошибка чтения, попробуйте еще раз");
            } finally {
                if (nonNull(pathReader)) {
                    try {
                        pathReader.close();
                    } catch (Exception ex) {
                        System.out.println("Ошибка закрытия входного потока");
                    }
                }
                if (nonNull(messageWriter)) {
                    messageWriter.flush();
                    try {
                        messageWriter.close();
                    } catch (Exception ex) {
                        System.out.println("Ошибка закрытия выходного потока");
                    }
                }
            }
        }
        return path;
    }
}
