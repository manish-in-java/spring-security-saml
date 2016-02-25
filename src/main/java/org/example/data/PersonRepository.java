package org.example.data;

import org.example.domain.Person;

/**
 * Contract for data access operations on {@link Person}.
 */
public interface PersonRepository extends ModelRepository<Person>
{
  /**
   * Finds a person having a specified email address.
   *
   * @param emailAddress The email address to find.
   * @return A {@link Person} if the specified email address is found,
   * {@code null} otherwise.
   */
  Person findByEmailAddress(String emailAddress);
}
