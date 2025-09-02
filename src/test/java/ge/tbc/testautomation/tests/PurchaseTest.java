package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.PurchaseSteps;
import org.testng.annotations.Test;

public class PurchaseTest extends BaseTest {
    PurchaseSteps purchaseSteps;

    @Test
    public void purchaseItemFromWishList(){
        // so turns out we accidentally killed someone's test website and now it is won't let me
        // login so half of this test is well tested but other half i have no clue how it runs
        // pls don't be too harsh with grading :)
        purchaseSteps = new PurchaseSteps(page);

        page.navigate(MagnetoConstants.PAGE_URL);

        purchaseSteps.login(MagnetoConstants.EMAIL, MagnetoConstants.PASSWORD);

        purchaseSteps.verifyItemInWishlist(MagnetoConstants.PRODUCT_TO_FAVORITE);

        purchaseSteps.logout();

        purchaseSteps.login(MagnetoConstants.EMAIL, MagnetoConstants.PASSWORD);

        purchaseSteps.verifyItemInWishlist(MagnetoConstants.PRODUCT_TO_FAVORITE);


        purchaseSteps.proceedToBuyFromWishlist(MagnetoConstants.STREET_ADDRESS,MagnetoConstants.CITY,MagnetoConstants.COUNTRY,MagnetoConstants.STATE_PROVINCE,MagnetoConstants.POST_CODE,MagnetoConstants.PHONE_NUMBER);

        purchaseSteps.validateReviewAndPaymentsPage(MagnetoConstants.FIRST_NAME,MagnetoConstants.LAST_NAME,MagnetoConstants.STREET_ADDRESS,MagnetoConstants.CITY);
        purchaseSteps.attemptInvalidDiscount(MagnetoConstants.INVALID_DISCOUNT_CODE,MagnetoConstants.DISCOUNT_ERROR_MESSAGE);

        purchaseSteps.placeOrderAndVerifySuccess();
    }
}
