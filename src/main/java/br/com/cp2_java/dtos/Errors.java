package br.com.cp2_java.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record Errors(
        LocalDateTime timestamp,
        int status,
        String error,
        List<String> messages,
        String path
) {}

