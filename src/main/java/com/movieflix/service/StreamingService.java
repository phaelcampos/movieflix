package com.movieflix.service;

import com.movieflix.entity.Streaming;
import com.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public List<Streaming> getAllStreaming(){
       return streamingRepository.findAll();
    }

    public Optional<Streaming> findStreamingById(Long id){
        return streamingRepository.findById(id);
    }

    public Streaming saveStreaming(Streaming streaming){
        return streamingRepository.save(streaming);
    }

    public void deleteStreamingById(Long id){
        streamingRepository.deleteById(id);
    }
}
