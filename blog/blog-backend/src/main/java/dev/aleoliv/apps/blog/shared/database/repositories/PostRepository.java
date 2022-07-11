package dev.aleoliv.apps.blog.shared.database.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import dev.aleoliv.apps.blog.shared.database.entities.PostEntity;
import dev.aleoliv.apps.blog.usecases.v1.posts.index.PostsIndexRequestDto;

public interface PostRepository extends JpaRepository<PostEntity, UUID>, JpaSpecificationExecutor<PostEntity> {

	Optional<PostEntity> findByIdAndUserId(UUID id, UUID userId);
	
	default Specification<PostEntity> params(PostsIndexRequestDto parameters, Example<PostEntity> example) {

		return (root, query, builder) -> {
			final List<Predicate> predicates = new ArrayList<>();

			if (parameters.getStartCreatedAt() != null) {
				predicates.add(builder.greaterThan(root.get("createdAt"), parameters.getStartCreatedAt()));
			}

			if (parameters.getEndCreatedAt() != null) {
				predicates.add(builder.lessThan(root.get("createdAt"), parameters.getEndCreatedAt()));
			}

			if (parameters.getStartUpdatedAt() != null) {
				predicates.add(builder.lessThan(root.get("updatedAt"), parameters.getStartUpdatedAt()));
			}

			if (parameters.getEndUpdatedAt() != null) {
				predicates.add(builder.lessThan(root.get("updatedAt"), parameters.getEndUpdatedAt()));
			}

			if (example != null) {
				predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
			}

			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
