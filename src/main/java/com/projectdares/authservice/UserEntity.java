package com.projectdares.authservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@AllArgsConstructor
@Getter
public class UserEntity {

    @Id
    private String id;

    private String pictureUrl;

    private String name;
}
