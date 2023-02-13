package com.dayalbagh.epay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.Menu;




@Repository
public interface  MenuRepository  extends JpaRepository<Menu, Integer>{
	

}
