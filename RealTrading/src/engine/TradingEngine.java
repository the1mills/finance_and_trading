package engine;

import gui.MainFrame;
import clientConnection.Contract;
import clientConnection.Order;
import clientConnection.UnderComp;

public class TradingEngine {

	private String m_faGroup;
	private String m_faProfile;
	private String m_faMethod;
	private String m_faPercentage;
	public String m_genericTicks;
	public boolean m_snapshotMktData;
	final static String ALL_GENERIC_TICK_TAGS = "100,101,104,105,106,107,165,221,225,233,236,258,293,294,295,318";
	final static int OPERATION_INSERT = 0;
	final static int OPERATION_UPDATE = 1;
	final static int OPERATION_DELETE = 2;
	final static int SIDE_ASK = 0;
	final static int SIDE_BID = 1;
	public boolean m_rc;
	public int m_id;
	public String m_backfillEndTime;
	public String m_backfillDuration;
	public String m_barSizeSetting;
	public int m_useRTH;
	public int m_formatDate;
	public int m_marketDepthRows;
	public String m_whatToShow;
	public Contract m_contract = null;
	public Order m_order = new Order();
	public UnderComp m_underComp = new UnderComp();
	public int m_exerciseAction;
	public int m_exerciseQuantity;
	public int m_override;
	public int m_marketDataType;

	private MainFrame mf = null;
	
	
	public TradingEngine(MainFrame mf, int m_id, Contract m_contract,
    		 String m_genericTicks, boolean m_snapshotMktData) {
		
		this.m_id = m_id;
		this.m_contract = m_contract;
		this.m_genericTicks = m_genericTicks;
		this.m_snapshotMktData = m_snapshotMktData;
		this.mf = mf;
		
//	 	mf.m_client.reqMktData(m_id, m_contract,
//        		m_genericTicks, m_snapshotMktData);
	 
	// 	mf.m_client.cancelMktData(m_id);
	 	
	 	Order order = new Order();
	 	
	 	order.m_minQty= 50;
	 	order.m_orderId = 10;
	 	order.m_clientId = 0;
	 	order.m_orderType ="MKT";
	 	order.m_totalQuantity = 100;
	 	order.m_action = "BUY";
	 	
	 	Contract contract = new Contract();
	 	
	 	contract.m_currency ="USD";
	 	contract.m_exchange = "SMART";
	 	contract.m_symbol = "GOOG";
	 	contract.m_secType = "STK";
	
	//	mf.m_client.placeOrder(order.m_orderId, contract, order);
		mf.m_client.reqMktData(100, contract,
        		"es", true);
		
	 	try {
	//		mf.m_client.placeOrder(order.m_orderId, contract, order);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	
	 	
//	 	mf.m_client.
	 	
	 	
	 	
	 	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
//	 	mf.orderStatus(orderId, status, filled, remaining, avgFillPrice, permId, parentId, 
//	 			lastFillPrice, clientId, whyHeld);
	 	
//	 	mf.m_client.cancelOrder(order.m_orderId);
	 	
	}
	
	



}
