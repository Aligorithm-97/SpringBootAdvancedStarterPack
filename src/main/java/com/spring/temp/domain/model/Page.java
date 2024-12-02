package com.spring.temp.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Page {

    // If you want to authenticate users also by page you can use it otherwise you can delete and use just role-based authentication.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pageName;
    private String pagePath;
    private String pageLevel;


    @ManyToMany(mappedBy = "pages")
    @JsonIgnore
    private List<User> users;
}
