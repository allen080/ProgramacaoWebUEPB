package com.progweb.DiarioEscolar.services.exceptions;



public class ExistingObjectSameNameException extends Exception {
    public ExistingObjectSameNameException(String message) {
        super(message);
    }
}