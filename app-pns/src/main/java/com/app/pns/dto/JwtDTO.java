package com.app.pns.dto;


public class JwtDTO {
	
    private String token;
    private int id;
    
    /*
    private String bearer = "Bearer";
    private String userName;
    private Collection<? extends GrantedAuthority> authorities;
    */

    public JwtDTO() {
  		super();
  	}
    
    public JwtDTO(String token, int id) {
        this.token = token;
        this.id=id;
    }

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
