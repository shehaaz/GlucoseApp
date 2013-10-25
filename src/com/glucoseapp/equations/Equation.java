package com.glucoseapp.equations;
import java.lang.Math;

public class Equation {
	private static final double D = 18000;
	private static final double BW = 70;
	static double V_G = 1.88; //1.49 // [dL/kg]
	static double k_1 = 0.065; //0.042 // [min ^-1]
	static double k_2 = 0.079 ;//0.071 // [min ^-1]
	static double V_I = 0.05 ;//0.04 // [L/kg]
	static double m_1 = 0.190; //0.379 // [min ^-1]
	static double m_2 = 0.484 ;//0.673 // [min ^-1]
	static double m_4 = 0.194 ;//0.269 // [min ^-1]
	static double m_5 = 0.0304; //0.0526 // [min *kg/pmol ]
    static double m_6 = 0.6471; //0.8118 // [-]
    static double HE_b = 0.6  ;// [-]
    static double k_max = 0.0558; //0.0465 // [min ^-1]
    static double k_min = 0.0080; //0.0076 // [min ^-1]
    static double k_abs = 0.057 ;//0.023 // [min ^-1]
    static double k_gri = 0.0558; //0.0465 // [min ^-1]
    static double f = 0.90; // [-]
    static double a = 0.00013; //0.00006 // [mg ^-1]
    static double b = 0.82 ;//0.68 // [-]
    static double c = 0.00236; //0.00023 // [mg ^-1]
    static double d = 0.010; // 0.09 // [-]
    static double k_p1 = 2.70; //3.09 // [mg/kg/min ]
    static double k_p2 = 0.0021; //0.0007 // [min ^-1]
    static double k_p3 = 0.009; //0.005 // [mg/kg/min per pmol/L]
    static double k_p4 = 0.0618; //0.0786 // [mg/kg/min per pmol/kg]
    static double k_i = 0.0079; //0.0066 // [min ^-1]
    static double F_cns = 1.0; // [mg/kg/min ]
    static double V_m0 = 2.50; //4.65 // [mg/kg/min ]
    static double V_mx = 0.047; //0.034 // [mg/kg/min per pmol/L]
    static double K_m0 = 225.59; //466.21 // [mg/kg]
    static double p_2U = 0.0331; //0.084 // [min ^-1]
    static double K = 2.30; //0.99 // [pmol/kg per mg/dL]
    static double alpha = 0.050; //0.013 // [min ^-1]
    static double beta = 0.11; //0.05 // [pmol/kg/min per mg/dL]
    static double gamma = 0.5; // [min ^-1]
    static double k_e1 = 0.0005; //0.0007 // [min ^-1]
    static double k_e2 = 339.0; //269 // [mg/kg]
    
