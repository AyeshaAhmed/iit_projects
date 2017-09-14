void setup()
{
  pinMode(9, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(3, OUTPUT);
}

int brightness = 0;
int increment = 1;

void loop()
{
  if(brightness > 50){
    increment = -1;
  }
  else if(brightness < 1){
    increment = 1;
  }
  brightness = brightness + increment;
  
  analogWrite(9, brightness);
  analogWrite(6, brightness);
  analogWrite(3, brightness);
  delay(10);
}
