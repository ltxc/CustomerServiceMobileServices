package com.ltxc.google.csms.server.servlet.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Factory for creating EntityManager.
 */
public final class EMFCSMOBILE {
    private static final EntityManagerFactory emfInstance =
        Persistence.createEntityManagerFactory("CSMobile");

    public static EntityManagerFactory get() {
        return emfInstance;
    }

    private EMFCSMOBILE() {
      // nothing 
    }
}

