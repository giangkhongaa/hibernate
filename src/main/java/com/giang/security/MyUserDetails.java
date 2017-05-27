package com.giang.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class MyUserDetails implements UserDetails {
	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	private List<GrantedAuthority> authorities;

	public MyUserDetails(String userName, String password, List<GrantedAuthority> authorities) {
		super();
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	// tai khoan chua het han?
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}
	// tai khoan chua bi khoa?
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	//chung chi co chua het han
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}
	// tai khoan duoc phep truy cap
	@Override
	public boolean isEnabled() {

		return true;
	}

}
