package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.pages.MyAccountPage;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.AuthSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = {"saveToFavoritesWhileUnauthorized [NEW-T2]"})
public class WishlistTest extends BaseTest {
    AuthSteps authSteps;

    @BeforeClass
    public void setupSteps() {
        this.authSteps = new AuthSteps(page);
    }

    @Test(priority = 1, description = "Step 1: Navigate to the home page")
    public void step1_NavigateToHomePage() {
        authSteps.navigateToHomePage(MagnetoConstants.PAGE_URL);
    }

    @Test(priority = 2, description = "Step 2: Search for a product", dependsOnMethods = "step1_NavigateToHomePage")
    public void step2_SearchForProduct() {
        authSteps.searchForProduct(MagnetoConstants.PRODUCT_TO_FAVORITE);
    }

    @Test(priority = 3, description = "Step 3: Select product from results", dependsOnMethods = "step2_SearchForProduct")
    public void step3_SelectProduct() {
        authSteps.selectProductFromResults();
    }

    @Test(priority = 4, description = "Step 4: Click 'Add to Wish List' and verify redirect", dependsOnMethods = "step3_SelectProduct")
    public void step4_ClickWishlistAndVerifyRedirect() {
        authSteps.clickAddToWishlist();
    }

    @Test(priority = 5, description = "Step 5: Navigate to Create Account page", dependsOnMethods = "step4_ClickWishlistAndVerifyRedirect")
    public void step5_NavigateToCreateAccount() {
        authSteps.clickCreateAccountOnLoginPage();
    }

    @Test(priority = 6, description = "Step 6: Fill the registration form", dependsOnMethods = "step5_NavigateToCreateAccount")
    public void step6_FillRegistrationForm() {
        authSteps.fillRegistrationForm(MagnetoConstants.FIRST_NAME, MagnetoConstants.LAST_NAME,MagnetoConstants.PASSWORD);
    }

    @Test(priority = 7, description = "Step 7: Submit the registration form", dependsOnMethods = "step6_FillRegistrationForm")
    public void step7_SubmitRegistration() {
        authSteps.submitRegistrationForm();
    }

    @Test(priority = 8, description = "Step 8: Verify registration and welcome messages", dependsOnMethods = "step7_SubmitRegistration")
    public void step8_VerifyRegistration() {
        authSteps.verifyRegistrationAndWelcomeMessages(MagnetoConstants.FIRST_NAME, MagnetoConstants.LAST_NAME);
    }

    @Test(priority = 9, description = "Step 9: Verify the wishlist status", dependsOnMethods = "step8_VerifyRegistration")
    public void step9_VerifyWishlist() {
        authSteps.verifyWishlistStatus();
    }
}