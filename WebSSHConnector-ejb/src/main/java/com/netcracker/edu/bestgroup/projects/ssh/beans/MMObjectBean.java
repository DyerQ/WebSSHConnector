package com.netcracker.edu.bestgroup.projects.ssh.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;


@Stateless(name = "MMObjectEJB")
public class MMObjectBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MMObjectBean.class);

    public MMObjectBean() {
        LOGGER.debug("Hello from MMObject constructor");
    }
}
