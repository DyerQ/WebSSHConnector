package com.netcracker.edu.bestgroup.projects.ssh.connect.impl;

import com.netcracker.edu.bestgroup.projects.ssh.connect.model.SSHCommandResult;

import java.util.List;

class SSHCommandResultImpl implements SSHCommandResult {
    private boolean isErroneous;
    private List<String> output;

    SSHCommandResultImpl(boolean isErroneous, List<String> output) {
        this.isErroneous = isErroneous;
        this.output = output;
    }

    @Override
    public boolean isErroneous() {
        return isErroneous;
    }

    @Override
    public List<String> getOutput() {
        return output;
    }
}
