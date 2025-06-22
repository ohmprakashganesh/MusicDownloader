package download.repository;

import download.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MusicRepo extends JpaRepository<Music, Long> {
List<Music> findByTitleContainingIgnoreCase(String title);
}
