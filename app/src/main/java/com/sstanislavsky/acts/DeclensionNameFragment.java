package com.sstanislavsky.acts;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.sstanislavsky.acts.retrofit.CustomApplication;
import com.sstanislavsky.acts.retrofit.MorpherResponse;
import com.sstanislavsky.acts.utility.InternetChecker;
import com.sstanislavsky.acts.utility.ToUpperCaseFirstLetterTextWatcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeclensionNameFragment extends Fragment implements View.OnClickListener{

    private EditText editTextName;
    private Button buttonDeclension;
    private CheckBox checkBoxName;
    private CheckBox checkBoxMale;
    private CheckBox checkBoxFemale;

    private static final String declensionLanguage = "ukrainian";
    private static final String responseFormat = "json";

    public DeclensionNameFragment(){}

    public static DeclensionNameFragment newInstance() {
        return new DeclensionNameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_declension_name, container, false);
        initializeUI(view);
        //findViews(view);
        //setupCheckBoxes();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new InternetChecker(new InternetChecker.IsConnectedListener() {
            @Override
            public void isConnected(Boolean internet) {
                if (!internet) showNotInternetConnectionDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //set enabled / disabled button depending on internet connected / not connected
        //buttonDeclension.setEnabled(MainActivity.internet);
        Log.d("MyLog", "DeclensionNameFragment.onResume()");
    }

    @Override
    public void onClick(View view) {
        decline();
    }

    private void initializeUI(View view) {
        editTextName = view.findViewById(R.id.editTextName);
        buttonDeclension = view.findViewById(R.id.buttonDeclension);
        checkBoxName = view.findViewById(R.id.checkBoxFullName);
        checkBoxMale = view.findViewById(R.id.checkBoxMale);
        checkBoxFemale = view.findViewById(R.id.checkBoxFemale);

        checkBoxMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                    checkBoxFemale.setChecked(false);
            }
        });
        checkBoxFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                    checkBoxMale.setChecked(false);
            }
        });

        buttonDeclension.setOnClickListener(this);

        ToUpperCaseFirstLetterTextWatcher textWatcher = new ToUpperCaseFirstLetterTextWatcher(editTextName);
        editTextName.addTextChangedListener(textWatcher);
    }

    private void decline() {
        new InternetChecker(new InternetChecker.IsConnectedListener() {
            @Override
            public void isConnected(Boolean internet) {
                if (internet) {
                    if (editTextName.getText().toString().isEmpty())
                        showToast(R.string.toast_declension_name_no_entered_name);
                    else makeRequest();
                } else showNotInternetConnectionDialog();
            }
        });
        /*if (!MainActivity.checkInternetConnection())
            showNotInternetConnectionDialog();
        else if (editTextName.getText().toString().isEmpty()) {
            showToast(R.string.toast_declension_name_no_entered_name);
        }
        else makeRequest();*/
    }

    private void showNotInternetConnectionDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.no_internet_dialog_title)
                .setMessage(R.string.no_internet_dialog_message)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }

    private void makeRequest() {
        final String name = editTextName.getText().toString();
        StringBuilder flags = new StringBuilder();

        if (checkBoxName.isChecked())
            flags.append("name");
        if (flags.length() != 0 && (checkBoxMale.isChecked() || checkBoxFemale.isChecked()))
            flags.append(",");
        if (checkBoxMale.isChecked())
            flags.append("masculine");
        else if (checkBoxFemale.isChecked())
            flags.append("feminine");

        CustomApplication.getApi().getData(
                declensionLanguage, name, flags.toString(), responseFormat)
                .enqueue(new Callback<MorpherResponse>() {
                    @Override
                    public void onResponse(Call<MorpherResponse> call, Response<MorpherResponse> response) {
                        response.body().setNaz(name);
                        showDeclensionDialog(response.body());
                    }

                    @Override
                    public void onFailure(Call<MorpherResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showDeclensionDialog(final MorpherResponse response) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.declension_dialog_title)
                .setItems(response.toArrayWithCases(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String copiedText = (response.toArrayWithoutCases())[i];
                        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text", copiedText);
                        try {
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(getContext(), getString(R.string.toast_declension_name_copied_to_clipboard) +
                                    (response.toArrayWithoutCases())[i], Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException e) {
                            showToast(R.string.toast_declension_name_error_copy);
                        }
                    }
                })
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }

    private void showToast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
