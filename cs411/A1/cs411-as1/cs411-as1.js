"use strict";

/////////////////////////////////////////////////////////////////////////////////////////
//
// cs411 assignment 1 (Fall 2016) - raster graphics
//
/////////////////////////////////////////////////////////////////////////////////////////
//BROWSER: Firefox VERSION: 48.0.2

var ctx;
var imageData;

var pauseFlag=1;
var lineFlag=1;
var triangleFlag=1;
var fillFlag=1;
var multicolorFlag=1;

function togglePause() {
  pauseFlag= 1- pauseFlag;
  console.log('pauseFlag = %d', pauseFlag);
}

function toggleLine() {
  lineFlag= 1- lineFlag;
  console.log('lineFlag = %d', lineFlag);
}

function toggleTriangle() {
  triangleFlag= 1- triangleFlag;
  console.log('triangleFlag = %d', triangleFlag);
}

function toggleFill() {
  fillFlag= 1- fillFlag;
  console.log('fillFlag = %d', fillFlag);
}

//BROWSER: Firefox VERSION: 48.0.2
function toggleMulticolor() {
	  multicolorFlag= 1- multicolorFlag;
	  console.log('multicolorFlag = %d', multicolorFlag);
}


function animate() 
{
  if(!pauseFlag) {
    if (lineFlag) drawRandomLineSegment();
    if (triangleFlag) drawRandomTriangle();
  }
  setTimeout(animate,100); // call animate() in 1000 msec
} 


function initImage(img) 
{
  var canvas = document.getElementById('mycanvas');
  ctx = canvas.getContext('2d');

  ctx.drawImage(img, 0, 0);
  imageData = ctx.getImageData(0,0,canvas.width, canvas.height); // get reference to image data
}


function main()
{
  // load and display image
  var img = new Image();
  img.src = 'data/frac2.png';
  img.onload = function() { initImage(this);}

  // set button listeners
  var grayscalebtn = document.getElementById('grayscaleButton');
  grayscalebtn.addEventListener('click', grayscale);

  var pausebtn = document.getElementById('pauseButton');
  pausebtn.addEventListener('click', togglePause);

  var linebtn = document.getElementById('lineButton');
  linebtn.addEventListener('click', toggleLine);

  var trianglebtn = document.getElementById('triangleButton');
  trianglebtn.addEventListener('click', toggleTriangle);

  var fillbtn = document.getElementById('fillButton');
  fillbtn.addEventListener('click', toggleFill);
  
//BROWSER: Firefox VERSION: 48.0.2
  var multcbtn = document.getElementById('multicolorButton');
  multcbtn.addEventListener('click', toggleMulticolor);

  // start animation
  animate();
}


/////////////////////////////////////////////////////////////////////////////////////////
//
// conversion to grayscale
// 
/////////////////////////////////////////////////////////////////////////////////////////

function grayscale() 
{
  var data = imageData.data;
  for (var i = 0; i < data.length; i += 4) {
    var m = (data[i] + data[i +1] + data[i +2]) / 3;
    data[i]     = m; // red
    data[i + 1] = m; // green
    data[i + 2] = m; // blue
  }
  ctx.putImageData(imageData, 0, 0);
}


/////////////////////////////////////////////////////////////////////////////////////////
//
// draw lines
//
/////////////////////////////////////////////////////////////////////////////////////////


//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// REPLACE THIS WITH YOUR FUNCTION FOLLOWING THE ASSIGNMENT SPECIFICATIONS
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//My function with Bresenham
//BROWSER: Firefox VERSION: 48.0.2
function drawLineSegment(vs,ve,color)
{
	var data = imageData.data;
	var h = imageData.height;
	var w = imageData.width;
	

	var dx = Math.abs(ve[0] -vs[0]); 
	var dy = Math.abs(ve[1] -vs[1]);
	var sx = (vs[0] < ve[0]) ? 1 : -1;//increment direction x
	var sy = (vs[1] < ve[1]) ? 1 : -1;//increment direction y
	// set initial coords
	var x = vs[0];
	var y = vs[1];
    var pk;//used to store error
	
	// ignore illegal lines
	if ((vs[0] <0) || (vs[1] <0) || (ve[0] >= w) || (ve[1] >= h)) return;
	if ((vs[0] == ve[0]) && (vs[1] == ve[1])) return;
	
    if (dx > dy){
        pk = 2*dy - dx;
        while(x != ve[0]){//repeat dx times
        	data[((h-y)*w+x)*4+0]     = color[0]; // red
        	data[((h-y)*w+x)*4+1]     = color[1]; // green
        	data[((h-y)*w+x)*4+2]     = color[2]; // blue
        	
        	x += sx;
        	if (pk < 0){
        		pk += 2*dy;
        	}else {  
        		pk += 2*dy - 2*dx;
        		y += sy;
        	}
        }
     }else{//swap x and y
        pk = 2*dx -dy;
        while (y != ve[1]){//repeat dy times
        	data[((h-y)*w+x)*4+0]     = color[0]; // red
     	   	data[((h-y)*w+x)*4+1]     = color[1]; // green
     	   	data[((h-y)*w+x)*4+2]     = color[2]; // blue
     	   	
     	   	y += sy;
     	   	if (pk < 0){
     	   		pk += 2*dx;
     	   	}
     	   	else {  
     	   		pk += 2*dx - 2*dy;
     	   		x += sx;
     	   	}  
        }
      }

	// update image
	ctx.putImageData(imageData, 0, 0);
}


