package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static utils.BaseHooks.wait;

public class ProfilePage extends BasePage {
    @FindBy(css = "div[class='nav__items'] a[href*='personal']")
    private WebElement aboutLink;

    @FindBy(css = "button[value='accept']")
    private WebElement acceptButton;

    @FindBy(css = "input[name='fname']")
    private WebElement nameInput;

    @FindBy(css = "input[name='fname_latin']")
    private WebElement nameLatinInput;

    @FindBy(css = "input[name='lname']")
    private WebElement lastNameInput;

    @FindBy(css = "input[name='lname_latin']")
    private WebElement lastNameLatinInput;

    @FindBy(css = "input[name='blog_name']")
    private WebElement nameBlogInput;

    @FindBy(css = "input[name='date_of_birth']")
    private WebElement dateInput;

    @FindBy(css = "div[class='container__col container__col_12'] div[class='lk-cv-block']:first-child div[class*='container__row']:first-child div[class*='lk-cv-block__input']:first-child")
    private WebElement countryInput;

    private String listValueLocator = "button[title='%s']";
    private WebElement buttonCountry;

    @FindBy(css = "div[class*='container__col']:first-child div[class*='container__row']:nth-child(2) div[class*='js-lk-cv-dependent-slave-city']")
    private WebElement cityInput;

    private WebElement buttonCity;

    @FindBy(css = "div[class*='container__col']:first-child div[class*='container__row']:nth-child(3) div[class*='input']:first-child")
    private WebElement languageInput;

    private WebElement languageLevelButton;

    @FindBy(css = "div.lk-cv-block div[class*='input_straight-top-right'] span")
    private WebElement contactTypeButton;

    @FindBy(xpath = "//button[@title='Facebook']")
    private WebElement contactFbButton;

    @FindBy(css = "input[name='contact-0-value']")
    private WebElement contactFbInput;

    @FindBy(css = "button[class*='js-lk-cv-custom-select-add']")
    private WebElement addContactButton;

    @FindBy(xpath = "//p[contains(text(), 'Контактная информация')]/..//div[contains(@class, 'js-formset-items')]/div[2]//button[@title='VK']")
    private WebElement contactVkButton;

    @FindBy(css = "input[name='contact-1-value']")
    private WebElement contactVkInput;

    @FindBy(css = "button[type='submit'][class*='lk-cv-action-buttons__button_gray']")
    private WebElement saveButton;

    private String deleteNContactButtonLocator = "//div[@data-num='%d']/div[contains(@class, 'container__col_12')]//button[contains(text(), 'Удалить')]";
    private WebElement deleteNContactButton;

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ProfilePage clickAboutLink() {
        aboutLink.click();
        logger.info("Клик на ссылке 'О себе'");

        try {
            acceptButton.click();
            logger.info("Клик на кнопке 'Согласен");
        } catch (Exception exception) {
            logger.info("Баннер с согласием не был показан");
        }

        return this;
    }

    public ProfilePage setNameValue(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
        logger.info(String.format("Введено имя '%s'", name));
        return this;
    }

    public ProfilePage setNameLatinValue(String nameLatin) {
        nameLatinInput.clear();
        if (!nameLatin.isEmpty()) {
            nameLatinInput.sendKeys(nameLatin);
            logger.info(String.format("Введено имя латиницей '%s'", nameLatin));
        } else {
            logger.info("Очищено поле 'Имя латиницей'");
        }

        return this;
    }

    public ProfilePage setLastNameValue(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        logger.info(String.format("Введена фамилия '%s'", lastName));
        return this;
    }

    public ProfilePage setLastNameLatinValue(String lastNameLatin) {
        lastNameLatinInput.clear();
        if (!lastNameLatin.isEmpty()) {
            lastNameLatinInput.sendKeys(lastNameLatin);
            logger.info(String.format("Введена фамилия латиницей '%s'", lastNameLatin));
        } else {
            logger.info("Очищено поле 'Фамилия латиницей'");
        }

        return this;
    }

    public ProfilePage setNameBlogValue(String nameBlog) {
        nameBlogInput.clear();
        if (!nameBlog.isEmpty()) {
            nameBlogInput.sendKeys(nameBlog);
            logger.info(String.format("Введено имя в блоге '%s'", nameBlog));
        } else {
            logger.info("Очищено поле 'Имя в блоге'");
        }

        return this;
    }

    public ProfilePage setDateValue(String date) {
        dateInput.clear();
        if (!date.isEmpty()) {
            dateInput.sendKeys(date);
            dateInput.sendKeys(Keys.ENTER);
            logger.info(String.format("Введена дата рождения '%s'", date));
        } else {
            logger.info("Очищено поле 'Дата рождения'");
        }

        return this;
    }

    public ProfilePage setCountryValue(String countryName) {
        countryInput.click();
        logger.info("Клик в поле 'Страна'");

        String countryNameLocator = String.format(listValueLocator, countryName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(countryNameLocator)));
        buttonCountry = driver.findElement(By.cssSelector(countryNameLocator));
        buttonCountry.click();
        logger.info(String.format("Выбрана страна '%s'", countryName));

