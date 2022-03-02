package com.passfortless.filebroless.config;

import java.lang.invoke.MethodHandles;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.jboss.logging.Logger;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class AppLifecycle {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    void onStart(@Observes StartupEvent startupEvent) {
        LOGGER.debug("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent shutdownEvent) {
        LOGGER.debug("The application is stopping...");
    }

}