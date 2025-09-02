package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    public final Locator createAccountButton;
    public final Locator pageTitle;
    public Locator emailInput;
    public Locator passwordInput;
    public Locator signInButton;

    public LoginPage(Page page) {
        this.createAccountButton = page.locator(".login-container .action.create.primary");
        this.pageTitle = page.locator(".page-title");

        this.emailInput = page.locator("#email");
        this.passwordInput = page.locator("input[name='login[password]']");
        this.signInButton = page.locator("button.action.login.primary");
    }
}