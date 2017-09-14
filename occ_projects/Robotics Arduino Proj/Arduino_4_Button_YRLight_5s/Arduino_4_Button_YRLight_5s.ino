const int buttonPin = 3;
const int ledPinY = 2;
const int ledPinR = 4;

int buttonState = 0;

void setup()
{
  pinMode(ledPinR, OUTPUT);
  pinMode(ledPinY, OUTPUT);
  pinMode(buttonPin, INPUT);
}

void loop()
{
  buttonState = digitalRead(buttonPin);
    if(buttonState == LOW){
      digitalWrite(ledPinY, HIGH);
      digitalWrite(ledPinR, HIGH);
      delay(5000);
    }
  digitalWrite(ledPinY, LOW);
  digitalWrite(ledPinR, LOW);
}
