package com.example.demo.User;


import com.example.demo.Base.QueryRepository;
import com.example.demo.Base.UtilsRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String>, QueryRepository<UserModel>, UtilsRepository<UserModel> {
    @Query("{'username': ?0}")
    UserModel findUserByUsername(String username);

}
