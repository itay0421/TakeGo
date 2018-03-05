package com.amar.itay.takego.model.entities;

/**
 * Car model class represents the car model to rent.
 */
public class CarsModel {

    private int ModelCode;//_id
    private String CompanyName;
    private String ModelName;
    private int EngineCapacity;
    private Gearbox GearBox;
    private int SeatsNumber;

    /**
     * default constructor.
     */
    public CarsModel() {
    }

    /**
     * constructor.
     * @param modelCode to insert model code.
     * @param companyName to insert company name.
     * @param modelName to insert model name.
     * @param engineCapacity to insert engine capacity.
     * @param gearBox to insert gear box.
     * @param seatsNumber to insert seats number.
     */
    public CarsModel(int modelCode, String companyName, String modelName, int engineCapacity, Gearbox gearBox, int seatsNumber) {
        ModelCode = modelCode;
        CompanyName = companyName;
        ModelName = modelName;
        EngineCapacity = engineCapacity;
        GearBox = gearBox;
        SeatsNumber = seatsNumber;
    }

    /**
     * @return the car Model code.
     */
    public int getModelCode() {
        return ModelCode;
    }

    /**
     * @param modelCode to change the current model code.
     */
    public void setModelCode(int modelCode) {
        ModelCode = modelCode;
    }

    /**
     * @return the company name.
     */
    public String getCompanyName() {
        return CompanyName;
    }

    /**
     * @param companyName to change the current company name.
     */
    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    /**
     * @return the car model name.
     */
    public String getModelName() {
        return ModelName;
    }

    /**
     * @param modelName to change the current car model name.
     */
    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    /**
     * @return the car engine capacity.
     */
    public int getEngineCapacity() {
        return EngineCapacity;
    }

    /**
     * @param engineCapacity to change the current car engine capacity.
     */
    public void setEngineCapacity(int engineCapacity) {
        EngineCapacity = engineCapacity;
    }

    /**
     * @return the car gear box.
     */
    public Gearbox getGearBox() {
        return GearBox;
    }

    /**
     * @param gearBox to change the current car gear box.
     */
    public void setGearBox(Gearbox gearBox) {
        GearBox = gearBox;
    }

    /**
     * @return the car seats number.
     */
    public int getSeatsNumber() {
        return SeatsNumber;
    }

    /**
     * @param seatsNumber to change the current seats number.
     */
    public void setSeatsNumber(int seatsNumber) {
        SeatsNumber = seatsNumber;
    }
    /**
     *
     * @return string with all attribute of the class.
     */
    public String toString() {
        return "Model Code: " + ModelCode + " Company Name: " + CompanyName + " Model Name: " + ModelName + "Engine Capacity: " + " Gear Box: " + GearBox + " Seat Numbers: " + SeatsNumber;
    }
}
