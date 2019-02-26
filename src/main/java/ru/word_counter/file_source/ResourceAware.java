package ru.word_counter.file_source;

import ru.word_counter.file_source.exceptions.NotPdfException;

import java.io.*;
import java.util.Optional;

import static java.util.Objects.isNull;

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
        PrintStream errorOutput = new PrintStream(new BufferedOutputStream(out));
        try (BufferedReader pathReader = new BufferedReader(new InputStreamReader(in))) {
            while (isNull(path)) {
                try {
                    path = getPathFromReader(pathReader);
                } catch (FileNotFoundException | NotPdfException ex) {
                    errorOutput.println(ex.getMessage());
                } catch (IOException ex) {
                    errorOutput.println("Ошибка чтения, попробуйте еще раз");
                }
                errorOutput.flush();
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(0);
        }
        return path;
    }

    public static String getPathFromReader(BufferedReader in) throws InterruptedException, IOException {
        String path = null;
        System.out.println("Введите путь к файлу");
        while (!in.ready()) {
            Thread.sleep(100);
        }
        path = Optional.ofNullable(in.readLine()).orElseThrow(IOException::new);
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
