package com.example.siembra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity {

    EditText et_title, et_note;
    ProgressDialog progressDialog;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        et_title = findViewById(R.id.title);
        et_note = findViewById(R.id.note);

        //      Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                //Save
                String title = et_title.getText().toString().trim();
                String note = et_note.getText().toString().trim();
                int color = -2184710;

                if (title.isEmpty()) {
                    et_title.setError("Please enter a title");
                } else if (note.isEmpty()) {
                    et_note.setError("Please enter a note");
                } else {
                    saveNote(title, note, color);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote(final String title, final String note, final int color){
        progressDialog.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(title, note, color);

        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NotNull Call<Note> call, @NotNull Response<Note> response) {
                progressDialog.dismiss();
                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        Toast.makeText(EditorActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        finish(); //back to main activity
                    }
                    else {
                        Toast.makeText(EditorActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // if error, still in this act.
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<Note> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorActivity.this,
                        t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}