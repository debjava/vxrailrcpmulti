package com.ddlab.rcp.actions;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormPage;

public class NextActionHandlerImpl implements ActionHandler {

  private FormPage page;
  private String nextPageId;

  public NextActionHandlerImpl(FormPage page, String nextPageId) {
    this.page = page;
    this.nextPageId = nextPageId;
  }

  @Override
  public String getName() {
    return "Next";
  }

  @Override
  public String getToolTip() {
    return "Go to next view";
  }

  @Override
  public String getImage() {
    return "/icons/next24.png";
  }

  @Override
  public void execute() {
    page.getEditor().setActivePage(nextPageId);
  }
}
