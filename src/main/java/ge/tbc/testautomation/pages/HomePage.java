package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {
    public Locator hotSellerItems;
    public Locator colorChanger;
    public Locator productImg;
    public Locator searchBar;
    public Locator cartIcon;
    public Locator viewCartLink;

    public Locator womenPageLink;
    public Locator desktopHeaderPanel;
    public Locator desktopNavMenu;
    public Locator burgerMenuButton;
    public Locator mobileMenuPanel;
    public Locator accountMenuHeader;
    public Locator signInLink;
    public Locator signOutLink;
    public Locator accountDropdown;
    public Locator mywishList;
    public Locator proceedToCheckoutButton;

    public HomePage(Page page){
        this.hotSellerItems=page.locator(".widget-product-grid .product-item");
        this.colorChanger=page.locator(".swatch-attribute.color .swatch-option");
        this.productImg=page.locator(".product-image-photo");
        this.searchBar = page.locator("#search");
        this.cartIcon = page.locator(".action.showcart");
        this.viewCartLink = page.getByText("View and Edit Cart");


        this.womenPageLink=page.getByText("Women");

        this.desktopHeaderPanel = page.locator(".panel.header");
        this.desktopNavMenu = page.locator("nav.navigation");

        this.burgerMenuButton = page.locator("[data-action='toggle-nav']");
        this.mobileMenuPanel = page.locator(".nav-sections");
        this.accountMenuHeader = page.locator("a.nav-sections-item-switch[href='#store.links']");
        this.signInLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In"));
        this.accountDropdown = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Change")
        ).filter(new Locator.FilterOptions().setHas(page.locator("button[data-action='customer-menu-toggle']")));
        this.signOutLink = page.locator("div.customer-menu li.authorization-link:visible").getByText("Sign Out");
        this.mywishList=page.locator("a[href='https://magento.softwaretestingboard.com/wishlist/']").first();
        this.proceedToCheckoutButton = page.locator("button#top-cart-btn-checkout");


    }
}
