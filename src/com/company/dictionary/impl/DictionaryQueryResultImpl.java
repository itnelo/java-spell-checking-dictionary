package com.company.dictionary.impl;

import com.company.dictionary.DictionaryQueryResult;
import com.company.dictionary.Word;
import java.util.ArrayList;

public class DictionaryQueryResultImpl
        implements DictionaryQueryResult
{
    private DictionaryQueryResult.STATUS status;
    private ArrayList<Word> similarWords;

    @Override
    public DictionaryQueryResult.STATUS getStatus() {
        return status;
    }

    @Override
    public void setStatus(STATUS status) {
        this.status = status;
    }

    @Override
    public ArrayList<Word> getSimilarWords() {
        return similarWords;
    }

    @Override
    public void setSimilarWords(ArrayList<Word> similarWords) {
        this.similarWords = similarWords;
    }
}
