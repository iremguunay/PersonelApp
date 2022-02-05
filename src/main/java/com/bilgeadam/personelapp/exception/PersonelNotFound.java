package com.bilgeadam.personelapp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class PersonelNotFound extends RuntimeException{

    public PersonelNotFound(String message) { super(message); }
}
