package db4oStuff;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import jtableStuff.MakeJTable;

import org.apache.commons.lang3.text.WordUtils;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;



public class DB4oConnect {

	private String filename;
	private ObjectContainer db = null;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public ObjectContainer getDb() {
		return db;
	}

	public void setDb(ObjectContainer db) {
		this.db = db;
	}
	

	
	public DB4oConnect() {
		filename = "C:/Users/denman/db4oStuff/jonny.db4o";

		if (db == null) {
			db = Db4oEmbedded.openFile(filename);
		}

	}

	public void insertIntoDatabase(Class className, Object o) {

		db.store(o);
	}
	
	public void deleteFromDatabase(Class className, Object o) {

		  ObjectSet x = null;
		   Query q = db.query();
		    
	        // Constrain query
	        q.constrain(o.getClass());
	        
	        // Descend to battingAverage field and constrain value to be > 0.3
	     //   q.descend("profit").constrain(new Float(7000f)).greater();
	        
	        x = q.execute();
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	        Date d = null;
			try {
				d = sdf.parse("12-03-2012 18:35:20");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(d != null){
	        q.descend("dateField").constrain(d).greater();
	        
	        while(x.hasNext()){
	        	db.delete(x.next());
	        }
			}
		
	}

	public void openTable(Object o) {
		
		
				Field[] columnFields = o.getClass().getDeclaredFields();
				String[] columnNames = new String[columnFields.length];
				
				for(int i = 0; i < columnNames.length; i++){
					columnNames[i] = columnFields[i].getName();
				}
				
				// Create some data
//				String dataValues[][] = { { "12", "234", "67" },
//						{ "-123", "43", "853" }, { "93", "89.2", "109" },
//						{ "279", "9033", "3092" } };
				
				
//				  ObjectSet<Class<?>> x  = (ObjectSet<Class<?>>) db.query(o.getClass());
			       
				  ObjectSet<Class<?>> x = null;
				  
				   Query q = db.query();
				    
			        // Constrain query
			        q.constrain(o.getClass());
			        
			        // Descend to battingAverage field and constrain value to be > 0.3
			     //   q.descend("profit").constrain(new Float(7000f)).greater();
			        
			        x = q.execute();
				  
				  Object[][] dataValues = new Object[x.size()][columnNames.length];
				  
				  int i = 0;
			        while(x.hasNext()) {

			        	Object p =  x.next();
			        	for(int j = 0; j < columnFields.length; j++){
			        		try {
								dataValues[i][j] = p.getClass().getDeclaredField(columnFields[j].getName()).toString();
								String nameOfField = p.getClass().getDeclaredField(columnFields[j].getName()).getName();
								nameOfField = WordUtils.capitalize(nameOfField);
								Method m = null;
								try {
								    m = p.getClass().getMethod("get"+nameOfField, null);
								} catch (NoSuchMethodException e) {
									e.printStackTrace();
									dataValues[i][j] =  new String("(null)");
									continue;
								}
								Object temp = null;
								try {
									temp = m.invoke(p, null);
									if(temp == null){
										dataValues[i][j] = new String("(null)");
									}
									else if(temp instanceof JCheckBox){
										dataValues[i][j] = temp.toString();
									}
									else if(temp instanceof JPanel){
										System.out.println(i);
										JPanel jx = new JPanel();
										JCheckBox jc = new JCheckBox();
										jc.setBackground(Color.blue);
										dataValues[i][j] =jc;
									}
									else{
									dataValues[i][j] = temp.toString();
									}
									
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (NoSuchFieldException e) {   
								e.printStackTrace();
							}
			        	}
			            i++;
			        }
			        
				
			    MakeJTable mainFrame = new MakeJTable(columnNames,dataValues);
				mainFrame.setVisible(true);

	}

}