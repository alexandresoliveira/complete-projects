package dev.aleoliv.controllers;

import dev.aleoliv.models.Task;
import dev.aleoliv.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("tasks")
@RequestMapping("tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController( TaskService taskService ) {
    this.taskService = taskService;
  }

  @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(code = HttpStatus.OK)
  @ResponseBody
  public List<Task> index() {
    return taskService.findAll();
  }

  @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(code = HttpStatus.OK)
  @ResponseBody
  public Task show(@PathVariable("id") Long id) {
    return taskService.findOne(id);
  }

  @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(code = HttpStatus.CREATED)
  @ResponseBody
  public Task store( @Valid @RequestBody Task task ) {
    return taskService.save(task);
  }

  @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseStatus(code = HttpStatus.OK)
  @ResponseBody
  public Task update(@Valid @RequestBody Task task) {
    return taskService.update(task);
  }

  @DeleteMapping(value = "{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) {
    taskService.delete(id);
  }
}
