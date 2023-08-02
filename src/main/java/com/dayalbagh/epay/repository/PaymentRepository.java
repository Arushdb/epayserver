package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.Studentfeereceipt;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {


	
}
