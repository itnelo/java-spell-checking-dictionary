package com.company.dictionary.impl;

import com.company.dictionary.*;
import java.util.HashMap;

public class DictionaryManagerImpl
        implements DictionaryManager
{
    private HashMap<String, Dictionary> dictionaries;
    private DictionaryTree currentDictionaryTree;
    private DictionaryQuery lastQuery;
    private DictionaryQueryResult lastResult;
    private static final int SIMILARITY_LIMIT = 5;

    public DictionaryManagerImpl() {
        dictionaries = new HashMap<>();
        currentDictionaryTree = new LevenshteinDistanceTree();
    }

    @Override
    public Dictionary loadInMemory() {
        Dictionary dictionary = new DummyDictionary();
        dictionary.open();
        dictionaries.put("default", dictionary);
        currentDictionaryTree.build(dictionary);
        return dictionary;
    }

    @Override
    public DictionaryQueryResult find(DictionaryQuery query) {
        lastQuery = query;
        DictionaryQueryResult result = new DictionaryQueryResultImpl();
        boolean exists = currentDictionaryTree.search(query.getWord());
        result.setStatus(exists
                ? DictionaryQueryResult.STATUS.SUCCESS
                : DictionaryQueryResult.STATUS.FAIL);
        result.setSimilarWords(currentDictionaryTree.getSimilarWords(SIMILARITY_LIMIT));
        return (lastResult = result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(Word word) {
        dictionaries.get("default").add(word);
        currentDictionaryTree.addWord(word);
    }

    @Override
    public DictionaryQuery lastQuery() {
        return lastQuery;
    }

    @Override
    public DictionaryQueryResult lastResult() {
        return lastResult;
    }

    @Override
    public void free() {
        if (! dictionaries.isEmpty()) {
            dictionaries.forEach((s, dictionary) -> dictionary.close());
            dictionaries = new HashMap<>();
        }
        if (currentDictionaryTree != null) {
            currentDictionaryTree.clear();
        }
    }
}
