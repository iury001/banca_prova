package com.provavorp.repository;

import com.provavorp.domain.Bonifico;
import com.provavorp.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonificoRepository extends JpaRepository<Bonifico, Long> {
}
