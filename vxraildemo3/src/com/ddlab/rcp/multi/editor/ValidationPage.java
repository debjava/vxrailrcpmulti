package com.ddlab.rcp.multi.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.ddlab.rcp.actions.ActionUtil;
import com.ddlab.rcp.actions.PreviousActionHandler;
import com.ddlab.rcp.actions.SysInfoNextHandlerImpl;

public class ValidationPage extends FormPage {
  private static String ID = "validation";
  private static String TITLE = "Validation";

  public ValidationPage(FormEditor editor, String id, String title) {
    super(editor, id, title);
  }

  public ValidationPage(FormEditor editor) {
    super(editor, ID, TITLE);
  }

  @Override
  protected void createFormContent(IManagedForm managedForm) {
    ScrolledForm mainForm = managedForm.getForm();
    mainForm.setText(TITLE);
    FormToolkit toolkit = managedForm.getToolkit();
    toolkit.decorateFormHeading(mainForm.getForm());

    setFormAction(mainForm.getForm());

    Form form = mainForm.getForm();

    Section section1 = createSection1(form, toolkit);

  }

  private Section createSection1(Form form, FormToolkit toolkit) {
    Section section =
        toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
    section.setVisible(true);
    section.setEnabled(true);
    section.setBounds(1, 10, 465, 330);

    section.setText("Validation");
    section.setDescription("All validations will be performed.");
    section.setToolTipText("validation");
    toolkit.createCompositeSeparator(section);

    Composite dnsComposite = toolkit.createComposite(section, SWT.None);
    // Create a layout of 3 columns
    GridLayout hl = new GridLayout(3, false);
    hl.horizontalSpacing = 10;
    hl.verticalSpacing = 10;
    dnsComposite.setLayout(hl);

    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
    Label l1 = new Label(dnsComposite, SWT.NONE);
    l1.setText("Some text :");
    l1.setFont(boldFont);
    l1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    
    
    Button btn = new Button(dnsComposite, SWT.None);
    btn.setText("Validate");
    btn.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
    btn.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            try {
              show13();
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }
        });
    
    
    Button errBtn1 = new Button(dnsComposite, SWT.None);
    errBtn1.setText("Show Error Type1");
    errBtn1.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

    errBtn1.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
        	  
        	  Status status = new Status(IStatus.ERROR, "My Plug-in ID", 0,
        	            "Status Error Message", null);
        	  ErrorDialog.openError( new Shell(), "Error Info", "Critical error", status);
          }
        });
    
    
    Button detailedErrBtn = new Button(dnsComposite, SWT.None);
    detailedErrBtn.setText("Show Detailed Error");
    detailedErrBtn.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

    detailedErrBtn.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
        	  try {
        		  throw new NullPointerException("Values can not be null");
        	  }
        	  catch(Exception ex) {
//        		  ex.printStackTrace();
        		  MultiStatus status = createMultiStatus(ex.getLocalizedMessage(), ex);
        		  ErrorDialog.openError(new Shell(), "Error", "This is an error", status);
        	  }
        	  
          }
        });
    
    
    
    

    section.setClient(dnsComposite); // Important
    section.requestLayout();

    return section;
  }
  
  private static MultiStatus createMultiStatus(String msg, Throwable t) {

      List<Status> childStatuses = new ArrayList<>();
      StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

      for (StackTraceElement stackTrace: stackTraces) {
          Status status = new Status(IStatus.ERROR,
                  "com.some.id", stackTrace.toString());
          childStatuses.add(status);
      }

      MultiStatus ms = new MultiStatus("com.example.e4.rcp.todo",
              IStatus.ERROR, childStatuses.toArray(new Status[] {}),
              t.toString(), t);
      return ms;
  }
  
  void show13() {
	    Job job =
	        new Job("Progress Information") {
	          @Override
	          protected IStatus run(IProgressMonitor monitor) {
	            monitor.beginTask("Validation in Progress", 100);
	            for (int i = 0; i < 100; i++) {
	              if (monitor.isCanceled()) {
	                return Status.CANCEL_STATUS;
	              }
	              //                  System.out.println(i);
	              //                  monitor.subTask("Copying file " + (i + 1) + " of " + "100" +
	              // "...");

	              monitor.subTask("Performing validation " + (i + 1) + " of " + "100" + "...");

	              try {
	                Thread.sleep(100);
	              } catch (InterruptedException e) {

	                e.printStackTrace();
	              }
	              monitor.worked(1);
	            }
	            monitor.done();
	            return Status.OK_STATUS;
	          }
	        };
	    job.setUser(true);
	    job.schedule();
	    
	  }

  private void setFormAction(Form form) {
    String helpDesc = "Help message for this panel";
    Action helpAction = ActionUtil.getHelpAction(form.getShell(), helpDesc);
    Action prevAction =
        ActionUtil.getAction(form.getShell(), new PreviousActionHandler(this, "torswitchinfo"));

    form.getToolBarManager().add(prevAction);
    form.getToolBarManager().add(helpAction);

    form.getToolBarManager().update(true);
    form.getMenuManager().update(true);
  }
}
