package com.provavorp.repository;

import com.provavorp.domain.Bonifico;
import com.provavorp.domain.Prelievo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrelievoRepository extends JpaRepository<Prelievo, Long> {
}
