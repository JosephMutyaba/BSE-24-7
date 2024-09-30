package com.todo.app.todo_list.controller;

import com.todo.app.todo_list.exception.ResourceNotFoundException;
import com.todo.app.todo_list.repository.TodoRepository;
import com.todo.app.todo_list.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Todo items.
 * Provides endpoints to perform CRUD operations on Todo items.
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Retrieves all Todo items.
     *
     * @return a list of all Todo items
     */
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    /**
     * Creates a new Todo item.
     *
     * @param todo the Todo item to create
     * @return the created Todo item
     */
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    /**
     * Updates an existing Todo item.
     *
     * @param id          the ID of the Todo item to update
     * @param updatedTodo the updated Todo item data
     * @return the updated Todo item
     * @throws ResourceNotFoundException if the Todo item is not found
     */
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) throws ResourceNotFoundException {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(updatedTodo.getTitle());
                    todo.setDescription(updatedTodo.getDescription());
                    todo.setCompleted(updatedTodo.isCompleted());
                    return todoRepository.save(todo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
    }

    /**
     * Deletes a Todo item by ID.
     *
     * @param id the ID of the Todo item to delete
     */
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
