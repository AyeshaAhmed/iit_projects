int leds[] = {9,6,3};
int num = 3;
//setup call methods:
void init_leds(){
  for(int i = 0; i<num; i++){
    pinMode(leds[i], OUTPUT);
  }
}

void FadeIN(int theLed){
  for(int i = 0; i < 255; i++){
    analogWrite(theLed, i);
    delay(10);
  }
}

void FadeOUT(){
 for(int i = 255; i > 0; i--){
   for(int j = 0; j < num; j++){
     analogWrite(leds[j], i);
   }
   delay(10);
 }
}
//initialize LEDs as output:
void setup(){
  init_leds();
}
//start squential loop:
void loop(){
  for(int i = 0; i < num; i++){    //Start sequential fade in loop
    FadeIN(leds[i]);               //using call method
  }
  delay(10);                      //wait for a little
  FadeOUT();                      //fade out using call method
}
