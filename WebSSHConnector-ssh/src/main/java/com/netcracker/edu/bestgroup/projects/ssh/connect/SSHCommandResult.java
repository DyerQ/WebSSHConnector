package com.netcracker.edu.bestgroup.projects.ssh.connect;

import java.util.List;

public class SSHCommandResult {
    private boolean isErroneous;
    private List<String> output;

    public SSHCommandResult(boolean isErroneous, List<String> output) {
        this.isErroneous = isErroneous;
        this.output = output;
    }

    public boolean isErroneous() {
        return isErroneous;
    }

    public List<String> getOutput() {
        return output;
    }
}