	public static void main(String[] args){        
        //double total_time = 0.5;
        //int N = 100;
      //  double[] S = new double[N+1];
       // double[] I = new double[N+1];
      //  double[] R = new double[N+1];

        //S[0] = 1.0;/* initial value */
        //I[0] = 99.0; /* initial value */
        //R[0] = 0.0;/* initial value */
        
        double totalTime = 1440.0;
        int N = 1440;
        
        double[] G_p = new double[N+1];
        double[] G_t = new double[N+1];
        double[] I_l = new double[N+1];
        double[] I_p = new double[N+1];
        double[] Q_sto1 = new double[N+1];
        double[] Q_sto2 = new double[N+1];
        double[] Q_gut = new double[N+1];
        double[] I_1 = new double[N+1];
        double[] I_d = new double[N+1];
        double[] X = new double[N+1];
        double[] Y = new double[N+1];
        double[] I_po = new double[N+1];
        
        double dt;
		G_p[0] = 90;
        G_t[0] = 90;
        I_l[0] = 90;
        I_p[0] = 90;
        Q_sto1[0] = 0;
        Q_sto2[0] = 0;
        Q_gut[0] = 0;
        I_1[0] = 0;
        I_d[0] = 0;
        X[0] = 0;
        Y[0] = 0;
        I_po[0] = 0;
        
        

       // double dt = total_time / N;  //REMOVE
        dt = totalTime / N;

        for (int i = 0; i < N; ++i)
        {


                //double g = 1 / total_time;

                //double t = i*dt;
                //double l = (50*0.5*S[i+1])/100;/* compute l here */
                
                double S = gamma*I_po[i];
                double S_b = (m_6 - HE_b)/m_5;
                
                double I_b = 4.4;
                double h = 70.0;
                
                double HE = -m_5*S + m_6;
                double m_3 = HE*m_1/(1 - HE);
                
                //GI Tract

                
				double Ra = (f*k_abs*Q_gut[i])/BW;
                double Q_sto = Q_sto1[i] + Q_sto2[i];
                double k_emptQ_sto = k_min +((k_max - k_min)/2)*((Math.tanh(alpha*(Q_sto -b* D)) - Math.tanh(c*(Q_sto -d*D))+2));
                
                // Liver
                double EGP = Math.max(0, k_p1 - k_p2*G_p[i] - k_p3*I_d[i] - k_p4*I_po[i] ); // Change 0 to 2.01 for test
                
                // Muscle and Adipose Tissue
                double V_m = V_m0 + V_mx*X[i];
                double K_m = K_m0;
                double U_id = V_m*G_t[i]/(K_m +G_t[i]);
                
                double E;
                // Kidneys - Glucose Renal Excretion
                if(G_p[i] > k_e2){
                      E = k_e1*(G_p[i] -k_e2);
                  }else{
                   E = 0.0;
                 }

                // Brain - CNS Glucose Utilization
                double U_ii = F_cns;
                
                /* calculate derivatives */
                //double dSdt = - I[i] * S[i];
                //double dIdt = I[i] * S[i] - g * I[i];
                //double dRdt = g * I[i];
                
                
				double dG_pdt = EGP + Ra - U_ii - E - k_1*G_p[i] + k_2*G_t[i]; 
                double dG_tdt = -U_id + k_1*G_p[i] - k_2*G_t[i];  
                double dI_ldt = -(m_1 +m_3 )*I_l[i] + m_2*I_p[i] + S; 
                double dI_pdt = -(m_2 +m_4 )*I_p[i] + m_1*I_l[i]; 
                
                double G = (double) (G_p[i]/V_G); //G(t)
                double I = (double) (I_p[i]/V_I);
                
               
                double dQ_sto1dt = D*d - k_gri*Q_sto1[i];
                double dQ_sto2dt = k_gri*Q_sto1[i] - k_emptQ_sto*Q_sto2[i];
                double dQ_gutdt = k_emptQ_sto*Q_sto2[i] - k_abs*Q_gut[i]  ;
                double dI_1dt = -k_i*(I_1[i] -I);
                double dI_ddt = -k_i*(I_d[i] -I_1[i] );
                double dXdt = p_2U*(I - I_b ) - p_2U*X[i];
               
                double dYdt;
               if(beta*(G-h) >= -S_b){
            	    dYdt =  -alpha*(Y[i]-beta*(G-h));
                }else{
                	 dYdt =  -alpha*(Y[i]+S_b);
               }
               
               double S_po;
			// Pancreas/ Beta -Cell
            if(G_p[i]/V_G > 0){
               S_po = Y[i] + S_b + K*(G_p[i]/V_G);
            }else{
                S_po = Y[i] + S_b;
            }
            double dI_podt = S_po - S; 
                
                
            G_p[i+1] = G_p[i] + dG_pdt * dt; 
            G_t[i+1] = G_t[i] + dG_tdt * dt;
            I_l[i+1] = I_l[i] + dI_ldt * dt;
            I_p[i+1] = I_p[i] + dI_pdt * dt;
            Q_sto1[i+1] = Q_sto1[i] + dQ_sto1dt * dt;
            Q_sto2[i+1] = Q_sto2[i] + dQ_sto2dt * dt;
            Q_gut[i+1] = Q_gut[i] + dQ_gutdt * dt;
            I_1[i+1] = I_1[i] + dI_1dt * dt;
            I_d[i+1] = I_d[i] + dI_ddt * dt;
            X[i+1] = X[i] + dXdt * dt;
            Y[i+1] = Y[i] + dYdt * dt;
            I_po[i+1] = I_po[i] + dI_podt * dt;
            
                /* now integrate using Euler */
               // S[i+1] = S[i] + dSdt * dt;
               // I[i+1] = I[i] + dIdt * dt;
               // R[i+1] = R[i] + dRdt * dt;
                
                System.out.println(G_p[i+1]);
        }
}
}
