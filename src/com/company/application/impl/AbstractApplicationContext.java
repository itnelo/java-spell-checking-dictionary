package com.company.application.impl;

import com.company.application.*;
import com.company.application.state.ApplicationState;

import java.util.*;

public abstract class AbstractApplicationContext
        implements ApplicationContext
{
    /**
     *  Application context variables.
     */
    protected Application application;
    protected ApplicationState state;
    protected Map<String, String> options;
    protected String input;

    AbstractApplicationContext(Application application) {
        this.application = application;
        this.options = new HashMap<>();
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public ApplicationState getState() {
        return state;
    }

    @Override
    public Map<String, String> getOptions() {
        return options;
    }

    @Override
    public String getInput() {
        return input;
    }

    @Override
    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    @Override
    public void free() {
        ;
    }

    public abstract void setState(int state);
    public abstract void doWork();
}
