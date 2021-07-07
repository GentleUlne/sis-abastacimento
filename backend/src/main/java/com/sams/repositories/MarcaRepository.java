package com.sams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sams.entities.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
	

}
