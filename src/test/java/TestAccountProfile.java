import driver.ChromeRule;
import driver.DriverFactory;
import driver.YandexRule;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.BasePage;
import pageobject.LoginPage;
import pageobject.PersonalAccount;
import restclients.UserApi;
import restclients.UserStep;

import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;
import static restclients.Urls.STELLAR_BURGERS_HOME_PAGE_URL;
import static restclients.UserGenerator.randomUser;

@RunWith(Parameterized.class)
public class TestAccountProfile {

    UserApi userApi = randomUser();
    UserStep userStep = new UserStep();

    @Rule
    public DriverFactory rule;

    public TestAccountProfile(DriverFactory rule) {
        this.rule = rule;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                { new YandexRule() },
                { new ChromeRule() }
        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = STELLAR_BURGERS_HOME_PAGE_URL;
        userStep.create(userApi);

        LoginPage loginPage = new LoginPage(rule.getWebDriver());
        loginPage
                .openLoginPage()
                .enterEmail(userApi.getEmail())
                .enterPassword(userApi.getPassword())
                .clickOnButtonLoginInFormAuth()
                .checkHomePageAfterAuth();
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику на «Личный кабинет»")
    public void checkGoToAccountByPersonalAccountButton(){
        PersonalAccount personalAccount = new PersonalAccount(rule.getWebDriver());
        BasePage basePage = new BasePage(rule.getWebDriver());

        basePage
                .clickOnPersonalAccountButtonHp();
        personalAccount
                .isDisplayedProfileText();
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор - по клику на «Конструктор»")
    public void transitionFromAccountByConstructorButton(){
        PersonalAccount personalAccount = new PersonalAccount(rule.getWebDriver());
        BasePage basePage = new BasePage(rule.getWebDriver());

        basePage
                .clickOnPersonalAccountButtonHp();
        personalAccount
                .clickOnConstructorButton();
        basePage
                .checkConstructorHeaderText();
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор - по клику на логотип Stellar Burgers")
    public void transitionFromAccountByLogo(){
        BasePage basePage = new BasePage(rule.getWebDriver());

        basePage
                .clickOnPersonalAccountButtonHp()
                .clickOnLogoStellarBurgers()
                .checkConstructorHeaderText();
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    public void transitionFromAccountByExitButton()  {
        PersonalAccount personalAccount = new PersonalAccount(rule.getWebDriver());
        LoginPage loginPage = new LoginPage(rule.getWebDriver());
        BasePage basePage = new BasePage(rule.getWebDriver());

        basePage
                .clickOnPersonalAccountButtonHp();
        personalAccount
                .clickOnExitButton();
        loginPage
                .isDisplayedEnterText();
    }

    @After
    public void tearDown(){
        userStep.delete(userApi);
        clearBrowserCookies();
    }
}