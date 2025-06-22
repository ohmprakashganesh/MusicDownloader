package download.imp;

import download.model.Music;
import download.repository.MusicRepo;
import download.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class MovieSerImpl  implements MovieService {

    private  final MusicRepo repo;
    @Override
    public Music save(Music song){
       return repo.save(song);
    }
    @Override
    public List<Music> listAll() {
       return  repo.findAll();
    }

    @Override
    public List<Music> search(String keyword) {
        return  repo.findByTitleContainingIgnoreCase(keyword);
    }
}
