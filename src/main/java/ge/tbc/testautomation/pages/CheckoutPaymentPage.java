package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPaymentPage {
    public Locator shippingAddressInfo;
    public Locator shippingCostSummary;
    public Locator orderTotalSummary;
    public Locator discountCodeToggle;
    public Locator discountCodeInput;
    public Locator applyDiscountButton;
    public Locator discountErrorMessage;
    public Locator placeOrderButton;

    public CheckoutPaymentPage(Page page) {
        this.shippingAddressInfo = page.locator(".shipping-information-content");
        this.shippingCostSummary = page.locator("tr.totals-shipping .price");
        this.orderTotalSummary = page.locator("tr.grand.totals .price");
        this.discountCodeToggle = page.locator("#block-discount-heading");
        this.discountCodeInput = page.locator("#discount-code");
        this.applyDiscountButton = page.locator("button[value='Apply Discount']");
        this.discountErrorMessage = page.locator(".message-error");
        this.placeOrderButton = page.locator("button[title='Place Order']");
    }
}
