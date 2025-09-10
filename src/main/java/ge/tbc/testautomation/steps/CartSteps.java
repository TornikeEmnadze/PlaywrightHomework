package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.pages.HomePage;
import ge.tbc.testautomation.pages.ProductPage;
import ge.tbc.testautomation.pages.SearchResultPage;
import ge.tbc.testautomation.pages.ShoppingCartPage;
import io.qameta.allure.Step;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartSteps {
    private final Page page;
    private final HomePage homePage;
    private final SearchResultPage searchResultsPage;
    private final ProductPage productPage;
    private final ShoppingCartPage shoppingCartPage;

    public CartSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
        this.searchResultsPage = new SearchResultPage(page);
        this.productPage = new ProductPage(page);
        this.shoppingCartPage = new ShoppingCartPage(page);
    }

    @Step("Navigate to Home Page: {0}")
    public CartSteps navigateToHomePage(String url) {
        page.navigate(url);
        System.out.println("I AM A CHANGE MEANWHILE");
        return this;
    }

    @Step("Search for: {0}")
    public CartSteps search(String searchText) {
        homePage.searchBar.fill(searchText);
        page.keyboard().press("Enter");
        return this;
    }

    @Step("Select the first item from search results and add it to the cart")
    public Map<String, String> selectFirstItemAndAddToCart() {
        searchResultsPage.productItemLinks.first().click();
        String name = productPage.productName.innerText();
        String price = productPage.productPrice.innerText();
        Map<String, String> productDetails = new HashMap<>();
        productDetails.put("name", name);
        productDetails.put("price", price);
        productPage.sizeSwatch.click();
        productPage.colorSwatches.first().click();
        productPage.addToCartButton.click();
        productPage.successMessageAlert.waitFor();
        return productDetails;
    }

    @Step("Verify item in cart matches expected product details")
    public CartSteps verifyItemInCart(Map<String, String> expectedProduct) {
        homePage.cartIcon.click();
        homePage.viewCartLink.click();
        String actualName = shoppingCartPage.productNameInCart.innerText();
        String actualPrice = shoppingCartPage.productPriceInCart.innerText();
        Assert.assertEquals(actualName, expectedProduct.get("name"), "Cart item name mismatch.");
        Assert.assertEquals(actualPrice, expectedProduct.get("price"), "Cart item price mismatch.");
        return this;
    }

    @Step("Delete item from the cart and verify cart becomes empty")
    public CartSteps deleteItemFromCart() {
        shoppingCartPage.deleteItemButton.waitFor();
        page.waitForTimeout(1000);
        shoppingCartPage.deleteItemButton.click();
        shoppingCartPage.emptyCartMessage.waitFor();
        Assert.assertTrue(shoppingCartPage.emptyCartMessage.isVisible(), "The cart is not empty after deletion.");
        return this;
    }

    @Step("Select product '{0}' from results and verify page")
    public CartSteps selectProductFromResultsAndVerify(String productName) {
        searchResultsPage.productItemLinks.first().click();
        productPage.productName.waitFor();
        assertThat(productPage.productName).hasText(productName);
        return this;
    }

    @Step("Configure product '{2}' with size '{0}' and color '{1}' and add to cart")
    public CartSteps configureProductAndAddToCart(String size, String color, String productName) {
        page.locator(".swatch-option.text[aria-label='" + size + "']").click();
        page.locator(".swatch-option.color[aria-label='" + color + "']").click();
        productPage.addToCartButton.click();
        productPage.successMessageAlert.waitFor();
        assertThat(productPage.successMessageAlert).containsText("You added " + productName + " to your");
        return this;
    }

    @Step("Add simple product '{0}' to cart")
    public CartSteps addSimpleProductToCart(String productName) {
        search(productName);
        searchResultsPage.productItemLinks.first().click();
        productPage.productName.waitFor();
        productPage.addToCartButton.click();
        productPage.successMessageAlert.waitFor();
        assertThat(productPage.successMessageAlert).containsText("You added " + productName + " to your");
        return this;
    }

    @Step("Navigate to the Shopping Cart page")
    public CartSteps navigateToCartPage() {
        homePage.cartIcon.click();
        homePage.viewCartLink.click();
        shoppingCartPage.productNameInCart.first().waitFor();
        return this;
    }

    @Step("Verify item '{0}' is present in the cart")
    public CartSteps verifyItemIsPresent(String productName) {
        assertThat(page.locator("td.col.item", new Page.LocatorOptions().setHasText(productName))).isVisible();
        return this;
    }

    @Step("Update quantity of item '{0}' to '{1}'")
    public CartSteps updateItemQuantity(String productName, int quantity) {
        Locator productRow = page.locator("tr.item-info", new Page.LocatorOptions().setHasText(productName));
        productRow.locator("input[title='Qty']").fill(String.valueOf(quantity));
        shoppingCartPage.updateCartButton.click();
        page.locator(".loading-mask").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        return this;
    }
}