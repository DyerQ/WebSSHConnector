package com.netcracker.edu.bestgroup.projects.ssh.connect.model;

import java.util.List;

public interface SSHCommandResult {
    boolean isErroneous();

    List<String> getOutput();
}
