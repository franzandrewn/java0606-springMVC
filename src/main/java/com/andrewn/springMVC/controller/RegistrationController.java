package com.andrewn.springMVC.controller;

import com.andrewn.springMVC.model.User;
import com.andrewn.springMVC.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

  private final UserService userService;

  public RegistrationController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/registration")
  public String registration(Model model) {
    model.addAttribute("userForm", new User());
    return "registration";
  }

  @PostMapping("/registration")
  public String addUser(
      @ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "registration";
    }
    if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
      bindingResult.addError(new FieldError("userForm", "password", "Passwords are not the same"));
      return "registration";
    }
    if (!userService.saveUser(userForm)) {
      bindingResult.addError(
          new FieldError("userForm", "username", "User with same username already exists"));
      return "registration";
    }
    return "redirect:/";
  }
}
