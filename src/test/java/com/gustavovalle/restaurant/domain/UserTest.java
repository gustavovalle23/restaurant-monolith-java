package com.gustavovalle.restaurant.domain;

import java.time.Instant;
import java.time.LocalDate;

import com.gustavovalle.restaurant.domain.entities.UserID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gustavovalle.restaurant.domain.entities.User;
import com.gustavovalle.restaurant.domain.exceptions.DomainException;
import com.gustavovalle.restaurant.domain.validation.ThrowsValidationHandler;

@DisplayName("User Unit Tests")
public class UserTest {

	@Test
	public void givenAValidParams_whenCallNewUser_thenInstantiateAnUser() {
		final UserID id = new UserID(Long.parseLong("1"));
		final String name = "Test Name User";
		final String email = "test@example";
		final String password = "fakepassword";
		final LocalDate birthDate = LocalDate.now();
		final Boolean isActive = true;

		final User user = User.newUserWithId(id, name, email, password, birthDate, isActive);

		Assertions.assertNotNull(user);
		Assertions.assertEquals(user.getName(), name);
		Assertions.assertEquals(user.getEmail(), email);
		Assertions.assertEquals(user.getPassword(), password);
		Assertions.assertEquals(user.getBirthDate(), birthDate);
		Assertions.assertEquals(user.isActive(), isActive);
		Assertions.assertNotNull(user.getId());
		Assertions.assertNotNull(user.getCreatedAt());
		Assertions.assertNotNull(user.getUpdatedAt());
		Assertions.assertNull(user.getDeletedAt());
	}

	@Test
	public void givenAnInvalidEmailParam_whenCallNewUser_thenShouldReceiveError() {
		final UserID id = new UserID(Long.parseLong("1"));
		final String name = "Test Name User";
		final String email = "emailinvalido@gmail.c";
		final String password = "fakepassword";
		final LocalDate birthDate = LocalDate.now();
		final Boolean isActive = true;

		final User user = User.newUserWithId(id, name, email, password, birthDate, isActive);

		final DomainException actualException = Assertions.assertThrows(DomainException.class,
				() -> user.validate(new ThrowsValidationHandler()));

		Assertions.assertEquals(1, actualException.getErrors().size());
		Assertions.assertEquals("Should be a valid e-mail!", actualException.getErrors().get(0).getMessage());
	}
}