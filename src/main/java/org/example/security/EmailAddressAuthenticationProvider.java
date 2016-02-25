package org.example.security;

import org.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * An implementation of Spring Security {@link AuthenticationProvider} that
 * authenticates a client using an email address and a password.
 */
public class EmailAddressAuthenticationProvider implements AuthenticationProvider
{
  @Autowired
  private PersonService personService;

  /**
   * Processes authentication information and attempts to authenticate the user.
   */
  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException
  {
    if (personService.authenticate(authentication.getName(), (String) authentication.getCredentials()))
    {
      // If the user authenticated successfully, set the authentication token
      // as authenticated.
      authentication.setAuthenticated(true);

      // Save the authentication token.
      SecurityContextHolder.getContext().setAuthentication(authentication);

      return authentication;
    }

    throw new AuthenticationServiceException(String.format("The email address [%s] could not be authenticated.", authentication.getName()));
  }

  /**
   * Indicates that authentication using an email address and a password is
   * supported.
   */
  @Override
  public boolean supports(final Class<?> authentication)
  {
    return authentication != null && authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
