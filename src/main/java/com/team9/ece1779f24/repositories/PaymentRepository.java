package com.team9.ece1779f24.repositories;

import com.team9.ece1779f24.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
