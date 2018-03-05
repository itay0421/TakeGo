package com.amar.itay.takego.model.backend;

import android.content.ContentValues;

import com.amar.itay.takego.model.entities.Branch;
import com.amar.itay.takego.model.entities.Car;
import com.amar.itay.takego.model.entities.CarsModel;
import com.amar.itay.takego.model.entities.Client;
import com.amar.itay.takego.model.entities.Gearbox;
import com.amar.itay.takego.model.entities.Invitation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by itay0 on 12/11/2017.
 */
/**
 * All the class down below in format of key and value.
 * this needed for ContentValues which also goes with key and value.
 * With ContentValues we can send to the php page parameters.
 * Also we will convert from ContentValues to our classes(Car/Branch..) and also the same to other side.
 */
public class Car_GoConst {

    /**
     * format of key and value for the Branch class.
     */
    public static class BranchConst {
        public static final String CITY = "city";
        public static final String STREET = "street";
        public static final String BUILDING_NUMBER = "building_number";
        public static final String PARKING_SPACE_NUMBER = "parking_spaces_number";
        public static final String BRANCH_NUMBER = "_id";
    }

    /**
     * format of key and value for the Car class.
     */
    public static class CarConst {
        public static final String BRANCH_NUMBER = "branch_number";
        public static final String MODEL_TYPE = "model_type";
        public static final String KILOMETERS = "kilometer";
        public static final String CAR_NUMBER = "_id";
        public static final String IN_USE = "in_use";
    }

    /**
     * format of key and value for the Car Model class.
     */
    public static class CarModelConst {
        public static final String MODEL_CODE = "_id";
        public static final String COMPANY_NAME = "company_name";
        public static final String MODEL_NAME = "model_name";
        public static final String ENGINE_CAPACITY = "engine_capacity";
        public static final String GEAR_BOX = "gearbox";
        public static final String SEATS_NUMBER = "seat_number";
    }

    /**
     * format of key and value for the Client class.
     */
    public static class ClientConst {
        public static final String FAMILY_NAME = "family_name";
        public static final String PRIVATE_NAME = "private_name";
        public static final String ID = "_id";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String EMAIL = "email";
        public static final String CREDIT_CARD = "credit_card";
    }

    /**
     * format of key and value for the Invitation class.
     */
    public static class InvitationConst{
        public static final String INVITATION_ID = "_id";
        public static final String CLIENT_ID = "client_id";
        public static final String INVITATION_IS_OPEN = "invitation_is_open";
        public static final String CAR_NUMBER = "car_number_id";
        public static final String START_RENT = "start_rent";
        public static final String END_RENT = "end_rent";
        public static final String IS_FUEL = "is_fuel";
        public static final String FUEL_LITER = "fuel_liter";
        public static final String TOTAL_PAYMENT = "total_payment";

    }

    /**
     * format of key and value for the user name and password.
     */
    public static class UesrNamePasswordConst {
        public static final String CLIENT_ID = "_id";
        public static final String USER_NAME = "user_name";
        public static final String PASSWORD = "password";

    }

    /**
     * convert Invitation to ContentValues.
     * @param invitation to be convert.
     * @return ContentValues with key and value format of Invitation.
     */
    public static ContentValues InvitationToContentValues(Invitation invitation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(InvitationConst.INVITATION_ID, invitation.getInvitationId());
        contentValues.put(InvitationConst.CLIENT_ID, invitation.getClientId());
        contentValues.put(InvitationConst.INVITATION_IS_OPEN, invitation.getIsInvitationIsOpen());
        contentValues.put(InvitationConst.CAR_NUMBER, invitation.getCarNumber());
        contentValues.put(InvitationConst.IS_FUEL, invitation.getIsFuel());
        contentValues.put(InvitationConst.FUEL_LITER, invitation.getFuelLiter());
        contentValues.put(InvitationConst.TOTAL_PAYMENT, invitation.getTotalPayment());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String dateString = dateFormat.format(invitation.getStartRent());
        contentValues.put(InvitationConst.START_RENT, dateString);

        dateString = dateFormat.format(invitation.getEndRent());
        contentValues.put(InvitationConst.END_RENT, dateString);


        return contentValues;
    }

