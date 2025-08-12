package com.movieflix.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(
        @Schema(type = "string", description = "nome do filme" )
        @NotEmpty(message = "Titulo do filme é obrigatório") String title,

        @Schema(type = "string", description = "Descrição do filme" )
        String description,

        @Schema(type = "date", description = "Data de lançamento no padrão dd/MM/yyyy" )
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate releaseDate,

        @Schema(type = "double", description = "Score do filme ex: 7.98" )
        double rating,

        @Schema(type = "array", description = "Lista das categorias do filme" )
        List<Long> categories,

        @Schema(type = "array", description = "Lista dos serviços de streaming que o filme está disponivel" )
        List<Long> streamings
        ) {
}
