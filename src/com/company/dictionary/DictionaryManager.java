package com.company.dictionary;

public interface DictionaryManager {

    Dictionary loadInMemory();
    DictionaryQueryResult find(DictionaryQuery query);
    void add(Word word);
    DictionaryQuery lastQuery();
    DictionaryQueryResult lastResult();
    void free();

}
