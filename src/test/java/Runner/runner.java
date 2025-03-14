package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"GlueCode", "Hooks"},
        plugin = {"pretty", "html:src/test/resources/cucumber-reports.html"},
        monochrome = true,
        tags = ""
)

@RunWith(Cucumber.class)
public class runner {

}

