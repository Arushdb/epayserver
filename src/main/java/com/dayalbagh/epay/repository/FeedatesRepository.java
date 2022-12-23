package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.dayalbagh.epay.model.Programfeedates;


public interface FeedatesRepository extends JpaRepository<Programfeedates, Integer> {

	 @Query(value = "select new Programfeedates( a.id,a.duedate ,a.cutoffdate,a.feeperiodstart,a.feeperiodto) from Programfeedates a "
	 		+ "where ?1 between duedate and cutoffdate and type =?2"  )
	List<Programfeedates> getepaymentstatus(Date date,Character type);
	
		
	 
	
}
