import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;


public class WymountSniper {

    private String browser;
    private String netID;
    private String password;

    public WymountSniper(String browser, String netID, String password) {
        this.browser = browser;
        this.netID = netID;
        this.password = password;
    }

    public void snipe(List<String> apartmentNumbers, boolean[] numberRooms) {
        WebDriver driver;
        try {
            driver = GetWebDriver();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error initializing browser: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!authenticate(driver)) {
            JOptionPane.showMessageDialog(null, "Unable to authenticate with BYU", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ApartmentsPOM apartmentsPOM = new ApartmentsPOM(driver);
        apartmentsPOM.navigate();
        apartmentsPOM.navigate();

        while (apartmentsPOM.nothingAvailable()) {
            apartmentsPOM.navigate();
        }

        try {
            boolean success = apartmentsPOM.selectApartment(apartmentNumbers, numberRooms);
            if (!success) {
                JOptionPane.showMessageDialog(null, "None of the available apartments fit your specified needs.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred while try to select an apartment according to your specified needs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        apartmentsPOM.proceed();

    }

    private WebDriver GetWebDriver() throws Exception {
        switch (browser) {
            case "chrome": {
                loadDriver("webdriver.chrome.driver", "lib/chromedriver.exe");
                return new ChromeDriver();
            }
            case "firefox": {
                loadDriver("webdriver.gecko.driver", "lib/geckodriver.exe");
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("marionette", true);
                return new FirefoxDriver(capabilities);
            }
            default:
                throw new Exception("browser not supported: " + browser);
        }
    }

    private void loadDriver(String driverProperty, String driverPath) throws  Exception {
        File driverFile = new File(driverPath);
        if (!driverFile.exists()) {
            driverFile.getParentFile().mkdirs();
            InputStream driverStream = this.getClass().getResourceAsStream(driverPath.replace("lib", ""));
            Files.copy(driverStream, driverFile.toPath());
        }
        System.setProperty(driverProperty, driverPath);
    }

    private boolean authenticate(WebDriver driver) {
        BYUCASPOM authenticationPage = new BYUCASPOM(driver);
        authenticationPage.navigate();
        authenticationPage.login(netID, password);
        return authenticationPage.loginSuccessful();
    }

}
