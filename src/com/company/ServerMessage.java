package com.company;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private final int command;
    private final Object data;

    public ServerMessage(int command, Object data) {
        this.command = command;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public Object getData() {
        return data;
    }
}
