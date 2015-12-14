package com.company.application.state.impl;

import com.company.application.impl.DictionaryApplicationContext;
import com.company.application.state.ApplicationState;
import com.company.helpers.*;

public class StartState extends AbstractDictionaryApplicationState
        implements ApplicationState
{
    public StartState(DictionaryApplicationContext context) {
        super(context);
    }

    @Override
    public String getInvitation() {
        return MessageHelper.t("ON_START");
    }

    @Override
    public boolean isInputRequired() {
        return false;
    }

    @Override
    public void doWork() {
        context.dictionaryManager.loadInMemory();
        context.setState(StateHelper.STATE_SEARCH);
    }
}
