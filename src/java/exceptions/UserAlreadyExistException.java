/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Class UserAlreadyExistException that is used when the signUp username it´s already in use.
 * @author Aitor Ruiz de Gauna Calvo,Miguel Sánchez, Zeeshan Yaqoob
 */
public class UserAlreadyExistException extends WebApplicationException{
    /**
     * Method getErrorMessage that return the error message.
     * 
     */
    public UserAlreadyExistException() {
        super(Response.Status.CONFLICT); // 409
    }
}
