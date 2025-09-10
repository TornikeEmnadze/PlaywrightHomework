package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.HomePage;
import org.testng.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ColorChangeSteps {
    Page page;
    HomePage homePage;

    public ColorChangeSteps(Page page){
        this.page=page;
        this.homePage=new HomePage(page);
    }

    public ColorChangeSteps navigateToPage(String url){
        page.navigate(url);
        return this;
    }

    public ColorChangeSteps validateColorChange(int numToTest){
        Random random= new Random();
        homePage.hotSellerItems.first().waitFor();
        List<Locator> hotSellerItems=homePage.hotSellerItems.all();
        Collections.shuffle(hotSellerItems); // shuffle items in list to pick "numToTest" random items
        List<Locator> randomItems=hotSellerItems.subList(0,numToTest);

        for(Locator item:randomItems){
            item.hover();
            Locator productImage=item.locator(homePage.productImg);
            List<Locator> colorSwatches=item.locator(homePage.colorChanger).all();

            if(colorSwatches.size()>1){
                String initialImgSrc=productImage.getAttribute("src");

                colorSwatches.get(2).click();

                // waiting function to wait till item img changes
                page.waitForFunction(
                        "(args) => { " +
                                "  const [element, initialSrc] = args; " +
                                "  return element.getAttribute('src') !== initialSrc; " +
                                "}",
                        new Object[]{productImage.elementHandle(), initialImgSrc}
                );

                String newImgSrc=productImage.getAttribute("src");

                Assert.assertNotEquals(initialImgSrc,newImgSrc);
            }
        }


        return this;
    }
}
