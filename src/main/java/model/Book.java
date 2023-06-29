// Author: Zay Yar Tun
// Admin No: 2235035
// Class: DIT/FT/2A/02
// Group: 10
// Date: 4.6.2023
// Description: to store book data from database 

package model;

import java.sql.Date;

public class Book {
	private String ISBNNo;
	private String title;
	private int page;
	private double price;
	private String publisher;
	private Date publicationDate;
	private int qty;
	private short rating;
	private String description;
	private String image;
	private String image3D;
	private String status;
	private Author[] authors;
	private Genre[] genres;
	
	public Book(String iSBNNo, int qty) {
		ISBNNo = iSBNNo;
		this.qty = qty;
	}
	
	public Book(String iSBNNo, String title, int page, double price, String publisher, Date publicationDate, int qty,
			short rating, String description, String image, String image3d, String status, Author[] authors) {
		ISBNNo = iSBNNo;
		this.title = title;
		this.page = page;
		this.price = price;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.qty = qty;
		this.rating = rating;
		this.description = description;
		this.image = image;
		image3D = image3d;
		this.status = status;
		this.authors = authors;
	}

	public Book(String iSBNNo, String title, int page, double price, String publisher, Date publicationDate, int qty,
			String description, String image, String image3d, String status) {
		ISBNNo = iSBNNo;
		this.title = title;
		this.page = page;
		this.price = price;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.qty = qty;
		this.description = description;
		this.image = image;
		image3D = image3d;
		this.status = status;
	}

	public Book(String iSBNNo, String title, double price, String description, String image) {
		ISBNNo = iSBNNo;
		this.title = title;
		this.price = price;
		this.description = description;
		this.image = image;
	}

	public Book(String iSBNNo, String title, String image, String status) {
		super();
		ISBNNo = iSBNNo;
		this.title = title;
		this.image = image;
		this.status = status;
	}

	public Book(String iSBNNo, String title, int page, double price, String publisher, Date publicationDate, int qty,
			short rating, String description, String image, String image3d, String status) {
		ISBNNo = iSBNNo;
		this.title = title;
		this.page = page;
		this.price = price;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.qty = qty;
		this.rating = rating;
		this.description = description;
		this.image = image;
		image3D = image3d;
		this.status = status;
	}

	public Book(String iSBNNo, String title, int page, double price, String publisher, Date publicationDate, int qty,
			short rating, String description, String image, String image3d, String status, Author[] authors,
			Genre[] genres) {
		ISBNNo = iSBNNo;
		this.title = title;
		this.page = page;
		this.price = price;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.qty = qty;
		this.rating = rating;
		this.description = description;
		this.image = image;
		image3D = image3d;
		this.status = status;
		this.authors = authors;
		this.genres = genres;
	}

	public Genre[] getGenres() {
		return genres;
	}

	public void setGenres(Genre[] genres) {
		this.genres = genres;
	}

	public String getISBNNo() {
		return ISBNNo;
	}

	public void setISBNNo(String iSBNNo) {
		ISBNNo = iSBNNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public short getRating() {
		return rating;
	}

	public void setRating(short rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage3D() {
		return image3D;
	}

	public void setImage3D(String image3d) {
		image3D = image3d;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Author[] getAuthors() {
		return authors;
	}

	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}
}