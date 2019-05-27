import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Run {

	private JFrame frmLadderNetwork;
	private JTextField numOfR;
	private JTextField values;
	private JTextField Req;
	private JTextField Vs;
	private JTextField Is;
	private JTextField Ieach;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Run window = new Run();
					window.frmLadderNetwork.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Run() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLadderNetwork = new JFrame();
		frmLadderNetwork.setTitle("Ladder Network Calculator");
		frmLadderNetwork.setBounds(100, 100, 1372, 771);
		frmLadderNetwork.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLadderNetwork.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1354, 724);
		frmLadderNetwork.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int num; double[] R; double Rs = 0; double Vt = 0; double It = 0; double Rt = 0; double[] Ri; double Rsi = 0; double[] I;
				try {
					num = Integer.parseInt(numOfR.getText());
					String[] parts = values.getText().split(",");
					R = new double[num];
					Ri = new double[num];
					I = new double[num];
					for (int i = 0; i < parts.length; i++) {
					    R[i] = Double.parseDouble(parts[i]);
					    Ri[i] = Double.parseDouble(parts[i]);
					}
					num--;
					//------- Calculating Req -------//
					if(num == 0) Req.setText(Double.toString(R[0]));
					else if(num%2 == 1) { //EVEN
						for(int i = num; i > 1; i-=2) {
							Rs = R[i] + R[i-1];
							R[i-2] = (Rs * R[i-2])/(Rs + R[i-2]);
						}
						Req.setText(Double.toString(R[1] + R[0]));
					}
					else if(num%2 == 0) { //ODD
						for(int i = num; i > 0; i-=2) {
							R[i-1] = (R[i] * R[i-1])/(R[i] + R[i-1]);
							Rs = R[i-1] + R[i-2];
						}
						Req.setText(Double.toString(Rs));
					}
					Vt = Double.parseDouble(Vs.getText());
					Rt = Double.parseDouble(Req.getText());
					It = Vt/Rt;
					Is.setText(Double.toString(It));
					//------- Calculating each element's I -------//
					I[0] = It;
					if(num == 0 || num == 1) Ieach.setText(Double.toString(It));
					else if(num%2 == 1) { //EVEN
						for(int k = 1; k < num; k+=2) { 
							for(int i = num; i > 1; i-=2) {
								Rsi = Ri[i] + Ri[i-1];
								if(i-2 == k) break;
								Ri[i-2] = (Rsi * Ri[i-2])/(Rsi + Ri[i-2]);
							}
							I[k] = I[k-1] * Rsi/(Ri[k] + Rsi);
							I[k+1] = I[k-1] - I[k];
							if(k == num-2) I[k+2] = I[k+1];
						}
						Ieach.setText(Arrays.toString(I));
					}
					else if(num%2 == 0) { //ODD
						for(int k = 1; k <= num; k+=2) {
							for(int i = num; i > 1; i-=2) {
								if(i-1 == k) break;
								Rsi = (Ri[i] * Ri[i-1])/(Ri[i] + Ri[i-1]);
								Ri[i-2] += Rsi;
							}
							I[k] = I[k-1] * Ri[k+1]/(Ri[k] + Ri[k+1]);
							I[k+1] = I[k-1] - I[k];
						}
						Ieach.setText(Arrays.toString(I));
					}
				}
				catch(Exception s) {
					JOptionPane.showMessageDialog(null, "Please enter valid number");
				}
			}
		});
		btnCalculate.setBounds(701, 104, 88, 37);
		panel.add(btnCalculate);
		
		JLabel lblReq = new JLabel("Enter number of resistors:");
		lblReq.setBounds(47, 44, 255, 26);
		panel.add(lblReq);
		lblReq.setFont(new Font("Tahoma", Font.PLAIN, 21));
		
		numOfR = new JTextField();
		numOfR.setHorizontalAlignment(SwingConstants.CENTER);
		numOfR.setFont(new Font("Tahoma", Font.PLAIN, 16));
		numOfR.setBounds(344, 22, 63, 48);
		panel.add(numOfR);
		numOfR.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Enter values of resistors:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel.setBounds(47, 115, 240, 26);
		panel.add(lblNewLabel);
		
		values = new JTextField();
		values.setFont(new Font("Tahoma", Font.PLAIN, 16));
		values.setBounds(344, 93, 344, 48);
		panel.add(values);
		values.setColumns(10);
		
		JLabel lblReq_1 = new JLabel("Req =");
		lblReq_1.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblReq_1.setBounds(47, 184, 82, 26);
		panel.add(lblReq_1);
		
		Req = new JTextField();
		Req.setEditable(false);
		Req.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Req.setBounds(113, 173, 174, 37);
		panel.add(Req);
		Req.setColumns(10);
		
		JLabel lblEnterSourceVoltage = new JLabel("Enter source voltage:");
		lblEnterSourceVoltage.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblEnterSourceVoltage.setBounds(434, 45, 204, 24);
		panel.add(lblEnterSourceVoltage);
		
		Vs = new JTextField();
		Vs.setHorizontalAlignment(SwingConstants.CENTER);
		Vs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Vs.setBounds(658, 22, 63, 49);
		panel.add(Vs);
		Vs.setColumns(10);
		
		JLabel lblIs = new JLabel("Is =");
		lblIs.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblIs.setBounds(309, 185, 51, 24);
		panel.add(lblIs);
		
		Is = new JTextField();
		Is.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Is.setEditable(false);
		Is.setBounds(358, 172, 187, 38);
		panel.add(Is);
		Is.setColumns(10);
		
		JLabel lblIrR = new JLabel("I[R1, R2, ..., Rn] =");
		lblIrR.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblIrR.setBounds(47, 260, 179, 24);
		panel.add(lblIrR);
		
		Ieach = new JTextField();
		Ieach.setEditable(false);
		Ieach.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Ieach.setBounds(231, 254, 1111, 37);
		panel.add(Ieach);
		Ieach.setColumns(10);
	}
}
