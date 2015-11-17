package com.company.dictionary.impl;

import com.company.dictionary.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class DummyDictionary
        implements Dictionary
{
    private HashSet<Word> words;
    private boolean isOpened;

    public DummyDictionary() {
        words = new HashSet<>(Arrays.asList(
                new WordImpl("Кот"),
                new WordImpl("Кошка"),
                new WordImpl("Кошмар"),
                new WordImpl("Кошалот"),
                new WordImpl("Королев"),
                new WordImpl("Корова"),
                new WordImpl("Корт"),
                new WordImpl("Коран"),
                new WordImpl("Книжка"),
                new WordImpl("Кимано")
        ));
    }

    @Override
    public void open() {
        isOpened = true;
    }

    @Override
    public Boolean find(Word word) {
        return words.contains(word);
    }

    public Iterator<Word> iterator() {
        return words.iterator();
    }

    @Override
    public Integer size() {
        return words.size();
    }

    @Override
    public void add(Word word) {

    }

    @Override
    public void close() {
        if (isOpened) {

        }
    }
}
