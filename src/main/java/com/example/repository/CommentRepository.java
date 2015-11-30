package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Comment;
import com.example.entity.CommentID;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	//public void delete(CommentID pk);

	//@Query(value = "select u.* from users u, actions a  WHERE u.id = a.id_user AND a.id_ticket=?1", nativeQuery = true)
	//public User findTicketCreator(Long id);
}

