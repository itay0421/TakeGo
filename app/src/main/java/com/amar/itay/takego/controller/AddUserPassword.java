package com.amar.itay.takego.controller;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.itay.takego.R;
import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.FactoryMethod;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.Client;

/**
 * this class is the code behind of the activity_add_user_password that handel his events.
 * class that add a new client.
 */
public class AddUserPassword extends AppCompatActivity implements View.OnClickListener {
    /*definition for the instance views we will get.
     to add new client we did two screens:
     1) for the userName and password.
     2) for the rest information about this client(private name, family name etc ).
     */
    //first screen
    ImageButton profilePicture;
    EditText userName;
    EditText password;
    ImageButton imageVisibility;
    Button Next;
    //second screen
    EditText privateName;
    EditText familyName;
    EditText id;
    EditText creditCard;
    EditText email;
    EditText phoneNumber;
    Button addUser;
     static String TA = "MES";

    /**
     * the function create the activity and find the views.
     * @param savedInstanceState contains the most recent data, specially contains
     * data of the activity's previous initialization part.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_password);
        findViews();
        Log.d(TA,"ASDASD");
    }

    /**
     * the findViews function gets the instance of the views.
     * and make the add button,next(to enter more information on the second screen),image to listen the click event.
     */
    void findViews()
    {
        //first screen
        profilePicture = (ImageButton) findViewById(R.id.profilePicture);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        imageVisibility = (ImageButton) findViewById(R.id.imageVisibility);
        Next = (Button) findViewById(R.id.Next);
        //second screen
        privateName = (EditText) findViewById(R.id.Name_editText13);
        familyName = (EditText) findViewById(R.id.FamilyName_editText14);
        id = (EditText) findViewById(R.id.Id_editText15);
        creditCard = (EditText) findViewById(R.id.Id_editText15);
        email = (EditText) findViewById(R.id.Email_editText16);
        phoneNumber = (EditText) findViewById(R.id.Phone_editText17);
        addUser = (Button) findViewById(R.id.addUser);

        Next.setOnClickListener(this);
        addUser.setOnClickListener(this);
        imageVisibility.setOnClickListener(this);
    }

    /**
     * check if the click is the add button click or one of the others.
     * @param v represent the view of the event that have been occurred.
     */
    @Override
    public void onClick(View v) {
        int visibility = userName.getVisibility();

        if (v == Next) {
            int gone = privateName.getVisibility();
            //first screen
            profilePicture.setVisibility(gone);
            userName.setVisibility(gone);
            password.setVisibility(gone);
            Next.setVisibility(gone);
            //second screen
            privateName.setVisibility(visibility);
            familyName.setVisibility(visibility);
            id.setVisibility(visibility);
            creditCard.setVisibility(visibility);
            email.setVisibility(visibility);
            phoneNumber.setVisibility(visibility);
            addUser.setVisibility(visibility);
        }
        if (v == addUser)
            addClient();
        if(v == imageVisibility);
            //password.setTextAppearance(23);
    }

    /**
     * the function take all the information that has been insert and send it by thread in the background to
     * the php page to add the new Client.
     */
    void addClient() {
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);

        final ContentValues contentValues_details = new ContentValues();
        final ContentValues contentValues_user_name_password = new ContentValues();
        try {
            long ID = Long.valueOf(this.id.getText().toString());
            long CreditCard = Long.valueOf(this.creditCard.getText().toString());
            String Email = this.email.getText().toString();
            String PhoneNumber = this.phoneNumber.getText().toString();
            String Name = this.privateName.getText().toString();
            final String FamilyName = this.familyName.getText().toString();
            ///
            String UserName = this.userName.getText().toString();
            String Password = this.password.getText().toString();

            contentValues_details.put(Car_GoConst.ClientConst.ID, ID);
            contentValues_details.put(Car_GoConst.ClientConst.CREDIT_CARD, CreditCard);
            contentValues_details.put(Car_GoConst.ClientConst.EMAIL, Email);
            contentValues_details.put(Car_GoConst.ClientConst.PHONE_NUMBER, PhoneNumber);
            contentValues_details.put(Car_GoConst.ClientConst.PRIVATE_NAME, Name);
            contentValues_details.put(Car_GoConst.ClientConst.FAMILY_NAME, FamilyName);

            contentValues_user_name_password.put(Car_GoConst.UesrNamePasswordConst.CLIENT_ID, ID);
            contentValues_user_name_password.put(Car_GoConst.UesrNamePasswordConst.USER_NAME,UserName);
            contentValues_user_name_password.put(Car_GoConst.UesrNamePasswordConst.PASSWORD,Password);

            /*if (FactoryMethod.getManager().UserExistsOnDataBase(contentValues)) {
                throw new Exception("User ID already exist");
            }*/

            if (ID == 0 || Email.isEmpty() || Name.isEmpty() || CreditCard == 0 || PhoneNumber.isEmpty() || FamilyName.isEmpty())
                throw new Exception("one of the filed missed!");
            else {


                new AsyncTask<Void, Void, Long>() {
                    @Override
                    protected void onPostExecute(Long idResult) {
                        super.onPostExecute(idResult);
                        if (idResult > 0)
                            Toast.makeText(getBaseContext(), "insert id: " + idResult, Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    protected Long doInBackground(Void... params) {

                        long _id = FactoryMethod.getManager().addUserNamePass(contentValues_user_name_password);
                        return FactoryMethod.getManager().addUser(contentValues_details);
                    }

                }.execute();


                finish();
            }
        } catch (Exception e) {
            //Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            String a = e.getMessage();
            toast.setText(e.getMessage());
            toast.show();
        }
    }
}