package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.NotFoundException;
import com.example.entity.User;
import com.example.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/api/user/disable/{id}", method = RequestMethod.GET, produces = "application/json")
	public Object disableUser(@PathVariable("id") Long id) {
		User user = userRepository.findOne(id);
		if (user == null ) {
			throw new NotFoundException(id.toString());
		}

		user.setStatus("disabled");
		userRepository.save(user);

		return user;
	}

	@ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(NotFoundException exception) {
    }
	
/*
	// @Autowired
	@RequestMapping("/greeting")
	public String greeting() {

		User user = userRepository.findOne(2L);
		System.out.println(user);

		Ticket ticket = ticketRepository.findOne(1L);
		System.out.println(ticket);

		User newUser = new User();
		newUser.setFirstName("firstName");
		newUser.setLastName("lastName");
		newUser.setLogin("login");
		newUser.setPassword("password");
		newUser.setStatus("active");
		userRepository.save(newUser);

		Ticket newTicket = new Ticket();
		newTicket.setDescription("description");
		newTicket.setPriority("normal");
		newTicket.setStatus("new");
		newTicket.setTitle("title");

		Action newAction = new Action();
		newAction.setAction("ticket created");
		newAction.setActionDate("2015-01-01 00:00:00");
		newAction.setUser(newUser);
		newAction.setTicket(newTicket);

		newTicket.getUserTickets().add(newAction);
		ticketRepository.save(newTicket);

		return "Hello " + user.toString() + ticket.toString();

	}


	@RequestMapping("/")
	public String defaultPage() {
		return "Available Methods:<br />" + "/api/user/disable/{id}<br/>"+ "/api/user/enable/{id}<br/>";
	}
*/
}
