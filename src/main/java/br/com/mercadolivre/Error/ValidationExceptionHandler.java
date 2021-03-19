package br.com.mercadolivre.Error;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

	private final MessageSource messageSource;

	private final String INVALID_DATA = "invalid.data.message";

	public ValidationExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ObjectError handler(MethodArgumentNotValidException exception) {
		List<FieldErrors> errors = extractFieldErrors(exception);

		String message = messageSource.getMessage(INVALID_DATA, null,
				LocaleContextHolder.getLocale());

		var error = new ObjectError(message, HttpStatus.BAD_REQUEST.value(), errors);

		return error;
	}

	private List<FieldErrors> extractFieldErrors(
			MethodArgumentNotValidException exception) {
		List<FieldErrors> response = new ArrayList<>();
		List<FieldError> errors = exception.getFieldErrors();

		errors.forEach(e -> {
			var message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			var error = new FieldErrors(e.getField(), message);
			response.add(error);
		});

		return response;
	}
}
