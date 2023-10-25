package com.provavorp.repository;

import com.provavorp.domain.Banca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancaRepository extends JpaRepository<Banca, Long> {
}
