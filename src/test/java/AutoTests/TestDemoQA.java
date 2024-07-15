package AutoTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestDemoQA {

    private final Locators locators = new Locators();

    private static void configurationRemote() throws MalformedURLException, URISyntaxException {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = "chrome";
        Configuration.fastSetValue = true;
        Configuration.pageLoadTimeout = 300000;
        Configuration.pageLoadStrategy = "eager";
        if (System.getenv("StartRemote") != null) if(System.getenv("StartRemote").equals("yes")) Configuration.remote = System.getenv("SELENOID_URI");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--ignore-certificate-errors");
//        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.setCapability("browserName", "chrome");
        options.setCapability("acceptInsecureCerts", true);
        options.setCapability("selenoid:options", Map.of("enableVNC", true));
        Configuration.browserCapabilities = options;
        if(Configuration.remote != null) {
            URI SELENOID_URI = new URI(Configuration.remote);
            RemoteWebDriver driver = new RemoteWebDriver(SELENOID_URI.toURL(), options);
        }
    }

    @BeforeAll
    public static void setUp() throws MalformedURLException, URISyntaxException {configurationRemote();}

    @BeforeEach
    public void prepareForTests() {open("/");}

    @AfterAll
    public static void tearDown() {Selenide.closeWebDriver();}

    @Test
    public void TestBoxTest() {
        locators.clickLink("Elements");
        locators.clickLink("Text Box");
        $("h1.text-center").shouldHave(text("Text Box"));
    }

    @Test
    public void PracticeFormTest() {
        locators.clickLink("Forms");
        locators.clickLink("Practice Form");
        $("h1.text-center").shouldHave(text("Practice Form"));
    }
}