package com.company.application;

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
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showError(String errorMessage) {
        System.err.println(errorMessage);
    }

    @Override
    public String getInput() throws IOException {
        return inputReader.readLine();
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
