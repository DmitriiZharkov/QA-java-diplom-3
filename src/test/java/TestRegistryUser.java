import com.github.javafaker.Faker;
import driver.ChromeRule;
import driver.DriverFactory;
import driver.YandexRule;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.RegisterPage;
import restclients.UserApi;
import restclients.UserStep;

import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;
import static restclients.Urls.STELLAR_BURGERS_HOME_PAGE_URL;
import static restclients.UserGenerator.randomUser;

@RunWith(Parameterized.class)
public class TestRegistryUser {

    Faker faker = new Faker();
    UserStep userStep = new UserStep();
    UserApi userApi = randomUser();

    @Rule
    public DriverFactory rule;

    public TestRegistryUser(DriverFactory rule) {
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
    @DisplayName("Заполнение формы и регистрация с валидными данными")
    public void fillingOutTheRegistrationForm(){
        RegisterPage registerPage = new RegisterPage(rule.getWebDriver());

        registerPage
                .openRegistrationPage()
                .enterName(userApi.getName())
                .enterEmail(userApi.getEmail())
                .enterPassword(userApi.getPassword())
                .tapOnBattonRegistration()

                .checkRegistrationSuccess();
    }

    @Test
    @DisplayName("Заполнение формы регистрации с некорректным паролем: пароль 5 символов")
    public void fillingRegistrationFormWithIncorrectPassword(){
        RegisterPage registerPage = new RegisterPage(rule.getWebDriver());

        registerPage
                .openRegistrationPage()
                .enterName(userApi.getName())
                .enterEmail(userApi.getPassword())
                .enterPassword(faker.bothify("29???"))
                .tapOnBattonRegistration()

                .checkIncorrectPassword();
    }

    @After
    public void tearDown(){

        Response response = userStep.login(userApi);
        if(response.statusCode() == 200) {userStep.delete(userApi);}

        clearBrowserCookies();
    }
}