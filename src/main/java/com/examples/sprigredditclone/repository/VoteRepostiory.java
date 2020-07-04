package com.examples.sprigredditclone.repository;

import com.examples.sprigredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepostiory extends JpaRepository<Vote, Long> {
}
