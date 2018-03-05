package com.amar.itay.takego.model.entities;


import java.util.Date;

public class Invitation {
    //attribute
    private int InvitationId;
    private long ClientId;
    private boolean InvitationIsOpen;
    private int CarNumber;
    private Date StartRent;
    private Date EndRent;
    private boolean IsFuel; //if client fill fuel
    private int FuelLiter;
    private double TotalPayment;


    /**
     * default constructor.
     */
    public Invitation() {
    }

    /**
     * constructor.
     * @param invitationId to insert invitation id.
     * @param clientId to insert client id.
     * @param invitationIsOpen to insert if invitation Is Open.
     * @param carNumber to insert car number.
     * @param startRent to insert start rent.
     * @param endRent to insert end rent.
     * @param isFuel to insert if tank is fuel.
     * @param fuelLiter to insert fuel liter.
     * @param totalPayment to insert total payment.
     */
    public Invitation(int invitationId, long clientId, boolean invitationIsOpen, int carNumber, Date startRent,
                      Date endRent, boolean isFuel, int fuelLiter, double totalPayment) {
        InvitationId = invitationId;
        ClientId = clientId;
        InvitationIsOpen = invitationIsOpen;
        CarNumber = carNumber;
        StartRent = startRent;
        EndRent = endRent;
        IsFuel = isFuel;
        FuelLiter = fuelLiter;
        TotalPayment = totalPayment;
    }
    /**
     * @return the invitation id.
     */
    public int getInvitationId() {
        return InvitationId;
    }
    /**
     * @return the client id.
     */
    public long getClientId() {
        return ClientId;
    }
    /**
     * @return if  invitation is open.
     */
    public boolean getIsInvitationIsOpen() {
        return InvitationIsOpen;
    }
    /**
     * @return the car number.
     */
    public int getCarNumber() {
        return CarNumber;
    }
    /**
     * @return the start rent date of the car.
     */
    public Date getStartRent() {
        return StartRent;
    }
    /**
     * @return the end rent date of the car.
     */
    public Date getEndRent() {
        return EndRent;
    }
    /**
     * @return if the tank is fuel.
     */
    public boolean getIsFuel() {
        return IsFuel;
    }
    /**
     * @return the fuel liter.
     */
    public int getFuelLiter() {
        return FuelLiter;
    }
    /**
     * @return the total payment.
     */
    public double getTotalPayment() {
        return TotalPayment;
    }
    /**
     *
     * @param invitationId to change the current invitation id.
     */
    public void setInvitationId(int invitationId) {
        InvitationId = invitationId;
    }
    /**
     *
     * @param clientId to change the current client id.
     */
    public void setClientId(long clientId) {
        ClientId = clientId;
    }
    /**
     *
     * @param invitationIsOpen to change the current if invitation is open.
     */
    public void setInvitationIsOpen(boolean invitationIsOpen) {
        InvitationIsOpen = invitationIsOpen;
    }
    /**
     *
     * @param carNumber to change the current car number.
     */
    public void setCarNumber(int carNumber) {
        CarNumber = carNumber;
    }
    /**
     *
     * @param startRent to change the current date of start rent.
     */
    public void setStartRent(Date startRent) {
        StartRent = startRent;
    }
    /**
     *
     * @param endRent to change the current date of end rent.
     */
    public void setEndRent(Date endRent) {
        EndRent = endRent;
    }
    /**
     *
     * @param fuel to change the current if the tank is fuel.
     */
    public void setIsFuel(boolean fuel) {
        IsFuel = fuel;
    }
    /**
     *
     * @param fuelLiter to change the current fuel liter.
     */
    public void setFuelLiter(int fuelLiter) {
        FuelLiter = fuelLiter;
    }
    /**
     *
     * @param totalPayment to change the current total payment.
     */
    public void setTotalPayment(double totalPayment) {
        TotalPayment = totalPayment;
    }
}
