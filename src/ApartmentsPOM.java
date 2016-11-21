import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ApartmentsPOM {

    private WebDriver driver;

    public ApartmentsPOM(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        String URL = "https://sasapps.byu.edu/services/family/area.aspx";
        driver.get(URL);
    }

    public boolean nothingAvailable() {
        return driver.getPageSource().contains("Nothing Available");
    }

    public boolean selectApartment(List<String> apartmentNumbers, boolean[] numberRooms) {
        String apartmentsDivXpath = "id('content')/table/tbody/tr/td[3]/div";
        WebElement apartmentsContainerDiv = driver.findElement(By.xpath(apartmentsDivXpath));
        List<WebElement> apartmentDivs = apartmentsContainerDiv.findElements(By.tagName("div"));

        for (WebElement apartmentDiv : apartmentDivs) {
            if (hasCorrectNumberRooms(apartmentDiv.getText(), numberRooms) && hasCorrectApartmentNumber(apartmentDiv.getText(), apartmentNumbers)) {
                apartmentDiv.findElement(By.tagName("a")).click();
                try {Thread.sleep(2000);} catch (Exception e){}
                return true;
            }
        }
        return false;
    }

    private boolean hasCorrectNumberRooms(String apartmentText, boolean[] numberRooms) {
        if (numberRooms == null || numberRooms.length == 0)
            return true;

        for (int i = 0; i < numberRooms.length; i++) {
            if (!numberRooms[i])
                continue;
            if (apartmentText.contains(String.format("Bedrooms: %d", i+1))) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCorrectApartmentNumber(String apartmentText, List<String> apartmentNumbers) {
        if (apartmentNumbers == null || apartmentNumbers.size() == 0)
            return true;

        for (String apartmentNumber : apartmentNumbers) {
            if (apartmentNumber == null || apartmentNumber.trim().isEmpty())
                continue;
            if (apartmentText.contains(apartmentNumber.trim())) {
                return true;
            }
        }
        return false;
    }

    public void proceed() {
        driver.findElement(By.id("ctl00_Content_HoldButton")).click();
    }
}
