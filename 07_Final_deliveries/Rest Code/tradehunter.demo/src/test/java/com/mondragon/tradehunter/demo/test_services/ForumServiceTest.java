package com.mondragon.tradehunter.demo.test_services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.repository.ForumRepository;
import com.mondragon.tradehunter.demo.services.ForumService;

class ForumServiceTest extends EasyMockSupport{
        ForumRepository forumRepository;
        ForumService forumService;
        Optional<Forum> forum;
    @BeforeEach
    void setUp() {
        forumRepository = createMock(ForumRepository.class);
        forumService = new ForumService(forumRepository);
    }

    @Test
    void testGetForumByID(){
        EasyMock.expect(forumRepository.findById(1)).andReturn(forum);
        replayAll();
        assertEquals(forumService.getForumByID(1), forum);
        verifyAll();
    }
}
