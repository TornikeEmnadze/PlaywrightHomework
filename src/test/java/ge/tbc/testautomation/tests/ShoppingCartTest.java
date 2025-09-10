package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.CartSteps;
import org.testng.annotations.Test;

import java.util.Map;

public class ShoppingCartTest extends BaseTest {
    private static Map<String, String> addedProduct;

    @Test(priority = 1, description = "addToCartTest: Search for an item and validate it is added to the cart.")
    public void addToCartTest(){
        CartSteps cartSteps = new CartSteps(page);

        cartSteps.navigateToHomePage(MagnetoConstants.PAGE_URL)
                .search(MagnetoConstants.TEXT_TO_SEARCH);

        addedProduct=cartSteps.selectFirstItemAndAddToCart();
        cartSteps.verifyItemInCart(addedProduct);
    }

    @Test(priority = 2, description = "deleteFromCart: Delete the item and verify the cart is empty.", dependsOnMethods = "addToCartTest")
    public void deleteFromCartTest(){
        CartSteps cartSteps=new CartSteps(page);

        cartSteps.deleteItemFromCart();
    }
}
