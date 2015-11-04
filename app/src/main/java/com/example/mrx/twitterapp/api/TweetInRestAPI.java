package com.example.mrx.twitterapp.api;


import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by ivor.aguado on 27/08/2015.
 */
public interface TweetInRestAPI {

    @GET("/1.1/statuses/mentions_timeline.json")
    void mentionsTimeline(@Query("count") Integer var1, @Query("since_id") Long var2, @Query("max_id") Long var3, @Query("trim_user") Boolean var4, @Query("contributor_details") Boolean var5, @Query("include_entities") Boolean var6, Callback<List<Tweet>> var7);

    @GET("/1.1/statuses/user_timeline.json")
    void userTimeline(@Query("user_id") Long var1, @Query("screen_name") String var2, @Query("count") Integer var3, @Query("since_id") Long var4, @Query("max_id") Long var5, @Query("trim_user") Boolean var6, @Query("exclude_replies") Boolean var7, @Query("contributor_details") Boolean var8, @Query("include_rts") Boolean var9, Callback<List<Tweet>> var10);

    @GET("/1.1/statuses/home_timeline.json")
    void homeTimeline(@Query("count") Integer var1, @Query("since_id") Long var2, @Query("max_id") Long var3, @Query("trim_user") Boolean var4, @Query("exclude_replies") Boolean var5, @Query("contributor_details") Boolean var6, @Query("include_entities") Boolean var7, Callback<List<Tweet>> var8);

    @GET("/1.1/statuses/retweets_of_me.json")
    void retweetsOfMe(@Query("count") Integer var1, @Query("since_id") Long var2, @Query("max_id") Long var3, @Query("trim_user") Boolean var4, @Query("include_entities") Boolean var5, @Query("include_user_entities") Boolean var6, Callback<List<Tweet>> var7);

    @GET("/1.1/statuses/show.json")
    void show(@Query("id") Long var1, @Query("trim_user") Boolean var2, @Query("include_my_retweet") Boolean var3, @Query("include_entities") Boolean var4, Callback<Tweet> var5);

    @GET("/1.1/statuses/lookup.json")
    void lookup(@Query("id") String var1, @Query("include_entities") Boolean var2, @Query("trim_user") Boolean var3, @Query("map") Boolean var4, Callback<List<Tweet>> var5);

    /**
     * @deprecated
     */
    @Deprecated
    @FormUrlEncoded
    @POST("/1.1/statuses/update.json")
    void update(@Field("status") String var1, @Field("in_reply_to_status_id") Long var2, @Field("possibly_sensitive") Boolean var3, @Field("lat") Double var4, @Field("long") Double var5, @Field("place_id") String var6, @Field("display_cooridnates") Boolean var7, @Field("trim_user") Boolean var8, Callback<Tweet> var9);

    @FormUrlEncoded
    @POST("/1.1/statuses/update.json")
    void update(@Field("status") String var1, @Field("in_reply_to_status_id") Long var2, @Field("possibly_sensitive") Boolean var3, @Field("lat") Double var4, @Field("long") Double var5, @Field("place_id") String var6, @Field("display_cooridnates") Boolean var7, @Field("trim_user") Boolean var8, @Field("media_ids") String var9, Callback<Tweet> var10);

    @FormUrlEncoded
    @POST("/1.1/statuses/retweet/{id}.json")
    void retweet(@Path("id") Long var1, @Field("trim_user") Boolean var2, Callback<Tweet> var3);

    @FormUrlEncoded
    @POST("/1.1/statuses/destroy/{id}.json")
    void destroy(@Path("id") Long var1, @Field("trim_user") Boolean var2, Callback<Tweet> var3);

    @FormUrlEncoded
    @POST("/1.1/statuses/unretweet/{id}.json")
    void unretweet(@Path("id") Long var1, @Field("trim_user") Boolean var2, Callback<Tweet> var3);
}
