package com.example.demo.User;

public interface ExampleResponse {
    String fetchAll = "{\"responseType\": \"SUCCESS\",\"data\": [{\"username\": \"bao123\",\"fullName\": \"Hello Bao\",\"email\": \"bao@gmail.com\",\"address\": \"123\",\"avatar\": \"\",\"id\": \"66d7076e065bfe3bdbe4eb1e\"}],\"message\": \"Find list successfully\",\"responseCode\": \"FETCH_SUCCESSFULLY\",\"timestamp\": [2024,9,18,11,50,3,221015600]}";
    String createUser = "{\"responseType\": \"SUCCESS\",\"data\": {\"username\": \"bao1234\",\"fullName\": \"Hello Bao\",\"email\": \"bao2@gmail.com\",\"address\": \"123\",\"avatar\": \"\",\"id\": \"66ea5e75b890e970a86ecfbb\"},\"message\": \"Create user bao1234 successfully\",\"responseCode\": \"CREATE_SUCCESSFULLY\",\"timestamp\": [2024,9,18,12,0,37,190464100]}";
    String findUserById = "{\"responseType\": \"SUCCESS\",\"data\": {\"username\": \"bao123\",\"fullName\": \"Hello Bao\",\"email\": \"bao@gmail.com\",\"address\": \"123\",\"avatar\": \"\",\"id\": \"66d7076e065bfe3bdbe4eb1e\"},\"message\": \"Find user66d7076e065bfe3bdbe4eb1esuccessfully\",\"responseCode\": \"FETCH_SUCCESSFULLY\",\"timestamp\": [2024,9,20,9,38,30,600004600]}";
    String editUser = "{\"responseType\": \"SUCCESS\",\"data\": {\"username\": \"bao123456\",\"fullName\": \"Hello Bao 1\",\"email\": \"le@gmail.com\",\"address\": \"123\",\"avatar\": \"\",\"id\": \"66ecd8453189233b490526a8\"},\"message\": \"Edit user 66ecd8453189233b490526a8 successfully\",\"responseCode\": \"EDIT_SUCCESSFULLY\",\"timestamp\": [2024,9,20,9,18,20,506093900]}";
    String deleteUser = "{\"responseType\": \"SUCCESS\",\"message\": \"Delete user 66ecd8453189233b490526a8 successfully\",\"responseCode\": \"DELETE_SUCCESSFULLY\",\"timestamp\": [2024,9,20,9,19,55,503453200]}";
}
