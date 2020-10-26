package com.walrus.assignment.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(unique = true)
    private String isbn;

    public User() {
    }

    public User(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }
}
