package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.PurchaseSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("E-Commerce Functionality")
@Feature("Registered User Checkout")
public class PurchaseTest extends BaseTest {
    PurchaseSteps purchaseSteps;

    @Test(description = "E2E test for a registered user purchasing an item from their wishlist")
    @Story("Full purchase journey from wishlist")
    @Severity(SeverityLevel.BLOCKER)
    @Description("This end-to-end scenario covers: logging in, verifying an item in the wishlist, adding it to the cart, proceeding to checkout, filling shipping details, attempting an invalid discount code, and successfully placing the order.")
    @Link(name = "Website", url = MagnetoConstants.PAGE_URL)
    public void purchaseItemFromWishList() {
        purchaseSteps = new PurchaseSteps(page);
        page.navigate(MagnetoConstants.PAGE_URL);

        purchaseSteps.login(MagnetoConstants.EMAIL, MagnetoConstants.PASSWORD)
                .verifyItemInWishlist(MagnetoConstants.PRODUCT_TO_FAVORITE)
                .logout()
                .login(MagnetoConstants.EMAIL, MagnetoConstants.PASSWORD) // Re-login to ensure state persistence
                .verifyItemInWishlist(MagnetoConstants.PRODUCT_TO_FAVORITE);

        purchaseSteps.selectItemFromWishlist()
                .configureAndAddToCart()
                .navigateToCheckout()
                .fillShippingDetailsAndProceed(
                        MagnetoConstants.STREET_ADDRESS, MagnetoConstants.CITY, MagnetoConstants.COUNTRY,
                        MagnetoConstants.STATE_PROVINCE, MagnetoConstants.POST_CODE, MagnetoConstants.PHONE_NUMBER
                );

        purchaseSteps.validateReviewAndPaymentsPage(
                MagnetoConstants.FIRST_NAME, MagnetoConstants.LAST_NAME,
                MagnetoConstants.STREET_ADDRESS, MagnetoConstants.CITY
        );

        purchaseSteps.attemptInvalidDiscount(
                MagnetoConstants.INVALID_DISCOUNT_CODE,
                MagnetoConstants.DISCOUNT_ERROR_MESSAGE
        );

        purchaseSteps.placeOrderAndVerifySuccess();
    }
}