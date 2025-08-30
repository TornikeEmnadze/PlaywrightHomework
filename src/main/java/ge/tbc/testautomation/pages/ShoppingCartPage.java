package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ShoppingCartPage {
    public final Locator productNameInCart;
    public final Locator productPriceInCart;
    public final Locator deleteItemButton;
    public final Locator emptyCartMessage;

    public ShoppingCartPage(Page page) {
        this.productNameInCart = page.locator("#shopping-cart-table .product-item-name a");
        this.productPriceInCart = page.locator("#shopping-cart-table td[data-th='Price'] .price");
        this.deleteItemButton =page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Remove item"));
        this.emptyCartMessage = page.locator(".cart-empty");
    }
}