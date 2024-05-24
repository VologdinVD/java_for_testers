package ru.stqa.mantis.tests;

import common.CommonFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.mantis.model.RegistrationData;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class UserRegistrationTests extends TestBase {

    public static Stream<RegistrationData> regData() {
        Supplier<RegistrationData> randomRegData = () -> new RegistrationData()
                .withUserName(CommonFunctions.randomString(5))
                .withPassword(CommonFunctions.randomString(5));
        return Stream.generate(randomRegData).limit(1);

    }

    @ParameterizedTest
    @MethodSource("regData")
    public void canRegisterUser(RegistrationData registrationData) {
        var email = String.format("%s@localhost", registrationData.username());
        // создать пользователя (адрес) на почтовом сервере (JamesHelper)
        app.jamesCli().addUser(email, registrationData.password());
        // заполняем форму создания и отправляем (браузер)
        app.registration().signUpForNewAccount(registrationData.username(), email);
        // ждем почту (MailHelper)
        var messages = app.mail().receive(email, registrationData.password(), Duration.ofSeconds(60));
        // извлекаем ссылку из письма
        var url = app.mail().getLinkOfMail(messages);
        // проходим по ссылке и завершаем регистрацию (браузер)
        app.registration().verifyUser(url, registrationData.username(), registrationData.password(), registrationData.password());
        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        app.http().login(registrationData.username(), registrationData.password());
    }

    @ParameterizedTest
    @MethodSource("regData")
    public void canRegisterUserFromApi(RegistrationData registrationData) {
        var email = String.format("%s@localhost", registrationData.username());
        app.jamesApi().addUser(email, registrationData.password());

        app.rest().createUser(registrationData.username(), email);

        var messages = app.mail().receive(email, registrationData.password(), Duration.ofSeconds(60));
        var url = app.mail().getLinkOfMail(messages);

        app.registration().verifyUser(url, registrationData.username(), registrationData.password(), registrationData.password());
        app.http().login(registrationData.username(), registrationData.password());
        Assertions.assertTrue(app.http().isLoggedIn());
    }

}
