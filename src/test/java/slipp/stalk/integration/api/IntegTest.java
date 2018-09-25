package slipp.stalk.integration.api;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import slipp.stalk.domain.Member;
import slipp.stalk.service.security.JwtHelper;
import slipp.stalk.service.security.JwtProperties;
import slipp.stalk.service.security.JwtToken;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtHelper jwtHelper;

    protected <T> ResponseEntity<T> exchangeWithJwtToken(String email, String url, HttpMethod httpMethod, Object body, Class<T> type) {
        JwtToken token = createJwtToKen(email);

        HttpHeaders headers = new HttpHeaders();
        headers.set(jwtProperties.getHeader(), token.getToken());
        return testRestTemplate.exchange(url, httpMethod, new HttpEntity<>(body, headers), type);
    }

    protected <T> ResponseEntity<T> exchangeWithJwtToken(String email, String url, HttpMethod httpMethod, Class<T> type) {
        return this.exchangeWithJwtToken(email, url, httpMethod, null, type);
    }

    private JwtToken createJwtToKen(String email) {
        Member member = new Member();
        member.setEmail(email);
        return jwtHelper.createToken(member);
    }
}
