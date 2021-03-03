package ru.com.petproject.todolist.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_task")
public class Task {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "topic")
    private String topic;
    
    @Column(name = "description")
    private String description;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", topic=" + topic + ", description=" + description + "]";
    }

}
