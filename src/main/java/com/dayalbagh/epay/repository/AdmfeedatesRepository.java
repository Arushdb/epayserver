package com.dayalbagh.epay.repository;



import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dayalbagh.epay.model.Admfeedates;

public interface AdmfeedatesRepository extends JpaRepository<Admfeedates, Integer> {
	
	List<Admfeedates> findByApplicationnumberAndLastdateGreaterThanEqual(String appno,Date curdate);
	
	}
