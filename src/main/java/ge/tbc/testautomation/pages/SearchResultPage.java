package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

// This page is ONLY for the grid of results
public class SearchResultPage {
    public Locator productItemLinks;

    public SearchResultPage(Page page) {
        this.productItemLinks = page.locator(".product-item-link");
    }
}