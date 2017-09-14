void setup()
{
  pinMode(4, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(8, OUTPUT);
}

void loop()
{
  digitalWrite(8, HIGH);
  delay(2000);
  digitalWrite(6, HIGH);
  delay(2000);
  digitalWrite(4, HIGH);
  delay(2000);
  digitalWrite(8, LOW);
  delay(2000);
  digitalWrite(6, LOW);
  delay(2000);
  digitalWrite(4, LOW);
  delay(2000);
}
