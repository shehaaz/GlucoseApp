package com.glucoseapp.equations;

import java.util.Collection;


import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.events.EventHandler;
import org.apache.commons.math3.ode.sampling.StepHandler;

public class GlucoseODE implements FirstOrderDifferentialEquations{

	double BW; //Body Weight
	double D;

	double V_G = 1.88; // 1.49 // [dL/kg]
	double k_1 = 0.065; // 0.042 // [min ^-1]
	double k_2 = 0.079;// 0.071 // [min ^-1]
	double V_I = 0.05;// 0.04 // [L/kg]
	double m_1 = 0.190; // 0.379 // [min ^-1]
	double m_2 = 0.484;// 0.673 // [min ^-1]
	double m_4 = 0.194;// 0.269 // [min ^-1]
	double m_5 = 0.0304; // 0.0526 // [min *kg/pmol ]
	double m_6 = 0.6471; // 0.8118 // [-]
	double HE_b = 0.6;// [-]
	double k_max = 0.0558; // 0.0465 // [min ^-1]
	double k_min = 0.0080; // 0.0076 // [min ^-1]
	double k_abs = 0.057;// 0.023 // [min ^-1]
	double k_gri = 0.0558; // 0.0465 // [min ^-1]
	double f = 0.90; // [-]
	double a = 0.00013; // 0.00006 // [mg ^-1]
	double b = 0.82;// 0.68 // [-]
	double c = 0.00236; // 0.00023 // [mg ^-1]
	double d = 0.010; // 0.09 // [-]
	double k_p1 = 2.70; // 3.09 // [mg/kg/min ]
	double k_p2 = 0.0021; // 0.0007 // [min ^-1]
	double k_p3 = 0.009; // 0.005 // [mg/kg/min per pmol/L]
	double k_p4 = 0.0618; // 0.0786 // [mg/kg/min per pmol/kg]
	double k_i = 0.0079; // 0.0066 // [min ^-1]
	double F_cns = 1.0; // [mg/kg/min ]
	double V_m0 = 2.50; // 4.65 // [mg/kg/min ]
	double V_mx = 0.047; // 0.034 // [mg/kg/min per pmol/L]
	double K_m0 = 225.59; // 466.21 // [mg/kg]
	double p_2U = 0.0331; // 0.084 // [min ^-1]
	double K = 2.30; // 0.99 // [pmol/kg per mg/dL]
	double alpha = 0.050; // 0.013 // [min ^-1]
	double beta = 0.11; // 0.05 // [pmol/kg/min per mg/dL]
	double gamma = 0.5; // [min ^-1]
	double k_e1 = 0.0005; // 0.0007 // [min ^-1]
	double k_e2 = 339.0; // 269 // [mg/kg]
	double[] p = new double[]{V_G ,k_1 ,k_2 , V_I ,m_1 ,m_2 ,m_4 ,
			m_5 ,m_6 ,HE_b, k_max , k_min , k_abs, 
			k_gri,f,a,b,c,d, k_p1,k_p2, k_p3, k_p4,k_i , 
			F_cns , V_m0,V_mx ,K_m0, p_2U,K, alpha,beta, 
			gamma , k_e1, k_e2};

	public GlucoseODE(double BW, double D) {
		this.BW = BW;
		this.D = D;
	}

	//@Override
	public int getDimension() {
		return 12;
	}

	public double getVG(){
		return V_G;
	}

	//@Override
	public void computeDerivatives(double t, double[] x, double[] xdot)
			throws MaxCountExceededException, DimensionMismatchException {
		// TODO Auto-generated method stub

		// double G_p = 90;
		double G_p = x[0];

		// double G_t = 90;
		double G_t = x[1];

		// double I_l = 0;
		double I_l = x[2];

		// double I_p = 54.18;
		double I_p = x[3];

		// double Q_sto1 = 0;
		double Q_sto1 = x[4];

		// double Q_sto2 = 0;
		double Q_sto2 = x[5];

		// double Q_gut = 0;
		double Q_gut = x[6];

		// double I_1 = 54.18;
		double I_1 = x[7];

		// double I_d = 54.18;
		double I_d = x[8];

		// double X = 0;
		double X = x[9];

		// double Y = 0;
		double Y = x[10];

		// double I_po = 0;
		double I_po = x[11];

		

		double S = gamma * I_po;
		double S_b = (m_6 - HE_b) / m_5;

		double I_b = 4.4;
		double h = 90.0;

		double HE = -m_5 * S + m_6;
		double m_3 = HE * m_1 / (1 - HE);

		// GI Tract

		double Ra = (f * k_abs * Q_gut) / BW;
		double Q_sto = Q_sto1 + Q_sto2;
		double k_emptQ_sto = k_min
				+ ((k_max - k_min) / 2)
				* ((Math.tanh(alpha * (Q_sto - b * D))
						- Math.tanh(c * (Q_sto - d * D)) + 2));

		// Liver
		double EGP = Math
				.max(2.4, k_p1 - k_p2 * G_p - k_p3 * I_d - k_p4 * I_po); // Change
		// 0
		// to
		// 2.01
		// for
		// test

		// Muscle and Adipose Tissue
		double V_m = V_m0 + V_mx * X;
		double K_m = K_m0;
		double U_id = V_m * G_t / (K_m + G_t);

		double E;
		// Kidneys - Glucose Renal Excretion
		if (G_p > k_e2) {
			E = k_e1 * (G_p - k_e2);
		} else {
			E = 0.0;
		}

		// Brain - CNS Glucose Utilization
		double U_ii = F_cns;

		xdot[0] = EGP + Ra - U_ii - E - k_1 * G_p + k_2 * G_t;
		xdot[1] = -U_id + k_1 * G_p - k_2 * G_t;
		xdot[2] = -(m_1 + m_3) * I_l + m_2 * I_p + S;
		xdot[3] = -(m_2 + m_4) * I_p + m_1 * I_l;

		double G = G_p / V_G; // G(t)
		double I = I_p / V_I;

		xdot[4] = D * d - k_gri * Q_sto1;
		xdot[5] = k_gri * Q_sto1 - k_emptQ_sto * Q_sto2;
		xdot[6] = k_emptQ_sto * Q_sto2 - k_abs * Q_gut;
		xdot[7] = -k_i * (I_1 - I);
		xdot[8] = -k_i * (I_d - I_1);
		xdot[9] = p_2U * (I - I_b) - p_2U * X;

		if (beta * (G - h) >= -S_b) {
			xdot[10] = -alpha * (Y - beta * (G - h));
		} else {
			xdot[10] = -alpha * (Y + S_b);
		}

		double S_po;

		// Pancreas/ Beta -Cell
		if (G_p / V_G > 0) {
			S_po = Y + S_b + K * (xdot[0] / V_G);
		} else {
			S_po = Y + S_b;
		}
		xdot[11] = S_po - S;

	}

	



}
