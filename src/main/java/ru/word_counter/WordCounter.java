package ru.word_counter;

import ru.word_counter.file_source.ResourceAware;

public class WordCounter
{
    public static void main(String[] args)
    {
        PdfReader fileReader = PdfReader.getPdfReader();
        fileReader.readPdfFile(ResourceAware.getPath());
    }
}
