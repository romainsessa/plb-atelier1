package fr.example.mono.dto;

public class FriendDto {
    private Integer uid;
    private String lastName;
    private String firstName;

    public Integer getUid() {
		return uid;
	}



	public void setUid(Integer uid) {
		this.uid = uid;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public FriendDto() {
		// TODO Auto-generated constructor stub
	}

	public FriendDto(Integer uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
