package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.DatabaseSteps;
import ge.tbc.testautomation.data.TechcanvassConstants;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.steps.RegistrationSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTest {
    RegistrationSteps registrationSteps;
    @BeforeClass
    public void goToPage() {
        registrationSteps = new RegistrationSteps(page);
        registrationSteps.navigateToRegistrationPage(TechcanvassConstants.PAGE_URL);
    }
    @DataProvider(name = "registrationDbData")
    public Object[][] dbDataProvider() {
        DatabaseSteps dbSteps = new DatabaseSteps();
        return dbSteps.getRegistrationData();
    }

    @Test(dataProvider = "registrationDbData")
    public void fillRegistrationFormFromDB(
            int id, String firstName, String lastName, String gender, String model,
            String address1, String address2, String city, String contact1, String contact2) {
        System.out.printf("Running test for user ID: %d, Name: %s %s%n", id, firstName, lastName);


        registrationSteps.fillFirstName(firstName)
                .fillLastName(lastName)
                .selectGender(gender)
                .selectModel(model)
                .fillAddress1(address1)
                .fillAddress2(address2)
                .fillCity(city)
                .fillContact1(contact1)
                .fillContact2(contact2)
                .clickRegisterButton()
                .verifySuccessAlert(TechcanvassConstants.SUCCESS_MESSAGE);
         // September 6 12:57 this site broke too, it isn't responding
         // turns out this website hates when u refresh it

        registrationSteps.refreshPage();
    }
}
