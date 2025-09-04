package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.HomePage;
import io.qameta.allure.Step;
import org.testng.Assert;
import java.util.Collections;
import java.util.List;

public class ColorChangeSteps {
    Page page;
    HomePage homePage;

    public ColorChangeSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
    }

    @Step("Navigate to page: {0}")
    public ColorChangeSteps navigateToPage(String url) {
        page.navigate(url);
        return this;
    }

    @Step("Validate color change for {0} random items on the homepage")
    public ColorChangeSteps validateColorChange(int numToTest) {
        homePage.hotSellerItems.first().waitFor();
        List<Locator> hotSellerItems = homePage.hotSellerItems.all();
        Collections.shuffle(hotSellerItems);
        List<Locator> randomItems = hotSellerItems.subList(0, numToTest);

        for (Locator item : randomItems) {
            item.hover();
            Locator productImage = item.locator(homePage.productImg);
            List<Locator> colorSwatches = item.locator(homePage.colorChanger).all();

            if (colorSwatches.size() > 1) {
                String initialImgSrc = productImage.getAttribute("src");
                colorSwatches.get(1).click(); // Use index 1 for consistent testing
                page.waitForFunction(
                        "(args) => { const [element, initialSrc] = args; return element.getAttribute('src') !== initialSrc; }",
                        new Object[]{productImage.elementHandle(), initialImgSrc}
                );
                String newImgSrc = productImage.getAttribute("src");
                Assert.assertNotEquals(initialImgSrc, newImgSrc, "Product image source did not change after clicking a color swatch.");
            }
        }
        return this;
    }
}