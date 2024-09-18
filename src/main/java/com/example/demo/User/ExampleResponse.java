package com.example.demo.User;

public interface ExampleResponse {
    String fetchAll = "{\"responseType\": \"SUCCESS\",\"data\": [{\"username\": \"bao123\",\"fullName\": \"Hello Bao\",\"email\": \"bao@gmail.com\",\"address\": \"123\",\"avatar\": \"\",\"id\": \"66d7076e065bfe3bdbe4eb1e\"}],\"message\": \"Find list successfully\",\"responseCode\": \"FETCH_SUCCESSFULLY\",\"timestamp\": [2024,9,18,11,50,3,221015600]}";
    String createUser = "{\"responseType\": \"SUCCESS\",\"data\": {\"username\": \"bao1234\",\"fullName\": \"Hello Bao\",\"email\": \"bao2@gmail.com\",\"address\": \"123\",\"avatar\": \"\",\"id\": \"66ea5e75b890e970a86ecfbb\"},\"message\": \"Create user bao1234 successfully\",\"responseCode\": \"CREATE_SUCCESSFULLY\",\"timestamp\": [2024,9,18,12,0,37,190464100]}";
    String findUserById = "";
    String editUser = "";
    String deleteUser = "";
}
