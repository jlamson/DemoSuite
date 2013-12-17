package com.darkmoose117.demos.model;

import java.util.List;

/**
 * Created by Joshua Lamson on 12/17/13.
 */
public class Email {

    public List<String> people;
    public String subject;
    public String body;
    public int numberOfReplies;
    public boolean isFavorite;
    public long time;
    public boolean hasAttachment;

    public Email(List<String> people, String subject, String body, int numberOfReplies, boolean isFavorite, boolean hasAttachment, long time) {
        this.people = people;
        this.subject = subject;
        this.body = body;
        this.numberOfReplies = numberOfReplies;
        this.isFavorite = isFavorite;
        this.hasAttachment = hasAttachment;
        this.time = time;
    }
}
