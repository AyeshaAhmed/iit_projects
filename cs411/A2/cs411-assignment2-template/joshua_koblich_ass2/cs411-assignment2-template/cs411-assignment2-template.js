"use strict";

/////////////////////////////////////////////////////////////////////////////////////////
//
// cs411 assignment 2 (Fall 2016) - 2d modeling and viewing
// Joshua Koblich
// Written and tested using Google Chrome 53.0.2785.116 and Firefox Version 48.0.2
//
/////////////////////////////////////////////////////////////////////////////////////////

// global variables
var canvas;
var gl;
var lastAnimationTime = Date.now();
var fps=30;
var u_ModelMatrix;
var u_FragColor;
var a_Position;
var vertices;
var n;
var pointBuffer;

var speed=0.1;
var angSpeed=1; 
var renderMode=0;
var pauseFlag=0;
var rotateFlag=0;
var boardW=10.0;          // board width
var boardH=10.0;          // board height
var curPosX=0,curPosY=0;  // current position of object
var curRotAngle = 0;      // current rotation of object
var dX,dY;                // currect direction of motion (unit vector)
var past = [];            // store past path points


// Vertex shader program
var VSHADER_SOURCE =
  'attribute vec4 a_Position;\n' +
  'uniform mat4 u_ModelMatrix;\n' +
  'void main() {\n' +
  '  gl_Position = u_ModelMatrix * a_Position;\n' +
  '  gl_PointSize = 3.0;\n' +
  '}\n';

// Fragment shader program
var FSHADER_SOURCE =
  'precision mediump float;\n' +
  'uniform vec4 u_FragColor;\n' +
  'void main() {\n' +
  '  gl_FragColor = u_FragColor;\n' + 
  '}\n';


// button event handlers
function speedUp() {
  speed *= 2;
  angSpeed += 1;
  console.log('speed = %f, angSpeed = %f', speed, angSpeed);
}

function speedDown() {
  speed /= 2;
  angSpeed -= 1;
  if (speed<0.0001) speed=0.0001;
  if (angSpeed<1) angSpeed=1;
  console.log('speed = %f, angSpeed = %f', speed, angSpeed);
}

function zoomIn() {
boardW = 0.5* boardW;
boardH = 0.5* boardH;
mvMatrix.scale(2, 2, 2);
}

function zoomOut() {
boardW = 2* boardW;
boardH = 2* boardH;
mvMatrix.scale(0.5, 0.5, 0.5);
}

function toggleRenderMode() {
  renderMode = 1-renderMode;
  console.log('renderMode = %d', renderMode);
}

function togglePause() {
  pauseFlag = 1-pauseFlag;
  console.log('pauseFlag = %d', pauseFlag);
}

function toggleRotation() {
  rotateFlag = 1-rotateFlag;
  console.log('rotateFlag = %d', rotateFlag);
}


function initVertexBuffers(gl) 
{
//////////////////////////////////////////////////////////////////////////////////
////////////////////////// DO NOT CHANGE THIS OBJECT /////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
  vertices = new Float32Array(
    [0,    0.3,   
    -0.3, -0.3,   
     0.3, -0.3,
     0.0,  -0.1]); // CM
  var n = 3; // The number of vertices

  // Create a buffer object
  var vertexBuffer = gl.createBuffer();
  if (!vertexBuffer) {
    console.log('Failed to create the buffer object');
    return false;
  }

  // Bind the buffer object to target
  gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);
  // Write data into the buffer object
  gl.bufferData(gl.ARRAY_BUFFER, vertices, gl.STATIC_DRAW);

  a_Position = gl.getAttribLocation(gl.program, 'a_Position');
  if (a_Position < 0) {
    console.log('Failed to get the storage location of a_Position');
    return -1;
  }
  // Assign the buffer object to a_Position variable
  gl.vertexAttribPointer(a_Position, 2, gl.FLOAT, false, 0, 0);

  // Enable the assignment to a_Position variable
  gl.enableVertexAttribArray(a_Position);

  return n;
}


function initScene(gl,u_ModelMatrix,u_FragColor,n)
{
  // select the viewport
  gl.viewport(0, 0, gl.viewportWidth, gl.viewportHeight);
 
  // reset the modelview matrix
  mvMatrix.setIdentity(); // erase all prior transformations

  // select the view window (projection camera)
  var left=-boardW/2.0, right=boardW/2.0, bottom=-boardH/2.0, top=boardH/2.0, near=0, far=10;
  pMatrix.setIdentity();
  pMatrix.ortho(left,right,bottom,top,near,far);
  mvMatrix.multiply(pMatrix);

  // set the camera position and orientation (viewing transformation)
  var eyeX=0, eyeY=0, eyeZ=10;
  var centerX=0, centerY=0, centerZ=0;
  var upX=0, upY=1, upZ=0;
  mvMatrix.lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
}





