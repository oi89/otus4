package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthPage extends BasePage {
    @FindBy(css = "input.js-email-input")
    private WebElement loginInput;

    @FindBy(css = "input.js-psw-input")
    private WebElement passInput;

    @FindBy(xpath = "//div[contains(@class, 'new-input-line_relative')]/button[@type='submit'][contains(text(), 'Войти')]")
    private WebElement enterButton;

    public AuthPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public AuthPage enterLogin(String login) {
        loginInput.sendKeys(login);
        logger.info("Введен логин");

        return this;
    }

    public AuthPage enterPassword (String password) {
        passInput.sendKeys(password);
        logger.info("Введен пароль");

        return this;
    }

    public AuthPage clickEnterButton() {
        enterButton.click();
        logger.info("Нажата кнопка 'Войти'");

        return this;
    }

    public AuthPage doAuthorization(String login, String password) {
        this
                .enterLogin(login)
                .enterPassword(password)
                .clickEnterButton();

        return this;
    }
}
