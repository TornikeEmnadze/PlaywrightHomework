package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.MobileNavigationSteps;
import org.testng.annotations.Test;

public class MobileNavigationTest extends BaseTest {

    MobileNavigationSteps mobileNavigationSteps;

    @Test
    public void mobileNavigationTest(){
        mobileNavigationSteps=new MobileNavigationSteps(page);

        mobileNavigationSteps.openHomePage(MagnetoConstants.PAGE_URL)
                .switchToMobileView()
                .validateLinksHaveMovedToBurgerMenu();
    }
}
