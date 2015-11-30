package com.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Ticket;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>{

}