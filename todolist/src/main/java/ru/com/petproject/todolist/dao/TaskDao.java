package ru.com.petproject.todolist.dao;

import java.util.List;

import ru.com.petproject.todolist.entity.Task;

public interface TaskDao {
    
    public void add(Task task);
    
    public void update(Task task);
    
    public void removeTask(int id);
    
    public Task getTaskById(int id);
    
    public List<Task> getTasksList();

}
