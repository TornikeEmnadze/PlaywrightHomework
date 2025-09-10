package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.CartSteps;
import ge.tbc.testautomation.steps.PurchaseSteps;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("E-Commerce Functionality")
@Feature("Shopping Cart Management")
@Test(groups = {"E2E - Registered User Modifies Cart and Completes Purchase [NEW-T4]"})
public class CartManagementE2ETest extends BaseTest {
    PurchaseSteps purchaseSteps;
    CartSteps cartSteps;

    @BeforeClass
    public void setupSteps() {
        this.purchaseSteps = new PurchaseSteps(page);
        this.cartSteps = new CartSteps(page);
    }

    @Test(priority = 1, description = "Step 1: Navigate and log in")
    @Story("Registered user cart modification and purchase")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Navigate to the homepage and log in as a registered user.")
    public void step1_login() {
        cartSteps.navigateToHomePage(MagnetoConstants.PAGE_URL);
        purchaseSteps.login(MagnetoConstants.EMAIL, MagnetoConstants.PASSWORD);
    }

    @Test(priority = 2, description = "Step 2: Add one simple product to the cart", dependsOnMethods = "step1_login")
    @Story("Registered user cart modification and purchase")
    @Severity(SeverityLevel.CRITICAL)
    public void step2_addProduct() {
        cartSteps.addSimpleProductToCart(MagnetoConstants.CART_MGMT_PRODUCT);
    }

    @Test(priority = 3, description = "Step 3: Navigate to cart and verify the item", dependsOnMethods = "step2_addProduct")
    @Story("Registered user cart modification and purchase")
    @Severity(SeverityLevel.NORMAL)
    public void step3_navigateToCartAndVerify() {
        cartSteps.navigateToCartPage()
                .verifyItemIsPresent(MagnetoConstants.CART_MGMT_PRODUCT);
    }

    @Test(priority = 4, description = "Step 4: Update the quantity of the item", dependsOnMethods = "step3_navigateToCartAndVerify")
    @Story("Registered user cart modification and purchase")
    @Severity(SeverityLevel.CRITICAL)
    public void step4_updateQuantity() {
        cartSteps.updateItemQuantity(MagnetoConstants.CART_MGMT_PRODUCT, 3);
    }

    @Test(priority = 5, description = "Step 5: Proceed to checkout", dependsOnMethods = "step4_updateQuantity")
    @Story("Registered user cart modification and purchase")
    @Severity(SeverityLevel.CRITICAL)
    public void step5_checkout() {
        purchaseSteps.navigateToCheckout();
        purchaseSteps.fillShippingDetailsAndProceed(
                MagnetoConstants.STREET_ADDRESS, MagnetoConstants.CITY, MagnetoConstants.COUNTRY,
                MagnetoConstants.STATE_PROVINCE, MagnetoConstants.POST_CODE, MagnetoConstants.PHONE_NUMBER
        );
    }

    @Test(priority = 6, description = "Step 6: Place the order and verify success", dependsOnMethods = "step5_checkout")
    @Story("Registered user cart modification and purchase")
    @Severity(SeverityLevel.BLOCKER)
    public void step6_placeOrderAndVerify() {
        purchaseSteps.placeOrderAndVerifySuccess();
    }
}