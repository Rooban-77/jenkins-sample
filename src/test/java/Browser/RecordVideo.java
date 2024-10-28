package Browser;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RecordVideo {
   public static void main(String[] args) {
      // Ensure video directory exists
      try {
         Files.createDirectories(Paths.get("Video")); // Update to your intended path
      } catch (Exception e) {
         System.out.println("Failed to create video directory: " + e.getMessage());
      }

      Playwright playwright = Playwright.create();
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
              .setHeadless(false));
      BrowserContext context = browser.newContext(new Browser.NewContextOptions()
              .setRecordVideoDir(Paths.get("Video/testflow")).setRecordVideoSize(1280,720)); // Ensure correct path

      Page page = context.newPage();
      page.navigate("https://practicetestautomation.com/practice-test-login/");
      page.getByLabel("Username").click();
      page.getByLabel("Username").fill("student");
      page.getByLabel("Password").click();
      page.getByLabel("Password").fill("Password123");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
      Locator logout = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log out"));
      logout.hover();
      logout.click();
      //page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Test login")).click();

      // Clean up
      context.close();
      browser.close();
      playwright.close();
   }
}
