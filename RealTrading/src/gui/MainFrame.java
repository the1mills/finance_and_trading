package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clientConnection.CommissionReport;
import clientConnection.Contract;
import clientConnection.ContractDetails;
import clientConnection.EClientSocket;
import clientConnection.EWrapper;
import clientConnection.Execution;
import clientConnection.Order;
import clientConnection.OrderState;
import clientConnection.UnderComp;
import engine.TradingEngine;

public class MainFrame extends JFrame implements EWrapper {

	private static final long serialVersionUID = -5019191183341921917L;
	public EClientSocket m_client = new EClientSocket(this);
	private JButton connectToTWSButton = new JButton("Connect");
	private JButton disconnectToTWSButton = new JButton("Disconnect");
	private JButton startTradingButton = new JButton("Start");
	private JPanel northPanel = new JPanel();
	String faGroupXML;
	String faProfilesXML;
	String faAliasesXML;
	public String m_FAAcctCodes;
	public boolean m_bIsFAAccount = false;
	private boolean m_disconnectInProgress = false;
	private MainPanel mainPanel = null;
	String m_retIpAddress = "";
	int m_retPort = 7496;
	int m_retClientId = 0;
	boolean m_rc;
	private TradingEngine te = null;
	public Contract m_contract = null;
	public boolean m_snapshotMktData = true;
	public String m_genericTicks = "X";
	public int m_id = 0;
	
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
		mainPanel.add(startTradingButton, BorderLayout.SOUTH);
		
		
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
		te = new TradingEngine(this,m_id,m_contract,
        		m_genericTicks, m_snapshotMktData);
		
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

	@Override
	public void tickPrice(int tickerId, int field, double price,
			int canAutoExecute) {

		System.out.println("Implements: " + " tickerId: " + tickerId
				+ " field: " + field + " price: " + price
				+ "canAutoExecute: " + canAutoExecute);
	}

	@Override
	public void tickSize(int tickerId, int field, int size) {

	}

	@Override
	public void tickOptionComputation(int tickerId, int field,
			double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta,
			double undPrice) {

	}

	@Override
	public void tickGeneric(int tickerId, int tickType, double value) {

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

		System.out.println("Implements: " + "id: " + orderId + " status: " + status + " filled: " + filled
        		+ " remaining: " + remaining + " average fill price: " + avgFillPrice
        		+ " permId: " + permId + " parentId: " + parentId);
	}

	@Override
	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState) {

        System.out.println("Implements openOrder():  " + "orderId: " + order.m_orderId + " contract: " + contract.toString() + " order: " + order.toString() + " orderState: " + orderState.toString());
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

	}

}
