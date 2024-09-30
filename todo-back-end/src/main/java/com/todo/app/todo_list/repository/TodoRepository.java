package com.todo.app.todo_list.repository;

import com.todo.app.todo_list.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Todo} entities.
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Custom query methods can be defined here, if needed in the future.
}
