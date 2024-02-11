package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;

import static restclients.Urls.URL_REGISTRATION;

public class RegisterPage {

    private WebDriver webDriver;

    public RegisterPage(WebDriver webDriver){ this.webDriver = webDriver; }

    //Текст Имя на поле для ввода имени
    private final By nameText = By.xpath(".//label[text() = 'Имя']");
    //Поле ввода имени
    private final By nameField = By.xpath(".//label[text()='Имя']/following-sibling::input");
    //Текст Email на поле для ввода email
    private final By emailText = By.xpath(".//label[text() = 'Email']");
    //Поле для ввода email
    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    //Текст Пароль на поле для ввода пароля
    private final By passwordText = By.xpath(".//label[text() = 'Пароль']");
    //Поле для ввода пароля
    private final By passwordField = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    //Текст Некорректный пароль
    private final By incorrectPasswordText = By.xpath(".//p[text() = 'Некорректный пароль']");
    //Форма для ввода данных и входа зарегистрированного пользователя
    private final By formToLogin = By.xpath(".//form[@class = 'Auth_form__3qKeq mb-20']");
    //Кнопка зарегистрироваться
    private final By registrationButton = By.xpath(".//button[text() = 'Зарегистрироваться']");

    @Step("Открытие страницы регистрации")
    public RegisterPage openRegistrationPage(){
        webDriver.get(URL_REGISTRATION);
        return this;
    }

    @Step("Заполнение поля Имя")
    public RegisterPage enterName(String name){
        webDriver.findElement(nameText).click();
        webDriver.findElement(nameField).sendKeys(name);
        return this;
    }

    @Step("Заполнение поля Email")
    public RegisterPage enterEmail(String email){
        webDriver.findElement(emailText).click();
        webDriver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Заполнение поля Пароль")
    public RegisterPage enterPassword(String email){
        webDriver.findElement(passwordText).click();
        webDriver.findElement(passwordField).sendKeys(email);
        return this;
    }

    @Step("Проверка неправильного ввода пароля")
    public RegisterPage checkIncorrectPassword(){
        webDriver.findElement(incorrectPasswordText);
        return this;
    }

    @Step("Проверка успешной регистрации")
    public boolean checkRegistrationSuccess(){
        return webDriver.findElement(formToLogin).isDisplayed();
    }

    @Step("Клик по кнопке Зарегистрироваться")
    public RegisterPage tapOnBattonRegistration(){
        webDriver.findElement(registrationButton).click();
        return this;
    }

}