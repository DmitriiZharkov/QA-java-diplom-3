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
import pageobject.RegisterPage;
import restclients.UserApi;
import restclients.UserStep;

import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;
import static restclients.Urls.STELLAR_BURGERS_HOME_PAGE_URL;
import static restclients.UserGenerator.randomUser;

@RunWith(Parameterized.class)
public class TestLoginUser {

    private UserApi userApi = randomUser();
    UserStep userStep = new UserStep();

    @Rule
    public DriverFactory rule;

    public TestLoginUser(DriverFactory rule) {
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
    }

    @Test
    @DisplayName("Вход в аккаунт по кнопке «Войти в аккаунт» на главной")
    public void inputByButtonToEnterAccountHp(){
        LoginPage loginPage = new LoginPage(rule.getWebDriver());
        BasePage basePage = new BasePage(rule.getWebDriver());

        basePage
                .openHomePage()
                .clickOnButtonToEnterAccountHp();
        loginPage
                .waitingForLoading()
                .enterEmail(userApi.getEmail())
                .enterPassword(userApi.getPassword())
                .clickOnButtonLoginInFormAuth()
                .checkHomePageAfterAuth();
    }

    @Test
    @DisplayName("Вход в аккаунт через кнопку «Личный кабинет» на главной")
    public void inputByPersonalAccountButtonHp(){
        LoginPage loginPage = new LoginPage(rule.getWebDriver());
        BasePage basePage = new BasePage(rule.getWebDriver());

        basePage
                .openHomePage()
                .clickOnPersonalAccountButtonHp();
        loginPage
                .enterEmail(userApi.getEmail())
                .enterPassword(userApi.getPassword())
                .clickOnButtonLoginInFormAuth()
                .checkHomePageAfterAuth();
    }

    @Test
    @DisplayName("Вход в аккаунт через кнопку в форме регистрации")
    public void inputByLoginButtonInFormRegistration(){
        RegisterPage registerPage = new RegisterPage(rule.getWebDriver());
        LoginPage loginPage = new LoginPage(rule.getWebDriver());

        registerPage
                .openRegistrationPage();
        loginPage
                .clickOnLoginButtonInForms()
                .enterEmail(userApi.getEmail())
                .enterPassword(userApi.getPassword())
                .checkHomePageAfterAuth();
    }

    @Test
    @DisplayName("Вход в аккаунт через кнопку в форме восстановления пароля")
    public void inputByLoginButtonInFormRestorePassword(){
        LoginPage loginPage = new LoginPage(rule.getWebDriver());

        loginPage
                .openPasswordRestorePage()
                .clickOnLoginButtonInForms()
                .enterEmail(userApi.getEmail())
                .enterPassword(userApi.getPassword())
                .clickOnButtonLoginInFormAuth()
                .checkHomePageAfterAuth();
    }

    @After
    public void tearDown(){
        userStep.delete(userApi);
        clearBrowserCookies();
    }
}