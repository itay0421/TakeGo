package com.amar.itay.takego.model.entities;

/**
 * Created by david salmon on 11/4/2017.
 */

/**
 * Client class represents the clients that rent the cars.
 */
public class Client {
    private String FamilyName;
    private String PrivateName;
    private long Id;
    private String PhoneNumber;
    private String email;
    private long CreditCard;

    /**
     * default constructor.
     */
    public Client() {
    }

    public void setId(long id) {
        Id = id;
    }

    /**
     * constructor.
     * @param familyName to insert family name.
     * @param privateName to insert private name.
     * @param id to insert id.
     * @param phoneNumber to insert phone number.
     * @param email to insert email.
     * @param creditCard to insert credit card.
     */
    public Client(String familyName, String privateName, long id, String phoneNumber, String email, long creditCard) {
        FamilyName = familyName;
        PrivateName = privateName;
        Id = id;
        PhoneNumber = phoneNumber;
        this.email = email;
        CreditCard = creditCard;
    }

    /**
     * @return the client family name.
     */
    public String getFamilyName() {
        return FamilyName;
    }

    /**
     * @param familyName to change the current family name.
     */
    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    /**
     * @return the client private name.
     */
    public String getPrivateName() {
        return PrivateName;
    }

    /**
     * @param privateName to change the current private name.
     */
    public void setPrivateName(String privateName) {
        PrivateName = privateName;
    }

    /**
     * @return the client phone number.
     */
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    /**
     * @param phoneNumber to change the current phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    /**
     * @return the client id.
     */
    public long getId() {
        return Id;
    }

    /**
     * @return the client email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email to change the current email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the client credit card number.
     */
    public long getCreditCard() {
        return CreditCard;
    }

    /**
     * @param creditCard to change the current credic card number.
     */
    public void setCreditCard(long creditCard) {
        CreditCard = creditCard;
    }
    /**
     * @return string with all attribute of the class.
     */
    public String toString()
    {
        return "Name: " + PrivateName + " " + FamilyName + " Id: " + Id + " Phone Number: " + PhoneNumber + " Email: " + email + " Credit Card: " + CreditCard;
    }


}
