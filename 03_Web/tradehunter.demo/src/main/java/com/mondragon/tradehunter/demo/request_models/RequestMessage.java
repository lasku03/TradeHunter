package com.mondragon.tradehunter.demo.request_models;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestMessage {
    private String content;
    private String userUsername;
    private int forumID;

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    private LocalDateTime date;
    
    public RequestMessage(String content, String userUsername, int forumID, LocalDateTime date) {
        this.content = content;
        this.userUsername = userUsername;
        this.forumID = forumID;
        this.date = date;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUserUsername() {
        return userUsername;
    }
    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
    public int getForumID() {
        return forumID;
    }
    public void setForumID(int forumID) {
        this.forumID = forumID;
    }
}
