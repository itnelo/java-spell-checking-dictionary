package com.company.application;

import java.io.IOException;

public interface ApplicationInterface {

    void showMessage(String message);
    void showError(String errorMessage);
    String getInput() throws IOException;
    void free();

}
