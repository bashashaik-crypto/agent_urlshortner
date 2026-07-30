package com.shortner.agent.url.dto;
import java.time.Instant;
public record UrlView(String code, String targetUrl, Instant createdAt, long clicks, boolean active) { }
