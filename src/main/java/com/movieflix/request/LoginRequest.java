package com.movieflix.request;

import lombok.Builder;

public record LoginRequest (String email, String password) {
}
