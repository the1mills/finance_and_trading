package gui;


/*Specifies the type of price. Pass the field value into TickType.getField(int tickType) to retrieve the field description.  For example, a field value of 1 will map to bidPrice, a field value of 2 will map to askPrice, etc.

·          1 = bid

·          2 = ask

·          4 = last

·          6 = high

·          7 = low

·          9 = close*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clientConnection.CommissionReport;
import clientConnection.Contract;
import clientConnection.ContractDetails;
import clientConnection.EClientSocket;
import clientConnection.EWrapper;
import clientConnection.EWrapperMsgGenerator;
import clientConnection.Execution;
import clientConnection.Order;
import clientConnection.OrderState;
import clientConnection.UnderComp;
import engine.NewTradingEngine;
import engine.TradingEngine;

public class MainFrame extends JFrame implements EWrapper {

	private static final long serialVersionUID = -5019191183341921917L;
	public EClientSocket m_client = new EClientSocket(this);
	private JButton connectToTWSButton = new JButton("Connect");
	private JButton disconnectToTWSButton = new JButton("Disconnect");
	private JButton startTradingButton = new JButton("Start");
	private JButton closeOutButton = new JButton("Close out");
	public JButton pauseButton = new JButton("Pause");
	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	String faGroupXML;
	String faProfilesXML;
	String faAliasesXML;
	public String m_FAAcctCodes;
	public boolean m_bIsFAAccount = false;
	private boolean m_disconnectInProgress = false;
	private MainPanel mainPanel = null;
	String m_retIpAddress = "";
	int m_retPort = 7496;
	int m_retClientId = 3;
	boolean m_rc;
	private TradingEngine te = null;
//	private TradingEngine te = null;
	private NewTradingEngine te_new = new NewTradingEngine(this);;
	public Contract m_contract = null;
	public boolean m_snapshotMktData = true;
	public String m_genericTicks = "X";
	public int m_id = 0;
	public boolean isPaused = false;
	
	//public WriteToTextFile wttf = new WriteToTextFile();
	
	private IBTextPanel m_TWS = new IBTextPanel("TWS Server Responses", false);
	
	
	public MainFrame() {
		mainPanel = new MainPanel("eee", true);
		northPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		this.add(m_TWS,BorderLayout.EAST);
		m_TWS.setPreferredSize(new Dimension(500,500));
		mainPanel.add(northPanel, BorderLayout.NORTH);
		northPanel.add(connectToTWSButton, BorderLayout.NORTH);
		northPanel.add(disconnectToTWSButton, BorderLayout.SOUTH);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(startTradingButton,BorderLayout.NORTH);
		southPanel.add(closeOutButton,BorderLayout.SOUTH);
		southPanel.add(pauseButton,BorderLayout.CENTER);
		
		
		
		disconnectToTWSButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				onClosingFrame();
				
			}
			
		});
		
		connectToTWSButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				onConnect();
				
			}
			
		});
		
		startTradingButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startTrading();
				
			}
			
		});
		
//		closeOutButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				onCloseOut();
//				
//			}
//
//			private void onCloseOut() {
//				
//				System.out.println("Close out position(s)...");
//				te_new.keepLooping = false;
//			}
//			
//		});
//		
//		pauseButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//
//				if (isPaused == false) {
//					isPaused = true;
//					pauseButton.setForeground(Color.red);
//					pauseButton.setText("Resume");
//				} else {
//					isPaused = false;
//					pauseButton.setForeground(Color.black);
//					pauseButton.setText("Pause");
//				}
//
//			}
//
//		});
		
		m_contract = new Contract();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();

	}
	
	private int onClosingFrame(){
		
		m_client.eDisconnect();
		this.dispose();
		System.exit(0);
		return 0;
		
	}
	
	private void startTrading(){
//		te = new TradingEngine(this,m_id,m_contract,
//        		m_genericTicks, m_snapshotMktData);
		
		te_new.startThisSucker();
		
//		Runnable r = new Runnable(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				te_new.startThisSucker();
//			}
//			
//		};
		
	//	r.run();
	}

	void onConnect() {
		m_bIsFAAccount = false;
		m_disconnectInProgress = false;

		m_client.eConnect(m_retIpAddress, m_retPort, m_retClientId);
		if (m_client.isConnected()) {
			mainPanel.addString("Connected to Tws server version "
					+ m_client.serverVersion() + " at "
					+ m_client.TwsConnectionTime());
		}
	}
	
	  void onDisconnect() {
	        // disconnect from TWS
	        m_disconnectInProgress = true;
	        m_client.eDisconnect();
	    }

	@Override
	public void tickPrice(int tickerId, int field, double price,
			int canAutoExecute) {
		
		Double[] x = te_new.getCurrentPriceArray().get(te_new.getCurrentPriceArray().size()-1);
		
		te_new.getPrices().add(price);
		
		switch (field) {
        case 1:  x[0] = price;
                 break;
        case 2:  x[1] = price;
                 break;
        case 4: x[2] = price;
                 break;
        case 6:  x[3] = price;
                 break;
        case 7: x[4] = price;
                 break;
        case 9:  x[5] = price;
        
//        default: x[6] = price;
//                 break;
    }
		
		
		if(field == 1 || field == 2){
		System.out.println("Implements (tickPrice): " + " tickerId: " + tickerId
				+ " field: " + field + " price: " + price
				+ "canAutoExecute: " + canAutoExecute);
		}
		
		boolean isss = true;
		for(int i = 0; i < 6; i++){
			if(x[i] == null){
				isss = false;
				break;
			}
		}
		te_new.newMarketDataReceived = isss;
		
		if(isss == true){
			System.out.println("");
		}
		
//		Date date = new Date();
//		wttf.writeToTextFile("");
//		wttf.writeToTextFile(date.toString());
//		wttf.writeToTextFile("Implements (tickPrice): " + " tickerId: " + tickerId
//				+ " field: " + field + " price: " + price
//				+ "canAutoExecute: " + canAutoExecute);
		
		
	}

	@Override
	public void tickSize(int tickerId, int field, int size) {
		
//		System.out.println("Implements (tickSize): " + " tickerId: " + tickerId
//				+ " field: " + field + " size: " + size);

	}



	@Override
	public void tickGeneric(int tickerId, int tickType, double value) {

//		System.out.println("Implements (tickGeneric): " + " tickerId: " + tickerId
//				+ " tickType: " + tickType + " value: " + value);

	}

	@Override
	public void tickString(int tickerId, int tickType, String value) {

	}

	@Override
	public void tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry) {

	}

	@Override
	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {

		
		System.out.println("Implements OrderStatus: " + "id: " + orderId + " status: " + status + " filled: " + filled
        		+ " remaining: " + remaining + " average fill price: " + avgFillPrice
        		+ " permId: " + permId + " parentId: " + parentId);
		
		if(filled > 0 && remaining == 0){
			te_new.setBuyOrderCompleted(true);
			}
	}

	@Override
	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState) {

     //   System.out.println("Implements4321 openOrder():  " + "orderId: " + order.m_orderId + " contract: " + contract.toString() + " order: " + order.toString() + " orderState: " + orderState.toString());
	}

	@Override
	public void openOrderEnd() {

	}

	@Override
	public void updateAccountValue(String key, String value, String currency,
			String accountName) {

	}

	@Override
	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {

	}

	@Override
	public void updateAccountTime(String timeStamp) {

	}

	@Override
	public void accountDownloadEnd(String accountName) {

	}

	@Override
	public void nextValidId(int orderId) {

	}

	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {

	}

	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {

	}

	@Override
	public void contractDetailsEnd(int reqId) {

	}

	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {

	}

	@Override
	public void execDetailsEnd(int reqId) {

	}

	@Override
	public void updateMktDepth(int tickerId, int position, int operation,
			int side, double price, int size) {

	}

	@Override
	public void updateMktDepthL2(int tickerId, int position,
			String marketMaker, int operation, int side, double price, int size) {

	}

	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange) {

	}

	@Override
	public void managedAccounts(String accountsList) {

	}

	@Override
	public void receiveFA(int faDataType, String xml) {

	}

	@Override
	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps) {

	}

	@Override
	public void scannerParameters(String xml) {

	}

	@Override
	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {

	}

	
	 public void tickOptionComputation( int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend,
		        double gamma, double vega, double theta, double undPrice) {
		        // received computation tick
		        String msg = EWrapperMsgGenerator.tickOptionComputation( tickerId, field, impliedVol, delta, optPrice, pvDividend,
		            gamma, vega, theta, undPrice);
	//	        m_tickers.add( msg );
		    }
	 
	@Override
	public void scannerDataEnd(int reqId) {

	}

	@Override
	public void realtimeBar(int reqId, long time, double open, double high,
			double low, double close, long volume, double wap, int count) {

	}

	@Override
	public void currentTime(long time) {

	}

	@Override
	public void fundamentalData(int reqId, String data) {

	}

	@Override
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {

	}

	@Override
	public void tickSnapshotEnd(int reqId) {

	}

	@Override
	public void marketDataType(int reqId, int marketDataType) {

	}

	@Override
	public void commissionReport(CommissionReport commissionReport) {

	}

	@Override
	public void error(Exception e) {

	}

	@Override
	public void error(String str) {

	}

	@Override
	public void error(int id, int errorCode, String errorMsg) {

	}

	@Override
	public void connectionClosed() {
		
		System.out.println("Connection Closed...");

	}

}
