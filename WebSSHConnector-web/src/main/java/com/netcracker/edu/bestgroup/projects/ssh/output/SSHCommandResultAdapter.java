package com.netcracker.edu.bestgroup.projects.ssh.output;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SSHCommandResultAdapter {
    private static final DateFormat EXECUTION_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private boolean plain;
    private String user;
    private Date executionDate;
    private String executionDirectory;
    private String output;
    private String outputColor;


    SSHCommandResultAdapter(boolean plain, String user, Date executionDate, String executionDirectory, String output, String outputColor) {
        this.plain = plain;
        this.user = user;
        this.executionDate = executionDate;
        this.executionDirectory = executionDirectory;
        this.output = output;
        this.outputColor = outputColor;
    }

    public String getOutput() {
        if (plain) {
            return output;
        } else {
            String directory;
            if (executionDirectory.length() > 20) {
                int cutIndex = executionDirectory.length() - 20 + 3; /* "..." */
                directory = "..." + executionDirectory.substring(cutIndex);
            } else {
                directory = executionDirectory;
            }
            return "[" + user + "@" + String.format("%20s", directory) + " " + EXECUTION_DATE_FORMAT.format(executionDate) + "]: " + output;
        }
    }

    public String getOutputColor() {
        return outputColor;
    }
}