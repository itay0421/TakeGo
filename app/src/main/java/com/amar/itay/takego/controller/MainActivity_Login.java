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

    //definition for the instance views we will get.
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
    Client client_load = new Client();
    public static final int PICK_IMAGE = 100;
    public static final String TAB = "dav";

    /**
     * @param savedInstanceState contains the most recent data, specially contains
     * data of the activity's previous initialization part.
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        findViews();//find the views.

        //clearSharedPreferences();
        loadSharedPreferences();//loading the username and the password.

        //getting all the information fom the sql table by the php page.
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MySQL_DBManager.branchList = FactoryMethod.getManager().AllBranch();
                MySQL_DBManager.carsModelList = FactoryMethod.getManager().AllCarsModel();
                MySQL_DBManager.carsList = FactoryMethod.getManager().allAvailableCars();
                MySQL_DBManager.invitationList = FactoryMethod.getManager().allInvatation();
                MySQL_DBManager.allCars = FactoryMethod.getManager().AllCars();
                Client b = new Client("dasd", "dasdasd", 555000, "01203", "dadqwe", 123123);
                boolean a = FactoryMethod.getManager().UserExistsOnDataBase(Car_GoConst.ClientToContentValues(b));
                return null;
            }
        }.execute();


    }


    /**
     * loading the information about the client in case he choose to save them.
     */
    private void loadSharedPreferences() {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPreferences.contains("UserName")) {
                userName.setText(sharedPreferences.getString("UserName", null));
                remember.setChecked(true);
            }
            if (sharedPreferences.contains("Password")) {
                password.setText(sharedPreferences.getString("Password", null));
                Toast.makeText(this, "loaded!", Toast.LENGTH_SHORT).show();
            }
            if (userName.getText().length() != 0 && password.getText().length() != 0) {
                load = "loaded";
                client_load.setFamilyName(sharedPreferences.getString("FamilyName", null));
                client_load.setPrivateName(sharedPreferences.getString("PrivateName", null));
                client_load.setCreditCard(sharedPreferences.getLong("CreditCard", 0));
                client_load.setId(sharedPreferences.getLong("Id", 0));
                client_load.setEmail(sharedPreferences.getString("email", null));
                client_load.setPhoneNumber(sharedPreferences.getString("PhoneNumber", null));
            }


        } catch (Exception ex) {
            Toast.makeText(this, "failed to load!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * saving the information about the client in case he choose to save them.
     * */
    private void saveSharedPreferences() {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String UserName = userName.getText().toString();
            String pass = password.getText().toString();
            editor.putString("UserName", UserName);
            editor.putString("Password", pass);
            editor.putString("FamilyName",client_load.getFamilyName());
            editor.putString("PrivateName",client_load.getPrivateName());
            editor.putLong("Id",client_load.getId());
            editor.putString("PhoneNumber",client_load.getPhoneNumber());
            editor.putString("email",client_load.getEmail());
            editor.putLong("CreditCard",client_load.getCreditCard());
            editor.commit();
            Toast.makeText(this, "saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "failed to save!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * clear the information about the client.
     */
    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        userName.setText("");
        password.setText("");
        Toast.makeText(this, "clear Preferences", Toast.LENGTH_SHORT).show();
    }

    /**
     * find the views.
     */
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

    /**
     * checking on the database if the client exist in there in order to let him get access into the app.
     */
    @SuppressLint("StaticFieldLeak")
    private void checkOnDataBase() {
         final ContentValues contentValuesUserPassword = Car_GoConst.UserPasswordtoContentValues(
                userName.getText().toString(), password.getText().toString(),0);
        new AsyncTask<Void, Void, Client>() {

            @Override
            protected Client doInBackground(Void... voids) {
                Client client = FactoryMethod.getManager().checkOnDataBase(contentValuesUserPassword);
                return client;
            }

            @Override
            protected void onPostExecute(Client client) {
                Log.d("<<<<<CLIENT::::%%%",client.getFamilyName());
                client_load = client;
                Intent intent = new Intent(MainActivity_Login.this, MainActivity_Drawer.class);
                if(client.getId() != -1) {
                    if (remember.isChecked()) {
                        saveSharedPreferences();
                    }
                    MySQL_DBManager.client = client;
                    startActivity(intent);
                }
                else
                {
                    errorLogin.setText("user name or password are incorrect");
                }
            }
        }.execute();
    }

    /**
     * checking if the click was to sign in or sign up.
     * 1)The sign in function check the client if exist by the shared preferences if the client doesn't exist there
     *   he is checking on the database by the checkOnDataBase() function.
     * 2)The sign up function open  a new activity to add the new client.
     * @param v represent the view of the event that have been occurred.
     */
    @Override
    public void onClick(View v) {
        if (v == sign_in) {
            Intent intent = new Intent(this, MainActivity_Drawer.class);
            if (load == "loaded")//check if shred preferences exist.
            {
                MySQL_DBManager.client = client_load;
                startActivity(intent);
            }
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

    /**
     * open gallery for the picture.
     */
    void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    /**
     *
     * @param RequestCode to know what the user want to do (PICK_IMAGE etc).
     * @param ResultCode to check if the result code ok.
     * @param data the uri to the gallery.
     */
    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent data) {
        super.onActivityResult(RequestCode, ResultCode, data);
        //change the Uri to the gallery if everything ok
        if (ResultCode == RESULT_OK && RequestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageButton.setImageURI(imageUri);
        }
        InputStream inputStream;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);//open the gallery.
            Bitmap imag = new BitmapFactory().decodeStream(inputStream);//getting the image the have been selected/
            imageButton.setImageBitmap(imag);//set the image that have been choose
            File file = new File(imageUri.toString());//make a new file with the Uri.
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(this, "unable to open image", Toast.LENGTH_LONG).show();
        }
    }


}