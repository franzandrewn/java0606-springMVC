package com.andrewn.springMVC.controller;

import com.andrewn.springMVC.exceptions.TodoNotFoundException;
import com.andrewn.springMVC.model.Todo;
import com.andrewn.springMVC.repository.TodoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TodoController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

  private final TodoRepository todoRepository;

  public TodoController(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @GetMapping("/todos")
  List<Todo> allTodo() {
    //        LOGGER.info("Получил GET запрос по адресу /api/todos");
    return todoRepository.findAll();
  }

  @GetMapping("/todos/{id}")
  Todo getTodo(@PathVariable Long id) {
    return todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
  }

  // теоретически работает, но предлагается так не писать, чтобы не "замусоривать" контроллер
  //    @ExceptionHandler(TodoNotFoundException.class)
  //    @ResponseBody
  //    @ResponseStatus(HttpStatus.NOT_FOUND)
  //    String todoNotFoundHandler(TodoNotFoundException ex) {
  //        return ex.getMessage();
  //    }

  @PostMapping("/todos")
  Todo createTodo(@RequestBody Todo newTodo) {
    newTodo.setCreated(new Date());
    return todoRepository.save(newTodo);
  }

  @DeleteMapping("/todos/{id}")
  void deleteTodo(@PathVariable Long id) {
    todoRepository.deleteById(id);
  }

  @PutMapping("/todos/{id}")
  Todo changeTodo(@PathVariable Long id, @RequestBody Todo changedTodo) {
    Optional<Todo> foundTodo = todoRepository.findById(id);
    if (foundTodo.isPresent()) {
      Todo todo = foundTodo.get();
      todo.setDesc(changedTodo.getDesc());
      todo.setComplete(changedTodo.isComplete());
      if (changedTodo.getCreated() != null) {
        todo.setCreated(changedTodo.getCreated());
      }
      return todoRepository.save(todo);
    } else {
      if (changedTodo.getCreated() == null) {
        changedTodo.setCreated(new Date());
      }
      return todoRepository.save(changedTodo);
    }
  }

  @GetMapping("/todos/by-desc/{desc}")
  List<Todo> findByDesc(@PathVariable String desc) {
    return todoRepository.findByDesc(desc);
  }
}