/*//Original function
function drawLineSegment(vs,ve,color)
{
  var data = imageData.data;
  var h = imageData.height;
  var w = imageData.width;

  var dx = ve[0] -vs[0]; 
  var dy = ve[1] -vs[1]; 
  var m = dy/dx;             // slope 
  var b = vs[1]-m*vs[0];     // y-intercept

  // ignore illegal lines
  if ((vs[0] <0) || (vs[1] <0) || (ve[0] >= w) || (ve[1] >= h)) return;
  if ((vs[0] == ve[0]) && (vs[1] == ve[1])) return;

  // handle nearly horizontal lines
  if(Math.abs(m)<1){
    for (var x = Math.min(vs[0],ve[0]); x <= Math.max(vs[0],ve[0]); x++) {
      var y=Math.round(m*x+b); // compute y coordinate
      var yi=h-y;//invert y coordinate
      data[(yi*w+x)*4+0]     = color[0]; // red
      data[(yi*w+x)*4+1]     = color[1]; // green
      data[(yi*w+x)*4+2]     = color[2]; // blue
    }    
  }

  // handle nearly vertical lines
  else {
    for (var y = Math.min(vs[1],ve[1]); y <= Math.max(vs[1],ve[1]); y++) {
      var x=Math.round((y-b)/m); // compute y coordinate
      var yi=h-y;//invert y coordinate
      data[(yi*w+x)*4+0]     = color[0]; // red
      data[(yi*w+x)*4+1]     = color[1]; // green
      data[(yi*w+x)*4+2]     = color[2]; // blue
    }    
  }

  // update image
  ctx.putImageData(imageData, 0, 0);
}*/


function drawRandomLineSegment()
{
  var h = imageData.height;
  var w = imageData.width;
  
  var xs=Math.floor(Math.random()*w);
  var ys=Math.floor(Math.random()*h);
  var xe=Math.floor(Math.random()*w);
  var ye=Math.floor(Math.random()*h);
  var r=Math.floor(Math.random()*255);
  var g=Math.floor(Math.random()*255);
  var b=Math.floor(Math.random()*255);

  drawLineSegment([xs,ys] ,[xe,ye],[r,g,b]);
}


/////////////////////////////////////////////////////////////////////////////////////////
//
// draw triangles
//
/////////////////////////////////////////////////////////////////////////////////////////


function triangleArea(a,b,c)
{
  var area = ((b[1] - c[1]) * (a[0] - c[0]) + (c[0] - b[0]) * (a[1] - c[1]));
  area = Math.abs(0.5*area);
  return area;
}

function vertexInside(v,v0,v1,v2)
{
  var T = triangleArea(v0,v1,v2);

  var alpha = triangleArea(v,v0,v1) /T ;
  var beta  = triangleArea(v,v1,v2) /T ;
  var gamma = triangleArea(v,v2,v0) /T ;

  if ((alpha>=0) && (beta>=0) && (gamma>=0) && (Math.abs(alpha+beta+gamma -1)<0.00001)) return true;
  else return false;
}

//>>>>>>>>>>>>>> My helper functions <<<<<<<<<<<<<<<<<
//BROWSER: Firefox VERSION: 48.0.2
function sortByY(v1, v2, v3){
	var vTmp = [0,0];
    
    if (v1[1] > v2[1]){
        vTmp = v1;
        v1 = v2;
        v2 = vTmp;
    }
    /* here v1[1] <= v2[1] */
    if (v1[1] > v3[1]){
        vTmp = v1;
        v1 = v3;
        v3 = vTmp;
    }
    /* here v1[1] <= v2[1] and v1[1] <= v3[1] so test v2 vs. v3 */
    if (v2[1] > v3[1]){
        vTmp = v2;
        v2 = v3;
        v3 = vTmp;
    }
    return ([v1[0], v1[1], v2[0], v2[1], v3[0], v3[1]]);
}
function bottomFlatTriangle(v1, v2, v3, color){
	var slope1 = (v2[0] - v1[0]) / (v2[1] - v1[1]);
	var slope2 = (v3[0] - v1[0]) / (v3[1] - v1[1]);

	var curx1 = v1[0];
	var curx2 = v1[0];

	if(multicolorFlag){
		for (var slY = v1[1]; slY <= v2[1]; slY++){
			drawLineSegment([Math.floor(curx1), slY], [Math.floor(curx2), slY], randomColor());
		    curx1 += slope1;
		    curx2 += slope2;
		}
	}else{
		for (var slY = v1[1]; slY <= v2[1]; slY++){
			drawLineSegment([Math.floor(curx1), slY], [Math.floor(curx2), slY], color);
		    curx1 += slope1;
		    curx2 += slope2;
		}
	}
}

