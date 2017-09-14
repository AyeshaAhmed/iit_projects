int sensorValue0 = 0;

void setup()
{
  Serial.begin(9600);
}
void loop()
{
  sensorValue0 = analogRead(0);
  
  Serial.print("Port A0 Voltage = ");
  Serial.println(sensorValue0);
  delay(2000);
}
