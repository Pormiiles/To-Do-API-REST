package com.todosimple.todosimple.models;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    public interface CreateUser {
    }
    public interface UpdateUser {
    }

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull(groups = CreateUser.class)
    @NotEmpty(groups = CreateUser.class)
    @Column(name = "username", unique = true)
    private String username;

    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<Task>();
}