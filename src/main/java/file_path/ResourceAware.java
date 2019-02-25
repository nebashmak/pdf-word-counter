package file_path;

import file_path.exceptions.NotPdfException;

import java.io.*;

public class ResourceAware {
    private ResourceAware() {
    }

    private static InputStream in = System.in;
    // TODO заменить out на логгирование
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
        String path = null;
        try (BufferedReader pathReader = new BufferedReader(new InputStreamReader(in));
             PrintStream errorOutput = new PrintStream(new BufferedOutputStream(out))) {
            while (true) {
                try {
                    path = getPathFromReader(pathReader);
                    break;
                } catch (FileNotFoundException | NotPdfException ex) {
                    errorOutput.println(ex.getMessage());
                } catch (IOException ex) {
                    errorOutput.println("Ошибка чтения, попробуйте еще раз");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(0);
        }
        return path;
    }

    public static String getPathFromReader(BufferedReader in) throws IOException {
        String path = in.readLine();
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден, попробуйте еще раз");
        }
        if (!file.getName().endsWith(".pdf")) {
            throw new NotPdfException("Пожалуйста укажите файл pdf формата");
        }
        return path;
    }
}
