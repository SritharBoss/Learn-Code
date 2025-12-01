#include <TM1637Display.h>

const int CLK = D1; //Set the CLK pin connection to the display
const int DIO = D2; //Set the DIO pin connection to the display
const int INP = D3;
int numCounter = 0;

TM1637Display display(CLK, DIO); //set up the 4-Digit Display.

void setup()
{
  pinMode(INP,INPUT);
  display.setBrightness(10); //set the diplay to maximum brightness
  Serial.begin(115200);
}


void loop()
{
  if(digitalRead(INP)==0){
    numCounter=numCounter+1;
    display.showNumberDec(numCounter);
    delay(1000);
  }
  display.showNumberDec(numCounter); //Display the numCounter value;
}