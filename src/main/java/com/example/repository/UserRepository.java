package com.example.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query(value = "select u.* from users u, actions a  WHERE u.id = a.id_user AND a.id_ticket=?1", nativeQuery = true)
	public User findTicketCreator(Long id);
}
