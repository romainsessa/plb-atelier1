package fr.example.mono.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserDto {
    private Integer id;

    @NotEmpty(message = "Please enter valid firstname.")
    private String firstName;

    @NotEmpty(message = "Please enter valid lastname.")
    private String lastName;

    @NotEmpty(message = "Please enter valid email.")
    @Email
    private String email;

    @NotEmpty(message = "Please enter valid password.")
    private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public UserDto(Integer id, @NotEmpty(message = "Please enter valid firstname.") String firstName,
			@NotEmpty(message = "Please enter valid lastname.") String lastName,
			@NotEmpty(message = "Please enter valid email.") @Email String email,
			@NotEmpty(message = "Please enter valid password.") String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
    
    
}
