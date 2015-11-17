package com.company.helpers;

import java.util.ResourceBundle;

public class MessageHelper
{
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.lang.messages.messages");

    public static String t(String str) {
        return resourceBundle.getString(str);
    }
}
