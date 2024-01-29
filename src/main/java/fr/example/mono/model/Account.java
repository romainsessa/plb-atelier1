package fr.example.mono.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid")
    private Integer aid;
    @Column(name = "uid")
    private Integer uid;
    @Column(name = "name")
    private String name;
    @Column(name = "card_type")
    private CardTypeProvider cardType;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "exp_month")
    private Integer expMonth;
    @Column(name = "exp_year")
    private Integer expYear;
    @Column(name = "modified_date")
    private Timestamp modifiedDate;
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardTypeProvider getCardType() {
		return cardType;
	}
	public void setCardType(CardTypeProvider cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Integer getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(Integer expMonth) {
		this.expMonth = expMonth;
	}
	public Integer getExpYear() {
		return expYear;
	}
	public void setExpYear(Integer expYear) {
		this.expYear = expYear;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
    
}
