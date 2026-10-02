package savickiy.corp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import savickiy.corp.entity.jpa.Note;
import savickiy.corp.entity.enums.Mood;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserIdOrderByDateDesc(Long userId);

    List<Note> findByUserIdAndDateBetweenOrderByDateDesc(
            Long userId, LocalDate from, LocalDate to);

    List<Note> findByCycleIdOrderByDateAsc(Long cycleId);

    List<Note> findByUserIdAndMoodOrderByDateDesc(Long userId, Mood mood);


    @Query("""
                SELECT n FROM Note n
                WHERE n.user.id = :userId
                  AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%'))
                       OR LOWER(n.content) LIKE LOWER(CONCAT('%', :query, '%')))
                ORDER BY n.date DESC
            """)
    List<Note> searchByUser(@Param("userId") Long userId,
                            @Param("query") String query);
}
