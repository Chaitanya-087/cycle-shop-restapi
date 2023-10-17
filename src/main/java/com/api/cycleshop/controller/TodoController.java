package com.api.cycleshop.controller;

import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    
    private static List<HashMap<String,String>> todos = new ArrayList<HashMap<String,String>> ();

    static {
        HashMap<String,String> todo = new HashMap<String,String>();
        todo.put("id", "1");
        todo.put("title", "Todo 1");
        todo.put("description", "Description 1");
        todo.put("done", "false");
        todos.add(todo);
    }

    @GetMapping
    public List<HashMap<String,String>> getAllTodos() {
        return todos;
    }

    @PostMapping
    public HashMap<String,String> addTodo() {
        HashMap<String,String> todo = new HashMap<String,String>();
        todo.put("id", "2");
        todo.put("title", "Todo 2");
        todo.put("description", "Description 2");
        todo.put("done", "false");
        todos.add(todo);
        return todo;
    }

    @DeleteMapping("/{id}")
    public HashMap<String, String> deleteTodo(@PathVariable("id") String id) {
        HashMap<String,String> todo = todos.stream().filter(t -> t.get("id").equals(id)).findFirst().orElseThrow();

        todos.stream().filter(t -> t.get("id").equals(id)).findFirst().orElseThrow();
        todos.remove(todo);
        return todo;
    }
    
}
