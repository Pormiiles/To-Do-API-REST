package com.todosimple.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todosimple.todosimple.models.User;
import com.todosimple.todosimple.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }
    
    @org.springframework.transaction.annotation.Transactional
    public User createUser(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);

        return obj;
    }

    @org.springframework.transaction.annotation.Transactional
    public User updateUser(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());

        return this.userRepository.save(newObj);
    }

    public void deleteUser(Long id) {
        findById(id);

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível deletar o usuário (Há entidades relacionadas)!");
        }
    }
}
