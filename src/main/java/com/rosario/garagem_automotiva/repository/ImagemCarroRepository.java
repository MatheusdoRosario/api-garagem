package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.ImagemCarro;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ImagemCarroRepository extends MongoRepository<ImagemCarro, UUID> {
    List<ImagemCarro> findByCarroId(UUID carroId);
}
