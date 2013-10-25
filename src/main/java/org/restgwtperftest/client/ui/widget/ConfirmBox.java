package org.restgwtperftest.client.ui.widget;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ButtonToolbar;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class ConfirmBox extends DialogBox
{

   private ConfirmBox(String title)
   {
      super();
      setText(title);
      setAnimationEnabled(true);
      setAutoHideOnHistoryEventsEnabled(true);
      setGlassEnabled(true);
      setModal(true);
   }

   private ConfirmBox(String title, Widget content)
   {
      this(title);
      setAutoHideEnabled(true);
      VerticalPanel panel = new VerticalPanel();
      panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      panel.add(content);
      ButtonToolbar buttonToolbar = new ButtonToolbar();
      buttonToolbar.add(createCloseButton());
      panel.add(buttonToolbar);
      setWidget(panel);
   }

   private ConfirmBox( String title, Widget content, final UserCallback userCallback)
   {
      this(title);
      VerticalPanel panel = new VerticalPanel();
      panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      panel.add(content);
      ButtonToolbar buttonToolbar = new ButtonToolbar();
      buttonToolbar.add(createConfirmButton(userCallback));
      buttonToolbar.add(createCancelButton(userCallback));
      panel.add(buttonToolbar);
      setWidget(panel);
   }

   private Button createCloseButton()
   {
      Button button = new Button("OK");
      button.addClickHandler(new ClickHandler()
      {
         @Override
         public void onClick(ClickEvent event)
         {
            hide();
         }
      });
      return button;
   }

   private Button createConfirmButton( final UserCallback userCallback)
   {
      Button confirmButton = new Button("OK");
      confirmButton.addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            hide();
            userCallback.onConfirm();
         }
      });
      return confirmButton;
   }

   private Button createCancelButton( final UserCallback userCallback)
   {
      Button cancelButton = new Button("Cancel");
      cancelButton.addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            hide();
            userCallback.onCancel();
         }
      });
      return cancelButton;
   }

   public interface UserCallback
   {
      void onConfirm();

      void onCancel();
   }

   public static void alert(String title, String htmlContent)
   {
      new ConfirmBox( title, new HTMLPanel(htmlContent)).center();

   }

   public static void confirm(String title, String htmlContent, UserCallback userCallback)
   {
      new ConfirmBox(title, new HTMLPanel(htmlContent), userCallback).center();
   }

}
