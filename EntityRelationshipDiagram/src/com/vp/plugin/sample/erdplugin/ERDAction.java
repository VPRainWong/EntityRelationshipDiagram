package com.vp.plugin.sample.erdplugin;
import java.awt.Point;
import com.vp.plugin.ApplicationManager;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.IDiagramTypeConstants;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.diagram.shape.IDBTableUIModel;
import com.vp.plugin.model.IDBColumn;
import com.vp.plugin.model.IDBForeignKey;
import com.vp.plugin.model.IDBForeignKeyConstraint;
import com.vp.plugin.model.IDBTable;
import com.vp.plugin.model.factory.IModelElementFactory;

public class ERDAction implements VPActionController {

	@Override
	public void performAction(VPAction  arg0) {
		//Create blank ER Diagram
		DiagramManager diagrammanager = ApplicationManager.instance().getDiagramManager();
		IDiagramUIModel erd = (IDiagramUIModel) diagrammanager.createDiagram(IDiagramTypeConstants.DIAGRAM_TYPE_ER_DIAGRAM);
		erd.setName("Simple ER Diagram");
		
		
		//Create table for retail centers
		IDBTable tableRetailCenter = IModelElementFactory.instance().createDBTable();
		tableRetailCenter.setName("Retail Center");
		//Create the table shape on diagram
		IDBTableUIModel shapeRetailCenter = (IDBTableUIModel) diagrammanager.createDiagramElement(erd, tableRetailCenter);
		shapeRetailCenter.setBounds(50,50,175,75);
		//Call to re-calculate caption position when render the diagram
		shapeRetailCenter.resetCaption();
		
		//Create the ID column under Retail Center table
		IDBColumn retailCenterID = tableRetailCenter.createDBColumn();
		retailCenterID.setName("ID");
		//Make it a primary key/ a member of a composite key
		retailCenterID.setPrimaryKey(true);
		//This attribute cannot be empty in any record
		retailCenterID.setNullable(false);
		//The attribute is an Integer with maximum 10 digits with no scale 
		retailCenterID.setType("integer", 10, 0);
		
		IDBColumn retailCenterType = tableRetailCenter.createDBColumn();
		retailCenterType.setName("Type");
		retailCenterType.setType("varchar",255,0);
		
		IDBColumn retailCenterAddress  = tableRetailCenter.createDBColumn();
		retailCenterAddress.setName("Address");
		retailCenterAddress.setType("varchar",255,0);
		
		//Create table for shipped items
		IDBTable tableShippedItem = IModelElementFactory.instance().createDBTable();
		tableShippedItem.setName("Shipped Item");
		IDBTableUIModel shapeShippedItem = (IDBTableUIModel)diagrammanager.createDiagramElement(erd, tableShippedItem);
		shapeShippedItem.setBounds(275, 50, 225, 150);
		shapeShippedItem.resetCaption();
		
		IDBColumn shippedItemItemNum = tableShippedItem.createDBColumn();
		shippedItemItemNum.setName("Item Num");
		shippedItemItemNum.setType("integer",10,0);
		shippedItemItemNum.setPrimaryKey(true);
		shippedItemItemNum.setNullable(false);
		
		
		//Create a foreign key
		IDBForeignKey shippedItemRetailCenterID = IModelElementFactory.instance().createDBForeignKey();
		//The foreign key is connecting from the retail Center table...
		shippedItemRetailCenterID.setFrom(tableRetailCenter);
		//to the Shipped Item table
		shippedItemRetailCenterID.setTo(tableShippedItem);
		//Create a constraint
		IDBForeignKeyConstraint RetailCenterIdConstraint = IModelElementFactory.instance().createDBForeignKeyConstraint();
		//The new foreign key will be referencing the ID column in the Retail Center table
		RetailCenterIdConstraint.setRefColumn(retailCenterID);
		RetailCenterIdConstraint.setForeignKey(shippedItemRetailCenterID);
		
		//Create the connector
		diagrammanager.createConnector(erd, shippedItemRetailCenterID, shapeRetailCenter, shapeShippedItem, new Point[] {new Point (225,80), new Point (275,80)});

		
		IDBColumn shippedItemWeight = tableShippedItem.createDBColumn();
		shippedItemWeight.setName("Weight");
		shippedItemWeight.setType("numeric", 19, 0);
		
		IDBColumn shippedItemDimension = tableShippedItem.createDBColumn();
		shippedItemDimension.setName("Dimension");
		shippedItemDimension.setType("numeric", 19, 0);
		
		IDBColumn shippedItemInsuranceAmt = tableShippedItem.createDBColumn();
		shippedItemInsuranceAmt.setName("InsuranceAmt");
		shippedItemInsuranceAmt.setType("numeric", 19, 0);
		
		IDBColumn shippedItemDestination = tableShippedItem.createDBColumn();
		shippedItemDestination.setName("Destination");
		shippedItemDestination.setType("varchar", 255, 0);
		
		IDBColumn shippedItemFinalDeliveryDate = tableShippedItem.createDBColumn();
		shippedItemFinalDeliveryDate.setName("FinalDeliveryDate");
		shippedItemFinalDeliveryDate.setType("date",0, 0);
		
		
		//Create entity for transportation events
		IDBTable TableTransportationEvent = IModelElementFactory.instance().createDBTable();
		TableTransportationEvent.setName("Transportation Event");
		IDBTableUIModel shapeTransportationEvent = (IDBTableUIModel) diagrammanager.createDiagramElement(erd, TableTransportationEvent);
		shapeTransportationEvent.setBounds(550, 50, 200, 75);
		shapeTransportationEvent.resetCaption();
		
		IDBColumn transportationEventSeqNumber = TableTransportationEvent.createDBColumn();
		transportationEventSeqNumber.setName("Seq Number");
		transportationEventSeqNumber.setType("integer", 10, 0);
		transportationEventSeqNumber.setPrimaryKey(true);
		transportationEventSeqNumber.setNullable(false);
		
		IDBColumn transportationEventType = TableTransportationEvent.createDBColumn();
		transportationEventType.setName("Type");
		transportationEventType.setType("varchar", 255, 0);
		
		IDBColumn transportationDeliveryRoute = TableTransportationEvent.createDBColumn();
		transportationDeliveryRoute.setName("Delivery Route");
		transportationDeliveryRoute.setType("varchar", 255, 0);
		
		
		//Create entity for transportations
		IDBTable tableItemTransportation = IModelElementFactory.instance().createDBTable();
		tableItemTransportation.setName("Transportation");
		IDBTableUIModel shapeItemTransportation = (IDBTableUIModel) diagrammanager.createDiagramElement(erd, tableItemTransportation);
		shapeItemTransportation.setBounds(385, 250, 285, 60);
		shapeItemTransportation.resetCaption();
		
		//Create a foreign key as usual
		IDBForeignKey transportationTransportationSeqNum = IModelElementFactory.instance().createDBForeignKey();
		transportationTransportationSeqNum.setFrom(TableTransportationEvent);
		transportationTransportationSeqNum.setTo(tableItemTransportation);
		IDBForeignKeyConstraint seqNumConstraint = IModelElementFactory.instance().createDBForeignKeyConstraint();
		seqNumConstraint.setRefColumn(transportationEventSeqNumber);
		seqNumConstraint.setForeignKey(transportationTransportationSeqNum);
		//Make the key both Primary and Foreign
		transportationTransportationSeqNum.setIdentifying(true);
		
		//connector
		diagrammanager.createConnector(erd, transportationTransportationSeqNum, shapeTransportationEvent, shapeItemTransportation, new Point[] {new Point(600,125), new Point(600,250)});
		
		IDBForeignKey transportationShippedItemNum = IModelElementFactory.instance().createDBForeignKey();
		transportationShippedItemNum.setFrom(tableShippedItem);
		transportationShippedItemNum.setTo(tableItemTransportation);
		IDBForeignKeyConstraint shippedItemIdConstraint = IModelElementFactory.instance().createDBForeignKeyConstraint();
		shippedItemIdConstraint.setRefColumn(shippedItemItemNum);
		shippedItemIdConstraint.setForeignKey(transportationShippedItemNum);
		transportationShippedItemNum.setIdentifying(true);
		
		//connector
		diagrammanager.createConnector(erd, transportationShippedItemNum, shapeShippedItem, shapeItemTransportation, new Point[] {new Point (425,200), new Point (425,250)});

		
		// Show up the diagram
		diagrammanager.openDiagram(erd);
	}

	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}

}