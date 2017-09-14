"use strict";

//Ayesha Ahmed
//CS411 HW4
//Browser: Google Chrome 53.0.2785.116
//Use mathProblems.html to see results 

function main(){
	document.write("Ayesha Ahmed<br><br>");
	document.write("CS 411 -- HW4 Math Problems<br><br>");
	
	//Browser Note
	document.write("BROWSER NOTE: USE Google Chrome 53.0.2785.116.<br><br>");
	
	//********     Prob1    **********************************************
	document.write("1) ");
	var angle = 45;
    var rangle = angle * (Math.PI / 180);
    var rotationMatrixY = [
	[Math.cos(rangle), 0, Math.sin(rangle), 0],
	[0,1,0,0],
	[-Math.sin(rangle), 0, Math.cos(rangle), 0],
	[0,0,0,1]
    ];
	document.write(" Rotation Matrix Y: <br>" + rotationMatrixY[0] + 
									   "<br>" + rotationMatrixY[1] + 
									   "<br>" + rotationMatrixY[2] + 
									   "<br>" + rotationMatrixY[3] + "<br><br>");
	
	//********     Prob2    **********************************************
	document.write("2) ");
    //Align (1,1,0) with Y axis, so rotate (1,1,0) by negative 45 degrees
	var v = [1,1,0];
    var axis = [0,1,0];
    var crossP = crossProduct(v, axis);
    var c = dotproduct(v, axis);
    //document.write("checking cross:" + c);
    var I = [
	[1,0,0],
	[0,1,0],
	[0,0,1]
    ];

    var uMatrix = [
	[0, -crossP[2], crossP[1]],
	[crossP[2], 0, -crossP[0]],
	[-crossP[1], crossP[0],0]
    ];

    var rotationMatrix = addTwoMatrices(addTwoMatrices(I, uMatrix), multMatrixbyNum((1/(1 + c)), uMatrix));
    document.write(" Rotation Matrix: <br>" + rotationMatrix[0] + 
    								 "<br>" + rotationMatrix[1] + 
    								 "<br>" + rotationMatrix[2] + "<br><br>");
	
	//********     Prob3    **********************************************
	document.write("3) ");
	var angle3 = 45;
    var rangle3 = angle3 * (Math.PI / 180);
    var rotationMatrixX = [
	[1,0,0,0],
	[0, Math.cos(rangle3), Math.sin(rangle3), 0],
	[0, -Math.sin(rangle3), Math.cos(rangle3), 0],
	[0,0,0,1]
    ];
    var rotationMatrixY = [
	[Math.cos(rangle3), 0, Math.sin(rangle3), 0],
	[0,1,0,0],
	[-Math.sin(rangle3), 0, Math.cos(rangle3), 0],
	[0,0,0,1]
    ];
    var transformationMatrix = multMatrixbyMatrix(rotationMatrixX, rotationMatrixY);
    document.write(" Rotation Matrix X*Y: <br>" + transformationMatrix[0] + 
    									 "<br>" + transformationMatrix[1] + 
    									 "<br>" + transformationMatrix[2] + 
    									 "<br>" + transformationMatrix[3] +"<br><br>");
	
	//********     Prob4    **********************************************
	document.write("4) ");
	//Matrix of Transformation from Local to World Coordinates =
	// = Inverse of TransMatrix from World to Local =
	// = inverse of R*T(-P) = T(P)*(R transpose)
    var p = [1,2,3];
    var DoF = [-1,-2,-3];
    var upV = [1,0,1];
    var transPMatrix = [
	[1,0,0,1],
	[0,1,0,2],
	[0,0,1,3],
	[0,0,0,1]
    ];
    //Calculate the unit vectors from the DoF 
    //and upVector to be used in the rotation matrix
    //Zhat = DoF / magnitude(DoF)
    //Xhat = Up cross product DoF / magnitude(Up cross product DoF)
    //Yhat = Zhat cross product Xhat
    var DoFMagnitude = getMagnitude(DoF);
    var zHat = multMatrixbyNum((1/DoFMagnitude),DoF);
    var upVCrossDoFMagnitude = getMagnitude(crossProduct(DoF,upV));
    var xHat = multMatrixbyNum((1/upVCrossDoFMagnitude),crossProduct(DoF, upV));
    var yHat = crossProduct(xHat,zHat);

    var rotMatrix = [
	[xHat[0], xHat[1], xHat[2], 0],
	[yHat[0], yHat[1], yHat[2], 0],
	[zHat[0], zHat[1], zHat[2], 0],
	[0,0,0,1]
    ];
    
    var transMatrix = multMatrixbyMatrix(transPMatrix,rotMatrix);
    document.write(" Final Transformation Matrix: <br>" + transMatrix[0] + 
    											 "<br>" + transMatrix[1] + 
    											 "<br>" + transMatrix[2] + 
    											 "<br>" + transMatrix[3] + "<br><br>");
	
	//********     Prob5    **********************************************
	document.write("5) ");
	var normal = [[0],[0],[1]];
    var vertexM = [
	[1,2,3,3],
	[2,3,4,4],
	[3,4,4,5],
	[0,0,0,1]
    ];

    var vertexMwithoutH = [
	[(1/3),(2/3),1],
	[(1/2),(3/4),1],
	[(3/5),(4/5),(4/5)]
    ];
    
    //Normal Transformation Matrix = ((vertex matrix)^-1) transposed
    var qM = transpose(findMatrixInverse(vertexMwithoutH));
    var newnormal = multMatrixbyMatrix(qM,normal);
    document.write(" Final Normal Transformation Matrix: <br>" + qM[0] + 
    													"<br>" + qM[1] + 
    													"<br>" + qM[2] + "<br><br>");
    document.write("Normal Vector After Tranform = [" + newnormal[0] + ", " + newnormal[1] + ", " + newnormal[2] + "]<br><br>");
	
	//********     Prob6    **********************************************
	document.write("6) ");
	var p = [[1],[2],[3],[1]];
    var orthoM = [
	[1,0,0,0],
	[0,1,0,0],
	[0,0,1,0],
	[0,0,0,1]
    ];
    //Orthographic projection matrix which preserves Z 
    //is the identity matrix, it does not alter the point
    var pAfterProj = multMatrixbyMatrix(orthoM,p);
    document.write(" P after projection = [" + pAfterProj[0] + ", " + pAfterProj[1] + ", " + pAfterProj[2] + "]<br><br>");

	//********     Prob7    **********************************************
	document.write("7) ");
	var p = [[1],[2],[3],[1]];
    var DoP = [0,-1,1];
    var shearx = -DoP[0]/DoP[2];
    var sheary = -DoP[1]/DoP[2];
    var parallelM = [
	[1,0,shearx,0],
	[0,1,sheary,0],
	[0,0,1,0],
	[0,0,0,1]
    ];
    //Parallel projection matrix whith Direction of Projection provided 
    //is a shear transformation matrix with 
    //shearx = -DoPx / DoPz and sheary = -DoPy / DoPz
    var pAfterProj = multMatrixbyMatrix(parallelM,p);
	document.write(" P after projection = (" + pAfterProj[0] + ", " + pAfterProj[1] + ", " + pAfterProj[2] + ")<br><br>");
	
	//********     Prob8    **********************************************
	document.write("8) ");
	var p = [[1],[2],[3],[1]];
    var f = 10;
    var perspectiveM = [
	[1,0,0,0],
	[0,1,0,0],
	[0,0,1,0],
	[0,0,(1/f),0]
    ];
    var pAfterProj = multMatrixbyMatrix(perspectiveM,p);
    pAfterProj = removeHomogenous(pAfterProj);
	//Perspective projection matrix using homogenous coordinates 
    //is simply the identity except for the last row, which has 
    //(1/f) for the z coordinate and zero for the rest
    document.write("P after projection = (" + pAfterProj[0] + ", " + pAfterProj[1] + ", " + pAfterProj[2] + ")<br><br>");

}// END OF MAIN


