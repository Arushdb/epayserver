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
				+ "pm.program_type as type,program_name as programname" + " from cms_live.student_program sp\r\n"
				+ " join cms_live.student_master sm on sm.enrollment_number = sp.enrollment_number \r\n"
				+ " join cms_live.program_master pm on pm.program_id = sp.program_id\r\n"
				+ " where roll_number =:rollno and program_status in (\"ACT\",\"PAS\",\"FAL\")", resultSetMapping = "getstudentdetailmap"),

		@NamedNativeQuery(name = "getstudentdetail", query = "select sp.roll_number as roll_number ,student_first_name as studentname,sp.program_id as programid ,"

				+ "	pm.program_type as type,program_name as programname,em.entity_type as mode,sp.branch_id as branchid ,sp.specialization_id as specializationid"
				+ " , sp.entity_id as entityid ,em.entity_name as entityName,stt.component_description as branchname from cms_live.student_program sp "
				+ " join cms_live.student_master sm on sm.enrollment_number = sp.enrollment_number "
				+ "	 join cms_live.program_master pm on pm.program_id = sp.program_id "
				+ "  join cms_live.entity_master em on em.entity_id = sp.entity_id "
				+ "  join cms_live.system_table_two stt on stt.component_code = sp.branch_id "
				+ "	 where roll_number =:rollno and program_status in ('ACT','PAS','FAL') and stt.group_code = 'BRNCOD' " 
				+ " order by sp.register_date desc "  , resultSetMapping = "getstudentdetailmap"),
		
		@NamedNativeQuery(name = "valid_roll_enrol_dob", query = "select sp.roll_number as roll_number ,student_first_name as studentname,sp.program_id as programid ,"

				+ "	pm.program_type as type,program_name as programname,em.entity_type as mode,sp.branch_id as branchid ,sp.specialization_id as specializationid"
				+ " , sp.entity_id as entityid ,'' as entityName ,'' as branchname from cms_live.student_program sp "
				+ " join cms_live.student_master sm on sm.enrollment_number = sp.enrollment_number "
				+ "	 join cms_live.program_master pm on pm.program_id = sp.program_id "
				+ "  join cms_live.entity_master em on em.entity_id = sp.entity_id "
				+ "	 where roll_number =:rollno and sm.date_of_birth=:dob and sp.enrollment_number=:enrolno and program_status in ('ACT','PAS','FAL')", resultSetMapping = "getstudentdetailmap"),

		@NamedNativeQuery(name = "valid_enrol_dob", query =" select sp.roll_number as roll_number ,student_first_name as studentname,sp.program_id as programid, "
								  
                + " pm.program_type as type,pm.program_name as programname,'mode' as mode,sp.branch_id as branchid ,"
                + " sp.specialization_id as specializationid ,sp.entity_id as entityid,'' as entityName ,'' as branchname from cms_live.student_program sp "
                + " join cms_live.student_master sm on sm.enrollment_number =:enrolno "
                + " join cms_live.program_master pm on pm.program_id = sp.program_id "
                + " and sm.date_of_birth=:dob "
                + " where registered_from_session= " 
                + " (select max(registered_from_session) from cms_live.student_program where enrollment_number=:enrolno) "
                + " and sp.enrollment_number=:enrolno "
                + " and program_status in ('ACT','PAS','FAL') " 
				
				, resultSetMapping = "getstudentdetailmap"),

		

		@NamedNativeQuery(name = "getpendingfee", query = "select srsh.roll_number as roll_number,pm.program_name as \r\n"
				+ "programname ,pch.semester_code as semestercode,srsh.session_start_date as semesterstartdate,srsh.session_end_date as semesterenddate,"
				+ "				pch.program_id as programid	,pch.branch_id as branchid,pch.specialization_id as specializationid,srsh.entity_id as entityid	from cms_live.student_registration_semester_header srsh\r\n"
				+ "                        join cms_live.program_course_header pch \r\n"
				+ "						on pch.program_course_key= srsh.program_course_key\r\n"
				+ "						left join student_fee_receipt sfr  on sfr.referenceno = srsh.roll_number \r\n"
				+ "						and sfr.program_id= pch.program_id \r\n"
				+ "                        and sfr.semester_code=pch.semester_code\r\n"
				+ "						and sfr.semester_start_date=srsh.session_start_date\r\n"
				+ "                       join cms_live.program_master pm on pm.program_id =pch.program_id\r\n"
				+ "						where srsh.roll_number =:rollno\r\n"
				+ "						and srsh.status in (\"PAS\",\"FAL\",\"REM\",\"REG\")\r\n"
				+ "						and srsh.session_start_date >=:epaystartdate and srsh.session_start_date "
				+ "						<> :semesterstartdate and sfr.referenceno is null\r\n"

				+ "	 and pch.semester_code not in "

				+ "	 (    select semester_code from fee_exemption fe "
				+ "	     where fe.roll_number= :rollno and fe.programid = pch.program_id"
				+ "	     and fe.semester_start_date = srsh.session_start_date"
				+ "	     and fe.semester_end_date = srsh.session_end_date and delflag = 0 )"
				+ "  order by pch.semester_code", resultSetMapping = "pendingfee"),

		@NamedNativeQuery(name = "getsemesterstatus", query = "select srsh.roll_number as roll_number,'' as programname"
				+ ",pch.semester_code as semestercode from cms_live.student_registration_semester_header srsh "
				+ "join cms_live.program_course_header pch on pch.program_course_key= "
				+ "srsh.program_course_key and pch.semester_code =:semester "
				+ "where roll_number=:rollno and srsh.status = 'PAS' "
				+ "and substr(srsh.program_course_key,1,7)=:pgmid ", resultSetMapping = "pendingfee"),

		@NamedNativeQuery(name = "getsemesters", query = "select '' as roll_number,'' as programname,"
				+ "semester_code  as semestercode, curdate()  as semesterstartdate,curdate() as semesterenddate,'' as programid, '' as branchid ,'' as specializationid "
				+ " ,'' as entityid from cms_live.program_term_details "
				+ " where semester_start_date = :ssd "
				+ " and semester_end_date = :sed and program_id =:pgm ", resultSetMapping = "pendingfee"),
		
		
		@NamedNativeQuery(name="getapplicantname",
		query = "select first_name as studentname "
				+ "from admissionform_live.entity_student where application_number=:appno"),
		
		@NamedNativeQuery(name="getmigrationdetail",
		query = "select max(sequence_no) from cms_live.migration_record where roll_number= :rollno"
				),
		
		@NamedNativeQuery(name="getpostfee",
		query = " select applicant_name as studentname,paa.application_number as applicationnumber ,cast(sum(post_fee)as decimal) as appfee from deipost.post_applicant_applied paa\r\n"
				+ "join deipost.post_applicant_master  pam on  paa.application_number = pam.application_number\r\n"
				+ "where paa.application_number=:appno group by paa.application_number" ,resultSetMapping ="applicantdetail"
				),

		@NamedNativeQuery(name = "getapplicantdetail", query = " select  first_name as studentname ,apr.application_number as applicationnumber ,"
				+ " cast(expected_fee as decimal)  as appfee,verification_status as message  from admissionform_live.student_application_status sas "
				+ " join admissionform_live.applicant_program_registration apr "
				+ " on apr.application_number= sas.application_number "
				+ " join admissionform_live.program_form pf on pf.program_id = apr.program_id "
				+ " join admissionform_live.entity_student es on es.application_number=apr.application_number "

				+ " where application_status='D'  "
				+ " and sas.application_number=:appno and available= 'Y'  and curdate()<= close_date  and sas.session_start_date =:sessionstartdate "
				+ " group by form_number ", resultSetMapping = "applicantdetail"),
		
		@NamedNativeQuery(name = "getapplicantforhostel", query = " select  first_name as studentname ,apr.application_number as applicationnumber ,"
				+ " cast(expected_fee as decimal)  as appfee  from admissionform_live.student_application_status sas "
				+ " join admissionform_live.applicant_program_registration apr "
				+ " on apr.application_number= sas.application_number "
				+ " join admissionform_live.program_form pf on pf.program_id = apr.program_id "
				+ " join admissionform_live.entity_student es on es.application_number=apr.application_number "
				+ " and sas.application_number=:appno "
				+ " where application_status='D' and curdate() <= date_add(close_date,interval 5 month)"
				+ "  group by form_number " ,resultSetMapping = "applicantdetail"),
		
		@NamedNativeQuery(name="updatestudentappfee",
		query = " 	update admissionform_live.student_application_status set  actual_deposited_fee=?,"
				+ "	fee_verification_time=now(),fee_verified_by=? ,verification_status='REC' ,"
				+ " ATRN=?,merchant_order_number=? "
				+ "      where application_number=?"
				+ "      and session_start_date =?"
				),
		@NamedNativeQuery(name = "isStudentRegistered",
		query = "select if(count(*)>0,true,false) from cms_live.student_registration_semester_header srsh "
		        + " join cms_live.program_course_header pch on pch.program_course_key = srsh.program_course_key "
				+ " where roll_number=:rollno  and pch.semester_code =:sem "
			
				+ " and session_start_date =:ssd and session_end_date =:sed "
						
				
				)
				
}
		
		
		
		
		)


