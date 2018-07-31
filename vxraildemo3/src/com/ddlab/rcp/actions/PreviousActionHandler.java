package com.ddlab.rcp.actions;

import org.eclipse.ui.forms.editor.FormPage;

public class PreviousActionHandler implements ActionHandler {

  private FormPage page;
  private String prevPageId;

  public PreviousActionHandler(FormPage page, String nextPageId) {
    this.page = page;
    this.prevPageId = nextPageId;
  }

  @Override
  public String getName() {
    return "Previous";
  }

  @Override
  public String getToolTip() {
    return "Go to previous view";
  }

  @Override
  public String getImage() {
    return "/icons/prev24.png";
  }

  @Override
  public void execute() {
    page.getEditor().setActivePage(prevPageId);
  }
}
