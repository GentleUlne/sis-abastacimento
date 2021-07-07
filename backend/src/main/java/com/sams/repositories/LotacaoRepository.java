package com.sams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sams.entities.Lotacao;

@Repository
public interface LotacaoRepository extends JpaRepository<Lotacao, Long> {
	

}
