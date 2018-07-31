package com.ddlab.rcp.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.ddlab.rcp.core.Activator;
import com.ddlab.rcp.multi.editor.VxRailEditorInput;

public class NavigationView extends ViewPart {
  public static final String ID = "vxraildemo1.navigationView";
  private TreeViewer viewer;

  class TreeObject {
    private String name;
    private TreeParent parent;

    public TreeObject(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setParent(TreeParent parent) {
      this.parent = parent;
    }

    public TreeParent getParent() {
      return parent;
    }

    public String toString() {
      return getName();
    }
  }

  class TreeParent extends TreeObject {
    private List<TreeObject> children;
    private String imagePath;
    private String viewId;

    public TreeParent(String name, String viewId) {
      super(name);
      this.viewId = viewId;
      children = new ArrayList<>();
    }

    public TreeParent(String name) {
      super(name);
      children = new ArrayList<>();
    }

    public void addChild(TreeObject child) {
      children.add(child);
      child.setParent(this);
    }

    public void removeChild(TreeObject child) {
      children.remove(child);
      child.setParent(null);
    }

    public TreeObject[] getChildren() {
      return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
    }

    public boolean hasChildren() {
      return children.size() > 0;
    }

    public void setImagePath(String imagePath) {
      this.imagePath = imagePath;
    }

    public String getImagePath() {
      return imagePath;
    }

    public void setViewId(String viewId) {
      this.viewId = viewId;
    }

    public String getViewId() {
      return viewId;
    }
  }

  class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

    @Override
    public void inputChanged(Viewer v, Object oldInput, Object newInput) {}

    @Override
    public void dispose() {}

    @Override
    public Object[] getElements(Object parent) {
      return getChildren(parent);
    }

    @Override
    public Object getParent(Object child) {
      if (child instanceof TreeObject) {
        return ((TreeObject) child).getParent();
      }
      return null;
    }

    @Override
    public Object[] getChildren(Object parent) {
      if (parent instanceof TreeParent) {
        return ((TreeParent) parent).getChildren();
      }
      return new Object[0];
    }

    @Override
    public boolean hasChildren(Object parent) {
      if (parent instanceof TreeParent) return ((TreeParent) parent).hasChildren();
      return false;
    }
  }

  class ViewLabelProvider extends LabelProvider {

    @Override
    public String getText(Object obj) {
      return obj.toString();
    }

    @Override
    public Image getImage(Object obj) {
      Image parentImage = null;
      if (obj instanceof TreeParent) {
        TreeParent tp = (TreeParent) obj;
        if (tp.getImagePath() == null) {
          parentImage =
              PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
        } else {
          parentImage = Activator.getImageDescriptor(tp.getImagePath()).createImage();
        }
      }
      return parentImage;
    }
  }


  private TreeObject createNavigationTreeModel() {
    TreeParent vxRailTreeparent = new TreeParent("VxRail");
    vxRailTreeparent.setImagePath("/icons/vxrail16.png");
    
    TreeParent ecsTreeparent = new TreeParent("ECS");
    ecsTreeparent.setImagePath("/icons/ecs16.png");

    TreeParent root = new TreeParent("");
    root.addChild(vxRailTreeparent);
    root.addChild(ecsTreeparent);
    

    return root;
  }

  @Override
  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    viewer.setContentProvider(new ViewContentProvider());
    viewer.setLabelProvider(new ViewLabelProvider());
    viewer.setInput(createNavigationTreeModel());

    Font font1 = new Font(null, "Tahoma", 12, SWT.BOLD);
    viewer.getTree().setFont(font1);
    addTreeViewDoubleClickListener(viewer);
  }

  private void addTreeViewDoubleClickListener(TreeViewer viewer) {
    viewer.addDoubleClickListener(
        e -> {
          IStructuredSelection selectedItem = (IStructuredSelection) e.getSelection();
          Object obj = selectedItem.getFirstElement();
          if (obj instanceof TreeParent) {
            try {
              getSite()
                  .getPage()
                  .openEditor(
                      new VxRailEditorInput("Some Name"),
                      "vxRailMultiPageID",
                      true,
                      IWorkbenchPage.MATCH_ID);
              // To open single instance IWorkbenchPage.MATCH_ID
            } catch (PartInitException e1) {
              e1.printStackTrace();
            }
          }
        });
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  public TreeViewer getViewer() {
    return viewer;
  }

  public void updateImage(String name) {
    viewer.setLabelProvider(new ErrorLabelProvider(name));
  }

  public void updateNormal() {
    viewer.setLabelProvider(new ViewLabelProvider());
  }

  class ErrorLabelProvider extends LabelProvider {

    private String name;

    public ErrorLabelProvider(String name) {
      this.name = name;
    }

    @Override
    public Image getImage(Object obj) {
      Image parentImage = null;
      if (obj instanceof TreeParent) {
        TreeParent tp = (TreeParent) obj;
        if (tp.getName().equals(name)) {
          String baseImgPath = tp.getImagePath();
          Image baseImage = Activator.getImageDescriptor(baseImgPath).createImage();
          Image errorTitleImage =
              new DecorationOverlayIcon(
                      baseImage,
                      PlatformUI.getWorkbench()
                          .getSharedImages()
                          .getImageDescriptor(ISharedImages.IMG_DEC_FIELD_ERROR),
                      IDecoration.BOTTOM_LEFT)
                  .createImage();
          parentImage = errorTitleImage;

        } else {
          parentImage = Activator.getImageDescriptor(tp.getImagePath()).createImage();
        }
      }
      return parentImage;
    }
  }
}
