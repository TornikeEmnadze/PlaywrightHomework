package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class WishlistPage {
    public Locator pageTitle;
    public Locator itemNamesInWishlist;
    public Locator addToCartButton;

    public WishlistPage(Page page) {
        this.pageTitle = page.locator(".page-title .base");
        this.itemNamesInWishlist = page.locator(".product-item-name a");
        this.addToCartButton =page.locator("button[data-bind*='add_to_cart_params']");
    }
}
