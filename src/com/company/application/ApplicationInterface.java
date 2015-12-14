package com.company.application;

public interface ApplicationInterface {

    ApplicationInterface showMessage(String message);
    ApplicationInterface showError(String errorMessage);
    String getInput();
    void free();

}
