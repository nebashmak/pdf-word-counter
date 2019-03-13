package ru.word_counter;

import ru.word_counter.file_source.ResourceAware;

public class WordCounterApp
{
    public static void main(String[] args)
    {
        PdfReader fileReader = PdfReader.getPdfReader();
        fileReader.countWordsInPdf(ResourceAware.getPath());
        fileReader.printWordCount(System.out);
    }
}
