package org.example.security;

import org.example.domain.Person;
import org.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Automatically registers a successfully authenticated user.
 */
public class AutoRegisteringAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
  private static final String ROLE = "User";

  @Autowired
  private PersonService service;

  @Override
  public void onAuthenticationSuccess(final HttpServletRequest request
      , final HttpServletResponse response
      , final Authentication authentication)
      throws ServletException, IOException
  {
    // Extract principal and credentials from the authentication token.
    final String principal = (String) authentication.getPrincipal();
    final SAMLCredential credential = (SAMLCredential) authentication.getCredentials();

    // Prepare to attempt registering the user.
    final Person person = new Person(principal, getFirstName(credential), getLastName(credential));

    try
    {
      // Attempt to register the user.
      service.register(person);
    }
    finally
    {
      // Generate an authentication token using the authenticated information.
      final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal()
          , authentication.getCredentials()
          , Collections.singletonList(new SimpleGrantedAuthority(ROLE)));

      // Save the person's full name so that it can be used in the presentation
      // layer later on.
      token.setDetails(person.getName());

      // Save the authentication token.
      SecurityContextHolder.getContext().setAuthentication(token);
    }

    super.onAuthenticationSuccess(request, response, authentication);
  }

  /**
   * Gets the first name attribute from a {@link SAMLCredential}.
   *
   * @param credential A {@link SAMLCredential}.
   * @return The first name if found, {@code null} otherwise.
   */
  private String getFirstName(final SAMLCredential credential)
  {
    return getStringAttribute(credential, "firstName");
  }

  /**
   * Gets the last name attribute from a {@link SAMLCredential}.
   *
   * @param credential A {@link SAMLCredential}.
   * @return The last name if found, {@code null} otherwise.
   */
  private String getLastName(final SAMLCredential credential)
  {
    return getStringAttribute(credential, "lastName");
  }

  /**
   * Gets a {@link String} attribute from a {@link SAMLCredential}.
   *
   * @param credential A {@link SAMLCredential}.
   * @param attribute  The name of the attribute to read.
   * @return The attribute value if found, {@code null} otherwise.
   */
  private String getStringAttribute(final SAMLCredential credential, final String attribute)
  {
    return credential == null
        ? null
        : credential.getAttributeAsString(attribute);
  }
}
