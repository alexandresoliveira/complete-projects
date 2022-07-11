package dev.aleoliv.apps.blog.shared.database.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class PostEntity extends BaseEntity {

	@Column(name = "text")
	@NotNull
	@NotEmpty
	@Size(max = 256)
	private String text;

	@Column(name = "link")
	@Size(max = 512)
	private String link;

	@Column(name = "link_photo")
	@Size(max = 512)
	private String linkPhoto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
	private List<CommentEntity> comments;

	public PostEntity(UUID id) {
		this.setId(id);
	}
}
