package com.example.passangers.repository;

import com.example.passangers.domain.Passengers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PassangersRepo extends JpaRepository<Passengers,Long>, PagingAndSortingRepository<Passengers,Long> {


    Page<Passengers> findAllByName(String name, Pageable pageable);

    List<Passengers> findAllByName (String name, Sort sort);



}
