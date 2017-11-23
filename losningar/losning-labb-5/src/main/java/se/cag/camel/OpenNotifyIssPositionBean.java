package se.cag.camel;

import lombok.Data;

@Data
public class OpenNotifyIssPositionBean {

    private String timestamp;
    private String message;
    private Position iss_position;

    @Data
    public class Position {
        private String longitude;
        private String latitude;

    }
}