package com.company.application.state.impl;

import com.company.application.impl.DictionaryApplicationContext;
import com.company.application.state.ApplicationState;
import com.company.helpers.MessageHelper;

public class EndState extends AbstractDictionaryApplicationState
        implements ApplicationState
{
    public EndState(DictionaryApplicationContext context) {
        super(context);
    }

    @Override
    public String getInvitation() {
        return MessageHelper.t("EXITING");
    }

    @Override
    public boolean isInputRequired() {
        return false;
    }

    @Override
    public void doWork() {
        context.getApplication().stop();
    }
}
