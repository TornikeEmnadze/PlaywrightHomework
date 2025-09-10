package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.HomePage;
import ge.tbc.testautomation.pages.ProductPage;
import ge.tbc.testautomation.pages.WomenSection;
import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ReviewValidationSteps {
    Page page;
    HomePage homePage;
    WomenSection womenSection;
    ProductPage productPage;

    public ReviewValidationSteps(Page page){
        this.page=page;
        this.homePage=new HomePage(page);
        this.productPage=new ProductPage(page);
        this.womenSection=new WomenSection(page);
    }

    public ReviewValidationSteps openHomePage(String url){
        page.navigate(url);
        return this;
    }

    public ReviewValidationSteps navigateToWomenSection(){
        homePage.womenPageLink.click();
        return this;
    }

    public ReviewValidationSteps findFirstElementWithReview(){
        Locator productWithReview= womenSection.allProductItems.filter(new Locator.FilterOptions().setHas(womenSection.reviewLink))
                .first();

        assertThat(productWithReview).isVisible();

        Locator reviewLink = productWithReview.locator(".reviews-actions a");
        String reviewText = reviewLink.textContent().trim();

        Pattern pattern = Pattern.compile("^\\d+");
        Matcher matcher = pattern.matcher(reviewText);

        Assert.assertTrue(matcher.find());

        int expectedCount = Integer.parseInt(matcher.group(0));

        reviewLink.click();
        page.waitForSelector(".items .review-items");

        int actualCount=productPage.reviewItems.count();

        Assert.assertEquals(actualCount,expectedCount);

        return this;
    }
}
