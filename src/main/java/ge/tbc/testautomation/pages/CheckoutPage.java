package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CheckoutPage {
    public Locator shippingMethodRadio;
    public Locator nextButton;
    public Locator streetInput;
    public Locator cityInput;
    public Locator countrySelect;
    public Locator regionInput;
    public Locator postCodeInput;
    public Locator phoneInput;
    public Locator loadingMask;
    public Locator emailInput;
    public Locator addNewAddressBtn;
    public Locator shipHereBtn;

    public CheckoutPage(Page page) {
        this.shippingMethodRadio = page.locator("input[name='ko_unique_1']").first();
        this.nextButton = page.locator("button[data-role='opc-continue']");

        this.streetInput = page.locator("input[name='street[0]']");
        this.cityInput = page.locator("input[name='city']");
        this.countrySelect = page.locator("select[name='country_id']");
        this.regionInput = page.locator("input[name='region']");
        this.postCodeInput = page.locator("input[name='postcode']");
        this.phoneInput = page.locator("input[name='telephone']");
        this.loadingMask = page.locator(".loading-mask");
        this.emailInput = page.locator("#customer-email-fieldset #customer-email");
        this.addNewAddressBtn=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("New Address"));
        this.shipHereBtn=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Ship here"));
    }
}
