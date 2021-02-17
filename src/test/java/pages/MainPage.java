package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {
    private final String BASE_URL = "https://otus.ru/";

    @FindBy(css = "button.header2__auth")
    private WebElement authButton;

    @FindBy(css = "p[class*='header2-menu__item-text__username']")
    private WebElement userNameLink;

    @FindBy(css = "div[class*='header2-menu__dropdown'] a[href='/learning/']")
    private WebElement linkLK;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage open() {
        driver.get(BASE_URL);
        logger.info("Открыта главная страница otus.ru");

        return this;
    }

    public MainPage clickAuthButton() {
        authButton.click();
        logger.info("Нажата кнопка 'Вход и регистрация'");

        return this;
    }

    public MainPage clickUserNameLink() {
        userNameLink.click();
        logger.info("Клик на имени пользователя");

        return this;
    }

    public MainPage clickLKLink() {
        linkLK.click();
        logger.info("Клик на ссылке 'Личный кабинет'");

        return this;
    }

    public MainPage enterLK() {
        this
                .clickUserNameLink()
                .clickLKLink();

        return this;
    }
}
