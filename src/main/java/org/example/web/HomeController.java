package org.example.web;

import org.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page controller.
 */
@Controller
public class HomeController
{
  @Autowired
  private PersonService service;

  /**
   * Displays the home page.
   */
  @RequestMapping("/")
  public String home(final Model model)
  {
    model.addAttribute("persons", service.list());

    return "home";
  }
}
