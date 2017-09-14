int sensorValue0 = 0;
float sensor = 0;

void setup()
{
  Serial.begin(9600);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
}

void loop()
{
  sensorValue0 = analogRead(0);
  sensor = (float(sensorValue0)/1024)*5;
  Serial.print("Port A0 Voltage = ");
  Serial.println(sensor);
  if(sensor < 3.5){
    digitalWrite(7, HIGH);
    digitalWrite(8, LOW);
  }
  else 
  {
    digitalWrite(8, HIGH);
    digitalWrite(7, LOW);
  }
  delay(2000);
}
