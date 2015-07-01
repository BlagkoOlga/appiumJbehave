package steps;

import io.appium.java_client.android.AndroidDriver;
import org.jbehave.core.annotations.BeforeScenario;


/**
 * Created by lio on 6/30/15.
 */
public class BeforeAndAfterSteps {
    public AndroidDriver driver;


    public BeforeAndAfterSteps(AndroidDriver driver){
        this.driver = driver;

    }


    @BeforeScenario
    public void beforeScenario(){
        driver.manage().deleteAllCookies();
        // mainPage.open("http://stubby.vm-max-test.kyiv.ciklum.net/");

    }
}
