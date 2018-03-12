package com.sstanislavsky.acts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

/**
 * Created by stanislav on 2/6/18.
 */

public class AgeCarFragment extends Fragment implements View.OnFocusChangeListener {

    private TextView textView;
    private EditText editTextMonth;
    private EditText editTextYear;
    private Button button;

    public AgeCarFragment() {}

    public static AgeCarFragment newInstance() {
        return new AgeCarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_age_car, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        textView = view.findViewById(R.id.ageCarTextView);
        editTextMonth = view.findViewById(R.id.ageCarEditTextMonth);
        editTextYear = view.findViewById(R.id.ageCarEditTextYear);
        button = view.findViewById(R.id.ageCarButton);

        editTextMonth.setOnFocusChangeListener(this);
        editTextYear.setOnFocusChangeListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howOldCar();
            }
        });
    }

    private void howOldCar() {
        String enteredMonth = editTextMonth.getText().toString();
        String enteredYear = editTextYear.getText().toString();

        Date now = new Date();
        int nowMonth = now.getMonth() + 1;
        //int nowMonth = 7;
        int nowYear = now.getYear() + 1900;

        if (enteredMonth.isEmpty() || enteredYear.isEmpty()) {
            showToast(R.string.toast_age_car_no_entered_text);
            return; // return from method
        }
        if (enteredMonth.contains(".") || enteredYear.contains(".")) {
            showToast(R.string.toast_age_car_contains_dot);
            return; // return from method
        }

        int carMonth = Integer.parseInt(enteredMonth);
        int carYear = Integer.parseInt(enteredYear);

        // для возможности ввода года в формате ХХ
        if (carYear <= (nowYear - 2000))
            carYear += 2000;
        else if (carYear <= 99)
            carYear += 1900;

        if (carMonth <= 0 || carMonth > 12)
            showToast(R.string.toast_age_car_wrong_month);
        else if (carYear > 3000)
            showToast(R.string.toast_age_car_wrong_year);
        else {
            BigDecimal resultYear = BigDecimal.valueOf(nowYear - carYear);
            BigDecimal resultMonth = BigDecimal.valueOf(Math.round((nowMonth - carMonth) / 1.2));
            resultMonth = resultMonth.multiply(BigDecimal.valueOf(0.1));

            textView.setText((resultYear.add(resultMonth)).toString());
            //textView.setText(String.format(Locale.ENGLISH, (resultYear.add(resultMonth)).toString());
        }
    }

    private void showToast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus)
            ((EditText) view).getText().clear();
    }
}
