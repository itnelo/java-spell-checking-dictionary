package com.company.application.state.impl;

import com.company.application.*;
import com.company.application.impl.DictionaryApplicationContext;
import com.company.application.state.ApplicationState;
import com.company.dictionary.*;
import com.company.dictionary.impl.*;
import com.company.helpers.*;

public class SearchState extends AbstractDictionaryApplicationState
        implements ApplicationState
{
    public SearchState(DictionaryApplicationContext context) {
        super(context);
    }

    @Override
    public String getInvitation() {
        return MessageHelper.t("ENTER_WORD");
    }

    @Override
    public boolean isInputRequired() {
        return true;
    }

    @Override
    public void doWork() {
        ApplicationInterface ui = context.getApplication().getUi();
        DictionaryManager dm = context.dictionaryManager;
        Word word = new WordImpl(context.getInput());
        DictionaryQuery query = new DictionaryQueryImpl(word);
        long startTime = System.nanoTime();
        DictionaryQueryResult result = dm.find(query);
        double searchDuration = (System.nanoTime() - startTime) / 1000000.0;
        ui.showMessage(MessageHelper.t("TIME_ELAPSED") + searchDuration);
        if (result.getStatus().equals(DictionaryQueryResult.STATUS.SUCCESS)) {
            ui.showMessage(MessageHelper.t("WORD_EXISTS") + dm.lastResult().getMatchWord());
        } else {
            context.setState(StateHelper.STATE_NOT_FOUND);
        }
    }
}
