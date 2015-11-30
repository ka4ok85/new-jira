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
import org.springframework.test.annotation.Rollback;
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
import com.example.entity.CommentID;
import com.example.entity.CommentJsonWrapper;
import com.example.entity.Ticket;
import com.example.entity.TicketWrapper;
import com.example.entity.User;
import com.example.repository.ActionRepository;
import com.example.repository.CommentRepository;
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
	
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
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
		if (user == null) {
			throw new NotFoundException("Ticket without author found");
		}

		TicketWrapper ticketWrapper = new TicketWrapper(ticket);		
		ticketWrapper.setUserId(user.getId());
		ticketWrapper.setLogin(user.getLogin());
		
		return ticketWrapper;
	}

	@JsonView(com.example.entity.Ticket.class)
	@RequestMapping(value = "/api/tickets/{count}", method = RequestMethod.GET, produces = "application/json")
	public List<Ticket> tickets(@PathVariable("count") int count) {
		Page<Ticket> page = ticketRepository
				.findAll(new PageRequest(0, count, new Sort(new Order(Sort.Direction.DESC, "id"))));

		return page.getContent();
	}

	@RequestMapping(value = "/api/ticket/add/user/{userId}", method = RequestMethod.POST, produces = "application/json")
	@JsonView(com.example.entity.Ticket.class)
	public Object addTicket(@PathVariable("userId") Long userId, @RequestBody Ticket ticket) {
		String currentDateime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

		// find user
		User user = userRepository.findOne(userId);
		if (user == null ) {
			throw new NotFoundException(userId.toString());
		}

		// add ticket
		ticket.setTicketAdded(currentDateime);
		Ticket newTicket = ticketRepository.save(ticket);
		if (newTicket == null ) {
			throw new NotFoundException("Can not add new Ticket");
		}

		// add action
		Action newAction = new Action();
		newAction.setAction("ticket added");
		newAction.setActionDate(currentDateime);
		newAction.setTicket(newTicket);
		newAction.setUser(user);
		actionRepository.save(newAction);
		
		return newTicket;
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


	// delete comment (only)
	// delete ticket (+comments +actions)

	// pass userId through POST
	@JsonView(com.example.entity.Comment.class)
	@RequestMapping(value = "/api/ticket/{id}/addComment", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Comment addComment(@PathVariable("id") Long id, @RequestBody CommentJsonWrapper commentJsonWrapper) {
		String currentDateime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		// find user
		User user = userRepository.findOne(commentJsonWrapper.getUserId());
		if (user == null) {
			throw new NotFoundException(id.toString() + "(User)");
		}

		// find ticket
		Ticket ticket = ticketRepository.findOne(id);
		if (ticket == null) {
			throw new NotFoundException(id.toString() + "(Ticket)");
		}

		// add new comment
		Comment newComment = new Comment();
		newComment.setComment(commentJsonWrapper.getComment());
		newComment.setCommentDate(currentDateime);
		newComment.setUser(user);
		newComment.setTicket(ticket);
		commentRepository.save(newComment);
		
		// update ticket
		ticket.getUserComments().add(newComment);
		ticket.setTicketUpdated(currentDateime);
		ticketRepository.save(ticket);
		
		return newComment;
	}

	@JsonView(com.example.entity.Comment.class)
	@RequestMapping(value = "/api/ticket/{ticketId}/deleteComment/{commentId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
	@Rollback(false)
	public void deleteComment(@PathVariable("ticketId") Long ticketId, @PathVariable("commentId") Long commentId) {
		System.out.println("---");
		// find ticket
		Ticket ticket = ticketRepository.findOne(ticketId);
		if (ticket == null) {
			throw new NotFoundException(ticketId.toString() + "(Ticket)");
		}

		// find comment
		//commentRepository.findOne(arg0)
		// Provided id of the wrong type for class com.example.entity.Comment 
		// Expected: class com.example.entity.CommentID, got class java.lang.Long; 
		
		
		//CommentID commentID = commentIDRepository.findOne(commentId);
		//if (commentID == null) {
			//throw new NotFoundException(commentId.toString() + "(Comment)");
		//}		

		Set<Comment> commentsList = ticket.getUserComments();
		Set<Comment> commentsList2 = commentsList;
		for (Comment commentItem : commentsList) {
			System.out.println(commentItem.getPk().getId());
			if (commentItem.getPk().getId().equals(commentId)) {
				System.out.println("delete: " + commentItem.getPk().getId());
				Comment c = commentItem;
				//
				//System.out.println(c);
				commentRepository.delete(c);
				commentsList2.remove(commentItem);
				//commentRepository.delete(commentItem.getPk().getId());
				//commentRepository.delete(commentItem.getPk());
			}
		}
		
		ticket.setUserComments(commentsList2);
		ticketRepository.save(ticket);
		
		System.out.println("done");
		
		//Comment comment = commentRepository.findOne(commentId);
		//if (comment == null) {
//			throw new NotFoundException(commentId.toString() + "(Comment)");
		//}
		
		// check comment belongs to specified ticket
		//if (!comment.getPk().getTicket().getId().equals(ticket.getId())) {
//			throw new NotFoundException("Wrong ticket specified for comment " + commentId);
		//}
	
		//commentRepository.delete(commentId);
	}

	@ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(NotFoundException exception) {
    }
	
}
// addComment should return FULL comment