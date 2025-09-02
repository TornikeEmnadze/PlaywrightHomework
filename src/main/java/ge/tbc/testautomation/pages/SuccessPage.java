package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SuccessPage {
    public Locator pageTitle;
    public Locator successMessage;
    public Locator orderNumber;

    public SuccessPage(Page page) {
        this.pageTitle = page.locator(".base");
        this.successMessage = page.locator(".checkout-success");
        this.orderNumber = page.locator(".order-number strong");
    }
}
