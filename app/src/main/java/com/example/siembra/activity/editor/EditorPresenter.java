package com.example.siembra.activity.editor;

import com.example.siembra.api.ApiClient;
import com.example.siembra.api.ApiInterface;
import com.example.siembra.model.Note;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {
    private EditorView view;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    void saveNote(final String ban, final String desp, final String resp, final String tag, final int color) {
        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(ban, desp, resp, tag, color);

        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NotNull Call<Note> call, @NotNull Response<Note> response) {
                view.hideProgress();
                if (response.isSuccessful()){
                    Boolean success = response.body().getSuccess();
                    if (success){
                        view.onAddSuccess(response.body().getMessage());
//                        Toast.makeText(EditorActivity.this,
//                                response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        finish(); //back to main activity
                    }
                    else {
                        view.onAddError(response.body().getMessage());
//                        // if error, still in this act.
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Note> call, @NotNull Throwable t) {
                view.hideProgress();
                view.onAddError(t.getLocalizedMessage());

            }
        });
    }

    void deleteNote(int id){
        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.deleteNote(id);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NotNull Call<Note> call, @NotNull Response<Note> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if (success){
                        view.onRequestSuccess(response.body().getMessage());
                    } else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Note> call, @NotNull Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getLocalizedMessage());
            }
        });
    }

    void updateNote(int id, String ban, String desp, String resp, String tag, int color){

        view.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Note> call = apiInterface.updateNote(id, ban, desp, resp, tag, color);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NotNull Call<Note> call, @NotNull Response<Note> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        view.onRequestSuccess(response.body().getMessage());
                    } else {
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Note> call, @NotNull Throwable t) {

            }
        });
    }
}
