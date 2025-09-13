package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.ProductSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("E-Commerce Functionality")
@Feature("Product Inventory")
public class ProductStockTest extends BaseTest {

    @Test(description = "Verify that an out-of-stock item cannot be added to the shopping cart")
    @Story("Out-of-stock product handling")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test selects a product known to be out of stock, attempts to add it to the cart, and verifies that the correct error message is displayed.")
    @Link(name = "Website", url = MagnetoConstants.PAGE_URL)
    public void outOfStockOfferTest() {
        ProductSteps productSteps = new ProductSteps(page);
        productSteps
                .navigateToHomePage(MagnetoConstants.PAGE_URL)
                .SelectOutOfStockItem()
                .verifyItemNotAddingToCart(MagnetoConstants.EXPECTED_ERROR_MESSAGE);
    }
}