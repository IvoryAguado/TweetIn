package com.example.mrx.twitterapp.api;

import com.innovacorp.profound.api.apicore.RestApiCallback;
import com.innovacorp.profound.db.models.PrivateMessage;
import com.innovacorp.profound.db.models.User;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by ivor.aguado on 27/08/2015.
 */
public interface TweetInRestAPI {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("/users/{id}")
    void getUserById(@Path("id") String username, RestApiCallback<User> user);

//    @GET("/group/{id}/users")
//    void groupList(@Path("id") int groupId, @Query("sort") String sort, Callback<List<ParkZone>> cb);

    @POST("/users/")
    void registerUser(@Body User userToCreate, RestApiCallback<User> userRegistered);

    @GET("/users/")
    void getUsers(RestApiCallback<List<User>> usersList);

    @GET("/users/")
    void loginUser(RestApiCallback<List<User>> usersList);

    @GET("/messages/")
    void getUserMessages(RestApiCallback<List<PrivateMessage>> userMessages);

    @POST("/messages/")
    void sendMessage(@Body PrivateMessage privateMessage, RestApiCallback<PrivateMessage> privateMessageRestApiCallback);

}
