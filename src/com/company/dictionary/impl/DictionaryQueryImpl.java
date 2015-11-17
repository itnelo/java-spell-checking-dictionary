package com.company.dictionary.impl;

import com.company.dictionary.DictionaryQuery;
import com.company.dictionary.Word;

public class DictionaryQueryImpl
        implements DictionaryQuery
{
    private Word word;

    public DictionaryQueryImpl(Word word) {
        this.word = word;
    }

    public Word getWord() {
        return word;
    }
}
