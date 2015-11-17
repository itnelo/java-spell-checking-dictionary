package com.company.dictionary;

import java.util.ArrayList;

public interface DictionaryQueryResult {

    enum STATUS {
        SUCCESS, FAIL
    }

    DictionaryQueryResult.STATUS getStatus();
    void setStatus(DictionaryQueryResult.STATUS status);
    ArrayList<Word> getSimilarWords();
    void setSimilarWords(ArrayList<Word> similarWords);

}
