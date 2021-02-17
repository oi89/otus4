package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.SoftAssertions;

import pages.AuthPage;
import pages.MainPage;
import pages.ProfilePage;
import utils.BaseHooks;

public class CheckUserProfileDataTest extends BaseHooks {
    private Logger logger = LogManager.getLogger(CheckUserProfileDataTest.class);
    SoftAssertions softAssertions = new SoftAssertions();

    MainPage mainPage;
    AuthPage authPage;
    ProfilePage profilePage;

    private String login;
    private String password;

    private String name = "Иван";
    private String nameLatin = "Ivan";
    private String lastName = "Петров";
    private String lastNameLatin = "Petrov";
    private String nameBlog = "Ivan123";
    private String date = "11.01.1990";
    private String country = "Россия";
    private String city = "Москва";
    private String language = "Средний (Intermediate)";
    private String contactFb = "ivanfb";
    private String contactVK = "ivanvk";

    @BeforeEach
    public void testSetUp() {
        login = System.getProperty("login").trim().toLowerCase();
        password = System.getProperty("password").trim().toLowerCase();

        mainPage = new MainPage(driver);
        authPage = new AuthPage(driver);
        profilePage = new ProfilePage(driver);

        mainPage
                .open()
                .clickAuthButton();
        authPage.doAuthorization(login, password);
        mainPage.enterLK();
        profilePage
                .clickAboutLink()
                .clearAllUserData();
    }

    @Test
    public void checkUserProfileDataTest() {
        mainPage.enterLK();
        profilePage
                .clickAboutLink()
                .setNameValue(name)
                .setNameLatinValue(nameLatin)
                .setLastNameValue(lastName)
                .setLastNameLatinValue(lastNameLatin)
                .setNameBlogValue(nameBlog)
                .setDateValue(date)
                .setCountryValue(country)
                .setCityValue(city)
                .setLanguageLevelValue(language)
                .setFbContactValue(contactFb)
                .setVkContactValue(contactVK)
                .clickSaveButton();

        deleteCookies();

        mainPage
                .open()
                .clickAuthButton();
        authPage.doAuthorization(login, password);
        mainPage.enterLK();
        profilePage.clickAboutLink();

        softAssertions.assertThat(profilePage.getNameValue()).isEqualTo(name);
        softAssertions.assertThat(profilePage.getNameLatinValue()).isEqualTo(nameLatin);
        softAssertions.assertThat(profilePage.getLastNameValue()).isEqualTo(lastName);
        softAssertions.assertThat(profilePage.getLastNameLatinValue()).isEqualTo(lastNameLatin);
        softAssertions.assertThat(profilePage.getNameBlogValue()).isEqualTo(nameBlog);
        softAssertions.assertThat(profilePage.getDateValue()).isEqualTo(date);
        softAssertions.assertThat(profilePage.getCountryValue()).isEqualTo(country);
        softAssertions.assertThat(profilePage.getCityValue()).isEqualTo(city);
        softAssertions.assertThat(profilePage.getLanguageLevelValue()).isEqualTo(language);
        softAssertions.assertThat(profilePage.getFbValue()).isEqualTo(contactFb);
        softAssertions.assertThat(profilePage.getVkValue()).isEqualTo(contactVK);
        softAssertions.assertAll();
    }
}
