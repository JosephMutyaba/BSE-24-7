package com.todo.app.todo_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Todo List Application.
 */
@SpringBootApplication
public class TodoListApplication {

	/**
	 * Main method that launches the application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}
}
