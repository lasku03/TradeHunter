package com.mondragon.tradehunter.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    List<Message> findByForum(Optional<Forum> forum);
}
