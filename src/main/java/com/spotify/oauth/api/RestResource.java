package com.spotify.oauth.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth.api.Routes.API;
import static com.spotify.oauth.api.Routes.TOKEN;
import static com.spotify.oauth.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response post(String path, String token, Object requestPlaylist){
        return  given(getRequestSpec())
                .auth().oauth2(token)
                .body(requestPlaylist)
                .when().post(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();

    }
    public static Response postAccount(HashMap<String, String> formParams){
        return given(getAccountRequestSpec())
                .formParams(formParams)
                .when().post(API+TOKEN)
                .then().spec(getResponseSpec())
                .extract().response();
    }

    public static Response get(String path, String token){
        return  given(getRequestSpec())
                .header("Authorization", "Bearer "+token)
                .when().get(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();

    }

    public static Response put( String path, String token,Object requestPlaylist){
        return given(getRequestSpec())
                .auth().oauth2(token)
                .body(requestPlaylist)
                .when().put(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();

    }

}
