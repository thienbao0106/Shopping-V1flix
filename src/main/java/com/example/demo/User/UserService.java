package com.example.demo.User;
import lombok.AllArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

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

    public UserModel editUser(UserDTO edittedUserDTO, String id) throws ServerException {
        if (userRepository.findById(id).isEmpty()) throw new ServerException("Can't find the id");
        UserModel userModel = userRepository.findById(id).get();
        userModel.convertDTOToUser(edittedUserDTO);
        Class<UserModel> userModelClass = UserModel.class;
        return userRepository.editCurrentObject(id, userRepository.convertItemToMap(userModel, userModelClass.getName()), userModelClass);
    }

    public void deleteUser(String id) throws ServerException {
        Optional<UserModel> user = userRepository.findById(id);
        user.ifPresentOrElse((result) -> {
            userRepository.deleteById(id);
        }, () ->{
            try {
                throw new ServerException("Can't find the user of id: " + id);
            } catch (ServerException ignored) {

            }
        });
    }

    public UserModel getUserById(String id) throws ServerException {
        Optional<UserModel> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new ServerException("Can't find the user of id: " + id)).getBody();
    }

}