//  *****************************************
//////////////// HELPER FUNCTIONS ////////////
//  *****************************************

function removeHomogenous(p){
    var i;
    for(i=0;i<p.length;i++){
	p[i] = p[i]/p[p.length-1];
    }
    return p;
}

function findMatrixInverse(M){
// Returns the inverse of matrix `M`.
    // Use Guassian Elimination to calculate the inverse:
    // (1) 'augment' the matrix (left) by the identity (on the right)
    // (2) Turn the matrix on the left into the identity by elemetry row ops
    // (3) The matrix on the right is the inverse (was the identity matrix)
    // There are 3 elemtary row ops: (combined b and c in code)
    // (a) Swap 2 rows
    // (b) Multiply a row by a scalar
    // (c) Add 2 rows
    
    //if the matrix isn't square: exit (error)
    if(M.length !== M[0].length){return;}
    
    //create the identity matrix (I), and a copy (C) of the original
    var i=0, ii=0, j=0, dim=M.length, e=0, t=0;
    var I = [], C = [];
    for(i=0; i<dim; i+=1){
        // Create the row
        I[I.length]=[];
        C[C.length]=[];
        for(j=0; j<dim; j+=1){
            
            //if we're on the diagonal, put a 1 (for identity)
            if(i==j){ I[i][j] = 1; }
            else{ I[i][j] = 0; }
            
            // Also, make the copy of the original
            C[i][j] = M[i][j];
        }
    }
    
    // Perform elementary row operations
    for(i=0; i<dim; i+=1){
        // get the element e on the diagonal
        e = C[i][i];
        
        // if we have a 0 on the diagonal (we'll need to swap with a lower row)
        if(e==0){
            //look through every row below the i'th row
            for(ii=i+1; ii<dim; ii+=1){
                //if the ii'th row has a non-0 in the i'th col
                if(C[ii][i] != 0){
                    //it would make the diagonal have a non-0 so swap it
                    for(j=0; j<dim; j++){
                        e = C[i][j];       //temp store i'th row
                        C[i][j] = C[ii][j];//replace i'th row by ii'th
                        C[ii][j] = e;      //repace ii'th by temp
                        e = I[i][j];       //temp store i'th row
                        I[i][j] = I[ii][j];//replace i'th row by ii'th
                        I[ii][j] = e;      //repace ii'th by temp
                    }
                    //don't bother checking other rows since we've swapped
                    break;
                }
            }
            //get the new diagonal
            e = C[i][i];
            //if it's still 0, not invertable (error)
            if(e==0){return}
        }
        
        // Scale this row down by e (so we have a 1 on the diagonal)
        for(j=0; j<dim; j++){
            C[i][j] = C[i][j]/e; //apply to original matrix
            I[i][j] = I[i][j]/e; //apply to identity
        }
        
        // Subtract this row (scaled appropriately for each row) from ALL of
        // the other rows so that there will be 0's in this column in the
        // rows above and below this one
        for(ii=0; ii<dim; ii++){
            // Only apply to other rows (we want a 1 on the diagonal)
            if(ii==i){continue;}
            
            // We want to change this element to 0
            e = C[ii][i];
            
            // Subtract (the row above(or below) scaled by e) from (the
            // current row) but start at the i'th column and assume all the
            // stuff left of diagonal is 0 (which it should be if we made this
            // algorithm correctly)
            for(j=0; j<dim; j++){
                C[ii][j] -= e*C[i][j]; //apply to original matrix
                I[ii][j] -= e*I[i][j]; //apply to identity
            }
        }
    }
    
    //we've done all operations, C should be the identity
    //matrix I should be the inverse:
    return I;
}


