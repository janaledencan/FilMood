package hr.ferit.filmood.common.exception;

import hr.ferit.filmood.common.exception.error.ErrorKey;
import org.springframework.http.HttpStatus;

public interface ServiceException {

    ErrorKey getErrorKey();

    HttpStatus getStatus();
}
