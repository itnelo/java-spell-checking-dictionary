package com.company.application.impl;

import com.company.application.ApplicationInterface;
import com.company.helpers.CmdHelper;
import com.company.helpers.MessageHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public enum ConsoleApplicationInterface
        implements ApplicationInterface
{
    INSTANCE;

    private BufferedReader inputReader;

    ConsoleApplicationInterface() {
        this.inputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public ApplicationInterface showMessage(String message) {
        System.out.println(message);
        return this;
    }

    @Override
    public ApplicationInterface showError(String errorMessage) {
        System.err.println(errorMessage);
        return this;
    }

    @Override
    public String getInput() {
        try {
            return inputReader.readLine();
        } catch (Exception e) {
            showError(e.getMessage());
            return CmdHelper.CMD_EXIT;
        }
    }

    public void free() {
        try {
            if (inputReader != null) {
                inputReader.close();
                inputReader = null;
            }
        } catch (Exception e) {
            showError(MessageHelper.t("FREE_ERROR_UI") + e);
        }
    }
}
