package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.CartSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import java.util.Map;

@Epic("E-Commerce Functionality")
@Feature("Shopping Cart")
public class ShoppingCartTest extends BaseTest {
    private static Map<String, String> addedProduct;

    @Test(priority = 1, description = "Search for an item and validate it is correctly added to the cart")
    @Story("User can add and remove items from the cart")
    @Severity(SeverityLevel.BLOCKER)
    @Description("This test searches for a specific product, adds it to the shopping cart, and then verifies that the product's name and price in the cart match the details from the product page.")
    @Link(name = "Website", url = MagnetoConstants.PAGE_URL)
    public void addToCartTest() {
        CartSteps cartSteps = new CartSteps(page);
        cartSteps.navigateToHomePage(MagnetoConstants.PAGE_URL)
                .search(MagnetoConstants.TEXT_TO_SEARCH);
        addedProduct = cartSteps.selectFirstItemAndAddToCart();
        cartSteps.verifyItemInCart(addedProduct);
    }

    @Test(priority = 2, description = "Delete the item from the cart and verify the cart becomes empty", dependsOnMethods = "addToCartTest")
    @Story("User can add and remove items from the cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test removes the previously added item from the shopping cart and confirms that the cart displays an 'empty' message.")
    public void deleteFromCartTest() {
        CartSteps cartSteps = new CartSteps(page);
        cartSteps.deleteItemFromCart();
    }
}