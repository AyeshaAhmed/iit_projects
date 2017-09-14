int sensorValue = 0;
int outputValue = 0;
int leds[] = {9,6,3};
int num = 3;

void setup(){
  Serial.begin(9600);
}

void loop(){
  sensorValue = analogRead(A0);
  outputValue = map(sensorValue, 0, 1023, 0, 255);
  for(int i = 0; i < num; i++){
    analogWrite(leds[i], outputValue);
  }
  
  Serial.print("Potentiometer = ");
  Serial.print(sensorValue);
  Serial.print("\t LED = ");
  Serial.println(outputValue);
  delay(10);
}
