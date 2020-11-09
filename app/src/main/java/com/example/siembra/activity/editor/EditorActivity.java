package com.example.siembra.activity.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siembra.R;
import com.example.siembra.api.ApiClient;
import com.example.siembra.api.ApiInterface;
import com.example.siembra.model.Note;
import com.thebluealliance.spectrum.SpectrumPalette;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity implements EditorView{

    EditText et_ban, et_desp, et_resp, et_tag;
    ProgressDialog progressDialog;
    SpectrumPalette palette;

    //ApiInterface apiInterface;

    EditorPresenter presenter;

    int color, id;
    String ban, desp, resp, tag;

    Menu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        et_ban = findViewById(R.id.ban);
        et_desp = findViewById(R.id.desp);
        et_resp = findViewById(R.id.resp);
        et_tag = findViewById(R.id.tag);

        palette = findViewById(R.id.palette);

        palette.setOnColorSelectedListener(
                clr -> color = clr
        );

        //      Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Espere...");

        presenter = new EditorPresenter(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        ban = intent.getStringExtra("ban");
        desp = intent.getStringExtra("desp");
        resp = intent.getStringExtra("resp");
        tag = intent.getStringExtra("tag");
        color = intent.getIntExtra("color", 0);

        setDataFromIntentExtra();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu = menu;

        if (id != 0) {
            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
        } else {
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String ban = et_ban.getText().toString().trim();
        String desp = et_desp.getText().toString().trim();
        String resp = et_resp.getText().toString().trim();
        String tag = et_tag.getText().toString().trim();
        int color = this.color;

        switch (item.getItemId()) {
            case R.id.save:
                //Save
                if (ban.isEmpty()) {
                    et_ban.setError("Ingresa #bandeja!");
                } else if (desp.isEmpty()) {
                    et_desp.setError("Ingresa Producto!");
                } else if (resp.isEmpty()) {
                    et_resp.setError("Ingresa Responasble!");
                } else if (tag.isEmpty()) {
                    et_tag.setError("Ingresa #Semilla!");
                } else {
                    presenter.saveNote(ban, desp, resp, tag, color);
                }
                return true;

            case R.id.edit:
                //Edit
                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.update:
                //Update

                if (ban.isEmpty()) {
                    et_ban.setError("Ingresa #bandeja!");
                } else if (desp.isEmpty()) {
                    et_desp.setError("Ingresa Producto!");
                } else if (resp.isEmpty()) {
                    et_resp.setError("Ingresa Responasble!");
                } else if (tag.isEmpty()) {
                    et_tag.setError("Ingresa #Semilla!");
                } else {
                    presenter.updateNote(id, ban, desp, resp, tag, color);
                }
                return true;

            case R.id.delete:
                //Delete
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirmado !");
                alertDialog.setMessage("Estas segur@?");
                alertDialog.setNegativeButton("Si", (dialog, which) -> {
                    presenter.deleteNote(id);
                });
                alertDialog.setPositiveButton("Cancel",
                        (dialog, which) -> dialog.dismiss());

                alertDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onAddSuccess(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        finish();  //back to main activity
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish(); //back to main activity
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {
            et_ban.setText(ban);
            et_desp.setText(desp);
            et_resp.setText(resp);
            et_tag.setText(tag);
            palette.setSelectedColor(color);

            getSupportActionBar().setTitle("Actualizar Item");
            readMode();
        } else {
            palette.setSelectedColor(getResources().getColor(R.color.white));
            color = getResources().getColor(R.color.white);
            editMode();
        }

    }

    private void editMode() {
        et_ban.setFocusableInTouchMode(true);
        et_desp.setFocusableInTouchMode(true);
        et_resp.setFocusableInTouchMode(true);
        et_tag.setFocusableInTouchMode(true);
        palette.setEnabled(true);
    }

    private void readMode() {
        et_ban.setFocusableInTouchMode(false);
        et_desp.setFocusableInTouchMode(false);
        et_resp.setFocusableInTouchMode(false);
        et_tag.setFocusableInTouchMode(false);
        et_ban.setFocusable(false);
        et_desp.setFocusable(false);
        et_resp.setFocusable(false);
        et_tag.setFocusable(false);
        palette.setEnabled(false);
    }

}