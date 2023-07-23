package com.andrewn.springMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

  @RequestMapping(method = RequestMethod.GET, path = "/")
  String hello() {
    return "hello";
  }

  @GetMapping("/greeting")
  String greeting(
      @RequestParam(name = "firstname", required = false, defaultValue = "World") String name,
      @RequestHeader(name = "Accept-Language") String language,
      Model model) {
    model.addAttribute("htmlname", name);
    return "greeting";
  }

  // создать метод, который обрабатывает адрес "/concat"
  // при этом в адресной строке можно передать два параметра с именами first и second (строки)
  // в ответ отправить html, где внутри <body> записаны обе строки поочереди
  // GET /concat?first=asd&second=qwe -> <body>asdqwe</body>
  @GetMapping("/concat")
  String concat(
      @RequestParam(name = "first", required = false, defaultValue = "") String first,
      @RequestParam(name = "second", required = false, defaultValue = "") String second,
      Model model) {
    model.addAttribute("first", first);
    model.addAttribute("second", second);
    return "concat";
  }
}
