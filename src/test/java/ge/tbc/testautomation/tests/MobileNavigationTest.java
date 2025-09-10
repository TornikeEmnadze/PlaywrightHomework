package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.MobileNavigationSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Frontend Features")
@Feature("Responsive UI")
public class MobileNavigationTest extends BaseTest {
    MobileNavigationSteps mobileNavigationSteps;

    @Test(description = "Verify that the site navigation adapts correctly to a mobile viewport")
    @Story("Mobile burger menu functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test switches the browser to a mobile viewport, confirms the burger menu appears, and validates that primary navigation links have moved inside it.")
    @Link(name = "Website", url = MagnetoConstants.PAGE_URL)
    public void mobileNavigationTest() {
        mobileNavigationSteps = new MobileNavigationSteps(page);
        mobileNavigationSteps.openHomePage(MagnetoConstants.PAGE_URL)
                .switchToMobileView()
                .validateLinksHaveMovedToBurgerMenu();
    }
}