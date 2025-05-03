package main;

import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebTest {

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl = "http://localhost:8080";
    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebElement findElement(By path) {
        return wait.until(ExpectedConditions.presenceOfElementLocated
                (path));
    }
    private void click(By path) {
        wait.until(ExpectedConditions.elementToBeClickable(
                path)).click();
    }
    private void clickInElement(By path,WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(
                webElement.findElement(path))).click();
    }
    private void clickWaitUrl(By path,String url) {
        wait.until(ExpectedConditions.elementToBeClickable(
                path)).click();
        wait.until(ExpectedConditions.urlContains(url));
    }
    private void clickWaitFullUrl(By path,String url) {
        wait.until(ExpectedConditions.elementToBeClickable(
                path)).click();
        wait.until(ExpectedConditions.urlToBe(url));
    }

    @Test
    void exampleTest() {
        driver.get(baseUrl);
        assertEquals("Книжный Интернет-магазин", driver.getTitle());
        click(By.linkText("Главная"));
        assertEquals("Книжный Интернет-магазин", driver.getTitle());

        click(By.linkText("Клиенты"));
        assertEquals("Клиенты", driver.getTitle());

        //Проверка поиска Клиентов
        driver.findElement(By.id("searchName")).sendKeys("H");
        click(By.xpath("//button[text()='Поиск']"));
        WebElement clientTable = findElement(By.xpath("//table[@class='table table-striped']"));
        WebElement tableBody = clientTable.findElement(By.xpath("//tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
        assertEquals(2, rows.size());

        driver.findElement(By.id("searchPhone")).sendKeys("+44 7700 900000");
        click(By.xpath("//button[text()='Поиск']"));
        clientTable = driver.findElement(By.xpath("//table[@class='table table-striped']"));
        tableBody = findElement(By.xpath("//tbody"));
        rows = tableBody.findElements(By.tagName("tr"));
        assertEquals(1, rows.size());

    }
    @Test
    void FunctionalityTest(){

        try {
            deleteTestClient();
        } catch (AssertionError e) {}
        try {
            deleteTestBook();
        } catch (AssertionError e) {}
        driver.get(baseUrl +"/books");
        //Проверка Поиска книг

        findElement(By.id("searchTitle")).sendKeys("T");
        click(By.xpath("//button[text()='Поиск']"));
        WebElement bookTable = findElement(By.xpath("//table[@class='table table-striped']"));
        WebElement tableBody = bookTable.findElement(By.xpath("//tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
        assertEquals(6, rows.size());


        findElement(By.id("searchAuthor")).sendKeys("w");
        click(By.xpath("//button[text()='Поиск']"));
        bookTable = findElement(By.xpath("//table[@class='table table-striped']"));
        tableBody = bookTable.findElement(By.xpath("//tbody"));
        rows = tableBody.findElements(By.tagName("tr"));
        assertEquals(5, rows.size());

        // Проверка добавления книги
        click(By.xpath("//a[text()='Добавить книгу в каталог']"));
        //Везде попытки добавить книгу без обязательных полей +click()
        click(By.xpath("//button[text()='Сохранить']"));
        assertEquals(baseUrl + "/books/add",driver.getCurrentUrl());

        findElement(By.id("title")).sendKeys("TestBook");
        click(By.xpath("//button[text()='Сохранить']"));
        assertEquals(baseUrl + "/books/add",driver.getCurrentUrl());

        findElement(By.id("authors")).sendKeys("TestAuthor");
        click(By.xpath("//button[text()='Сохранить']"));
        assertEquals(baseUrl + "/books/add",driver.getCurrentUrl());

        findElement(By.id("genre")).sendKeys("JustTestBookGenre");
        click(By.xpath("//button[text()='Сохранить']"));
        assertEquals(baseUrl + "/books/add",driver.getCurrentUrl());

        findElement(By.id("price")).sendKeys("543");
        click(By.xpath("//button[text()='Сохранить']"));
        assertEquals(baseUrl + "/books/add",driver.getCurrentUrl());

        findElement(By.id("available")).sendKeys("3");
        // Проверка перехода на страницу
        clickWaitFullUrl(By.xpath("//button[text()='Сохранить']"),baseUrl + "/books");

        // Проверка, что книга добавлена
        tableBody = findElement(By.xpath("//tbody"));
        rows = tableBody.findElements(By.tagName("tr"));
        assertEquals(11, rows.size());


        //ADD WRONG CLIENT
        driver.get(baseUrl + "/clients");
        click(By.xpath("//a[text()='Добавить клиента']"));
        assertEquals(baseUrl + "/clients/add",driver.getCurrentUrl());
        findElement(By.id("fullName")).sendKeys("Test Client");
        findElement(By.id("phone")).sendKeys("+44 7700 900000");
        findElement(By.id("address")).sendKeys("Test Address");
        click(By.xpath("//button[text()='Сохранить']"));
        assertTrue(isElementPresent(By.xpath("//div[@class='alert alert-danger']")));

        //ADD CLIENT
        findElement(By.id("fullName")).sendKeys("Test Client");
        findElement(By.id("phone")).sendKeys("+44 7700 911111");
        findElement(By.id("address")).sendKeys("Test Address");
        clickWaitFullUrl(By.xpath("//button[text()='Сохранить']"),baseUrl+"/clients");
        assertEquals(baseUrl + "/clients",driver.getCurrentUrl());

        //FIND CLIENT IN CLIENTS
        WebElement clientTable =findElement(By.xpath("//table[@class='table table-striped']"));
        tableBody = clientTable.findElement(By.xpath("//tbody"));
        rows = tableBody.findElements(By.tagName("tr"));
        Integer find = null;
        WebElement clientrow = null;
        for (WebElement row : rows) {
            List<WebElement> Cells = row.findElements(By.tagName("td"));
            if("Test Client".equals(Cells.get(1).getText())){
                find = Integer.parseInt(Cells.get(0).getText());
                clientrow = row;
            }
        }
        assertNotNull(find);

        //WORK WITH PERSONAL CLIENT PAGE
        clickInElement(By.tagName("a"),clientrow);
        assertEquals(baseUrl + "/clients/" + find,driver.getCurrentUrl());

        //ADD ORDER
        click(By.xpath("//a[contains(text(), 'Добавить Заказ')]"));
        assertEquals(baseUrl + "/clients/" + find + "/addOrder",driver.getCurrentUrl());

        findElement(By.id("creationTime")).sendKeys("12122025");
        findElement(By.id("creationTime")).sendKeys(Keys.ARROW_RIGHT);
        findElement(By.id("creationTime")).sendKeys("1212");
        findElement(By.id("address")).sendKeys("123 Short Address");
        findElement(By.id("delivTime")).sendKeys("12122025 1212");
        findElement(By.id("delivTime")).sendKeys(Keys.ARROW_RIGHT);
        findElement(By.id("delivTime")).sendKeys("1212");
        new Select(driver.findElement(By.id("status"))).selectByValue("pending");
        clickWaitUrl(By.xpath("//button[contains(text(), 'Создать заказ')]"),"orders");
        assertEquals("orders",driver.getCurrentUrl().split("/")[3]);
        Integer orderId = Integer.parseInt(driver.getCurrentUrl().split("/")[4]);
        //CHECK NEW ORDER
        clickWaitUrl(By.xpath("//a[contains(text(), 'Добавить Книгу')]"),"books");
        assertEquals(baseUrl +"/orders/" +orderId + "/books/add",driver.getCurrentUrl());
        WebElement table = findElement(By.tagName("tbody"));
        rows = table.findElements(By.tagName("tr"));
        Integer bookId = 0;
        for(WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if("TestBook".equals(cells.get(1).getText())) {
                bookId = Integer.parseInt(cells.get(0).getText());
                break;
            }
        }
        assertNotEquals(0,bookId);
        clickWaitFullUrl(By.xpath("//a[@href='/orders/"+ orderId +"/books/" + bookId +
                "/add' and contains(@class, 'btn-success')]"),baseUrl + "/orders/" + orderId);
        assertEquals("543",findElement(By.xpath("//input[@id='total']")).getAttribute("value"));
        //drop in delete testClient testBook
        deleteTestClient();
        deleteTestBook();
    }

    void deleteTestClient() {
        driver.get(baseUrl + "/clients");
        WebElement clientTable = findElement(By.xpath("//table[@class='table table-striped']"));
        WebElement tableBody = clientTable.findElement(By.xpath("//tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
        Integer clientId = 0;
        for (WebElement row : rows) {
            List<WebElement> Cells = row.findElements(By.tagName("td"));
            if("Test Client".equals(Cells.get(1).getText())){
                clientId = Integer.parseInt(Cells.get(0).getText());
                wait.until(ExpectedConditions.elementToBeClickable(Cells.get(3))).click();
                break;
            }
        }
        assertNotEquals(0,clientId);
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/clients/" + clientId));
        click(By.xpath("//a[contains(text(), 'Удалить')]"));
        org.openqa.selenium.Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        assertEquals(baseUrl + "/clients",driver.getCurrentUrl());
    }
    void deleteTestBook() {
        //DELETE testBook
        driver.get(baseUrl + "/books");
        WebElement bookTable = findElement(By.xpath("//table[@class='table table-striped']"));
        WebElement tableBody = bookTable.findElement(By.xpath("//tbody"));
        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> Cells = row.findElements(By.tagName("td"));
            if("TestBook".equals(Cells.get(1).getText())){
                wait.until(ExpectedConditions.elementToBeClickable(Cells.get(6))).click();
                break;
            }
        }
        assertNotEquals(baseUrl + "/books",driver.getCurrentUrl());
        click(By.xpath("//a[contains(text(), 'Удалить')]"));
        org.openqa.selenium.Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        assertEquals(baseUrl + "/books",driver.getCurrentUrl());
    }

}