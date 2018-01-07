package com.amar.itay.takego.model.entities;


import java.util.Date;

public class Invitation {

    private int InvitationId;
    private long ClientId;
    private boolean InvitationIsOpen;
    private int CarNumber;
    private Date StartRent;
    private Date EndRent;
    private boolean IsFuel; //if client fill fuel
    private int FuelLiter;
    private double TotalPayment;


    //******Constractor******

    public Invitation() {
    }

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

    //*****getter and setter*****


    public int getInvitationId() {
        return InvitationId;
    }

    public long getClientId() {
        return ClientId;
    }

    public boolean getIsInvitationIsOpen() {
        return InvitationIsOpen;
    }

    public int getCarNumber() {
        return CarNumber;
    }

    public Date getStartRent() {
        return StartRent;
    }

    public Date getEndRent() {
        return EndRent;
    }

    public boolean getIsFuel() {
        return IsFuel;
    }

    public int getFuelLiter() {
        return FuelLiter;
    }

    public double getTotalPayment() {
        return TotalPayment;
    }

    public void setInvitationId(int invitationId) {
        InvitationId = invitationId;
    }

    public void setClientId(long clientId) {
        ClientId = clientId;
    }

    public void setInvitationIsOpen(boolean invitationIsOpen) {
        InvitationIsOpen = invitationIsOpen;
    }

    public void setCarNumber(int carNumber) {
        CarNumber = carNumber;
    }

    public void setStartRent(Date startRent) {
        StartRent = startRent;
    }

    public void setEndRent(Date endRent) {
        EndRent = endRent;
    }

    public void setIsFuel(boolean fuel) {
        IsFuel = fuel;
    }

    public void setFuelLiter(int fuelLiter) {
        FuelLiter = fuelLiter;
    }

    public void setTotalPayment(double totalPayment) {
        TotalPayment = totalPayment;
    }
}
