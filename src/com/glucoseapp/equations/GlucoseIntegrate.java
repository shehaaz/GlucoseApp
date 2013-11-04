package com.glucoseapp.equations;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

public class GlucoseIntegrate {
	double eatingTime = 30.0; // [min ]
	double BW = 70.0;
	double[][] X = new double[1440][12];

	double bHour = 7.0;
	double bMin = 0.0; // D value
	double bCHO = 500.0;  //converted to m
	double bInsulin = 0.0; //  # converted from IU/L to pmol/L
	double lHour = 0.0 ;
	double lMin = 0.0 ;
	double lCHO = 0.0;//  # converted to mg
	double lInsulin = 0.0;//  # converted to pmol/L
	double dHour = 0.0;
	double dMin = 0.0;
	double dCHO = 0.0;// # converted to mg
	double dInsulin = 0.0; //# converted to pmol/L
	double insulinTime = 0.0;


	public GlucoseIntegrate(){
		System.out.println("This is working! ");

		double[] t; 
		t =	new double[11];
		t[0] = 0;
		t[1] = bHour*60 + bMin - insulinTime; 
		t[2] = bHour*60 + bMin ;
		t[3] = bHour*60 + bMin + eatingTime ;
		t[4] = lHour*60 + lMin - insulinTime ;
		t[5] = lHour*60 + lMin ;
		t[6] = lHour*60 + lMin + eatingTime; 
		t[7] = dHour*60 + dMin - insulinTime; 
		t[8] = dHour*60 + dMin ;
		t[9] = dHour*60 + dMin + eatingTime; 
		t[10] = 24*60;

		FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-3, 1.0, 1.0e-10, 1.0e-10);
		FirstOrderDifferentialEquations ode =  new GlucoseODE(BW,bCHO);

		double[] x = new double[] { 90,90,54.18,54.18,0,0,0,4.4,4.4,0,0,0}; // initial state
		double [] y = new double[12];
		//p853.integrate(ode, 0.0, x, t[1], x); // now y contains final state at time t=16.0



			for (int i=1; i<1440;i++){
				
				dp853.integrate(ode, 0.0, x, i, y); // now y contains final state at time t=16.0
		
				x = compute(y);
				
					System.out.println(x[0]);
				
			
			
			}
		
	}

	public static void main (String[] args){
		new GlucoseIntegrate();
	}


}