    /**
     * convert ContentValues to Invitation.
     * @param contentValues to be convert.
     * @return instance of Invitation with the ContentValues values.
     */
    public static Invitation ContentValuesToInvitation(ContentValues contentValues) {
        Invitation invitation = new Invitation();
        invitation.setInvitationId(contentValues.getAsInteger(InvitationConst.INVITATION_ID));
        invitation.setClientId(contentValues.getAsLong(InvitationConst.CLIENT_ID));
        invitation.setInvitationIsOpen(contentValues.getAsBoolean(InvitationConst.INVITATION_IS_OPEN));
        invitation.setCarNumber(contentValues.getAsInteger(InvitationConst.CAR_NUMBER));
        invitation.setIsFuel(contentValues.getAsBoolean(InvitationConst.IS_FUEL));
        invitation.setFuelLiter(contentValues.getAsInteger(InvitationConst.FUEL_LITER));
        invitation.setTotalPayment(contentValues.getAsDouble(InvitationConst.TOTAL_PAYMENT));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String dateString = contentValues.getAsString(InvitationConst.START_RENT);
        String dateString2 = contentValues.getAsString(InvitationConst.END_RENT);
        try {
            invitation.setStartRent(dateFormat.parse(dateString));
            invitation.setEndRent(dateFormat.parse(dateString2));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return invitation;
    }

    /**
     * convert Car to ContentValues.
     * @param car to be convert.
     * @return ContentValues with key and value format of Car(like we defined earlier-lines 37-42).
     */
    public static ContentValues CarToContentValues(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Car_GoConst.CarConst.BRANCH_NUMBER, car.getBranchNumber());
        contentValues.put(Car_GoConst.CarConst.MODEL_TYPE, car.getModelType());
        contentValues.put(Car_GoConst.CarConst.KILOMETERS, car.getKilometers());
        contentValues.put(Car_GoConst.CarConst.CAR_NUMBER, car.getCarNumber());
        contentValues.put(CarConst.IN_USE, car.isInUse());
        return contentValues;
    }

    /**
     * convert ContentValues to Car.
     * @param contentValues to be convert.
     * @return instance of Car with the ContentValues values.
     */
    public static Car ContentValuesToCar(ContentValues contentValues) {
        Car car = new Car();
        car.setBranchNumber(contentValues.getAsInteger(Car_GoConst.CarConst.BRANCH_NUMBER));
        car.setModelType(contentValues.getAsInteger(Car_GoConst.CarConst.MODEL_TYPE));
        car.setKilometers(contentValues.getAsInteger(Car_GoConst.CarConst.KILOMETERS));
        car.setCarNumber(contentValues.getAsInteger(Car_GoConst.CarConst.CAR_NUMBER));
        car.setInUse(contentValues.getAsBoolean(CarConst.IN_USE));
        return car;
    }

