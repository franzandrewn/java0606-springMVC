package com.andrewn.springMVC.controller;

import com.andrewn.springMVC.exceptions.TodoNotFoundException;
import com.andrewn.springMVC.model.Todo;
import com.andrewn.springMVC.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoEditController {

  private final TodoRepository todoRepository;

  public TodoEditController(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @GetMapping("/todos")
  public String allTodos(Model model) {
    model.addAttribute("todos", todoRepository.findAll());
    return "todos";
  }

  @GetMapping("/todos/edit/{id}")
  public String todoForm(@PathVariable Long id, Model model) {
    Todo foundTodo = todoRepository.findById(id).orElseGet(() -> new Todo());
    model.addAttribute("todo", foundTodo);
    return "todo-edit";
  }

  @GetMapping("/todos/{id}")
  public String todoInfo(@PathVariable Long id, Model model) {
    Todo foundTodo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
    model.addAttribute("todo", foundTodo);
    return "todo-info";
  }

  @PostMapping("/todos/{id}")
  public String todoSubmit(@PathVariable Long id, @ModelAttribute Todo changedTodo, Model model) {
    Todo foundTodo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
    foundTodo.setDesc(changedTodo.getDesc());
    foundTodo.setComplete(changedTodo.isComplete());
    model.addAttribute("todo", todoRepository.save(foundTodo));
    return "todo-info";
  }
}
