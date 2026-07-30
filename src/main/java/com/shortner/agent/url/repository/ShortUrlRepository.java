package com.shortner.agent.url.repository;
import com.shortner.agent.url.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> { Optional<ShortUrl> findByCode(String code); }
