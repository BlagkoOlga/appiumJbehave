package steps;

import io.appium.java_client.android.AndroidDriver;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.testng.Assert.assertEquals;

/**
 * Created by lio on 6/30/15.
 */
public class SignUpSteps extends BeforeAndAfterSteps {

    public SignUpSteps(AndroidDriver driver) {
        super(driver);
    }

    @Given("Select country")
    public void openMainPage() {
        driver.findElementByCssSelector("a[data-abbr='US']").click();
    }

    @When("Click on Sign Up")
    public void clickSignUp(){
        driver.findElementByCssSelector("a.cta.flat-red[data-action='sign-in']").click();
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
    @When("Select product")
    public void selectProduct(){
        driver.findElementByCssSelector(".command[data-param='ABOC']").click();
    }
    @Then("Check ProductPage")
    public void checkPage(){
        assertEquals(driver.getCurrentUrl(), "https://itf.masterpass.mastercard.com/Wallet/ABOC/en-us/");
    }

}
