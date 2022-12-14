package org.reto.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/feature/reto_auto.feature",
        glue = "org.reto.stepsdefinition",
        snippets = SnippetType.CAMELCASE)

public class Runner {
}
