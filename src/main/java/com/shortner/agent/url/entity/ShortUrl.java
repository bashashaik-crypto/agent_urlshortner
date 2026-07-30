package com.shortner.agent.url.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "short_urls", indexes = @Index(name = "idx_short_code", columnList = "code", unique = true))
public class ShortUrl {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 2048) private String targetUrl;
    @Column(nullable = false, unique = true, length = 16) private String code;
    @Column(nullable = false) private Instant createdAt = Instant.now();
    @Column(nullable = false) private long clicks;
    @Column(nullable = false) private boolean active = true;
    protected ShortUrl() { }
    public ShortUrl(String targetUrl, String code) { this.targetUrl = targetUrl; this.code = code; }
    public Long getId() { return id; } public String getTargetUrl() { return targetUrl; } public String getCode() { return code; }
    public Instant getCreatedAt() { return createdAt; } public long getClicks() { return clicks; } public boolean isActive() { return active; }
    public void registerClick() { clicks++; } public void deactivate() { active = false; }
}
