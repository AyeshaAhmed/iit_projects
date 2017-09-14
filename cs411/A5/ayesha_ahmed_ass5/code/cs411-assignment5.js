"use strict";

////////////////////////////////////////////////////////////////////////////////////////
// -->>   Ayesha Ahmed
// cs411 assignment5 (Spring 2015) - lighting
// -->>   BROWSER: Firefox 50.0
/////////////////////////////////////////////////////////////////////////////////////////

var canvas;
var gl;
var buffers;                        // vertex buffers
var model;                          // object model

var lastAnimationTime = Date.now(); // last time tick() was called
var angleStep = 10;                 // increments of rotation angle (degrees)
var fps = 30;                       // frames per second
var currentAngle = 0.0;             // current rotation angle [degree]

//var objName = 'mycube.obj';
var objName = 'cow.obj';

var camZ = 0;
var invertNorm = 0;
var curRot = new Matrix4();
var leftRot = new Matrix4();
var rightRot = new Matrix4();
var upRot = new Matrix4();
var downRot = new Matrix4();
var tmpRot = new Matrix4();
var lightPosX = -0.4;       //added here
var lightPosY = 0.4;
var lightPosZ = 0.9;
var iA = 0.5;
var iD = 1.0;
var iS = 0.3;
var ka = 0.5;
var kd = 1.0;
var ks = 0.3;
var ns = 0.5;
var kc = 1.0;
var kl = 0.0;
var kq = 0.0;
var pauseFlag = 0;

// vertex shader program
var VSHADER_SOURCE =
  'attribute vec4 a_Position;\n' +
  'attribute vec4 a_Color;\n' +
  'attribute vec4 a_Normal;\n' +
  'uniform mat4 u_MvpMatrix;\n' +
  'uniform mat4 u_NormalMatrix;\n' +
  'varying vec4 v_Color;\n' +
  'uniform float u_ik_ambient;\n' +     //added this
  'uniform float u_ik_diffuse;\n' +     //added this
  'uniform float u_ik_specular;\n' +     //added this
  'uniform float u_ns;\n' +     //added this
  'uniform float u_kconstant;\n' +     //added this
  'uniform float u_klinear;\n' +     //added this
  'uniform float u_kquadratic;\n' +     //added this
  'uniform vec3 u_lightDirection;\n' +     //added this

  'void main() {\n' +
  //'  vec3 lightDirection = vec3(-0.35, 0.35, 0.87);\n' +    //took out
  '  gl_Position = u_MvpMatrix * a_Position;\n' +
  '  vec3 normal = normalize(vec3(u_NormalMatrix * a_Normal));\n' +
  '  float nDotL = max(dot(normal, u_lightDirection), 0.0);\n' +      //changed
  '  vec3 r = 2.0*nDotL*(normal - u_lightDirection);\n' +      //added this
  '  float vDotR = max(dot(r, vec3(gl_Position)), 0.0);\n' +      //added this
  '  float diffuse = u_ik_diffuse * nDotL;\n' +      //added this
  '  float specular = u_ik_specular * pow(vDotR, u_ns);\n' +      //added this
  '  float u_Itotal = u_ik_ambient + diffuse + specular;\n' +      //added this
  '  float dist = length(u_lightDirection - vec3(a_Position));\n' +
  '  float lightAttenuation = 1.0/(u_kconstant + u_klinear*dist + u_kquadratic*pow(dist, 2.0));\n' +
  '  float final = u_Itotal * lightAttenuation;\n' +      //added this
  //'  v_Color = vec4(a_Color.rgb * nDotL, a_Color.a);\n' +     //took out
  '  v_Color = vec4(a_Color.rgb * final, a_Color.a);\n' +     //changed
  '}\n';

// fragment shader program
var FSHADER_SOURCE =
  '#ifdef GL_ES\n' +
  'precision mediump float;\n' +
  '#endif\n' +
  'varying vec4 v_Color;\n' +
  'void main() {\n' +
  '  gl_FragColor = v_Color;\n' +
  '}\n';


// event handlers
function zoomin(){
  mvMatrix.scale(2.0,2.0,2.0);
}
function zoomout(){
  mvMatrix.scale(0.5,0.5,0.5)
}
function toggRot() {
  pauseFlag = 1-pauseFlag;
  console.log('pauseFlag = %d', pauseFlag);
}

function lightPosChange(){                                                  //added this
  lightPosX =parseInt(document.getElementById("x").value, 10)/10;
  lightPosY =parseInt(document.getElementById("y").value, 10)/10;
  lightPosZ =parseInt(document.getElementById("z").value, 10)/10;
  console.log('lightPosition: [\nX: %f,\nY: %f,\nZ: %f]', lightPosX, lightPosY, lightPosZ);
  var lightDir = gl.getUniformLocation(gl.program, 'u_lightDirection');
  var lightPos = new Vector3([lightPosX, lightPosY, lightPosZ]);
  lightPos.normalize();
  gl.uniform3fv(lightDir, lightPos.elements);
}

