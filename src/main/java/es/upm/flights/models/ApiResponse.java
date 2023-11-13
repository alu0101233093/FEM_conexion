package es.upm.flights.models;

public class ApiResponse {
    private ResponseData response;

    public ApiResponse(ResponseData response) {
        this.response = response;
    }

    public ResponseData getResponse() {
        return response;
    }

    public void setResponse(ResponseData response) {
        this.response = response;
    }
}