@SqlResultSetMappings({
  
		@SqlResultSetMapping(name = "pendingfee", classes = {
				@ConstructorResult(targetClass = Student.class, columns = { @ColumnResult(name = "roll_number"),

						@ColumnResult(name = "programname"), @ColumnResult(name = "semestercode"),
						@ColumnResult(name = "semesterstartdate"), @ColumnResult(name = "semesterenddate"),
						@ColumnResult(name = "programid"),@ColumnResult(name = "branchid"),@ColumnResult(name = "specializationid"),
						@ColumnResult(name = "entityid")
				}

				) }),

		@SqlResultSetMapping(name = "getstudentdetailmap", classes = {
				@ConstructorResult(targetClass = Student.class, columns = { @ColumnResult(name = "roll_number"),
						@ColumnResult(name = "studentname"), @ColumnResult(name = "programid"),
						@ColumnResult(name = "type"), @ColumnResult(name = "programname"), @ColumnResult(name = "mode"),
						@ColumnResult(name = "branchid"), @ColumnResult(name = "specializationid"),
						@ColumnResult(name = "entityid"),@ColumnResult(name = "entityName"),
						@ColumnResult(name = "branchname")

				}

				) }),

		@SqlResultSetMapping(name = "applicantdetail", classes = {
				@ConstructorResult(targetClass = Student.class, columns = { @ColumnResult(name = "studentname"),

						@ColumnResult(name = "applicationnumber"), @ColumnResult(name = "appfee"),
						@ColumnResult(name = "message")
				}

				) }),
		@SqlResultSetMapping(name="getapplicantnameResult",columns = {@ColumnResult(name="studentname"),@ColumnResult(name="applicationnumber")})
		

}

)

//@SqlResultSetMapping(name="StudentVerificationmap2" ,entities = {@EntityResult(entityClass = Verification.class)} )

public class Querybase {
	@Id
	private long id;

}
