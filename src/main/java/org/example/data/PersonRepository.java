package org.example.data;

import org.example.domain.Person;
import org.springframework.data.domain.Sort;

/**
 * Contract for data access operations on {@link Person}.
 */
public interface PersonRepository extends ModelRepository<Person>
{
  Sort DEFAULT_SORT = new Sort("firstName", "lastName", "emailAddress");

  /**
   * Finds a person having a specified email address.
   *
   * @param emailAddress The email address to find.
   * @return A {@link Person} if the specified email address is found,
   * {@code null} otherwise.
   */
  Person findByEmailAddress(String emailAddress);
}
