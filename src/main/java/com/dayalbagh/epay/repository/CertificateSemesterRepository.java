package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Certificate;
import com.dayalbagh.epay.model.Certificatesemester;
import com.dayalbagh.epay.model.Studentfeereceipt;
@Repository
public interface CertificateSemesterRepository extends JpaRepository<Certificatesemester, Integer> {

	
	
	
}
