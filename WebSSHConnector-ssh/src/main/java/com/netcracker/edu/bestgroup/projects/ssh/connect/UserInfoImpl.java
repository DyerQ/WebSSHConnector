package com.netcracker.edu.bestgroup.projects.ssh.connect;

import com.jcraft.jsch.UserInfo;

class UserInfoImpl implements UserInfo { // This class is a container of connection settings

    public String getPassword() {
        return null;
    }

    public boolean promptYesNo(String str) {
        return true;
    }

    public String getPassphrase() {
        return null;
    }

    public boolean promptPassphrase(String message) {
        return true;
    }

    public boolean promptPassword(String message) {
        return true;
    }

    public void showMessage(String message) {

    }
}
