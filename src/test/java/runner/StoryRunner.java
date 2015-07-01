package runner;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

/**
 * Created by lio on 6/30/15.
 */
@RunWith(Parameterized.class)
@Configure()
public class StoryRunner {

    private AndroidDriver driver;
    private String platform;
    private String browser;
    private String browserVersion;

    public StoryRunner(String platform, String browser,
                       String browserVersion) {
        this.platform = platform;
        this.browser = browser;
        this.browserVersion = browserVersion;
    }

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Testing");
        capabilities.setCapability(MobileCapabilityType.PLATFORM, "ANDROID");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "BROWSER");
        capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);
       // capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, “com.example.android.contactmanager”);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println(driver);
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextName);
           /* if (contextName.contains("WEBVIEW")) {
                driver.context(contextName);
                System.out.println(contextName);
                Thread.sleep(1500);
            }*/
        }
                driver.get("https://itf.masterpass.mastercard.com/");


    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Parameterized.Parameters
    public static LinkedList<String[]> getEnvironments() throws Exception {
        LinkedList<String[]> env = new LinkedList<String[]>();
        env.add(new String[]{Platform.ANDROID.toString(), "ANDROID", "40"});
        //env.add(new String[]{Platform.MAC.toString(), "firefox", "36.0"});
        //env.add(new String[]{Platform.MAC.toString(), "safari", "8.0.3"});
        //env.add(new String[]{Platform.MAC.toString(), "opera", "12.14"});

        //add more browsers here

        return env;
    }

    @Test
    public void runStories() {
        Embedder storyEmbedder = new StoryEmbedder(driver);
        storyEmbedder.runStoriesAsPaths(storyPaths());
    }

    List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/*.story", "");
    }
}
