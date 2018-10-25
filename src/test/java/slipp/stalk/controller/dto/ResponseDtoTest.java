package slipp.stalk.controller.dto;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseDtoTest {

    @Test
    public void create_SUCCESS_ResponseDto() {
        TestObject object = new TestObject("test");
        ResponseDto<TestObject> response = ResponseDto.create(object, HttpStatus.OK);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.SUCCESS);
        assertThat(response.getData()).isEqualTo(object);
    }

    @Test
    public void create_FAIL_ResponseDto() {
        TestObject object = new TestObject("test");
        ResponseDto<TestObject> response = ResponseDto.create(object, HttpStatus.NOT_FOUND);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);
        assertThat(response.getData()).isEqualTo(object);
    }

    @Test
    public void fromErrorMessage() {
        String errorMessage = "Exception is occur";
        ResponseDto<Object> response = ResponseDto.fromErrorMessage(errorMessage);
        assertThat(response.getResult()).isEqualTo(ResponseDto.Result.FAIL);
        assertThat(response.getData()).isNull();
        assertThat(response.getErrorMessage()).isEqualTo(errorMessage);
    }

    public static class TestObject {
        String name;

        public TestObject(String name) {
            this.name = name;
        }
    }
}