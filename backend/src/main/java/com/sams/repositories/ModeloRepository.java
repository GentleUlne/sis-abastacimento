package com.sams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sams.entities.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
	

}
