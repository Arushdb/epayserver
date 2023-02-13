package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Defaulters;
import com.dayalbagh.epay.model.Programfeedates;


@Repository
public interface DefaulterRepository extends JpaRepository<Defaulters, Integer> {

	
	List<Defaulters> findAllByRollnumberAndDefaulter(String rollno,int status);
	
		
	 
	
}
