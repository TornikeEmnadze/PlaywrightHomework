package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ShoppingCartPage {
    public Locator productNameInCart;
    public Locator productPriceInCart;
    public Locator deleteItemButton;
    public Locator emptyCartMessage;
    public Locator updateCartButton;

    public ShoppingCartPage(Page page) {
        this.productNameInCart = page.locator("#shopping-cart-table .product-item-name a");
        this.productPriceInCart = page.locator("#shopping-cart-table td[data-th='Price'] .price");
        this.deleteItemButton =page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Remove item"));
        this.emptyCartMessage = page.locator(".cart-empty");
        this.updateCartButton = page.locator("button.action.update[title='Update Shopping Cart']");

    }
}