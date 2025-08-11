package com.movieflix.controller;

import com.movieflix.entity.Streaming;
import com.movieflix.mapper.StreamingMapper;
import com.movieflix.request.StreamingRequest;
import com.movieflix.response.StreamingResponse;
import com.movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streamingService;

    @GetMapping
    public ResponseEntity<List<StreamingResponse>> getAllStreams(){
        List<Streaming> streamings = streamingService.getAllStreaming();
        return ResponseEntity.ok(streamings
                .stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getStreamingById(@PathVariable Long id){
        return streamingService.findStreamingById(id)
                .map(
                        streaming -> ResponseEntity.ok(
                        StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StreamingResponse> saveStreaming(@RequestBody StreamingRequest request){
        Streaming streaming = StreamingMapper.toStreaming(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        StreamingMapper.toStreamingResponse(streamingService.saveStreaming(streaming)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StreamingResponse> deleteStreamingById(@PathVariable Long id){
        streamingService.deleteStreamingById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
