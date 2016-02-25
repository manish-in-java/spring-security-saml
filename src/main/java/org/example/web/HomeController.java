package org.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController
{
  @RequestMapping("/")
  public String home()
  {
    return "home";
  }

  @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/protected")
  public String _protected()
  {
    return "protected";
  }

  @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/unprotected")
  public String _unprotected()
  {
    return "unprotected";
  }
}
