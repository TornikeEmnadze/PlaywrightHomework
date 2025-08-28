package ge.tbc.testautomation.tests;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.constants.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;


public class IsolatedUserTest {
    private static Playwright pwInstance;
    private static Browser browserSession;


    private Page userPage;
    private String currentUserEmail;

    @BeforeClass
    public void suiteSetup() {
        pwInstance = Playwright.create();
        browserSession = pwInstance.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterClass
    public void suiteTeardown() {
        if (browserSession != null) {
            browserSession.close();
        }
        if (pwInstance != null) {
            pwInstance.close();
        }
    }


    @BeforeMethod
    public void initializeTestEnvironment() {
        userPage = browserSession.newPage();
        currentUserEmail = "test.user." + RandomStringUtils.randomAlphanumeric(8).toLowerCase() + "@example.com";
        registerNewUser();
        loginAsCurrentUser();
        userPage.locator("a[data-test='nav-home']").click();
        assertThat(userPage.locator("div.jumbotron")).isVisible();
    }


    @AfterMethod
    public void cleanupTest() {
        if (userPage != null) {
            userPage.close();
        }
    }

    @Test
    public void verifyProductCanBeAddedToFavorites() {
        Locator firstProductOnPage = userPage.locator("a.card").first();
        String selectedProductName = firstProductOnPage.locator("[data-test='product-name']").textContent().trim();

        firstProductOnPage.click();
        assertThat(userPage.locator("h1[data-test='product-name']")).hasText(selectedProductName);

        userPage.locator("button[data-test='add-to-favorites']").click();
        assertThat(userPage.locator("#toast-container .ngx-toastr")).isVisible();

        userPage.locator("a[data-test='nav-menu']").click();
        userPage.locator("a[data-test='nav-my-favorites']").click();

        assertThat(userPage.locator(".card-title:has-text('" + selectedProductName + "')")).isVisible();
        System.out.println("Confirmation: Product '" + selectedProductName + "' was successfully added to favorites.");
    }

    @Test
    public void validateCategoryFilteringLogic() {
            assertThat(userPage.locator("a.card")).hasCount(9);
            int initialCount = userPage.locator("a.card").count();
            assertThat(userPage.locator("a.card")).hasCount(initialCount);
            Locator handToolsCheckbox = userPage.locator("label:has-text('Screwdriver') input[type='checkbox']");
            Locator powerToolsCheckbox = userPage.locator("label:has-text('Sander') input[type='checkbox']");


            handToolsCheckbox.check();

            assertThat(userPage.locator("a.card")).not().hasCount(initialCount);

            int handToolsCount = userPage.locator("a.card").count();


            handToolsCheckbox.uncheck();

            assertThat(userPage.locator("a.card")).hasCount(initialCount);

            powerToolsCheckbox.check();

            assertThat(userPage.locator("a.card")).not().hasCount(initialCount);
            int powerToolsCount = userPage.locator("a.card").count();

            handToolsCheckbox.check();

            assertThat(userPage.locator("a.card")).not().hasCount(powerToolsCount);
            int totalCount = userPage.locator("a.card").count();
            assertEquals(totalCount, handToolsCount + powerToolsCount);

        }

    @Test
    public void verifyFavoriteCanBeRemoved() {
        Locator firstItem = userPage.locator("a.card").first();
        String itemName = firstItem.locator("[data-test='product-name']").textContent().trim();
        firstItem.click();
        userPage.locator("button[data-test='add-to-favorites']").click();
        assertThat(userPage.locator("#toast-container .ngx-toastr")).isVisible();

        userPage.locator("a[data-test='nav-menu']").click();
        userPage.locator("a[data-test='nav-my-favorites']").click();
        Locator favoriteItemCard = userPage.locator(".card-title:has-text('" + itemName + "')");
        assertThat(favoriteItemCard).isVisible();

        userPage.locator("button[data-test='delete']").click();

    }

    @Test
    public void confirmProductTagsAreDisplayedCorrectly() {
        userPage.locator("a[data-test='nav-categories']").click();
        userPage.locator("a[data-test='nav-hand-tools']").click();
        userPage.locator("a.card:has-text('Thor Hammer')").click();
        assertThat(userPage.locator("h1[data-test='product-name']")).hasText(" Thor Hammer ");
        assertThat(userPage.locator("span.badge:has-text('Hammer')")).isVisible();
        System.out.println("Validation passed: Thor Hammer product page displays the correct 'Hammer' tag.");
    }

    // --- Helper Methods for Setup ---

    private void registerNewUser() {
        userPage.navigate("https://practicesoftwaretesting.com/auth/register");
        userPage.locator("input[data-test='first-name']").fill(Constants.DEFAULT_FIRST_NAME);
        userPage.locator("input[data-test='last-name']").fill(Constants.DEFAULT_LAST_NAME);
        userPage.locator("input[data-test='dob']").fill(Constants.DEFAULT_DOB);
        userPage.locator("input[data-test='street']").fill(Constants.DEFAULT_ADDRESS);
        userPage.locator("input[data-test='postal_code']").fill(Constants.DEFAULT_POSTAL_CODE);
        userPage.locator("input[data-test='city']").fill(Constants.DEFAULT_CITY);
        userPage.locator("input[data-test='state']").fill(Constants.DEFAULT_STATE);
        userPage.locator("select[data-test='country']").selectOption(Constants.DEFAULT_COUNTRY_VALUE);
        userPage.locator("input[data-test='phone']").fill(Constants.DEFAULT_PHONE);
        userPage.locator("input[data-test='email']").fill(currentUserEmail);
        userPage.locator("input[data-test='password']").fill(Constants.USER_PASSWORD);
        userPage.locator("button[data-test='register-submit']").click();
        userPage.waitForURL("**/login");
        assertThat(userPage.locator("h3:has-text('Login')")).isVisible();
    }

    private void loginAsCurrentUser() {
        userPage.locator("input[data-test='email']").fill(currentUserEmail);
        userPage.locator("input[data-test='password']").fill(Constants.USER_PASSWORD);
        userPage.locator("input[data-test='login-submit']").click();
        assertThat(userPage.locator("h1:has-text('My account')")).isVisible();
    }
}