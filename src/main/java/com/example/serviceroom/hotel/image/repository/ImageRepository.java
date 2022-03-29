package com.example.serviceroom.hotel.image.repository;

import com.example.serviceroom.hotel.image.ImageBO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ImageRepository extends JpaRepository<ImageBO, Integer> {
    void deleteByGuid(String guid);
    Optional<ImageBO> findByGuidHotel(String guid);

}