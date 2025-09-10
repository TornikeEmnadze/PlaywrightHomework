package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.pages.HomePage;

import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MobileNavigationSteps {
    Page page;
    HomePage homePage;

    public MobileNavigationSteps(Page page){
        this.page=page;
        this.homePage=new HomePage(page);
    }

    public MobileNavigationSteps openHomePage(String url){
        page.navigate(url);

        return this;
    }

    public MobileNavigationSteps switchToMobileView(){
        page.setViewportSize(390,844);

        assertThat(homePage.burgerMenuButton).isVisible();
        return this;
    }

    public MobileNavigationSteps validateLinksHaveMovedToBurgerMenu(){
        assertThat(homePage.desktopHeaderPanel.getByText("Sign In")).isHidden();
        assertThat(homePage.desktopHeaderPanel.getByText("Create an Account")).isHidden();
        assertThat(homePage.desktopHeaderPanel.getByText("Women")).isHidden();
        page.waitForTimeout(2000);
        homePage.burgerMenuButton.click();
        assertThat(homePage.mobileMenuPanel).isVisible();

        List<String> mainNavLinks = Arrays.asList(
                "What's New", "Women", "Men", "Gear", "Training", "Sale"
        );

        for (String linkText : mainNavLinks) {
            Locator linkLocator = homePage.mobileMenuPanel.getByText(linkText, new Locator.GetByTextOptions().setExact(true));
            assertThat(linkLocator).isVisible();
        }

        homePage.accountMenuHeader.click();
        assertThat(homePage.mobileMenuPanel.getByText("Sign In")).isVisible();
        assertThat(homePage.mobileMenuPanel.getByText("Create an Account")).isVisible();


        return this;
    }


}
