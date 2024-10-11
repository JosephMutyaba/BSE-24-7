package com.todo.app.todo_list.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.todo.app.todo_list.models.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

//    @Test
    public void testSaveAndFindAll() {
        // Create a new Todo item
        Todo todo = new Todo();
        todo.setTitle("Test Task");
        todo.setCompleted(false);

        // Save the Todo item to the repository
        todoRepository.save(todo);

        // Retrieve all Todo items from the repository
        List<Todo> todos = todoRepository.findAll();

        // Assert that the Todo item was saved and retrieved correctly
        assertThat(todos).isNotEmpty();
        assertThat(todos.get(0).getTitle()).isEqualTo("Test Task");
    }
}
