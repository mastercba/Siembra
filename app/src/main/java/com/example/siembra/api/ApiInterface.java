package com.example.siembra.api;

import com.example.siembra.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("siembraPost.php")
    Call<Note> saveNote(
            @Field("ban") String ban,
            @Field("desp") String desp,
            @Field("resp") String resp,
            @Field("tag") String tag,
            @Field("color") int color
    );

    @GET("siembraGet.php")
    Call<List<Note>> getNotes();

    @FormUrlEncoded
    @POST("siembraUpt.php")
    Call<Note> updateNote(
            @Field("id") int id,
            @Field("ban") String ban,
            @Field("desp") String desp,
            @Field("resp") String resp,
            @Field("tag") String tag,
            @Field("color") int color
    );

    @FormUrlEncoded
    @POST("siembraDel.php")
    Call<Note> deleteNote( @Field("id") int id );
}
