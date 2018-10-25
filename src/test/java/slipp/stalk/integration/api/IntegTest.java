package slipp.stalk.integration.api;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import slipp.stalk.controller.dto.ResponseDto;
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

    protected <T> ResponseDto<T> get(String url, Class<T> type) {
        return this.exchange(url, HttpMethod.GET, null, type);
    }

    protected <T> ResponseDto<T> post(String url, Object body, Class<T> type) {
        return this.exchange(url, HttpMethod.POST, body, type);
    }

    protected <T> ResponseDto<T> delete(String url, Object body, Class<T> type) {
        return this.exchange(url, HttpMethod.DELETE, body, type);
    }

    protected <T> ResponseDto<T> put(String url, Object body, Class<T> type) {
        return this.exchange(url, HttpMethod.PUT, body, type);
    }

    private <T> ResponseDto<T> exchange(String url, HttpMethod httpMethod, Object body, Class<T> type) {
        return testRestTemplate.exchange(url, httpMethod, new HttpEntity<>(body), new ParameterizedTypeReference<ResponseDto<T>>() {})
                               .getBody();
    }

    protected <T> ResponseDto<T> postForEntityWithJwtToken(String email, String url, Object body, Class<T> type) {
        return this.exchangeWithJwtToken(email, url, HttpMethod.POST, body, type);
    }

    protected <T> T postForEntityWithJwtToken(String email, String url, Object body, ParameterizedTypeReference<T> reference) {
        return this.exchangeWithJwtToken(email, url, HttpMethod.POST, body, reference);
    }

    protected <T> ResponseDto<T> deleteForEntityWithJwtToken(String email, String url, Object body, Class<T> type) {
        return this.exchangeWithJwtToken(email, url, HttpMethod.DELETE, body, type);
    }

    protected <T> ResponseDto<T> putForEntityWithJwtToken(String email, String url, Object body, Class<T> type) {
        return this.exchangeWithJwtToken(email, url, HttpMethod.PUT, body, type);
    }

    protected <T> T putForEntityWithJwtToken(String email, String url, Object body, ParameterizedTypeReference<T> reference) {
        return this.exchangeWithJwtToken(email, url, HttpMethod.PUT, body, reference);
    }

    protected <T> ResponseDto<T> getForEntityWithJwtToken(String email, String url, Class<T> type) {
        return this.exchangeWithJwtToken(email, url, HttpMethod.GET, type);
    }

    protected <T> T getForEntityWithJwtToken(String email, String url, ParameterizedTypeReference<T> type) {
        return this.exchangeWithJwtToken(email, url, HttpMethod.GET, null, type);
    }

    protected <T> ResponseDto<T> exchangeWithJwtToken(String email, String url, HttpMethod httpMethod, Class<T> type) {
        return this.exchangeWithJwtToken(email, url, httpMethod, null, type);
    }

    protected <T> ResponseDto<T> exchangeWithJwtToken(String email, String url, HttpMethod httpMethod, Object body, Class<T> type) {
        return this.exchangeWithJwtToken(email, url, httpMethod, body, new ParameterizedTypeReference<ResponseDto<T>>() {});
    }

    protected <T> T exchangeWithJwtToken(String email,
                                         String url,
                                         HttpMethod httpMethod,
                                         Object body,
                                         ParameterizedTypeReference<T> reference) {
        JwtToken token = createJwtToKen(email);

        HttpHeaders headers = new HttpHeaders();
        headers.set(jwtProperties.getHeader(), token.getToken());
        //noinspection unchecked
        return testRestTemplate.exchange(url, httpMethod, new HttpEntity<>(body, headers), reference).getBody();
    }

    protected TestRestTemplate restTemplate() {
        return this.testRestTemplate;
    }

    private JwtToken createJwtToKen(String email) {
        Member member = new Member();
        member.setEmail(email);
        return jwtHelper.createToken(member);
    }
}
