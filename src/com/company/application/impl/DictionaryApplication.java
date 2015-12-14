package com.company.application.impl;

import com.company.application.*;
import com.company.helpers.*;

public enum DictionaryApplication
        implements Application
{
    INSTANCE;

    private static final ApplicationInterface ui = ConsoleApplicationInterface.INSTANCE;
    private ApplicationContext context;
    public volatile boolean running;

    DictionaryApplication() {
        context = new DictionaryApplicationContext(this);
        context.setState(StateHelper.STATE_START);
        running = false;
    }

    @Override
    public ApplicationInterface getUi() {
        return ui;
    }

    public void run(String[] args) {
        try {
            running = true;
            while (running) {
                doWork();
            }
        } catch (Exception e) {
            ui.showError(e.getMessage());
        } finally {
            free();
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    private void doWork() {
        context.doWork();
    }

    public void free() {
        try {
            ui.free();
            context.free();
        } catch (Exception e) {
            System.err.println(MessageHelper.t("FREE_ERROR_APP") + e);
        }
    }

}
