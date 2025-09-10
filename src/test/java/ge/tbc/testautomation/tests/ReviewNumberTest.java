package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.ReviewValidationSteps;
import org.testng.annotations.Test;

public class ReviewNumberTest extends BaseTest {
    ReviewValidationSteps reviewValidationSteps;

    @Test
    void reviewNumberTest(){
        reviewValidationSteps=new ReviewValidationSteps(page);
        reviewValidationSteps.openHomePage(MagnetoConstants.PAGE_URL)
                .navigateToWomenSection()
                .findFirstElementWithReview();
    }
}
