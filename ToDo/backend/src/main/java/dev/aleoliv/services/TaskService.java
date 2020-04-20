package dev.aleoliv.services;

import dev.aleoliv.models.Task;
import dev.aleoliv.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService( TaskRepository taskRepository ) {
    this.taskRepository = taskRepository;
  }

  public List<Task> findAll() {
    return taskRepository.findAll();
  }

  public Task save( Task task ) {
    return taskRepository.saveAndFlush( task );
  }

  public Task findOne( Long id ) {
    Optional<Task> optionalTask = taskRepository.findById( id );
    return optionalTask.isPresent() ? optionalTask.get() : optionalTask.orElseGet( null );
  }

  public Task update( Task task ) {
    return taskRepository.saveAndFlush( task );
  }

  public void delete( Long id ) {
    Optional<Task> optionalTask = taskRepository.findById( id );
    if (optionalTask.isPresent())
      taskRepository.delete( optionalTask.get() );
  }
}
