package com.example.demo.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.User.UserModel;
import com.example.demo.User.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.rmi.ServerException;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private static final String SECRET_KEY = "V1sion";
 // 10 days in milliseconds
    private final UserRepository userRepository;


    public LoginResponseModel authenticate(String username, String password) throws ServerException {
        UserModel userModel = userRepository.findUserByUsername(username);
        if(userModel == null) throw new ServerException("Can't find this username");
        boolean isPassword = BCrypt.checkpw(password, userModel.getPassword());
        if(!isPassword) throw new ServerException("Can't find this password");

        Date expiredAt = new Date(System.currentTimeMillis() + 1000L);

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String jwtToken = JWT.create()
                .withClaim("userId", userModel.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(expiredAt)
                .withJWTId(UUID.randomUUID()
                        .toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);
        System.out.println("Token: " + jwtToken);
        return new LoginResponseModel(jwtToken, userModel.getId(), username, expiredAt);
    }




}
