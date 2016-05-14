package com.netcracker.edu.bestgroup.projects.ssh.output;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SSHCommandOutputBuilder {
    private boolean plain;
    private String user;
    private Date executionDate;
    private String executionDirectory;
    private String outputColor;

    private SSHCommandOutputBuilder(boolean plain, String user, Date executionDate, String executionDirectory, String outputColor) {
        this.plain = plain;
        this.user = user;
        this.executionDate = executionDate;
        this.executionDirectory = executionDirectory;
        this.outputColor = outputColor;
    }

    private static SSHCommandOutputBuilder getInstance(boolean plain, String user, Date executionDate, String executionDirectory, String outputColor) {
        return new SSHCommandOutputBuilder(plain, user, executionDate, executionDirectory, outputColor);
    }

    public static SSHCommandOutputBuilder getUserInputOutputBuilder(String userName, String directory) {
        return getInstance(false, userName, new Date(), directory, "#0f0f0f");
    }

    public static SSHCommandOutputBuilder getPlainOutputBuilder() {
        return getInstance(true, null, null, null, "#0f0f0f");
    }

    public static SSHCommandOutputBuilder getErrorOutputBuilder() {
        return getInstance(true, null, null, null, "#ff0f0f");
    }

    public SSHCommandOutput convert(String output) {
        return new SSHCommandOutput(plain, user, executionDate, executionDirectory, output, outputColor);
    }

    public List<SSHCommandOutput> convertAll(List<String> outputLines) {
        List<SSHCommandOutput> outputList = new ArrayList<>();
        for (String line : outputLines) {
            outputList.add(convert(line));
        }
        return outputList;
    }

}