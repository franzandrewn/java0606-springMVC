package com.andrewn.springMVC.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="todo")
public class Todo {
    @Id
    @Column(name="todo_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String desc;
    private boolean complete;
    private Date created;

    public Todo() {
    }

    public Todo(long id, String desc, boolean complete, Date created) {
        this.id = id;
        this.desc = desc;
        this.complete = complete;
        this.created = created;
    }

    public Todo(String desc, boolean complete, Date created) {
        this.desc = desc;
        this.complete = complete;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", complete=" + complete +
                ", created=" + created +
                '}';
    }
}
