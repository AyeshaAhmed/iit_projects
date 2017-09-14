"use strict";

function myV4mult(a1, a2){
	var sum = 0.0;
	for(var i=0; i<a1.length; i++){
		sum+=a1[i]*a2[i];
	}
	return sum;
}

function myM4V4mult(m, v){
	var e = m;
	var p = v;
	var result = [0,0,0,0];

	result[0] = p[0] * e[0] + p[1] * e[4] + p[2] * e[8]  + p[3] * e[12];
	result[1] = p[0] * e[1] + p[1] * e[5] + p[2] * e[9]  + p[3] * e[13];
	result[2] = p[0] * e[2] + p[1] * e[6] + p[2] * e[10] + p[3] * e[14];
	result[3] = p[0] * e[3] + p[1] * e[7] + p[2] * e[11] + p[3] * e[15];

	return result;
}

function main(){
	document.write("Ayesha Ahmed<br><br>");
	document.write("CS 411 -- HW3 Math Problems<br><br>");
	
	//Browser Note
	document.write("BROWSER NOTE: USE Google Chrome 53.0.2785.116.<br><br>");
	
	var x = 0.0;
	var y = 0.0;
	var u = 0.5;
	var u2 = 0.25
	var u3 = 0.125
	var vu = [u3,u2,u,1];//u=0.5
	var vhx = [2,4,1,1];//hermite x vector
	var vhy = [2,2,1,-1];//hermite y vector
	var vx = [1,2,4,5];//all other x vectors
	var vy = [1,2,2,1];//all other y vectors
	var mH = [2,-2,1,1,
	          -3,3,-2,-1,
	          0,0,1,0,
	          1,0,0,0];
	var mC = [-0.5,1.5,-1.5,0.5,
	          1,-2.5,2,-0.5,
	          -0.5,0,0.5,0,
	          0,1,0,0];
	var mB =[-1,3,-3,1,
	         3,-6,3,0,
	         -3,3,0,0,
	         1,0,0,0];
	
	//Prob1
	document.write("1) ");
	x = myV4mult(vhx, myM4V4mult(mH, vu));
	y = myV4mult(vhy, myM4V4mult(mH, vu));
	document.write("(" + x + ", " + y + ")<br><br>");
	
	//Prob2
	document.write("2) ");
	var h1 = 2*u3-3*u2+1;
	var h2 = -2*u3+3*u2;
	var h3 = u3-2*u2+u;
	var h4 = u3-u2;
	x = h1*vhx[0]+h2*vhx[1]+h3*vhx[2]-h4*vhx[3]-0.25;
	y = h1*vhy[0]+h2*vhy[1]+h3*vhy[2]-h4*vhy[3]+0.25;
	document.write("(" + x + ", " + y + ")<br><br>");
	
	//Prob3
	document.write("3) ");
	x = myV4mult(vx, myM4V4mult(mC, vu));
	y = myV4mult(vy, myM4V4mult(mC, vu));
	document.write("(" + x + ", " + y + ")<br><br>");
	
	//Prob4
	document.write("4) ");
	var c1 = -0.5*u3+u2-0.5*u;
	var c2 = 1.5*u3-2.5*u2+1;
	var c3 = -1.5*u3+2*u2+0.5*u;
	var c4 = 0.5*u3-0.5*u2;
	x = c1*vx[0]+c2*vx[1]+c3*vx[2]-c4*vx[3]-0.625;
	y = c1*vy[0]+c2*vy[1]+c3*vy[2]-c4*vy[3]-0.125;
	document.write("(" + x + ", " + y + ")<br><br>");
	
	//Prob5
	document.write("5) ");
	x = myV4mult(vx, myM4V4mult(mB, vu));
	y = myV4mult(vy, myM4V4mult(mB, vu));
	document.write("(" + x + ", " + y + ")<br><br>");
	
	//Prob6
	document.write("6) ");
	var b1 = -1*u3+3*u2-3*u+1;
	var b2 = 3*u3-6*u2+3*u;
	var b3 = -3*u3+3*u2;
	var b4 = u3;
	x = b1*vx[0]+b2*vx[1]+b3*vx[2]-b4*vx[3]+1.25;
	y = b1*vy[0]+b2*vy[1]+b3*vy[2]-b4*vy[3]+0.25;
	document.write("(" + x + ", " + y + ")<br><br>");
	
	//Prob7
	document.write("7) ");
	x = vx[3];
	y = vy[3];
	document.write("(" + x + ", " + y + ")<br><br>");
	
	//Prob8
	document.write("8) ");
	
	document.write("(" + x + ", " + y + ")<br><br>");
}
//EOF