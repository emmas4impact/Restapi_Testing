package com.spotify.oauth.api.ApplicationAPi;

import com.spotify.oauth.api.RestResource;
import com.spotify.oauth.api.TokenManager;
import com.spotify.oauth.utils.ConfigLoader;
import com.spotify.oauth.pojo.Playlist;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth.api.Routes.PLAYLISTS;
import static com.spotify.oauth.api.Routes.USERS;
import static io.restassured.RestAssured.given;


public class PlaylistApi {
    @Step
    public static Response post(Playlist requestPlaylist){
        return RestResource.post(USERS+"/"+ ConfigLoader.getInstance().getUserId()+PLAYLISTS, TokenManager.getToken(),requestPlaylist);
    }

    public static Response post(String token , Playlist requestPlaylist){
        return RestResource.post(USERS+"/"+ ConfigLoader.getInstance().getUserId(), token,requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS+"/"+playlistId, TokenManager.getToken());
    }


    public static Response put(Playlist requestPlaylist, String playlistId){
        return RestResource.put(PLAYLISTS+"/"+playlistId, TokenManager.getToken(),requestPlaylist);
    }

}
