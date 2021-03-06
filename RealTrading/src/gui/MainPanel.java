package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;


public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7051494111911422190L;
	private JTextArea m_textArea = new JTextArea();
	private JScrollPane m_scrollPane = new JScrollPane(m_textArea);
	public static final Color textBackgroundColor = new Color(5, 5, 5);
	public static final Color textForegroundColor = new Color(0, 245, 0);
	public static final Font textComponentFont = new JList().getFont();
	public static final Color textCaretColor = Color.WHITE;
	public static final String lineSeparator = System
			.getProperty("line.separator");
	


	private final static String CRLF = "\r\n";
	private final static String LF = "\n";
	private final static String TAB = "\t";
	private final static String EIGHT_SPACES = "        ";
	private final static String EMPTY_STRING = "";

	public MainPanel(String title, boolean editable) {
		// TODO Auto-generated constructor stub
	        super(new BorderLayout());
	        if (title != null) {
	            Border border = BorderFactory.createTitledBorder( title);
	            setBorder(border);
	        }
	        m_textArea.setBackground(textBackgroundColor);
	        m_textArea.setForeground(textForegroundColor);
	        m_textArea.setFont(textComponentFont);
	        m_textArea.setCaretColor(textCaretColor);
	        m_textArea.setEditable(editable);
	        add(m_scrollPane,BorderLayout.CENTER);
	    }

	    public void clear() {
	        m_textArea.setText(EMPTY_STRING);
	    }

	    public void setText(String text) {
	        m_textArea.setText(text);
	        if (m_textArea.isEditable()) {
	            moveCursorToBeginning();
	        } else {
	            moveCursorToEnd();
	        }
	    }

	    public void setTextDetabbed(String text) {
	        m_textArea.setText(detabbed(text));
	    }

	    public String getText() {
	        return m_textArea.getText();
	    }

	    public void addString(String line) {
	        m_textArea.append(line + lineSeparator);
	        moveCursorToEnd();
	    }

	    public void moveCursorToEnd() {
	        m_textArea.setCaretPosition(m_textArea.getText().length());
	    }
	    
	    public void moveCursorToBeginning() {
	        m_textArea.setCaretPosition(0);
	    }
	    
	    public void add(Collection lines) {
	        for (Iterator iter = lines.iterator(); iter.hasNext(); ) {
	            addString((String)iter.next());
	        }
	    }
	    
	    public void addText(String text) {
	        add(tokenizedIntoArrayList(detabbed(text), LF));
	    }
	    
	    public static ArrayList<String> tokenizedIntoArrayList(String source, String delimiter) {
	        ArrayList list = new ArrayList();
	        StringTokenizer st = new StringTokenizer(source, delimiter);
	        while (st.hasMoreTokens()) {
	            String temp = st.nextToken();
	            list.add(temp);
	        }
	        return list;
	    }
	    
	    private String detabbed(String text) {
	        return text.replaceAll(TAB, EIGHT_SPACES);
	    }
}
