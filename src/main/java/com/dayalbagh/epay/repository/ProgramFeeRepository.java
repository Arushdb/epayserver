package com.dayalbagh.epay.repository;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.ProgramFee;


@Repository
public interface ProgramFeeRepository extends JpaRepository<ProgramFee, Integer> {
	
	List<ProgramFee>  findByProgramidAndSemestercodeAndBranchidAndSpecializationidAndModeAndActive
	( String programid,String sem,String brn,String spc,String mode,int act);
	
	List<ProgramFee>  findByProgramidAndSemestercodeAndBranchidAndModeAndActive
	( String programid,String sem,String brn,String mode,int act);
	
	List<ProgramFee>  findByProgramidAndSemestercodeAndModeAndActive
	( String programid,String sem,String mode,int act);
	
	List<ProgramFee>  findByProgramidAndModeAndActive
	( String programid,String mode,int act);

	
}
