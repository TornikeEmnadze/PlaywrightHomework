package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.constants.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.ColorChangeSteps;
import org.testng.annotations.Test;

public class ColorChangeTest extends BaseTest {
    @Test
    void colorChangeOnHomePageShouldUpdateImage() {
        ColorChangeSteps colorChangeSteps = new ColorChangeSteps(page);
        colorChangeSteps
                .navigateToPage(MagnetoConstants.PAGE_URL)
                .validateColorChange(3);
    }
}
