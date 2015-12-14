package com.company.application.state;

import com.company.application.ApplicationContext;

public interface ApplicationState {

    String getInvitation();
    ApplicationContext getContext();
    boolean isInputRequired();
    void doWork();

}
