package ru.tinkoff.hibernate.test;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.fintech.qa.homework.db.hibernate.DbHibernateService;
import ru.fintech.qa.homework.db.hibernate.models.Animal;
import ru.fintech.qa.homework.db.hibernate.models.Places;
import ru.fintech.qa.homework.db.hibernate.models.Workman;
import ru.fintech.qa.homework.db.hibernate.models.Zoo;
import ru.fintech.qa.homework.utils.BeforeUtils;

import java.util.List;

public class ServiceTests {
    private static DbHibernateService dbHibernateService;

    @BeforeAll
    public static void beforeAll() {
        dbHibernateService = new DbHibernateService();
        BeforeUtils.createData();
    }

    @Test
    public void animalTableSizeTest() {
        List<Animal> animalList = dbHibernateService.getAllFromTable(Animal.class);
        Assertions.assertEquals(10, animalList.size());
    }

    @Test
    public void insertAnimalWithSameIdTest() {

        Animal animal = new Animal();
        animal.setAge(5);
        animal.setSex(1);
        animal.setName("Rex");
        animal.setPlace(3);
        animal.setType(1);
        for (int i = 1; i <= 10; i++) {
            animal.setId(i);
            Assertions.assertEquals(dbHibernateService.putRow(Animal.class, animal), TransactionStatus.ROLLED_BACK);
        }
    }

    @Test
    public void insertWorkmanWithNullNameTest() {

        Workman workman = new Workman();
        workman.setId(dbHibernateService.getMaxId(Workman.class) + 1); // чтобы id был уникальным
        workman.setName(null);
        workman.setAge(5);
        workman.setPosition(2);

        Assertions.assertEquals(dbHibernateService.putRow(Workman.class, workman), TransactionStatus.ROLLED_BACK);
    }

    @Test
    public void insertPlacesRowsCountTest() {

        Places places = new Places();
        places.setId(dbHibernateService.getMaxId(Places.class) + 1); // чтобы id был уникальным
        places.setName("Rex");
        places.setPlaceNum(1);
        places.setRow(2);

        dbHibernateService.putRow(Places.class, places);

        List<Places> placesList = dbHibernateService.getAllFromTable(Places.class);
        Assertions.assertEquals(6, placesList.size());
    }

    @Test
    public void zooRowsCountByNameTest() {
        List<Zoo> zooList = dbHibernateService.getAllFromTable(Zoo.class);
        Assertions.assertEquals(3, zooList.size()); // проверяем, что изначально 3 записи

        // Проверяем, что каждое название соответствуют заданию в 1 экзмепляре
        Assertions.assertEquals(3, dbHibernateService.getRowsCountByName(Zoo.class));
    }
}
