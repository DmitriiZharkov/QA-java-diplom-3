import driver.ChromeRule;
import driver.DriverFactory;
import driver.YandexRule;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.BasePage;

import static org.openqa.selenium.devtools.v85.network.Network.clearBrowserCookies;

@RunWith(Parameterized.class)
public class TestConstructor {

    @Rule
    public DriverFactory rule;

    public TestConstructor(DriverFactory rule) {
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
        BasePage basePage = new BasePage(rule.getWebDriver());
        basePage
                .openHomePage();
    }

    @Test
    @DisplayName("Переход к разделу конструктора Соусы")
    public void goToSaucesSection(){
        BasePage basePage = new BasePage(rule.getWebDriver());
        basePage
                .clickOnSaucesButton()
                .previousAndNextButtonsAreNotSelected();
    }

    @Test
    @DisplayName("Переход к разделу конструктора Начинки")
    public void goToFillingsSection(){
        BasePage basePage = new BasePage(rule.getWebDriver());
        basePage
                .clickOnFillingsButton()
                .previousTwoButtonsAreNotSelected();
    }

    @Test
    @DisplayName("Переход к разделу конструктора Булки")
    public void goToBunsSection(){
        BasePage basePage = new BasePage(rule.getWebDriver());
        basePage
                .clickOnFillingsButton()
                .clickOnBunsButton()
                .nextTwoButtonsAreNotSelected();
    }

    @After
    public void tearDown(){
        clearBrowserCookies();
    }
}