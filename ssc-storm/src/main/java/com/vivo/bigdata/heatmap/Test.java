package com.vivo.bigdata.heatmap;

public class Test {



    private static String reqUrl  = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyDRS3eCK-z7KYRrgfvFHycg5LVo-wAPaZ8";

//
//
//
//      homeMobileCountryCode: The mobile country code (MCC) for the device's home network.
//      homeMobileNetworkCode: The mobile network code (MNC) for the device's home network.
//      radioType: The mobile radio type. Supported values are lte, gsm, cdma, and wcdma. While this field is optional, it should be included if a value is available, for more accurate results.
//      carrier: The carrier name.
//      considerIp: Specifies whether to fall back to IP geolocation if wifi and cell tower signals are not available. Defaults to true. Set considerIp to false to disable fall back.
//      cellTowers: An array of cell tower objects. See the Cell Tower Objects section below.
//      wifiAccessPoints: An array of WiFi access point objects. See the WiFi Access Point Objects section below.
//
//		{
//		  "homeMobileCountryCode": 310,
//		  "homeMobileNetworkCode": 410,
//		  "radioType": "gsm",
//		  "carrier": "Vodafone",
//		  "considerIp": "true",
//		  "cellTowers": [
//		    // See the Cell Tower Objects section below.
//		  ],
//		  "wifiAccessPoints": [
//		    // See the WiFi Access Point Objects section below.
//		  ]
//		}
//
//
//

//			cellId (required): Unique identifier of the cell. On GSM, this is the Cell ID (CID); CDMA networks use the Base Station ID (BID). WCDMA networks use the UTRAN/GERAN Cell Identity (UC-Id), which is a 32-bit value concatenating the Radio Network Controller (RNC) and Cell ID. Specifying only the 16-bit Cell ID value in WCDMA networks may return inaccurate results.
//			locationAreaCode (required): The Location Area Code (LAC) for GSM and WCDMA networks. The Network ID (NID) for CDMA networks.
//			mobileCountryCode (required): The cell tower's Mobile Country Code (MCC).
//			mobileNetworkCode (required): The cell tower's Mobile Network Code. This is the MNC for GSM and WCDMA; CDMA uses the System ID (SID).
//			The following optional fields are not currently used, but may be included if values are available.
//
//			age: The number of milliseconds since this cell was primary. If age is 0, the cellId represents a current measurement.
//			signalStrength: Radio signal strength measured in dBm.
//			timingAdvance: The timing advance value.
//
//			An example GSM cell tower object is below.
//			{
//			  "cellTowers": [
//			    {
//			      "cellId": 42,
//			      "locationAreaCode": 415,
//			      "mobileCountryCode": 310,
//			      "mobileNetworkCode": 410,
//			      "age": 0,
//			      "signalStrength": -60,
//			      "timingAdvance": 15
//			    }
//			  ]
//			}
//			An example WCDMA cell tower object is below.
//
//			{
//			  "cellTowers": [
//			    {
//			      "cellId": 21532831,
//			      "locationAreaCode": 2862,
//			      "mobileCountryCode": 214,
//			      "mobileNetworkCode": 7
//			    }
//			  ]
//			}
//			WiFi access point objects
//			The request body's wifiAccessPoints array must contain two or more WiFi access point objects. macAddress is required; all other fields are optional.
//
//			macAddress: (required) The MAC address of the WiFi node. It's typically called a BSS, BSSID or MAC address. Separators must be : (colon).
//			signalStrength: The current signal strength measured in dBm.
//			age: The number of milliseconds since this access point was detected.
//			channel: The channel over which the client is communicating with the access point.
//			signalToNoiseRatio: The current signal to noise ratio measured in dB.
//			An example WiFi access point object is shown below.
//
//			{
//			  "macAddress": "00:25:9c:cf:1c:ac",
//			  "signalStrength": -43,
//			  "age": 0,
//			  "channel": 11,
//			  "signalToNoiseRatio": 0
//			}
//			Geolocation responses
//			A successful geolocation request will return a JSON-formatted response defining a location and radius.
//
//			location: The user’s estimated latitude and longitude, in degrees. Contains one lat and one lng subfield.
//			accuracy: The accuracy of the estimated location, in meters. This represents the radius of a circle around the given location.
//			{
//			  "location": {
//			    "lat": 51.0,
//			    "lng": -0.1
//			  },
//			  "accuracy": 1200.4
//			}

//			Reason	Domain	HTTP Status Code	Description
//			dailyLimitExceeded	usageLimits	403	You have exceeded your daily limit.
//			keyInvalid	usageLimits	400	Your API key is not valid for the Geolocation API. Please ensure that you've included the entire key, and that you've either purchased the API or have enabled billing and activated the API to obtain the free quota.
//			userRateLimitExceeded	usageLimits	403	You have exceeded the request limit that you configured in the Google Cloud Platform Console. This limit is typically set as requests per day, requests per 100 seconds, and requests per 100 seconds per user. This limit should be configured to prevent a single or small group of users from exhausting your daily quota, while still allowing reasonable access to all users. See Capping API Usage to configure these limits.
//			notFound	geolocation	404	The request was valid, but no results were returned.
//			parseError	global	400	The request body is not valid JSON. Refer to the Request Body section for details on each field.


//    curl -d @your_filename.json -H "Content-Type: application/json" -i "https://www.googleapis.com/geolocation/v1/geolocate?key=YOUR_API_KEY"
    public static void main(String[] args) {


    }
}
