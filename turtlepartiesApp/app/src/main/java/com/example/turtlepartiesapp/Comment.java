package com.example.turtlepartiesapp;

public class Comment {
    private String commentBody;
    private String author;

    Comment(String cmnt, String auth){
        this.commentBody = cmnt;
        this.author = auth;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public String getAuthor() {
        return author;
    }
}
