package ge.tbc.testautomation.runners;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    // Keep these static
    protected static Playwright playwright;
    protected static Browser browser;

    protected BrowserContext context;
    public Page page;

    @BeforeClass
    public void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();

        page.route("**/*", route -> {
            String url = route.request().url();
            if (url.contains("doubleclick.net") ||
                    url.contains("googlesyndication.com") ||
                    url.contains("ads") ||
                    url.contains("tracking")) {
                System.out.println("Blocking request: " + url);
                route.abort();
            } else {
                route.resume();
            }
        }); // without this when i tried to click on items it was redirecting me on different ads
    }

    @AfterClass
    public void closeBrowser() {
        playwright.close();
    }
}