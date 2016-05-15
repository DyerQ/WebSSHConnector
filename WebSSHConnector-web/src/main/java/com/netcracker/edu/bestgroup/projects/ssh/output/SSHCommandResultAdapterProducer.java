package com.netcracker.edu.bestgroup.projects.ssh.output;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SSHCommandResultAdapterProducer {

    private boolean plain;

    private String user;

    private Date executionDate;

    private String executionDirectory;

    private String outputColor;

    private SSHCommandResultAdapterProducer(boolean plain, String user, Date executionDate, String executionDirectory, String outputColor) {
        this.plain = plain;
        this.user = user;
        this.executionDate = executionDate;
        this.executionDirectory = executionDirectory;
        this.outputColor = outputColor;
    }

    private static SSHCommandResultAdapterProducer getInstance(boolean plain, String user, Date executionDate, String executionDirectory, String outputColor) {
        return new SSHCommandResultAdapterProducer(plain, user, executionDate, executionDirectory, outputColor);
    }

    public static SSHCommandResultAdapterProducer getUserInputAdapterProducer(String userName, String directory) {
        return getInstance(false, userName, new Date(), directory, "#0f0f0f");
    }

    public static SSHCommandResultAdapterProducer getPlainAdapterProducer() {
        return getInstance(true, null, null, null, "#0f0f0f");
    }

    public static SSHCommandResultAdapterProducer getErrorAdapterProducer() {
        return getInstance(true, null, null, null, "#ff0f0f");
    }

    public SSHCommandResultAdapter convert(String output) {
        return new SSHCommandResultAdapter(plain, user, executionDate, executionDirectory, output, outputColor);
    }

    public List<SSHCommandResultAdapter> convertAll(List<String> outputLines) {
        List<SSHCommandResultAdapter> outputList = new ArrayList<>();
        for (String line : outputLines) {
            outputList.add(convert(line));
        }
        return outputList;
    }
}
