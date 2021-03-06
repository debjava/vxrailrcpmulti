package com.ddlab.rcp.core;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.internal.WorkbenchWindow;

import com.ddlab.rcp.actions.TipOfTheDayContributionItem;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction newWindowAction;
	private OpenViewAction openViewAction;
	private Action messagePopupAction;

	private IWorkbenchAction showHelpAction; // NEW
	private IWorkbenchAction searchHelpAction; // NEW
	private IWorkbenchAction dynamicHelpAction; // NEW

	// For Intro
	private IWorkbenchAction introAction = null;
	
	//For Restart
	private Action restartAction = null;


	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(final IWorkbenchWindow window) {

		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);
		
		restartAction = new RestartAction("Restart",window);
		register(restartAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);

		newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
		register(newWindowAction);

		openViewAction = new OpenViewAction(window, "Open Another Message View", View.ID);
		register(openViewAction);

		messagePopupAction = new MessagePopupAction("Open to Chat", window);
		register(messagePopupAction);

		// introAction = ActionFactory.INTRO.create(window);
		// register(introAction);

		showHelpAction = ActionFactory.HELP_CONTENTS.create(window); // NEW
		register(showHelpAction); // NEW

		searchHelpAction = ActionFactory.HELP_SEARCH.create(window); // NEW
		register(searchHelpAction); // NEW

		dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(window); // NEW
		register(dynamicHelpAction); // NEW

		introAction = ActionFactory.INTRO.create(window);
		register(introAction);

	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);

		menuBar.add(fileMenu);
		// Add a group marker indicating where action set menus will appear.
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(helpMenu);

		// File
		fileMenu.add(newWindowAction);
		fileMenu.add(new Separator());
		fileMenu.add(messagePopupAction);
		fileMenu.add(openViewAction);
		fileMenu.add(new Separator());
		fileMenu.add(restartAction);
		fileMenu.add(exitAction);

		// Help
		helpMenu.add(aboutAction);

		// helpMenu.add(introAction);

		helpMenu.add(showHelpAction); // NEW
		helpMenu.add(searchHelpAction); // NEW
		helpMenu.add(dynamicHelpAction); // NEW
		helpMenu.add(introAction);
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar, "main"));
		toolbar.add(openViewAction);
		toolbar.add(messagePopupAction);
	}

	@Override
	protected void fillStatusLine(IStatusLineManager statusLine) {
		StatusLineManager sm = ((WorkbenchWindow) PlatformUI.getWorkbench().getActiveWorkbenchWindow())
				.getStatusLineManager();
		sm.add(new TipOfTheDayContributionItem("SCREEN"));
		sm.update(true);
	}
}