function ikParamChange(){
  iA =parseInt(document.getElementById("iA").value, 10)/10;
  ka =parseInt(document.getElementById("ka").value, 10)/10;
  var ikambient = gl.getUniformLocation(gl.program, 'u_ik_ambient');
  var ikamb = iA*ka;
  gl.uniform1f(ikambient, ikamb);

  iD =parseInt(document.getElementById("iD").value, 10)/10;
  kd =parseInt(document.getElementById("kd").value, 10)/10;
  var ikdiffuse = gl.getUniformLocation(gl.program, 'u_ik_diffuse');
  var ikdif = iD*kd;
  gl.uniform1f(ikdiffuse, ikdif);

  iS =parseInt(document.getElementById("iS").value, 10)/10;
  ks =parseInt(document.getElementById("ks").value, 10)/10;
  var ikspecular = gl.getUniformLocation(gl.program, 'u_ik_specular');
  var ikspc = iS*ks;
  gl.uniform1f(ikspecular, ikspc);

  ns =parseInt(document.getElementById("ns").value, 10)/10;
  var _ns = gl.getUniformLocation(gl.program, 'u_ns');
  gl.uniform1f(_ns, ns);

  console.log('lightParams: [\nIa: %f,\nId: %f,\nIs: %f]', iA, iD, iS);
  console.log('materialParams: [\nka: %f,\nkd: %f,\nks: %f]', ka, kd, ks);
  console.log('Shininess: %f', ns);
}

function lightAttenChange(){
  kc =parseInt(document.getElementById("kc").value, 10)/10;
  kl =parseInt(document.getElementById("kl").value, 10)/10;
  kq =parseInt(document.getElementById("kq").value, 10)/10;
  var kcnst = gl.getUniformLocation(gl.program, 'u_kconstant');
  var klin = gl.getUniformLocation(gl.program, 'u_klinear');
  var kquad = gl.getUniformLocation(gl.program, 'u_kquadratic');
  console.log('Attenuation Coeff: [\nkc: %f,\nkl: %f,\nkq: %f]', kc, kl, kq);
  gl.uniform1f(kcnst, kc);
  gl.uniform1f(klin, kl);
  gl.uniform1f(kquad, kq);
}

// create a buffer object, assign it to attribute variable, and enable the assignment
function createEmptyArrayBuffer(gl, a_attribute, num, type)
{
  var buffer =  gl.createBuffer();  // Create a buffer object
  if (!buffer) {
    console.log('Failed to create the buffer object');
    return null;
  }
  gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
  gl.vertexAttribPointer(a_attribute, num, type, false, 0, 0);  // Assign the buffer object to the attribute variable
  gl.enableVertexAttribArray(a_attribute);  // Enable the assignment

  return buffer;
}


function initVertexBuffers(gl, program)
{
  var o = new Object(); // create new object. Utilize Object object to return multiple buffer objects
  o.vertexBuffer = createEmptyArrayBuffer(gl, program.a_Position, 3, gl.FLOAT);
  o.normalBuffer = createEmptyArrayBuffer(gl, program.a_Normal, 3, gl.FLOAT);
  o.colorBuffer = createEmptyArrayBuffer(gl, program.a_Color, 4, gl.FLOAT);
  o.indexBuffer = gl.createBuffer();
  if (!o.vertexBuffer || !o.normalBuffer || !o.colorBuffer || !o.indexBuffer) { return null; }

  gl.bindBuffer(gl.ARRAY_BUFFER, null);

  return o;
}


function assignVertexBuffersData(gl, buffers, model)
{
  // write date into the buffer objects
  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.vertexBuffer);
  gl.bufferData(gl.ARRAY_BUFFER, model.arrays.vertices, gl.STATIC_DRAW);

  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.normalBuffer);
  gl.bufferData(gl.ARRAY_BUFFER, model.arrays.normals, gl.STATIC_DRAW);

  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.colorBuffer);
  gl.bufferData(gl.ARRAY_BUFFER, model.arrays.colors, gl.STATIC_DRAW);

  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, buffers.indexBuffer);
  gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, model.arrays.indices, gl.STATIC_DRAW);
}


function getShaderVariables(program)
{
  //get the storage locations of attribute and uniform variables
  program.a_Position = gl.getAttribLocation(program, 'a_Position');
  program.a_Normal = gl.getAttribLocation(program, 'a_Normal');
  program.a_Color = gl.getAttribLocation(program, 'a_Color');
  program.u_MvpMatrix = gl.getUniformLocation(program, 'u_MvpMatrix');
  program.u_NormalMatrix = gl.getUniformLocation(program, 'u_NormalMatrix');

  if (program.a_Position < 0 ||  program.a_Normal < 0 || program.a_Color < 0 ||
      !program.u_MvpMatrix || !program.u_NormalMatrix) {
    console.log('attribute, uniform');
    return false;
  }
  return true;
}


