package com.todosimple.todosimple.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todosimple.todosimple.models.User;
import com.todosimple.todosimple.models.enums.ProfileEnum;
import com.todosimple.todosimple.repositories.UserRepository;
import com.todosimple.todosimple.services.exceptions.DataBindingViolationException;
import com.todosimple.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }
    
    @org.springframework.transaction.annotation.Transactional
    public User createUser(User obj) {
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);

        return obj;
    }

    @org.springframework.transaction.annotation.Transactional
    public User updateUser(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));

        return this.userRepository.save(newObj);
    }

    public void deleteUser(Long id) {
        findById(id);

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não foi possível deletar o usuário (Há entidades relacionadas)!");
        }
    }
}
