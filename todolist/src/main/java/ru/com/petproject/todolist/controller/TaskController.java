package ru.com.petproject.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.com.petproject.todolist.entity.Task;
import ru.com.petproject.todolist.service.TaskService;

@Controller
public class TaskController {
    private TaskService taskService;

    @Autowired(required = true)
    @Qualifier(value = "taskService")
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @RequestMapping(value = "task", method = RequestMethod.GET)
    public String listTasks(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("listTasks", this.taskService.getTasksList());
        
        return "tasks";
    }
    
    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task) {
        if(task.getId() ==0) {
            this.taskService.add(task);
        }else {
            this.taskService.update(task);
        }
        
        return "redirect:/tasks";
    }
    
    @RequestMapping("/remove/{id}")
    public String removeTask(@PathVariable("id") int id) {
        this.taskService.removeTask(id);
        
        return "redirect:/tasks";
    }
    
    @RequestMapping("edit/{id}")
    public String editTask(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", this.taskService.getTaskById(id));
        model.addAttribute("listTasks", this.taskService.getTasksList());
        
        return "tasks";
    }
    
    @RequestMapping("taskdata/{id}")
    public String taskData(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", this.taskService.getTaskById(id));
        
        return "taskdata";
    }

}
