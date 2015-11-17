package com.company.dictionary;

import java.util.Iterator;

public interface Dictionary {

    void open();
    Iterator<Word> iterator();
    Integer size();
    Boolean find(Word word);
    void add(Word word);
    void close();

}
