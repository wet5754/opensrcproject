package opensrcproject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener{
	private Container frame;
	private JPanel panel1,panel2;
	private String keyWord;
	JTextField keywordfield;
	JTextArea textfield;
	JButton btn;
	GUI(){
		super("chart");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame();
		this.setVisible(true);

	}

	private void main_frame() {
		// TODO Auto-generated method stub
		frame = this.getContentPane();
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		keywordfield = new JTextField("검색어를 입력하세요",15);
		panel1.add(keywordfield,BorderLayout.NORTH);
		
		btn = new JButton("검색");
		btn.addActionListener(this);
		panel1.add(btn);
		
		textfield = new JTextArea();
		panel2.add(textfield,BorderLayout.CENTER);
		
		frame.add(panel1,BorderLayout.NORTH);
		frame.add(panel2,BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn) {
			NaverAPI temp = new NaverAPI(keywordfield.getText());
			textfield.setText(temp.getResponse());
			textfield.repaint();
		}
	}

}
