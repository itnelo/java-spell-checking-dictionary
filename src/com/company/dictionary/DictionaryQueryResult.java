package com.company.dictionary;

import java.util.List;

public interface DictionaryQueryResult {

    enum STATUS {
        SUCCESS, FAIL
    }

    DictionaryQueryResult.STATUS getStatus();
    void setStatus(DictionaryQueryResult.STATUS status);
    List<Word> getSimilarWords();
    void setSimilarWords(List<Word> similarWords);

}
