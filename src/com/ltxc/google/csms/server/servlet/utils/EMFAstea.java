package com.ltxc.google.csms.server.servlet.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFAstea {
    private static final EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("Astea");

        public static EntityManagerFactory get() {
            return emfInstance;
        }

        private EMFAstea() {
          // nothing 
        }
}
