package driver;

public class YandexRule extends DriverFactory{

    @Override
    protected String getDriverPath() {
        return "src/test/java/resources/yandexdriver.exe";
    }
}
