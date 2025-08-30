package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.ProductPage;
import org.testng.Assert;

public class ProductSteps {
    Page page;
    ProductPage productPage;

    public ProductSteps(Page page) {
        this.page = page;
        this.productPage = new ProductPage(page);
    }

    public ProductSteps navigateToHomePage(String url) {
        page.navigate(url);

        return this;
    }

    public ProductSteps SelectOutOfStockItem() {
        System.out.println(productPage.outOfStockItem.allInnerTexts());
        productPage.outOfStockItem.click();
        return this;
    }

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