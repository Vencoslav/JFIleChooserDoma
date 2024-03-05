import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class JFileChooserForm extends JFrame {
    private JPanel panelMain;
    private JTextArea txt;
    private JScrollPane scroll;
    private File selectedFile;

    public JFileChooserForm(){
        setContentPane(panelMain);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("JFileChooser");
        setSize(700,700);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem loadItem = new JMenuItem("Load");
        file.add(loadItem);
        loadItem.addActionListener(e -> ChooseFile());

        JMenuItem saveItem = new JMenuItem("Save");
        file.add(saveItem);
        saveItem.addActionListener(e -> SaveToFile(selectedFile));

        JMenuItem saveAsItem = new JMenuItem("Save As");
        file.add(saveAsItem);
        saveAsItem.addActionListener(e -> SaveAs());

        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChooseFile();
            }
        });
    }
    public void ChooseFile() {
        JFileChooser fc = new JFileChooser(".");
//        fc.setFileFilter(new FileNameExtensionFilter("Project files", "txt")); //Filter který ti nastaví aby šlo pouze otvírat soubory s příponou txt
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {//Kontroluje, zda uživatel vybral soubor
            selectedFile = fc.getSelectedFile();
            StringBuilder content = new StringBuilder();
            try (Scanner sc = new Scanner(new BufferedReader(new FileReader(selectedFile)))) {
                while (sc.hasNextLine()) {
                    content.append(sc.nextLine()).append("\n"); // \ = podrž alt + 9 a 2
                }
                txt.setText(String.valueOf(content));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "File not found: " + e.getLocalizedMessage());
            }
        }
    }

    public void SaveToFile(File file) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            pw.write(txt.getText());
            JOptionPane.showMessageDialog(this, "File saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Problem with writing into the file: " + e.getLocalizedMessage());
        }
    }

    public void SaveAs() {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("Project files", "txt"));
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            SaveToFile(selectedFile);
        }
    }
}
