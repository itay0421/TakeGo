package com.amar.itay.takego.model.datasource;

import android.content.ContentValues;
import android.util.Log;

import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.DB_manager;
import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itay0 on 09/12/2017.
 */

public class MySQL_DBManager implements DB_manager {

    private final String UserName="itaamar";
    private final String WEB_URL = "http://"+UserName+".vlab.jct.ac.il/Car_and_Go/";


    @Override
    public boolean UserExistsOnDataBase(Long ID) {
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
    public String addCar(ContentValues newCar) {

        try {
            String result = PHPtools.POST(WEB_URL + "/insertCar.php", newCar);
         //   String id = result;
//            if (id > 0)
//                SetUpdate();
//            printLog("addStudent:\n" + result);
            return "1";
        } catch (IOException e) {
            printLog("addCar Exception:\n" + e);
            return null;
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
        List<Branch> result = new ArrayList<Branch>();

        try{
            String str = PHPtools.GET(WEB_URL+"/getAllBranch.php");
            JSONArray jsonArray = new JSONObject(str).getJSONArray("branches");

            for( int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                Branch branch = Car_GoConst.ContentValuesToBranch(contentValues);
                result.add(branch);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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




    public void printLog(String message)
    {
        Log.d(this.getClass().getName(),"\n"+message);
    }
    public void printLog(Exception message)
    {
        Log.d(this.getClass().getName(),"Exception-->\n"+message);
    }


}
