int sensorValue = 0;
int outputValue = 0;

void setup(){
  Serial.begin(9600);
}

void loop(){
  sensorValue = analogRead(A0);
  outputValue = map(sensorValue, 0, 1023, 0, 255);
  analogWrite(9, outputValue);
  
  Serial.print("Potentiometer = ");
  Serial.print(sensorValue);
  Serial.print("\t LED = ");
  Serial.println(outputValue);
  delay(10);
}
