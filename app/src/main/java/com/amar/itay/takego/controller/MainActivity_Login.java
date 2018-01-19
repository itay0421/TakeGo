package com.amar.itay.takego.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.controller.MainActivity_Drawer;
import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.datasource.MySQL_DBManager;
import com.amar.itay.takego.model.entities.Client;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;

public class MainActivity_Login extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    boolean SavePass;
    private EditText editText;
    private Button sign_in;
    private TextView sign_up;
    private ImageButton imageButton;
    private EditText userName;
    private EditText password;
    TextView errorLogin;
    Uri imageUri;
    private ImageButton imageVisibility;
    private CheckBox remember;
    String load;
    public static final int PICK_IMAGE = 100;
    public static final String TAB = "dav";


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        findViews();

        //clearSharedPreferences();
        loadSharedPreferences();


        //*******just for test.
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MySQL_DBManager.branchList = FactoryMethod.getManager().AllBranch();
                MySQL_DBManager.carsModelList = FactoryMethod.getManager().AllCarsModel();
                MySQL_DBManager.carsList = FactoryMethod.getManager().allAvailableCars();
                Client b = new Client("dasd", "dasdasd", 555000, "01203", "dadqwe", 123123);
                boolean a = FactoryMethod.getManager().UserExistsOnDataBase(Car_GoConst.ClientToContentValues(b));
                return null;
            }

        }.execute();

    }


    private void loadSharedPreferences() {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPreferences.contains("UserName")) {
                userName.setText(sharedPreferences.getString("UserName", null));
                remember.setChecked(true);
            }
            if (sharedPreferences.contains("Password")) {
                password.setText(sharedPreferences.getString("Password", null));
            }
            if (userName.getText().length() != 0 && password.getText().length() != 0) {
                load = "loaded";
                Toast.makeText(this, "loaded!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "failed to load!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSharedPreferences() {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String UserName = userName.getText().toString();
            String pass = password.getText().toString();
            editor.putString("UserName", UserName);
            editor.putString("Password", pass);
            editor.commit();
            Toast.makeText(this, "saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "failed to save!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        userName.setText("");
        password.setText("");
        Toast.makeText(this, "clear Preferences", Toast.LENGTH_SHORT).show();
    }

    private void findViews() {
        //editText = (EditText)findViewById( R.id.editText );
        sign_in = (Button) findViewById(R.id.sign_in);
        sign_up = (TextView) findViewById(R.id.sign_up);
        imageButton = (ImageButton) findViewById(R.id.profilePicture);
        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password_login);
        //imageVisibility = (ImageButton) findViewById(R.id.imageVisibility);
        remember = (CheckBox) findViewById(R.id.remember);
        errorLogin = (TextView) findViewById(R.id.errorLogin);

        sign_in.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        //imageVisibility.setOnClickListener(this);
    }

    @SuppressLint("StaticFieldLeak")
    private void checkOnDataBase() {
         final ContentValues contentValuesUserPassword = Car_GoConst.UserPasswordtoContentValues(
                userName.getText().toString(), password.getText().toString(), 0);
        new AsyncTask<Void, Void, Client>() {

            @Override
            protected Client doInBackground(Void... voids) {
                Client client = FactoryMethod.getManager().checkOnDataBase(contentValuesUserPassword);
                return client;
            }

            @Override
            protected void onPostExecute(Client client) {
                Intent intent = new Intent(MainActivity_Login.this, MainActivity_Drawer.class);
                if(client.getId()!=-1) {
                    if (remember.isChecked()) {
                        saveSharedPreferences();
                    }
                    MySQL_DBManager.client = client;
                    startActivity(intent);
                }
                else
                    errorLogin.setText("user name or password are incorrect");
            }
        }.execute();

    }

    @Override
    public void onClick(View v) {
        if (v == sign_in) {
            Intent intent = new Intent(this, MainActivity_Drawer.class);
            if (load == "loaded")//check if shred preferences exist.
                startActivity(intent);
            else
                checkOnDataBase();
            //check on data base if the user name and password didnt found on the shared preferences


        } else if (v == sign_up) {
            Intent intent = new Intent(this, AddUserPassword.class);
            startActivity(intent);
        } else if (v == imageButton)
            openGallery();
        else if (v == imageVisibility) {
            password.setInputType(userName.getInputType());
        }
    }

    void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent data) {
        super.onActivityResult(RequestCode, ResultCode, data);
        if (ResultCode == RESULT_OK && RequestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
        }
        InputStream inputStream;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap imag = new BitmapFactory().decodeStream(inputStream);
            imageButton.setImageBitmap(imag);
            File file = new File(imageUri.toString());
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(this, "unable to open image", Toast.LENGTH_LONG).show();
        }
    }


}