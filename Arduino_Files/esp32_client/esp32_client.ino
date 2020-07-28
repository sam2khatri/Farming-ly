#include <WiFi.h>
#include <HTTPClient.h>

const char* ssid = "ESP8266-Access-Point";
const char* password ="123456789";

int ldr=3;


String ldr_status;
String payLoad;



void setup() {
  
  pinMode(ldr,INPUT);
  Serial.begin(115200);
  delay(2000);   //Delay needed before calling the WiFi.begin
  
  WiFi.begin(ssid, password); 
  
  while (WiFi.status() != WL_CONNECTED) { //Check for the connection
    delay(1000);
    Serial.println("Connecting to WiFi..");
  }
  
  Serial.println("Connected to the WiFi network : "+(String)ssid);
} 
void readLDRStatus(){
  if (digitalRead(ldr)==HIGH)
    {
     ldr_status="ldrstatus=No Light";
    }   
  else
    { 
    ldr_status="ldrstatus=Light";
    }
}
void generatePayload()
{
  readLDRStatus();
  
  payLoad=ldr_status;  
  Serial.println(payLoad);
}

void loop() {
  generatePayload();
 if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
   HTTPClient http;   

   http.begin("http://192.168.4.1/sensor");  //Specify destination for HTTP request
   
   http.addHeader("Content-Type", "application/x-www-form-urlencoded");             //Specify content-type header
   int httpResponseCode = http.POST(payLoad);                             //Send the actual POST request
  
   if(httpResponseCode==200){
  
    //String response = http.getString();                       //Get the response to the request
  
    Serial.println(httpResponseCode);   //Print return code
    //Serial.println(response);           //Print request answer
  
   }
   else
   {
    Serial.print("Error on sending POST: ");
    Serial.println(httpResponseCode);  
   }
  
   http.end();  //Free resources
  
 }else{
  
    Serial.println("Error in WiFi connection");   
  
 }
  delay(20000);  //Send a request every 10 seconds
  
}
