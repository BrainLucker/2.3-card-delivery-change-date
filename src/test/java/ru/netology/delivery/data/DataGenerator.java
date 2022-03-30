package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        val date = LocalDate.now().plusDays(shift).format(formatter);
        return date;
    }

    public static String generateCity(String locale) {
        val faker = new Faker(new Locale(locale));
        val city = faker.address().cityName();
        return city;
    }

    public static String generateName(String locale) {
        val faker = new Faker(new Locale(locale));
        val name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        val faker = new Faker(new Locale(locale));
        val phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            val user = new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}