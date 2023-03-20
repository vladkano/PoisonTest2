package functionalTests;

import baseForTests.TestBase;
import basket.Basket;
import collectionAndSet.Collection;
import filters.Filters;
import filters.Size;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import sections.Wishlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Тесты избранного")
public class WishListTest extends TestBase {

    @BeforeEach
    public void setUp() {
        mainSetUp();
        wishlist = new Wishlist(driver);
        filters = new Filters(driver);
        size = new Size(driver);
        collection = new Collection(driver);
        basket = new Basket(driver);
    }

    /**
     * Вспомогательный метод для тестов:<p>
     * Добавление товара в избранное и сравнение наименования изделий в каталоге и в избранном
     */
    public void addItemToFavoritesAndCheckNames() {
        String itemName = wishlist.getItemName();
        wishlist.clickToAddToWishlistFromCatalogButton();
        wishlist.clickToWishListButton();
        String itemNameFromWishlist = wishlist.getItemNameFromFavorites();
        assertEquals(itemName.substring(0, 10), itemNameFromWishlist.substring(0, 10).toLowerCase());
    }

    /**
     * Проверка того, что товар добавился в избранное из карточки товара + урл и заголовок корректны.
     */
    @Test()
    @Description("Проверка того, что товар добавился в избранное из карточки товара + урл и заголовок корректны")
    public void wishListCheckButton() {
        driver.get(getUrl + "catalog/");
        wishlist.clickOnImageLink();
        wishlist.clickToWishListInCardListButton();
        wishlist.clickToWishListButton();
        int numbers = driver.findElements(By.xpath("//h3/a")).size();
        assertTrue(numbers > 0);
        String url = driver.getCurrentUrl();
        String header = wishlist.getWishListHeader();
        Assertions.assertAll(
                () -> assertEquals(getUrl + "wishlist/", url),
                () -> assertEquals("избранное", header));
    }

    /**
     * Блок тестов по добавлению в избранное со страниц каталога:<p>
     * Основной каталог
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Основной каталог")
    public void addToWishlistFromCatalog() {
        driver.get(getUrl + "catalog/");
        addItemToFavoritesAndCheckNames();
    }

    /**
     * Блок тестов по добавлению в избранное со страниц каталога:<p>
     * Браслеты
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Браслеты")
    public void addToWishlistFromBracelets() {
        driver.get(getUrl + "catalog/braslety/");
        addItemToFavoritesAndCheckNames();
    }

    /**
     * Блок тестов по добавлению в избранное со страниц каталога:<p>
     * Кольца
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Кольца")
    public void addToWishlistFromRings() {
        driver.get(getUrl + "catalog/koltsa/");
        addItemToFavoritesAndCheckNames();
    }

    /**
     * Серьги
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Серьги")
    public void addToWishlistFromEarrings() {
        driver.get(getUrl + "catalog/sergi/");
        addItemToFavoritesAndCheckNames();
    }

    /**
     * Колье
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Колье")
    public void addToWishlistFromNecklaces() {
        driver.get(getUrl + "catalog/kole/");
        addItemToFavoritesAndCheckNames();
    }

    /**
     * Броши
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Броши")
    public void addToWishlistFromBrooches() {
        driver.get(getUrl + "catalog/broshi/");
        addItemToFavoritesAndCheckNames();
    }

    /**
     * Мужики
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Мужики")
    public void addToWishlistFromMen() {
        driver.get(getUrl + "catalog/men/");
        addItemToFavoritesAndCheckNames();
    }


    /**
     * Новинки
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Новинки")
    public void addToWishlistFromNewItems() {
        driver.get(getUrl + "catalog/new/");
        addItemToFavoritesAndCheckNames();
    }

    /**
     * Sale
     */
    @Test()
    @Description("Блок тестов по добавлению в избранное со страниц каталога: Sale")
    public void addToWishlistFromSale() {
        driver.get(getUrl + "catalog/sale/");
        addItemToFavoritesAndCheckNames();
    }


    /**
     * Блок тестов по переносу товара из избранного в корзину:<p>
     * Добавление в избранное из каталога:<p>
     * Товар без размера.
     */
    @Test()
    @Description("Блок тестов по переносу товара из избранного в корзину. Добавление в избранное из каталога: Товар без размера.")
    public void favoritesToBasket() {
        driver.get(getUrl + "catalog/sergi/");
        String itemName = wishlist.getItemName();
        wishlist.clickToAddToWishlistFromCatalogButton();
        wishlist.clickToWishListButton();
        String itemNameFromWishlist = wishlist.getItemNameFromFavorites().toLowerCase();
        wishlist.clickToTransferToBasketButton();
        wishlist.clickToMoveToBasketButton();
        String basketProductName = wishlist.getBasketProductName().toLowerCase();
        Assertions.assertAll(
                () -> assertEquals(itemName, itemNameFromWishlist),
                () -> assertEquals(itemNameFromWishlist.substring(0, 20), basketProductName.substring(0, 20)));
    }

