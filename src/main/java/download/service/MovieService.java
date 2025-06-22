package download.service;

import download.model.Music;
import download.repository.MusicRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
public interface MovieService {


     Music save(Music song) ;
     List<Music> listAll();
     List<Music> search(String keyword);
}
