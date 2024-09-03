package com.example.demo.User;

import lombok.AllArgsConstructor;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel createUser(UserDTO userDTO) throws ServerException {
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt(10));
        userDTO.setPassword(hashedPassword);
        UserModel newUserModel = new UserModel();
        newUserModel.convertDTOToUser(userDTO);
        return userRepository.save(newUserModel);
    }
}
