package com.ddlab.rcp.core;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class RestartAction extends Action {

  RestartAction(String text, IWorkbenchWindow window) {
    super(text);
    // The id is used to refer to the action in a menu or toolbar
    setId(ICommandIds.CMD_RESTART);
    // Associate the action with a pre-defined command, to allow key bindings.
    setActionDefinitionId(ICommandIds.CMD_RESTART);
    setImageDescriptor(com.ddlab.rcp.core.Activator.getImageDescriptor("/icons/restart16.png"));
  }

  @Override
  public void run() {
    PlatformUI.getWorkbench().restart(true);
  }
}
