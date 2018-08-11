package com.ddlab.rcp.actions;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.nebula.widgets.opal.tipoftheday.TipOfTheDay;
import org.eclipse.nebula.widgets.opal.tipoftheday.TipOfTheDay.TipStyle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.ddlab.rcp.core.Activator;

public class TipOfTheDayContributionItem extends ContributionItem {

  private Button tipOfTheDayBtn = null;
  private TipOfTheDay tip = new TipOfTheDay();

  public TipOfTheDayContributionItem(String id) {
    super(id);
    tip.setStyle(TipStyle.TWO_COLUMNS);
    //  tip.setStyle(TipStyle.TWO_COLUMNS_LARGE);
  }

  public void fill(final Composite parent) {
    tipOfTheDayBtn = new Button(parent, SWT.NONE);
    tipOfTheDayBtn.setToolTipText("Tip of The Day");
    Image tipOfTheDayImg = Activator.getImageDescriptor("/icons/tip16.png").createImage();
    tipOfTheDayBtn.setImage(tipOfTheDayImg);
    addTipOfTheDayContents();
    tipOfTheDayBtn.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            Shell shell = parent.getShell();
//            Image tipImg = Activator.getImageDescriptor("icons/tip16.png").createImage();
//            shell.setImage(tipImg);
            tip.open(shell);
          }
        });
  }

  private void addTipOfTheDayContents() {
    String msg1 = "<b>You have comprehensive information about the product in the help section</b>";
    String msg2 = "<b><u>A robotic guidance is also provided to you.</u></b>";
    String msg3 = "This is the next generation bullet proof product";

    tip.addTip(msg1);
    tip.addTip(msg2);
    tip.addTip(msg3);
  }
}
