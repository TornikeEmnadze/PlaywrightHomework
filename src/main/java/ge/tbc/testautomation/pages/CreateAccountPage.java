package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateAccountPage {
    public Locator firstNameInput;
    public Locator lastNameInput;
    public Locator emailInput;
    public Locator passwordInput;
    public Locator confirmPasswordInput;
    public Locator createAccountButton;

    public CreateAccountPage(Page page) {
        this.firstNameInput = page.locator("#firstname");
        this.lastNameInput = page.locator("#lastname");
        this.emailInput = page.locator("#email_address");
        this.passwordInput = page.locator("#password");
        this.confirmPasswordInput = page.locator("#password-confirmation");
        this.createAccountButton = page.locator("button[title='Create an Account']");
    }
}