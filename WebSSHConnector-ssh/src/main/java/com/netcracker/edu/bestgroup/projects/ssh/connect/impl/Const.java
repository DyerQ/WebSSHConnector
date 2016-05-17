package com.netcracker.edu.bestgroup.projects.ssh.connect.impl;

final class Const {
    private Const() {
        throw new UnsupportedOperationException("No Const instances for you!");
    }

    public static final int PORT_MIN_VALUE = 0;
    public static final int PORT_MAX_VALUE = 65535;
    public static final int DEFAULT_PORT = 22;
    public static final int DEFAULT_CHANNEL_SCANNING_DELAY_MS = 100;
}
