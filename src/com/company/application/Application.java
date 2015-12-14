package com.company.application;

public interface Application {

    ApplicationInterface getUi();
    void run(String[] args);
    void stop();
    void free();

}
