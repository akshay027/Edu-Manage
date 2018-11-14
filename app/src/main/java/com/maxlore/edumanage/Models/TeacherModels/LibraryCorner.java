package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class LibraryCorner {

    @SerializedName("book_reviews")
    @Expose
    private List<BookReview> bookReviews = new ArrayList<BookReview>();
    @SerializedName("popular_book")
    @Expose
    private PopularBook popularBook;
    @SerializedName("famous_author")
    @Expose
    private String famousAuthor;

    /**
     * @return The bookReviews
     */
    public List<BookReview> getBookReviews() {
        return bookReviews;
    }

    /**
     * @param bookReviews The book_reviews
     */
    public void setBookReviews(List<BookReview> bookReviews) {
        this.bookReviews = bookReviews;
    }

    /**
     * @return The popularBook
     */
    public PopularBook getPopularBook() {
        return popularBook;
    }

    /**
     * @param popularBook The popular_book
     */
    public void setPopularBook(PopularBook popularBook) {
        this.popularBook = popularBook;
    }

    /**
     * @return The famousAuthor
     */
    public String getFamousAuthor() {
        return famousAuthor;
    }

    /**
     * @param famousAuthor The famous_author
     */
    public void setFamousAuthor(String famousAuthor) {
        this.famousAuthor = famousAuthor;
    }

}
