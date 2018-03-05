package com.amar.itay.takego.model.datasource;

import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.DB_manager;
import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;
import com.amar.itay.takego.model.entities.Invitation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by itay0 on 09/12/2017.
 */

/**
 * class that get interaction with our data that inserted and our database.
 */
public class MySQL_DBManager implements DB_manager {

    private final String UserName="itaamar";
    private final String WEB_URL = "http://"+UserName+".vlab.jct.ac.il/Car_and_Go/";

    static public List<Branch> branchList = new ArrayList<Branch>();
    static public  List<Invitation> invitationList = new ArrayList<Invitation>();
    static public List<CarsModel> carsModelList = new ArrayList<>();
    static public List<Car> allCars = new ArrayList<>();
    static public List<Car> carsList = new ArrayList<>();
    static public Client client = new Client();
    static public Invitation currentInvitation = null;
    static public CarsModel currentCarModel = null;

    /**
     *  We did not implement this function here.
     * -> We used this function in the List_DBManager class, because we decided to search the
     *  client in the list we got in the beginning. in this way we don't need to call the sql table
     *  and load all the client and then to search(less effective). <-
     * @param newClient contentValues of the client.
     * @return true if the user exist false otherwise.
     */
    @Override
    public boolean UserExistsOnDataBase(ContentValues newClient) {
        try {

            String result = PHPtools.POST(WEB_URL + "/checkUserExist.php", newClient);
            String s = result.trim();
            int id = Integer.parseInt(s);

//            if (id > 0)
//                SetUpdate();
//            printLog("addStudent:\n" + result);
            //return id;
            if (id > 0)
                return true;
            else
                return false;
        } catch (IOException e) {
            printLog("addCar Exception:\n" + e);
            //return -1;
        }
        return false;
    }

    /**
     * add new car model.
     * the function send the new car model to php page that handle the "add function" and add the
     * new car model to the car model table(in our sql database).
     * @param newModel to be added.
     * @return the car model id.
     */
    @Override
    public int addModel(ContentValues newModel) {
        try{
            String result = PHPtools.POST(WEB_URL + "/insertCarModel.php", newModel);
            String s = result.trim();
            int id = Integer.parseInt(s) ;
            //          if (id > 0)
//                SetUpdate();
            printLog("addCarModel:\n" + 1);
            return id;
        } catch (IOException e) {
            e.getMessage();
            printLog("addCarModel Exception:\n" + e);

            return -1;
        }
    }

    /**
     * delete existing car model.
     * the function send the car model to be deleted to php page that handle the "delete function" and delete the
     * car model from the car model table(in our sql database).
     * @param id of the car model to be deleted.
     */
    public void deleteModel(ContentValues id) {
        try{
            PHPtools.POST(WEB_URL + "/deleteCarModel.php", id);
        } catch (IOException e) {
            e.getMessage();
            printLog("addCarModel Exception:\n" + e);

        }
    }

    /**
     * @param contentValues of model.
     * @return branch that have free model.
     */
    @Override
    public List<Branch> AllBranchByModel(ContentValues contentValues) {
        List<Branch> branchList = new ArrayList<>();
        try{
            String str = PHPtools.POST(WEB_URL+"/branch_contains_a_free_model.php", contentValues);
            JSONArray jsonArray = new JSONObject(str).getJSONArray("branches");

            for( int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ContentValues contentValues1 = PHPtools.JsonToContentValues(jsonObject);
                Branch branch = Car_GoConst.ContentValuesToBranch(contentValues1);
                branchList.add(branch);
            }
            return branchList;
        } catch (Exception e) {
            e.printStackTrace();
            return branchList;
        }
    }

