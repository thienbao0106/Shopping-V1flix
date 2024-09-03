package com.example.demo.User;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@Document(collection = "users")
public class UserModel extends BaseUser {
    @Id
    private String id;


    public UserModel() {
        super();
    }
    public void convertDTOToUser(UserDTO userDTO) {
        this.setAddress(userDTO.getAddress());
        this.setAvatar(userDTO.getAvatar());
        this.setEmail(userDTO.getEmail());
        this.setPassword(userDTO.getPassword());
        this.setUsername(userDTO.getUsername());
        this.setFullName(userDTO.getFullName());
    }
}
