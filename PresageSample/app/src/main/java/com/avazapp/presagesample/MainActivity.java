package com.avazapp.presagesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    // Buffer size used.
    private final static int BUFFER_SIZE = 1024;

    // Used to load the 'presage-lib' library on application startup.
    static {
        System.loadLibrary("presage-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAndCheckDatabase();

        String filePath = getFilesDir().getPath()+"/db";
        PresageLib(filePath);

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
                Log.d("tLayout","text is "+txt);

                String[] words = getSuggesstionsForWord(txt);
                Log.d("tLayout","words are "+words);

                TextView tv1 = findViewById(R.id.textView1);

                TextView tv2 = findViewById(R.id.textView2);

                TextView tv3 = findViewById(R.id.textView3);

                TextView tv4 = findViewById(R.id.textView4);

                TextView tv5 = findViewById(R.id.textView5);

                TextView tv6 = findViewById(R.id.textView6);

                for (int i = 0; i < words.length; i++){
                    switch (i) {
                        case 0 :
                            tv1.setText(words[i]);
                            break;
                        case 1:
                            tv2.setText(words[i]);
                            break;
                        case 2 :
                            tv3.setText(words[i]);
                            break;
                        case 3:
                            tv4.setText(words[i]);
                            break;
                        case 4 :
                            tv5.setText(words[i]);
                            break;
                        case 5:
                            tv6.setText(words[i]);
                            break;
                    }
                    if ( i >= 5)
                        break;
                }
            }
        });

    }

    void createAndCheckDatabase()
    {
        String filePath = getFilesDir().getPath()+"/db/";

        File f = new File(filePath);
        if (f.exists()) return;

        try {
            AssetManager assetFiles = getAssets();

            // db is the name of folder from inside our assets folder
            String[] files = assetFiles.list("db");

            // Initialize streams
            InputStream in = null;
            OutputStream out = null;

            File outFile = new File(filePath);
            outFile.mkdirs();

            for (int i = 0; i < files.length; i++) {
                    // @Folder name is also case sensitive
                    // @Mdb is the folder from our assets
                in = assetFiles.open("db/" + files[i]);
                out = new FileOutputStream(filePath+files[i]);
                copyAssetFiles(in, out);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyAssetFiles(InputStream in, OutputStream out) {
        try {

            byte[] buffer = new byte[BUFFER_SIZE];
            int read;

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A native method that is implemented by the 'presage-lib' native library,
     * which is packaged with this application.
     */
    public native void PresageLib(String dictPath);
    public native String[] getSuggesstionsForWord(String word);
    public native void setFilePath(String dictPath);
}
