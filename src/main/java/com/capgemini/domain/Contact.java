package com.capgemini.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Contact {

	@Column(length = 20, nullable = false)
	private String email;
	@Column(length = 20, nullable = false)
	private String telefonHome;
	@Column(length = 20, nullable = false)
	private String telefonMobile;

	
	public Contact(){
		
	}
	
	public Contact(String email, String telefonHome, String telefonMobile) {
		this.email = email;
		this.telefonHome = telefonHome;
		this.telefonMobile = telefonMobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonHome() {
		return telefonHome;
	}

	public void setTelefonHome(String telefonHome) {
		this.telefonHome = telefonHome;
	}

	public String getTelefonMobile() {
		return telefonMobile;
	}

	public void setTelefonMobile(String telefonMobile) {
		this.telefonMobile = telefonMobile;
	}

}
