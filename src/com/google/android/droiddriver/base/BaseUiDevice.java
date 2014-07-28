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

package com.google.android.droiddriver.base;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.droiddriver.UiDevice;
import com.google.android.droiddriver.actions.Action;
import com.google.android.droiddriver.actions.SingleKeyAction;
import com.google.android.droiddriver.util.FileUtils;
import com.google.android.droiddriver.util.Logs;

import java.io.BufferedOutputStream;

/**
 * Base implementation of {@link UiDevice}.
 */
public abstract class BaseUiDevice implements UiDevice {
  // power off may not trigger new events
  private static final SingleKeyAction POWER_OFF = new SingleKeyAction(KeyEvent.KEYCODE_POWER, 0,
      false);
  // power on should always trigger new events
  private static final SingleKeyAction POWER_ON = new SingleKeyAction(KeyEvent.KEYCODE_POWER,
      1000L, false);

  @Override
  public boolean isScreenOn() {
    PowerManager pm =
        (PowerManager) getContext().getInstrumentation().getTargetContext()
            .getSystemService(Service.POWER_SERVICE);
    return pm.isScreenOn();
  }

  @Override
  public void wakeUp() {
    if (!isScreenOn()) {
      perform(POWER_ON);
    }
  }

  @Override
  public void sleep() {
    if (isScreenOn()) {
      perform(POWER_OFF);
    }
  }

  @Override
  public void pressBack() {
    perform(SingleKeyAction.BACK);
  }

  @Override
  public boolean perform(Action action) {
    return getContext().getDriver().getRootElement().perform(action);
  }

  @Override
  public boolean takeScreenshot(String path) {
    return takeScreenshot(path, Bitmap.CompressFormat.PNG, 0);
  }

  @Override
  public boolean takeScreenshot(String path, CompressFormat format, int quality) {
    Logs.call(this, "takeScreenshot", path, quality);
    Bitmap screenshot = takeScreenshot();
    if (screenshot == null) {
      return false;
    }
    BufferedOutputStream bos = null;
    try {
      bos = FileUtils.open(path);
      screenshot.compress(format, quality, bos);
      return true;
    } catch (Exception e) {
      Logs.log(Log.WARN, e);
      return false;
    } finally {
      if (bos != null) {
        try {
          bos.close();
        } catch (Exception e) {
          // ignore
        }
      }
      screenshot.recycle();
    }
  }

  protected abstract Bitmap takeScreenshot();

  protected abstract DroidDriverContext<?, ?> getContext();
}
