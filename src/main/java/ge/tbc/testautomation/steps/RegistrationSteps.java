package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import ge.tbc.testautomation.data.TechcanvassConstants;
import ge.tbc.testautomation.pages.RegistrationPage;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegistrationSteps {
    private final Page page;
    private final RegistrationPage registrationPage;

    public RegistrationSteps(Page page) {
        this.page = page;
        this.registrationPage = new RegistrationPage(page);
    }

    public RegistrationSteps navigateToRegistrationPage(String url) {
        page.navigate(url);
        return this;
    }

    public RegistrationSteps fillFirstName(String firstName) {
        registrationPage.firstNameInput.fill(firstName);
        return this;
    }

    public RegistrationSteps fillLastName(String lastName) {
        registrationPage.lastNameInput.fill(lastName);
        return this;
    }

    public RegistrationSteps selectGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            registrationPage.maleRadioButton.check();
        } else if ("female".equalsIgnoreCase(gender)) {
            registrationPage.femaleRadioButton.check();
        }
        return this;
    }

    public RegistrationSteps selectModel(String model) {
        registrationPage.modelDropdown.selectOption(new SelectOption().setLabel(model));
        return this;
    }

    public RegistrationSteps fillAddress1(String address1) {
        registrationPage.address1.fill(address1);
        return this;
    }

    public  RegistrationSteps fillAddress2(String address2) {
        registrationPage.address2.fill(address2);
        return this;
    }

    public RegistrationSteps fillCity(String city) {
        registrationPage.city.fill(city);
        return this;
    }

    public RegistrationSteps fillContact1(String contact1) {
        registrationPage.contact1.fill(contact1);
        return this;
    }
    public  RegistrationSteps fillContact2(String contact2) {
        registrationPage.contact2.fill(contact2);
        return this;
    }

    public RegistrationSteps clickRegisterButton(){
        registrationPage.registerButton.click();
        return this;
    }

    public RegistrationSteps verifySuccessAlert(String SuccessMessage){
        page.onDialog(dialog -> {
            Assert.assertEquals(dialog.message(), SuccessMessage);

            dialog.accept();
        });
        return this;
    }

    public void refreshPage() {
       page.reload();
    }
}