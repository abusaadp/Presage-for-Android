package com.avazapp.presagesample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputLayout tLayout = findViewById(R.id.textInputLayout);
        EditText editText = tLayout.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String txt = tLayout.getEditText().getText().toString();
                Log.d("error","text is "+txt);

                try {
                    int myNum = Integer.parseInt(txt);
                } catch(NumberFormatException nfe) {
                    txt = "1";
                    System.out.println("Could not parse " + nfe);
                }
                TextView tv1 = findViewById(R.id.textView1);
                tv1.setText(mulFromJNI(txt, 2));

                TextView tv2 = findViewById(R.id.textView2);
                tv2.setText(mulFromJNI(txt, 3));

                TextView tv3 = findViewById(R.id.textView3);
                tv3.setText(mulFromJNI(txt, 4));

                TextView tv4 = findViewById(R.id.textView4);
                tv4.setText(mulFromJNI(txt, 5));

                TextView tv5 = findViewById(R.id.textView5);
                tv5.setText(mulFromJNI(txt, 6));

                TextView tv6 = findViewById(R.id.textView6);
                tv6.setText(mulFromJNI(txt, 7));;
            }
        });


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String mulFromJNI(String txt, int mul);
}
