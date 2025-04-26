package praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class BurgerConstructorTest {
    private WebDriver driver;

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Before
    @Step("Инициализация и открытие браузера")
    public void setUp() {
        WebDriver driver = driverRule.getDriver();
        driver.get(EnvConfig.BASE_URL);
    }

    @Test
    @DisplayName("Проверка переходов к разделам: «Булки», «Соусы», «Начинки»")
    public void testConstructorSectionNavigation() {
        WebDriver driver = driverRule.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesTab();

        WebElement activeSaucesTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span[text()='Соусы']")));
        assertTrue("Раздел 'Соусы' должен быть активным", activeSaucesTab.isDisplayed());

        mainPage.clickFillingsTab();

        WebElement activeFillingsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span[text()='Начинки']")));
        assertTrue("Раздел 'Начинки' должен быть активным", activeFillingsTab.isDisplayed());

        mainPage.clickBunsTab();

        WebElement activeBunsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span[text()='Булки']")));
        assertTrue("Раздел 'Булки' должен быть активным", activeBunsTab.isDisplayed());
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
