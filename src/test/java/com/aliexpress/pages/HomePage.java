package com.aliexpress.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    private SelenideElement
            acceptCookiesButton = $(by("data-role", "gdpr-accept")),
            declineSubscriptionButton = $(".Sk1_X._1-SOk"),
            languageCurrencyButton = $(By.cssSelector("a#switcher-info")),
            chooseLanguageButton = $(byClassName("switcher-currency-c")),
            saveButton = $("button[data-role=save]");
    ElementsCollection buttonList = $$("#nav-global .ng-item");

    public HomePage openPage() {
        WebDriverManager.chromedriver().setup();
        open("https://aliexpress.com/");
        return this;
    }

    public HomePage acceptCookies() {
        if (acceptCookiesButton.exists()) {
            acceptCookiesButton.shouldBe(visible).click();
        }
        return this;
    }

    public HomePage declineSubscription() {
        if (declineSubscriptionButton.exists()) {
            declineSubscriptionButton.shouldBe(visible).click();
        }
        return this;
    }

    public HomePage changeLanguage(String locale) {
        languageCurrencyButton.click();
        chooseLanguageButton.click();
        $(By.cssSelector("[data-locale='" + locale + "']")).click();
        saveButton.click();
        return this;
    }

    public ElementsCollection getButtonList() {
        return buttonList;
    }

}
