package org.example.security;

import org.example.domain.Person;
import org.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

/**
 * An implementation of Spring Security {@link AuthenticationProvider} that
 * authenticates a client using an email address and a password.
 */
public class EmailAddressAuthenticationProvider implements AuthenticationProvider
{
  private static final String ROLE = "User";

  @Autowired
  private PersonService personService;

  /**
   * Processes authentication information and attempts to authenticate the user.
   */
  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException
  {
    try
    {
      // Attempt to authenticate the person.
      final Person person = personService.authenticate(authentication.getName(), (String) authentication.getCredentials());

      // Generate an authentication token using the authenticated information.
      final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal()
          , authentication.getCredentials()
          , Collections.singletonList(new SimpleGrantedAuthority(ROLE)));

      // Save the person's full name so that it can be used in the presentation
      // layer later on.
      token.setDetails(person.getName());

      // Save the authentication token.
      SecurityContextHolder.getContext().setAuthentication(token);

      return token;
    }
    catch (final Throwable t)
    {
      throw new AuthenticationServiceException(String.format("The email address [%s] could not be authenticated.", authentication.getName()), t);
    }
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
