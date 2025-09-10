package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.*;
import io.qameta.allure.Step;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AuthSteps {
    private final Page page;
    private final HomePage homePage;
    private final SearchResultPage searchResultsPage;
    private final ProductPage productPage;
    private final LoginPage loginPage;
    private final CreateAccountPage createAccountPage;
    private final MyAccountPage myAccountPage;
    private static String productToFavorite;

    public AuthSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
        this.searchResultsPage = new SearchResultPage(page);
        this.productPage = new ProductPage(page);
        this.loginPage = new LoginPage(page);
        this.createAccountPage = new CreateAccountPage(page);
        this.myAccountPage = new MyAccountPage(page);
    }

    @Step("Navigate to Home Page: {0}")
    public AuthSteps navigateToHomePage(String url) {
        page.navigate(url);
        System.out.println("I AM A CHANGE MEANWHILE");
        return this;
    }

    @Step("Search for product: {0}")
    public AuthSteps searchForProduct(String productName) {
        productToFavorite = productName;
        homePage.searchBar.fill(productName);
        homePage.searchBar.press("Enter");
        searchResultsPage.productItemLinks.first().waitFor();
        return this;
    }

    @Step("Select the first product from search results")
    public AuthSteps selectProductFromResults() {
        searchResultsPage.productItemLinks.first().click();
        productPage.productName.waitFor();
        Assert.assertEquals(productPage.productName.innerText(), productToFavorite);
        return this;
    }

    @Step("Click 'Add to Wishlist' and expect redirect to Login page")
    public AuthSteps clickAddToWishlist() {
        productPage.addToWishlistButton.waitFor();
        page.waitForTimeout(2000);
        productPage.addToWishlistButton.click();
        loginPage.pageTitle.waitFor();
        Assert.assertEquals(loginPage.pageTitle.innerText(), "Customer Login");
        return this;
    }

    @Step("Click 'Create an Account' on the Login page")
    public AuthSteps clickCreateAccountOnLoginPage() {
        loginPage.createAccountButton.click();
        createAccountPage.firstNameInput.waitFor();
        return this;
    }

    @Step("Fill registration form for user: {0} {1}")
    public AuthSteps fillRegistrationForm(String firstName, String lastName, String password) {
        String email = String.format("testuser_%d@example.com", System.currentTimeMillis());
        createAccountPage.firstNameInput.fill(firstName);
        createAccountPage.lastNameInput.fill(lastName);
        createAccountPage.emailInput.fill(email);
        createAccountPage.passwordInput.fill(password);
        createAccountPage.confirmPasswordInput.fill(password);
        return this;
    }

    @Step("Submit the registration form")
    public AuthSteps submitRegistrationForm() {
        page.waitForTimeout(2000);
        createAccountPage.createAccountButton.click();
        myAccountPage.registrationSuccessMessage.waitFor();
        return this;
    }

    @Step("Verify registration success and welcome messages for: {0} {1}")
    public AuthSteps verifyRegistrationAndWelcomeMessages(String firstName, String lastName) {
        Assert.assertTrue(myAccountPage.registrationSuccessMessage.isVisible(), "Registration success message not visible.");
        String expectedWelcome = String.format("Welcome, %s %s!", firstName, lastName);
        assertThat(myAccountPage.welcomeMessageHeader.getByText(expectedWelcome)).isVisible();
        return this;
    }

    @Step("Verify wishlist status and confirm product is present")
    public AuthSteps verifyWishlistStatus() {
        Assert.assertTrue(myAccountPage.addToWishlistSuccessMessage.isVisible(), "Add to wishlist success message not visible.");
        Assert.assertTrue(myAccountPage.addToWishlistSuccessMessage.innerText().contains(productToFavorite), "Wishlist message has wrong product name.");
        Assert.assertEquals(myAccountPage.wishlistItemName.innerText(), productToFavorite, "Product name in wishlist is incorrect.");
        return this;
    }
}