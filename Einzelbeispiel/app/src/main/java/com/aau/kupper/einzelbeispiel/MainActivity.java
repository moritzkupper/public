package com.aau.kupper.einzelbeispiel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String DOMAIN = "se2-isys.aau.at";
    private static final int PORT = 53212;
    private EditText et_matrikelnummer;
    private Button btn_send;
    private Button btn_calculate;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_matrikelnummer = findViewById(R.id.et_matrikelnummer);
        btn_send = findViewById(R.id.btn_send);
        btn_calculate = findViewById(R.id.btn_calculate);
        tv_result = findViewById(R.id.tv_result);

        btn_send.setOnClickListener(v -> {
            String input = et_matrikelnummer.getText().toString();
            if (checkInput(input)) {
                new AsyncSendTcpTask(DOMAIN, PORT, input, result -> tv_result.setText("Response:\n" + result)).execute();
            }
        });

        btn_calculate.setOnClickListener(v -> {
            String input = et_matrikelnummer.getText().toString();
            if (checkInput(input)) {
                tv_result.setText("Matrikelnummer sortiert:\n" + sortInput(input));
            }
        });
    }

    private boolean checkInput(String input) {
        if (input.isEmpty()) {
            tv_result.setText("Bitte geben Sie eine Matrikelnummer ein.");
            return false;
        } else if (input.length() != 8) {
            tv_result.setText("Bitte geben Sie eine gültge Matrikelnummer ein.");
            return false;
        } else {
            return true;
        }
    }

    private String sortInput(String value) {
        String even = "";
        String odd = "";
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isDigit(c)) {
                int digit = Character.getNumericValue(c);
                if (digit % 2 == 0) {
                    even += c;
                } else {
                    odd += c;
                }
            }
        }

        char[] evenDigits = even.toCharArray();
        char[] oddDigits = odd.toCharArray();

        Arrays.sort(evenDigits);
        Arrays.sort(oddDigits);

        return new String(evenDigits) + new String(oddDigits);
    }
}