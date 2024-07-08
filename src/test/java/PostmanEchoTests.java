import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class PostmanEchoTests {

    @BeforeClass
    public void setUp() {

        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void testGetMethod() {
        Response response = RestAssured.get("/get");

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.jsonPath().get("args.foo1"), "bar1");
        Assert.assertEquals(response.jsonPath().get("args.foo2"), "bar2");
    }

    @Test
    public void testPostMethod() {
        Response response = given()
                .param("key1", "value1")
                .param("key2", "value2")
                .post("/post");

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.jsonPath().get("form.key1"), "value1");
        Assert.assertEquals(response.jsonPath().get("form.key2"), "value2");
    }

    @Test
    public void testPutRequest() {
        given()
                .contentType("application/json")
                .body("{\"foo1\":\"bar1\",\"foo2\":\"bar2\"}")
                .when()
                .put("https://postman-echo.com/put")
                .then()
                .statusCode(200)
                .body("json.foo1", equalTo("bar1"))
                .body("json.foo2", equalTo("bar2"));
    }

    @Test
    public void testPatchRequest() {
        given()
                .contentType("application/json")
                .body("{\"foo\":\"bar\"}")
                .when()
                .patch("https://postman-echo.com/patch")
                .then()
                .statusCode(200)
                .body("json.foo", equalTo("bar"));
    }

    @Test
    public void testDeleteRequest() {
        given()
                .contentType("application/json")
                .when()
                .delete("https://postman-echo.com/delete")
                .then()
                .statusCode(200);
    }

}