import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Upload {
	JDialog dialog = new JDialog();
	JPanel u_panel = new JPanel();
	JButton u_upload = new JButton("Upload");
	String[] columnNames = { "English", "Finnish" };
	String[][] data = { { "1r - 1c", "1r - 2c" }, { "2r - 1c", "2r - 2c" },
			{ "3r - 1c", "3r - 2c" } };
	JTable table = new JTable(data, columnNames);
	JButton save = new JButton("Save");

	public Upload() {

		dialog.setSize(600, 300);
		dialog.setVisible(true);
		dialog.setTitle("Upload new words into the database");
		dialog.add(u_panel);

		table.setRowHeight(40);
		table.setCellSelectionEnabled(true);
		u_panel.add(u_upload);
		u_panel.add(save);
		u_panel.add(new JScrollPane(table)); // displays the headers of the table
		saveFile();

		u_upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(getValueAt(1, 0));
			}
		});

	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void saveFile() {
		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser
						.setDialogTitle("Specify a folder where the two files will be saved");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				File savingPath = null;

				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					System.out.println("getSelectedFile() : "
							+ fileChooser.getSelectedFile());
					savingPath = fileChooser.getSelectedFile();
				}
				
				PrintWriter writer = null;
				// Saving the English words
				try {
					writer = new PrintWriter(savingPath + File.separator
							+ "english1.txt", "UTF-8");
					System.out.println("will save to: " + savingPath
							+ File.separator + "the-file-name.txt");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(fileChooser,
							"Unable to save file " + e1.getMessage(),
							"Save Dialog", JOptionPane.ERROR_MESSAGE);
					
				} catch (UnsupportedEncodingException e1) {
					JOptionPane.showMessageDialog(fileChooser,
							"Unable to save file because the text contains unsupported characters " + e1.getMessage(),
							"Save Dialog", JOptionPane.ERROR_MESSAGE);
					
				}
				if (writer != null) { // checks if the file has been created
					writer.println("The first line");
					writer.println("The second line");
					//for (Object x : data) {
					// fw.write(x.toString());
					for (int englishRowCount = 0; englishRowCount <= table.getRowCount()-1; englishRowCount++){
						System.out.println(getValueAt(englishRowCount, 0));
						writer.println(getValueAt(englishRowCount, 0));
					}
					

				//}
					writer.close();
				}
				
				// Saving the Finnish words
				try {
					writer = new PrintWriter(savingPath + File.separator
							+ "finnish1.txt", "UTF-8");
					System.out.println("will save to: " + savingPath
							+ File.separator + "the-file-name.txt");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(fileChooser,
							"Unable to save file " + e1.getMessage(),
							"Save Dialog", JOptionPane.ERROR_MESSAGE);
					
				} catch (UnsupportedEncodingException e1) {
					JOptionPane.showMessageDialog(fileChooser,
							"Unable to save file because the text contains unsupported characters " + e1.getMessage(),
							"Save Dialog", JOptionPane.ERROR_MESSAGE);
					
				}
				if (writer != null) { // checks if the file has been created
					writer.println("The first line");
					writer.println("The second line");
					for (int finnishRowCount = 0; finnishRowCount <= table.getRowCount()-1; finnishRowCount++){
						System.out.println(getValueAt(finnishRowCount, 1));
						writer.println(getValueAt(finnishRowCount, 1));
					}
					writer.close();
				}
				
				

			}

		});
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
public void enter(){
	
	
}
	// URL url = new URL("ftp://user:pass@myftp.abc.com/myFile.txt;type=i");
	// URLConnection urlc = url.openConnection();
	// OutputStream os = urlc.getOutputStream(); // To upload
	// OutputStream buffer = new BufferedOutputStream(os);
	// ObjectOutput output = new ObjectOutputStream(buffer);
	// output.writeObject(myObject);
	// buffer.close();
	// os.close();
	// output.close();

}
