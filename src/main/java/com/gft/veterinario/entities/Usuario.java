package com.gft.veterinario.entities;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	@JsonIgnore
	private String senha;
	@ManyToOne
	private Perfil perfil;

	public Usuario(Long id, String cpf, String senha, Perfil perfil) {
		this.id = id;
		this.cpf = cpf;
		this.senha = senha;
		this.perfil = perfil;
	}

	public Usuario() {

	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(this.perfil);
	}
	@Override
	public String getPassword() {
		return getSenha();
	}
	@Override
	public String getUsername() {
		return getCpf();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

}
