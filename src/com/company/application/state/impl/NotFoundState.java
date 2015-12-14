package com.company.application.state.impl;

import com.company.application.*;
import com.company.application.impl.DictionaryApplicationContext;
import com.company.application.state.ApplicationState;
import com.company.dictionary.DictionaryManager;
import com.company.dictionary.Word;
import com.company.helpers.*;
import java.util.*;

public class NotFoundState extends AbstractDictionaryApplicationState
        implements ApplicationState
{
    public NotFoundState(DictionaryApplicationContext context) {
        super(context);
    }

    @Override
    public String getInvitation() {
        return MessageHelper.t("WORD_NOT_EXISTS");
    }

    @Override
    public boolean isInputRequired() {
        return true;
    }

    @Override
    public void doWork() {
        ApplicationInterface ui = context.getApplication().getUi();
        String input = context.getInput();
        DictionaryManager dm = context.dictionaryManager;

        if (! CmdHelper.isValidOption(input)) {
            return;
        }

        if (input.equals(CmdHelper.CMD_0)) {
            Word addWord = dm.lastQuery().getWord();
            dm.add(addWord);
            ui.showMessage(MessageHelper.t("WORD_ADDED") + addWord);
            context.setState(StateHelper.STATE_SEARCH);
            return;
        }

        if (input.equals(CmdHelper.CMD_1)) {
            List<Word> similarWords = dm.lastResult().getSimilarWords();
            if (similarWords.isEmpty()) {
                ui.showMessage(MessageHelper.t("WORD_NO_SIMILAR"));
                context.setState(StateHelper.STATE_SEARCH);
                return;
            }
            {
                Map<String, String> options = new HashMap<>();
                int optionNumber = 0;
                for (Iterator<Word> iter = similarWords.iterator(); iter.hasNext();) {
                    options.put(String.valueOf(optionNumber++), iter.next().toString());
                }
                context.setOptions(options);
            }
            context.setState(StateHelper.STATE_CHOSE_SIMILAR);
        }
    }
}
