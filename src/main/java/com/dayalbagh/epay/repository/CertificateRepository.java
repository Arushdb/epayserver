package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Certificate;
import com.dayalbagh.epay.model.Studentfeereceipt;
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

	
	Certificate findByPayment_id(int paymentid);
	
	
	
}
