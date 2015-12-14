package com.company.application.impl;

import com.company.application.*;
import com.company.application.state.impl.*;
import com.company.helpers.*;
import com.company.dictionary.DictionaryManager;
import com.company.dictionary.impl.DictionaryManagerImpl;

public final class DictionaryApplicationContext extends AbstractApplicationContext
        implements ApplicationContext
{
    /**
     *  Dictionary Application context variables.
     */
    public DictionaryManager dictionaryManager;

    DictionaryApplicationContext(Application application) {
        super(application);
        dictionaryManager = new DictionaryManagerImpl();
    }

    @Override
    public void setState(int state) {
        if (state == StateHelper.STATE_START) {
            this.state = new StartState(this);
        } else if (state == StateHelper.STATE_SEARCH) {
            this.state = new SearchState(this);
        } else if (state == StateHelper.STATE_NOT_FOUND) {
            this.state = new NotFoundState(this);
        } else if (state == StateHelper.STATE_CHOSE_SIMILAR) {
            this.state = new ChoseSimilarState(this);
        } else if (state == StateHelper.STATE_END) {
            this.state = new EndState(this);
        }
    }

    @Override
    public void doWork() {
        ApplicationInterface ui = application.getUi();
        ui.showMessage(state.getInvitation());

        if (state.isInputRequired()) {
            input = ui.getInput();
            if (input.length() == CmdHelper.CMD_EXIT.length() && input.toLowerCase().equals(CmdHelper.CMD_EXIT)) {
                setState(StateHelper.STATE_END);
            }
        } else {
            input = null;
        }

        state.doWork();
    }

    @Override
    public void free() {
        super.free();
        if (dictionaryManager != null) {
            dictionaryManager.free();
            dictionaryManager = null;
        }
    }
}
