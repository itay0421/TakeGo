package com.amar.itay.takego.model.entities;

/**
 * Created by david salmon on 11/2/2017.
 */

public class Car {
    //
    private int BranchNumber;
    private int ModelType;//just int
    private int Kilometers;
    private String CarNumber;

    public Car() {
    }

    public Car(int branchNumber, int modelType, int kilometers, String carNumber) {
        BranchNumber = branchNumber;
        ModelType = modelType;
        Kilometers = kilometers;
        CarNumber = carNumber;
    }

    /**
     * @return the branch number.
     */
    public int getBranchNumber() {
        return BranchNumber;
    }

    /**
     * @param branchNumber to change the current branch number.
     */
    public void setBranchNumber(int branchNumber) {
        BranchNumber = branchNumber;
    }

    /**
     * @return the car model type.
     */
    public int getModelType() {
        return ModelType;
    }

    /**
     * @param modelType to change the cutrrent car model type.
     */
    public void setModelType(int modelType) {
        ModelType = modelType;
    }

    /**
     * @return the car kilometers.
     */
    public int getKilometers() {
        return Kilometers;
    }

    /**
     * @param kilometers to change the current car kilometers.
     */
    public void setKilometers(int kilometers) {
        Kilometers = kilometers;
    }

    /**
     * @return the car number.
     */
    public String getCarNumber() {
        return CarNumber;
    }

    /**
     * @param carNumber to change the car number.
     */
    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public String toString() {
        return "Branch Number: " + BranchNumber + " Model Type: " + ModelType + " Kilometers: " + Kilometers + " Car Number: " + CarNumber;
    }

    //********ContentValues*******


}
