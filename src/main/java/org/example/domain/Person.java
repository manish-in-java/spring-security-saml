package org.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * Represents a person.
 */
@Entity
@Table(name = "person")
public class Person extends Model
{
  @Column(name = "email_address", unique = true)
  @NotNull
  @Size(max = 254, min = 6)
  private String emailAddress;

  @Column(name = "first_name")
  @NotNull
  @Size(max = 50, min = 1)
  private String firstName;

  @Column(name = "last_name")
  @NotNull
  @Size(max = 50, min = 1)
  private String lastName;

  @Column(name = "password")
  @Size(max = 1000)
  private String password;

  /**
   * Default constructor.
   */
  public Person()
  {
    super();
  }

  /**
   * Creates a person with a specified email address, first name and last name.
   *
   * @param emailAddress The person's email address.
   * @param firstName    The person's first name.
   * @param lastName     The person's last name.
   */
  public Person(final String emailAddress, final String firstName, final String lastName)
  {
    this();

    setEmailAddress(emailAddress);
    setFirstName(firstName);
    setLastName(lastName);
  }

  /**
   * Gets the email address for the person.  Used to identify the person
   * uniquely.
   *
   * @return The email address for the person.
   */
  public String getEmailAddress()
  {
    return emailAddress;
  }

  /**
   * Gets the person's first name.
   *
   * @return The person's first name.
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Gets the person's last name.
   *
   * @return The person's last name.
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * Gets the full name for the person as a combination of their first and
   * last names.
   *
   * @return The person's full name.
   */
  public String getName()
  {
    return String.format("%s %s", firstName, lastName);
  }

  /**
   * Gets the authentication password for the person.
   *
   * @return The authentication password for the person.
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * Sets the email address for the person.
   *
   * @param emailAddress The email address for the person.
   */
  public void setEmailAddress(final String emailAddress)
  {
    this.emailAddress = Optional.ofNullable(emailAddress).map(e -> e.trim().toLowerCase()).orElse(null);
  }

  /**
   * Sets the person's first name.
   *
   * @param firstName The person's first name.
   */
  public void setFirstName(final String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * Sets the person's last name.
   *
   * @param lastName The person's last name.
   */
  public void setLastName(final String lastName)
  {
    this.lastName = lastName;
  }

  /**
   * Sets the authentication password for the person.
   *
   * @param password The authentication password for the person.
   */
  public void setPassword(final String password)
  {
    this.password = password;
  }
}
