package com.dayalbagh.epay.repository;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.Studentfeereceipt;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

     Payment findByMerchantorderno(String str);
     Payment findByATRN(String str);
     Payment findByMerchantordernoAndAmount(String morderno,BigDecimal amount);
     Payment findByATRNAndAmount(String atrn,BigDecimal amount);
     
	
}
