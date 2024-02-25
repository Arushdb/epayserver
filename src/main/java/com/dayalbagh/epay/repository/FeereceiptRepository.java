package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.Studentfeereceipt;
@Repository
public interface FeereceiptRepository extends JpaRepository<Studentfeereceipt, Integer> {

	List <Studentfeereceipt>findAllByRollnumberAndSemesterAndProgramidAndSemesterstartdateAndSemesterenddate
	(String roll_number,String sem,String pgm,Date ssd,Date sed );
	
	List <Studentfeereceipt>findAllByRollnumberAndSemesterAndProgramidAndSemesterstartdateAndSemesterenddateAndReftype
	(String roll_number,String sem,String pgm,Date ssd,Date sed ,String reftype);
	
	Studentfeereceipt findByPayment_id(int id);
	
	List <Studentfeereceipt> findAllByRollnumberAndInserttimeLessThanEqualAndInserttimeGreaterThanEqualOrderByInserttimeDesc  
	(String roll_number,Date enddate,Date startdate);
	
}
