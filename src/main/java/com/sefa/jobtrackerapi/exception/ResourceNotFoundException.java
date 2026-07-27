package com.sefa.jobtrackerapi.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("ID " + id + " olan iş başvurusu bulunamadı");
    }
}