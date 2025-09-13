package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.AuthSteps;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("User Account Management")
@Feature("Wishlist")
@Test(groups = {"saveToFavoritesWhileUnauthorized [NEW-T2]"})
public class WishlistTest extends BaseTest {
    AuthSteps authSteps;

    @BeforeClass
    public void setupSteps() {
        this.authSteps = new AuthSteps(page);
    }

    @Test(priority = 1, description = "Step 1: Navigate to the home page")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.BLOCKER)
    public void step1_NavigateToHomePage() {
        authSteps.navigateToHomePage(MagnetoConstants.PAGE_URL);
    }

    @Test(priority = 2, description = "Step 2: Search for a product", dependsOnMethods = "step1_NavigateToHomePage")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.CRITICAL)
    public void step2_SearchForProduct() {
        authSteps.searchForProduct(MagnetoConstants.PRODUCT_TO_FAVORITE);
    }

    @Test(priority = 3, description = "Step 3: Select product from results", dependsOnMethods = "step2_SearchForProduct")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.NORMAL)
    public void step3_SelectProduct() {
        authSteps.selectProductFromResults();
    }

    @Test(priority = 4, description = "Step 4: Click 'Add to Wish List' and verify redirect to login", dependsOnMethods = "step3_SelectProduct")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.CRITICAL)
    public void step4_ClickWishlistAndVerifyRedirect() {
        authSteps.clickAddToWishlist();
    }

    @Test(priority = 5, description = "Step 5: Navigate to Create Account page", dependsOnMethods = "step4_ClickWishlistAndVerifyRedirect")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.NORMAL)
    public void step5_NavigateToCreateAccount() {
        authSteps.clickCreateAccountOnLoginPage();
    }

    @Test(priority = 6, description = "Step 6: Fill the registration form", dependsOnMethods = "step5_NavigateToCreateAccount")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.CRITICAL)
    public void step6_FillRegistrationForm() {
        authSteps.fillRegistrationForm(MagnetoConstants.FIRST_NAME, MagnetoConstants.LAST_NAME, MagnetoConstants.PASSWORD);
    }

    @Test(priority = 7, description = "Step 7: Submit the registration form", dependsOnMethods = "step6_FillRegistrationForm")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.CRITICAL)
    public void step7_SubmitRegistration() {
        authSteps.submitRegistrationForm();
    }

    @Test(priority = 8, description = "Step 8: Verify registration and welcome messages", dependsOnMethods = "step7_SubmitRegistration")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.NORMAL)
    public void step8_VerifyRegistration() {
        authSteps.verifyRegistrationAndWelcomeMessages(MagnetoConstants.FIRST_NAME, MagnetoConstants.LAST_NAME);
    }

    @Test(priority = 9, description = "Step 9: Verify the wishlist status after registration", dependsOnMethods = "step8_VerifyRegistration")
    @Story("Guest user adds item to wishlist and is prompted to register")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Final verification to ensure the item added to the wishlist as a guest is now present in the newly created user's account.")
    public void step9_VerifyWishlist() {
        authSteps.verifyWishlistStatus();
    }
}