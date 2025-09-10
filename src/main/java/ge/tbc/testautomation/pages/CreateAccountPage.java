package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateAccountPage {
    public final Locator firstNameInput;
    public final Locator lastNameInput;
    public final Locator emailInput;
    public final Locator passwordInput;
    public final Locator confirmPasswordInput;
    public final Locator createAccountButton;

    public CreateAccountPage(Page page) {
        this.firstNameInput = page.locator("#firstname");
        this.lastNameInput = page.locator("#lastname");
        this.emailInput = page.locator("#email_address");
        this.passwordInput = page.locator("#password");
        this.confirmPasswordInput = page.locator("#password-confirmation");
        this.createAccountButton = page.locator("button[title='Create an Account']");
    }
}