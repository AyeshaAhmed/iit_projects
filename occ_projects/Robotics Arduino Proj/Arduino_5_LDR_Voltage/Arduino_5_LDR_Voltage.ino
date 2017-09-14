int sensorValue0 = 0;
float sensor = 0;

void setup()
{
  Serial.begin(9600);
}

void loop()
{
  sensorValue0 = analogRead(0);
  sensor = (float(sensorValue0)/1024)*5;
  Serial.print("Port A0 Voltage = ");
  Serial.println(sensor);
  delay(2000);
}
