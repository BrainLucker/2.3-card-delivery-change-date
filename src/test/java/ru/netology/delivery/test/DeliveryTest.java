package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1000x800";
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        var notification = "Встреча успешно запланирована на ";

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").val(validUser.getCity());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(firstMeetingDate);
        form.$("[data-test-id=name] input").val(validUser.getName());
        form.$("[data-test-id=phone] input").val(validUser.getPhone());
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").should(appear).shouldHave(text(notification + firstMeetingDate));
        // Повторная отправка формы с новой датой
        form.$("[data-test-id=date] input.input__control").doubleClick().sendKeys(secondMeetingDate);
        form.$$("button").find(exactText("Запланировать")).click();
        $$("[data-test-id=replan-notification] button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").should(appear).shouldHave(text(notification + secondMeetingDate));
    }
}
