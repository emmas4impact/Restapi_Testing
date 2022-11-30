package com.spotify.oauth2.api.ApplicationAPi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Routes.PLAYLISTS;
import static com.spotify.oauth2.api.Routes.USERS;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.api.TokenManager.getToken;
import static io.restassured.RestAssured.given;


public class PlaylistApi {
    @Step
    public static Response post(Playlist requestPlaylist){
        return RestResource.post(USERS+"/"+ ConfigLoader.getInstance().getUserId()+PLAYLISTS,getToken(),requestPlaylist);
    }

    public static Response post(String token , Playlist requestPlaylist){
        return RestResource.post(USERS+"/"+ ConfigLoader.getInstance().getUserId(), token,requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS+"/"+playlistId,getToken());
    }


    public static Response put(Playlist requestPlaylist, String playlistId){
        return RestResource.put(PLAYLISTS+"/"+playlistId, getToken(),requestPlaylist);
    }

}
