package dev.aleoliv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Task extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Size(min = 1, max = 30)
  @Column(name = "title", length = 30, nullable = false)
  private String title;

  @NotEmpty
  @Size(min = 1, max = 100)
  @Column(name = "description", length = 100, nullable = false)
  private String description;
}
