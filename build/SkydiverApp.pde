Skydiver s;
float lastFrame = 0;
float simulationSpeed =1.;  // real time is simulationSpeed = 1 

void setup()
{
  size(200,1020);
  s = new Skydiver(2000, 500, 80);
  frameRate(100);
}

void draw()
{
  background(255);
  
  float thisFrame = millis();
 
  float dt = (thisFrame - lastFrame)/1000.;
 
  lastFrame = thisFrame;
  
  s.move(simulationSpeed*dt);
  s.display();
  
 

}
