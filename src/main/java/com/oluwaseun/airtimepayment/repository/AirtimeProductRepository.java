package com.oluwaseun.airtimepayment.repository;

import com.oluwaseun.airtimepayment.domain.AirtimeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirtimeProductRepository extends JpaRepository<AirtimeProduct, Long> {
    Optional<AirtimeProduct> findByNetworkProvider(String networkProvider);
}
