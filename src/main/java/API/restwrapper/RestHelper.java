package API.restwrapper;

import API.utils.Endpoints;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestHelper {
    public static <T> T restPost(String URL, Endpoints endpoint, Map<String, String> headers, Object bodyData, Class<T> responseClass) {
        return given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(bodyData)
                .when()
                .post(URL.concat(endpoint.getValue()))
                .then()
                .extract()
                .as(responseClass);
    }
    public static <T> T restPut(String URL, Endpoints endpoint, Map<String, String> headers, Object bodyData, Class<T> responseClass) {
        return given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(bodyData)
                .when()
                .put(URL.concat(endpoint.getValue()))
                .then()
                .extract()
                .as(responseClass);
    }

    public static <T> T restGet(String URL, Endpoints endpoint, Map<String, String> headers, Class<T> responseClass) {
        return given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .when()
                .get(URL.concat(endpoint.getValue()))
                .then()
                .extract()
                .as(responseClass);
    }

    public static <T> T restGetWithRequestParameters(String URL, Endpoints endpoint, Map<String, String> headers,Map<String, String> queryParameters, Class<T> responseClass) {
        return given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .queryParams(queryParameters)
                .when()
                .get(URL.concat(endpoint.getValue()))
                .then()
                .extract()
                .as(responseClass);
    }
    public static Response restGetWithRequestParameters(String URL, Endpoints endpoint, Map<String, String> headers, Map<String, String> queryParameters) {
        return given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .queryParams(queryParameters)
                .when()
                .get(URL.concat(endpoint.getValue()))
                .then()
                .extract()
                .response();
    }
    public static int restPostResponseStatusCode(String URL, Endpoints endpoint, Map<String, String> headers, Object bodyData) {
        return given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(bodyData)
                .when()
                .post(URL.concat(endpoint.getValue()))
                .then()
                .extract()
                .response().statusCode();
    }
    public static int restGetResponseStatusCode(String URL,Endpoints endpoint, Map<String, String> headers) {
        return given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .when()
                .get(URL.concat(endpoint.getValue()))
                .then()
                .extract()
                .response().statusCode();
    }
    public static int restPutResponseStatusCode(String URL, Endpoints endpoint, Map<String, String> headers, Object bodyData) {
        return given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(bodyData)
                .when()
                .put(URL.concat(endpoint.getValue()))
                .then()
                .extract()
                .response().statusCode();
    }
    public static Map<String, Object> restPostWithBodyAndStatus(String URL, Endpoints endpoint, Map<String, String> headers, Object bodyData, Class<?> responseClass) {
        Response response = given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(bodyData)
                .when()
                .post(URL.concat(endpoint.getValue()));

        Map<String, Object> result = new HashMap<>();
        result.put("responseStatusCode", response.statusCode());
        result.put("responseBody", response.then().extract().as(responseClass));
        return result;
    }
    public static Map<String, Object> restGetWithBodyAndStatus(String URL, String endpoint, Map<String, String> headers, Class<?> responseClass) {
        Response response = given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .when()
                .get(URL.concat(endpoint));

        Map<String, Object> result = new HashMap<>();
        result.put("responseStatusCode", response.statusCode());
        result.put("responseBody", response.then().extract().as(responseClass));
        return result;
    }
    public static Map<String, Object> restPutWithBodyAndStatus(String URL,String endpoint, Map<String, String> headers, Object bodyData, Class<?> responseClass) {
        Response response = given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(bodyData)
                .when()
                .put(URL.concat(endpoint));

        Map<String, Object> result = new HashMap<>();
        result.put("responseStatusCode", response.statusCode());
        result.put("responseBody", response.then().extract().as(responseClass));
        return result;
    }
}
