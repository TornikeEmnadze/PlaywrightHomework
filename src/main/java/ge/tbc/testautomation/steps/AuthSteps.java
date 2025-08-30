package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.*;
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

    public AuthSteps navigateToHomePage(String url) {
        page.navigate(url);
        return this;
    }

    public AuthSteps searchForProduct(String productName) {
        productToFavorite = productName;
        homePage.searchBar.fill(productName);
        homePage.searchBar.press("Enter");
        searchResultsPage.productItemLinks.first().waitFor();
        return this;
    }

    public AuthSteps selectProductFromResults() {
        searchResultsPage.productItemLinks.first().click();
        productPage.productName.waitFor();
        Assert.assertEquals(productPage.productName.innerText(), productToFavorite);
        return this;
    }

    public AuthSteps clickAddToWishlist() {
        productPage.addToWishlistButton.waitFor(); // this should have waited but it didn't work and as much as i hate it i had to make it wait forcefully
        page.waitForTimeout(2000);

        productPage.addToWishlistButton.click();

        loginPage.pageTitle.waitFor(); // Wait for the redirect to complete
        Assert.assertEquals(loginPage.pageTitle.innerText(), "Customer Login");
        return this;
    }

    public AuthSteps clickCreateAccountOnLoginPage() {
        loginPage.createAccountButton.click();
        createAccountPage.firstNameInput.waitFor();
        return this;
    }

    public AuthSteps fillRegistrationForm(String firstName, String lastName, String password) {
        String email = String.format("testuser_%d@example.com", System.currentTimeMillis());
        createAccountPage.firstNameInput.fill(firstName);
        createAccountPage.lastNameInput.fill(lastName);
        createAccountPage.emailInput.fill(email);
        createAccountPage.passwordInput.fill(password);
        createAccountPage.confirmPasswordInput.fill(password);
        return this;
    }

    public AuthSteps submitRegistrationForm() {
        page.waitForTimeout(2000);
        createAccountPage.createAccountButton.click();
        myAccountPage.registrationSuccessMessage.waitFor(); // Wait for the final page to load
        return this;
    }

    public AuthSteps verifyRegistrationAndWelcomeMessages(String firstName, String lastName) {
        Assert.assertTrue(myAccountPage.registrationSuccessMessage.isVisible(), "Registration success message not visible.");

        String expectedWelcome = String.format("Welcome, %s %s!", firstName, lastName);
        assertThat(myAccountPage.welcomeMessageHeader.getByText(expectedWelcome)).isVisible();

        return this;
    }

    public AuthSteps verifyWishlistStatus() {
        Assert.assertTrue(myAccountPage.addToWishlistSuccessMessage.isVisible(), "Add to wishlist success message not visible.");
        Assert.assertTrue(myAccountPage.addToWishlistSuccessMessage.innerText().contains(productToFavorite), "Wishlist message has wrong product name.");
        Assert.assertEquals(myAccountPage.wishlistItemName.innerText(), productToFavorite, "Product name in wishlist is incorrect.");
        return this;
    }
}