function drawScene(gl,u_ModelMatrix,u_FragColor,n)
{

  // clear canvas
  gl.clearColor(0, 0, 0, 1);
  gl.clear(gl.COLOR_BUFFER_BIT);

  mvPushMatrix();
  
  mvMatrix.translate(curPosX, curPosY, 0);
  mvMatrix.rotate(curRotAngle, 0, 0, 1);
  
  // apply the transformation
  gl.uniformMatrix4fv(u_ModelMatrix, false, mvMatrix.elements);

  // draw the object
  gl.bufferData(gl.ARRAY_BUFFER, vertices, gl.STATIC_DRAW); // copy the vertices
  gl.uniform4f(u_FragColor, 1,0,0,1);
  gl.drawArrays(gl.TRIANGLES, 0, 3);  // draw the triangle
  gl.uniform4f(u_FragColor, 1,1,1,1);
  gl.drawArrays(gl.POINTS, 3, 1);     // draw the CM

  mvPopMatrix();
  gl.uniformMatrix4fv(u_ModelMatrix, false, mvMatrix.elements);

  // draw the path as points in immediate mode
  if(renderMode==1){
	var pointBuffer = gl.createBuffer();
    if (!pointBuffer) {
      console.log('Failed to create the buffer object');
      return false;
    }
	
	var num = past.length;
	var pastPath = new Float32Array(past)
    // Write data into the buffer object
    gl.bufferData(gl.ARRAY_BUFFER, pastPath, gl.STATIC_DRAW);

    a_Position = gl.getAttribLocation(gl.program, 'a_Position');
    if (a_Position < 0) {
      console.log('Failed to get the storage location of a_Position');
     return -1;
    }
    // Assign the buffer object to a_Position variable
    gl.vertexAttribPointer(a_Position, 2, gl.FLOAT, false, 0, 0);

    // Enable the assignment to a_Position variable
    gl.uniform4f(u_FragColor, 1, 1, 0, 1);
	gl.drawArrays(gl.POINTS, 0, num/2); 
  }
}


function animate() 
{
  // Calculate the elapsed time
  var now = Date.now();
  var elapsed = now - lastAnimationTime;
  if(elapsed < 1000/fps) return;

  // record the current time
  lastAnimationTime = now;

  // compute new angle
  if (!rotateFlag){
    curRotAngle += angSpeed;
    if (curRotAngle >360) curRotAngle-=360;
  }
  else if (rotateFlag){
	curRotAngle += 0;
	if (curRotAngle >360) curRotAngle-=360;
  }
  
  // compute new position
  curPosX += speed * dX; 
  curPosY += speed * dY; 

  if (curPosX < -boardW/2.0){ // left intersection
    curPosX = -boardW/2.0;
    dX *= -1;
  }

  if (curPosX> boardW/2.0){ // right intersection
    curPosX = boardW/2.0;
    dX *= -1;
  }

  if (curPosY < -boardH/2.0){ // bottom intersection
    curPosY = -boardH/2.0;
    dY *= -1;
  }

  if (curPosY > boardH/2.0){ // top intersection
    curPosY = boardH/2.0;
    dY *= -1;
  }

  past.push(curPosX);  // record past path
  past.push(curPosY);
}


function tick()
{
  if (!pauseFlag) animate();                                   // update position and rotation angle
  drawScene(gl,u_ModelMatrix,u_FragColor,n);   // draw the object
  requestAnimationFrame(tick, canvas);         // request a new animation frame
}


function main() 
{
  // Retrieve <canvas> element
  canvas = document.getElementById('webgl');

  // Get the rendering context for WebGL
  gl = getWebGLContext(canvas);
  if (!gl) {
    console.log('Failed to get the rendering context for WebGL');
    return;
  }

  // get canvas height/width
  gl.viewportWidth = canvas.width;
  gl.viewportHeight = canvas.height;
  console.log(gl.viewportWidth);
  console.log(gl.viewportHeight);

  // Initialize shaders
  if (!initShaders(gl, VSHADER_SOURCE, FSHADER_SOURCE)) {
    console.log('Failed to intialize shaders.');
    return;
  }

  // Write the positions of vertices to a vertex shader
  n = initVertexBuffers(gl);
  if (n < 0) {
    console.log('Failed to set the positions of the vertices');
    return;
  }

  // get pointers to shader uniform variables 
  u_ModelMatrix = gl.getUniformLocation(gl.program, 'u_ModelMatrix');
  if (!u_ModelMatrix) {
    console.log('Failed to get the storage location of u_ModelMatrix');
    return;
  }

  u_FragColor = gl.getUniformLocation(gl.program, 'u_FragColor');
  if (!u_FragColor) {
    console.log('Failed to get the storage location of u_FragColor');
    return;
  }


  // set button listeners
  var speedUpBtn = document.getElementById('speedUpButton');
  speedUpBtn.addEventListener('click', speedUp);

  var speedDownBtn = document.getElementById('speedDownButton');
  speedDownBtn.addEventListener('click', speedDown);

  var zoomInBtn = document.getElementById('zoomInButton');
  zoomInBtn.addEventListener('click', zoomIn);

  var zoomOutBtn = document.getElementById('zoomOutButton');
  zoomOutBtn.addEventListener('click', zoomOut);

  var renderModeBtn = document.getElementById('renderModeButton');
  renderModeBtn.addEventListener('click', toggleRenderMode);

  var pauseBtn = document.getElementById('pauseButton');
  pauseBtn.addEventListener('click', togglePause);
  
   var rotateBtn = document.getElementById('rotateButton');
  rotateBtn.addEventListener('click', toggleRotation);

  // set path angle
  var pathBaseAngle = 30;
  dX = Math.cos(Math.PI * pathBaseAngle / 180.0);
  dY = Math.sin(Math.PI * pathBaseAngle / 180.0);

  // start animation and draw
  initScene(gl,u_ModelMatrix,u_FragColor,n);
  tick();
}





