package com.example.SimpleSwingProject;
import com.example.SimpleSwingProject.Validator;

import java.awt.*;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SwingApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textName;
	private JTextField textPhone;
	private JTextField textEmail;
	private JTextField textAddress;
	private JTextField textSearch;
	Connection conn = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingApp frame = new SwingApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SwingApp() {
		addWindowListener(new WindowAdapter() {
			
			public void windowOpened(java.awt.event.WindowEvent e) {
				updateTableData();
			}
		});
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 812, 534);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(new Color(128, 128, 255));
		panel.setBounds(0, 10, 796, 237);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("EMP NAME");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 97, 31);
		panel.add(lblNewLabel);
		
		JLabel lblEmpPhone = new JLabel("EMP PHONE");
		lblEmpPhone.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmpPhone.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmpPhone.setBounds(424, 11, 97, 31);
		panel.add(lblEmpPhone);
		
		JLabel lblEmpEmail = new JLabel("EMP EMAIL");
		lblEmpEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmpEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmpEmail.setBounds(10, 108, 97, 31);
		panel.add(lblEmpEmail);
		
		JLabel lblEmpAddress = new JLabel("EMP ADDRESS");
		lblEmpAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmpAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmpAddress.setBounds(404, 108, 117, 31);
		panel.add(lblEmpAddress);
		
		textName = new JTextField();
		textName.setBounds(117, 11, 224, 31);
		panel.add(textName);
		textName.setColumns(10);
		
		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(531, 11, 224, 31);
		panel.add(textPhone);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(117, 108, 224, 31);
		panel.add(textEmail);
		
		textAddress = new JTextField();
		textAddress.setColumns(10);
		textAddress.setBounds(531, 108, 224, 31);
		panel.add(textAddress);
		
		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_1","root", "00000000");
					
					String empName = textName.getText();
				    String empPhone = textPhone.getText();
				    String empEmail = textEmail.getText();
				    String empAddress = textAddress.getText();
					
					
					
				    if (empName.isEmpty() || empPhone.isEmpty() || empEmail.isEmpty() || empAddress.isEmpty()) {
				        JOptionPane.showMessageDialog(null, "All fields are required. Please fill them.");
				    }else if (!Validator.isValidPhone(empPhone)) {
				        JOptionPane.showMessageDialog(null, "Invalid phone number format. Please enter a valid phone number.");
				    }
				    else if (!Validator.isValidEmail(empEmail)) {
				        JOptionPane.showMessageDialog(null, "Invalid email format. Please enter a valid email address.");
				    }
				    else {
				        Statement st = conn.createStatement();
				        String query = "INSERT INTO employee (EMP_NAME, EMP_PHONE, EMP_EMAIL, EMP_ADDRESS) VALUES (?, ?, ?, ?)";
				        PreparedStatement preparedStatement = conn.prepareStatement(query);
				        preparedStatement.setString(1, empName);
				        preparedStatement.setString(2, empPhone);
				        preparedStatement.setString(3, empEmail);
				        preparedStatement.setString(4, empAddress);

				        int rowsInserted = preparedStatement.executeUpdate();

				        if (rowsInserted > 0) {
				            JOptionPane.showMessageDialog(null, "Record inserted successfully.");
				        } else {
				            JOptionPane.showMessageDialog(null, "Insertion failed. Please try again.");
				        }

				        preparedStatement.close();
				        //windowOpened(null);
				        updateTableData();
				    }

				    conn.close();
				    
				} catch (SQLException | ClassNotFoundException e1) {
				    e1.printStackTrace();
				    JOptionPane.showMessageDialog(null, "An error occurred: " + e1.getMessage());
				}
			}
		});
		
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(18, 185, 97, 31);
		panel.add(btnNewButton);
		
		JButton btnSelect = new JButton("SELECT");	
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int id =Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_1","root", "00000000");
					Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs = st.executeQuery("select * from employee where EMP_ID ="+id);
					
					while(rs.next())
					{
						textName.setText(rs.getString("EMP_NAME"));
						textPhone.setText(rs.getString("EMP_PHONE"));
						textEmail.setText(rs.getString("EMP_EMAIL"));
						textAddress.setText(rs.getString("EMP_ADDRESS"));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		btnSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSelect.setBounds(151, 185, 97, 31);
		panel.add(btnSelect);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int id =Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_1","root", "00000000");
					Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					int r = JOptionPane.showConfirmDialog(null, "are you sure want to delete the selected row ");
					
					if(r==0)
					{
						if(!st.execute("delete from employee where EMP_ID = "+id))
						{
							updateTableData();
							JOptionPane.showMessageDialog(null, "Record deleted successfully.");
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBounds(282, 185, 97, 31);
		panel.add(btnDelete);
		
		textSearch = new JTextField();
		textSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_1","root", "00000000");
				
					Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs = st.executeQuery("select * from employee where EMP_NAME like '%"+textSearch.getText()+"%'");
					rs.last();
					int row = rs.getRow();
					int col = rs.getMetaData().getColumnCount();
					rs.beforeFirst();
					String rowData[][] = new String[row][col];
					int r = 0;
					
					while(rs.next())
						{
							for(int i=0;i<col;i++)
								{
									rowData[r][i] = rs.getString(i+1);
								}
							r++;
					
						}
					String [] columnName = {"ID","NAME","PHONE","EMAIL","ADDRESS"};
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					model.setDataVector(rowData, columnName);
					
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		
		textSearch.setColumns(10);
		textSearch.setBounds(531, 185, 224, 31);
		panel.add(textSearch);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		int id =Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_1","root", "00000000");
					
				    if (textName.getText().isEmpty() || textPhone.getText().isEmpty() || textEmail.getText().isEmpty() || textAddress.getText().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "All fields are required. Please fill them.");
				    }else if (!Validator.isValidPhone(textPhone.getText())) {
				        JOptionPane.showMessageDialog(null, "Invalid phone number format. Please enter a valid phone number.");
				    }
				    else if (!Validator.isValidEmail(textEmail.getText())) {
				        JOptionPane.showMessageDialog(null, "Invalid email format. Please enter a valid email address.");
				    }
				    else {
					PreparedStatement pstmt;
					pstmt = conn.prepareStatement("UPDATE employee SET EMP_NAME=?, EMP_PHONE=?, EMP_EMAIL=?, EMP_ADDRESS=? WHERE EMP_ID="+id);
					pstmt.setString(1,textName.getText());
					pstmt.setString(2, textPhone.getText());
					pstmt.setString(3, textEmail.getText());
					pstmt.setString(4, textAddress.getText());
					
					pstmt.executeUpdate();
					pstmt.close();
					
					updateTableData();
					JOptionPane.showMessageDialog(null, "Record updated successfully.");					
				    }
				}
				 catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnEdit.setBounds(404, 185, 97, 31);
		panel.add(btnEdit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 272, 776, 212);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.ORANGE);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NAME", "PHONE", "EMAIL", "ADDRESS"
			}
		));
	}
	
		private void updateTableData() {
			try
				{
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_1","root", "00000000");
		
				Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = st.executeQuery("select * from employee");
				rs.last();
				int row = rs.getRow();
				int col = rs.getMetaData().getColumnCount();
				rs.beforeFirst();
				String rowData[][] = new String[row][col];
				int r = 0;
				
				while(rs.next())
				{
					for(int i=0;i<col;i++)
						{
							rowData[r][i] = rs.getString(i+1);
						}
					r++;
			
				}
			String [] columnName = {"ID","NAME","PHONE","EMAIL","ADDRESS"};
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.setDataVector(rowData, columnName);
			clearFieds();
			} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
			}
		}
	
	private void clearFieds()
	{
		textName.setText(null);
		textPhone.setText(null);
		textEmail.setText(null);
		textAddress.setText(null);
	}
}
	
