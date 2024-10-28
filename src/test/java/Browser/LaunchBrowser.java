package Browser;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LaunchBrowser {

    Playwright playwright;
    Browser browser;
    Page page;


    @BeforeTest
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(List.of("--start-maximized")));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
        page = context.newPage();
    }

    @Test(priority = 0)
    public void browserLaunch() throws InterruptedException {
        Thread.sleep(3000);
        page.navigate("https://www.amazon.in/");
        String webPageName = page.title();
        System.out.println("Web page title: " + webPageName);
    }

    @Test(priority = 1)
    public void userLogin() throws InterruptedException {
        Locator myAccount = page.locator("//header/div[@id='navbar']/div[@id='nav-belt']/div[3]/div[1]/a[2]/span[1]");
        Locator username = page.locator("#ap_email_login");
        Locator password = page.locator("#ap_password");
        Locator login = page.locator("#signInSubmit");
        Locator continueButton = page.locator("//body/div[@id='a-page']/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/span[1]/form[1]/span[1]/span[1]/input[1]");

        Thread.sleep(2000);
        myAccount.click();
        Thread.sleep(2000);
        username.click();
        username.fill("rooban.johnson@gmail.com");
        Thread.sleep(1000);
        continueButton.click();
        Thread.sleep(2000);
        password.click();
        password.fill("Roobanj@77");
        Thread.sleep(2000);
        login.click();
        System.out.println("Login successfully");
    }

    @Test(priority = 2)
    public void handlingInputs() throws InterruptedException {

        Locator searchBar = page.locator("#twotabsearchtextbox");
        Locator search = page.locator("#nav-search-submit-button");
        searchBar.fill("iwatch");
        search.click();
        Thread.sleep(2000);
        String searchMessage = searchBar.inputValue();
        System.out.println("Searched by the user: " + searchMessage);
        Assert.assertEquals("iwatch", "iwatch");
        page.pause();
        BrowserContext browserContext= browser.newContext();
        Page page1= browserContext.newPage();
        page1.navigate("https://letcode.in/signin");
        Locator placeholder=page.locator("app-signin.ng-star-inserted:nth-child(3) div.container.is-fluid.mt-4.mb-4.Site-content.pt-6 div.columns.is-narrow-fullhd.is-vcentered div.column.bg div.box.column.is-half.is-offset-one-quarter form.ng-pristine.ng-valid.ng-touched div.field:nth-child(1) div.control.has-icons-left > input.input.ng-pristine.ng-valid.ng-touched");
        assertThat(placeholder).hasAttribute("placeholder","Enter registered email");


    }

}
