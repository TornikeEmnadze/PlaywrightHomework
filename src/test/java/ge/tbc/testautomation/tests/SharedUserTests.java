package ge.tbc.testautomation.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.constants.Constants;
import io.qameta.allure.*;
import org.testng.annotations.*;

import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

@Epic("User Account Features (Shared User)")
@Feature("Product Interaction and State Persistence")
@Link(name = "Practice Software Testing", url = "https://practicesoftwaretesting.com/")
public class SharedUserTests {
    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    private static String testEmail;
    private static final String TEST_PASSWORD = Constants.USER_PASSWORD;
    private String favouriteProduct;

    @BeforeClass
    public void classSetup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--no-sandbox"))
        );
        testEmail = Constants.USER_Email;
        Page setupPage = browser.newPage();
        setupPage.navigate("https://practicesoftwaretesting.com/auth/register");

        setupPage.locator("input[data-test='first-name']").fill(Constants.DEFAULT_FIRST_NAME);
        setupPage.locator("input[data-test='last-name']").fill(Constants.DEFAULT_LAST_NAME);
        setupPage.locator("input[data-test='dob']").fill(Constants.DEFAULT_DOB);
        setupPage.locator("input[data-test='street']").fill(Constants.DEFAULT_ADDRESS);
        setupPage.locator("input[data-test='postal_code']").fill(Constants.DEFAULT_POSTAL_CODE);
        setupPage.locator("input[data-test='city']").fill(Constants.DEFAULT_CITY);
        setupPage.locator("input[data-test='state']").fill(Constants.DEFAULT_STATE);
        setupPage.locator("select[data-test='country']").selectOption(Constants.DEFAULT_COUNTRY_VALUE);
        setupPage.locator("input[data-test='phone']").fill(Constants.DEFAULT_PHONE);
        setupPage.locator("input[data-test='email']").fill(testEmail);
        setupPage.locator("input[data-test='password']").fill(Constants.USER_PASSWORD);
        setupPage.locator("button[data-test='register-submit']").click();

        assertThat(setupPage.locator("h3:has-text('Login')")).isVisible();
        setupPage.close();
    }

    @BeforeMethod
    public void loginBeforeEachTest() {
        page = browser.newPage();
        page.navigate("https://practicesoftwaretesting.com/auth/login");
        assertThat(page.locator("h3:has-text('Login')")).isVisible();

        page.locator("input[data-test='email']").fill(testEmail);
        page.locator("input[data-test='password']").fill(TEST_PASSWORD);
        page.locator("input[data-test='login-submit']").click();

        page.waitForURL("**/account");

        assertThat(page.locator("h1:has-text('My account')")).isVisible();
        page.locator("a[data-test='nav-home']").click();
    }

    @AfterMethod
    public void closePage() {
        page.close();
    }

    @AfterClass
    public void shutdown() {
        browser.close();
        playwright.close();
    }

    @Test(priority = 1, description = "Verify favorites persistence across sessions for a shared user")
    @Story("User favorites persist after logging out and back in")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Adds a product to favorites, logs out, logs back in with the same user, and verifies the product is still in the favorites list.")
    public void favouritesTest() {
        Locator firstProduct = page.locator("a.card").first();
        favouriteProduct = firstProduct.locator("[data-test='product-name']").textContent().trim();
        firstProduct.click();

        assertThat(page.locator("h1[data-test='product-name']")).hasText(favouriteProduct);
        page.locator("button[data-test='add-to-favorites']").click();
        page.locator("#toast-container .ngx-toastr").waitFor();

        page.locator("a[data-test='nav-menu']").click();
        page.getByText("Sign out").click();
        loginBeforeEachTest();

        page.locator("a[data-test='nav-menu']").click();
        page.locator("a[data-test='nav-my-favorites']").click();
        assertThat(page.locator("[data-test='product-name']:has-text('" + favouriteProduct + "')")).isVisible();
    }

    @Test(priority = 2, description = "Validate product category filtering logic for a shared user")
    @Story("User can filter products")
    @Severity(SeverityLevel.NORMAL)
    public void filterTest() {
        assertThat(page.locator("a.card")).hasCount(9);
        int initialCount = page.locator("a.card").count();
        assertThat(page.locator("a.card")).hasCount(initialCount);
        Locator handToolsCheckbox = page.locator("label:has-text('Screwdriver') input[type='checkbox']");
        Locator powerToolsCheckbox = page.locator("label:has-text('Sander') input[type='checkbox']");

        handToolsCheckbox.check();

        assertThat(page.locator("a.card")).not().hasCount(initialCount);

        int handToolsCount = page.locator("a.card").count();

        handToolsCheckbox.uncheck();
        assertThat(page.locator("a.card")).hasCount(initialCount);

        powerToolsCheckbox.check();
        assertThat(page.locator("a.card")).not().hasCount(initialCount);
        int powerToolsCount = page.locator("a.card").count();

        handToolsCheckbox.check();
        assertThat(page.locator("a.card")).not().hasCount(powerToolsCount);
        int totalCount = page.locator("a.card").count();
        assertEquals(totalCount, handToolsCount + powerToolsCount);

    }



    @Test(priority = 3, dependsOnMethods = "favouritesTest", description = "Verify removing a favorite persists across sessions")
    @Story("User favorites persist after logging out and back in")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Removes a previously favorited item, logs out, logs back in, and verifies the item is no longer in the favorites list.")
    public void removeFavouriteTest() {
        page.locator("a[data-test='nav-menu']").click();
        page.locator("a[data-test='nav-my-favorites']").click();
        assertThat(page.locator(".card-title:has-text('" + favouriteProduct + "')")).isVisible();

        page.locator("button[data-test='delete']").click();
        assertThat(page.locator(".card-title:has-text('" + favouriteProduct + "')")).not().isVisible();

        // log out & back in to confirm it stays deleted
        page.locator("a[data-test='nav-menu']").click();
        page.getByText("Sign out").click();;
        loginBeforeEachTest();

        page.locator("a[data-test='nav-menu']").click();
        page.locator("a[data-test='nav-my-favorites']").click();
        assertThat(page.locator(".card-title:has-text('" + favouriteProduct + "')")).not().isVisible();
    }

    @Test(priority = 4, description = "Confirm product tags are displayed correctly")
    @Story("User can view product details")
    @Severity(SeverityLevel.MINOR)
    public void tagsTest() {
        page.locator("a[data-test='nav-categories']").click();
        page.locator("a[data-test='nav-hand-tools']").click();

        page.locator("a.card:has-text('Thor Hammer')").click();
        assertThat(page.locator("h1[data-test='product-name']")).hasText(" Thor Hammer ");
        assertThat(page.locator("span.badge:has-text('Hammer')")).isVisible();
    }
}