package com.company.helpers;

public class CmdHelper
{
    public static final String CMD_EXIT = "exit";
    public static final String CMD_0    = "0";
    public static final String CMD_1    = "1";
    public static final String CMD_2    = "2";
    public static final String CMD_3    = "3";
    public static final String CMD_4    = "4";

    public static boolean isValidOption(String option) {
        switch (option) {
            case CMD_EXIT:
            case CMD_0:
            case CMD_1:
            case CMD_2:
            case CMD_3:
            case CMD_4:
                return true;
            default: return false;
        }
    }
}
