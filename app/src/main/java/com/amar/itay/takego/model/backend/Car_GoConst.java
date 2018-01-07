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


/**
 * Created by itay0 on 12/11/2017.
 */

public class Car_GoConst {


    public static class BranchConst {
        public static final String CITY = "city";
        public static final String STREET = "street";
        public static final String BUILDING_NUMBER = "building_number";
        public static final String PARKING_SPACE_NUMBER = "parking_spaces_number";
        public static final String BRANCH_NUMBER = "_id";
    }

    public static class CarConst {
        public static final String BRANCH_NUMBER = "branch_number";
        public static final String MODEL_TYPE = "model_type";
        public static final String KILOMETERS = "kilometer";
        public static final String CAR_NUMBER = "_id";
        public static final String IN_USE = "in_use";
    }

    public static class CarModelConst {
        public static final String MODEL_CODE = "_id";
        public static final String COMPANY_NAME = "company_name";
        public static final String MODEL_NAME = "model_name";
        public static final String ENGINE_CAPACITY = "engine_capacity";
        public static final String GEAR_BOX = "gearbox";
        public static final String SEATS_NUMBER = "seat_number";
    }

    public static class ClientConst {
        public static final String FAMILY_NAME = "family_name";
        public static final String PRIVATE_NAME = "private_name";
        public static final String ID = "_id";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String EMAIL = "email";
        public static final String CREDIT_CARD = "credit_card";
    }

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

    public static ContentValues CarToContentValues(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Car_GoConst.CarConst.BRANCH_NUMBER, car.getBranchNumber());
        contentValues.put(Car_GoConst.CarConst.MODEL_TYPE, car.getModelType());
        contentValues.put(Car_GoConst.CarConst.KILOMETERS, car.getKilometers());
        contentValues.put(Car_GoConst.CarConst.CAR_NUMBER, car.getCarNumber());
        contentValues.put(CarConst.IN_USE, car.isInUse());
        return contentValues;
    }

    public static Car ContentValuesToCar(ContentValues contentValues) {
        Car car = new Car();
        car.setBranchNumber(contentValues.getAsInteger(Car_GoConst.CarConst.BRANCH_NUMBER));
        car.setModelType(contentValues.getAsInteger(Car_GoConst.CarConst.MODEL_TYPE));
        car.setKilometers(contentValues.getAsInteger(Car_GoConst.CarConst.KILOMETERS));
        car.setCarNumber(contentValues.getAsInteger(Car_GoConst.CarConst.CAR_NUMBER));
        car.setInUse(contentValues.getAsBoolean(CarConst.IN_USE));
        return car;
    }

    public static ContentValues BranchToContentValues(Branch branch) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Car_GoConst.BranchConst.BRANCH_NUMBER, branch.getBranchNumber());
        contentValues.put(Car_GoConst.BranchConst.CITY, branch.getCity());
        contentValues.put(Car_GoConst.BranchConst.STREET, branch.getStreet());
        contentValues.put(Car_GoConst.BranchConst.BUILDING_NUMBER, branch.getBuildingNumber());
        contentValues.put(Car_GoConst.BranchConst.PARKING_SPACE_NUMBER, branch.getParkingSpacesNumber());
        return contentValues;
    }

    public static Branch ContentValuesToBranch(ContentValues contentValues) {

        Branch branch = new Branch();
        branch.setBranchNumber(contentValues.getAsInteger(Car_GoConst.BranchConst.BRANCH_NUMBER));
        branch.setCity(contentValues.getAsString(Car_GoConst.BranchConst.CITY));
        branch.setStreet(contentValues.getAsString((Car_GoConst.BranchConst.STREET)));
        branch.setBuildingNumber( contentValues.getAsInteger(BranchConst.BUILDING_NUMBER));
        branch.setParkingSpacesNumber(contentValues.getAsInteger(BranchConst.PARKING_SPACE_NUMBER));

        return branch;
    }

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

    //Client
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

}
