package com.example.school_management_system.Service;

import com.example.school_management_system.Entity.User;
import com.example.school_management_system.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers()  {
        return userRepository.findAll();
    }

}
