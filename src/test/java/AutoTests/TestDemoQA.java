package AutoTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.PushGateway;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestDemoQA {

    private final Locators locators = new Locators();

    static CollectorRegistry registry;
    static Counter requests;
    public static Counter failedRequests;
    public static Counter passedRequests;

    private static void configurationRemote() throws MalformedURLException, URISyntaxException {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = "chrome";
        Configuration.fastSetValue = true;
        Configuration.pageLoadTimeout = 300000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = true;
        if (System.getenv("StartRemote") != null) if(System.getenv("StartRemote").equals("yes") && System.getenv("SELENOID_URI") != null) {
            Configuration.remote = System.getenv("SELENOID_URI");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
            options.setCapability("browserName", "chrome");
            options.setCapability("acceptInsecureCerts", true);
            options.setCapability("selenoid:options", Map.of("enableVNC", true));
            Configuration.browserCapabilities = options;
            URI SELENOID_URI = new URI(Configuration.remote);
            RemoteWebDriver driver = new RemoteWebDriver(SELENOID_URI.toURL(), options);
        }
    }

    @BeforeAll
    public static void setUp() throws MalformedURLException, URISyntaxException {
        configurationRemote();
        registry = new CollectorRegistry();
        requests = Counter.build()
                .name("requests")
                .help("Number of requests")
                .register(registry);
        failedRequests = Counter.build()
                .name("failed_requests")
                .help("Number of failed requests")
                .register(registry);
        passedRequests = Counter.build()
                .name("passed_requests")
                .help("Number of passed requests")
                .register(registry);
    }

    @BeforeEach
    public void prepareForTests() {open("/");}

    @AfterAll
    public static void tearDown() throws IOException {
        Selenide.closeWebDriver();
        if(Configuration.remote == null) {
            PushGateway pg = new PushGateway("127.0.0.1:9091");
            pg.push(registry, "my_batch_job");
        }
    }

    @AfterEach
    public void testTearDown(TestInfo testInfo) {
        requests.inc();
        if (testInfo.getTags().contains("failed")) {
            failedRequests.inc();
        } else {
            passedRequests.inc();
        }
    }

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