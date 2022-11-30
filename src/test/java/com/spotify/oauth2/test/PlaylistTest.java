package com.spotify.oauth2.test;

import com.spotify.oauth2.api.ApplicationAPi.PlaylistApi;
import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static com.spotify.oauth2.api.ApplicationAPi.PlaylistApi.post;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify 0auth 2.0")
@Feature("Playlist API")
public class PlaylistTest extends BaseTest{
    @Story("Create a playlist story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("GPR-000")
    @Description("This is a post playlist")
    @Test(description = "Should be able to create a playlist")
    public void ShouldBeAbleToCreatePlaylist(){
        Playlist requestPlaylist =  playlistBuilder(generateName(), generateDescription() , false);

        Response response = post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);
    }
    @Story("Create a playlist story")
    @Test(description = "Should be able to get a playlist")
    public void ShouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Lady sings blues 1960",
                "An album by Billie Holiday in the 60s",false);

        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);


    }
    @Story("Create a playlist story")
    @Test(description = "Should be able to Update a playlist")
    public void ShouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription() , false);

        Response response = PlaylistApi.put(requestPlaylist, DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);

    }
    @Story("Unable to Create a playlist story")
    @Test(description = "Should not be able to create a playlist without name")
    public void ShouldNotBeAbleToCreatePlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("", generateDescription() ,false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);

        assertErrorCode(response.as(Error.class),StatusCode.CODE_400);

    }
    @Story("Unable to Create a playlist story")
    @Test(description = "Should not be able to create a play list with expired or invalid token")
    public void ShouldNotBeAbleToCreatePlaylistWithExpiredToken(){
        String invalid_token = "1234566";
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription() ,false);

        Response response = PlaylistApi.post(invalid_token,requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401);
        assertErrorCode(response.as(Error.class), StatusCode.CODE_401);

    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
      return Playlist.builder()
              .name(name)
              .description(description)
              ._public(_public)
              .build();
    }
    @Step
    public void assertPlaylistEqual(Playlist requestPlaylist,Playlist responsePlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));

    }
    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode,equalTo(statusCode.code));
    }
    @Step
    public void assertErrorCode(Error responseErr,  StatusCode statusCode){
        assertThat(responseErr.getError().getStatus(),equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(),equalTo(statusCode.msg));
    }







}
