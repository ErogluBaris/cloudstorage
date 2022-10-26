package com.udacity.jwdnd.course1.cloudstorage.enums;

public enum ErrorMessages {

    USER_EXISTS("Given username is not available, already exists"),
    USER_NOT_CREATED("User could not created with the given information");

    private final String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
