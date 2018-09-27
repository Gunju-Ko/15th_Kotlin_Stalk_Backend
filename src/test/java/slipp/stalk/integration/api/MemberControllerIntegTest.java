package slipp.stalk.integration.api;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import slipp.stalk.controller.dto.CreateMemberDto;
import slipp.stalk.controller.dto.MemberDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MemberControllerIntegTest extends IntegTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_create_new_member() throws Exception {
        String email = "gunju@slipp.com";
        CreateMemberDto body = createBody(email);

        String path = createResource("/members", body);

        ResponseEntity<MemberDto> response = getForEntityWithJwtToken(email, path, MemberDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(body.getName());
        assertThat(responseBody.getEmail()).isEqualTo(body.getEmail());

        deleteResource(email, path);
        assertHttpStatusCode(email, path, HttpStatus.NOT_FOUND);
    }

    @Test
    public void should_return_409_when_email_isAlready_exist() throws Exception {
        CreateMemberDto body = createBody("gunjuko92@gmail.com");
        ResponseEntity<Void> response = restTemplate.postForEntity("/members", body, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    private String createResource(String path, Object body) {
        ResponseEntity<Void> response = restTemplate.postForEntity(path, body, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getHeaders().getLocation().getPath();
    }

    private void deleteResource(String email, String path) {
        ResponseEntity<Void> response = deleteForEntityWithJwtToken(email, path, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private void assertHttpStatusCode(String email, String path, HttpStatus status) {
        ResponseEntity<MemberDto> response = getForEntityWithJwtToken(email, path, MemberDto.class);
        assertThat(response.getStatusCode()).isEqualTo(status);
    }

    private CreateMemberDto createBody(String email) {
        CreateMemberDto body = new CreateMemberDto();
        body.setPassword("password");
        body.setName("hi");
        body.setEmail(email);
        return body;
    }
}
