package com.vivo.bigdata;


import com.alibaba.fastjson.JSON;

public class GeocoderRequestBuilder {
    private String address;

    private String language;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static class Builder {
        private String address;

        private String language;

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }


        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }


        public GeocoderRequestBuilder build() {
            GeocoderRequestBuilder geocoderRequestBuilder = new GeocoderRequestBuilder();
            geocoderRequestBuilder.setAddress(address);
            geocoderRequestBuilder.setLanguage(language);
            return geocoderRequestBuilder;
        }

    }


    public static void main(String[] args) {
        GeocoderRequestBuilder request = new GeocoderRequestBuilder.Builder()
                .setAddress("ddd")
                .setLanguage("en")
                .build();
        System.out.println(JSON.toJSONString(request));
    }


}
