package com.example.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.NotFoundException;
import com.example.entity.Action;
import com.example.entity.ActionID;
import com.example.entity.Comment;
import com.example.entity.CommentJsonWrapper;
import com.example.entity.Ticket;
import com.example.entity.TicketWrapper;
import com.example.entity.User;
import com.example.repository.TicketRepository;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@JsonView(com.example.entity.Ticket.class)
	@RequestMapping(value = "/api/ticket/{id}", method = RequestMethod.GET, produces = "application/json")
	public Ticket ticketById(@PathVariable("id") Long id) {
		Ticket ticket = ticketRepository.findOne(id);
		if (ticket == null) {
			throw new NotFoundException(id.toString());
		}
		
		return ticket;
	}

	@JsonView(com.example.entity.TicketWrapper.class)
	@RequestMapping(value = "/api/ticket/{id}/detailed", method = RequestMethod.GET, produces = "application/json")
	public TicketWrapper ticketDetailedById(@PathVariable("id") Long id) {
	
		
		Ticket ticket = ticketRepository.findOne(id);
		if (ticket == null) {
			throw new NotFoundException(id.toString());
		}


		User user = userRepository.findTicketCreator(id);
		TicketWrapper ticketWrapper = new TicketWrapper(ticket);		
		ticketWrapper.setUserId(user.getId());
		ticketWrapper.setLogin(user.getLogin());
		
		//ticketWrapper.setTicket(ticket);
		//ticketWrapper.setUser(user);
		
		System.out.println(ticketWrapper);
		

		//ObjectMapper mapper = new ObjectMapper();
		//return mapper.writeValueAsString(ticketWrapper);
		
		return ticketWrapper;
	}

	@JsonView(com.example.entity.Ticket.class)
	@RequestMapping(value = "/api/tickets/{count}", method = RequestMethod.GET, produces = "application/json")
	public List<Ticket> tickets(@PathVariable("count") int count) {
		Page<Ticket> page = ticketRepository
				.findAll(new PageRequest(0, count, new Sort(new Order(Sort.Direction.DESC, "id"))));

		return page.getContent();
	}

	@JsonView(com.example.entity.Ticket.class)
	@RequestMapping(value = "/api/tickets/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	public List<Ticket> ticketsByUser(@PathVariable("userId") Long userId) {
		List<Ticket> tickets = new ArrayList<Ticket>();

		User user = userRepository.findOne(userId);
		Set<Action> list = user.getUserTickets();
		for (Action action : list) {
			ActionID actionPk = action.getPk();
			tickets.add(actionPk.getTicket());
		}

		return tickets;
	}

	@JsonView(com.example.entity.Comment.class)
	@RequestMapping(value = "/api/ticket/{id}/addComment", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Comment> addComment(@PathVariable("id") Long id, @RequestBody CommentJsonWrapper commentJsonWrapper) {
		List<Comment> comments = new ArrayList<Comment>();

		// update
		User user = userRepository.findOne(commentJsonWrapper.getUserId());
		if (user == null) {
			throw new NotFoundException(id.toString() + "(User)");
		}

		Ticket ticket = ticketRepository.findOne(id);
		if (ticket == null) {
			throw new NotFoundException(id.toString() + "Ticket)");
		}

	
		Comment newComment = new Comment();
		newComment.setComment(commentJsonWrapper.getComment());


		//SimpleDateFormat currentDateime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());


		newComment.setCommentDate(currentDateime);
		newComment.setUser(user);
		newComment.setTicket(ticket);
		
		ticket.getUserComments().add(newComment);
		ticket.setTicketUpdated(currentDateime);
		ticketRepository.save(ticket);
		

		
		/*
		User user = userRepository.findOne(userId);
		Set<Action> list = user.getUserTickets();
		for (Action action : list) {
			ActionID actionPk = action.getPk();
			tickets.add(actionPk.getTicket());
		}
*/
		return comments;
	}

	@ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(NotFoundException exception) {
    }
	
	@RequestMapping("/")
	public String defaultPage() {
		return "Available Methods:<br />" + "/api/ticket/{id}<br/>" + "/api/tickets/{count}<br/>" + "/api/tickets/user/{userId}";
	}
}
