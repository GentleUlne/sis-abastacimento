package com.sams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sams.entities.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	

}
