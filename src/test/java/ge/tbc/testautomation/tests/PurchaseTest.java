package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.PurchaseSteps;
import org.testng.annotations.Test;

public class PurchaseTest extends BaseTest {
    PurchaseSteps purchaseSteps;

    @Test
    public void purchaseItemFromWishList() {
        purchaseSteps = new PurchaseSteps(page);

        page.navigate(MagnetoConstants.PAGE_URL);

        purchaseSteps.login(MagnetoConstants.EMAIL, MagnetoConstants.PASSWORD)
                .verifyItemInWishlist(MagnetoConstants.PRODUCT_TO_FAVORITE)
                .logout()
                .login(MagnetoConstants.EMAIL, MagnetoConstants.PASSWORD)
                .verifyItemInWishlist(MagnetoConstants.PRODUCT_TO_FAVORITE);

        purchaseSteps.selectItemFromWishlist()
                .configureAndAddToCart()
                .navigateToCheckout()
                .fillShippingDetailsAndProceed(
                        MagnetoConstants.STREET_ADDRESS,
                        MagnetoConstants.CITY,
                        MagnetoConstants.COUNTRY,
                        MagnetoConstants.STATE_PROVINCE,
                        MagnetoConstants.POST_CODE,
                        MagnetoConstants.PHONE_NUMBER
                );
        purchaseSteps.validateReviewAndPaymentsPage(
                MagnetoConstants.FIRST_NAME,
                MagnetoConstants.LAST_NAME,
                MagnetoConstants.STREET_ADDRESS,
                MagnetoConstants.CITY
        );

        purchaseSteps.attemptInvalidDiscount(
                MagnetoConstants.INVALID_DISCOUNT_CODE,
                MagnetoConstants.DISCOUNT_ERROR_MESSAGE
        );

        purchaseSteps.placeOrderAndVerifySuccess();
    }
}