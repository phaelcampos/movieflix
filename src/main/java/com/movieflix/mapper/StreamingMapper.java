package com.movieflix.mapper;

import com.movieflix.entity.Streaming;
import com.movieflix.request.StreamingRequest;
import com.movieflix.response.StreamingResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {

    public Streaming toStreaming(StreamingRequest streamingResponse){
        return Streaming
                .builder()
                .name(streamingResponse.name())
                .build();
    }

    public StreamingResponse toStreamingResponse(Streaming streaming){
        return StreamingResponse
                .builder()
                .name(streaming.getName())
                .id(streaming.getId())
                .build();
    }
}
