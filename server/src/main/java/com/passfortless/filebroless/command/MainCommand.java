package com.passfortless.filebroless.command;

import java.io.File;

import com.passfortless.filebroless.config.ParametersConfig;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParseResult;

@CommandLine.Command(name = "filebroless", mixinStandardHelpOptions = true, showEndOfOptionsDelimiterInUsageHelp = true, version = {
                "@|yellow Simple HTTP File Browser 1.0 (c) 2022|@",
                "   JVM: ${java.version} (${java.vendor} ${java.vm.name} ${java.vm.version})",
                "   OS: ${os.name} ${os.version} ${os.arch}" })
public class MainCommand {

        public static final String PARAMETER_PORT = "port";
        public static final String PARAMETER_DIRECTORY = "directory";
        public static final String PARAMETER_BROWSER = "browser";
        public static final String PARAMETER_OPEN_BROWSER = "open-browser";
        public static final String DEFAULT_PORT = "0";
        public static final String DEFAULT_DIRECTORY = ".";
        public static final String DEFAULT_BROWSER = "system";

        @Parameters(index = "0", paramLabel = "<directory>", defaultValue = DEFAULT_DIRECTORY, description = "Working directory path. Default: ${DEFAULT-VALUE}")
        String directoryParameter;

        @CommandLine.Option(names = { "-d",
                        "--" + PARAMETER_DIRECTORY }, paramLabel = "directory", arity = "0..1", defaultValue = DEFAULT_DIRECTORY, fallbackValue = DEFAULT_DIRECTORY, description = "Working directory path. Default: ${DEFAULT-VALUE}")
        String directoryOption;

        @CommandLine.Option(names = { "-p",
                        "--" + PARAMETER_PORT }, arity = "0..1", defaultValue = DEFAULT_PORT, fallbackValue = DEFAULT_PORT, description = "HTTP server port. Default: random")
        int port;

        @CommandLine.Option(names = { "-o",
                        "--" + PARAMETER_OPEN_BROWSER }, paramLabel = "open", description = "Open browser. Default: ${DEFAULT-VALUE}")
        boolean openBrowser;

        @CommandLine.Option(names = { "-b",
                        "--" + PARAMETER_BROWSER }, paramLabel = "browser", arity = "0..1", defaultValue = DEFAULT_BROWSER, fallbackValue = DEFAULT_BROWSER, description = "Browser. Default: ${DEFAULT-VALUE}")
        String browserName;

        public static ParametersConfig parse(ParseResult parseResult, Integer assignedPort) {
                String workingDirectory = getWorkingDirectory(parseResult);
                String browserName = getBrowser(parseResult);
                boolean openBrowser = shouldOpenBrowser(parseResult);

                return ParametersConfig.builder(assignedPort, workingDirectory).openBrowser(openBrowser)
                                .browserName(browserName).build();
        }

        public static Integer getServerPort(ParseResult parseResult) {
                Integer portValue = parseResult.hasMatchedOption(PARAMETER_PORT)
                                ? parseResult.matchedOption(PARAMETER_PORT).getValue()
                                : Integer.parseInt(DEFAULT_PORT);
                return portValue;
        }

        public static String getWorkingDirectory(ParseResult parseResult) {
                String workingDirectory = DEFAULT_DIRECTORY;
                if (parseResult.hasMatchedPositional(0)) {
                        workingDirectory = parseResult.matchedPositional(0).getValue();
                } else if (parseResult.hasMatchedOption(PARAMETER_DIRECTORY)) {
                        workingDirectory = parseResult.matchedOption(PARAMETER_DIRECTORY).getValue();
                }
                return workingDirectory;
        }

        public static boolean validateWorkingDirectory(String workingDirectory) {
                File file = new File(workingDirectory);
                if (!file.exists() || !file.isDirectory()) {
                        throw new IllegalArgumentException(
                                        String.format("Working directory not valid [%s]", workingDirectory));
                }
                return true;
        }

        public static String getBrowser(ParseResult parseResult) {
                String browser = parseResult.hasMatchedOption(PARAMETER_BROWSER)
                                ? parseResult.matchedOption(PARAMETER_BROWSER).getValue()
                                : DEFAULT_BROWSER;
                return browser;
        }

        public static boolean shouldOpenBrowser(ParseResult parseResult) {
                boolean open = parseResult.hasMatchedOption(PARAMETER_OPEN_BROWSER)
                                ? parseResult.matchedOption(PARAMETER_OPEN_BROWSER).getValue()
                                : false;
                return open;
        }
}