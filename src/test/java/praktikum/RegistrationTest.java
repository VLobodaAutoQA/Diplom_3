package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.*;
import org.openqa.selenium.WebDriver;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RegistrationTest {

    private WebDriver driver;
    private RegistrationPage registrationPage;

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Before
    @Step("Инициализация и открытие браузера")
    public void setUp() {
        WebDriver driver = driverRule.getDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.openRegistrationPage();
    }

    @Test
    @Description("Проверка успешной регистрации")
    public void testSuccessfulRegistration() {
        WebDriver driver = driverRule.getDriver();
        RegistrationPage registrationPage = new RegistrationPage(driver)
                .openRegistrationPage();
        registrationPage.waitForModalToDisappear();
        registrationPage.register("Vloboda", "vloboda" + System.currentTimeMillis() + "@yandex.rus", "11111111");
        Assert.assertTrue("Ожидалась переадресация на страницу входа", registrationPage.isLoginPageVisible());
    }

    @Test
    @Description("Проверка ошибки при коротком пароле")
    public void testRegistrationWithShortPassword() {
        WebDriver driver = driverRule.getDriver();
        RegistrationPage registrationPage = new RegistrationPage(driver)
                .openRegistrationPage();
        registrationPage.waitForModalToDisappear();
        String email = generateRandomEmail();
        registrationPage.register("Короткий Пароль", email, "123");
        Assert.assertTrue("Ожидалась ошибка 'Некорректный пароль'", registrationPage.isPasswordErrorVisible());
    }

    private String generateRandomEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

