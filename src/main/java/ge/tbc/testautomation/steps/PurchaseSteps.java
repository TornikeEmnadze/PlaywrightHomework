package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.*;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PurchaseSteps {
    Page page;
    HomePage homePage;
    LoginPage loginPage;
    MyAccountPage myAccountPage;
    WishlistPage wishlistPage;
    CheckoutPage checkoutPage;
    CheckoutPaymentPage checkoutPaymentPage;
    SuccessPage successPage;

    public PurchaseSteps(Page page){
        this.page = page;
        this.homePage = new HomePage(page);
        this.loginPage = new LoginPage(page);
        this.myAccountPage = new MyAccountPage(page);
        this.wishlistPage = new WishlistPage(page);
        this.checkoutPage = new CheckoutPage(page);
        this.checkoutPaymentPage = new CheckoutPaymentPage(page);
        this.successPage = new SuccessPage(page);
    }

    public PurchaseSteps login(String email, String password) {
        homePage.signInLink.click();
        loginPage.emailInput.fill(email);
        loginPage.passwordInput.fill(password);

        page.waitForTimeout(2000);
        loginPage.signInButton.click();
        return this;
    }


    public PurchaseSteps logout() {
        homePage.accountDropdown.click();
        homePage.signOutLink.click();

        homePage.signInLink.waitFor();
        return this;
    }

    public PurchaseSteps verifyItemInWishlist(String favouriteProduct) {
        page.waitForSelector(".logged-in");
        homePage.accountDropdown.click();
        homePage.mywishList.click();
        wishlistPage.pageTitle.waitFor();

        assertThat(wishlistPage.itemNamesInWishlist.first()).hasText(favouriteProduct);
        return this;
    }


    public PurchaseSteps proceedToBuyFromWishlist(String streetAddress,String city,String country,String state,String postcode, String phoneNumber) {
        wishlistPage.addToCartButton.click();
        page.pause();
        checkoutPage.nextButton.waitFor();
        if (checkoutPage.streetInput.isVisible()) {
            checkoutPage.streetInput.fill(streetAddress);
            checkoutPage.cityInput.fill(city);
            checkoutPage.countrySelect.selectOption(country);
            checkoutPage.regionInput.fill(state);
            checkoutPage.postCodeInput.fill(postcode);
            checkoutPage.phoneInput.fill(phoneNumber);
        }
        checkoutPage.shippingMethodRadio.check();
        checkoutPage.nextButton.click();
        return this;
    }


    public PurchaseSteps validateReviewAndPaymentsPage(String fistName,String lastname,String streetAddress,String city) {
        checkoutPaymentPage.placeOrderButton.waitFor();

        // Validate shipping is added
        String shippingCostText = checkoutPaymentPage.shippingCostSummary.innerText();
        double shippingCost = Double.parseDouble(shippingCostText.replace("$", ""));
        Assert.assertTrue(shippingCost > 0, "Shipping cost was not added.");

        // Validate account information
        assertThat(checkoutPaymentPage.shippingAddressInfo).containsText(fistName + " " + lastname);
        assertThat(checkoutPaymentPage.shippingAddressInfo).containsText(streetAddress);
        assertThat(checkoutPaymentPage.shippingAddressInfo).containsText(city);
        return this;
    }

    public PurchaseSteps attemptInvalidDiscount(String discountCode,String errorMessage) {
        checkoutPaymentPage.discountCodeToggle.click();
        checkoutPaymentPage.discountCodeInput.fill(discountCode);
        checkoutPaymentPage.applyDiscountButton.click();
        checkoutPaymentPage.discountErrorMessage.waitFor();
        assertThat(checkoutPaymentPage.discountErrorMessage).hasText(errorMessage);
        return this;
    }

    public PurchaseSteps placeOrderAndVerifySuccess() {
        checkoutPaymentPage.placeOrderButton.click();
        successPage.successMessage.waitFor();

        // Validate success page title
        assertThat(page).hasTitle("Success Page");

        // Validate order number is displayed
        String orderNumber = successPage.orderNumber.innerText();
        Assert.assertNotNull(orderNumber, "Order number is null.");
        Assert.assertTrue(orderNumber.matches("\\d+"), "Order number is not a valid number: " + orderNumber);
        System.out.println("ORDER PLACED: " + orderNumber);
        return this;
    }


}
