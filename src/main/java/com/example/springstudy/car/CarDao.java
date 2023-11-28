package com.example.springstudy.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("postgres")
public class CarDao implements CarRepository{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void dbInit() {
        String createCarTableSql = "CREATE TABLE CAR(CAR_ID INTEGER, BRAND VARCHAR(255), MODEL VARCHAR(255), COLOR VARCHAR(255) );";
        jdbcTemplate.update(createCarTableSql);
        cleanUp();
        save(new Car(1, "Fiat", "126p", "red"));
        save(new Car(2, "Fiat", "126p", "blue"));
        save(new Car(3, "Audi", "a3", "black"));
        save(new Car(4, "Audi", "a4", "silver"));

        System.out.println("\nCars with Brand equal to Audi:");
        getCarsByBrand("Audi").forEach(System.out::println);
    }

    public void save(Car car) {
        String insertCar = "INSERT INTO CAR VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(insertCar, new Object[]{car.getCarId(), car.getBrand(), car.getModel(), car.getColor()});
    }

    private void cleanUp() {
        String deleteAllRows = "DELETE FROM CAR;";
        jdbcTemplate.update(deleteAllRows);
    }

    public List<Car> getCarsByBrand(String brand) {
        String selectByBrand = "SELECT * FROM CAR WHERE BRAND=?";
        return jdbcTemplate.query(selectByBrand,
                new Object[]{brand},
                (rs, row) -> new Car(rs.getLong("CAR_ID"), rs.getString("BRAND"), rs.getString("MODEL"), rs.getString("COLOR")));
    }
}
