package org.example.web;

import org.example.domain.Person;
import org.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User Registration controller
 */
@Controller
@RequestMapping("/register")
public class RegistrationController
{
  @Autowired
  private PersonService personService;

  /**
   * Saves a person.
   */
  @RequestMapping(method = RequestMethod.POST)
  public String save(final Person person, final Model model)
  {
    try
    {
      personService.register(person);
    }
    catch (final RuntimeException ex)
    {
      // Add an error to the model.
      model.addAttribute("error", ex.getMessage());

      // Display the registration page again.
      return show();
    }

    // Redirect to the login page.
    return "redirect:/login";
  }

  /**
   * Displays the registration page.
   */
  @RequestMapping(method = RequestMethod.GET)
  public String show()
  {
    return "registration";
  }
}
