package com.passfortless.filebroless.config;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

import com.passfortless.filebroless.command.MainCommand;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import picocli.CommandLine;

@ApplicationScoped
public class ParametersConfigProvider {

    @ConfigProperty(name = "quarkus.http.port")
	Integer assignedPort;
    
    @Produces
    @ApplicationScoped
    ParametersConfig parametersConfig(CommandLine.ParseResult parseResult) {
        return MainCommand.parse(parseResult, assignedPort);
    }
}
