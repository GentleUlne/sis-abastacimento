package com.sams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sams.entities.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
	

}
