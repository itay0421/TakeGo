package com.amar.itay.takego.model.backend;

import android.content.ContentValues;


import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;

import java.util.List;

/**
 * Created by david salmon on 11/3/2017.
 */

public interface DB_manager {

    boolean UserExistsOnDataBase(Long ID);
    long addUser(ContentValues newClient);
    int addModel(ContentValues newModel);
    String addCar(ContentValues newCar);
    int addBranch(ContentValues newBranch);
    List<CarsModel> AllCarsModel();
    List<Client> AllUsers();
    List<Branch> AllBranch();
    List<Car> AllCars();

    //functions we added
    /*boolean CarModelAvailable();
    boolean addInvitaion();
    boolean removeClient();
    vo removeCar();
    boolean removeCarModel();
    boolean removeInvitation();*/

    void deleteModel(ContentValues _idDel);



}
