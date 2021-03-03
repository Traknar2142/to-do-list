package ru.com.petproject.todolist.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.com.petproject.todolist.entity.Task;

@Repository
public class TaskDaoImpl implements TaskDao{
    private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);
    
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void add(Task task) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(task);
        logger.info("Task saved. Details: " + task);
        
    }

    public void update(Task task) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(task);
        logger.info("Task update. Details: " + task);
    }

    public void removeTask(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Task task = (Task) session.load(Task.class, new Integer(id));
        
        if(task != null) {
            session.delete(task);
        }
        logger.info("Task deleted. Details: " + task);        
    }

    public Task getTaskById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Task task = (Task) session.load(Task.class, new Integer(id));
        logger.info("Task loaded. Details: " + task);
        
        return task;
    }
    
    @SuppressWarnings("unchecked")
    public List<Task> getTasksList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Task> taskList = session.createQuery("from t_task").list();
        
        for(Task task : taskList) {
            logger.info("Task list: " + task);
        }
        
        return taskList;
    }
}
