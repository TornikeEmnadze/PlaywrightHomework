package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.CartSteps;
import ge.tbc.testautomation.steps.PurchaseSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = {"E2E - Guest User Purchase of a Specific Product [NEW-T3]"})
public class GuestCheckoutE2ETest extends BaseTest {
    CartSteps cartSteps;
    PurchaseSteps purchaseSteps;

    @BeforeClass
    public void setupSteps() {
        this.cartSteps = new CartSteps(page);
        this.purchaseSteps = new PurchaseSteps(page);
    }

    @Test(priority = 1, description = "Step 1 & 2: Navigate to homepage and search for product")
    public void step1_navigateAndSearch() {
        cartSteps.navigateToHomePage(MagnetoConstants.PAGE_URL)
                .search(MagnetoConstants.GUEST_PRODUCT_TO_BUY);
    }

    @Test(priority = 2, description = "Step 3 & 4: Select product from search results", dependsOnMethods = "step1_navigateAndSearch")
    public void step2_selectProductFromResult() {
        cartSteps.selectProductFromResultsAndVerify(MagnetoConstants.GUEST_PRODUCT_TO_BUY);
    }

    @Test(priority = 3, description = "Step 5, 6 & 7: Select size/color and add product to cart", dependsOnMethods = "step2_selectProductFromResult")
    public void step3_configureAndAddToCart() {
        cartSteps.configureProductAndAddToCart("M", "Green", MagnetoConstants.GUEST_PRODUCT_TO_BUY);
    }

    @Test(priority = 4, description = "Step 8: Proceed to checkout", dependsOnMethods = "step3_configureAndAddToCart")
    public void step4_proceedToCheckout() {
        purchaseSteps.navigateToCheckout();
    }

    @Test(priority = 5, description = "Step 9, 10 & 11: Fill guest shipping details and proceed to payments", dependsOnMethods = "step4_proceedToCheckout")
    public void step5_fillShippingAndProceed() {
        String guestEmail = String.format("guest_user_%d@example.com", System.currentTimeMillis());
        purchaseSteps.fillGuestShippingDetailsAndProceed(
                guestEmail,
                MagnetoConstants.GUEST_FIRST_NAME,
                MagnetoConstants.GUEST_LAST_NAME,
                MagnetoConstants.GUEST_STREET_ADDRESS,
                MagnetoConstants.GUEST_CITY,
                MagnetoConstants.GUEST_COUNTRY,
                MagnetoConstants.GUEST_STATE_PROVINCE,
                MagnetoConstants.GUEST_POST_CODE,
                MagnetoConstants.GUEST_PHONE_NUMBER
        );
    }

    @Test(priority = 6, description = "Step 12 & 13: Place the order and verify success", dependsOnMethods = "step5_fillShippingAndProceed")
    public void step6_placeOrderAndVerify() {
        purchaseSteps.placeOrderAndVerifySuccess();
    }
}