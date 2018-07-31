package com.ddlab.rcp.multi.editor;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.ddlab.rcp.actions.ActionUtil;
import com.ddlab.rcp.actions.NextActionHandlerImpl;
import com.ddlab.rcp.contents.SysInfoDesignerContent;
import com.ddlab.rcp.core.Activator;
import com.ddlab.rcp.messages.Messages;

public class SystemInformationPage extends FormPage {

  private static String ID = "systeminformation";
  private static String TITLE = "System Information";
  private Section section;
  private FormToolkit toolkit;
  private SysInfoDesignerContent sysInfoDesigner;

  public SystemInformationPage(FormEditor editor, String id, String title) {
    super(editor, id, title);
    sysInfoDesigner = new SysInfoDesignerContent(this);
  }

  public SystemInformationPage(FormEditor editor) {
    super(editor, ID, TITLE);
    sysInfoDesigner = new SysInfoDesignerContent(this);
  }

  @Override
  protected void createFormContent(IManagedForm managedForm) {
    ScrolledForm mainForm = managedForm.getForm();
    mainForm.setText(TITLE);
    toolkit = managedForm.getToolkit();
    toolkit.decorateFormHeading(mainForm.getForm());

    setFormAction(mainForm.getForm());

    Form form = mainForm.getForm();
    section = createSection(form);

    ToolBar bar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
    createResetToolItem(bar);
    section.setTextClient(bar);

    Composite header = toolkit.createComposite(section, SWT.None);
    // Create a layout of 3 columns
    GridLayout hl = new GridLayout(2, false);
    hl.horizontalSpacing = 10;
    hl.verticalSpacing = 10;
    header.setLayout(hl);

    sysInfoDesigner.designContents(header, form);
    section.setClient(header);
    section.requestLayout();
  }

  private void createResetToolItem(ToolBar toolBar) {
    ToolItem resetItem = new ToolItem(toolBar, SWT.PUSH);
    resetItem.setImage(
        Activator.getImageDescriptor(Messages.SystemInformationView_refreshIcon16).createImage());
    resetItem.setToolTipText(Messages.SystemInformationView_resetFieldMsg);
    resetItem.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            //            MessageDialog.openInformation(new Shell(),
            // Messages.SystemInformationView_titleMsg, Messages.SystemInformationView_dialogMsg);

            //	            show13();
            runInBackgroundProgressService();
          }
        });
  }

  private void runInBackgroundProgressService() {
    Job job =
        new Job("Initiating a critical service") {

          @Override
          protected IStatus run(IProgressMonitor monitor) {
            monitor.beginTask("Initiating critical service in background", 100);
            // execute the task ...

            try {
              for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                monitor.setTaskName("Completed task " + i + "of 10");
                monitor.worked(i * 10);
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
            monitor.done();

            monitor.done();
            return Status.OK_STATUS;
          }
        };
    job.schedule();
    //		job.setUser(true);

  }

  private Section createSection(Form form) {
    Section section =
        toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
    section.setVisible(true);
    section.setEnabled(true);
    section.setBounds(1, 10, 365, 230);

    section.setText(Messages.SystemInformationView_sectionHeaderMsg);
    section.setDescription(Messages.SystemInformationView_sectionDescMsg);
    section.setToolTipText(Messages.SystemInformationView_sectionToolTipMsg);
    toolkit.createCompositeSeparator(section);

    GridLayout layout = new GridLayout(1, true);
    GridData gd_composite = new GridData(SWT.LEFT, SWT.BEGINNING, true, false, 1, 1);
    section.setLayoutData(gd_composite);
    section.setLayout(layout);

    return section;
  }

  private void setFormAction(Form form) {
    String helpDesc = "Help message for this panel";
    Action helpAction = ActionUtil.getHelpAction(form.getShell(), helpDesc);
    Action nextAction =
        ActionUtil.getAction(form.getShell(), new NextActionHandlerImpl(this, "networkservices"));

    form.getToolBarManager().add(nextAction);
    form.getToolBarManager().add(helpAction);

    form.getToolBarManager().update(true);
    form.getMenuManager().update(true);
  }
  
  public void updateErrorImage() {
	  Image baseImage = Activator.getImageDescriptor("icons/vxrail16.png").createImage();
	  Image errorTitleImage = new DecorationOverlayIcon(baseImage,
              PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
                      ISharedImages.IMG_DEC_FIELD_ERROR), IDecoration.BOTTOM_LEFT)
              .createImage();
	  ((VxRailMultiPageEditor)getEditor()).setTitleImage(errorTitleImage);
  }
  
  public void updateNormalImage() {
	  Image baseImage = Activator.getImageDescriptor("icons/vxrail16.png").createImage();
	  ((VxRailMultiPageEditor)getEditor()).setTitleImage(baseImage);
  }
}
