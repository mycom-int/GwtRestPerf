package org.restgwtperftest.client.ui;

import javax.annotation.Nullable;

import org.restgwtperftest.client.cases.Case;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

//extract presenter if needed.... 
public class CaseView extends Composite {
	@UiTemplate("CaseView.ui.xml")
	public interface Binder extends UiBinder<Widget, CaseView> {
	}
	
	public interface Factory{
		CaseView create(Case caze);
	}
	@Nullable
	@UiField
	Label caseNameLb;
	@Nullable
	@UiField
	TextBox threadAmountTxb;
	@Nullable
	@UiField
	TextBox beanAmountTxb;
	@Nullable
	@UiField
	TextBox sleepMillisTxb;
	
	private Case caze;
	@Inject
	public CaseView(Binder uiBinder, @Assisted Case caze) {
		initWidget(uiBinder.createAndBindUi(this));
		this.caze = caze;
		caseNameLb.setText(caze.getName());
	}
	private int threadAmount;
	private int beanAmount;
	private int sleepMillis;
	private void initValues(){
		threadAmount = Integer.parseInt(threadAmountTxb.getText());
		beanAmount = Integer.parseInt(beanAmountTxb.getText());
		sleepMillis = Integer.parseInt(sleepMillisTxb.getText());
	}
	@UiHandler("findallBigBeansBtn")
	public void onStartFindBeans(ClickEvent event)
	{
		initValues();
		caze.findBigBeans(threadAmount, beanAmount, 300);
	}
	
	@UiHandler("doLongOpBtn")
	public void onDoLongOpetration(ClickEvent event)
	{
		initValues();
		caze.doLongOperation(threadAmount, sleepMillis, 300);
	}
}
