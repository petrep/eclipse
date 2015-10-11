import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Upload {
	JDialog dialog = new JDialog();
	JPanel u_panel = new JPanel();
	JButton u_upload = new JButton("Upload");
	Object[] columnNames = { "English", "Finnish" };
	Object[][] data = { { "1r - 1c", "1r - 2c" }, 
			{ "2r - 1c", "2r - 2c" },
			{ "3r - 1c", "3r - 2c" } };
	JTable table = new JTable(data, columnNames);
	JButton save = new JButton("Save");

	public Upload() {

		dialog.setSize(400, 300);
		dialog.setVisible(true);
		dialog.setTitle("Upload new words into the database");
		dialog.add(u_panel);

		table.setRowHeight(40);
		table.setCellSelectionEnabled(true);
		u_panel.add(table);
		u_panel.add(u_upload);
		u_panel.add(save);
		saveFile();
		
		u_upload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(getValueAt(1, 0));
            }
        });

	}
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
	    return true;
	}
	public void saveFile(){
		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");

				int userSelection = fileChooser.showSaveDialog(null);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					System.out.println("Save as file: "
							+ fileToSave.getAbsolutePath());
					try (FileWriter fw = new FileWriter(fileChooser
							.getSelectedFile())) {
						for (Object x : data) {
							//fw.write(x.toString());
							System.out.println(x);

						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	public Object getValueAt(int row, int col) { 
		return data[row][col];
    }

//	URL  url = new URL("ftp://user:pass@myftp.abc.com/myFile.txt;type=i");
//	URLConnection urlc = url.openConnection();
//	OutputStream os = urlc.getOutputStream(); // To upload
//	OutputStream buffer = new BufferedOutputStream(os);
//	ObjectOutput output = new ObjectOutputStream(buffer);
//	output.writeObject(myObject);
//	buffer.close();
//	os.close();
//	output.close();
	
}
