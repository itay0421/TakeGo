package com.amar.itay.takego.model.datasource;

import android.content.ContentValues;

import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;
import com.amar.itay.takego.model.entities.Invitation;
import com.amar.itay.takego.model.backend.Car_GoConst;
import com.amar.itay.takego.model.backend.DB_manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david salmon on 11/3/2017.
 */


public class List_DBManager implements DB_manager {
    static List<Branch> branchs;
    static List<Car> cars;
    static List<CarsModel> carsModels;
    static List<Client> clients;
    static List<Invitation> invitations;

    static {
        branchs = new ArrayList<>();
        cars = new ArrayList<>();
        carsModels = new ArrayList<>();
        clients = new ArrayList<>();
        invitations = new ArrayList<>();
    }


    @Override
    public boolean UserExistsOnDataBase(ContentValues ID) {
        /*int ClientsMount = clients.size();
        for (int i = 0; i < ClientsMount; i++)
            if (clients.get(i).getId() == ID)
                return true;
        return false;*/
        return false;
    }

    @Override
    public long addUser(ContentValues newClient) {
        Client client = Car_GoConst.ContentValuesToClient(newClient);
        clients.add(client);
        return client.getId();
    }

    @Override
    public int addModel(ContentValues newModel) {
        CarsModel model = Car_GoConst.ContentValuesToCarModel(newModel);
        carsModels.add(model);
        return model.getModelCode();
    }

    @Override
    public int addCar(ContentValues newCar) {
        Car car = Car_GoConst.ContentValuesToCar(newCar);
        cars.add(car);
        return car.getCarNumber();
    }
    @Override
    public int addBranch(ContentValues newBranch) {
        Branch branch = Car_GoConst.ContentValuesToBranch(newBranch);
        branchs.add(branch);
        return branch.getBranchNumber();
    }

    @Override
    public int addInvitation(ContentValues newInvatation) {
        return 0;
    }

    @Override
    public long addUserNamePass(ContentValues UserPassword) {
        return 0;
    }

    @Override
    public Client checkOnDataBase(ContentValues UserPassword) {
        return null;
    }


    @Override
    public List<CarsModel> AllCarsModel() {
        return carsModels;
    }

    @Override
    public List<Client> AllUsers() {
        return clients;
    }

    @Override
    public List<Branch> AllBranch() {
        return branchs;
    }

    @Override
    public List<Car> AllCars() {
        return cars;
    }

    @Override
    public List<Car> allAvailableCars() {
        return null;
    }

    @Override
    public List<Invitation> allInvatation() {
        return null;
    }

    @Override
    public List<CarsModel> AllAvilableCarsForBranch(ContentValues Branch) {
        return null;
    }

    @Override
    public void deleteModel(ContentValues _idDel) {

    }

    @Override
    public List<Branch> AllBranchByModel(ContentValues contentValues) {
        return null;
    }

    @Override
    public Car GetCarByModelBranch(ContentValues contentValues) {
        return null;
    }

    @Override
    public List<ContentValues> AllCarModelFree() {
        return null;
    }

    @Override
    public void updateCar(ContentValues contentValues_update) {

    }

    @Override
    public void updateInvitation(ContentValues contentValues) {

    }

    @Override
    public List<Invitation> checkChangeAtLast10Sec() {
        return null;
    }

    @Override
    public Invitation getAllOpenInvitation(ContentValues contentValues) {
        return null;
    }


}
