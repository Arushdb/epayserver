package com.dayalbagh.epay.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.dayalbagh.epay.model.PendingPayment;

@Repository
public interface PendingPaymentRepository extends JpaRepository<PendingPayment, Integer> {

    
}
