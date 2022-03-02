package com.passfortless.filebroless;

import java.net.URI;

import javax.inject.Inject;

import com.passfortless.filebroless.config.AppConfig;
import com.passfortless.filebroless.config.ParametersConfig;
import com.passfortless.filebroless.service.BrowserService;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;

public class MainApp implements QuarkusApplication {

        @Inject
        AppConfig appConfig;

        @Inject
        ParametersConfig parametersConfig;

        @Override
        public int run(String... args) throws Exception {
                String serverUrl = String.format("http://localhost:%d", parametersConfig.getPort());
                System.out.println(String.format("Server listening on %s", serverUrl));

                if (parametersConfig.isOpenBrowser()) {
                        URI uri = new URI(serverUrl);
                        new BrowserService(appConfig, parametersConfig).open(uri);
                }

                Quarkus.waitForExit();
                return 0;
        }
}