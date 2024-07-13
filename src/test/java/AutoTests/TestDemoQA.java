package AutoTests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestDemoQA {

    private final Locators locators = new Locators();

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
//        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
    }

    @BeforeEach
    public void prepareForTests() {open("/");}

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
