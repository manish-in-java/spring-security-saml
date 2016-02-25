package org.example.service;

import org.example.data.PersonRepository;
import org.example.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic operations for {@link Person}.
 */
@Service
@Transactional(readOnly = true)
public class PersonService
{
  @Autowired
  private PersonRepository repository;

  /**
   * Authenticates a person using an email address and a password.  The email
   * address is used to look up the person and the provided password is
   * compared with that stored for the person.
   *
   * @param emailAddress The email address for the person to authenticate.
   * @param password     The password to use to authenticate.
   * @return {@code true} if a person with the specified email address exists
   * and has the specified password.
   */
  public Person authenticate(final String emailAddress, final String password)
  {
    if (emailAddress == null)
    {
      // A person cannot be authenticated without the email address.
      throw new IllegalArgumentException(Errors.Authentication.BLANK_EMAIL);
    }

    if (password == null)
    {
      // A person cannot be authenticated without the password.
      throw new IllegalArgumentException(Errors.Authentication.BLANK_PASSWORD);
    }

    // Attempt to locate the person with the specified email address, making
    // sure that a case-insensitive search is performed.
    final Person person = repository.findByEmailAddress(emailAddress.trim().toLowerCase());

    if (person == null)
    {
      // No person with the specified email address was found.
      throw new IllegalArgumentException(Errors.Authentication.UNKNOWN_EMAIL);
    }

    if (!person.getPassword().equals(password.trim()))
    {
      // The password did not match.
      throw new IllegalArgumentException(Errors.Authentication.PASSWORD_MISMATCH);
    }

    return person;
  }

  /**
   * Gets all persons registered with the application.
   *
   * @return A {@link List} of {@link Person}s.
   */
  public List<Person> list()
  {
    return (List) repository.findAll(PersonRepository.DEFAULT_SORT);
  }

  /**
   * Registers a person with specified information.
   *
   * @param person The person to register.
   */
  @Transactional
  public void register(final Person person)
  {
    // Ensure that a person with the provided email address does not exist
    // already.
    if (repository.findByEmailAddress(person.getEmailAddress()) != null)
    {
      throw new IllegalArgumentException(Errors.Registration.DUPLICATE_EMAIL);
    }

    // Save the person.
    repository.save(person);
  }

  /**
   * Defines errors that may be encountered during processing.
   */
  private static final class Errors
  {
    /**
     * Defines errors that may be encountered when authenticating a user.
     */
    private static final class Authentication
    {
      static final String BLANK_EMAIL       = "authentication.email.blank";
      static final String BLANK_PASSWORD    = "authentication.password.blank";
      static final String PASSWORD_MISMATCH = "authentication.password.mismatch";
      static final String UNKNOWN_EMAIL     = "authentication.email.unknown";
    }

    /**
     * Defines errors that may be encountered when registering a user.
     */
    private static final class Registration
    {
      static final String DUPLICATE_EMAIL = "registration.email.duplicate";
    }
  }
}
