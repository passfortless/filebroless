package com.passfortless.filebroless.service;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import com.passfortless.filebroless.config.AppConfig;
import com.passfortless.filebroless.config.ParametersConfig;
import com.passfortless.filebroless.platforms.PlatformFactory;

import org.jboss.logging.Logger;

public class BrowserService {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String DEFAULT_BROWSER = "system";

    private final String browserCommand;

    public BrowserService(final AppConfig appConfig, final ParametersConfig parametersConfig) {
        String platformName = PlatformFactory.getPlatform().getName();
        String browserName = parametersConfig.getBrowserName();
        Map<String, String> platformBrowsers = appConfig.browser().get(platformName).config();
        if (platformBrowsers.containsKey(browserName)) {
            this.browserCommand = platformBrowsers.get(browserName);
        } else {
            this.browserCommand = platformBrowsers.get(DEFAULT_BROWSER);
        }
    }

    public boolean open(URI uri) {
        String commandString = String.format(browserCommand, uri.toString());
        LOGGER.debug(String.format("Command: %s", commandString));
        return executeSystemCommand(commandString);
    }

    private boolean executeSystemCommand(String commandString) {
        try {
            // split command parameters and escape double and single quotes
            String lookaheadRegex = "\\s+(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            String[] command = Arrays.stream(commandString.split(lookaheadRegex, -1))
                    .map(val -> val.replaceAll("\"|'", ""))
                    .toArray(String[]::new);
            LOGGER.debug(String.format("System process args: %s", String.join(" | ", command)));
            ProcessBuilder process = new ProcessBuilder(command);
            int result = process.inheritIO().start().waitFor();
            LOGGER.debug(String.format("Process executed, result: %d", result));
            return true;
        } catch (Exception e) {
            LOGGER.error("Error opening browser", e);
            return false;
        }
    }
}
