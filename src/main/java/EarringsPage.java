import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EarringsPage {

    private static DBWorker worker = new DBWorker();
    private WebDriver driver;

    By imageLink = By.xpath("//picture/img");
    By nameLink = By.xpath("//h3[@class='catalog-card__name']");
    By designerLink = By.xpath("//div[@class='catalog-card__designer']/a");

    By nameHeader = By.xpath("//h1[@class='product-main-info__product-name']");
    By designerHeader = By.xpath("//b[@class='product-main-info__designer-name']");


    public EarringsPage(WebDriver driver) {
        this.driver = driver;
    }

    public EarringsPage clickOnImageLink() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", driver.findElement(imageLink));
        return this;
    }

    public EarringsPage clickOnNameLink() {
        List<WebElement> elements = driver.findElements(nameLink);
        elements.get(1).click();
        return this;
    }

    public EarringsPage clickOnDesignerLink() {
        List<WebElement> elements = driver.findElements(designerLink);
        elements.get(2).click();
        return this;
    }

    public String getImageHeader() {
        List<WebElement> elements = driver.findElements(imageLink);
        return elements.get(0).getAttribute("alt");
    }

    public String getNameHeader() {
        List<WebElement> elements = driver.findElements(nameLink);
        return elements.get(1).getAttribute("textContent");
    }

    public String getDesignerHeader() {
        List<WebElement> elements = driver.findElements(designerLink);
        return elements.get(2).getAttribute("textContent");
    }

    public String getHeader() {
        return driver.findElement(nameHeader).getText();
    }

    public String getNextDesignerHeader() {
        return driver.findElement(designerHeader).getText();
    }


    public int countEarrings() {
        worker = new DBWorker();
        int id = 0;
        String query = "SELECT COUNT(*)id from item " +
                "JOIN designer ON item.designer_id = designer.id " +
                "JOIN catalog ON item.catalog_id = catalog.id " +
                "JOIN item_sku ON item.id = item_sku.item_id " +
                "JOIN sku_picture_list ON item_sku.id = sku_picture_list.sku_id " +
                "where EXISTS (SELECT * FROM item_sku WHERE item_sku.id = sku_picture_list.sku_id and (tag_id = 1 or tag_id = 4))" +
                "and catalog_id=1 and is_archive = 0 and price != 0" +
                " and item_sku.url is not null and item_sku.show != 0 and catalog.show !=0 ";

        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //worker.getSession().disconnect();

        return id;
    }


    public List<String> getNames() {
        worker = new DBWorker();
        String name;
        List<String> text = new ArrayList<>();
        String query = "SELECT item_sku.name from item " +
                "JOIN designer ON item.designer_id = designer.id " +
                "JOIN catalog ON item.catalog_id = catalog.id " +
                "JOIN item_sku ON item.id = item_sku.item_id " +
                "JOIN sku_picture_list ON item_sku.id = sku_picture_list.sku_id " +
                "JOIN storage_stock ON item_sku.id = storage_stock.sku_id " +
                "where EXISTS (SELECT * FROM item_sku WHERE item_sku.id = sku_picture_list.sku_id and (tag_id = 1 or tag_id = 4))" +
                "and catalog_id=1 and is_archive = 0 and price != 0" +
                " and item_sku.url is not null and item_sku.show != 0 and catalog.show !=0 and balance > 0" +
                " group by item_sku.id ";

        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                name = resultSet.getString("name");
//                System.out.println(name);
                text.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //worker.getSession().disconnect();
//        System.out.println("метод getNames: " + text);

        return text;
    }


    public List<String> getDesigners() {
        worker = new DBWorker();
        String designer;
        List<String> text = new ArrayList<>();
        String query = "SELECT designer.name from item " +
                "JOIN designer ON item.designer_id = designer.id " +
                "JOIN catalog ON item.catalog_id = catalog.id " +
                "JOIN item_sku ON item.id = item_sku.item_id " +
                "JOIN sku_picture_list ON item_sku.id = sku_picture_list.sku_id " +
                "JOIN storage_stock ON item_sku.id = storage_stock.sku_id " +
                "where EXISTS (SELECT * FROM item_sku WHERE item_sku.id = sku_picture_list.sku_id and (tag_id = 1 or tag_id = 4))" +
                "and catalog_id=1 and is_archive = 0 and price != 0" +
                " and item_sku.url is not null and item_sku.show != 0 and catalog.show !=0 and balance > 0" +
                " group by item_sku.id ";

        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                designer = resultSet.getString("name");
//                System.out.println(designer);
                text.add(designer);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //worker.getSession().disconnect();

//        System.out.println("метод getDesigner: " + text);

        return text;
    }

    public List<Integer> getPrice() {
        worker = new DBWorker();
        int price;
        List<Integer> text = new ArrayList<>();
        String query = "SELECT item_sku.price from item " +
                "JOIN designer ON item.designer_id = designer.id " +
                "JOIN catalog ON item.catalog_id = catalog.id " +
                "JOIN item_sku ON item.id = item_sku.item_id " +
                "JOIN sku_picture_list ON item_sku.id = sku_picture_list.sku_id " +
                "JOIN storage_stock ON item_sku.id = storage_stock.sku_id " +
                "where EXISTS (SELECT * FROM item_sku WHERE item_sku.id = sku_picture_list.sku_id and (tag_id = 1 or tag_id = 4))" +
                "and catalog_id=1 and is_archive = 0 and price != 0" +
                " and item_sku.url is not null and item_sku.show != 0 and catalog.show !=0 and balance > 0" +
                " group by item_sku.id ";
        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                price = resultSet.getInt("price");
//                System.out.println(price);
                text.add(price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //worker.getSession().disconnect();
//        System.out.println("метод getPrice: " + text);
        return text;
    }


//    public List<Integer> getPicture() {
//        worker = new DBWorker();
//        int id;
//        List<Integer> text = new ArrayList<>();
//        String query = "SELECT sku_picture_list.id from item " +
//                "JOIN designer ON item.designer_id = designer.id " +
//                "JOIN item_sku ON item.id = item_sku.item_id " +
//                "JOIN sku_picture_list ON item_sku.id = sku_picture_list.sku_id " +
//                "where EXISTS (SELECT * FROM item_sku WHERE item_sku.id = sku_picture_list.sku_id and tag_id = 1)" +
//                "and catalog_id=1 and is_archive = 0 and price != 0 and item_sku.url is not null"+
//                " group by item_sku.id ";
//
//        try {
//            Statement statement = worker.getCon().createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                id = resultSet.getInt("id");
////                System.out.println(id);
//                text.add(id);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        //worker.getSession().disconnect();
//
////        System.out.println("метод getPicture: " + text);
//        return text;
//    }


    public static void main(String[] args) {
        int id = 0;
        String query = "SELECT COUNT(DISTINCT item_sku.id) as count from item " +
                "JOIN catalog ON item.catalog_id = catalog.id " +
                "JOIN item_sku ON item.id = item_sku.item_id " +
                "JOIN sku_picture_list ON item_sku.id = sku_picture_list.sku_id " +
                "JOIN storage_stock ON item_sku.id = storage_stock.sku_id " +
                "where EXISTS (SELECT * FROM item_sku WHERE item_sku.id = sku_picture_list.sku_id and (tag_id = 1 or tag_id = 4))" +
                "and catalog_id=1 and is_archive = 0 and price != 0" +
                " and item_sku.url is not null and item_sku.show != 0 and catalog.show !=0 ";
        try {
            Statement statement = worker.getCon().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                id = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //worker.getSession().disconnect();
        System.out.println(id);
    }

}
