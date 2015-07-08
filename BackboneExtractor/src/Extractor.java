import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.w3c.dom.css.Counter;

public class Extractor {
	JButton add;
	JTextArea button1;
	JTextArea button2;
	JTextArea result;

	public Extractor() {
		JFrame frame = new JFrame("Backbone Extractor");
		button1 = new JTextArea("");
		button2 = new JTextArea("");
		add = new JButton("Find & Copy");
		result = new JTextArea("");
		result.setEditable(false);
		result.setBackground(Color.LIGHT_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBounds(500, 200, 900, 310);
		frame.setLayout(new GridLayout(0, 2, 1, 1));
		frame.add(new JLabel("Enter the source folder: ", 11));
		frame.add(button1);
		frame.add(new JLabel("Enter the target folder: ", 11));
		frame.add(button2);
		frame.add(new JLabel("Results: ", 11));
		frame.add(result);
		frame.add(add);
		add.addActionListener(new action());

	}

	public static void main(String[] args) {

		Extractor ff = new Extractor();
	}

	public class action implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {

				String searchDirectory = button1.getText().trim();
				String targetDirectory = button2.getText().trim();
				if ("Find & Copy".equals(ae.getActionCommand())) {

					File dir = new File(searchDirectory);
					File[] directoryListing = dir.listFiles();
					List<File> files = (List<File>) FileUtils.listFiles(dir,
							TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
					List<String> results = new ArrayList<String>();
					int counter = 1;
					if (directoryListing != null) {

						for (File fileName : files) {

							if (fileName.toString().contains("index.xml")) {
								results.add(fileName.toString());
								File diri = new File(targetDirectory + "\\"
										+ counter);
								diri.mkdir();

								File newFile = new File(diri + "\\index.xml");

								try {

									FileUtils.copyFile(fileName, newFile);
								} catch (Exception ex) {
									result.setText("");
									result.setText("Target directory already exists");
									results = null;
									System.out
											.println("Target directory already exists");
									break;
								}
								counter++;
							}
						}
					}
					counter = 0;

					if (results.size() > 0) {
						result.setText("");
						for (String x : results) {
							System.out.println(x);
							result.append(x + "\n");
						}

					} else {
						System.out.println("File not found");
						result.setText("");
						result.setText("File not found");
					}

				}
			} catch (Exception e) {
				if (!result.getText().equals("Target directory already exists")) {
					result.setText("");
					result.setText("Directory not valid");
					System.out.println("Directory not valid");
				}
					

			}

		}
	}
}