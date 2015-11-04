package com.example.mrx.twitterapp.api;


import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ivor.aguado on 27/08/2015.
 */
public interface TweetInRestAPI {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter
    @GET("/statuses/home_timeline.json")
    void homeTimeline(@Query("count") Integer count,
                      @Query("since_id") Long sinceId,
                      @Query("max_id") Long maxId,
                      @Query("trim_user") Boolean trimUser,
                      @Query("exclude_replies") Boolean excludeReplies,
                      @Query("contributor_details") Boolean contributeDetails,
                      @Query("include_entities") Boolean includeEntities,
                      Callback<List<Tweet>> cb);
//    @GET("/users/{id}")
//    void getUserById(@Path("id") String username, RestApiCallback<User> user);
//
////    @GET("/group/{id}/users")
////    void groupList(@Path("id") int groupId, @Query("sort") String sort, Callback<List<ParkZone>> cb);
//
//    @POST("/users/")
//    void registerUser(@Body User userToCreate, RestApiCallback<User> userRegistered);
//
//    @GET("/users/")
//    void getUsers(RestApiCallback<List<User>> usersList);
//
//    @GET("/users/")
//    void loginUser(RestApiCallback<List<User>> usersList);
//
//    @GET("/messages/")
//    void getUserMessages(RestApiCallback<List<PrivateMessage>> userMessages);
//
//    @POST("/messages/")
//    void sendMessage(@Body PrivateMessage privateMessage, RestApiCallback<PrivateMessage> privateMessageRestApiCallback);

}
