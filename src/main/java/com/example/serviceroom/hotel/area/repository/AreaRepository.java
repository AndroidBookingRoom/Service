package com.example.serviceroom.hotel.area.repository;

import com.example.serviceroom.hotel.area.bo.AreaBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<AreaBO, Integer> {
}
