package com.gugucoding.restful.member.repository;

import com.gugucoding.restful.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity,String> {

}
