package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class RegistrationPage {
    public Locator firstNameInput;
    public Locator lastNameInput;
    public Locator maleRadioButton;
    public Locator femaleRadioButton;
    public Locator address1;
    public Locator address2;
    public Locator city;
    public Locator contact1;
    public Locator contact2;
    public Locator registerButton;
    public Locator modelDropdown;


    public RegistrationPage(Page page) {
        this.firstNameInput = page.locator("input[value='First Name']");
        this.lastNameInput =  page.locator("input[value='Last Name']");
        this.maleRadioButton = page.locator("input[value='male']");
        this.femaleRadioButton = page.locator("input[value='female']");
        this.address1 = page.locator("input[value='Address1']");
        this.address2 = page.locator("input[value='Address2']");
        this.city = page.locator("input[value='City']");
        this.contact1 = page.locator("input[value='Contact1']");
        this.contact2 = page.locator("input[value='Contact2']");
        this.registerButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("REGISTER"));
        this.modelDropdown = page.locator("select[name='model']");
    }
}