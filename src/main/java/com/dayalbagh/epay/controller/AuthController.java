package com.dayalbagh.epay.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dayalbagh.epay.model.Login;
import com.dayalbagh.epay.model.UserRoles;
import com.dayalbagh.epay.payload.request.LoginRequest;
import com.dayalbagh.epay.payload.response.JwtResponse;
import com.dayalbagh.epay.repository.RoleRepository;
import com.dayalbagh.epay.repository.UserRepository;
import com.dayalbagh.epay.security.jwt.JwtUtils;
import com.dayalbagh.epay.service.UserDetailsImpl;
import com.dayalbagh.epay.service.UserDetailsServiceImpl;






//@CrossOrigin(origins = "localhost:4200", maxAge = 3600)


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;	

	



	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		Login login = new Login();
		
		
		
		List<UserRoles> dftroles  =userDetailsServiceImpl.getdefaultrole(userDetails.getId());
		
		int id =dftroles.get(0).getUserrolePK().getUser_id();
		JSONArray menuary =userDetailsServiceImpl.getNewMenu(id);
		
		

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 menuary.toString(),roles));
	}
}
