package com.mondragon.tradehunter.demo.request_models;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.NoArgsConstructor;

@XmlRootElement
@NoArgsConstructor
public class RequestMessage {
    private String content;
    private String userUsername;
    private int forumID;

    @XmlElement
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    private Date date;
    
    public RequestMessage(String content, String userUsername, int forumID, Date date) {
        this.content = content;
        this.userUsername = userUsername;
        this.forumID = forumID;
        this.date = date;
    }
    @XmlElement
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    @XmlElement
    public String getUserUsername() {
        return userUsername;
    }
    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
    @XmlElement
    public int getForumID() {
        return forumID;
    }
    public void setForumID(int forumID) {
        this.forumID = forumID;
    }
}