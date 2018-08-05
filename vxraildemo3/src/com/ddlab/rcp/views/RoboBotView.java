package com.ddlab.rcp.views;

import java.io.File;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.ddlab.rcp.core.Activator;

public class RoboBotView extends ViewPart {

  public static final String ID = "robobot";

  private StyledText robotTxt;
  private Text userTxt;
  private Display display = Display.getDefault();
  private String botName = "Sophia";

  @Override
  public void createPartControl(Composite parent) {
    Composite top = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout(2, false);
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    top.setLayout(layout);

    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);

    Label headerLbl = new Label(top, SWT.NONE);
    GridData gd1 = new GridData();
    gd1.grabExcessHorizontalSpace = true;
    gd1.horizontalSpan = 2;

    headerLbl.setFont(boldFont);
    headerLbl.setText(
        "It is a Robot guided assistance system, ask only relevant question to get information");
    headerLbl.setLayoutData(gd1);

    Label robotLbl = new Label(top, SWT.NONE);
    robotLbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    robotLbl.setText("Robot : ");
    robotLbl.setImage(Activator.getImageDescriptor("icons/robo32.png").createImage());

    robotTxt =
        new StyledText(top, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
    GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
    gd_text_1.heightHint = 200;
    robotTxt.setLayoutData(gd_text_1);

    Label middleLbl = new Label(top, SWT.NONE);
    middleLbl.setText("Enter your query below, robot will try to help with relevant information");
    GridData gd2 = new GridData();
    gd2.grabExcessHorizontalSpace = true;
    gd2.horizontalSpan = 2;
    middleLbl.setLayoutData(gd2);

    Label userLbl = new Label(top, SWT.NONE);
    userLbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    userLbl.setText("You : ");

    userTxt = new Text(top, SWT.NONE);
    GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
    gd_text_3.heightHint = 70;
    userTxt.setLayoutData(gd_text_3);
    addUserTextListener();
  }

  private void addUserTextListener() {
    userTxt.addKeyListener(
        new KeyAdapter() {
          @Override
          public void keyPressed(KeyEvent e) {}

          @Override
          public void keyReleased(KeyEvent e) {
            if (e.keyCode == SWT.CR || e.keyCode == SWT.LF || e.keyCode == SWT.KEYPAD_CR) {
              updateToRobot(userTxt.getText());
              userTxt.setText("");
            }
          }
        });
  }
  
  private DDRobotBot bot = new DDRobotBot();

  private void updateToRobot(String text) {
    String userText = showAndGetUserInput(text);
    String roboText = showAndGetBotResponse(text);

    setStyleForUserInput(userText);
    setStyleForBotResponse(roboText);

    robotTxt.setSelection(robotTxt.getCharCount()); // For auto scroll
  }

//  private static String getResourcesPath() {
//    File currDir = new File("E:/RCP-Demo-2018/vxrailrcpmulti/resources");
//
//    String resourcesPath = currDir.getAbsolutePath();
//    return resourcesPath;
//  }

  private String showAndGetUserInput(String userInputText) {
    String userText = "You :>" + userInputText;
    robotTxt.append(userText);
    robotTxt.append("\n");
    return userText;
  }

  private String showAndGetBotResponse(String userInputText) {
	  
	  System.out.println("user Input text : "+userInputText);
	
	 String botResponseText = bot.getBotResponse(userInputText);
	 System.out.println("botResponseText------>"+botResponseText);
	  
//    String botResponseText = "Hi Darling how are you ?";
    String roboText = botName + " :>" + botResponseText;
    robotTxt.append(roboText);
    robotTxt.append("\n");
    return roboText;
  }

  private void setStyleForUserInput(String userInputText) {
    StyleRange userStyle = new StyleRange();
    userStyle.fontStyle = SWT.ITALIC;
    userStyle.foreground = display.getSystemColor(SWT.COLOR_DARK_GRAY);
    userStyle.length = userInputText.length();

    String completeText = robotTxt.getText();
    int j = completeText.indexOf(userInputText);
    while (j >= 0) {
      userStyle.start = j;
      robotTxt.setStyleRange(userStyle);
      j = completeText.indexOf(userInputText, j + 1);
    }
  }

  private void setStyleForBotResponse(String roboText) {
    StyleRange roboStyle = new StyleRange();
    roboStyle.fontStyle = SWT.BOLD;
    roboStyle.foreground = display.getSystemColor(SWT.COLOR_BLUE);
    roboStyle.length = roboText.length();

    String completeText = robotTxt.getText();
    completeText = robotTxt.getText();
    int i = completeText.indexOf(roboText);
    while (i >= 0) {
      roboStyle.start = i;
      robotTxt.setStyleRange(roboStyle);
      i = completeText.indexOf(roboText, i + 1);
    }
  }

  @Override
  public void setFocus() {
    userTxt.setFocus();
  }
}
