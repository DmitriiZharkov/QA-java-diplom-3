package restclients;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserStep {

    private static final String CREATE_USER = "/api/auth/register";
    private static final String LOGIN_USER = "/api/auth/login";
    private static final String DELETE_CHANGING_USER = "/api/auth/user";

    @Step("Send POST request to api/auth/register - создание пользователя")
    public Response create(UserApi user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(CREATE_USER);
    }

    @Step("Send POST request to api/auth/login - авторизация пользователя с почтой именем и паролем")
    public Response login(UserApi user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(LOGIN_USER);
    }

    @Step("Send POST request to api/auth/login - получение токена у юзера с почтой, паролем и именем")
    public String getToken(UserApi user){
        Response response = given()
                .header("Content-type", "application/json")
                .body(user)
                .post(LOGIN_USER);
        return response.jsonPath().getString("accessToken");
    }

    @Step("Send DELETE request to api/auth/user - удаление пользователя")
    public Response delete(UserApi user){
        String token = getToken(user);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .delete(DELETE_CHANGING_USER);
    }
}