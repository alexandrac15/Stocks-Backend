package com.example.stocks.repositories;

import com.example.stocks.domain.MLModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MLModelRepository extends JpaRepository<MLModel,Integer> {
//        MLModel findMlModelByIsDefault(Boolean isDefault);
        }
