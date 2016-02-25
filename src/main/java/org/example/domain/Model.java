package org.example.domain;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

/**
 * Represents an entity relevant to the business domain of the application.
 */
@MappedSuperclass
public abstract class Model
{
  @Column(name = "id")
  @Generated(GenerationTime.INSERT)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @SuppressWarnings("unused")
  private Long id;

  /**
   * Gets the unique identifier for this entity instance.
   *
   * @return The unique identifier for this entity instance.
   */
  public Long getID()
  {
    return id;
  }
}