function transpose(m){
    var newm = new Array(m.length);
    var i,j;
    for (i=0;i<m.length;i++){
	newm[i] = new Array(m[0].length);
    }
    for (i=0;i<m.length;i++){
	for (j=0;j<m[0].length;j++){
	    newm[i][j] = m[j][i];
	}
    }
    return newm;
}
    
function getMagnitude(vector){
    var i,sum=0;
    for (i=0;i<vector.length;i++){
	sum = sum + vector[i]*vector[i];
    }
    return Math.sqrt(sum);
}

function crossProduct(v1,v2){
    var result = new Array(3);
    result[0] =   ( (v1[1] * v2[2]) - (v1[2] * v2[1]) );
    result[1] = - ( (v1[0] * v2[2]) - (v1[2] * v2[0]) );
    result[2] =   ( (v1[0] * v2[1]) - (v1[1] * v2[0]) );
    return result;
}

function convertToHomogenous(point) {
    return [[point[0]],[point[1]],[point[2]],[1]];
}

function rotate3DAboutYAxis(point, angle) {
    var hpoint = convertToHomogenous(point);
    console.log(hpoint);
    var rangle = angle * (Math.PI / 180);
    var rotationMatrix = [
			  [Math.cos(rangle), 0, Math.sin(rangle), 0],
			  [0,1,0,0],
			  [-Math.sin(rangle), 0, Math.cos(rangle), 0],
			  [0,0,0,1]
			  ];
    return multMatrixbyMatrix(rotationMatrix,hpoint);
}

function dotproduct(a,b) {
    var n = 0, lim = Math.min(a.length,b.length);
    for (var i = 0; i < lim; i++) {
	n += a[i] * b[i];
    }
    return n;
}


    
function rotate3DPoint(point, axis, angle){
    var uMatrix = [
		   [0, -axis[2], axis[1]],
		   [axis[2], 0, -axis[0]],
		   [-axis[1], axis[0], 0],
		   ];
    var I = [
	     [1,0,0],
	     [0,1,0],
	     [0,0,1]
	     ];

    var placeholder = addTwoMatrices(multMatrixbyNum(Math.cos(angle), uMatrix), multMatrixbyNum(Math.sin(angle), uMatrix));
    var rotationMatrix = addTwoMatrices(I, placeholder);
    
    return multMatrixbyMatrix(point, rotationMatrix);
}

function addTwoMatrices(m1, m2) {
    var i, j;
    for(i = 0; i < m2.length; i++){
	for(j = 0; j < m2[0].length; j++){
	    m2[i][j] = m2[i][j] + m1[i][j];
	}
    }
    return m2;
}
function multMatrixbyNum(num, m2) {
    var i, j;
    for(i = 0; i < m2.length; i++){
	for(j = 0; j < m2[0].length; j++){
	    m2[i][j] = m2[i][j] * num;
	}
    }
    return m2;
}

function multMatrixbyMatrix(m1, m2){
    //Multiplies an nxy matrix with a yxm matrix, resulting in an nxm matrix
    var m1rows = m1.length;
    var m1cols = m1[0].length;
    var m2rows = m2.length;
    var m2cols = m2[0].length;
    if (m1cols != m2rows){
	console.log("Invalid Matrix Dimensions\n");
	return;
    }
    var k, i, j;

    var m3 = new Array(m1rows);

    for(k = 0; k < m1rows; k++) {
	m3[k] = new Array(m2cols);
	for(i = 0; i < m2cols; i++) {
	    m3[k][i] = 0;
	    for(j = 0; j < m1cols; j++) {
		m3[k][i] += m1[k][j] * m2[j][i];
	    }
	}
    }
    return m3;
}
// END OF HELPER FUNCTIONS


//EOF