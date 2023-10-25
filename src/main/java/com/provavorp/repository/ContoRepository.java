package com.provavorp.repository;

import com.provavorp.domain.Conto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContoRepository extends JpaRepository<Conto, Long> {
    void deleteByIban(String iban);

    Conto findByIban(String iban);
}
