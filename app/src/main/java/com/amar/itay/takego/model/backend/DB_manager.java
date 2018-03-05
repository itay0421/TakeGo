package com.amar.itay.takego.model.backend;

import android.content.ContentValues;


import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;
import com.amar.itay.takego.model.entities.Invitation;

import java.util.List;

/**
 * Created by david salmon on 11/3/2017.
 */

/**
 * interface : everyone the inherit from me must to implement all his functions.
 * interface defines the functions we should implements.
 */
public interface DB_manager {

    boolean UserExistsOnDataBase(ContentValues newClient);
    long addUser(ContentValues newClient);
    int addModel(ContentValues newModel);
    int addCar(ContentValues newCar);
    int addBranch(ContentValues newBranch);
    int addInvitation (ContentValues newInvatation);
    long addUserNamePass(ContentValues UserPassword);
    Client checkOnDataBase(ContentValues UserPassword);

    List<CarsModel> AllCarsModel();
    List<Client> AllUsers();
    List<Branch> AllBranch();
    List<Car> AllCars();
    List<Car> allAvailableCars();
    List<Invitation> allInvatation();
    List<CarsModel> AllAvilableCarsForBranch(ContentValues Branch);
        //functions we added
    /*boolean CarModelAvailable();
    boolean addInvitaion();
    boolean removeClient();
    vo removeCar();
    boolean removeCarModel();
    boolean removeInvitation();*/

    void deleteModel(ContentValues _idDel);


    List<Branch> AllBranchByModel(ContentValues contentValues);

    Car GetCarByModelBranch(ContentValues contentValues);

    List<ContentValues> AllCarModelFree();

    void updateCar(ContentValues contentValues_update);
    void updateInvitation(ContentValues contentValues);

    List<Invitation> checkChangeAtLast10Sec();

    Invitation getAllOpenInvitation(ContentValues contentValues);
}
