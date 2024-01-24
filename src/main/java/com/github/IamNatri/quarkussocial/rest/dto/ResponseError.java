package com.github.IamNatri.quarkussocial.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Data
public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY = 422;
    private String message;
    private Collection<FieldError> fieldErrors;


    public ResponseError(String message, Collection<FieldError> fieldErrors) {
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public static <T> ResponseError fromConstraintViolations(
            Set<ConstraintViolation<T>> constraintViolations) {
        List<FieldError> fieldsErrors = constraintViolations.
                stream()
                .map( constraintViolation -> new FieldError(
                        constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()))
                .collect(Collectors.toList());

        String message = "Invalid fields";

        var responseError = new ResponseError(message, fieldsErrors);

        return responseError;

    }

    public Response withStatusCode(int statusCode) {
        return Response.status(statusCode).entity(this).build();
    }
}
