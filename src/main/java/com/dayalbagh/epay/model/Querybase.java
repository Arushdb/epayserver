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



@Entity
@NamedQueries({

@NamedQuery (name="getParentMenuItems",
	//query="SELECT parent.id as id, parent.name as name, parent.lft as lft , "
	//		+ " parent.rgt as rgt , parent.route  as route FROM Menu AS node, Menu AS parent" +
query="SELECT  parent "
		+ "  FROM Menu AS node, Menu AS parent" + 
		
        " WHERE node.lft BETWEEN parent.lft AND parent.rgt"+
        " AND node.id in (select menu_id FROM Role_Menu  WHERE role_id = :role_id )"+
        " and parent.id > 1 and parent.lft+1 <>  parent.rgt"+
        " group by parent.id order by parent.lft"
		),
		
@NamedQuery (name="getChildrenMenuItems",
	query ="Select  node "+
		" FROM Menu AS node,Menu AS parent"+
	    " WHERE node.lft BETWEEN parent.lft AND parent.rgt"+
		" and node.rgt <parent.rgt"+
		" AND node.id in (select menu_id FROM Role_Menu  WHERE role_id = :role_id )"+
	    " AND parent.id = :MenuId "+
		" ORDER BY node.lft"
	),
@NamedQuery (name="getdefaultrole",
query =" select u from UserRoles u where user_id=:userid and default_role=:defaultrole"
),


})


@NamedNativeQueries(
		{  
			
		@NamedNativeQuery(
					name="getstudentdetail",
					query="select sp.roll_number ,student_first_name as studentname,program_name as programname,"
							+ "sp.program_id as programid from cms21112022.student_program sp\r\n"
							+ " join cms21112022.student_master sm on sm.enrollment_number = sp.enrollment_number \r\n"
							+ " join cms21112022.program_master pm on pm.program_id = sp.program_id\r\n"
							+ " where roll_number =:rollno and program_status in (\"ACT\",\"PAS\",\"FAL\")",
					resultSetMapping="studentDetailmap")
			

			
		})
	    
	    
	    @SqlResultSetMapping(
	    		name ="studentDetailmap",
	    		classes = 
	    				{
	    					@ConstructorResult
	    							(
	    							targetClass = Student.class,
	    							columns = {
	    									@ColumnResult(name = "roll_number"),
	    									@ColumnResult(name = "studentname"),
	    									@ColumnResult(name = "programname"),
	    									@ColumnResult(name = "programid")
	    									
	    							}
	    					
	    							)
	    					}
	    			)
	    
	    		







//@SqlResultSetMapping(name="StudentVerificationmap2" ,entities = {@EntityResult(entityClass = Verification.class)} )

public class Querybase {
	 @Id
     private long id;
}