    //выбор размера в избранном отключен

    /**
     * Товар с размером.
     */
    @Test()
    @Description("Блок тестов по переносу товара из избранного в корзину. Добавление в избранное из каталога: Товар с размером.")
    public void favoritesToBasketWithSize() {
        driver.get(getUrl + "catalog/");
        filters.clickToOkButton();
        filters.clickToFilterButton();
        size.clickToSizeButton();
        size.clickToThirdSizeButton();
        filters.clickToShowProductsButton();
        String itemName = wishlist.getItemName();
        wishlist.clickToAddToWishlistFromCatalogButton();
        wishlist.clickToWishListButton();
        String itemNameFromWishlist = wishlist.getItemNameFromFavorites().toLowerCase();
        wishlist.clickToTransferToBasketButton();
        sleep(1000);
        String wishListProductSize = wishlist.getWishListProductSize();
        System.out.println(wishListProductSize);
        wishlist.clickToTransferToBasketWithSizeButton();
        wishlist.clickToMoveToBasketButton();
        String basketProductName = wishlist.getBasketProductName().toLowerCase();
        String basketProductSize = wishlist.getBasketProductSize();
        Assertions.assertAll(
                () -> assertEquals(itemName.substring(0, 20), itemNameFromWishlist.substring(0, 20)),
                () -> assertEquals(itemNameFromWishlist.substring(0, 20), basketProductName.substring(0, 20)),
                () -> assertEquals(itemNameFromWishlist.substring(0,30) + "\n" + "Размер: " + wishListProductSize,
                        basketProductName.substring(0,30) +  "\n" + basketProductSize));
    }

    /**
     * Добавление в избранное из карточки товара:<p>
     * Товар из коллекции без размера.
     */
    @Test()
    @Description("Блок тестов по переносу товара из избранного в корзину. Добавление в избранное из карточки товара: Товар из коллекции без размера.")
    public void favoritesToBasketWithCollection() {
        driver.navigate().to(basket.getSecondLinkOfCollection());
        String itemName = wishlist.getHeader();
        wishlist.clickToWishListInCardListButton();
        wishlist.clickToWishListButton();
        String itemNameFromWishlist = wishlist.getItemNameFromFavorites();
        wishlist.clickToTransferToBasketButton();
        wishlist.clickToMoveToBasketButton();
        String basketProductName = wishlist.getBasketProductName();
        Assertions.assertAll(
                () -> assertEquals(itemName, itemNameFromWishlist),
                () -> assertEquals(itemNameFromWishlist.substring(0, 30), basketProductName.substring(0, 30)));
    }


    //выбор размера в избранном отключен

    /**
     * Добавление в избранное из карточки товара:<p>
     * Товар из коллекции с размером.
     */
    @Test()
    @Description("Блок тестов по переносу товара из избранного в корзину. Добавление в избранное из карточки товара: Товар из коллекции с размером.")
    public void
    favoritesToBasketWithCollectionAndSize() {
        driver.get(getUrl + "catalog/");
        filters.clickToOkButton();
        filters.clickToFilterButton();
        size.clickToSizeButton();
        size.clickToSecondSizeButton();
        filters.clickToShowProductsButton();
        collection.clickOnFirstHref();
        String itemName = wishlist.getHeader();
        wishlist.clickToWishListInCardListButton();
        wishlist.clickToWishListButton();
        String itemNameFromWishlist = wishlist.getItemNameFromFavorites();
        wishlist.clickToTransferToBasketButton();
        String wishListProductSize = wishlist.getWishListProductSize();
        wishlist.clickToTransferToBasketWithSizeButton();
        wishlist.clickToMoveToBasketButton();
        String basketProductName = wishlist.getBasketProductName();
        String basketProductSize = wishlist.getBasketProductSize();
        Assertions.assertAll(
                () -> assertEquals(itemName.substring(0, 20), itemNameFromWishlist.substring(0, 20)),
                () -> assertEquals(itemNameFromWishlist.substring(0, 20), basketProductName.substring(0, 20)),
                () -> assertEquals(itemNameFromWishlist.substring(0,30) + "\n" + "Размер: " + wishListProductSize,
                        basketProductName.substring(0,30) +  "\n" + basketProductSize));
    }
    /**
     * Удаление товара из избранного:<p>
     *
     */
    @Test()
    @Description("Блок тестов по удалению товаров из избранного")
    public void deleteItemToWishlist() {
        driver.get(getUrl + "catalog/");
        wishlist.clickToAddToWishlistFromCatalogButton();
        wishlist.clickToWishListButton();
        wishlist.clickToDeleteFromWishlist();
    }

}
