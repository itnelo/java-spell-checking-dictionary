package com.company.dictionary.impl;

import com.company.dictionary.DictionaryQueryResult;
import com.company.dictionary.Word;
import java.util.*;

public class DictionaryQueryResultImpl
        implements DictionaryQueryResult
{
    private DictionaryQueryResult.STATUS status;
    private List<Word> similarWords;

    @Override
    public DictionaryQueryResult.STATUS getStatus() {
        return status;
    }

    @Override
    public void setStatus(STATUS status) {
        this.status = status;
    }

    @Override
    public List<Word> getSimilarWords() {
        return similarWords;
    }

    @Override
    public Word getMatchWord() {
        return (similarWords.size() > 0)
                ? similarWords.get(similarWords.size() - 1)
                : null;
    }

    @Override
    public void setSimilarWords(List<Word> similarWords) {
        this.similarWords = similarWords;
    }
}
