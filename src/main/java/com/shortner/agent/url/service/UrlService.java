package com.shortner.agent.url.service;
import com.shortner.agent.url.entity.ShortUrl; import com.shortner.agent.url.repository.ShortUrlRepository;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.net.URI; import java.security.SecureRandom; import java.util.NoSuchElementException;
@Service public class UrlService {
 private static final String ALPHABET="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; private final ShortUrlRepository repository; private final SecureRandom random=new SecureRandom();
 public UrlService(ShortUrlRepository repository){this.repository=repository;}
 @Transactional public ShortUrl create(String targetUrl){validate(targetUrl);String code;do{code=randomCode();}while(repository.findByCode(code).isPresent());return repository.save(new ShortUrl(targetUrl,code));}
 @Transactional public String resolve(String code){ShortUrl url=repository.findByCode(code).filter(ShortUrl::isActive).orElseThrow(()->new NoSuchElementException("Short URL not found or inactive"));url.registerClick();return url.getTargetUrl();}
 @Transactional(readOnly=true) public ShortUrl analytics(String code){return repository.findByCode(code).orElseThrow(()->new NoSuchElementException("Short URL not found"));}
 @Transactional public void deactivate(String code){repository.findByCode(code).orElseThrow(()->new NoSuchElementException("Short URL not found")).deactivate();}
 private String randomCode(){StringBuilder value=new StringBuilder(8);for(int i=0;i<8;i++)value.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));return value.toString();}
 private void validate(String raw){try{URI uri=URI.create(raw);if(!("http".equalsIgnoreCase(uri.getScheme())||"https".equalsIgnoreCase(uri.getScheme()))||uri.getHost()==null)throw new IllegalArgumentException();}catch(Exception e){throw new IllegalArgumentException("url must be an absolute http(s) URL");}}
}
