package com.shortner.agent.url;
import com.shortner.agent.url.entity.ShortUrl; import com.shortner.agent.url.service.UrlService; import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest class UrlServiceTest { @Autowired UrlService service;
 @Test void createsResolvesAndMeasuresClick(){ShortUrl created=service.create("https://example.com/path"); assertEquals("https://example.com/path",service.resolve(created.getCode())); assertEquals(1,service.analytics(created.getCode()).getClicks());}
 @Test void rejectsNonHttpUrl(){assertThrows(IllegalArgumentException.class,()->service.create("javascript:alert(1)"));}
}
