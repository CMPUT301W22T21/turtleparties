package com.example.turtlepartiesapp.Models;
// Used for comments on QR codes
public class Comment {
    private String commentBody;
    private String author;

    Comment(){}

    /**
     *
     * @param cmnt String representing the commen
     * @param auth Person who wrote the comment
     */
    Comment(String cmnt, String auth){
        this.commentBody = cmnt;
        this.author = auth;
    }

    /**
     *  Getter method for comment
     * @return
     * Returns the comment body
     */
    public String getCommentBody() {
        return commentBody;
    }

    /**
     * Geetter method for author
     * @return
     * Return the comment author
     */
    public String getAuthor() {
        return author;
    }
}
