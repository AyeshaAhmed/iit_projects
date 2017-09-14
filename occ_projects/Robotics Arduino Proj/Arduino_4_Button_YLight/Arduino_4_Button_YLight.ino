const int buttonPin = 3;
const int ledPin = 2;

int buttonState = 0;

void setup()
{
  pinMode(ledPin, OUTPUT);
  pinMode(buttonPin, INPUT);
}

void loop()
{
  buttonState = digitalRead(buttonPin);
    if(buttonState == LOW){
      digitalWrite(ledPin, HIGH);
    }
  digitalWrite(ledPin, LOW);
}
