package com.dayalbagh.epay.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

@Entity

@NamedQueries({

		@NamedQuery(name = "getParentMenuItems",
				// query="SELECT parent.id as id, parent.name as name, parent.lft as lft , "
				// + " parent.rgt as rgt , parent.route as route FROM Menu AS node, Menu AS
				// parent" +
				query = "SELECT  parent " + "  FROM Menu AS node, Menu AS parent" +

						" WHERE node.lft BETWEEN parent.lft AND parent.rgt"
						+ " AND node.id in (select menu_id FROM Role_Menu  WHERE role_id = :role_id )"
						+ " and parent.id > 1 and parent.lft+1 <>  parent.rgt"
						+ " group by parent.id order by parent.lft"),

		@NamedQuery(name = "getChildrenMenuItems", query = "Select  node " + " FROM Menu AS node,Menu AS parent"
				+ " WHERE node.lft BETWEEN parent.lft AND parent.rgt" + " and node.rgt <parent.rgt"
				+ " AND node.id in (select menu_id FROM Role_Menu  WHERE role_id = :role_id )"
				+ " AND parent.id = :MenuId " + " ORDER BY node.lft"),
		@NamedQuery(name = "getdefaultrole", query = " select u from UserRoles u where user_id=:userid and default_role=:defaultrole"),

})

@NamedNativeQueries({

		@NamedNativeQuery(name = "getstudentdetail_old", query = "select sp.roll_number as roll_number ,student_first_name as studentname,sp.program_id as programid ,"
				+ "pm.program_type as type,program_name as programname" + " from cms21112022.student_program sp\r\n"
				+ " join cms21112022.student_master sm on sm.enrollment_number = sp.enrollment_number \r\n"
				+ " join cms21112022.program_master pm on pm.program_id = sp.program_id\r\n"
				+ " where roll_number =:rollno and program_status in (\"ACT\",\"PAS\",\"FAL\")", resultSetMapping = "getstudentdetailmap"),

		@NamedNativeQuery(name = "getstudentdetail", query = "select sp.roll_number as roll_number ,student_first_name as studentname,sp.program_id as programid ,"

				+ "	pm.program_type as type,program_name as programname,em.entity_type as mode,sp.branch_id as branchid ,sp.specialization_id as specializationid"
				+ " from cms21112022.student_program sp "
				+ " join cms21112022.student_master sm on sm.enrollment_number = sp.enrollment_number "
				+ "	 join cms21112022.program_master pm on pm.program_id = sp.program_id "
				+ "  join cms21112022.entity_master em on em.entity_id = sp.entity_id "
				+ "	 where roll_number =:rollno and program_status in ('ACT','PAS','FAL')", resultSetMapping = "getstudentdetailmap"),

		@NamedNativeQuery(name = "getpendingfee", query = "select srsh.roll_number as roll_number,pm.program_name as \r\n"
				+ "programname ,pch.semester_code as semestercode"
				+ "						from cms21112022.student_registration_semester_header srsh\r\n"
				+ "                        join cms21112022.program_course_header pch \r\n"
				+ "						on pch.program_course_key= srsh.program_course_key\r\n"
				+ "						left join student_fee_receipt sfr  on sfr.roll_number = srsh.roll_number \r\n"
				+ "						and sfr.program_id= pch.program_id \r\n"
				+ "                        and sfr.semester_code=pch.semester_code\r\n"
				+ "						and sfr.semester_start_date=srsh.session_start_date\r\n"
				+ "                       join cms21112022.program_master pm on pm.program_id =pch.program_id\r\n"
				+ "						where srsh.roll_number =:rollno\r\n"
				+ "						and srsh.status in (\"PAS\",\"FAL\",\"REM\",\"REG\")\r\n"
				+ "						and srsh.session_start_date >=:epaystartdate and srsh.session_start_date "
				+ "						<> :semesterstartdate and sfr.roll_number is null\r\n"

				+ "	 and pch.semester_code not in "

				+ "	 (    select semester_code from fee_exemption fe "
				+ "	     where fe.roll_number= :rollno and fe.programid = pch.program_id"
				+ "	     and fe.semester_start_date = srsh.session_start_date"
				+ "	     and fe.semester_end_date = srsh.session_end_date and delflag = 0 )"
				+ "  order by pch.semester_code", resultSetMapping = "pendingfee"),

		@NamedNativeQuery(name = "getsemesterstatus", query = "select srsh.roll_number as roll_number,'' as programname"
				+ ",pch.semester_code as semestercode from cms21112022.student_registration_semester_header srsh "
				+ "join cms21112022.program_course_header pch on pch.program_course_key= "
				+ "srsh.program_course_key and pch.semester_code =:semester "
				+ "where roll_number=:rollno and srsh.status = 'PAS' "
				+ "and substr(srsh.program_course_key,1,7)=:pgmid ", resultSetMapping = "pendingfee"),

		@NamedNativeQuery(name = "getsemesters", query = "select '' as roll_number,'' as programname,"
				+ "semester_code  as semestercode from cms21112022.program_term_details "
				+ " where semester_start_date = :ssd "
				+ " and semester_end_date = :sed and program_id =:pgm ", resultSetMapping = "pendingfee") })

@SqlResultSetMappings({

		@SqlResultSetMapping(name = "pendingfee", classes = {
				@ConstructorResult(targetClass = Student.class, columns = { @ColumnResult(name = "roll_number"),

						@ColumnResult(name = "programname"), @ColumnResult(name = "semestercode")

				}

				) }),

		@SqlResultSetMapping(name = "getstudentdetailmap", classes = {
				@ConstructorResult(targetClass = Student.class, columns = { @ColumnResult(name = "roll_number"),
						@ColumnResult(name = "studentname"), @ColumnResult(name = "programid"),
						@ColumnResult(name = "type"), @ColumnResult(name = "programname"), @ColumnResult(name = "mode"),
						@ColumnResult(name = "branchid"), @ColumnResult(name = "specializationid")

				}

				) }),

}

)

//@SqlResultSetMapping(name="StudentVerificationmap2" ,entities = {@EntityResult(entityClass = Verification.class)} )

public class Querybase {
	@Id
	private long id;

}
