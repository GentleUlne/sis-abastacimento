package com.sams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sams.entities.Tipo;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {
	

}
