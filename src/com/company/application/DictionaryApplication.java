package com.company.application;

import com.company.dictionary.*;
import com.company.dictionary.impl.*;
import com.company.helpers.*;
import java.io.IOException;
import java.util.*;

public enum DictionaryApplication
        implements Application
{
    INSTANCE;

    private static final ApplicationInterface ui = ConsoleApplicationInterface.INSTANCE;
    private DictionaryManager dictionaryManager;
    private boolean running;
    private int state;
    private HashMap<Integer, String> invitationMessages;
    private HashMap<String, String> optionsValues;

    DictionaryApplication() {
        this(new DictionaryManagerImpl());
    }

    DictionaryApplication(DictionaryManager dictionaryManager) {
        this.dictionaryManager = dictionaryManager;
        this.invitationMessages = new HashMap<>();
        this.invitationMessages.put(StateHelper.STATE_SEARCH, MessageHelper.t("ENTER_WORD"));
        this.invitationMessages.put(StateHelper.STATE_NOT_FOUND, MessageHelper.t("WORD_NOT_EXISTS"));
        this.optionsValues = new HashMap<>();
    }

    private void prepare() {
        ui.showMessage(MessageHelper.t("ON_START"));
        dictionaryManager.loadInMemory();
        state = StateHelper.STATE_SEARCH;
        running = true;
    }

    public void run(String[] args) {
        try {
            prepare();
            while (running) {
                doWork();
            }
        } catch (IOException e) {
            ui.showError(MessageHelper.t("IO_EXCEPTION"));
        } finally {
            free();
        }
    }

    private void doWork() throws IOException {
        ui.showMessage(invitationMessages.get(state));
        String input = ui.getInput();
        if (input.length() == CmdHelper.CMD_EXIT.length() && input.toLowerCase().equals(CmdHelper.CMD_EXIT)) {
            ui.showMessage(MessageHelper.t("EXITING"));
            running = false;
            return;
        }

        switch (state) {
            case StateHelper.STATE_SEARCH:
                doSearch(input);
                break;

            case StateHelper.STATE_NOT_FOUND:
                doNotFound(input);
                break;

            case StateHelper.STATE_CHOSE_SIMILAR:
                doChoseSimilar(input);
                break;
        }
    }

    private void doSearch(String input) {
        Word word = new WordImpl(input);
        DictionaryQuery query = new DictionaryQueryImpl(word);
        long startTime = System.nanoTime();
        DictionaryQueryResult result = dictionaryManager.find(query);
        double searchDuration = (System.nanoTime() - startTime) / 1000000.0;
        ui.showMessage(MessageHelper.t("TIME_ELAPSED") + searchDuration);
        if (result.getStatus().equals(DictionaryQueryResult.STATUS.SUCCESS)) {
            ui.showMessage(MessageHelper.t("WORD_EXISTS") + dictionaryManager.lastResult().getSimilarWords().get(0));
        } else {
            state = StateHelper.STATE_NOT_FOUND;
        }
    }

    private void doNotFound(String input) {
        if (! CmdHelper.isValidOption(input)) {
            return;
        }
        if (input.equals(CmdHelper.CMD_0)) {
            Word addWord = dictionaryManager.lastQuery().getWord();
            dictionaryManager.add(addWord);
            ui.showMessage(MessageHelper.t("WORD_ADDED") + addWord);
            state = StateHelper.STATE_SEARCH;
        }
        if (input.equals(CmdHelper.CMD_1)) {
            ArrayList<Word> similarWords = dictionaryManager.lastResult().getSimilarWords();
            if (similarWords.isEmpty()) {
                ui.showMessage(MessageHelper.t("WORD_NO_SIMILAR"));
                state = StateHelper.STATE_SEARCH;
                return;
            }
            optionsValues.clear();
            StringBuilder similarInvitationMessagesBuilder = new StringBuilder(MessageHelper.t("WORD_CHOSE_SIMILAR"));
            String format = "\n [%d] %s";
            int optionNumber = 0;
            for (Iterator<Word> iter = similarWords.iterator(); iter.hasNext();) {
                Word similarWord = iter.next();
                similarInvitationMessagesBuilder.append(new Formatter().format(format, optionNumber, similarWord.toString()).toString());
                optionsValues.put(String.valueOf(optionNumber), similarWord.toString());
                ++optionNumber;
            }
            invitationMessages.put(StateHelper.STATE_CHOSE_SIMILAR, similarInvitationMessagesBuilder.toString());
            state = StateHelper.STATE_CHOSE_SIMILAR;
        }
    }

    private void doChoseSimilar(String input) {
        if (! CmdHelper.isValidOption(input)) {
            return;
        }
        String wordString = optionsValues.get(input);
        if (wordString == null || wordString.isEmpty()) {
            return;
        }
        ui.showMessage(MessageHelper.t("WORD_CHOSEN") + wordString);
        state = StateHelper.STATE_SEARCH;
    }

    public void free() {
        try {
            ui.free();
            if (dictionaryManager != null) {
                dictionaryManager.free();
                dictionaryManager = null;
            }
        } catch (Exception e) {
            System.err.println(MessageHelper.t("FREE_ERROR_APP") + e);
        }
    }

}
