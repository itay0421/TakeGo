package com.amar.itay.takego.model.entities;

/**
 * Created by david salmon on 11/2/2017.
 */

public class Branch {

    private String City;
    private String Street;
    private int BuildingNumber;
    private int ParkingSpacesNumber;
    private int BranchNumber; //ID

    //default constructor.
    public Branch(){}

    //constructor.
    public Branch(String city, String street, int buildingNumber, int parkingSpacesNumber, int branchNumber) {
        City = city;
        Street = street;
        BuildingNumber = buildingNumber;
        ParkingSpacesNumber = parkingSpacesNumber;
        BranchNumber = branchNumber;
    }

    /**
     * @return the branch city.
     */
    public String getCity() {
        return City;
    }

    /**
     *
     * @param city to chanbge the current branch city.
     */
    public void setCity(String city) {
        City = city;
    }

    /**
     * @return the branch street.
     */
    public String getStreet() {
        return Street;
    }

    /**
     *
     * @param street to change the current street.
     */
    public void setStreet(String street) {
        Street = street;
    }

    /**
     *
     * @return the branch building number.
     */
    public int getBuildingNumber() {
        return BuildingNumber;
    }

    /**
     *
     * @param buildingNumber to change the current building number.
     */
    public void setBuildingNumber(int buildingNumber) {
        BuildingNumber = buildingNumber;
    }

    /**
     *
     * @return the number of parking spaces.
     */
    public int getParkingSpacesNumber() {
        return ParkingSpacesNumber;
    }

    /**
     *
     * @param parkingSpacesNumber to change the current number of parking spaces.
     */
    public void setParkingSpacesNumber(int parkingSpacesNumber) {
        ParkingSpacesNumber = parkingSpacesNumber;
    }

    /**
     *
     * @return the branch number.
     */
    public int getBranchNumber() {
        return BranchNumber;
    }

    /**
     *
     * @param branchNumber to change the current branch number.
     */
    public void setBranchNumber(int branchNumber) {
        BranchNumber = branchNumber;
    }

    public String toString()
    {
        return " City: " + City + " Street: " +  Street + " Building Number: " + BuildingNumber + " Parking Spaces: " + ParkingSpacesNumber + " Branch Number: " + BranchNumber;
    }


    //********ContentValues*******


}
