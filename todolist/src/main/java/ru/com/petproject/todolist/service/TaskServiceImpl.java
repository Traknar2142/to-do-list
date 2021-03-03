package ru.com.petproject.todolist.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ru.com.petproject.todolist.dao.TaskDao;
import ru.com.petproject.todolist.entity.Task;

@Service
public class TaskServiceImpl implements TaskService{
    private TaskDao taskDao;

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Transactional
    public void add(Task task) {
        this.taskDao.add(task);
        
    }

    @Transactional
    public void update(Task task) {
        this.taskDao.update(task);
        
    }

    @Transactional
    public void removeTask(int id) {
        this.taskDao.removeTask(id);
        
    }

    @Transactional
    public Task getTaskById(int id) {
        return taskDao.getTaskById(id);
    }

    @Transactional
    public List<Task> getTasksList() {
        return taskDao.getTasksList();
    }

}
