package org.example.security;

import org.example.domain.Person;
import org.example.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Automatically registers a user successfully authenticated with SAML SSO.
 */
public class SAMLAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
  private static Logger LOGGER = LoggerFactory.getLogger(SAMLAuthenticationSuccessHandler.class);

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

    LOGGER.debug(String.format("Attempting to auto-register principal [%s].", principal));

    // Prepare to attempt registering the user.
    final Person person = new Person(principal, getFirstName(credential), getLastName(credential));

    try
    {
      // Attempt to register the user.
      service.register(person);

      LOGGER.debug(String.format("Principal [%s] auto-registered successfully.", principal));
    }
    catch (final Throwable t)
    {
      LOGGER.debug(String.format("Principal [%s] already exists.", principal));
    }
    finally
    {
      LOGGER.debug(String.format("Setting profile information for principal [%s].", principal));

      // Generate an authentication token using the authenticated information.
      final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal()
          , authentication.getCredentials()
          , Collections.singletonList(new SimpleGrantedAuthority(ROLE)));

      // Save the person's full name so that it can be used in the presentation
      // layer later on.
      token.setDetails(person.getName());

      // Save the authentication token.
      SecurityContextHolder.getContext().setAuthentication(token);

      LOGGER.debug(String.format("Profile information set for principal [%s].", principal));
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
    return getStringAttribute(credential, "FirstName");
  }

  /**
   * Gets the last name attribute from a {@link SAMLCredential}.
   *
   * @param credential A {@link SAMLCredential}.
   * @return The last name if found, {@code null} otherwise.
   */
  private String getLastName(final SAMLCredential credential)
  {
    return getStringAttribute(credential, "LastName");
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
