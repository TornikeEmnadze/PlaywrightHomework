package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    public final Locator createAccountButton;
    public final Locator pageTitle;

    public LoginPage(Page page) {
        this.createAccountButton = page.locator(".login-container .action.create.primary");
        this.pageTitle = page.locator(".page-title");
    }
}