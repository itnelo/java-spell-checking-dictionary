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
                new WordImpl("book"),
                new WordImpl("books"),
                new WordImpl("cake"),
                new WordImpl("boo"),
                new WordImpl("cape"),
                new WordImpl("boon"),
                new WordImpl("cook"),
                new WordImpl("cart")
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
