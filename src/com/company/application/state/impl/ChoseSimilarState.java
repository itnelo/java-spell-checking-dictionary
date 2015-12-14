package com.company.application.state.impl;

import com.company.application.impl.DictionaryApplicationContext;
import com.company.application.state.ApplicationState;
import com.company.helpers.*;
import java.util.*;

public class ChoseSimilarState extends AbstractDictionaryApplicationState
        implements ApplicationState
{
    private static final String OPTION_FORMAT = "\n [%s] %s";

    public ChoseSimilarState(DictionaryApplicationContext context) {
        super(context);
    }

    @Override
    public String getInvitation() {
        Map<String, String> options = context.getOptions();
        StringBuilder invitationBuilder = new StringBuilder(MessageHelper.t("WORD_CHOSE_SIMILAR"));
        for (Iterator<Map.Entry<String, String>> iter = options.entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String, String> option = iter.next();
            invitationBuilder.append(new Formatter().format(OPTION_FORMAT, option.getKey(), option.getValue()).toString());
        }
        return invitationBuilder.toString();
    }

    @Override
    public boolean isInputRequired() {
        return true;
    }

    @Override
    public void doWork() {
        String input = context.getInput();

        if (! CmdHelper.isValidOption(input)) {
            return;
        }

        String wordString = context.getOptions().get(input);
        if (wordString == null || wordString.isEmpty()) {
            return;
        }

        context.getApplication().getUi().showMessage(MessageHelper.t("WORD_CHOSEN") + wordString);
        context.setState(StateHelper.STATE_SEARCH);
    }
}