    /**
     * @param _contentValues of branch.
     * @return car by specific model and branch.
     */
    @Override
    public Car GetCarByModelBranch(ContentValues _contentValues) {
        Car car;

        try{
            String result = PHPtools.POST(WEB_URL+"/first_free_car_for_m_model_b_branch.php",_contentValues );
            JSONArray jsonArray = new JSONObject(result).getJSONArray("cars");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
            car = Car_GoConst.ContentValuesToCar(contentValues);

            return car;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @return all cars model that are free.
     */
    @Override
    public List<ContentValues> AllCarModelFree() {
        List<ContentValues> result = new ArrayList<ContentValues>();

        try{
            String str = PHPtools.GET(WEB_URL+"/get_all_model_free.php");
            JSONArray jsonArray = new JSONObject(str).getJSONArray("cars_and_model");

            for( int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                result.add(contentValues);

            }
            Log.d("resoult list", result.toString());

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param contentValues_update to be update with.
     */
    @Override
    public void updateCar(ContentValues contentValues_update)  {

        try {
            String result = PHPtools.POST(WEB_URL + "/UpdateCar.php", contentValues_update);
            printLog("updateCar :\n" + result);

        }catch (IOException e){
            printLog("updateCar Exception:\n" + e);

        }

    }

    /**
     * @param contentValues_update to be update with
     */
    @Override
    public void updateInvitation(ContentValues contentValues_update) {

        try {
            String result = PHPtools.POST(WEB_URL + "/updateInvitation.php", contentValues_update);
            printLog("updateInvitation :\n" + result);

        } catch (IOException e){
            printLog("updateInvitation Exception:\n" + e);
        }

    }

    /**
     * @return true if there was a change in the last 10 seconds false otherwise.
     */
    @Override
    public List<Invitation> checkChangeAtLast10Sec() {
        String result ;
        List<Invitation> invitationList_res = new ArrayList<>();
        //send Query if there ig any update at last 10 sec
        try{

            result = PHPtools.POST(WEB_URL + "/checkChangeAtLast10Sec.php", new ContentValues());

            String r = result.trim();
            Log.d("servise resoult: ", r);



            if(!Objects.equals(r, "There was no change to the cars")) {
                JSONArray jsonArray = new JSONObject(result).getJSONArray("invitations_at_last_10_sec");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                    Invitation invitation = Car_GoConst.ContentValuesToInvitation(contentValues);
                    invitationList_res.add(invitation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            printLog("checkChangeAtLast10Sec Exception:\n" + e);

        }
        return invitationList_res;

    }

    /**
     * @param contentValues1 of invitations.
     * @return all open invitation.
     */
    @Override
    public Invitation getAllOpenInvitation(ContentValues contentValues1) {
        List<Invitation> invitationList = new ArrayList<>();
        Invitation invit = null;
        try{
            String str = PHPtools.POST(WEB_URL+"/getAllInvitation.php", contentValues1);
            JSONArray jsonArray = new JSONObject(str).getJSONArray("invitations");

            for( int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Invitation invitation = Car_GoConst.ContentValuesToInvitation(contentValues);
                invitationList.add(invitation);
            }
            for(Invitation invet: invitationList)
            {
                if(invet.getIsInvitationIsOpen())
                    invit = invet;
            }
            return invit;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

     /**
     * add new car.
     * the function send the new car to php page that handle the "add function" and add the
     * new car to the car table(in our sql database).
     * @param newCar to be added
     * @return "1" in case he succeed to add.
     */
    @Override
    public int addCar(ContentValues newCar)  {


        try {
            String result = PHPtools.POST(WEB_URL + "/insertCar.php", newCar);
            String s = result.trim();
            int id = Integer.parseInt(s) ;

//            if (id > 0)
//                SetUpdate();
//            printLog("addStudent:\n" + result);
            return id;
        } catch (IOException e) {
            printLog("addCar Exception:\n" + e);
            return -1;
        }

    }

    /**
     * add new client.
     * the function send the new client to php page that handle the "add function" and add the
     * new client to the client table(in our sql database).
     * @param newClient to be added
     * @return the client id.
     */
    @Override
    public long addUser(ContentValues newClient) {

        try {
            String result = PHPtools.POST(WEB_URL + "/insertClient.php", newClient);
            String s = result.trim();
            long id = Long.parseLong(s) ;

//            if (id > 0)
//                SetUpdate();
//            printLog("addStudent:\n" + result);
            return id;
        } catch (IOException e) {
            printLog("addCar Exception:\n" + e);
            return -1;
        }
    }

    /**
     * add new branch.
     * the function send the new branch to php page that handle the "add function" and add the
     * new branch to the branch table(in our sql database).
     * @param newBranch to be added
     * @return the branch number.
     */
    @Override
    public int addBranch(ContentValues newBranch) {

        try{
            String result = PHPtools.POST(WEB_URL + "insertBranch.php", newBranch);
            String s = result.trim();
            int id = Integer.parseInt(s) ;
  //          if (id > 0)
//                SetUpdate();
            printLog("addStudent:\n" + result);
                return id;
        } catch (IOException e) {
            e.getMessage();
            printLog("addBranch Exception:\n" + e);

            return -1;
        }

    }

    /**
     * add new Invitation.
     * the function send the new invitation to php page that handle the "add function" and add the
     * new invitation to the invitation table(in our sql database).
     * @param newInvitation to be added
     * @return the invitation id.
     */
    @Override
    public int addInvitation(ContentValues newInvitation) {
        try{
            //Log.d("<<<<<<<<******>>>>>>>>>",String.valueOf(Car_GoConst.ContentValuesToInvitation(newInvitation).getClientId()));
            String result = PHPtools.POST(WEB_URL + "insertInvitation.php", newInvitation);
            String s = result.trim();
            int id = Integer.parseInt(s) ;
            //          if (id > 0)
//                SetUpdate();
            printLog("addInvitation:\n" + result);

            return id;
        } catch (IOException e) {
            e.getMessage();
            printLog("addInvitation Exception:\n" + e);

            return -1;
        }
    }

    /**
     * @param UserPassword to be add.
     * @return the user name id.
     */
    @Override
    public long addUserNamePass(ContentValues UserPassword) {
        try {
            String result = PHPtools.POST(WEB_URL + "/addUserPassword.php", UserPassword);
            String s = result.trim();
            long id = Long.parseLong(s);

//            if (id > 0)
//                SetUpdate();
//            printLog("addStudent:\n" + result);
            return id;
        } catch (IOException e) {
            printLog("addCar Exception:\n" + e);
            return -1;
        }
    }

    /**
     * check on our database if the user exist.
     * @param UserPassword the user to be checked.
     * @return the client details if he has been found and null otherwise.
     */
    @Override
    public Client checkOnDataBase(ContentValues UserPassword) {
        List<Client> result = new ArrayList<Client>();
        try {
            String str = PHPtools.POST(WEB_URL + "/UserNameExistOnDataBase.php", UserPassword);
            JSONArray jsonArray = new JSONObject(str).getJSONArray("client");

            for( int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Client client = Car_GoConst.ContentValuesToClient(contentValues);
                result.add(client);
            }
            Log.d("CENTEE:::::",result.get(0).getFamilyName());
            return result.get(0);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    /**
     * the function use the php page that handel the "function that return all the cars model" to return all the cars model
     * @return all the cars model.
     */
    @Override
    public List<CarsModel> AllCarsModel() {
        List<CarsModel> result = new ArrayList<CarsModel>();

        try{
            String str = PHPtools.GET(WEB_URL+"/getAllCarModels.php");
            JSONArray jsonArray = new JSONObject(str).getJSONArray("cars_model");

            for( int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                CarsModel carsModel = Car_GoConst.ContentValuesToCarModel(contentValues);
                result.add(carsModel);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * the function use the php page that handel the "function that return all the client" to return all the clients.
     * @return all the clients.
     */
    @Override
    public List<Client> AllUsers() {
        List<Client> result = new ArrayList<>();

        try{
            String str = PHPtools.GET(WEB_URL+"/getAllClients.php");
            JSONArray jsonArray = new JSONObject(str).getJSONArray("client");

            for( int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Client client = Car_GoConst.ContentValuesToClient(contentValues);
                result.add(client);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * the function use the php page that handel the "function that return all the branch" to return all the branch.
     * @return all the branch.
     */
    @Override
    public List<Branch> AllBranch() {

        if(branchList.size() <= 0){

            try{
                String str = PHPtools.GET(WEB_URL+"/getAllBranch.php");
                JSONArray jsonArray = new JSONObject(str).getJSONArray("branches");

                for( int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                    Branch branch = Car_GoConst.ContentValuesToBranch(contentValues);
                    branchList.add(branch);
                }
                return branchList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        else return branchList;

    }


    /**
     * the function use the php page that handel the "function that return all the cars" to return all the cars.
     * @return all the cars.
     */
    @Override
    public List<Car> AllCars() {
        List<Car> result = new ArrayList<Car>();

        try{
            String str = PHPtools.GET(WEB_URL+"/getAllCars.php");
            JSONArray jsonArray = new JSONObject(str).getJSONArray("cars");

            for( int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                /*
                Car car = new Car();
                car.setCarNumber(jsonObject.getString(Car_GoConst.CarConst.CAR_NUMBER));
                car.setBranchNumber(jsonObject.getInt(Car_GoConst.CarConst.BRANCH_NUMBER));
                car.setKilometers(jsonObject.getInt(Car_GoConst.CarConst.KILOMETERS));
                car.setModelType(jsonObject.getInt(Car_GoConst.CarConst.));
                //car.setImage(jsonObject.getString("image"));
                */

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Car car = Car_GoConst.ContentValuesToCar(contentValues);
                result.add(car);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return all invitations.
     */
    @Override
    public List<Invitation> allInvatation() {

            try{
                String str = PHPtools.GET(WEB_URL+"/getAllInvitation.php");
                JSONArray jsonArray = new JSONObject(str).getJSONArray("invitations");

                for( int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                    Invitation invitation = Car_GoConst.ContentValuesToInvitation(contentValues);
                    invitationList.add(invitation);
                }
                return invitationList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

    }



    //two function that printing to the screen - "the class properties/Exception".
    public void printLog(String message)
    {
        Log.d(this.getClass().getName(),"\n"+message);
    }
    public void printLog(Exception message)
    {
        Log.d(this.getClass().getName(),"Exception-->\n"+message);
    }

    /**
     * @return all available cars.
     */
    @Override
    public List<Car> allAvailableCars() {
        if(carsList.size() <= 0){

            try{
                String str = PHPtools.GET(WEB_URL+"/getAllAvailableCars.php");
                JSONArray jsonArray = new JSONObject(str).getJSONArray("cars");

                for( int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                    Car car = Car_GoConst.ContentValuesToCar(contentValues);
                    carsList.add(car);
                }
                return carsList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        else return carsList;
    }

    /**
     * @param number to be checked
     * @return if the number is a real car number.
     */
    public static String realCarNumber(int number){
        String str = String.valueOf(number);
        int length = str.length();
        if (length == 7 )
            return str.substring(0,2)+"-"+str.substring(2,5)+"-"+str.substring(5,7);
        if (length == 8)
            return str.substring(0,3)+"-"+str.substring(3,5)+"-"+str.substring(5,8);
        return str;

    }

    /**
     * @param Branch to find his available cars.
     * @return all available cars for specific Branch.
     */
      @Override
    public List<CarsModel> AllAvilableCarsForBranch(ContentValues Branch) {

          List<CarsModel> result = new ArrayList<CarsModel>();
          try {
              String str = PHPtools.POST(WEB_URL + "/getAllAvailableModelCarsOfBranch.php", Branch);
              JSONArray jsonArray = new JSONObject(str).getJSONArray("carsModel");

              for (int i = 0; i < jsonArray.length(); i++) {
                  JSONObject jsonObject = jsonArray.getJSONObject(i);

                  ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                  CarsModel carsModel = Car_GoConst.ContentValuesToCarModel(contentValues);
                  result.add(carsModel);
              }
              return result;
          } catch (Exception e) {
              e.printStackTrace();
          }
          return null;
      }
}
