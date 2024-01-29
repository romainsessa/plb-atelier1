package fr.example.mono.dto;

import java.sql.Timestamp;

public class TransactionDto {
    private Integer uid;
    private String firstName;
    private String lastName;
    private Timestamp date;
    private double amount;
    private String label;

    
    
    public Integer getUid() {
		return uid;
	}



	public void setUid(Integer uid) {
		this.uid = uid;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public Timestamp getDate() {
		return date;
	}



	public void setDate(Timestamp date) {
		this.date = date;
	}



	public double getAmount() {
		return amount;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}

	public TransactionDto() {
		// TODO Auto-generated constructor stub
	}


	public TransactionDto(Integer uid, String firstName, String lastName, Timestamp date, double amount, String label) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.amount = amount;
        this.label = label;
    }
}
