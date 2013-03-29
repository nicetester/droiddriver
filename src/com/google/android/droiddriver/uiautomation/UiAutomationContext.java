/*
 * Copyright (C) 2013 DroidDriver committers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.droiddriver.uiautomation;

import android.app.UiAutomation;
import android.view.InputEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.android.droiddriver.InputInjector;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;

import java.util.Map;

/**
 * Internal helper for managing all instances.
 */
public class UiAutomationContext {
  private final UiAutomation uiAutomation;
  private final InputInjector injector;
  private final Map<AccessibilityNodeInfo, UiAutomationElement> map = new MapMaker().weakKeys()
      .makeMap();

  UiAutomationContext(UiAutomation uiAutomation) {
    this.uiAutomation = Preconditions.checkNotNull(uiAutomation);
    injector = new InputInjector() {
      @Override
      public boolean injectInputEvent(InputEvent event) {
        return getUiAutomation().injectInputEvent(event, true /* sync */);
      }
    };
  }

  public UiAutomation getUiAutomation() {
    return uiAutomation;
  }

  public InputInjector getInjector() {
    return injector;
  }

  public UiAutomationElement getUiElement(AccessibilityNodeInfo node) {
    UiAutomationElement element = map.get(node);
    if (element == null) {
      element = new UiAutomationElement(this, node);
      map.put(node, element);
    }
    return element;
  }
}