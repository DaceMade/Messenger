package com.company;

interface ServerCommands {
    int SEND_MESSAGE = 0;
    int LOGIN = 1;
    int REGISTER = 2;
    int REGISTER_FAILED = 3;
    int REGISTER_SUCCESS = 4;
    int LOGIN_SUCCESS = 5;
    int INCORRECT_PASSWORD = 6;
    int INCORRECT_LOGIN = 7;
}