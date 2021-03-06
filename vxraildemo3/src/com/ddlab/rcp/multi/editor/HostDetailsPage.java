package com.ddlab.rcp.multi.editor;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.ddlab.rcp.actions.ActionUtil;
import com.ddlab.rcp.actions.NextActionHandlerImpl;
import com.ddlab.rcp.actions.PreviousActionHandler;
import com.ddlab.rcp.actions.SysInfoNextHandlerImpl;

public class HostDetailsPage extends FormPage {
  private static String ID = "hostDetail";
  private static String TITLE = "Host Details";

  public HostDetailsPage(FormEditor editor, String id, String title) {
    super(editor, id, title);
  }

  public HostDetailsPage(FormEditor editor) {
    super(editor, ID, TITLE);
  }

  @Override
  protected void createFormContent(IManagedForm managedForm) {
    ScrolledForm mainForm = managedForm.getForm();
    mainForm.setText(TITLE);
    FormToolkit toolkit = managedForm.getToolkit();
    toolkit.decorateFormHeading(mainForm.getForm());

    setFormAction(mainForm.getForm());
  }

  private void setFormAction(Form form) {
    String helpDesc = "Help message for this panel";
    Action helpAction = ActionUtil.getHelpAction(form.getShell(), helpDesc);
    Action nextAction = ActionUtil.getAction(form.getShell(), new NextActionHandlerImpl(this, "virtualization"));
    Action prevAction = ActionUtil.getAction(form.getShell(), new PreviousActionHandler(this, "networkservices"));

    form.getToolBarManager().add(prevAction);
    form.getToolBarManager().add(nextAction);
    form.getToolBarManager().add(helpAction);

    form.getToolBarManager().update(true);
    form.getMenuManager().update(true);
  }
}
