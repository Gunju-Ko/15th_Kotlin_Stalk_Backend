package slipp.stalk.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import slipp.stalk.controller.exceptions.MemberNotFoundException;

import javax.validation.ConstraintViolationException;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestApiControllerAdviceTest {

    private MockMvc mvc;

    private Fake fake;

    @Before
    public void setUp() {
        fake = Mockito.mock(Fake.class);
        mvc = MockMvcBuilders.standaloneSetup(new FakeController())
                             .setControllerAdvice(new RestApiControllerAdvice())
                             .build();
    }

    @Test
    public void response_with_statusCode400_when_controller_throwConstraintViolationException() throws Exception {
        doThrow(ConstraintViolationException.class)
            .when(fake).doAction();

        assertStatusCode("/test", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void response_with_statusCode404_when_controller_MemberNotFoundException() throws Exception {
        doThrow(MemberNotFoundException.class)
            .when(fake).doAction();

        assertStatusCode("/test", HttpStatus.NOT_FOUND);
    }

    private void assertStatusCode(String url, HttpStatus status) throws Exception {
        mvc.perform(get(url))
           .andDo(print())
           .andExpect(status().is(status.value()));
    }

    public interface Fake {
        void doAction();
    }

    @RestController
    private class FakeController {

        @GetMapping("/test")
        public ResponseEntity<Void> get() {
            fake.doAction();

            return ResponseEntity.ok()
                                 .build();
        }
    }
}