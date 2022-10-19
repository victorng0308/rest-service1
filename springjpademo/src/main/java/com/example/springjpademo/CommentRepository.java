package com.example.springjpademo;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Page<Comment> findByPostId(Long postId, Pageable pageable);

  Optional<Comment> findByIdAndPostId(Long id, Long postId);
}
