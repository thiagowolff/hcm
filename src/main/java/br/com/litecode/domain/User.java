package br.com.litecode.domain;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "user")
@NamedQuery(name = "findUserByUsername", query = "select u from User u where u.username = ?1", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") })
public class User implements Serializable  {
    private static final long serialVersionUID = 1L;

	public enum Role { ADMIN, USER }
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column(name = "full_name")
	private String fullName;

	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name = "last_access")
	private Date lastAccess;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
    public User() {
    	creationDate = Date.from(Instant.now());
    }
    
	public Integer getUserId() {
    	return this.userId;
    }
    
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return userId.equals(other.userId);
	}
}