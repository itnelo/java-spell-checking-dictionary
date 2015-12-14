package com.company.application;

import com.company.application.state.ApplicationState;

import java.util.Map;

public interface ApplicationContext {

    Application getApplication();
    ApplicationState getState();
    Map<String, String> getOptions();
    String getInput();
    void setState(int state);
    void setOptions(Map<String, String> options);
    void doWork();
    void free();

}
