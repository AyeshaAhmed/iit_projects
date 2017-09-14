void setup()
{
  Serial.begin(9600);
}

void loop()
{
  Serial.println(digitalRead(3));
  delay(250);
}
