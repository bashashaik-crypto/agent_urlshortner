package com.shortner.agent.url.controller;
import com.shortner.agent.url.service.UrlService; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.net.URI;
@RestController public class RedirectController { private final UrlService service; public RedirectController(UrlService service){this.service=service;} @GetMapping("/{code}") ResponseEntity<Void> redirect(@PathVariable String code){return ResponseEntity.status(302).location(URI.create(service.resolve(code))).build();} }
