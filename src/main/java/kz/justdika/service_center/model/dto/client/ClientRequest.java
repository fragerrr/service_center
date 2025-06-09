package kz.justdika.service_center.model.dto.client;

public class ClientRequest {
    public String firstName;
    public String lastName;

    public String phoneNumber;

    public ClientRequest(String firstName, String lastName, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public ClientRequest(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}
