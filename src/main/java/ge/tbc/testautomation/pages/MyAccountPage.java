package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyAccountPage {
    public final Locator registrationSuccessMessage;
    public final Locator welcomeMessageHeader; // In the page header
    public final Locator wishlistItemName;
    public final Locator addToWishlistSuccessMessage;

    public MyAccountPage(Page page) {
        this.registrationSuccessMessage = page.locator(".message-success:has-text('Thank you for registering')");

        this.welcomeMessageHeader = page.locator(".page-header");

        this.wishlistItemName = page.locator(".products-grid.wishlist .product-item-name a");
        this.addToWishlistSuccessMessage = page.locator(".message-success:has-text('has been added to your Wish List')");
    }
}