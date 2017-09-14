/* 
Code Taken from: 
http://forum.arduino.cc/index.php/topic,119778.0.html
Courtesy of: baselsw
*/

//make an array
int leds[] = {9,6,3};
int num = 3;

void init_leds(){
  for(int i = 0; i<num; i++){
    digitalWrite(leds[i],LOW);//not neccessary - tested
    pinMode(leds[i], OUTPUT);     
  } 
}

void FadeOutLeds(){
  for(int i = 255;i>0;i--){

    for(int j = 0;j<num;j++){
      analogWrite(leds[j],i); 
    }
    delay(10);
  } 

}

void FadeInLed(int theLed){
  for(int i = 0;i<256;i++){
    analogWrite(theLed,i);
    delay(10);
  }
}


void setup() {                
  // initialize the digital pins as an output.
  init_leds();
}

// the loop routine runs over and over again forever:
void loop() {

  for(int i = 0; i<num;i++){
    FadeInLed(leds[i]);      
  }
  delay(1000);               // wait for 1 seconds
  FadeOutLeds();

}
