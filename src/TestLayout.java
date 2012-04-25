import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.JButton;


public class TestLayout extends JFrame {

	public JPanel pane;
	public JButton btn;
	
	
	
	
	TestLayout() {
		setVisible(true);
		setSize(100, 100);
		btn = new JButton("btn");
		pane = new JPanel();
		pane.setLayout(new GridLayout());
		pane.add(btn);
		
		btn.setActionCommand("" + 1);
		btn.setEnabled(true);
		
		
		add(pane);
		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		pane.remove(btn);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		btn.setText("2");
		pane.add(btn);
	
	}
	
	public static void main(String args[]) {
		new TestLayout();
		
	}
}

