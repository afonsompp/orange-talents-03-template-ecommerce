package br.com.mercadolivre.error;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

	private final MessageSource messageSource;

	private String invalidData = "invalid.data.message";

	public ValidationExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ObjectError handler(MethodArgumentNotValidException exception) {
		List<FieldErrors> errors = extractFieldErrors(exception);

		String message = messageSource.getMessage(
				invalidData, null,
				LocaleContextHolder.getLocale());

		return new ObjectError(message, HttpStatus.BAD_REQUEST.value(), errors);

	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ObjectError handler(ConstraintViolationException exception) {
		List<FieldErrors> errors = new ArrayList<>();
		exception.getConstraintViolations().forEach(v -> errors
				.add(new FieldErrors(v.getPropertyPath().toString(), v.getMessage())));

		String message = messageSource.getMessage(
				invalidData, null,
				LocaleContextHolder.getLocale());

		return new ObjectError(message, HttpStatus.BAD_REQUEST.value(), errors);

	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ObjectError handler(BindException exception) {
		List<FieldErrors> errors = extractFieldErrors(exception);

		String message = messageSource.getMessage(
				invalidData, null,
				LocaleContextHolder.getLocale());

		return new ObjectError(message, HttpStatus.BAD_REQUEST.value(), errors);

	}

	private List<FieldErrors> extractFieldErrors(
			BindException exception) {
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
