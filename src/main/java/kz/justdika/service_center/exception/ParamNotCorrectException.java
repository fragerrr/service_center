package kz.justdika.service_center.exception;

public class ParamNotCorrectException extends RuntimeException{
    public ParamNotCorrectException(String message){
        super(message);
    }
}
