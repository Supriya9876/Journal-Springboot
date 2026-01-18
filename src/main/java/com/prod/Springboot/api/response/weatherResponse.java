package com.prod.Springboot.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class weatherResponse {

    private Current current = new Current(); //

    @Getter
    @Setter
    public static class Current {  //
        @JsonProperty("temp_c")
        private double temp;

        @JsonProperty("feelslike_c")
        private int feelslike;
    }
}
