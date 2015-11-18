package com.company.dictionary;

import java.util.List;

public interface DictionaryTree {

    void build(Dictionary dictionary);
    boolean search(Word word);
    List<Word> getSimilarWords(int limit);
    void addWord(Word word);
    void clear();

}
