package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.ReviewValidationSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Frontend Features")
@Feature("Product Reviews")
public class ReviewNumberTest extends BaseTest {
    ReviewValidationSteps reviewValidationSteps;

    @Test(description = "Validate that the review count on a product listing matches the number of reviews on the product page")
    @Story("Review count consistency")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test navigates to the Women's section, finds the first product that has reviews, and compares the review count link with the actual number of reviews displayed on the product's detail page.")
    @Link(name = "Website", url = MagnetoConstants.PAGE_URL)
    void reviewNumberTest() {
        reviewValidationSteps = new ReviewValidationSteps(page);
        reviewValidationSteps.openHomePage(MagnetoConstants.PAGE_URL)
                .navigateToWomenSection()
                .findFirstElementWithReview();
    }
}