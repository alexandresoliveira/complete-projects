package dev.aleoliv.thezueranetwork.thezueraneverendsapp.shared.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseEntity {

  @Column
  private String name;

  @Column(unique = true)
  private String email;

  @Column
  private String password;

  @PrePersist
  public void prePersist() {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt(8));
  }
}
