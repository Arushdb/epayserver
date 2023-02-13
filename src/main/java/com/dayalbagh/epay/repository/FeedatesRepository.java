package com.dayalbagh.epay.repository;


import java.sql.Date;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Programfeedates;

@Repository
public interface FeedatesRepository extends JpaRepository<Programfeedates, Integer> {

	 @Query(value = "select new Programfeedates( a.duedate ,a.lastdate,a.cutoffdate,"
	 		+ "a.semestertype,a.semesterstartdate,semesterenddate,seqno,status) from Programfeedates a "
	 		+ "where ?1 between str_to_date((concat(YEAR(?1),'-',duedate)),'%Y-%m-%d')"
	 		+ " and str_to_date((concat(YEAR(?1),'-',cutoffdate)),'%Y-%m-%d') and semestertype =?2 and status =1"  )
	List<Programfeedates> getepaymentstatus(Date date,Character type);
	
	 
	 @Query(value = "select new Programfeedates( a.duedate ,a.lastdate,a.cutoffdate,"
		 		+ "a.semestertype,a.semesterstartdate,semesterenddate,seqno,status) from Programfeedates a "
		 		+ "where ?1 > str_to_date((concat(YEAR(?1),'-',lastdate)),'%Y-%m-%d')"
		 		+ " and ?1 <= str_to_date((concat(YEAR(?1),'-',cutoffdate)),'%Y-%m-%d') and semestertype =?2 and status=1"  )
	 List<Programfeedates> getpaymentdelaytatus(Date date,Character type); 
		
	 	
}
