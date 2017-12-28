package se.cag.camel;

public class OpenNotifyIssPositionBean {
  private String timestamp;
  private String message;
  private Position iss_position;

  public OpenNotifyIssPositionBean() {}

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Position getIss_position() {
    return iss_position;
  }

  public void setIss_position(Position iss_position) {
    this.iss_position = iss_position;
  }

  public static class Position {
    private String longitude;
    private String latitude;

    public String getLongitude() {
      return longitude;
    }

    public void setLongitude(String longitude) {
      this.longitude = longitude;
    }

    public String getLatitude() {
      return latitude;
    }

    public void setLatitude(String latitude) {
      this.latitude = latitude;
    }
  }
}
