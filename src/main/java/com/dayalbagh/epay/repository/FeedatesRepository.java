package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import com.dayalbagh.epay.model.Programfeedates;

public interface FeedatesRepository extends JpaRepository<Programfeedates, Integer> {

	 @Query(value = "select a from Programfeedates a where ?1 between duedate and cutoffdate"  )
	List<Programfeedates> getepaymentstatus(Date date);
	
	
}
