package com.shortner.agent.url.dto;
import jakarta.validation.constraints.NotBlank;
public record CreateUrlRequest(@NotBlank String url) { }
