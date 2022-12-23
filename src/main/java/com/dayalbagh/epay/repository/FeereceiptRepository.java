package com.dayalbagh.epay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayalbagh.epay.model.Studentfeereceipt;

public interface FeereceiptRepository extends JpaRepository<Studentfeereceipt, Integer> {

	List <Studentfeereceipt>findAllByRollnumberAndSemesterAndProgramidAndId(String roll_number,String sem,String pgm,Integer id );
	List <Studentfeereceipt>findAllByRollnumberAndSemesterAndProgramidAndIdNot(String roll_number,String sem,String pgm,Integer id );
}
