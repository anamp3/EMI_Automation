package Hooks;

import Accelerators.ActionsClass;
import Accelerators.BaseClass;
import Utilities.ExceptionHandles;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks {

    //	Everything that needs to be done before every scenario is run
    @Before
    public void beforeScenario(Scenario scenario) {
        try {
            ActionsClass.sTestCaseName = scenario.getName();
            BaseClass.OpenBrowser();
            BaseClass.driver.get(ActionsClass.EnvironmentURL);

        } catch (Exception e) {
            ExceptionHandles.HandleException(e, e.getMessage());
        }
    }
    //	Everything that needs to be done after every scenario is run
    @After
    public void afterScenario(Scenario scenario) {
        try {
            ActionsClass.sTestCaseName = scenario.getName();
            BaseClass.tearDown();
        } catch (Exception e) {
            ExceptionHandles.HandleException(e, e.getMessage());
        }

    }


}
