function main(){
	document.write("Ayesha Ahmed<br><br>");
	document.write("CS 411 -- HW2 Math Problems<br><br>");
	
	//Browser Note
	document.write("BROWSER NOTE: USE Google Chrome 53.0.2785.116, output for problems 3 and 5 do not display in Firefox 48.0.2.<br><br>");
	
	var hp1 = new Vector3([1, 2, 3]);//homogeneous point
	var hp2 = new Vector3([0, 0, 0]);//homogeneous point
	var p_2d = new Vector3([0, 0, 0]);//2d point
	var p2_2d = new Vector3([0, 0, 0]);//2d point
	var rotM = new Matrix4();//matrix for rotations
	var ans = new Vector3();

	//Prob1
	document.write("1) ");
	p_2d = [(hp1.elements[0] / hp1.elements[2]), (hp1.elements[1] / hp1.elements[2])];
	hp2 = [(p_2d[0] * 6), (p_2d[1] * 6), 6];
	document.write("(" + hp2 + ")<br><br>");
	
	//Prob2
	document.write("2) ");
	p_2d = [(hp1.elements[0] / hp1.elements[2]), (hp1.elements[1] / hp1.elements[2])];
	document.write("(" + p_2d + ")<br><br>");
	
	//Prob3
	document.write("3) ");
	p_2d.elements = [2, 5, 0];
	rotM.setRotate(30, 0, 0, 1);
	ans = rotM.multiplyVector3(p_2d);
	document.write("(" + ans.elements + ")<br><br>");
	
	//Prob4
	document.write("4) ");
	p2_2d.elements = [1, 2, 0];
	p_2d.elements = [2, 5, 0];
	p_2d.elements = [p_2d.elements[0] - p2_2d.elements[0], p_2d.elements[1] - p2_2d.elements[1], 0];
	ans = rotM.multiplyVector3(p_2d);
	p_2d.elements = [ans.elements[0] + p2_2d.elements[0], ans.elements[1] + p2_2d.elements[1], 0];
	document.write("(" + p_2d.elements + ")<br><br>");
	
	//Prob5
	document.write("5) ");
	p_2d.elements = [2, 5, 0];
	p2_2d.elements = [3, 4, 0];
	p_2d.elements = [p_2d.elements[0] + p2_2d.elements[0], p_2d.elements[1] + p2_2d.elements[1], 0];
	rotM.setRotate(45, 0, 0, 1);
	ans = rotM.multiplyVector3(p_2d);
	document.write("(" + ans.elements + ")<br><br>");
	
	//Prob6
	document.write("6) ");
	p_2d.elements = [2, 5, 0];
	ans = rotM.multiplyVector3(p_2d);
	p_2d.elements = [ans.elements[0] + p2_2d.elements[0], ans.elements[1] + p2_2d.elements[1], 0];
	document.write("(" + p_2d.elements + ")<br><br>");
	
	//Prob7
	document.write("7) ");
	p_2d.elements = [5, 6, 0];
	p2_2d.elements = [1, 2, 0]
	ans = rotM.multiplyVector3(p_2d);
	p_2d.elements = [ans.elements[0] - p2_2d.elements[0], ans.elements[1] - p2_2d.elements[1], 0];
	document.write("(" + p_2d.elements + ")<br><br>");
	
	//EOF
}