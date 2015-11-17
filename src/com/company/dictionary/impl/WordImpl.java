package com.company.dictionary.impl;

import com.company.dictionary.Word;

public class WordImpl implements Word {
    private String text;

    public WordImpl(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
