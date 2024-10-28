package Browser;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.testng.TestRunner.PriorityWeight.priority;

public class Sample {

    static Playwright playwright;
    static Browser browser;
    static BrowserContext context;
    static Page page;
    static  Page page2;

    @BeforeClass
    public static void setup() throws InterruptedException {
        // Initialize Playwright and browser only once
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(List.of("--start-maximized")));

        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("Video/sampleflow")) // Set video recording directory
                .setRecordVideoSize(1280, 720) // Set video size
                .setViewportSize(null));

    }

    @Test(priority = 0)
    public void testAmazonNavigation() {
        // Reuse the page and browser context from setup
        page = context.newPage();
        page.navigate("https://www.amazon.in/");
        page.waitForTimeout(5000); // Wait to observe the page

        page.
    }

    @Test(priority = 1)
    public void dropDown() {
        // Create a new page within the same context for the dropdown test
        page2 = context.newPage();
        page2.navigate("https://practice.expandtesting.com/dropdown");
        page2.waitForTimeout(5000);

        // Scroll and select dropdown
        page2.evaluate("window.scrollTo(0, 500);");
        page2.waitForTimeout(5000);
        Locator selectCountry = page2.locator("select#country");
        selectCountry.selectOption(new SelectOption().setLabel("India"));
        page2.waitForTimeout(8000);


    }

    @Test(priority = 2)
    public void screenShot(){
        // Taking a screenshot of the page
        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        page2.screenshot(screenshotOptions.setPath(Paths.get("./snaps/scr.png")));
        page2.waitForTimeout(5000);

        // Taking a screenshot of an element
        Locator sample = page2.locator("//header/nav[1]/a[1]");
        sample.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("./snaps/automation.png")));
        page2.waitForTimeout(8000);

        // Masking locator in screenshot
        Locator mask = page2.locator("Home");
        page2.screenshot(screenshotOptions.setPath(Paths.get("./snaps/mask.png"))
                .setFullPage(false)
                .setMask(Collections.singletonList(mask)));
        page2.waitForTimeout(8000);
        page2.close();  // Close the page after the test

        //Creating
        //page2.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
    }

    @AfterClass
    public static void tearDown() {
        // Close the browser and Playwright after all tests
        browser.close();
        playwright.close();
    }
}
