package com.hongikbros.jobmanager.notice.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    
    @Query("select case when count(bookmark) > 0 then true else false end from Bookmark bookmark where bookmark.memberId.id = :memberId")
    boolean existsBookmarkByMemberId(Long memberId);
}
