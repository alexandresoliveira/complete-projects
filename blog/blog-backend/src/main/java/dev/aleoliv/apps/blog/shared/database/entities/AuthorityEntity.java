package dev.aleoliv.apps.blog.shared.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityEntity extends BaseEntity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;
	
	@PrePersist
	public void prePersist() {
		if (!name.startsWith("ROLE_"))
			this.name = String.format("ROLE_%s", this.name);
	}

	@Override
	public String getAuthority() {
		return this.name;
	}
}
