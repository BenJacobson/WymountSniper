import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class BYUCASPOM {

    private WebDriver driver;
    private final String URL = "https://cas.byu.edu/cas/login";

    public BYUCASPOM(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        driver.get(URL);
    }

    public void login(String username, String password) {
        driver.findElement(By.id("netid")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("submit")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) { }
    }

    public boolean loginSuccessful() {
        return driver.getPageSource().contains("Sign-in Successful");
    }

}
