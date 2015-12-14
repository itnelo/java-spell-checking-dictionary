package com.company.application.state.impl;

import com.company.application.ApplicationContext;
import com.company.application.impl.DictionaryApplicationContext;
import com.company.application.state.ApplicationState;

public abstract class AbstractDictionaryApplicationState implements ApplicationState
{
    protected DictionaryApplicationContext context;

    AbstractDictionaryApplicationState(DictionaryApplicationContext context) {
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public abstract String getInvitation();
    public abstract boolean isInputRequired();
    public abstract void doWork();
}
