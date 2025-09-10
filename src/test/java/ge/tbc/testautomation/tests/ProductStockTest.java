package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.ProductSteps;
import org.testng.annotations.Test;

public class ProductStockTest extends BaseTest {

    @Test
    public void outOfStockOfferTest() {
        ProductSteps productSteps = new ProductSteps(page);

        productSteps
                .navigateToHomePage(MagnetoConstants.PAGE_URL)
                .SelectOutOfStockItem()
                .verifyItemNotAddingToCart(MagnetoConstants.EXPECTED_ERROR_MESSAGE);
    }
}