function printModelInfo(model)
{
  console.log("number of vertices=%d",model.arrays.vertices.length/3);
  console.log("number of normals=%d",model.arrays.normals.length/3);
  console.log("number of colors=%d",model.arrays.colors.length/4);
  console.log("nummer of faces=%d",model.arrays.indices.length/3);

  for(var i=0;i<10 && i< model.arrays.vertices.length; i++){
    console.log("v[%d]=(%f,%f,%f)",i,
      model.arrays.vertices[i*3+0],
      model.arrays.vertices[i*3+1],
      model.arrays.vertices[i*3+2]);
  }
  for(var i=0;i<10 && i< model.arrays.vertices.length; i++){
    console.log("n[%d]=(%f,%f,%f)",i,
      model.arrays.normals[i*3+0],
      model.arrays.normals[i*3+1],
      model.arrays.normals[i*3+2]);
  }
  for(var i=0;i<10 && i< model.arrays.indices.length; i++){
    console.log("f[%d]=(%d,%d,%d)",i,
      model.arrays.indices[i*3+0],
      model.arrays.indices[i*3+1],
      model.arrays.indices[i*3+2]);
  }
}


function initScene()
{
  // set the clear color and enable the depth test
  gl.clearColor(0.2, 0.2, 0.2, 1.0);
  gl.enable(gl.DEPTH_TEST);

  // select the viewport
  gl.viewportWidth = canvas.width;
  gl.viewportHeight = canvas.height;
  gl.viewport(0, 0, gl.viewportWidth, gl.viewportHeight);

  // set the projection matrix
  pMatrix.setPerspective(30.0, canvas.width/canvas.height, 1.0, 5000.0);

  // set the modelview matrix
  mvMatrix.setIdentity(); // erase all prior transformations
  mvMatrix.lookAt(0.0, 500.0, 200.0,   0.0, 0.0, 0.0,   0.0, 1.0, 0.0);

  // start reading the OBJ file
  model = new Object();
  var scale=60; // 1
  readOBJFile(objName, gl, model, scale, true); // cube.obj

  // init rotation matrices
  curRot.setIdentity();
  leftRot.setRotate ( 5, 0,1,0);
  rightRot.setRotate(-5, 0,1,0);
  upRot.setRotate   (-5, 0,0,1);
  downRot.setRotate ( 5, 0,0,1);

}


function drawScene(gl, program, angle, buffers, model)
{
  // get model arrays if necessary
  if (!model.arrays){
    if(isOBJFileLoaded(model)){
      extractOBJFileArrays(model);
      assignVertexBuffersData(gl, buffers, model);
      printModelInfo(model);
    }
    if (!model.arrays) return;   // drawing failed
  }

  // clear canvas
  gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);  // Clear color and depth buffers

  // perform modeling transformations (rotate)
  mvPushMatrix();
  mvMatrix.rotate(angle, 1.0, 0.0, 0.0); // about x
  mvMatrix.rotate(angle, 0.0, 1.0, 0.0); // about y
  mvMatrix.rotate(angle, 0.0, 0.0, 1.0); // about z

  // set the normal matrix
  nMatrix.setInverseOf(mvMatrix);
  nMatrix.transpose();
  gl.uniformMatrix4fv(program.u_NormalMatrix, false, nMatrix.elements);

  // compute the combined transformation matrix
  mvpMatrix.set(pMatrix);
  mvpMatrix.multiply(mvMatrix);
  gl.uniformMatrix4fv(program.u_MvpMatrix, false, mvpMatrix.elements);
  mvPopMatrix();

  // draw
  gl.drawElements(gl.TRIANGLES, model.arrays.indices.length, gl.UNSIGNED_SHORT, 0);

}


function animate(angle)
{
  var now = Date.now();
  var elapsed = now - lastAnimationTime;
  if(elapsed < 1000/fps) return angle;
  lastAnimationTime = now;
  // update the current rotation angle (adjusted by elapsed time)
  var newAngle = angle + (angleStep * elapsed) / 1000.0;
  return newAngle % 360;
}


function tick()
{
  if(!pauseFlag){
	  currentAngle = animate(currentAngle); // update current rotation angles
  }
  drawScene(gl, gl.program, currentAngle, buffers, model);
  requestAnimationFrame(tick, canvas);
}


function main()
{
  // retrieve the <canvas> element
  canvas = document.getElementById('webgl');

  // get rendering context for WebGL
  gl = getWebGLContext(canvas);
  if (!gl) {
    console.log('Failed to get the rendering context for WebGL');
    return;
  }

  // initialize shaders
  if (!initShaders(gl, VSHADER_SOURCE, FSHADER_SOURCE)) {
    console.log('Failed to intialize shaders.');
    return;
  }

  // get storage locations of attribute and uniform variables
  var program = gl.program;
  if(!getShaderVariables(program)){
    console.log('error locating shader variables');
    return;
  }

  // prepare empty buffer objects
  buffers = initVertexBuffers(gl, program);
  if (!buffers) {
    console.log('Failed to set the vertex information');
    return;
  }

  lightPosChange();
  ikParamChange();
  lightAttenChange();

  // set button event listeners
  // took out

  // initialize the scene and start animation
  initScene();
  tick();
}


// EOF
