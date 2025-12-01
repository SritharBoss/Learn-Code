void setup() {
  // put your setup code here, to run once:
  pinMode(D2,INPUT);
  pinMode(D3,OUTPUT);
  Serial.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(digitalRead(D2)==0){
    digitalWrite(D3,HIGH);
  }else{
    digitalWrite(D3,LOW);
  }
}
