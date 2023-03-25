package com.aliexpress.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.aliexpress.data.Locale;
import com.aliexpress.pages.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class LocalizationTest {


    HomePage homePage = new HomePage();
    ElementsCollection buttonList = homePage.returnButtonList();

    static Stream<Arguments> siteShouldContainAllOfTheGivenButtonsForGivenLocale() {
        return Stream.of(
                Arguments.of(Locale.en_US, List.of("Sell on Aliexpress", "Cookie Preferences", "Help",
                        "Buyer Protection", "App", "/ English / EUR", "Wish List", "Account")),
                Arguments.of(Locale.es_ES, List.of("Vende en AliExpress", "Configuración de privacidad", "Ayuda",
                        "Protección del comprador", "App", "/ Español / EUR", "Lista de Deseos", "Mi Cuenta"))
        );
    }

    @BeforeAll
    static void configure() {
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "0x0";
        WebDriverManager.chromedriver().setup();
        open("https://aliexpress.com/");
    }

    @BeforeEach
    void declineSubscriptionAfterEachCheck() {
//        aliexpressPage.declineSubscription();
    }

    @MethodSource
    @ParameterizedTest(name = "For language {0} on website https://aliexpress.com/ must be visible the list of buttons {1}")
    void siteShouldContainAllOfTheGivenButtonsForGivenLocale(Enum localeAliexpress, List<String> expectedButtons) {
        homePage
                .changeLanguage(localeAliexpress.toString())
                .acceptCookies()
                .declineSubscription();
        buttonList.filter(visible).shouldHave(texts(expectedButtons));
    }


    @DisplayName("Тест с использованием .csv файла")
    @CsvFileSource(resources = "/LocalizationButtonsData.csv", numLinesToSkip = 0)
    @ParameterizedTest(name = "For language {0} on website aliexpress.com must be visible the list of buttons {1}")
    void siteShouldContainAllOfTheGivenButtonsFromSCV(String locale, List<String> expectedButtons) {
        homePage
                .changeLanguage(locale)
                .acceptCookies()
                .declineSubscription();
        buttonList.filter(visible).shouldHave(texts(expectedButtons));
    }
}