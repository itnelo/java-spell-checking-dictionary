package com.company;

import com.company.application.DictionaryApplication;

public class Main {
    public static void main(String[] args) {
        try {
            DictionaryApplication.INSTANCE.run(args);
        } catch (Exception e) {
            System.err.println("Unhandled exception!");
            e.printStackTrace();
        }
    }
}
