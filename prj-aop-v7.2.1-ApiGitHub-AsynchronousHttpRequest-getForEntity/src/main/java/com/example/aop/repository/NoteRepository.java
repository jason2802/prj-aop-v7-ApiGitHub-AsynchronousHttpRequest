package com.example.aop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aop.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
