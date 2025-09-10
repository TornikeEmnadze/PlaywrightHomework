package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    public Locator hotSellerItems;
    public Locator colorChanger;
    public Locator productImg;
    public Locator searchBar;
    public Locator cartIcon;
    public Locator viewCartLink;

    public HomePage(Page page){
        this.hotSellerItems=page.locator(".widget-product-grid .product-item");
        this.colorChanger=page.locator(".swatch-attribute.color .swatch-option");
        this.productImg=page.locator(".product-image-photo");
        this.searchBar = page.locator("#search");
        this.cartIcon = page.locator(".action.showcart");
        this.viewCartLink = page.getByText("View and Edit Cart");
    }
}
