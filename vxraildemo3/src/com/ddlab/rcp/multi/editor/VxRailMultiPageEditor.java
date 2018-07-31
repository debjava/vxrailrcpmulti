package com.ddlab.rcp.multi.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.ddlab.rcp.core.Activator;

public class VxRailMultiPageEditor extends FormEditor {

  @Override
  protected void addPages() {
    try {
      addPage(new SystemInformationPage(this));
      addPage(new NetworkServicesPage(this));
      addPage(new HostDetailsPage(this));
      addPage(new VirtualizationPage(this));
      addPage(new ToRSwitchInfoPage(this));
      addPage(new ValidationPage(this));

      Image sysInfoImg = Activator.getImageDescriptor("/icons/SysInfo16.png").createImage();
      Image netServiceImg =
          Activator.getImageDescriptor("/icons/NetworkService16.png").createImage();
      Image hostDetailsImg = Activator.getImageDescriptor("/icons/Host16.png").createImage();
      Image virtualizationImg = Activator.getImageDescriptor("/icons/Virtualization16.png").createImage();
      Image torSwitchImg = Activator.getImageDescriptor("/icons/Switch16.png").createImage();
      Image valdnImg = Activator.getImageDescriptor("/icons/Validation16.png").createImage();

      setPageImage(0, sysInfoImg);
      setPageImage(1, netServiceImg);
      setPageImage(2, hostDetailsImg);
      setPageImage(3, virtualizationImg);
      setPageImage(4, torSwitchImg);
      setPageImage(5, valdnImg);
      

      // Host details,Virtualization,ToR switch Information,validation

    } catch (PartInitException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void doSave(IProgressMonitor monitor) {}

  @Override
  public void doSaveAs() {}

  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }
  
  @Override
  protected void setTitleImage(Image titleImage) {
    super.setTitleImage(titleImage);
  }
}
