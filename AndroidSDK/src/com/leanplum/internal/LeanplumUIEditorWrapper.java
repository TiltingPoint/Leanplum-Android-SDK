// Copyright 2017 Leanplum, Inc. All Rights Reserved.
package com.leanplum.internal;

import android.app.Activity;

import com.leanplum.LeanplumEditorMode;
import com.leanplum.LeanplumUIEditor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.leanplum.internal.Constants.ClassUtil.UI_INTERFACE_EDITOR;

/**
 * Wrapper class for the UI Editor. Method calls will be forwarded to UI Editor package if its
 * available.
 */
public class LeanplumUIEditorWrapper implements LeanplumUIEditor {
  private static LeanplumUIEditor interfaceEditorSingleton;
  private static LeanplumUIEditorWrapper instance = null;

  protected LeanplumUIEditorWrapper() {
    // Exists only to defeat instantiation.
  }

  static {
    Class clazz = null;
    try {
      clazz = Class.forName(UI_INTERFACE_EDITOR);
    } catch (ClassNotFoundException ignored) {
    }
    if (clazz != null) {
      Method method = null;
      try {
        method = clazz.getMethod("getInstance", null);
      } catch (NoSuchMethodException e) {
        Util.handleException(e);
      }
      if (method != null) {
        try {
          interfaceEditorSingleton = (LeanplumUIEditor) method.invoke(null);
          if (interfaceEditorSingleton != null) {
            interfaceEditorSingleton.allowInterfaceEditing(Constants.isDevelopmentModeEnabled);
          }
        } catch (IllegalAccessException e) {
          Util.handleException(e);
        } catch (InvocationTargetException e) {
          Util.handleException(e);
        }
      }
    }
  }

  public static LeanplumUIEditorWrapper getInstance() {
    if (instance == null) {
      instance = new LeanplumUIEditorWrapper();
    }
    return instance;
  }

  public static boolean isUIEditorAvailable() {
    return interfaceEditorSingleton != null;
  }

  @Override
  public void allowInterfaceEditing(Boolean isDevelopmentModeEnabled) {
    if (interfaceEditorSingleton != null) {
      interfaceEditorSingleton.allowInterfaceEditing(isDevelopmentModeEnabled);
    }
  }

  @Override
  public void applyInterfaceEdits(Activity activity) {
    if (interfaceEditorSingleton != null && activity != null) {
      interfaceEditorSingleton.applyInterfaceEdits(activity);
    }
  }

  /**
   * Sets the update flag to true.
   */
  @Override
  public void startUpdating() {
    if (interfaceEditorSingleton != null) {
      interfaceEditorSingleton.startUpdating();
    }
  }

  /**
   * Sets the update flag to false.
   */
  @Override
  public void stopUpdating() {
    if (interfaceEditorSingleton != null) {
      interfaceEditorSingleton.stopUpdating();
    }
  }

  @Override
  public void sendUpdate() {
    if (interfaceEditorSingleton != null) {
      interfaceEditorSingleton.sendUpdate();
    }
  }

  @Override
  public void sendUpdateDelayed(int delay) {
    if (interfaceEditorSingleton != null) {
      interfaceEditorSingleton.sendUpdateDelayed(delay);
    }
  }

  @Override
  public void sendUpdateDelayedDefault() {
    if (interfaceEditorSingleton != null) {
      interfaceEditorSingleton.sendUpdateDelayedDefault();
    }
  }

  @Override
  public LeanplumEditorMode getMode() {
    if (interfaceEditorSingleton != null) {
      return interfaceEditorSingleton.getMode();
    }
    return null;
  }

  @Override
  public void setMode(LeanplumEditorMode mode) {
    if (interfaceEditorSingleton != null) {
      interfaceEditorSingleton.setMode(mode);
    }
  }
}
