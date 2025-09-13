package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.MagnetoConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.ColorChangeSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Frontend Features")
@Feature("Product Display")
public class ColorChangeTest extends BaseTest {

    @Test(description = "Verify that changing a product's color swatch updates its image on the homepage")
    @Story("User can preview different product colors")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test hovers over several products on the homepage, clicks a color swatch, and verifies that the product's main image is updated accordingly.")
    @Link(name = "Website", url = MagnetoConstants.PAGE_URL)
    void colorChangeOnHomePageShouldUpdateImage() {
        ColorChangeSteps colorChangeSteps = new ColorChangeSteps(page);
        colorChangeSteps
                .navigateToPage(MagnetoConstants.PAGE_URL)
                .validateColorChange(3);
    }
}