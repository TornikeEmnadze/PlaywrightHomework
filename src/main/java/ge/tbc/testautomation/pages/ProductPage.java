package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ProductPage {
    public Locator productName;
    public Locator productPrice;
    public Locator sizeSwatch;
    public Locator colorSwatches;
    public Locator addToCartButton;
    public Locator successMessageAlert;
    public Locator outOfStockMessage;
    public Locator blueColorSwatch;
    public Locator xsSizeSwatch;
    public Locator outOfStockItem;
    public Locator addToWishlistButton;
    public Locator reviewsTab;
    public Locator reviewItems;
    public Locator youAreReviewingText;
    public Locator greenColorSwatch;

    public ProductPage(Page page) {
        this.productName = page.locator(".page-title .base");
        this.productPrice = page.locator(".product-info-main .price-wrapper .price");
        this.sizeSwatch = page.locator("[aria-label='M']");
        this.colorSwatches = page.locator(".swatch-option.color");
        this.addToCartButton = page.locator("#product-addtocart-button");
        this.successMessageAlert = page.locator(".message-success");
        this.outOfStockMessage = page.locator(".message-error");
        this.blueColorSwatch = page.locator(".product-info-main .swatch-option.color[aria-label='Blue']");
        this.xsSizeSwatch = page.locator(".product-info-main .swatch-option.text[aria-label='XS']");
        this.outOfStockItem=page.getByText("Radiant Tee");
        this.addToWishlistButton = page.locator("div.product-social-links")
                .getByText("Add to Wish List");
        this.reviewItems=page.locator(".review-items .review-item");
        this.reviewsTab=page.locator("#reviews");
        this.youAreReviewingText=page.getByText("You're reviewing:");
        this.greenColorSwatch = page.locator(".swatch-option.color[aria-label='Green']");
    }
}