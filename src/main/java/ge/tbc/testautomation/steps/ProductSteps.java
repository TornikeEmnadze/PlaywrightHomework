package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.ProductPage;
import io.qameta.allure.Step;
import org.testng.Assert;

public class ProductSteps {
    Page page;
    ProductPage productPage;

    public ProductSteps(Page page) {
        this.page = page;
        this.productPage = new ProductPage(page);
    }

    @Step("Navigate to Home Page: {0}")
    public ProductSteps navigateToHomePage(String url) {
        page.navigate(url);
        return this;
    }

    @Step("Select the out-of-stock item 'Radiant Tee'")
    public ProductSteps SelectOutOfStockItem() {
        productPage.outOfStockItem.click();
        return this;
    }

    @Step("Verify that adding the item to cart fails with message: '{0}'")
    public ProductSteps verifyItemNotAddingToCart(String expectedErrorMessage) {
        productPage.xsSizeSwatch.click();
        productPage.blueColorSwatch.click();
        productPage.addToCartButton.click();
        productPage.outOfStockMessage.waitFor();
        Assert.assertTrue(productPage.outOfStockMessage.isVisible());
        String actualErrorMessage = productPage.outOfStockMessage.innerText();
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage));
        return this;
    }
}