        String countryDivLocator = "//input[@name='country']/following-sibling::div";
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElement(By.xpath(countryDivLocator)).getText().trim().contains(countryName);
            }
        });

        return this;
    }

    public ProfilePage setCityValue(String cityName) {
        wait.until(ExpectedConditions.attributeToBe(cityInput, "value", ""));
        wait.until(ExpectedConditions.elementToBeClickable(cityInput));
        Actions actions = new Actions(driver);
        actions
                .moveToElement(cityInput)
                .click(cityInput)
                .build()
                .perform();
        logger.info("Клик в поле 'Город'");

        String cityNameLocator = String.format(listValueLocator, cityName);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cityNameLocator)));
        buttonCity = driver.findElement(By.cssSelector(cityNameLocator));
        buttonCity.click();
        logger.info(String.format("Выбран город '%s'", cityName));

        return this;
    }

    public ProfilePage setLanguageLevelValue(String languageLevelName) {
        languageInput.click();
        logger.info("Клик в поле 'Уровень английского'");

        String levelNameLocator = String.format(listValueLocator, languageLevelName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(levelNameLocator)));
        languageLevelButton = driver.findElement(By.cssSelector(levelNameLocator));
        languageLevelButton.click();
        logger.info(String.format("Выбран уровень английского '%s'", languageLevelName));

        return this;
    }

    public ProfilePage clickContactTypeButton() {
        contactTypeButton.click();
        logger.info("Клик на кнопку 'Способ связи'");
        return this;
    }

    public ProfilePage clickContactFbButton() {
        contactFbButton.click();
        logger.info("Клик в выпадающем списке на значении 'Facebook'");
        return this;
    }

    public ProfilePage setFbValue(String fbName) {
        contactFbInput.sendKeys(fbName);
        logger.info("Добавлен контакт FB");
        return this;
    }

    public ProfilePage clickAddContactButton() {
        addContactButton.click();
        logger.info("Клик на кнопку 'Добавить' в контактах");
        return this;
    }

    public ProfilePage clickContactVkButton() {
        contactVkButton.click();
        logger.info("Клик в выпадающем списке на значении 'VK'");
        return this;
    }

    public ProfilePage setVkValue(String vkName) {
        contactVkInput.sendKeys(vkName);
        logger.info("Добавлен контакт VK");
        return this;
    }

    public ProfilePage setFbContactValue(String fbName) {
        this
                .clickContactTypeButton()
                .clickContactFbButton()
                .setFbValue(fbName);

        return this;
    }

    public ProfilePage setVkContactValue(String vkName) {
        this
                .clickAddContactButton()
                .clickContactTypeButton()
                .clickContactVkButton()
                .setVkValue(vkName);

        return this;
    }

    public String getNameValue() {
        return nameInput.getAttribute("value");
    }

    public String getNameLatinValue() {
        return nameLatinInput.getAttribute("value");
    }

    public String getLastNameValue() {
        return lastNameInput.getAttribute("value");
    }

    public String getLastNameLatinValue() {
        return lastNameLatinInput.getAttribute("value");
    }

    public String getNameBlogValue() {
        return nameBlogInput.getAttribute("value");
    }

    public String getDateValue() {
        return dateInput.getAttribute("value");
    }

    public String getCountryValue() {
        return countryInput.getText();
    }

    public String getCityValue() {
        return cityInput.getText();
    }

    public String getLanguageLevelValue() {
        return languageInput.getText();
    }

    public String getFbValue() {
        return contactFbInput.getAttribute("value");
    }

    public String getVkValue() {
        return contactVkInput.getAttribute("value");
    }

    public ProfilePage clickSaveButton() {
        saveButton.click();
        logger.info("Нажата кнопка 'Сохранить и заполнить позже'");
        return this;
    }

    public ProfilePage deleteContactByNumber(int blockNumber) {
        String deleteContactLocator = String.format(deleteNContactButtonLocator, blockNumber);

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deleteContactLocator)));
            deleteNContactButton = driver.findElement(By.xpath(deleteContactLocator));
            deleteNContactButton.click();
            logger.info(String.format("Удален контакт №%d", blockNumber));
        } catch (TimeoutException exception) {
            logger.info(String.format("Кнопка удаления контакта №%d не была найдена", blockNumber));
        }

        return this;
    }

    public ProfilePage clearAllUserData() {
        this
                .setNameValue("Дмитрий")
                .setNameLatinValue("")
                .setLastNameValue("Дмитриев")
                .setLastNameLatinValue("")
                .setNameBlogValue("")
                .setDateValue("")
                .setCountryValue("Германия")
                .setCityValue("Мюнхен")
                .setLanguageLevelValue("Супер продвинутый (Mastery)")
                .deleteContactByNumber(0)
                .deleteContactByNumber(1)
                .clickSaveButton();

        return this;
    }
}
