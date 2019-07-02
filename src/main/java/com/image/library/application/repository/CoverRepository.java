package com.image.library.application.repository;

import com.image.library.application.entity.Cover;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends MongoRepository<Cover, String> {

    Cover findByBookTitle(String title);
}
