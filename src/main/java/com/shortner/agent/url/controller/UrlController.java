package com.shortner.agent.url.controller;
import com.shortner.agent.url.dto.*; import com.shortner.agent.url.entity.ShortUrl; import com.shortner.agent.url.service.UrlService;
import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/urls") public class UrlController {
 private final UrlService service; public UrlController(UrlService service){this.service=service;}
 @PostMapping public ResponseEntity<UrlView> create(@Valid @RequestBody CreateUrlRequest request){return ResponseEntity.status(HttpStatus.CREATED).body(view(service.create(request.url())));}
 @GetMapping("/{code}/analytics") public UrlView analytics(@PathVariable String code){return view(service.analytics(code));}
 @DeleteMapping("/{code}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deactivate(@PathVariable String code){service.deactivate(code);}
 private UrlView view(ShortUrl u){return new UrlView(u.getCode(),u.getTargetUrl(),u.getCreatedAt(),u.getClicks(),u.isActive());}
}
