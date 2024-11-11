package com.shivam.instagram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class IpLocationInfo 
{
    
    @JsonProperty("ip")
    String ip;

    @JsonProperty("city")
    String city ;

    @JsonProperty("region")
    String region ;

    @JsonProperty("region_code")
    String regionCode ;

    @JsonProperty("country_name")
    String countryName ;

    @JsonProperty("country_capital")
    String countryCapital ;

    
    @JsonProperty("country_calling_code")
    String countryCallingCode ;

    @JsonProperty("timezone")
    String timezone ;


    @JsonProperty("currency")
    String currency ;


    @JsonProperty("currency_name")
    String currencyName ;


    @JsonProperty("languages")
    String languages ;

    @JsonProperty("org")
    String networkProvider ;

    

}
