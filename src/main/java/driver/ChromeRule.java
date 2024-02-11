package driver;

public class ChromeRule extends DriverFactory {

    @Override
    protected String getDriverPath() {
        return "src/test/java/resources/chromedriver.exe";
    }
}