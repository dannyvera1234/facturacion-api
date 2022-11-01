package com.facturacion.ideas.api.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)

    // org.springframework.security.access.AccessDeniedException.class se debe
    // agrega spring Security
    // para que reconosca
    @ExceptionHandler({UnauthorizedException.class,})
    @ResponseBody
    public void unauthorizedRequest() {
        // Empty. Nothing to do
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public ErrorMessage notFoundRequest(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, org.springframework.dao.DuplicateKeyException.class,
            org.springframework.web.bind.support.WebExchangeBindException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class,
            org.springframework.web.server.ServerWebInputException.class,
            DuplicatedResourceException.class,
            NotDataAccessException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            KeyAccessException.class,
            ViolectRestrictException.class
    })
    @ResponseBody
    public ErrorMessage badRequest(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ConflictException.class})
    @ResponseBody
    public ErrorMessage conflict(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.CONFLICT.value());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ForbiddenException.class})
    @ResponseBody
    public ErrorMessage forbidden(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.FORBIDDEN.value());
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler({BadGatewayException.class})
    @ResponseBody
    public ErrorMessage badGateway(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.BAD_GATEWAY.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ErrorMessage exception(Exception exception) { // The error must be corrected
        exception.printStackTrace();
        return new ErrorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ErrorMessage handleValidateException(MethodArgumentNotValidException exception) {

        Map<String, String> errorsField = new HashMap<>();
		 exception.getBindingResult().getAllErrors().forEach( (error) ->{
			 String  fieldName = ((FieldError)error).getField();
			 String message = error.getDefaultMessage();
			 errorsField.put(fieldName, message);

 		 });

        return new ErrorMessage(exception,  errorsField, HttpStatus.BAD_REQUEST.value());
    }

}
