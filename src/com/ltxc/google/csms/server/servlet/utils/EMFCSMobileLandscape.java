package com.ltxc.google.csms.server.servlet.utils;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Factory for creating EntityManager.
 */
public final class EMFCSMobileLandscape {
    private static final EntityManagerFactory emfInstance =
        Persistence.createEntityManagerFactory("CSMobileLandscape");

    public static EntityManagerFactory get() {
        return emfInstance;
    }

    private EMFCSMobileLandscape() {
      // nothing 
    }
}