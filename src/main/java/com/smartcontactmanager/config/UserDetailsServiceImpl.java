package com.smartcontactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetching user from database
		User user = userRepository.getUserByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("Could not found User!!");

		CustomUserDetails userDetails = new CustomUserDetails(user);
		return userDetails;
	}

}
