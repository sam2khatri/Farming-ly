#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

ESP8266WebServer server(80);
const char* ssid="ESP8266-Access-Point";
const char* password="123456789";

void setup(){
  Serial.begin(115200);
  Serial.print("Setting AP (Access Point)…");
  // Remove the password parameter, if you want the AP (Access Point) to be open
  WiFi.softAP(ssid,password);

  IPAddress IP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(IP);

  // Print ESP8266 Local IP Address
  Serial.println(WiFi.localIP());

  server.on("/",handleGenericArgs);
  server.on("/sensor",handleSpecificArg);
  //server.onNotFound(handleNotFound);           // When a client requests an unknown URI (i.e. something other than "/"), call function "handleNotFound"

  server.begin();                            // Actually start the server
  Serial.println("HTTP server started");
}

void loop(){
  server.handleClient();                     // Listen for HTTP requests from clients
}

void handleGenericArgs(){
  String message = "Number of args received: ";
  message+=server.args();
  message+="\n";

  for (int i=0;i<server.args();i++)
  {
    message += "Arg no "+(String)i+"->";
    message += server.argName(i)+": ";
    message += server.arg(i)+"\n";
  }
  Serial.println(message);
  server.send(200,"text/plain","Received Request");
}
void handleSpecificArg()
{
  String message="";
  if (server.arg("ldrstatus")==NULL)
   { 
     message="Status unavailable";
     server.send(400,"text/plain",message);
   }
  else
    {
      message="Status received = ";
      message+=server.arg("ldrstatus");  
    }
    Serial.println(message);
    server.send(200,"text/plain","Received status");
}
void handleNotFound(){
  server.send(404, "text/plain", "404: Not found"); // Send HTTP status 404 (Not Found) when there's no handler for the URI in the request
}
