package com.dayalbagh.epay.repository;



import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Admfeedates;

@Repository
public interface AdmfeedatesRepository extends JpaRepository<Admfeedates, Integer> {
	
	List<Admfeedates> findByApplicationnumberAndLastdateGreaterThanEqual(String appno,Date curdate);
	
	}
