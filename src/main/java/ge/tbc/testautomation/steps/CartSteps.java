package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.HomePage;
import ge.tbc.testautomation.pages.ProductPage;
import ge.tbc.testautomation.pages.SearchResultPage;
import ge.tbc.testautomation.pages.ShoppingCartPage;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class CartSteps {
    private Page page;
    private HomePage homePage;
    private SearchResultPage searchResultsPage;
    private ProductPage productPage;
    private ShoppingCartPage shoppingCartPage;

    public CartSteps(Page page){
        this.page = page;
        this.homePage = new HomePage(page);
        this.searchResultsPage = new SearchResultPage(page);
        this.productPage = new ProductPage(page);
        this.shoppingCartPage = new ShoppingCartPage(page);
    }


    public CartSteps navigateToHomePage(String url){
        page.navigate(url);
        return this;
    }

    public CartSteps search(String searchText){
        homePage.searchBar.fill(searchText);
        page.keyboard().press("Enter");
        return this;
    }

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

    public CartSteps verifyItemInCart(Map<String, String> expectedProduct) {
        homePage.cartIcon.click();
        homePage.viewCartLink.click();

        String actualName = shoppingCartPage.productNameInCart.innerText();
        String actualPrice = shoppingCartPage.productPriceInCart.innerText();

        Assert.assertEquals(actualName, expectedProduct.get("name"), "Cart item name mismatch.");
        Assert.assertEquals(actualPrice, expectedProduct.get("price"), "Cart item price mismatch.");
        return this;
    }

    public CartSteps deleteItemFromCart() {
        shoppingCartPage.deleteItemButton.waitFor();
        page.waitForTimeout(1000);

        shoppingCartPage.deleteItemButton.click();

        shoppingCartPage.emptyCartMessage.waitFor();

        Assert.assertTrue(shoppingCartPage.emptyCartMessage.isVisible(), "The cart is not empty after deletion.");
        return this;
    }



}
