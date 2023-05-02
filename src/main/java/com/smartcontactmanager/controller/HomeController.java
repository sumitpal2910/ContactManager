package com.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public HomeController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping("/")
	public String home(ModelMap model) {
		model.put("name", "Sumit");
		model.put("title", "Smart Contact Manager");
		return "home";
	}

	@RequestMapping("/about")
	public String about(ModelMap model) {
		model.put("title", "About - Smart Contact Manager");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(ModelMap model) {
		model.put("title", "Singup - Smart Contact Manager");
		model.put("user", new User());
		return "signup";
	}

	@RequestMapping("/signin")
	public String signin(ModelMap model) {
		model.put("title", "Singin - Smart Contact Manager");
		return "login";
	}

	
	@PostMapping("/do-register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result ,
			@RequestParam(value = "terms", defaultValue = "false") boolean terms, ModelMap model, HttpSession session
			) {

		try {
			if (!terms)
				throw new Exception("You have to aggreed to terms and conditions");
			
			if (result.hasErrors()) {
				System.out.println(result.getAllErrors());
				model.put("user", user);
				
				return "signup";
			}
			
			user.setActive(true);
			user.setRole("ROLE_USER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser = userRepository.save(user);

			model.put("user", new User());
			session.setAttribute("message", new Message("Register Success", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Someting went wrong !!" + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}
}