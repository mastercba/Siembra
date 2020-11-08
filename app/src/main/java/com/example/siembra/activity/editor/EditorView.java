package com.example.siembra.activity.editor;

public interface EditorView {

    void showProgress();
    void hideProgress();
    void onAddSuccess(String message);
    void onAddError(String message);
    void onRequestSuccess(String message);
    void onRequestError(String message);
}
