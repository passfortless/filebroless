package com.passfortless.filebroless;

import com.passfortless.filebroless.command.MainCommand;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        int exitCode = parseArgsAndExecute(args);
        System.exit(exitCode);
    }

    static int parseArgsAndExecute(String... args) {
        CommandLine cmd = new CommandLine(new MainCommand());

        try {
            ParseResult parseResult = cmd.parseArgs(args);

            // user requested usage help (--help)
            if (cmd.isUsageHelpRequested()) {
                cmd.usage(cmd.getOut());
                return cmd.getCommandSpec().exitCodeOnUsageHelp();

                // user requested version help (--version)
            } else if (cmd.isVersionHelpRequested()) {
                cmd.printVersionHelp(cmd.getOut());
                return cmd.getCommandSpec().exitCodeOnVersionHelp();
            }

            // invoke the business logic
            startApplication(parseResult);
            return cmd.getCommandSpec().exitCodeOnSuccess();

            // invalid user input: print error message and usage help
        } catch (ParameterException ex) {
            cmd.getErr().println(ex.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }
            return cmd.getCommandSpec().exitCodeOnInvalidInput();

            // exception occurred in business logic
        } catch (Exception ex) {
            ex.printStackTrace(cmd.getErr());
            return cmd.getCommandSpec().exitCodeOnExecutionException();
        }
    }

    static void startApplication(ParseResult parseResult) {
        // setup port
        String portValue = MainCommand.getServerPort(parseResult);
        System.setProperty(MainCommand.PARAMETER_PORT, portValue);

        // confirm that working directory is valid
        String workingDirectory = MainCommand.getWorkingDirectory(parseResult);
        MainCommand.validateWorkingDirectory(workingDirectory);

        // run application
        Quarkus.run(MainApp.class, parseResult.originalArgs().toArray(new String[0]));
    }
}