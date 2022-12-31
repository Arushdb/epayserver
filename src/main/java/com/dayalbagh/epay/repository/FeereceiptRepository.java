package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayalbagh.epay.model.Studentfeereceipt;

public interface FeereceiptRepository extends JpaRepository<Studentfeereceipt, Integer> {

	List <Studentfeereceipt>findAllByRollnumberAndSemesterAndProgramidAndSemesterstartdateAndSemesterenddate
	(String roll_number,String sem,String pgm,Date ssd,Date sed );
	
}
