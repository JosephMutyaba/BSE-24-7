package com.todo.app.todo_list.repository;

import com.todo.app.todo_list.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {}

