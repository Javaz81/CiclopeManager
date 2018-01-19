/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.ciclopemanager.ejb.session.exception;
//To do not automatically let the JTA to mark the transaction bounding the 
//facade as rollback=true.
//Take a look at next link ("first point of "Mind that" paragraph) to know why 
//catchin general PersistenceException is not enough:
//http://piotrnowicki.com/2013/03/jpa-and-cmt-why-catching-persistence-exception-is-not-enough/
import javax.ejb.ApplicationException; 

/**
 *
 * @author andrea
 */
@ApplicationException
public class FacadeException extends Exception {

    /**
     * Creates a new instance of <code>FacadeException</code> without detail
     * message.
     */
    public FacadeException() {
    }

    /**
     * Constructs an instance of <code>FacadeException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public FacadeException(String msg) {
        super(msg);
    }

    public FacadeException(Throwable cause) {
        super(cause);      
    }
    
    
}
