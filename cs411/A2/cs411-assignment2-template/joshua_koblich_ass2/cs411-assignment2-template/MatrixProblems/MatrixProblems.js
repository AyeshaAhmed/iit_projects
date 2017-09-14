function main(){
	document.write("Joshua Koblich<br>CS 411 Assignment 2 Matrix Problems:<br><br>");
	document.write("1). ");
	var p1h = new Vector3([1, 2, 3]);
	var p2h = new Vector3([0, 0, 0]);
	var p2d = new Vector3([0, 0, 0]);
	var p2d2 = new Vector3([0, 0, 0]);
	p2d = [(p1h.elements[0] / p1h.elements[2]), (p1h.elements[1] / p1h.elements[2])];
	p2h = [(p2d[0] * 6), (p2d[1] * 6), 6];
	document.write("(" + p2h + ")");
	///////////////////////////////////////////////////////////////////
	document.write("<br><br>2). ");
	p2d = [(p1h.elements[0] / p1h.elements[2]), (p1h.elements[1] / p1h.elements[2])];
	document.write("(" + p2d + ")");
	///////////////////////////////////////////////////////////////////
	document.write("<br><br>3). ");
	p2d.elements = [2, 5, 0];
	var rotation = new Matrix4();
	rotation.setRotate(30, 0, 0, 1);
	var answer = new Vector3();
	answer = rotation.multiplyVector3(p2d);
	document.write("(" + answer.elements + ")");
	///////////////////////////////////////////////////////////////////
	document.write("<br><br>4). ");
	p2d2.elements = [1, 2, 0];
	p2d.elements = [2, 5, 0];
	p2d.elements = [p2d.elements[0] - p2d2.elements[0], p2d.elements[1] - p2d2.elements[1], 0];
	//rotation.setRotate(45, 0, 0, 1);
	answer = rotation.multiplyVector3(p2d);
	p2d.elements = [answer.elements[0] + p2d2.elements[0], answer.elements[1] + p2d2.elements[1], 0];
	document.write("(" + p2d.elements + ")");
	///////////////////////////////////////////////////////////////////
	document.write("<br><br>5). ");
	p2d.elements = [2, 5, 0];
	p2d2.elements = [3, 4, 0];
	p2d.elements = [p2d.elements[0] + p2d2.elements[0], p2d.elements[1] + p2d2.elements[1], 0];
	rotation.setRotate(45, 0, 0, 1);
	answer = rotation.multiplyVector3(p2d);
	document.write("(" + answer.elements + ")");
	///////////////////////////////////////////////////////////////////
	document.write("<br><br>6). ");
	p2d.elements = [2, 5, 0];
	answer = rotation.multiplyVector3(p2d);
	p2d.elements = [answer.elements[0] + p2d2.elements[0], answer.elements[1] + p2d2.elements[1], 0];
	document.write("(" + p2d.elements + ")");
	///////////////////////////////////////////////////////////////////
	document.write("<br><br>7). ");
	p2d.elements = [5, 6, 0];
	p2d2.elements = [1, 2, 0]
	answer = rotation.multiplyVector3(p2d);
	p2d.elements = [answer.elements[0] - p2d2.elements[0], answer.elements[1] - p2d2.elements[1], 0];
	document.write("(" + p2d.elements + ")");
	document.write("<br><br><br>(Note: Output of problems 3 and 5 display properly in Google Chrome 53.0.2785.116, but not in Firefox 48.0.2.)");
}