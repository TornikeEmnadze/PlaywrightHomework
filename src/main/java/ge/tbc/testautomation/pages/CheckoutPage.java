package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPage {
    public Locator shippingMethodRadio;
    public Locator nextButton;
    public Locator streetInput;
    public Locator cityInput;
    public Locator countrySelect;
    public Locator regionInput;
    public Locator postCodeInput;
    public Locator phoneInput;

    public CheckoutPage(Page page) {
        this.shippingMethodRadio = page.locator("input[name='ko_unique_1']").first();
        this.nextButton = page.locator("button[data-role='opc-continue']");

        this.streetInput = page.locator("input[name='street[0]']");
        this.cityInput = page.locator("input[name='city']");
        this.countrySelect = page.locator("select[name='country_id']");
        this.regionInput = page.locator("input[name='region']");
        this.postCodeInput = page.locator("input[name='postcode']");
        this.phoneInput = page.locator("input[name='telephone']");
    }
}
