package com.aliexpress.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.aliexpress.data.Locale;
import com.aliexpress.pages.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationTest {

    HomePage homePage = new HomePage();
    ElementsCollection buttonList = homePage.returnButtonList();

    @BeforeAll
    static void configure() {
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "0x0";
        WebDriverManager.chromedriver().setup();
        open("https://aliexpress.com/");
    }

    @AfterEach
    void declineSubscriptionAfterEachCheck() {
        homePage.declineSubscription();
    }

    static Stream<Arguments> siteShouldContainAllButtonsUsingStreamTest() {
        return Stream.of(
                Arguments.of(Locale.en_US, List.of("Sell on Aliexpress", "Cookie Preferences", "Help",
                        "Buyer Protection", "App", "/ English / EUR", "Wish List", "Account")),
                Arguments.of(Locale.es_ES, List.of("Vende en AliExpress", "Configuración de privacidad", "Ayuda",
                        "Protección del comprador", "App", "/ Español / EUR", "Lista de Deseos", "Mi Cuenta"))
        );
    }

    @MethodSource("siteShouldContainAllButtonsUsingStreamTest")
    @ParameterizedTest(name = "For language {0} on website https://aliexpress.com/ must be visible the list of buttons {1}")
    void siteShouldContainAllButtonsUsingStreamTest(Enum locale, List<String> expectedButtons) {
        homePage
                .changeLanguage(locale.toString())
                .acceptCookies()
                .declineSubscription();
        buttonList.filter(visible).shouldHave(texts(expectedButtons));
    }

    @CsvSource({
            "en_US, Sell on Aliexpress, Cookie Preferences, Help, Buyer Protection, App, / English / EUR, Wish List, Account"
//            "pt_BR,Vender no aliexpress" //,Configurações De Privacidade,Ajuda,Proteção ao Consumidor,  App,/ Português / EUR,Lista de Desejos,Minha Conta",
//            "es_ES,Vende en AliExpress, Configuración de privacidad, Ayuda,Protección del comprador,App,/ Español / EUR,  Lista de Deseos, Mi Cuenta"

    } )

    @ParameterizedTest(name = "For language {0} on website https://aliexpress.com/ must be visible the list of buttons {1}")
    void siteShouldContainButtonsUsingCsvSource(String locale, String expectedButtons) {
        void exampleTest(@CsvSource.Split(separator = ",") String[] csvValues) {
            List<String> buttonList = Arrays.asList(csvValues).subList(1, csvValues.length);
            System.out.println(buttonList);
        }
        homePage
                .changeLanguage(locale)
                .acceptCookies()
                .declineSubscription();
        buttonList.filter(visible).shouldHave(exactTexts(buttonList));
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