package es.upm.flights.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Airport {
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("iata_code")
    @Expose
    private String iataCode;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city_code")
    @Expose
    private String cityCode;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("popularity")
    @Expose
    private Integer popularity;

    public Airport(){}

    public Airport(String iataCode, String name){
        this.iataCode = iataCode;
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return
                "Nombre: " + name + '\n' +
                "Código de país: " + countryCode + '\n' +
                "Código de aeropuerto: " + iataCode + '\n' +
                "Ciudad: " + city + '\n' +
                "Zona horaria: " + timezone + '\n' +
                "Código de ciudad: " + cityCode + '\n' +
                "Slug: " + slug + '\n' +
                "Latitud: " + lat + '\n' +
                "Longitud: " + lng + '\n' +
                "Popularidad: " + popularity;
    }
}