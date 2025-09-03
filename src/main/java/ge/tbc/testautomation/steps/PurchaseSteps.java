package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.pages.*;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PurchaseSteps {
    Page page;
    HomePage homePage;
    LoginPage loginPage;
    WishlistPage wishlistPage;
    CheckoutPage checkoutPage;
    CheckoutPaymentPage checkoutPaymentPage;
    SuccessPage successPage;
    ProductPage productPage;
    ShoppingCartPage shoppingCartPage;

    public PurchaseSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
        this.loginPage = new LoginPage(page);
        this.wishlistPage = new WishlistPage(page);
        this.checkoutPage = new CheckoutPage(page);
        this.checkoutPaymentPage = new CheckoutPaymentPage(page);
        this.successPage = new SuccessPage(page);
        this.productPage = new ProductPage(page);
        this.shoppingCartPage = new ShoppingCartPage(page);
    }

    public PurchaseSteps login(String email, String password) {
        homePage.signInLink.click();
        loginPage.emailInput.fill(email);
        loginPage.passwordInput.fill(password);
        page.waitForTimeout(2000);// i tried various waitsForSelectors but nothing works i have to use this.
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

    public PurchaseSteps selectItemFromWishlist() {
        wishlistPage.itemNamesInWishlist.first().click();
        productPage.addToCartButton.waitFor();
        return this;
    }

    public PurchaseSteps configureAndAddToCart() {
        productPage.sizeSwatch.click();
        productPage.colorSwatches.first().click();
        productPage.addToCartButton.click();
        productPage.successMessageAlert.waitFor();
        return this;
    }

    public PurchaseSteps navigateToCheckout() {
        page.waitForTimeout(1500);
        homePage.cartIcon.click();
        homePage.proceedToCheckoutButton.click();
        return this;
    }

    // Turns out this site is faulty in every aspect person can imagine but when
    // it comes to saving states and crashing all of a sudden they are pros
    // and all of this hits even harder when u discover it on 4 am.
    public PurchaseSteps fillShippingDetailsAndProceed(String streetAddress, String city, String country, String state, String postcode, String phoneNumber) {
        page.waitForTimeout(2000);

        if (checkoutPage.streetInput.first().isVisible()) {
            System.out.println("Shipping form is visible. Filling details...");
            checkoutPage.streetInput.first().fill(streetAddress);
            checkoutPage.cityInput.fill(city);
            checkoutPage.countrySelect.selectOption(new SelectOption().setLabel(country));

            if (checkoutPage.regionInput.isVisible()) {
                checkoutPage.regionInput.fill(state);
            } else {
                page.locator("select[name='region_id']").selectOption(new SelectOption().setLabel(state));
            }

            checkoutPage.postCodeInput.fill(postcode);
            checkoutPage.phoneInput.fill(phoneNumber);
        } else {
            System.out.println("Shipping form not visible. A default address is likely selected. Proceeding...");
        }

        checkoutPage.shippingMethodRadio.check();
        checkoutPage.nextButton.click();

        return this;
    }

    public PurchaseSteps validateReviewAndPaymentsPage(String fistName, String lastname, String streetAddress, String city) {
        checkoutPaymentPage.shippingAddressInfo.waitFor();
        assertThat(checkoutPaymentPage.shippingAddressInfo).containsText(fistName + " " + lastname);
        assertThat(checkoutPaymentPage.shippingAddressInfo).containsText(streetAddress);
        assertThat(checkoutPaymentPage.shippingAddressInfo).containsText(city);
        return this;
    }

    public PurchaseSteps attemptInvalidDiscount(String discountCode, String errorMessage) {
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

        assertThat(page).hasTitle("Success Page");

        String orderNumber;
        if (successPage.orderNumber.isVisible()) {
            System.out.println("Using registered user order number locator.");
            orderNumber = successPage.orderNumber.innerText();
        } else {
            System.out.println("Using guest user order number locator.");
            orderNumber = successPage.orderNumberForGuest.innerText();
        }

        Assert.assertNotNull(orderNumber, "Order number is null.");
        Assert.assertTrue(orderNumber.matches("\\d+"), "Order number is not a valid number: " + orderNumber);
        System.out.println("ORDER PLACED: " + orderNumber);
        return this;
    }


    public PurchaseSteps fillGuestShippingDetailsAndProceed(String email, String firstName, String lastName, String streetAddress, String city, String country, String state, String postcode, String phoneNumber) {
        checkoutPage.loadingMask.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        page.waitForTimeout(1000);

        checkoutPage.emailInput.fill(email);
        page.locator("input[name='firstname']").fill(firstName);
        page.locator("input[name='lastname']").fill(lastName);
        checkoutPage.streetInput.first().fill(streetAddress);
        checkoutPage.cityInput.fill(city);
        checkoutPage.countrySelect.selectOption(new SelectOption().setLabel(country));

        if (page.locator("select[name='region_id']").isVisible()) {
            page.locator("select[name='region_id']").selectOption(new SelectOption().setLabel(state));
        } else {
            checkoutPage.regionInput.fill(state);
        }

        checkoutPage.postCodeInput.fill(postcode);
        checkoutPage.phoneInput.fill(phoneNumber);

        checkoutPage.shippingMethodRadio.check();
        checkoutPage.loadingMask.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        checkoutPage.nextButton.click();
        return this;
    }
}