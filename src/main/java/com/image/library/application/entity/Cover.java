package com.image.library.application.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "covers")
public class Cover {
    @Id
    private String id;
    private String link;
    private String bookTitle;
}
