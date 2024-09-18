package com.example.demo.Category;

public interface ExampleResponse {
    String fetchAll = "{\"responseType\": \"SUCCESS\",\"data\": [{\"name\": \"Book\",\"image\": null,\"id\": \"66d15bccdbb9cf59f73eb2ff\",\"products\": [{\"name\": \"Yuru Camp vol 2\",\"price\": 11,\"description\": \"A second vol of yuru camp!\",\"quantity\": 400,\"created\": [2024,8,30,12,43,45,984000000],\"id\": \"66d15c15dbb9cf59f73eb300\",\"images\": []}]}],\"message\": \"Fetch list of categories successfully\",\"responseCode\": \"FETCH_SUCCESSFULLY\",\"timestamp\": [2024,9,18,10,41,41,721313900]}";
    String fetchCategoryById = "{\"responseType\": \"SUCCESS\",\"data\": {\"name\": \"Clothes\",\"image\": null,\"id\": \"66ea4f298da7e4359c97218b\",\"products\": []},\"message\": \"Find category with id 66ea4f298da7e4359c97218b successfully\",\"responseCode\": \"FETCH_SUCCESSFULLY\",\"timestamp\": [2024,9,18,10,57,48,780154300]}";
    String createCategory = "{\"responseType\": \"SUCCESS\",\"data\": {\"name\": \"Clothes\",\"image\": null,\"id\": \"66ea4f298da7e4359c97218b\",\"products\": []},\"message\": \"Create category Clothes successfully\",\"responseCode\": \"CREATE_SUCCESSFULLY\",\"timestamp\": [2024,9,18,10,55,21,316350900]}";
    String editCategory = "{\"responseType\": \"SUCCESS\",\"data\": {\"name\": \"Clothess\",\"image\": null,\"id\": \"66ea4f298da7e4359c97218b\",\"products\": []},\"message\": \"Edit category with id 66ea4f298da7e4359c97218b successfully\",\"responseCode\": \"EDIT_SUCCESSFULLY\",\"timestamp\": [2024,9,18,11,9,38,227467200]}";
    String deleteCategory = "{\"responseType\": \"SUCCESS\",\"message\": \"Delete category with id 66ea4f298da7e4359c97218b successfully\",\"responseCode\": \"DELETE_SUCCESSFULLY\",\"timestamp\": [2024,9,18,11,10,38,264959500]}";
}
