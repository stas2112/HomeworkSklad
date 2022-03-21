package ru.digitalleague;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.digitalleague.storage_example.Storage;

import static org.junit.Assert.*;

/**
 * Юнит тесты, которые проверяет основную логику склада
 */
public class UnitTests {
    String firstNameProduct = "productname";
    String secondNameProduct = "JAVA";
    String thirdNameProduct = "JavAProdUcT";
    String negativeCyrillicLetters = "Негативный тест";
    String negativeNameWithNumbers = "negat1veT3stW1thC0unt";
    @After
    public void aTEST(){
        Storage.clearingClassData();
    }
    @Test
    /**
     * Работа функции наличия вещи на складе | позитивный тест
     */
    public void productExistsInStoragePositiveTest(){
        Storage.addObject(firstNameProduct, 5);
        assertTrue(Storage.isInStock(firstNameProduct));
    }
    @Test
    /**
     * Работа функции наличия вещи на складе | негативный тест
     */
    public void productExistsInStorageNegativeTest(){
        assertFalse(Storage.isInStock(firstNameProduct));
    }

    @Test
    /**
     * буквы Латинского алфавита нижнего регистра 10 позиций, латинского алфавита верхнего регистра 10 позиций, а так же Верхнего и нижнего регистра 1 позиция
     * Проверка, что после добавление 3 товаров полок больше нет
     * | позитивный тест
     */
    public void positiveTestWithLowerCase(){
        Storage.addObject(firstNameProduct, 10);
        Storage.addObject(secondNameProduct, 10);
        Storage.addObject(thirdNameProduct, 1);
        int freePlacesInStorage= Storage.getFreePlaces();

        assertTrue(Storage.isInStock(firstNameProduct));
        assertTrue(Storage.isInStock(secondNameProduct));
        assertTrue(Storage.isInStock(thirdNameProduct));
        assertEquals(0,freePlacesInStorage );
    }
    @Test
    /**
     * Проверка "Колличества свободных полок | позитивный тест
     */
    public void checkFreeShelfsPositiveTest(){
        assertEquals(3,Storage.getFreePlaces());
        Storage.addObject(firstNameProduct, 3);
        assertEquals(2,Storage.getFreePlaces());
        Storage.addObject(thirdNameProduct, 5);
        assertEquals(1,Storage.getFreePlaces());
        Storage.addObject(secondNameProduct, 4);
        assertEquals(0,Storage.getFreePlaces());
    }
    @Test
    /**
     * Пытаемся загрузить на полку больше максимальных 10 товара | негативный тест
     */
    public void negativeTestWithShelfOverflow(){
        Storage.addObject(firstNameProduct, 11);
        assertFalse(Storage.isInStock(firstNameProduct));
    }
    @Test
    /**
     * Добавить товар, когда 3 полки заняты | негативный тест
     */
    public void negativeTestWithOverflowOfTheNumberOfShelves(){
        positiveTestWithLowerCase();
        Storage.addObject("ItsNegativeTest", 9);
        assertFalse(Storage.isInStock("ItsNegativeTest"));
    }
    @Test
    /**
     * Добавить товар с цифрами в названии | негативный тест
     */
    public void negativeTestWithInputCyrillicLetters(){
        Storage.addObject(negativeCyrillicLetters, 1);
        Assert.assertFalse(Storage.isInStock(negativeCyrillicLetters));

    }
    @Test
    /**
     * Добавить товар с цифрами в названии | негативный тест
     */
    public void negativeTestWithNumbersInName(){
        Storage.addObject(negativeNameWithNumbers, 1);
        Assert.assertFalse(Storage.isInStock(negativeNameWithNumbers));
    }
    @Test
    /**
     * Добавить 0 товара на полку | негативный тест
     */
    public void AddZeroProductNegativeTest(){
        Storage.addObject(firstNameProduct, 0);
        Assert.assertFalse(Storage.isInStock(firstNameProduct));
    }
    @Test
    /**
     * Удалить товар со склада | позитивный тест
     */
    public void removingProductFromStoragePositiveTest(){
        positiveTestWithLowerCase();
        Storage.removeObject(firstNameProduct);
        assertFalse(Storage.isInStock(firstNameProduct));
    }
    @Test
    /**
     * Добавление вещи к уже существующей | позитивный тест
     */
    public void addingProductToExistingOnePositiveTest(){
        positiveTestWithLowerCase();
        Storage.addObject(thirdNameProduct, 9);
        assertEquals(10, Storage.getProductAmount(thirdNameProduct));
    }
    @Test
    /**
     * Добавление вещи к уже существующей, переполняя текущую полку | негативный тест
     */
    public void addingProductToExistingOneNegativeTest(){
        positiveTestWithLowerCase();
        Storage.addObject(thirdNameProduct, 10);
        assertEquals(1, Storage.getProductAmount(thirdNameProduct));
    }

}
