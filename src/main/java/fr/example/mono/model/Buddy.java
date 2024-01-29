package fr.example.mono.model;

import jakarta.persistence.*;

@Entity
@Table(name = "buddies")
public class Buddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid")
    private Integer bid;
    @Column(name = "uid1")
    private Integer uid1;
    @Column(name = "uid2")
    private Integer uid2;
    
	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public Integer getUid1() {
		return uid1;
	}

	public void setUid1(Integer uid1) {
		this.uid1 = uid1;
	}

	public Integer getUid2() {
		return uid2;
	}

	public void setUid2(Integer uid2) {
		this.uid2 = uid2;
	}

	public Buddy() {
		// TODO Auto-generated constructor stub
	}    
    
    public Buddy(Integer uid1, Integer uid2) {
        this.uid1 = uid1;
        this.uid2 = uid2;
    }
}
