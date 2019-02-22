package jar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

public class App 
{
    public static void main( String[] args )
    {
        File file = new File(ResourceAware.getPath());
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println(text);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
