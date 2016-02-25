package org.example.service;

import org.example.data.PersonRepository;
import org.example.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public boolean authenticate(final String emailAddress, final String password)
  {
    if (emailAddress == null)
    {
      // A person cannot be authenticated without the email address.
      return false;
    }

    if (password == null)
    {
      // A person cannot be authenticated without the password.
      return false;
    }

    // Attempt to locate the person with the specified email address, making
    // sure that a case-insensitive search is performed.
    final Person person = repository.findByEmailAddress(emailAddress.trim().toLowerCase());

    if (person == null)
    {
      // No person with the specified email address was found.
      return false;
    }

    // Compare the supplied password with the stored value.
    return person.getPassword().equals(password.trim());
  }
}
