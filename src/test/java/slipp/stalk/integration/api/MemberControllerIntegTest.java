package slipp.stalk.integration.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import slipp.stalk.controller.dto.CreateMemberDto;
import slipp.stalk.controller.dto.MemberDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerIntegTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_create_new_member() throws Exception {
        CreateMemberDto body = createBody("slipp");
        String path = createResource("/members", body);

        ResponseEntity<MemberDto> response = restTemplate.getForEntity(path, MemberDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMemberId()).isEqualTo(body.getMemberId());
        assertThat(responseBody.getName()).isEqualTo(body.getName());
        assertThat(responseBody.getEmail()).isEqualTo(body.getEmail());

        deleteResource(path);

        assertHttpStatusCode(path, HttpStatus.NOT_FOUND);
    }

    @Test
    public void should_return_409_when_memberId_isAlready_exist() throws Exception {
        CreateMemberDto body = createBody("gunju");
        ResponseEntity<Void> response = restTemplate.postForEntity("/members", body, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    private String createResource(String path, Object body) {
        ResponseEntity<Void> response = restTemplate.postForEntity(path, body, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        return response.getHeaders().getLocation().getPath();
    }

    private void deleteResource(String path) {
        ResponseEntity<Void> response = restTemplate.exchange(path, HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private void assertHttpStatusCode(String path, HttpStatus status) {
        ResponseEntity<MemberDto> response = restTemplate.getForEntity(path, MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(status);
    }

    private CreateMemberDto createBody(String memberId) {
        CreateMemberDto body = new CreateMemberDto();
        body.setMemberId(memberId);
        body.setPassword("password");
        body.setName("hi");
        body.setEmail(memberId + "@naver.com");
        return body;
    }
}
