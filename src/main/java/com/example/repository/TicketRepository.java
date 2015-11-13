package com.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Action;
import com.example.entity.Ticket;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>{

	//List<Ticket> findFirst10ByUsername(String username);
	//List<Ticket> findFirst10();
	
	//public Action findTicketCreator(Long id);

}