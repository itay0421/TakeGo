package com.amar.itay.takego.model.datasource;

import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

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

public class MySQL_DBManager implements DB_manager {

    private final String UserName="itaamar";
    private final String WEB_URL = "http://"+UserName+".vlab.jct.ac.il/Car_and_Go/";

    static public List<Branch> branchList = new ArrayList<Branch>();
    static public  List<Invitation> invitationList = new ArrayList<Invitation>();
    static public List<CarsModel> carsModelList = new ArrayList<>();
    static public List<Car> carsList = new ArrayList<>();
    static public Client client = new Client();
    static public Invitation invitation = null;

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

    public void deleteModel(ContentValues id) {
        try{
            PHPtools.POST(WEB_URL + "/deleteCarModel.php", id);
        } catch (IOException e) {
            e.getMessage();
            printLog("addCarModel Exception:\n" + e);

        }
    }

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

    @Override
    public void updateCar(ContentValues contentValues_update)  {

        try {
            String result = PHPtools.POST(WEB_URL + "/UpdateCar.php", contentValues_update);
            printLog("updateCar :\n" + result);

        }catch (IOException e){
            printLog("updateCar Exception:\n" + e);

        }

    }

    @Override
    public void updateInvitation(ContentValues contentValues_update) {

        try {
            String result = PHPtools.POST(WEB_URL + "/updateInvitation.php", contentValues_update);
            printLog("updateInvitation :\n" + result);

        } catch (IOException e){
            printLog("updateInvitation Exception:\n" + e);
        }


    }

    @Override
    public List<Invitation> checkChangeAtLast10Sec() {
        String result ;
        List<Invitation> invitationList_res = new ArrayList<>();
        //send Query if there ig any update at last 10 sec
        try{

            result = PHPtools.POST(WEB_URL + "/checkChangeAtLast10Sec.php", new ContentValues());
            Log.d("resoult: ", result);

            String r = result.trim();
            Log.d("resoult: ", r);



            if(!Objects.equals(r, "not")) {
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

    @Override
    public int addInvitation(ContentValues newInvitation) {
        try{
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

    @Override
    public List<Invitation> allInvatation() {
        if(branchList.size() <= 0){

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
        else return invitationList;
    }




    public void printLog(String message)
    {
        Log.d(this.getClass().getName(),"\n"+message);
    }
    public void printLog(Exception message)
    {
        Log.d(this.getClass().getName(),"Exception-->\n"+message);
    }

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

    public static String realCarNumber(int number){
        String str = String.valueOf(number);
        int length = str.length();
        if (length == 7 )
            return str.substring(0,2)+"-"+str.substring(2,5)+"-"+str.substring(5,7);
        if (length == 8)
            return str.substring(0,3)+"-"+str.substring(3,5)+"-"+str.substring(5,8);
        return str;

    }
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