    /**
     * convert Branch to ContentValues.
     * @param branch to be convert.
     * @return ContentValues with key and value format of Branch(like we defined earlier-lines 26-32).
     */
    public static ContentValues BranchToContentValues(Branch branch) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Car_GoConst.BranchConst.BRANCH_NUMBER, branch.getBranchNumber());
        contentValues.put(Car_GoConst.BranchConst.CITY, branch.getCity());
        contentValues.put(Car_GoConst.BranchConst.STREET, branch.getStreet());
        contentValues.put(Car_GoConst.BranchConst.BUILDING_NUMBER, branch.getBuildingNumber());
        contentValues.put(Car_GoConst.BranchConst.PARKING_SPACE_NUMBER, branch.getParkingSpacesNumber());
        return contentValues;
    }

    /**
     * convert ContentValues to Branch.
     * @param contentValues to be convert.
     * @return instance of Branch
     */
    public static Branch ContentValuesToBranch(ContentValues contentValues) {

        Branch branch = new Branch();
        branch.setBranchNumber(contentValues.getAsInteger(Car_GoConst.BranchConst.BRANCH_NUMBER));
        branch.setCity(contentValues.getAsString(Car_GoConst.BranchConst.CITY));
        branch.setStreet(contentValues.getAsString((Car_GoConst.BranchConst.STREET)));
        branch.setBuildingNumber( contentValues.getAsInteger(BranchConst.BUILDING_NUMBER));
        branch.setParkingSpacesNumber(contentValues.getAsInteger(BranchConst.PARKING_SPACE_NUMBER));

        return branch;
    }

    /**
     * convert Car Model to ContentValues.
     * @param carModel to be convert.
     * @return ContentValues with key and value format of Car Model(like we defined earlier-lines 26-32).
     */
    public static ContentValues CarModelToContentValues(CarsModel carModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Car_GoConst.CarModelConst.MODEL_CODE, carModel.getModelCode());
        contentValues.put(Car_GoConst.CarModelConst.COMPANY_NAME, carModel.getCompanyName());
        contentValues.put(Car_GoConst.CarModelConst.MODEL_NAME, carModel.getModelName());
        contentValues.put(Car_GoConst.CarModelConst.ENGINE_CAPACITY, carModel.getEngineCapacity());
        contentValues.put(Car_GoConst.CarModelConst.GEAR_BOX, carModel.getGearBox().toString());
        contentValues.put(Car_GoConst.CarModelConst.SEATS_NUMBER, carModel.getSeatsNumber());
        return contentValues;
    }

    /**
     * convert ContentValues to Car Model.
     * @param contentValues to be convert.
     * @return instance of Car Model with the ContentValues values.
     */
    public static CarsModel ContentValuesToCarModel(ContentValues contentValues) {
        CarsModel carModel = new CarsModel();
        carModel.setCompanyName(contentValues.getAsString(Car_GoConst.CarModelConst.COMPANY_NAME));
        carModel.setEngineCapacity(contentValues.getAsInteger(Car_GoConst.CarModelConst.ENGINE_CAPACITY));
        carModel.setGearBox(Gearbox.valueOf(contentValues.getAsString(Car_GoConst.CarModelConst.GEAR_BOX)));
        carModel.setModelCode(contentValues.getAsInteger(Car_GoConst.CarModelConst.MODEL_CODE));
        carModel.setModelName(contentValues.getAsString(Car_GoConst.CarModelConst.MODEL_NAME));
        carModel.setSeatsNumber(contentValues.getAsInteger(Car_GoConst.CarModelConst.SEATS_NUMBER));
        return carModel;
    }

    /**
     * convert Client to ContentValues.
     * @param client to be convert.
     * @return ContentValues with key and value format of Client(like we defined earlier-lines 59-66).
     */
    public static ContentValues ClientToContentValues(Client client) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Car_GoConst.ClientConst.FAMILY_NAME, client.getFamilyName());
        contentValues.put(Car_GoConst.ClientConst.PRIVATE_NAME, client.getPrivateName());
        contentValues.put(Car_GoConst.ClientConst.ID, client.getId());
        contentValues.put(Car_GoConst.ClientConst.PHONE_NUMBER, client.getPhoneNumber());
        contentValues.put(Car_GoConst.ClientConst.EMAIL, client.getEmail());
        contentValues.put(Car_GoConst.ClientConst.CREDIT_CARD, client.getCreditCard());
        return contentValues;
    }

    /**
     * convert ContentValues to Client.
     * @param contentValues to be convert.
     * @return instance of Client with the ContentValues values.
     */
    public static Client ContentValuesToClient(ContentValues contentValues) {
        Client client = new Client();
        client.setId(contentValues.getAsLong(Car_GoConst.ClientConst.ID));
        client.setFamilyName(contentValues.getAsString(Car_GoConst.ClientConst.FAMILY_NAME));
        client.setPrivateName(contentValues.getAsString(Car_GoConst.ClientConst.PRIVATE_NAME));
        client.setPhoneNumber(contentValues.getAsString(Car_GoConst.ClientConst.PHONE_NUMBER));
        client.setEmail(contentValues.getAsString(Car_GoConst.ClientConst.EMAIL));
        client.setCreditCard(contentValues.getAsInteger(Car_GoConst.ClientConst.CREDIT_CARD));
        return client;
    }

    /**
     * convert userName to ContentValues.
     * @param userName to be convert.
     * @param password to be convert.
     * @param ID to be convert.
     * @return ContentValues with key and value format of userName.
     */
    public static ContentValues UserPasswordtoContentValues(String userName,String password,long ID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UesrNamePasswordConst.CLIENT_ID,ID);
        contentValues.put(UesrNamePasswordConst.USER_NAME,userName);
        contentValues.put(UesrNamePasswordConst.PASSWORD, password);
        return contentValues;
    }

    /**
     * convert ContentValues to UserPassword.
     * @param contentValues to be convert.
     * @return instance of UserPassword with the ContentValues values.
     */
    public static List<String> ContentValuestoUserPassword(ContentValues contentValues) {
        List<String> user_password = new ArrayList<>();
        String _id = (contentValues.getAsLong(UesrNamePasswordConst.CLIENT_ID)).toString();
        String userName =contentValues.getAsString(UesrNamePasswordConst.USER_NAME);
        String password =contentValues.getAsString(UesrNamePasswordConst.USER_NAME);
        user_password.add(_id);
        user_password.add(userName);
        user_password.add(password);
        return user_password;
    }

}