function topFlatTriangle(v1, v2, v3, color){
	  var slope1 = (v3[0] - v1[0]) / (v3[1] - v1[1]);
	  var slope2 = (v3[0] - v2[0]) / (v3[1] - v2[1]);

	  var curx1 = v3[0];
	  var curx2 = v3[0];

	  if(multicolorFlag){
		  for (var slY = v3[1]; slY > v1[1]; slY--){
			    drawLineSegment([Math.floor(curx1), slY], [Math.floor(curx2), slY], randomColor());
			    curx1 -= slope1;
			    curx2 -= slope2;
		  }
	  }else{
		  for (var slY = v3[1]; slY > v1[1]; slY--){
			    drawLineSegment([Math.floor(curx1), slY], [Math.floor(curx2), slY], color);
			    curx1 -= slope1;
			    curx2 -= slope2;
		  }
	  }
}

function randomColor(){
	  var r=Math.floor(Math.random()*255);
	  var g=Math.floor(Math.random()*255);
	  var b=Math.floor(Math.random()*255);
	  return [r, g, b];	
}

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// REPLACE THIS WITH YOUR FUNCTION FOLLOWING THE ASSIGNMENT SPECIFICATIONS
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// Need to add scanline triangle fill rasteriization for HW
//BROWSER: Firefox VERSION: 48.0.2
function drawTriangle(v0,v1,v2,color)
{
  var data = imageData.data;
  var h = imageData.height;
  var w = imageData.width;

  if ((v0[0] == v1[0]) && (v0[1] == v1[1])) return;
  if ((v1[0] == v2[0]) && (v1[1] == v2[1])) return;
  if ((v2[0] == v0[0]) && (v2[1] == v0[1])) return;

  if (!fillFlag){
    drawLineSegment(v0 ,v1, color);
    drawLineSegment(v1 ,v2, color);
    drawLineSegment(v2 ,v0, color);
  }
  else{//scanline triangle fill
	  // first sort the three vertices by y-coordinate ascending so v1 is the topmost vertice
	  var points = sortByY(v0, v1, v2);
	  var vt0 = [0,0];
	  var vt1 = [0,0];
	  var vt2 = [0,0];
	  vt0[0]=points[0];vt0[1]=points[1];
	  vt1[0]=points[2];vt1[1]=points[3];
	  vt2[0]=points[4];vt2[1]=points[5];
	  // now here we know that vt1[1] <= vt2[1] <= vt3[1]
	  // check for trivial case of bottom-flat triangle
	  if (vt1[1] == vt2[1])
	  {
	    bottomFlatTriangle(vt0, vt1, vt2, color);
	  }
	  // check for trivial case of top-flat triangle
	  else if (vt0[1] == vt1[1])
	  {
	    topFlatTriangle(vt0, vt1, vt2, color);
	  } 
	  else
	  {
	    // general case - split the triangle in a topflat and bottom-flat one 
	    var x_coor = (vt0[0]) + ((vt1[1] - vt0[1]) / (vt2[1] - vt0[1])) * (vt2[0] - vt0[0]);
		var v3 = [Math.floor(x_coor), vt1[1]];
	    bottomFlatTriangle(vt0, vt1, v3, color);
	    topFlatTriangle(vt1, v3, vt2, color);
	  }
  /* //Original triangle function
  // handle filled triangles
  else
  {
    var xmin = Math.min(v0[0],v1[0],v2[0]);
    var xmax = Math.max(v0[0],v1[0],v2[0]);
    var ymin = Math.min(v0[1],v1[1],v2[1]);
    var ymax = Math.max(v0[1],v1[1],v2[1]);

    for (var x=xmin; x<=xmax; x++) for (var y=ymin; y<=ymax; y++){
      if (vertexInside([x,y],v0,v1,v2)){
        var yi=h-y;//invert y coordinate
        data[(yi*w+x)*4+0]     = color[0]; // red
        data[(yi*w+x)*4+1]     = color[1]; // green
        data[(yi*w+x)*4+2]     = color[2]; // blue
      }
    }*/
  }

  ctx.putImageData(imageData, 0, 0);

}


function drawRandomTriangle()
{
  var h = imageData.height;
  var w = imageData.width;
  
  var v0x=Math.floor(Math.random()*w);
  var v0y=Math.floor(Math.random()*h);
  var v1x=Math.floor(Math.random()*w);
  var v1y=Math.floor(Math.random()*h);
  var v2x=Math.floor(Math.random()*w);
  var v2y=Math.floor(Math.random()*h);
  var r=Math.floor(Math.random()*255);
  var g=Math.floor(Math.random()*255);
  var b=Math.floor(Math.random()*255);

  drawTriangle([v0x,v0y], [v1x,v1y], [v2x,v2y], [r,g,b]);

}



//
// EOF
//
