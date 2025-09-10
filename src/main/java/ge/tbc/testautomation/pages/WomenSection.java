package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class WomenSection {
    public Locator allProductItems;
    public Locator reviewLink;

    public WomenSection(Page page){
        this.allProductItems=page.locator(".products-grid .product-item");
        this.reviewLink= page.locator(".reviews-actions");
    }
}
