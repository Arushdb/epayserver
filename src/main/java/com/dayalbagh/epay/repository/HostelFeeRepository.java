package com.dayalbagh.epay.repository;



import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.HostelFee;

@Repository
public interface HostelFeeRepository extends JpaRepository<HostelFee, Integer> {
	
	List<HostelFee> findByYearmonthAndHostelid(String yearmon,int hostelid);
	
	}
