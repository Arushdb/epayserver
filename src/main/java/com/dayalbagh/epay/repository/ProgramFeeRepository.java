package com.dayalbagh.epay.repository;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.ProgramFee;


@Repository
public interface ProgramFeeRepository extends JpaRepository<ProgramFee, Integer> {
	
	List<ProgramFee>  findByProgramidAndSemestercodeAndBranchidAndSpecializationidAndMode
	( String programid,String sem,String brn,String spc,String mode);
	
	List<ProgramFee>  findByProgramidAndSemestercodeAndBranchidAndMode
	( String programid,String sem,String brn,String mode);
	
	List<ProgramFee>  findByProgramidAndSemestercodeAndMode
	( String programid,String sem,String mode);
	
	List<ProgramFee>  findByProgramidAndMode
	( String programid,String mode);

	
}
