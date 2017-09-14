void setup()
{
  pinMode(9, OUTPUT);
}

int brightness = 0;
int increment = 1;

void loop()
{
  if(brightness > 255){
    increment = -1;
  }
  else if(brightness < 1){
    increment = 1;
  }
  brightness = brightness + increment;
  
  analogWrite(9, brightness);
  delay(10);
}
