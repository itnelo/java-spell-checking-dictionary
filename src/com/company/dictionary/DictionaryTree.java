package com.company.dictionary;

import java.util.ArrayList;

public interface DictionaryTree {

    void build(Dictionary dictionary);
    boolean search(Word word);
    ArrayList<Word> getSimilarWords(int limit, String queryWord);
    void addWord(Word word);
    void clear();

}
